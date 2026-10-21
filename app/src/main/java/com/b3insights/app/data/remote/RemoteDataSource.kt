package com.b3insights.app.data.remote

import com.b3insights.app.BuildConfig
import com.b3insights.app.data.model.*
import com.b3insights.app.util.detectAssetType
import com.b3insights.app.util.normalizeB3Ticker
import com.b3insights.app.util.toYahooSymbol
import com.b3insights.app.util.isCryptoTicker
import com.b3insights.app.util.quoteTickerFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Fonte de dados:
 *  - Yahoo Finance = padrão (cotação, gráfico, dividendos de qualquer ticker da B3)
 *  - brapi.dev     = busca de tickers + perfil/P/L/logo (token no local.properties)
 *
 * O plano gratuito da brapi responde 403 se pedirmos dividendos — por isso
 * proventos NUNCA são pedidos lá.
 */
class RemoteDataSource {

    /** Cache simples de cotações (TTL 60s) para acelerar carteira/rankings. */
    private val quoteCache = java.util.concurrent.ConcurrentHashMap<String, Pair<Long, AssetQuote>>()
    private val quoteCacheTtlMs = 60_000L

    private val token: String? get() = BuildConfig.BRAPI_API_TOKEN.ifBlank { null }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    }

    private val headerInterceptor = Interceptor { chain ->
        val request = chain.request()
        val builder = request.newBuilder()
            .header("Accept", "application/json")
        val host = request.url.host
        if (host.contains("yahoo", ignoreCase = true)) {
            builder.header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
            )
        }
        if (host.contains("brapi", ignoreCase = true)) {
            token?.let { builder.header("Authorization", "Bearer $it") }
        }
        chain.proceed(builder.build())
    }

    private val retryInterceptor = Interceptor { chain ->
        var attempt = 0
        var response: okhttp3.Response? = null
        var lastError: Exception? = null
        while (attempt < 3) {
            try {
                response?.close()
                response = chain.proceed(chain.request())
                if (response.isSuccessful || response.code in listOf(400, 401, 403, 404)) {
                    return@Interceptor response
                }
                response.close()
            } catch (e: Exception) {
                lastError = e
            }
            attempt++
            try { Thread.sleep(300L * attempt) } catch (_: InterruptedException) {}
        }
        response ?: throw (lastError ?: java.io.IOException("Falha de rede após 3 tentativas"))
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(retryInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(12, TimeUnit.SECONDS)
        .readTimeout(18, TimeUnit.SECONDS)
        .writeTimeout(18, TimeUnit.SECONDS)
        .callTimeout(35, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val yahooService: YahooFinanceService = Retrofit.Builder()
        .baseUrl("https://query1.finance.yahoo.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(YahooFinanceService::class.java)

    private val brapiService: BrapiService = Retrofit.Builder()
        .baseUrl("https://brapi.dev/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BrapiService::class.java)


    /**
     * Cotação de cripto via API pública Coinbase (exchange-grade).
     * product: BTC-USD, ETH-USD, etc.
     */
    private fun fetchCoinbaseSpot(product: String): AssetQuote? {
        return try {
            val url = "https://api.coinbase.com/v2/prices/$product/spot"
            val req = okhttp3.Request.Builder().url(url).header("Accept", "application/json").build()
            okHttpClient.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return null
                val body = resp.body?.string() ?: return null
                val amount = org.json.JSONObject(body)
                    .getJSONObject("data")
                    .getString("amount")
                    .toDoubleOrNull() ?: return null
                val base = product.substringBefore("-")
                AssetQuote(
                    ticker = product,
                    name = "$base USD",
                    assetType = AssetType.CRIPTO,
                    sector = null,
                    currentPrice = amount,
                    dayChange = null,
                    dayChangePercent = null,
                    dayHigh = null,
                    dayLow = null,
                    volume = null,
                    marketCap = null,
                    lastUpdated = System.currentTimeMillis(),
                    logoUrl = null
                )
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun fetchCoinbaseCandles(product: String, granularitySec: Int = 3600, limit: Int = 72): List<PricePoint> {
        return try {
            val url = "https://api.exchange.coinbase.com/products/$product/candles?granularity=$granularitySec"
            val req = okhttp3.Request.Builder().url(url).header("Accept", "application/json").build()
            okHttpClient.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return emptyList()
                val body = resp.body?.string() ?: return emptyList()
                // [[time, low, high, open, close, volume], ...]
                val arr = org.json.JSONArray(body)
                val out = mutableListOf<PricePoint>()
                for (i in 0 until arr.length()) {
                    val row = arr.optJSONArray(i) ?: continue
                    val ts = row.optLong(0) * 1000L
                    val close = row.optDouble(4, Double.NaN)
                    if (!close.isNaN()) out.add(PricePoint(ts, close))
                }
                out.sortedBy { it.timestamp }.takeLast(limit)
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun fetchQuote(ticker: String, forceRefresh: Boolean = false): DataResult<AssetQuote> = withContext(Dispatchers.IO) {
        val code = normalizeB3Ticker(ticker)
        if (code.isBlank()) return@withContext DataResult.Error("asset_not_found")

        if (!forceRefresh) {
            quoteCache[code]?.let { (ts, q) ->
                if (System.currentTimeMillis() - ts < quoteCacheTtlMs) {
                    // Cache de performance — não é “sem conexão”
                    return@withContext DataResult.Success(q, fromCache = false)
                }
            }
        }

        try {
            // Cripto: Coinbase público primeiro (mais fiel a exchange; você opera na Binance)
            if (isCryptoTicker(code) || code.contains("-USD")) {
                val product = quoteTickerFor(code, "CRIPTO").let {
                    if (it.endsWith("-USD")) it else "$it-USD"
                }.replace("USDT", "USD")
                val cb = fetchCoinbaseSpot(product)
                if (cb?.currentPrice != null) {
                    quoteCache[code] = System.currentTimeMillis() to cb
                    return@withContext DataResult.Success(cb)
                }
            }

            // C-06: brapi primeiro; Yahoo só como fallback (menos dependência crítica)
            val brapiFirst = runCatching { brapiService.getQuote(code, range = "1d", interval = "1d") }.getOrNull()
            val fromBrapiFirst = brapiFirst?.results?.firstOrNull()?.toAssetQuote()
            if (fromBrapiFirst?.currentPrice != null) {
                quoteCache[code] = System.currentTimeMillis() to fromBrapiFirst
                return@withContext DataResult.Success(fromBrapiFirst)
            }
            val yahoo = runCatching { yahooService.getChart(toYahooSymbol(code), "5d", "1d") }.getOrNull()
            val fromYahoo = yahoo?.chart?.result?.firstOrNull()?.toAssetQuote(code)
            if (fromYahoo?.currentPrice != null) {
                quoteCache[code] = System.currentTimeMillis() to fromYahoo
                return@withContext DataResult.Success(fromYahoo)
            }

            val brapi = runCatching { brapiService.getQuote(code, range = "1d", interval = "1d") }.getOrNull()
            val fromBrapi = brapi?.results?.firstOrNull()?.toAssetQuote()
            if (fromBrapi != null) {
                quoteCache[code] = System.currentTimeMillis() to fromBrapi
                DataResult.Success(fromBrapi)
            } else {
                // fallback: cotação expirada do cache, se existir
                quoteCache[code]?.let { (_, q) ->
                    return@withContext DataResult.Success(q, fromCache = true)
                }
                DataResult.Error("asset_not_found")
            }
        } catch (e: java.io.IOException) {
            quoteCache[code]?.let { (_, q) ->
                return@withContext DataResult.Success(q, fromCache = true)
            }
            DataResult.Error("no_connection")
        } catch (e: Exception) {
            quoteCache[code]?.let { (_, q) ->
                return@withContext DataResult.Success(q, fromCache = true)
            }
            DataResult.Error("update_error")
        }
    }

    suspend fun fetchAssetDetail(ticker: String, period: ChartPeriod): DataResult<AssetDetail> =
        withContext(Dispatchers.IO) {
            val code = normalizeB3Ticker(ticker)
            if (code.isBlank()) return@withContext DataResult.Error("asset_not_found")
            val yahooSymbol = toYahooSymbol(code)
            val brapiRange = period.toBrapiRange()

            try {
                coroutineScope {
                    val yahooChartDef = async {
                        runCatching {
                            yahooService.getChart(yahooSymbol, period.range, period.interval)
                        }.getOrNull()
                    }
                    val yahooDivsDef = async {
                        val longRange = period == ChartPeriod.Y5 || period == ChartPeriod.MAX
                        if (longRange) null
                        else runCatching {
                            yahooService.getChart(yahooSymbol, "5y", "1d", events = "div")
                        }.getOrNull()
                    }
                    val brapiQuoteDef = async {
                        val withModules = runCatching {
                            brapiService.getQuote(
                                tickers = code,
                                range = brapiRange,
                                interval = "1d",
                                fundamental = true,
                                modules = "defaultKeyStatistics,financialData"
                            )
                        }.getOrNull()
                        if (withModules?.results != null) withModules
                        else runCatching {
                            brapiService.getQuote(
                                tickers = code,
                                range = brapiRange,
                                interval = "1d",
                                fundamental = true
                            )
                        }.getOrNull()
                    }
                    val brapiProfileDef = async {
                        runCatching { brapiService.getProfile(code) }.getOrNull()
                    }
                    // Fallbacks de balanço e DRE (podem falhar no plano gratuito → lista vazia)
                    val balanceDef = async {
                        runCatching { brapiService.getBalanceSheet(code) }.getOrNull()
                    }
                    val incomeDef = async {
                        runCatching { brapiService.getIncomeStatement(code) }.getOrNull()
                    }
                    val yahooSummaryDef = async {
                        runCatching {
                            yahooService.getQuoteSummary(yahooSymbol)
                        }.getOrNull()
                    }
                    val fiiIndicatorsDef = async {
                        if (code.endsWith("11")) {
                            runCatching { brapiService.getFiiIndicators(code) }.getOrNull()
                        } else null
                    }

                    val yahooChart = yahooChartDef.await()?.chart?.result?.firstOrNull()
                    val yahooDivsChart = yahooDivsDef.await()?.chart?.result?.firstOrNull() ?: yahooChart
                    val brapiQuote = brapiQuoteDef.await()?.results?.firstOrNull()
                    val profile = brapiProfileDef.await()?.results?.firstOrNull()?.data
                    val balanceResp = balanceDef.await()
                    val incomeResp = incomeDef.await()
                    val fiiResp = fiiIndicatorsDef.await()
                    val yahooSummary = yahooSummaryDef.await()?.quoteSummary?.result?.firstOrNull()

                    if (yahooChart == null && brapiQuote == null) {
                        return@coroutineScope DataResult.Error("asset_not_found")
                    }

                    val yahooQuote = yahooChart?.toAssetQuote(code)
                    val brapiMapped = brapiQuote?.toAssetQuote()
                    val mergedQuote = mergeQuotes(code, yahooQuote, brapiMapped, profile)

                    var history = yahooChart?.toPriceHistory().orEmpty().ifEmpty {
                        brapiQuote?.historicalDataPrice.orEmpty().mapNotNull { p ->
                            val date = p.date ?: return@mapNotNull null
                            val close = p.close ?: return@mapNotNull null
                            PricePoint(timestamp = date * 1000, close = close)
                        }
                    }
                    if (isCryptoTicker(code) || code.contains("-USD")) {
                        val product = quoteTickerFor(code, "CRIPTO").let {
                            if (it.endsWith("-USD")) it else "$it-USD"
                        }.replace("USDT", "USD")
                        val gran = when (period) {
                            ChartPeriod.D1 -> 300
                            ChartPeriod.W1 -> 900
                            ChartPeriod.M1 -> 3600
                            ChartPeriod.M3, ChartPeriod.M6 -> 21600
                            else -> 86400
                        }
                        val cbHist = fetchCoinbaseCandles(product, gran, 200)
                        if (cbHist.isNotEmpty()) history = cbHist
                    }

                    val dividends = yahooDivsChart.toDividendEntries(mergedQuote.assetType)
                    val dy = computeDividendYield(dividends, mergedQuote.currentPrice)
                    val lastDiv = dividends.firstOrNull()?.valuePerShare
                    val oneYearAgoMs = System.currentTimeMillis() - 365L * 24 * 60 * 60 * 1000
                    val dpsLtm = dividends.filter { it.exDate >= oneYearAgoMs }.sumOf { it.valuePerShare }
                        .takeIf { it > 0 }

                    val balanceSheet = mapBalanceSheet(balanceResp).ifEmpty {
                        yahooSummary?.balanceSheetHistory?.balanceSheetStatements.orEmpty().mapNotNull { st ->
                            val ts = st.endDate?.raw?.toLong() ?: return@mapNotNull null
                            val label = java.text.SimpleDateFormat("yyyy", java.util.Locale.US)
                                .format(java.util.Date(ts * 1000))
                            com.b3insights.app.data.model.BalanceSheetEntry(
                                periodLabel = label,
                                totalAssets = st.totalAssets?.raw,
                                totalLiabilities = st.totalLiab?.raw,
                                netWorth = st.totalStockholderEquity?.raw,
                                cash = null,
                                totalDebt = null,
                                netDebt = null,
                                receivables = null,
                                fixedAssets = null
                            )
                        }
                    }
                    val results = mapIncomeStatement(incomeResp).ifEmpty {
                        yahooSummary?.incomeStatementHistory?.incomeStatementHistory.orEmpty().mapNotNull { st ->
                            val ts = st.endDate?.raw?.toLong() ?: return@mapNotNull null
                            val label = java.text.SimpleDateFormat("yyyy", java.util.Locale.US)
                                .format(java.util.Date(ts * 1000))
                            com.b3insights.app.data.model.ResultsEntry(
                                periodLabel = label,
                                revenue = st.totalRevenue?.raw,
                                grossProfit = st.grossProfit?.raw,
                                ebitda = null,
                                ebit = st.operatingIncome?.raw,
                                netIncome = st.netIncome?.raw,
                                netMargin = null,
                                ebitdaMargin = null
                            )
                        }
                    }

                    val fiiDto = fiiResp?.fiis?.firstOrNull()
                    val ks = brapiQuote?.defaultKeyStatistics
                    val fd = brapiQuote?.financialData
                    // ROE/margens da brapi costumam vir em fração (0.22 = 22%)
                    fun pct(v: Double?): Double? = v?.let { if (kotlin.math.abs(it) <= 1.5) it * 100.0 else it }
                    val indicators = AssetIndicators(
                        pl = brapiQuote?.priceEarnings ?: ks?.trailingPE ?: yahooSummary?.defaultKeyStatistics?.trailingPE?.raw ?: yahooSummary?.summaryDetail?.trailingPE?.raw,
                        pvp = if (mergedQuote.assetType == com.b3insights.app.data.model.AssetType.FII) {
                            fiiDto?.priceToNav // não usar priceToBook de ação como P/VP de FII
                        } else {
                            ks?.priceToBook ?: yahooSummary?.defaultKeyStatistics?.priceToBook?.raw ?: yahooSummary?.summaryDetail?.priceToBook?.raw
                        },
                        lpa = brapiQuote?.earningsPerShare,
                        vpa = ks?.bookValue ?: fiiDto?.navPerShare ?: yahooSummary?.defaultKeyStatistics?.bookValue?.raw,
                        roe = pct(fd?.returnOnEquity) ?: yahooSummary?.financialData?.returnOnEquity?.raw?.let { if (kotlin.math.abs(it) <= 1.5) it * 100.0 else it },
                        roic = null, // ROA ≠ ROIC — não substituir (auditoria C-05)
                        netMargin = pct(fd?.profitMargins ?: ks?.profitMargins),
                        ebitdaMargin = pct(fd?.operatingMargins),
                        netDebtToEbitda = null, // debtToEquity ≠ dívida líquida/EBITDA (auditoria C-05)
                        marketCap = mergedQuote.marketCap ?: ks?.marketCap,
                        freeFloat = ks?.floatShares,
                        revenue = fd?.totalRevenue,
                        revenueGrowth = pct(fd?.revenueGrowth),
                        profitGrowth = pct(fd?.earningsGrowth),
                        dividendYield = fiiDto?.dividendYield12m?.let { it * 100 }
                            ?: ks?.dividendYield?.let { pct(it) }
                            ?: ks?.yieldValue?.let { pct(it) }
                            ?: dy,
                        dividendPerShare = dpsLtm ?: lastDiv, // DPS anual (soma 12m); fallback último provento
                        annualYield = fiiDto?.dividendYield12m?.let { it * 100 } ?: dy,
                        monthlyYield = fiiDto?.dividendYield1m?.let { it * 100 },
                        quotaPrice = if (mergedQuote.assetType == AssetType.FII) mergedQuote.currentPrice else null,
                        quotaBookValue = fiiDto?.navPerShare ?: ks?.bookValue,
                        netWorth = fiiDto?.equity,
                        numberOfShareholders = fiiDto?.totalInvestors
                    )

                    DataResult.Success(
                        AssetDetail(
                            quote = mergedQuote,
                            about = profile?.longBusinessSummary ?: brapiQuote?.longSummary,
                            indicators = indicators,
                            dividends = dividends,
                            balanceSheet = balanceSheet,
                            results = results,
                            news = NewsFetcher.fetchNewsForTicker(code, limit = 12),
                            reports = emptyList(),
                            priceHistory = history
                        )
                    )
                }
            } catch (e: java.io.IOException) {
                DataResult.Error("no_connection")
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 404) DataResult.Error("asset_not_found") else DataResult.Error("update_error")
            } catch (e: Exception) {
                DataResult.Error("update_error")
            }
        }

    suspend fun fetchAllStocks(limit: Int = 500): DataResult<List<AssetQuote>> =
        withContext(Dispatchers.IO) {
            try {
                val resp = brapiService.listStocks(type = "stock", limit = limit)
                val list = resp.stocks.orEmpty()
                    .filter { it.type.equals("stock", true) || it.subType.equals("stock", true) || it.type == null }
                    .map { it.toAssetQuote() }
                    .sortedBy { it.ticker }
                if (list.isEmpty()) DataResult.Error("asset_not_found")
                else DataResult.Success(list)
            } catch (e: java.io.IOException) {
                DataResult.Error("no_connection")
            } catch (e: Exception) {
                DataResult.Error("update_error")
            }
        }

    suspend fun searchTickers(query: String): DataResult<List<AssetQuote>> = withContext(Dispatchers.IO) {
        val q = query.trim()
        if (q.isBlank()) return@withContext DataResult.Success(emptyList())
        try {
            val results = linkedMapOf<String, AssetQuote>()

            // Atalhos de cripto: BTC, ETH, bitcoin...
            val cryptoAlias = resolveCryptoQuery(q)
            if (cryptoAlias != null) {
                when (val c = fetchQuote(cryptoAlias)) {
                    is DataResult.Success -> results[c.data.ticker] = c.data.copy(
                        assetType = AssetType.CRIPTO
                    )
                    else -> Unit
                }
            }

            val brapiItems = runCatching {
                brapiService.searchAssets(search = q, limit = 15).stocks.orEmpty()
            }.getOrDefault(emptyList())
            brapiItems.forEach { dto ->
                val aq = dto.toAssetQuote()
                results.putIfAbsent(aq.ticker, aq)
            }

            val yahooItems = runCatching {
                yahooService.search(q).quotes.orEmpty()
            }.getOrDefault(emptyList())

            yahooItems.forEach { yq ->
                val symbol = yq.symbol?.uppercase().orEmpty()
                val isCrypto = yq.quoteType.equals("CRYPTOCURRENCY", true) ||
                    symbol.contains("-USD") || symbol.endsWith("USDT")
                if (yq.isBrazilianListing() || isCrypto) {
                    yq.toAssetQuote()?.let { aq ->
                        val typed = if (isCrypto) aq.copy(assetType = AssetType.CRIPTO) else aq
                        results.putIfAbsent(typed.ticker, typed)
                    }
                }
            }

            if (results.isEmpty()) {
                // Ticker direto (ação B3 ou cripto BTC-USD)
                val candidates = buildList {
                    add(q)
                    val up = q.uppercase()
                    if (!up.contains("-") && up.length <= 6) {
                        add("$up-USD")
                    }
                }
                for (cand in candidates) {
                    when (val direct = fetchQuote(cand)) {
                        is DataResult.Success -> {
                            results[direct.data.ticker] = direct.data
                            break
                        }
                        else -> Unit
                    }
                }
            }

            DataResult.Success(results.values.toList())
        } catch (e: java.io.IOException) {
            DataResult.Error("no_connection")
        } catch (e: Exception) {
            DataResult.Error("update_error")
        }
    }

    private fun resolveCryptoQuery(raw: String): String? {
        val q = raw.trim().lowercase()
        val map = mapOf(
            "btc" to "BTC-USD", "bitcoin" to "BTC-USD",
            "eth" to "ETH-USD", "ethereum" to "ETH-USD",
            "sol" to "SOL-USD", "solana" to "SOL-USD",
            "bnb" to "BNB-USD", "binance" to "BNB-USD",
            "xrp" to "XRP-USD", "ripple" to "XRP-USD",
            "ada" to "ADA-USD", "cardano" to "ADA-USD",
            "doge" to "DOGE-USD", "dogecoin" to "DOGE-USD",
            "avax" to "AVAX-USD", "dot" to "DOT-USD",
            "link" to "LINK-USD", "ltc" to "LTC-USD"
        )
        if (q.uppercase().endsWith("-USD") || q.uppercase().endsWith("-BRL")) return q.uppercase()
        return map[q]
    }

    private fun ChartPeriod.toBrapiRange(): String = when (this) {
        ChartPeriod.D1 -> "1d"
        ChartPeriod.W1 -> "5d"
        ChartPeriod.M1 -> "1mo"
        ChartPeriod.M3 -> "3mo"
        ChartPeriod.M6 -> "6mo"
        ChartPeriod.Y1 -> "1y"
        ChartPeriod.Y5 -> "5y"
        ChartPeriod.MAX -> "max"
    }

    private fun mergeQuotes(
        ticker: String,
        yahoo: AssetQuote?,
        brapi: AssetQuote?,
        profile: BrapiProfileData?
    ): AssetQuote {
        val base = yahoo ?: brapi ?: AssetQuote(
            ticker = ticker,
            name = null,
            assetType = detectAssetType(null, ticker, null),
            sector = profile?.sector,
            currentPrice = null,
            dayChange = null,
            dayChangePercent = null,
            dayHigh = null,
            dayLow = null,
            volume = null,
            marketCap = null,
            lastUpdated = null
        )
        return base.copy(
            name = listOf(base.name, brapi?.name).firstOrNull { !it.isNullOrBlank() },
            sector = profile?.sector ?: profile?.industry ?: brapi?.sector ?: base.sector,
            marketCap = base.marketCap ?: brapi?.marketCap,
            logoUrl = brapi?.logoUrl ?: base.logoUrl,
            assetType = detectAssetType(
                apiType = brapi?.assetType?.name,
                ticker = ticker,
                name = base.name ?: brapi?.name
            ).takeIf { it != AssetType.OUTRO } ?: base.assetType
        )
    }

    private fun YahooChartResult.toAssetQuote(ticker: String): AssetQuote {
        val meta = this.meta
        val series = indicators?.quote?.firstOrNull()
        val lastClose = series?.close?.asReversed()?.firstOrNull { it != null }
        val price = meta?.regularMarketPrice ?: lastClose
        val prev = meta?.chartPreviousClose ?: meta?.previousClose
        val change = if (price != null && prev != null) price - prev else null
        val changePct = meta?.regularMarketChangePercent
            ?: if (change != null && prev != null && prev != 0.0) (change / prev) * 100.0 else null
        val name = meta?.longName ?: meta?.shortName
        val highs = series?.high?.filterNotNull()
        val lows = series?.low?.filterNotNull()
        val volumes = series?.volume?.filterNotNull()
        return AssetQuote(
            ticker = ticker,
            name = name,
            assetType = detectAssetType(meta?.instrumentType, ticker, name),
            sector = null,
            currentPrice = price,
            dayChange = change,
            dayChangePercent = changePct,
            dayHigh = meta?.regularMarketDayHigh ?: highs?.maxOrNull(),
            dayLow = meta?.regularMarketDayLow ?: lows?.minOrNull(),
            volume = meta?.regularMarketVolume ?: volumes?.lastOrNull(),
            marketCap = null,
            lastUpdated = meta?.regularMarketTime?.let { it * 1000 },
            logoUrl = null
        )
    }

    private fun YahooChartResult.toPriceHistory(): List<PricePoint> {
        val times = timestamp.orEmpty()
        val closes = indicators?.quote?.firstOrNull()?.close.orEmpty()
        if (times.isEmpty() || closes.isEmpty()) return emptyList()
        val n = minOf(times.size, closes.size)
        val points = ArrayList<PricePoint>(n)
        for (i in 0 until n) {
            val close = closes[i] ?: continue
            points.add(PricePoint(timestamp = times[i] * 1000, close = close))
        }
        return points
    }

    private fun YahooChartResult?.toDividendEntries(assetType: AssetType): List<DividendEntry> {
        val typeLabel = if (assetType == AssetType.FII) "Rendimento" else "Dividendo"
        return this?.events?.dividends.orEmpty().values.mapNotNull { d ->
            val dateSec = d.date ?: return@mapNotNull null
            val amount = d.amount ?: return@mapNotNull null
            val millis = dateSec * 1000
            DividendEntry(
                exDate = millis,
                paymentDate = millis,
                valuePerShare = amount,
                type = typeLabel
            )
        }.sortedByDescending { it.exDate }
    }

    private fun computeDividendYield(dividends: List<DividendEntry>, price: Double?): Double? {
        if (price == null || price <= 0) return null
        val oneYearAgo = System.currentTimeMillis() - 365L * 24 * 60 * 60 * 1000
        val last12m = dividends.filter { it.exDate >= oneYearAgo }.sumOf { it.valuePerShare }
        if (last12m <= 0) return null
        return (last12m / price) * 100.0
    }

    private fun QuoteResultDto.toAssetQuote(): AssetQuote {
        val name = longName ?: shortName
        return AssetQuote(
            ticker = normalizeB3Ticker(symbol),
            name = name,
            assetType = detectAssetType(type, symbol, name),
            sector = sector,
            currentPrice = regularMarketPrice,
            dayChange = regularMarketChange,
            dayChangePercent = regularMarketChangePercent,
            dayHigh = regularMarketDayHigh,
            dayLow = regularMarketDayLow,
            volume = regularMarketVolume,
            marketCap = marketCap,
            lastUpdated = parseIsoToMillis(regularMarketTime),
            logoUrl = logoUrl
        )
    }

    private fun ListedAssetDto.toAssetQuote(): AssetQuote {
        val apiType = if (subType.equals("fii", ignoreCase = true)) "FII" else type
        return AssetQuote(
            ticker = normalizeB3Ticker(stock),
            name = name,
            assetType = detectAssetType(apiType, stock, name),
            sector = sector,
            currentPrice = close,
            dayChange = null,
            dayChangePercent = change,
            dayHigh = null,
            dayLow = null,
            volume = null,
            marketCap = null,
            lastUpdated = null,
            logoUrl = logo
        )
    }

    private fun YahooSearchQuote.isBrazilianListing(): Boolean {
        val symbol = this.symbol?.uppercase() ?: return false
        val exch = (exchDisp ?: exchange ?: "").uppercase()
        return symbol.endsWith(".SA") || exch.contains("SAO") || exch.contains("PAULO")
    }

    private fun YahooSearchQuote.toAssetQuote(): AssetQuote? {
        val symbol = symbol ?: return null
        val ticker = normalizeB3Ticker(symbol)
        if (ticker.isBlank()) return null
        val name = longname ?: shortname
        return AssetQuote(
            ticker = ticker,
            name = name,
            assetType = detectAssetType(quoteType, ticker, name),
            sector = null,
            currentPrice = null,
            dayChange = null,
            dayChangePercent = null,
            dayHigh = null,
            dayLow = null,
            volume = null,
            marketCap = null,
            lastUpdated = null
        )
    }

    private fun parseIsoToMillis(raw: String?): Long? {
        if (raw.isNullOrBlank()) return null
        return try {
            java.time.Instant.parse(raw).toEpochMilli()
        } catch (e: Exception) {
            try {
                java.time.LocalDate.parse(raw.take(10))
                    .atStartOfDay(java.time.ZoneOffset.UTC).toInstant().toEpochMilli()
            } catch (e2: Exception) {
                null
            }
        }
    }

    private fun mapBalanceSheet(resp: BrapiBalanceSheetResponse?): List<BalanceSheetEntry> {
        val item = resp?.results?.firstOrNull() ?: return emptyList()
        val entries = item.balanceSheetHistory.orEmpty().ifEmpty {
            item.balanceSheetHistoryQuarterly.orEmpty()
        }
        return entries.mapNotNull { e ->
            val label = e.period ?: e.endDate?.let { epochToYearLabel(it) } ?: return@mapNotNull null
            val totalDebt = listOfNotNull(e.shortLongTermDebt, e.longTermDebt).sum().takeIf { it > 0 }
            BalanceSheetEntry(
                periodLabel = label,
                totalAssets = e.totalAssets,
                totalLiabilities = e.totalLiab,
                netWorth = e.totalStockholderEquity,
                cash = e.cash,
                totalDebt = totalDebt,
                netDebt = e.netDebt,
                receivables = e.netReceivables,
                fixedAssets = e.propertyPlantEquipment
            )
        }
    }

    private fun mapIncomeStatement(resp: BrapiIncomeStatementResponse?): List<ResultsEntry> {
        val item = resp?.results?.firstOrNull() ?: return emptyList()
        val entries = item.incomeStatementHistory.orEmpty().ifEmpty {
            item.incomeStatementHistoryQuarterly.orEmpty()
        }
        return entries.mapNotNull { e ->
            val label = e.period ?: e.endDate?.let { epochToYearLabel(it) } ?: return@mapNotNull null
            val revenue = e.totalRevenue
            val netIncome = e.netIncome
            val netMargin = if (revenue != null && revenue != 0.0 && netIncome != null) {
                (netIncome / revenue) * 100.0
            } else null
            val ebitdaMargin = if (revenue != null && revenue != 0.0 && e.ebitda != null) {
                (e.ebitda / revenue) * 100.0
            } else null
            ResultsEntry(
                periodLabel = label,
                revenue = revenue,
                grossProfit = e.grossProfit,
                ebitda = e.ebitda,
                ebit = e.ebit,
                netIncome = netIncome,
                netMargin = netMargin,
                ebitdaMargin = ebitdaMargin
            )
        }
    }

    private fun epochToYearLabel(epochSec: Long): String {
        return try {
            val year = java.time.Instant.ofEpochSecond(epochSec)
                .atZone(java.time.ZoneOffset.UTC)
                .year
            year.toString()
        } catch (e: Exception) {
            epochSec.toString()
        }
    }

}
