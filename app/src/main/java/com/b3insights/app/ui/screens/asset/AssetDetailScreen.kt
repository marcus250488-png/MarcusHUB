@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.asset

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.ui.components.ChangePill
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.IndicatorTile
import com.b3insights.app.ui.components.PriceChart
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asMoneyForTicker
import com.b3insights.app.util.isUsStockTicker
import com.b3insights.app.util.asDateTime
import com.b3insights.app.viewmodel.AssetDetailViewModel

private val stockTabs = listOf(
    "Visão Geral", "Valuation", "Dividendos", "Balanço", "Resultados", "Indicadores", "Notícias", "Relatórios", "Histórico"
)
private val cryptoTabs = listOf(
    "Visão Geral", "Mercado", "Indicadores", "Histórico"
)

@Composable
fun AssetDetailScreen(
    ticker: String,
    onBack: () -> Unit,
    onBuy: ((String, String) -> Unit)? = null,
    viewModel: AssetDetailViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(ticker) {
        selectedTab = 0
        viewModel.load(ticker)
    }

    PaperBackground {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
            }
            Text(
                text = uiState.ticker,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { viewModel.retry() }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Atualizar", tint = TextSecondary)
            }
            IconButton(onClick = { viewModel.toggleFavorite() }) {
                Icon(
                    imageVector = if (uiState.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Favorito",
                    tint = if (uiState.isFavorite) BluePrimary else TextSecondary
                )
            }
        }

        when {
            uiState.isLoading && uiState.detail == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }
            }
            uiState.errorMessage != null && uiState.detail == null -> {
                Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            mapError(uiState.errorMessage),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                        ) {
                            Text("Tentar novamente")
                        }
                    }
                }
            }
            uiState.detail != null -> {
                val detail = uiState.detail!!
                val tabs = if (detail.quote.assetType == com.b3insights.app.data.model.AssetType.CRIPTO) cryptoTabs else stockTabs
                val isCrypto = detail.quote.assetType == com.b3insights.app.data.model.AssetType.CRIPTO
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            com.b3insights.app.ui.components.AssetLogo(
                                ticker = detail.quote.ticker,
                                logoUrl = detail.quote.logoUrl,
                                size = 44.dp,
                                corner = 12.dp,
                                assetType = detail.quote.assetType.name
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                detail.quote.name ?: detail.quote.ticker,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            val showUsdBrl = isCrypto || isUsStockTicker(detail.quote.ticker)
                            if (showUsdBrl) {
                                var usdBrl by remember { mutableStateOf(5.0) }
                                LaunchedEffect(Unit) {
                                    val r = com.b3insights.app.B3InsightsApp.instance.marketDataRepository.getQuote("USDBRL=X")
                                    if (r is com.b3insights.app.data.model.DataResult.Success) {
                                        r.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
                                    }
                                }
                                val usd = detail.quote.currentPrice
                                Text(
                                    usd.asCurrency("USD"),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                if (usd != null && usd > 0) {
                                    Text(
                                        "  —  ",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        (usd * usdBrl).asCurrency("BRL"),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            } else {
                                Text(
                                    detail.quote.currentPrice.asMoneyForTicker(detail.quote.ticker),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ChangePill(detail.quote.dayChangePercent)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                detail.quote.lastUpdated.asDateTime(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(
                                onClick = {
                                    val t = detail.quote.ticker
                                    val type = detail.quote.assetType.name
                                    onBuy?.invoke(t, type)
                                },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("Adicionar à carteira", color = BluePrimary, fontWeight = FontWeight.SemiBold)
                            }
                        }
                        if (uiState.fromCache) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Sem conexão. Mostrando último dado disponível.", color = RedNegative, style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            IndicatorTile("Máxima do dia", detail.quote.dayHigh.asMoneyForTicker(detail.quote.ticker), modifier = Modifier.weight(1f))
                            Spacer(modifier = Modifier.width(8.dp))
                            IndicatorTile("Mínima do dia", detail.quote.dayLow.asMoneyForTicker(detail.quote.ticker), modifier = Modifier.weight(1f))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Seletor de período
                        LazyRow {
                            items(ChartPeriod.entries) { period ->
                                val selected = uiState.period == period
                                Box(
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(if (selected) BluePrimary else MaterialTheme.colorScheme.surface)
                                        .clickable { viewModel.onPeriodSelected(period) }
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        period.label,
                                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        PriceChart(points = detail.priceHistory)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Tabs
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = BluePrimary,
                        edgePadding = 20.dp
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = { Text(title, style = MaterialTheme.typography.bodyMedium) }
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(20.dp)) {
                        if (isCrypto) {
                            when (selectedTab) {
                                0 -> OverviewTab(detail)
                                1 -> CryptoMarketTab(detail)
                                2 -> IndicatorsTab(detail.indicators, detail.quote.assetType)
                                3 -> HistoryTab(detail.priceHistory)
                            }
                        } else {
                            when (selectedTab) {
                                0 -> OverviewTab(detail)
                                1 -> ValuationTab(detail)
                                2 -> DividendsTab(detail.dividends)
                                3 -> BalanceTab(detail.balanceSheet)
                                4 -> ResultsTab(detail.results)
                                5 -> IndicatorsTab(detail.indicators, detail.quote.assetType)
                                6 -> NewsTab(detail.news)
                                7 -> ReportsTab(detail.reports)
                                8 -> HistoryTab(detail.priceHistory)
                            }
                        }
                    }
                }
            }
        }
    }
    }
}

private fun mapError(code: String?): String = com.b3insights.app.util.mapErrorMessage(code)
