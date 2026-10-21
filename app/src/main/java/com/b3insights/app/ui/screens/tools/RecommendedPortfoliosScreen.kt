package com.b3insights.app.ui.screens.tools

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.recommended.PORTFOLIO_TEMPLATES
import com.b3insights.app.data.recommended.PortfolioScoring
import com.b3insights.app.data.recommended.PortfolioSnapshot
import com.b3insights.app.data.recommended.PortfolioTemplate
import com.b3insights.app.data.recommended.ScoredPosition
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asDateTime
import com.b3insights.app.util.asPercent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RecommendedPortfoliosScreen(
    onBack: () -> Unit,
    onAssetClick: (String) -> Unit = {}
) {
    var selected by remember { mutableStateOf<PortfolioTemplate?>(null) }
    var snapshot by remember { mutableStateOf<PortfolioSnapshot?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    PaperBackground {
        Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 12.dp)) {
                IconButton(onClick = {
                    if (selected != null) {
                        selected = null
                        snapshot = null
                        error = null
                    } else onBack()
                }) {
                    Icon(Icons.Filled.ArrowBack, null, tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    selected?.name ?: "Carteiras Recomendadas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 24.dp)
            ) {
                if (selected == null) {
                    Text(
                        "Cinco carteiras-modelo com pesos-alvo. Cotações e scores são atualizados com brapi/Yahoo ao abrir cada carteira. Não é recomendação personalizada.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    PORTFOLIO_TEMPLATES.forEach { t ->
                        SoftCard(
                            modifier = Modifier.padding(bottom = 10.dp),
                            onClick = {
                                selected = t
                                loading = true
                                error = null
                                snapshot = null
                                scope.launch {
                                    try {
                                        snapshot = withContext(Dispatchers.IO) { buildSnapshot(t) }
                                    } catch (e: Exception) {
                                        error = "Falha ao atualizar dados. Tente novamente."
                                    } finally {
                                        loading = false
                                    }
                                }
                            }
                        ) {
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(t.name, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                    Spacer(Modifier.height(4.dp))
                                    Text(t.objective, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        "Risco: ${t.riskLevel}  ·  ${t.positions.size} ativos",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                                Icon(Icons.Filled.ChevronRight, null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                } else {
                    val t = selected!!
                    Text(t.objective, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Risco: ${t.riskLevel}  ·  Horizonte: ${t.horizon}",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )

                    if (loading) {
                        Spacer(Modifier.height(24.dp))
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = BluePrimary)
                        }
                        Text(
                            "Atualizando cotações e indicadores…",
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    error?.let {
                        Spacer(Modifier.height(12.dp))
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }

                    snapshot?.let { snap ->
                        Spacer(Modifier.height(12.dp))
                        SoftCard {
                            Text(
                                "Última atualização: ${snap.updatedAt.asDateTime()}",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                "Fontes: ${snap.sources.joinToString(", ")}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                snap.disclaimer,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text("Composição (pesos-alvo)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(8.dp))
                        snap.positions.forEach { p ->
                            SoftCard(
                                modifier = Modifier.padding(bottom = 10.dp),
                                onClick = { onAssetClick(p.ticker) }
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AssetLogo(ticker = p.ticker, size = 40.dp, corner = 10.dp)
                                    Spacer(Modifier.width(12.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(p.ticker, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                                        Text(p.name ?: p.segment, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                                        Text(p.segment, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("${"%.0f".format(p.targetWeight * 100)}%", fontWeight = FontWeight.Bold, color = BluePrimary)
                                        p.price?.let {
                                            Text(it.asCurrency(), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                        }
                                        p.dayChangePercent?.let {
                                            Text(it.asPercent(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                                Spacer(Modifier.height(8.dp))
                                val scoreTxt = p.score?.let { "Score $it/100" } ?: "Score não publicado (dados insuficientes)"
                                Text(scoreTxt, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                                p.score?.let { sc ->
                                    LinearProgressIndicator(
                                        progress = { sc / 100f },
                                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                        color = BluePrimary
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                Text(p.selectionReason, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                                p.factors.filter { it.points != null }.take(4).forEach { f ->
                                    Text(
                                        "• ${f.name}: ${f.points}/100 — ${f.detail}",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                if (p.riskFlags.isNotEmpty()) {
                                    Text(
                                        "Riscos: ${p.riskFlags.joinToString("; ")}",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun buildSnapshot(template: PortfolioTemplate): PortfolioSnapshot = coroutineScope {
    val repo = B3InsightsApp.instance.marketDataRepository
    val scored = template.positions.map { pos ->
        async {
            val quote = when (val q = repo.getQuote(pos.ticker)) {
                is DataResult.Success -> q.data
                else -> null
            }
            val detail = when (val d = repo.getAssetDetail(pos.ticker, ChartPeriod.Y1, forceRefresh = false)) {
                is DataResult.Success -> d.data
                else -> null
            }
            val ind = detail?.indicators
            val price = quote?.currentPrice ?: detail?.quote?.currentPrice
            val (score, factors) = PortfolioScoring.score(
                category = when (template.slug) {
                    "fiis" -> "FII"
                    "bdrs" -> "BDR"
                    "dividendos" -> "dividendos"
                    else -> template.category
                },
                dy = ind?.dividendYield,
                pl = ind?.pl,
                pvp = ind?.pvp,
                roe = ind?.roe,
                netMargin = ind?.netMargin,
                debtToEbitda = ind?.netDebtToEbitda,
                volume = quote?.volume ?: detail?.quote?.volume,
                hasQuote = price != null && price > 0
            )
            val risks = mutableListOf<String>()
            if (template.slug == "bdrs") risks += "Risco cambial (BDR)"
            if (template.slug == "small-caps") risks += "Maior volatilidade (small cap)"
            if (score == null) risks += "Indicadores críticos ausentes"
            if ((quote?.volume ?: 0L) < 50_000L) risks += "Liquidez potencialmente baixa"

            ScoredPosition(
                ticker = pos.ticker,
                name = quote?.name ?: detail?.quote?.name,
                segment = pos.segment,
                targetWeight = pos.targetWeight,
                price = price,
                dayChangePercent = quote?.dayChangePercent ?: detail?.quote?.dayChangePercent,
                score = score,
                factors = factors,
                riskFlags = risks,
                selectionReason = "Peso-alvo do modelo ${template.name}; score recalculado com dados de mercado disponíveis."
            )
        }
    }.awaitAll()

    PortfolioSnapshot(
        template = template,
        positions = scored,
        updatedAt = System.currentTimeMillis(),
        sources = listOf("brapi.dev", "Yahoo Finance")
    )
}
