package com.b3insights.app.data.remote

import com.google.gson.annotations.SerializedName

data class YahooChartResponse(
    @SerializedName("chart") val chart: YahooChartEnvelope? = null
)

data class YahooChartEnvelope(
    @SerializedName("result") val result: List<YahooChartResult>? = null,
    @SerializedName("error") val error: YahooApiError? = null
)

data class YahooApiError(
    @SerializedName("code") val code: String? = null,
    @SerializedName("description") val description: String? = null
)

data class YahooChartResult(
    @SerializedName("meta") val meta: YahooChartMeta? = null,
    @SerializedName("timestamp") val timestamp: List<Long>? = null,
    @SerializedName("events") val events: YahooEvents? = null,
    @SerializedName("indicators") val indicators: YahooIndicators? = null
)

data class YahooChartMeta(
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("exchangeName") val exchangeName: String? = null,
    @SerializedName("instrumentType") val instrumentType: String? = null,
    @SerializedName("regularMarketPrice") val regularMarketPrice: Double? = null,
    @SerializedName("regularMarketChangePercent") val regularMarketChangePercent: Double? = null,
    @SerializedName("chartPreviousClose") val chartPreviousClose: Double? = null,
    @SerializedName("previousClose") val previousClose: Double? = null,
    @SerializedName("shortName") val shortName: String? = null,
    @SerializedName("longName") val longName: String? = null,
    @SerializedName("regularMarketTime") val regularMarketTime: Long? = null,
    @SerializedName("regularMarketDayHigh") val regularMarketDayHigh: Double? = null,
    @SerializedName("regularMarketDayLow") val regularMarketDayLow: Double? = null,
    @SerializedName("regularMarketVolume") val regularMarketVolume: Long? = null
)

data class YahooEvents(
    @SerializedName("dividends") val dividends: Map<String, YahooDividendDto>? = null
)

data class YahooDividendDto(
    @SerializedName("amount") val amount: Double? = null,
    @SerializedName("date") val date: Long? = null
)

data class YahooIndicators(
    @SerializedName("quote") val quote: List<YahooQuoteSeries>? = null
)

data class YahooQuoteSeries(
    @SerializedName("close") val close: List<Double?>? = null,
    @SerializedName("high") val high: List<Double?>? = null,
    @SerializedName("low") val low: List<Double?>? = null,
    @SerializedName("volume") val volume: List<Long?>? = null
)

data class YahooSearchResponse(
    @SerializedName("quotes") val quotes: List<YahooSearchQuote>? = null
)

data class YahooSearchQuote(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("shortname") val shortname: String? = null,
    @SerializedName("longname") val longname: String? = null,
    @SerializedName("quoteType") val quoteType: String? = null,
    @SerializedName("exchange") val exchange: String? = null,
    @SerializedName("exchDisp") val exchDisp: String? = null
)


data class YahooQuoteSummaryResponse(
    @SerializedName("quoteSummary") val quoteSummary: YahooQuoteSummaryBody? = null
)
data class YahooQuoteSummaryBody(
    @SerializedName("result") val result: List<YahooQuoteSummaryResult>? = null
)
data class YahooQuoteSummaryResult(
    @SerializedName("defaultKeyStatistics") val defaultKeyStatistics: YahooKs? = null,
    @SerializedName("financialData") val financialData: YahooFd? = null,
    @SerializedName("summaryDetail") val summaryDetail: YahooSummaryDetail? = null,
    @SerializedName("balanceSheetHistory") val balanceSheetHistory: YahooBalanceHist? = null,
    @SerializedName("incomeStatementHistory") val incomeStatementHistory: YahooIncomeHist? = null
)
data class YahooRawNumber(
    @SerializedName("raw") val raw: Double? = null,
    @SerializedName("fmt") val fmt: String? = null
)
data class YahooKs(
    @SerializedName("trailingPE") val trailingPE: YahooRawNumber? = null,
    @SerializedName("priceToBook") val priceToBook: YahooRawNumber? = null,
    @SerializedName("bookValue") val bookValue: YahooRawNumber? = null,
    @SerializedName("profitMargins") val profitMargins: YahooRawNumber? = null,
    @SerializedName("enterpriseValue") val enterpriseValue: YahooRawNumber? = null
)
data class YahooFd(
    @SerializedName("returnOnEquity") val returnOnEquity: YahooRawNumber? = null,
    @SerializedName("profitMargins") val profitMargins: YahooRawNumber? = null,
    @SerializedName("totalRevenue") val totalRevenue: YahooRawNumber? = null,
    @SerializedName("grossMargins") val grossMargins: YahooRawNumber? = null
)
data class YahooSummaryDetail(
    @SerializedName("dividendYield") val dividendYield: YahooRawNumber? = null,
    @SerializedName("trailingPE") val trailingPE: YahooRawNumber? = null,
    @SerializedName("priceToBook") val priceToBook: YahooRawNumber? = null,
    @SerializedName("marketCap") val marketCap: YahooRawNumber? = null
)
data class YahooBalanceHist(
    @SerializedName("balanceSheetStatements") val balanceSheetStatements: List<YahooBalanceStmt>? = null
)
data class YahooBalanceStmt(
    @SerializedName("endDate") val endDate: YahooRawNumber? = null,
    @SerializedName("totalAssets") val totalAssets: YahooRawNumber? = null,
    @SerializedName("totalLiab") val totalLiab: YahooRawNumber? = null,
    @SerializedName("totalStockholderEquity") val totalStockholderEquity: YahooRawNumber? = null
)
data class YahooIncomeHist(
    @SerializedName("incomeStatementHistory") val incomeStatementHistory: List<YahooIncomeStmt>? = null
)
data class YahooIncomeStmt(
    @SerializedName("endDate") val endDate: YahooRawNumber? = null,
    @SerializedName("totalRevenue") val totalRevenue: YahooRawNumber? = null,
    @SerializedName("grossProfit") val grossProfit: YahooRawNumber? = null,
    @SerializedName("netIncome") val netIncome: YahooRawNumber? = null,
    @SerializedName("operatingIncome") val operatingIncome: YahooRawNumber? = null
)
