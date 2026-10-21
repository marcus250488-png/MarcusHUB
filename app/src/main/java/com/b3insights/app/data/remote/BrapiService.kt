package com.b3insights.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * brapi.dev — busca de tickers da B3, cotação, perfil, balanço e indicadores de FII.
 *
 * IMPORTANTE: o plano Gratuito NÃO inclui dividendos. Pedir dividends=true
 * devolve HTTP 403 e derruba a cotação inteira. Dividendos vêm do Yahoo.
 *
 * Endpoints de balanço / DRE / FII indicators exigem plano pago na maioria dos
 * casos; o código trata falha como lista vazia (fallback silencioso).
 */
interface BrapiService {

    @GET("api/quote/{tickers}")
    suspend fun getQuote(
        @Path("tickers") tickers: String,
        @Query("range") range: String = "3mo",
        @Query("interval") interval: String = "1d",
        @Query("fundamental") fundamental: Boolean = true,
        @Query("modules") modules: String? = null
    ): QuoteResponse

    @GET("api/quote/list")
    suspend fun searchAssets(
        @Query("search") search: String,
        @Query("limit") limit: Int = 15
    ): AssetListResponse

    /** Lista ampla de ações da B3 (sem filtro de busca). */
    @GET("api/quote/list")
    suspend fun listStocks(
        @Query("type") type: String = "stock",
        @Query("limit") limit: Int = 500
    ): AssetListResponse

    @GET("api/v2/stocks/profile")
    suspend fun getProfile(
        @Query("symbols") symbols: String
    ): BrapiProfileResponse

    /** Balanço patrimonial (anual). Plano pago; falha silenciosamente no free. */
    @GET("api/v2/stocks/balance-sheet")
    suspend fun getBalanceSheet(
        @Query("symbols") symbols: String
    ): BrapiBalanceSheetResponse

    /** DRE / resultados (anual). Plano pago; falha silenciosamente no free. */
    @GET("api/v2/stocks/income-statement")
    suspend fun getIncomeStatement(
        @Query("symbols") symbols: String
    ): BrapiIncomeStatementResponse

    /** Indicadores de FII (P/VP, DY, patrimônio…). Pro ou sandbox MXRF11/HGLG11. */
    @GET("api/v2/fii/indicators")
    suspend fun getFiiIndicators(
        @Query("symbols") symbols: String
    ): BrapiFiiIndicatorsResponse
}
