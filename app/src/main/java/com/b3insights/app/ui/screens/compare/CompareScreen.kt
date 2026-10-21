@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.compare

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.AssetDetail
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.ui.theme.DividerColor
import com.b3insights.app.ui.theme.SurfaceCard
import com.b3insights.app.ui.theme.TextPrimary
import com.b3insights.app.ui.theme.TextSecondary
import com.b3insights.app.util.asCompactMoney
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asDecimal
import com.b3insights.app.util.asPercent
import com.b3insights.app.viewmodel.CompareViewModel
import kotlinx.coroutines.delay

private data class CompareRow(
    val label: String,
    val extract: (AssetDetail) -> String
)

private val compareRows = listOf(
    CompareRow("Preço") { d -> d.quote.currentPrice.asCurrency() },
    CompareRow("Variação (dia)") { d -> d.quote.dayChangePercent.asPercent() },
    CompareRow("P/L") { d -> d.indicators.pl.asDecimal() },
    CompareRow("P/VP") { d -> d.indicators.pvp.asDecimal() },
    CompareRow("ROE") { d -> d.indicators.roe.asPercent(showSign = false) },
    CompareRow("ROIC") { d -> d.indicators.roic.asPercent(showSign = false) },
    CompareRow("DY") { d -> d.indicators.dividendYield.asPercent(showSign = false) },
    CompareRow("Margem líquida") { d -> d.indicators.netMargin.asPercent(showSign = false) },
    CompareRow("Receita") { d -> d.indicators.revenue.asCompactMoney() },
    CompareRow("Dív.líq./EBITDA") { d -> d.indicators.netDebtToEbitda.asDecimal() }
)

@Composable
fun CompareScreen(viewModel: CompareViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    var newTicker by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<AssetQuote>>(emptyList()) }
    val repo = B3InsightsApp.instance.marketDataRepository

    LaunchedEffect(newTicker) {
        val q = newTicker.trim()
        if (q.length < 2) {
            suggestions = emptyList()
            return@LaunchedEffect
        }
        delay(350)
        when (val r = repo.searchAssets(q)) {
            is DataResult.Success -> suggestions = r.data.take(6)
            else -> suggestions = emptyList()
        }
    }

    PaperBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                "Comparar Ativos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Selecione até 4 ativos para comparar lado a lado.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newTicker,
                    onValueChange = { newTicker = it.uppercase() },
                    label = { Text("Ticker ou nome") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (newTicker.isNotBlank()) {
                            viewModel.addTicker(newTicker.trim())
                            newTicker = ""
                            suggestions = emptyList()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    enabled = uiState.tickers.size < 4
                ) {
                    Text("Adicionar")
                }
            }

            if (suggestions.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                suggestions.forEach { s ->
                    SoftCardSuggest(
                        title = s.ticker,
                        subtitle = s.name,
                        onClick = {
                            viewModel.addTicker(s.ticker)
                            newTicker = ""
                            suggestions = emptyList()
                        }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (uiState.tickers.isEmpty()) {
                EmptyState("Adicione de 2 a 4 tickers para comparar, ex.: ITUB4 × BBAS3 × PETR4.")
            } else {
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = BluePrimary
                    )
                    Spacer(Modifier.height(8.dp))
                }

                val hScroll = rememberScrollState()
                val rowHeight = 40.dp
                SoftCard {
                    Row(modifier = Modifier.horizontalScroll(hScroll)) {
                        // Coluna de labels
                        Column(modifier = Modifier.width(120.dp)) {
                            Box(
                                modifier = Modifier.height(rowHeight).fillMaxWidth(),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(
                                    "Indicador",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                            compareRows.forEachIndexed { idx, row ->
                                Box(
                                    modifier = Modifier.height(rowHeight).fillMaxWidth(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        row.label,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(rowHeight * (compareRows.size + 1))
                                .background(androidx.compose.ui.graphics.Color.Transparent)
                        )

                        uiState.tickers.forEach { ticker ->
                            Column(
                                modifier = Modifier.width(110.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier.height(rowHeight).padding(horizontal = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AssetLogo(ticker = ticker, size = 18.dp, corner = 5.dp)
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        ticker,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.labelMedium,
                                        maxLines = 1
                                    )
                                    IconButton(
                                        onClick = { viewModel.removeTicker(ticker) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Close,
                                            contentDescription = "Remover",
                                            tint = TextSecondary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                val detail = uiState.details[ticker]
                                compareRows.forEachIndexed { idx, row ->
                                    Box(
                                        modifier = Modifier
                                            .height(rowHeight)
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = detail?.let { row.extract(it) } ?: "…",
                                            color = MaterialTheme.colorScheme.onBackground,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Medium,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(rowHeight * (compareRows.size + 1))
                                    .background(androidx.compose.ui.graphics.Color.Transparent)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun SoftCardSuggest(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = SurfaceCard,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            if (!subtitle.isNullOrBlank()) {
                Text(
                    subtitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }
    }
}
