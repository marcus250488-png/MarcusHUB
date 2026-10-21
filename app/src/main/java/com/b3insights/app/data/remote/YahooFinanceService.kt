package com.b3insights.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Yahoo Finance (não oficial). Usado como fonte padrão de cotação, gráfico e dividendos,
 * porque o plano gratuito da brapi não inclui proventos.
 *
 * Qualquer ticker da B3 usa o sufixo .SA (PETR4.SA, HGLG11.SA).
 */
interface YahooFinanceService {

    @GET("v8/finance/chart/{symbol}")
    suspend fun getChart(
        @Path("symbol") symbol: String,
        @Query("range") range: String,
        @Query("interval") interval: String,
        @Query("events") events: String = "div,splits",
        @Query("includePrePost") includePrePost: Boolean = false
    ): YahooChartResponse

    @GET("v1/finance/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("quotesCount") quotesCount: Int = 15,
        @Query("newsCount") newsCount: Int = 0,
        @Query("enableFuzzyQuery") enableFuzzyQuery: Boolean = true
    ): YahooSearchResponse


    /** Fundamentals / balanço (não oficial). */
    @GET("v10/finance/quoteSummary/{symbol}")
    suspend fun getQuoteSummary(
        @Path("symbol") symbol: String,
        @Query("modules") modules: String = "defaultKeyStatistics,financialData,price,summaryDetail,balanceSheetHistory,incomeStatementHistory"
    ): YahooQuoteSummaryResponse
}
