package com.b3insights.app.ui.screens.ranking

import androidx.compose.foundation.clickable
import com.b3insights.app.util.Valuation
import com.b3insights.app.data.model.NewsItem
import android.net.Uri
import android.content.Intent
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.components.ProUpgradeDialog
import com.b3insights.app.ui.components.requireProAccess
import com.b3insights.app.billing.ProManager
import androidx.compose.runtime.collectAsState
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asCompactMoney
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asDecimal
import com.b3insights.app.util.asMoneyForTicker
import com.b3insights.app.util.asPercent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class RankingListUiState(
    val isLoading: Boolean = true,
    val rows: List<RankingRow> = emptyList(),
    val error: String? = null
)

class RankingListViewModel : ViewModel() {
    private val repository = B3InsightsApp.instance.marketDataRepository
    private val _ui = MutableStateFlow(RankingListUiState())
    val ui: StateFlow<RankingListUiState> = _ui.asStateFlow()

    fun load(categoryKey: String, metricKey: String) {
        viewModelScope.launch {
            _ui.value = RankingListUiState(isLoading = true)
            val cat = RankingCategory.entries.find { it.key == categoryKey } ?: RankingCategory.ACOES
            val metric = RankingMetric.entries.find { it.name == metricKey } ?: RankingMetric.MAIOR_CAPITALIZACAO
            // Só tickers da categoria (evita misturar BTC/FIIs no ranking de ETFs, etc.)
            val tickers = RankingUniverse.tickers(cat)

            val needsFundamentals = metric in setOf(
                RankingMetric.DIVIDEND_YIELD, RankingMetric.MENOR_PL, RankingMetric.MENOR_PVP,
                RankingMetric.MAIOR_ROE, RankingMetric.MAIOR_MARGEM, RankingMetric.MAIORES_RECEITAS,
                RankingMetric.MAIORES_LUCROS, RankingMetric.MAIS_BARATAS_GRAHAM, RankingMetric.PRECO_TETO_BAZIN
            )
            val universe = if (needsFundamentals) tickers.take(16) else tickers
            val rows = kotlinx.coroutines.coroutineScope {
                universe.map { ticker ->
                    async {
                        val quote = when (val r = repository.getQuote(ticker)) {
                            is DataResult.Success -> r.data
                            else -> return@async null
                        }
                        var pl: Double? = null
                        var pvp: Double? = null
                        var dy: Double? = null
                        var roe: Double? = null
                        var netMargin: Double? = null
                        var revenue: Double? = null
                        var netIncome: Double? = null
                        var lpa: Double? = null
                        var vpa: Double? = null
                        var dps: Double? = null
                        if (needsFundamentals) {
                            runCatching {
                                when (val d = repository.getAssetDetail(ticker, com.b3insights.app.data.model.ChartPeriod.Y1)) {
                                    is DataResult.Success -> {
                                        pl = d.data.indicators.pl
                                        pvp = d.data.indicators.pvp
                                        dy = d.data.indicators.dividendYield
                                        roe = d.data.indicators.roe
                                        netMargin = d.data.indicators.netMargin
                                        revenue = d.data.indicators.revenue
                                        netIncome = d.data.indicators.netIncome
                                        lpa = d.data.indicators.lpa
                                        vpa = d.data.indicators.vpa
                                        dps = d.data.indicators.dividendPerShare
                                    }
                                    else -> Unit
                                }
                            }
                        }
                        val graham = Valuation.grahamPrice(lpa, vpa, quote.currentPrice, pl, pvp)
                        val bazinBd = Valuation.bazinBreakdown(
                            price = quote.currentPrice,
                            dividendYieldPercent = dy,
                            annualDividendsPerShare = dps
                        )
                        val bazin = bazinBd?.ceilingPrice
                        val mGraham = Valuation.safetyMargin(graham, quote.currentPrice)
                        val mBazin = Valuation.safetyMargin(bazin, quote.currentPrice)
                        val metricValue = when (metric) {
                            RankingMetric.MAIOR_CAPITALIZACAO -> quote.marketCap
                            RankingMetric.MAIORES_ALTAS_DIA -> quote.dayChangePercent
                            RankingMetric.MAIORES_BAIXAS_DIA -> quote.dayChangePercent
                            RankingMetric.DIVIDEND_YIELD -> dy
                            RankingMetric.MENOR_PL -> pl
                            RankingMetric.MENOR_PVP -> pvp
                            RankingMetric.MAIOR_ROE -> roe
                            RankingMetric.MAIOR_MARGEM -> netMargin
                            RankingMetric.MAIORES_RECEITAS -> revenue
                            RankingMetric.MAIORES_LUCROS -> netIncome
                            RankingMetric.MAIS_BARATAS_GRAHAM -> mGraham
                            RankingMetric.PRECO_TETO_BAZIN -> mBazin
                            RankingMetric.VOLUME -> quote.volume?.toDouble()
                            RankingMetric.AS_MAIS_QUERIDAS -> quote.volume?.toDouble()
                        }
                        RankingRow(
                            ticker = quote.ticker,
                            name = quote.name,
                            price = quote.currentPrice,
                            changePercent = quote.dayChangePercent,
                            marketCap = quote.marketCap,
                            volume = quote.volume,
                            pl = pl,
                            pvp = pvp,
                            dy = dy,
                            roe = roe,
                            netMargin = netMargin,
                            revenue = revenue,
                            netIncome = netIncome,
                            lpa = lpa,
                            vpa = vpa,
                            grahamPrice = graham,
                            bazinPrice = bazin,
                            marginGraham = mGraham,
                            marginBazin = mBazin,
                            bazinDps = bazinBd?.dividendsPerShare,
                            bazinPeriod = bazinBd?.dividendPeriod,
                            metricValue = metricValue,
                            metricLabel = metric.title,
                            logoUrl = quote.logoUrl
                        )
                    }
                }.mapNotNull { it.await() }
            }

            val cryptoOrder = listOf(
                "BTC-USD", "ETH-USD", "BNB-USD", "SOL-USD", "XRP-USD", "ADA-USD",
                "DOGE-USD", "AVAX-USD", "DOT-USD", "LINK-USD", "LTC-USD", "ATOM-USD"
            )
            val sorted = when (metric) {
                RankingMetric.MENOR_PL, RankingMetric.MENOR_PVP, RankingMetric.MAIORES_BAIXAS_DIA ->
                    rows.sortedBy { it.metricValue ?: Double.POSITIVE_INFINITY }
                RankingMetric.MAIS_BARATAS_GRAHAM, RankingMetric.PRECO_TETO_BAZIN ->
                    rows.sortedByDescending { it.metricValue ?: Double.NEGATIVE_INFINITY }
                RankingMetric.MAIOR_CAPITALIZACAO -> {
                    if (rows.all { it.marketCap == null }) {
                        // Fallback: ordem de relevância de mercado (cripto/ações sem marketCap na API)
                        rows.sortedBy { row -> cryptoOrder.indexOf(row.ticker).let { i -> if (i < 0) 999 else i } }
                    } else {
                        rows.sortedByDescending { it.marketCap ?: Double.NEGATIVE_INFINITY }
                    }
                }
                else ->
                    rows.sortedByDescending { it.metricValue ?: Double.NEGATIVE_INFINITY }
            }

            _ui.value = RankingListUiState(isLoading = false, rows = sorted)
        }
    }
}

