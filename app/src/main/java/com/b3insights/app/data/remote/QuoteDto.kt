package com.b3insights.app.data.remote

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("results") val results: List<QuoteResultDto>? = null
)

data class QuoteResultDto(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("shortName") val shortName: String? = null,
    @SerializedName("longName") val longName: String? = null,
    @SerializedName("currency") val currency: String? = null,
    @SerializedName("regularMarketPrice") val regularMarketPrice: Double? = null,
    @SerializedName("regularMarketChange") val regularMarketChange: Double? = null,
    @SerializedName("regularMarketChangePercent") val regularMarketChangePercent: Double? = null,
    @SerializedName("regularMarketDayHigh") val regularMarketDayHigh: Double? = null,
    @SerializedName("regularMarketDayLow") val regularMarketDayLow: Double? = null,
    @SerializedName("regularMarketVolume") val regularMarketVolume: Long? = null,
    @SerializedName("regularMarketTime") val regularMarketTime: String? = null,
    @SerializedName("marketCap") val marketCap: Double? = null,
    @SerializedName("logourl") val logoUrl: String? = null,
    @SerializedName("priceEarnings") val priceEarnings: Double? = null,
    @SerializedName("earningsPerShare") val earningsPerShare: Double? = null,
    @SerializedName("sector") val sector: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("longSummary") val longSummary: String? = null,
    @SerializedName("historicalDataPrice") val historicalDataPrice: List<HistoricalPriceDto>? = null,
    @SerializedName("defaultKeyStatistics") val defaultKeyStatistics: BrapiKeyStatisticsDto? = null,
    @SerializedName("financialData") val financialData: BrapiFinancialDataDto? = null
)

data class BrapiKeyStatisticsDto(
    @SerializedName("trailingPE") val trailingPE: Double? = null,
    @SerializedName("forwardPE") val forwardPE: Double? = null,
    @SerializedName("priceToBook") val priceToBook: Double? = null,
    @SerializedName("bookValue") val bookValue: Double? = null,
    @SerializedName("profitMargins") val profitMargins: Double? = null,
    @SerializedName("enterpriseToEbitda") val enterpriseToEbitda: Double? = null,
    @SerializedName("pegRatio") val pegRatio: Double? = null,
    @SerializedName("marketCap") val marketCap: Double? = null,
    @SerializedName("floatShares") val floatShares: Double? = null,
    @SerializedName("dividendYield") val dividendYield: Double? = null,
    @SerializedName("yield") val yieldValue: Double? = null
)

data class BrapiFinancialDataDto(
    @SerializedName("returnOnEquity") val returnOnEquity: Double? = null,
    @SerializedName("returnOnAssets") val returnOnAssets: Double? = null,
    @SerializedName("profitMargins") val profitMargins: Double? = null,
    @SerializedName("operatingMargins") val operatingMargins: Double? = null,
    @SerializedName("grossMargins") val grossMargins: Double? = null,
    @SerializedName("revenueGrowth") val revenueGrowth: Double? = null,
    @SerializedName("earningsGrowth") val earningsGrowth: Double? = null,
    @SerializedName("totalRevenue") val totalRevenue: Double? = null,
    @SerializedName("debtToEquity") val debtToEquity: Double? = null,
    @SerializedName("currentRatio") val currentRatio: Double? = null,
    @SerializedName("freeCashflow") val freeCashflow: Double? = null
)

data class HistoricalPriceDto(
    @SerializedName("date") val date: Long? = null,
    @SerializedName("close") val close: Double? = null
)

data class ListedAssetDto(
    @SerializedName("stock") val stock: String,
    @SerializedName("name") val name: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("subType") val subType: String? = null,
    @SerializedName("close") val close: Double? = null,
    @SerializedName("change") val change: Double? = null,
    @SerializedName("sector") val sector: String? = null,
    @SerializedName("logo") val logo: String? = null
)

data class AssetListResponse(
    @SerializedName("stocks") val stocks: List<ListedAssetDto>? = null
)

data class BrapiProfileResponse(
    @SerializedName("results") val results: List<BrapiProfileItem>? = null
)

data class BrapiProfileItem(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("data") val data: BrapiProfileData? = null
)

data class BrapiProfileData(
    @SerializedName("sector") val sector: String? = null,
    @SerializedName("industry") val industry: String? = null,
    @SerializedName("longBusinessSummary") val longBusinessSummary: String? = null,
    @SerializedName("website") val website: String? = null
)

// ---------- Balanço patrimonial ----------

data class BrapiBalanceSheetResponse(
    @SerializedName("results") val results: List<BrapiBalanceSheetItem>? = null
)

data class BrapiBalanceSheetItem(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("balanceSheetHistory") val balanceSheetHistory: List<BrapiBalanceSheetEntryDto>? = null,
    @SerializedName("balanceSheetHistoryQuarterly") val balanceSheetHistoryQuarterly: List<BrapiBalanceSheetEntryDto>? = null
)

data class BrapiBalanceSheetEntryDto(
    @SerializedName("endDate") val endDate: Long? = null,
    @SerializedName("period") val period: String? = null,
    @SerializedName("totalAssets") val totalAssets: Double? = null,
    @SerializedName("totalLiab") val totalLiab: Double? = null,
    @SerializedName("totalStockholderEquity") val totalStockholderEquity: Double? = null,
    @SerializedName("cash") val cash: Double? = null,
    @SerializedName("shortLongTermDebt") val shortLongTermDebt: Double? = null,
    @SerializedName("longTermDebt") val longTermDebt: Double? = null,
    @SerializedName("netDebt") val netDebt: Double? = null,
    @SerializedName("netReceivables") val netReceivables: Double? = null,
    @SerializedName("propertyPlantEquipment") val propertyPlantEquipment: Double? = null
)

// ---------- DRE / Resultados ----------

data class BrapiIncomeStatementResponse(
    @SerializedName("results") val results: List<BrapiIncomeStatementItem>? = null
)

data class BrapiIncomeStatementItem(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("incomeStatementHistory") val incomeStatementHistory: List<BrapiIncomeEntryDto>? = null,
    @SerializedName("incomeStatementHistoryQuarterly") val incomeStatementHistoryQuarterly: List<BrapiIncomeEntryDto>? = null
)

data class BrapiIncomeEntryDto(
    @SerializedName("endDate") val endDate: Long? = null,
    @SerializedName("period") val period: String? = null,
    @SerializedName("totalRevenue") val totalRevenue: Double? = null,
    @SerializedName("grossProfit") val grossProfit: Double? = null,
    @SerializedName("ebitda") val ebitda: Double? = null,
    @SerializedName("ebit") val ebit: Double? = null,
    @SerializedName("netIncome") val netIncome: Double? = null
)

// ---------- Indicadores de FII ----------

data class BrapiFiiIndicatorsResponse(
    @SerializedName("fiis") val fiis: List<BrapiFiiIndicatorDto>? = null
)

data class BrapiFiiIndicatorDto(
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("navPerShare") val navPerShare: Double? = null,
    @SerializedName("priceToNav") val priceToNav: Double? = null,
    @SerializedName("dividendYield12m") val dividendYield12m: Double? = null,
    @SerializedName("dividendYield1m") val dividendYield1m: Double? = null,
    @SerializedName("equity") val equity: Double? = null,
    @SerializedName("totalAssets") val totalAssets: Double? = null,
    @SerializedName("totalInvestors") val totalInvestors: Int? = null,
    @SerializedName("sharesOutstanding") val sharesOutstanding: Long? = null
)