@Composable
fun RankingHubScreen(
    onCategoryClick: (RankingCategory) -> Unit,
    onDividendAgendaClick: () -> Unit = {},
    onNewsClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var news by remember { mutableStateOf<List<NewsItem>>(emptyList()) }
    var newsLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        newsLoading = true
        news = withContext(kotlinx.coroutines.Dispatchers.IO) { loadHubNews() }
        newsLoading = false
    }

    PaperBackground {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.EmojiEvents, contentDescription = null, tint = BluePrimary)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Rankings",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Spacer(Modifier.height(6.dp))
                Text(
                    "Listas ordenadas por indicadores de mercado",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                SoftCard {
                    RankingCategory.entries.forEachIndexed { index, cat ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCategoryClick(cat) }
                                .padding(horizontal = 4.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                cat.title.take(1),
                                modifier = Modifier.width(28.dp),
                                fontWeight = FontWeight.Bold,
                                color = BluePrimary,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                cat.title,
                                modifier = Modifier.weight(1f),
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Icon(
                                Icons.Filled.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                SoftCard(onClick = onDividendAgendaClick) {
                    Text("Agenda de Dividendos", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Datas com e pagamento de ações, FIIs e ETFs",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notícias de hoje", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    if (!newsLoading && news.isNotEmpty()) {
                        TextButton(onClick = onNewsClick) {
                            Text("Ver todas", color = BluePrimary)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            if (newsLoading) {
                item {
                    Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BluePrimary, strokeWidth = 2.dp, modifier = Modifier.size(28.dp))
                    }
                }
            } else if (news.isEmpty()) {
                item {
                    Text(
                        "Não foi possível carregar notícias agora.",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                items(news.take(8)) { n ->
                    SoftCard(
                        modifier = Modifier.padding(bottom = 10.dp),
                        onClick = {
                            runCatching {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(n.url)))
                            }
                        }
                    ) {
                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            if (!n.imageUrl.isNullOrBlank()) {
                                AsyncImage(
                                    model = n.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(72.dp)
                                        .clip(androidx.compose.foundation.shape.RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(12.dp))
                            }
                            Column(Modifier.weight(1f)) {
                                Surface(
                                    color = BlueLight,
                                    shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        n.source.uppercase().take(18),
                                        Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                        color = NavyDark,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    n.title,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextPrimary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 3
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

private fun loadHubNews(): List<NewsItem> =
    com.b3insights.app.data.remote.NewsFetcher.fetchMarketNews(limit = 12)


@Composable
fun RankingMetricsScreen(
    categoryKey: String,
    onMetricClick: (RankingMetric) -> Unit,
    onBack: () -> Unit
) {
    val cat = RankingCategory.entries.find { it.key == categoryKey } ?: RankingCategory.ACOES
    val metrics = RankingMetric.entries.filter { cat in it.categories }
    val proState by ProManager.state.collectAsState()
    val isPro = true
    var showProDialog by remember { mutableStateOf(false) }

    if (showProDialog) {
        ProUpgradeDialog(onDismiss = { showProDialog = false })
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                }
                Text(cat.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
            Text(
                "Escolha um ranking",
                Modifier.padding(horizontal = 20.dp),
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(metrics) { m ->
                    val locked = m.requiresProFor(cat) && !isPro
                    SoftCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 108.dp),
                        onClick = {
                            if (locked) {
                                showProDialog = true
                            } else {
                                onMetricClick(m)
                            }
                        }
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                m.title,
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                m.description,
                                color = TextSecondary,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            if (locked) {
                                Spacer(Modifier.height(6.dp))
                                Text("🔒", fontSize = 20.sp, style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RankingListScreen(
    categoryKey: String,
    metricKey: String,
    onAssetClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: RankingListViewModel = viewModel()
) {
    val ui by viewModel.ui.collectAsState()
    val metric = RankingMetric.entries.find { it.name == metricKey }
    val title = metric?.title ?: "Ranking"

    LaunchedEffect(categoryKey, metricKey) {
        viewModel.load(categoryKey, metricKey)
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                }
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = TextPrimary)
            }

            when {
                ui.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = BluePrimary)
                        Spacer(Modifier.height(10.dp))
                        Text("Montando ranking…", color = TextSecondary)
                    }
                }
                ui.rows.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Sem dados no momento. Tente de novo.", color = TextSecondary)
                }
                else -> {
                    // Cabeçalho
                    val isValuationHeader = metric == RankingMetric.MAIS_BARATAS_GRAHAM ||
                        metric == RankingMetric.PRECO_TETO_BAZIN
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text("Ativo", Modifier.weight(1.4f), color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                        Text("Preço", Modifier.weight(0.9f), color = TextSecondary, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.End)
                        if (!isValuationHeader) {
                            Text("Indicador", Modifier.weight(1f), color = TextSecondary, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.End)
                        }
                    }
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(ui.rows) { row ->
                            val isValuation = metric == RankingMetric.MAIS_BARATAS_GRAHAM ||
                                metric == RankingMetric.PRECO_TETO_BAZIN
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { onAssetClick(row.ticker) }
                                    .padding(horizontal = 16.dp, vertical = 12.dp)
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        Modifier.weight(if (isValuation) 1.4f else 1.2f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        com.b3insights.app.ui.components.AssetLogo(
                                            ticker = row.ticker,
                                            logoUrl = row.logoUrl,
                                            size = 36.dp,
                                            corner = 8.dp
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Column {
                                            Text(row.ticker, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                            Text(
                                                row.name ?: "",
                                                color = TextSecondary,
                                                style = MaterialTheme.typography.bodySmall,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                    Text(
                                        row.price.asMoneyForTicker(row.ticker),
                                        Modifier.weight(if (isValuation) 0.9f else 1f),
                                        color = TextPrimary,
                                        textAlign = TextAlign.End,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    if (!isValuation) {
                                        Text(
                                            formatMetric(metric, row),
                                            Modifier.weight(1f),
                                            color = TextPrimary,
                                            textAlign = TextAlign.End,
                                            fontWeight = FontWeight.Medium,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                if (isValuation) {
                                    Spacer(Modifier.height(6.dp))
                                    val g = row.grahamPrice?.let { "Graham R$ %.2f".format(it) } ?: "Graham —"
                                    val b = row.bazinPrice?.let { "Bazin R$ %.2f".format(it) } ?: "Bazin —"
                                    val mg = row.marginGraham?.let { " (%.1f%%)".format(it) } ?: ""
                                    val mb = row.marginBazin?.let { " (%.1f%%)".format(it) } ?: ""
                                    Text(
                                        "Cotação atual: ${row.price.asMoneyForTicker(row.ticker)}",
                                        color = TextSecondary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        "$g$mg",
                                        color = TextSecondary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        "$b$mb",
                                        color = TextSecondary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    // Detalhe do cálculo Bazin
                                    if (row.bazinPrice != null) {
                                        val dpsTxt = row.bazinDps?.let { "DPS 12m R$ %.2f".format(it) } ?: "DPS 12m"
                                        val periodTxt = row.bazinPeriod ?: "Proventos dos últimos 12 meses (LTM)"
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            "Fórmula Bazin: DPS ÷ 0,06 (alvo 6%)  ·  $dpsTxt",
                                            modifier = Modifier.fillMaxWidth(),
                                            color = TextSecondary.copy(alpha = 0.80f),
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            maxLines = 2
                                        )
                                        Text(
                                            periodTxt,
                                            modifier = Modifier.fillMaxWidth(),
                                            color = TextSecondary.copy(alpha = 0.70f),
                                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                            maxLines = 2
                                        )
                                    }
                                }
                            }
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

private fun formatMetric(metric: RankingMetric?, row: RankingRow): String {
    if (metric == null) return row.metricValue?.toString() ?: "—"
    return when (metric) {
        RankingMetric.MAIOR_CAPITALIZACAO -> row.marketCap?.asCompactMoney()
            ?: row.volume?.let { v -> if (v >= 1_000_000) "Vol. %.1f M".format(v / 1_000_000.0) else "Vol. $v" }
            ?: row.changePercent.asPercent()
        RankingMetric.MAIORES_ALTAS_DIA, RankingMetric.MAIORES_BAIXAS_DIA -> row.changePercent.asPercent()
        RankingMetric.DIVIDEND_YIELD -> row.dy.asPercent(showSign = false)
        RankingMetric.MENOR_PL -> row.pl.asDecimal()
        RankingMetric.MENOR_PVP -> row.pvp.asDecimal()
        RankingMetric.MAIOR_ROE -> row.roe.asPercent(showSign = false)
        RankingMetric.MAIOR_MARGEM -> row.netMargin.asPercent(showSign = false)
        RankingMetric.MAIORES_RECEITAS -> row.revenue.asCompactMoney()
        RankingMetric.MAIORES_LUCROS -> row.netIncome.asCompactMoney()
        RankingMetric.MAIS_BARATAS_GRAHAM -> {
            val g = row.grahamPrice?.let { "Graham R$ %.2f".format(it) } ?: "Graham —"
            val m = row.marginGraham?.let { " · margem %.1f%%".format(it) } ?: ""
            g + m
        }
        RankingMetric.PRECO_TETO_BAZIN -> {
            val b = row.bazinPrice?.let { "Bazin R$ %.2f".format(it) } ?: "Bazin —"
            val m = row.marginBazin?.let { " · margem %.1f%%".format(it) } ?: ""
            b + m
        }
        RankingMetric.VOLUME, RankingMetric.AS_MAIS_QUERIDAS ->
            row.volume?.let { if (it >= 1_000_000) String.format("%.1f M", it / 1_000_000.0) else it.toString() } ?: "—"
    }
}
