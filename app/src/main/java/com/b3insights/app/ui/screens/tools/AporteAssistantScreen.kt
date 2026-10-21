package com.b3insights.app.ui.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.util.Valuation
import com.b3insights.app.util.asCurrency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.min

private enum class AporteMode(val label: String, val desc: String) {
    RENDA("Gerar renda", "Prioriza proventos e sustentabilidade"),
    EQUILIBRAR("Equilibrar carteira", "Reduz concentração e diversifica"),
    DESCONTO("Buscar desconto", "Graham, Bazin e margem de segurança"),
    INTELIGENTE("Aporte inteligente", "Cruza todos os fatores")
}

private data class SectorShare(val sector: String, val pct: Double, val emoji: String)

private data class Suggestion(
    val ticker: String,
    val name: String?,
    val assetKind: String,
    val price: Double,
    val score: Double,
    val dy: Double?,
    val bazin: Double?,
    val graham: Double?,
    val marginBazin: Double?,
    val marginGraham: Double?,
    val currentWeight: Double,
    val afterWeight: Double,
    val sharesAffordable: Int,
    val sector: String,
    val corrHint: String,
    val annualIncome: Double?,
    val reasons: List<String>,
    val dpsUsed: Double?,
    val bazinNote: String?
)

private val SECTOR_MAP = mapOf(
    "PETR" to ("Commodities" to "⛏️"), "VALE" to ("Commodities" to "⛏️"), "PRIO" to ("Commodities" to "⛏️"),
    "SUZB" to ("Commodities" to "⛏️"), "CSAN" to ("Commodities" to "⛏️"), "UGPA" to ("Commodities" to "⛏️"),
    "GOAU" to ("Commodities" to "⛏️"), "GGBR" to ("Commodities" to "⛏️"), "USIM" to ("Commodities" to "⛏️"),
    "KLBN" to ("Commodities" to "⛏️"), "VBBR" to ("Commodities" to "⛏️"),
    "ITUB" to ("Bancos" to "🏦"), "BBDC" to ("Bancos" to "🏦"), "BBAS" to ("Bancos" to "🏦"),
    "SANB" to ("Bancos" to "🏦"), "BPAC" to ("Bancos" to "🏦"), "ITSA" to ("Bancos" to "🏦"),
    "ABEV" to ("Consumo" to "🛒"), "LREN" to ("Consumo" to "🛒"), "MGLU" to ("Consumo" to "🛒"),
    "RENT" to ("Consumo" to "🛒"), "JBSS" to ("Consumo" to "🛒"),
    "VIVT" to ("Telecom" to "📡"), "TIMS" to ("Telecom" to "📡"),
    "WEGE" to ("Industrial" to "🏭"), "EMBR" to ("Industrial" to "🏭"), "RAIL" to ("Industrial" to "🏭"),
    "CCRO" to ("Industrial" to "🏭"),
    "EGIE" to ("Energia" to "⚡"), "EQTL" to ("Energia" to "⚡"), "CMIG" to ("Energia" to "⚡"),
    "CPLE" to ("Energia" to "⚡"), "TAEE" to ("Energia" to "⚡"), "ENEV" to ("Energia" to "⚡"),
    "ELET" to ("Energia" to "⚡"), "ENGI" to ("Energia" to "⚡"), "TRPL" to ("Energia" to "⚡"),
    "B3SA" to ("Financeiro" to "💹"), "BBSE" to ("Financeiro" to "💹"), "CXSE" to ("Financeiro" to "💹"),
    "HAPV" to ("Saúde" to "🏥"), "FLRY" to ("Saúde" to "🏥"), "RADL" to ("Saúde" to "🏥"), "HYPE" to ("Saúde" to "🏥"),
    "TOTS" to ("Tecnologia" to "💻"),
    "SBSP" to ("Saneamento" to "💧"),
    "MULT" to ("Imobiliário" to "🏢"), "IGTI" to ("Imobiliário" to "🏢"),
    "MXRF" to ("FII" to "🏠"), "HGLG" to ("FII" to "🏠"), "KNRI" to ("FII" to "🏠"), "XPLG" to ("FII" to "🏠"),
    "VISC" to ("FII" to "🏠"), "BTLG" to ("FII" to "🏠"), "KNCR" to ("FII" to "🏠"), "HGRE" to ("FII" to "🏠"),
    "XPML" to ("FII" to "🏠"), "BCFF" to ("FII" to "🏠"), "IRDM" to ("FII" to "🏠"), "RECT" to ("FII" to "🏠"),
    "GARE" to ("FII" to "🏠"), "CPTS" to ("FII" to "🏠"), "TGAR" to ("FII" to "🏠"), "RBRR" to ("FII" to "🏠"),
    "MALL" to ("FII" to "🏠"), "HSML" to ("FII" to "🏠"), "PVBI" to ("FII" to "🏠"), "VILG" to ("FII" to "🏠"),
    "HGBS" to ("FII" to "🏠"), "JSRE" to ("FII" to "🏠"), "BRCO" to ("FII" to "🏠"), "LVBI" to ("FII" to "🏠"),
    "TRXF" to ("FII" to "🏠"), "DEVA" to ("FII" to "🏠"), "KNIP" to ("FII" to "🏠"), "HGCR" to ("FII" to "🏠"),
    "BOVA" to ("ETF" to "📊"), "IVVB" to ("ETF" to "📊"), "SMAL" to ("ETF" to "📊"), "HASH" to ("ETF" to "📊"),
    "GOLD" to ("ETF" to "📊"), "NASD" to ("ETF" to "📊"), "XINA" to ("ETF" to "📊"), "EURP" to ("ETF" to "📊"),
    "MATB" to ("ETF" to "📊"), "FIND" to ("ETF" to "📊")
)

private fun sectorOf(ticker: String): Pair<String, String> {
    val t = ticker.uppercase().removeSuffix(".SA")
    SECTOR_MAP.entries.firstOrNull { t.startsWith(it.key) }?.let { return it.value }
    return when {
        t.endsWith("11") && t.length >= 5 -> "FII" to "🏠"
        t.endsWith("11") -> "ETF" to "📊"
        else -> "Outros" to "📌"
    }
}

private fun kindOf(ticker: String): String {
    val t = ticker.uppercase()
    val (sec, _) = sectorOf(t)
    return when (sec) {
        "FII" -> "FII"
        "ETF" -> "ETF"
        else -> "Ação"
    }
}

/** Universo amplo: ações + FIIs + ETFs líquidos da B3 */
private val UNIVERSE = listOf(
    // Ações
    "PETR4", "VALE3", "ITUB4", "BBDC4", "BBAS3", "ABEV3", "WEGE3", "RENT3",
    "B3SA3", "SUZB3", "JBSS3", "RADL3", "PRIO3", "EQTL3", "VIVT3", "EGIE3",
    "CMIG4", "CPLE6", "TAEE11", "SANB11", "BPAC11", "HAPV3", "TOTS3", "RAIL3",
    "CSAN3", "UGPA3", "ENEV3", "VBBR3", "KLBN11", "GOAU4", "GGBR4", "USIM5",
    "EMBR3", "CCRO3", "MULT3", "FLRY3", "HYPE3", "LREN3", "BBSE3", "ITSA4",
    "ELET3", "SBSP3", "ENGI11", "TRPL4", "CXSE3", "TIMS3",
    // FIIs
    "MXRF11", "HGLG11", "KNRI11", "XPLG11", "VISC11", "BTLG11", "KNCR11",
    "HGRE11", "XPML11", "BCFF11", "IRDM11", "RECT11", "GARE11", "CPTS11",
    "TGAR11", "RBRR11", "MALL11", "HSML11", "PVBI11", "VILG11", "HGBS11",
    "JSRE11", "BRCO11", "LVBI11", "TRXF11", "KNIP11", "HGCR11",
    // ETFs
    "BOVA11", "IVVB11", "SMAL11", "HASH11", "GOLD11", "NASD11", "XINA11",
    "FIND11", "MATB11", "DIVO11", "ISUS11",
    // Mais FIIs e ações líquidas
    "XPCI11", "VGIR11", "RECR11", "KNSC11", "RZTR11",
    "PETR3", "VALE5", "BBDC3", "ITUB3", "WEGE3", "RDOR3", "ASAI3", "CRFB3",
    "CSNA3", "BRKM5", "BRFS3", "CYRE3", "EZTC3", "MRVE3"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AporteAssistantScreen(
    onBack: () -> Unit,
    onAssetClick: (String) -> Unit = {}
) {
    var amountText by remember { mutableStateOf("50") }
    var mode by remember { mutableStateOf(AporteMode.INTELIGENTE) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var sectors by remember { mutableStateOf<List<SectorShare>>(emptyList()) }
    var suggestions by remember { mutableStateOf<List<Suggestion>>(emptyList()) }
    var analyzedAmount by remember { mutableStateOf(0.0) }
    val scope = rememberCoroutineScope()

    PaperBackground {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 12.dp)) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = MaterialTheme.colorScheme.onBackground)
                }
                Text(
                    "Assistente de Aporte",
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
                Text(
                    "Informe o valor. O app cruza sua carteira com cotações atualizadas, Bazin, Graham e diversificação — sem ordem automática de compra.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(16.dp))

                SoftCard {
                    Text("Quanto você deseja aportar?", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it.filter { c -> c.isDigit() || c == ',' || c == '.' } },
                        label = { Text("Valor (R$)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Modo de análise", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AporteMode.entries.forEach { m ->
                            val selected = mode == m
                            Box(
                                Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (selected) BluePrimary else MaterialTheme.colorScheme.surface)
                                    .border(
                                        1.dp,
                                        if (selected) BluePrimary else MaterialTheme.colorScheme.outlineVariant,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .clickable { mode = m }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    m.label,
                                    color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                    Text(
                        mode.desc,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(Modifier.height(14.dp))
                    Button(
                        onClick = {
                            val amount = amountText.replace(',', '.').toDoubleOrNull()
                            if (amount == null || amount <= 0) {
                                error = "Informe um valor válido de aporte."
                                return@Button
                            }
                            loading = true
                            error = null
                            suggestions = emptyList()
                            scope.launch {
                                try {
                                    val result = withContext(Dispatchers.IO) {
                                        runAporteAnalysis(amount, mode)
                                    }
                                    sectors = result.first
                                    suggestions = result.second
                                    analyzedAmount = amount
                                    if (result.second.isEmpty()) {
                                        error = "Nenhuma sugestão com dados atualizados para este valor/modo. Tente outro valor ou modo."
                                    }
                                } catch (e: Exception) {
                                    error = "Não foi possível analisar agora. Verifique a conexão e tente de novo."
                                } finally {
                                    loading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                    ) {
                        Text("Analisar minha carteira")
                    }
                }

                if (loading) {
                    Spacer(Modifier.height(24.dp))
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = BluePrimary)
                    }
                    Text(
                        "Buscando cotações e fundamentos atualizados (ações, FIIs e ETFs)…",
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                error?.let {
                    Spacer(Modifier.height(12.dp))
                    Text(it, color = Color(0xFFE23D3D), style = MaterialTheme.typography.bodyMedium)
                }

                if (suggestions.isNotEmpty()) {
                    Spacer(Modifier.height(20.dp))
                    Text(
                        "Aporte de ${analyzedAmount.asCurrency()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))

                    SoftCard {
                        Text("Análise da sua carteira", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                        Spacer(Modifier.height(6.dp))
                        if (sectors.isEmpty()) {
                            Text(
                                "Carteira vazia. Sugestões baseadas só em fundamentos e preço atual.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        } else {
                            Text(
                                "Exposição atual por setor:",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            sectors.take(6).forEach { s ->
                                Text(
                                    "${s.emoji} ${s.sector} — ${"%.0f".format(s.pct)}%",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Sugestões (${suggestions.size}) — toque para abrir o ativo",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(Modifier.height(8.dp))

                    suggestions.forEach { s ->
                        SoftCard(
                            modifier = Modifier.padding(bottom = 10.dp),
                            onClick = { onAssetClick(s.ticker) }
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AssetLogo(ticker = s.ticker, size = 40.dp, corner = 10.dp)
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        "${s.ticker}  ·  ${s.assetKind}",
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                    Text(
                                        s.name ?: s.sector,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(s.price.asCurrency(), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                            }
                            Spacer(Modifier.height(6.dp))

                            // Graham/Bazin só para ações (não FII/ETF)
                            if (s.assetKind == "Ação") {
                                val bazinLine = when {
                                    s.bazin != null && s.marginBazin != null ->
                                        "Bazin ${s.bazin.asCurrency()} (margem ${"%.1f".format(s.marginBazin)}%)"
                                    s.bazin != null -> "Bazin ${s.bazin.asCurrency()}"
                                    else -> "Bazin —"
                                }
                                val grahamLine = when {
                                    s.graham != null && s.marginGraham != null ->
                                        "Graham ${s.graham.asCurrency()} (margem ${"%.1f".format(s.marginGraham)}%)"
                                    s.graham != null -> "Graham ${s.graham.asCurrency()}"
                                    else -> "Graham —"
                                }
                                Text(bazinLine, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
                                Text(grahamLine, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
                                s.dpsUsed?.let {
                                    Text(
                                        "DPS 12m: ${it.asCurrency()}" + (s.dy?.let { d -> "  ·  DY ${"%.1f".format(d)}%" } ?: ""),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                                s.bazinNote?.let {
                                    Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
                                }
                            } else {
                                s.dy?.let {
                                    Text(
                                        "DY ${"%.1f".format(it)}%  ·  análise por rendimento e diversificação (sem Graham/Bazin)",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } ?: Text(
                                    "Análise por segmento, liquidez e diversificação (Graham/Bazin não se aplicam a ${s.assetKind})",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Com ${analyzedAmount.asCurrency()} ≈ ${s.sharesAffordable} cota(s)  ·  peso ${"%.1f".format(s.currentWeight)}% → ~${"%.1f".format(s.afterWeight)}%",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "Correlação estimada: ${s.corrHint}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            s.annualIncome?.let {
                                Text(
                                    "Proventos anuais estimados neste aporte: ${it.asCurrency()}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            s.reasons.forEach { r ->
                                Text("• $r", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodySmall)
                            }
                            Text(
                                "Análise informativa — não é recomendação de compra.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private suspend fun runAporteAnalysis(amount: Double, mode: AporteMode): Pair<List<SectorShare>, List<Suggestion>> {
    val app = B3InsightsApp.instance
    val repo = app.marketDataRepository
    val positions = runCatching {
        app.portfolioRepository.getAllPositions().first()
    }.getOrDefault(emptyList())

    val holdings = linkedMapOf<String, Double>()
    positions.groupBy { it.ticker.uppercase() }.forEach { (t, rows) ->
        val qty = rows.sumOf { it.quantity }
        val avg = rows.sumOf { it.quantity * it.averagePrice } / qty.coerceAtLeast(1e-9)
        val price = when (val q = repo.getQuote(t)) {
            is DataResult.Success -> q.data.currentPrice ?: avg
            else -> avg
        }
        holdings[t] = qty * price
    }
    val total = holdings.values.sum().coerceAtLeast(0.01)

    val sectorTotals = mutableMapOf<String, Pair<Double, String>>()
    holdings.forEach { (t, v) ->
        val (sec, emoji) = sectorOf(t)
        val prev = sectorTotals[sec]
        sectorTotals[sec] = ((prev?.first ?: 0.0) + v) to emoji
    }
    val sectorShares = sectorTotals.entries
        .map { (sec, p) -> SectorShare(sec, 100.0 * p.first / total, p.second) }
        .sortedByDescending { it.pct }

    val underweightSectors = sectorShares.filter { it.pct < 12.0 }.map { it.sector }.toSet()
    val dominantSectors = sectorShares.filter { it.pct >= 22.0 }.map { it.sector }.toSet()

    data class Cand(
        val ticker: String,
        val name: String?,
        val price: Double,
        val dy: Double?,
        val bazin: Double?,
        val graham: Double?,
        val mBazin: Double?,
        val mGraham: Double?,
        val sector: String,
        val dps: Double?,
        val bazinNote: String?
    )

    // Busca em paralelo (lotes) com force path via getAssetDetail
    val candidates = coroutineScope {
        UNIVERSE.map { ticker ->
            async {
                try {
                    val quote = when (val q = repo.getQuote(ticker)) {
                        is DataResult.Success -> q.data
                        else -> return@async null
                    }
                    val price = quote.currentPrice ?: return@async null
                    if (price <= 0) return@async null
                    // Aporte precisa comprar ao menos ~0.05 cota (fracionário) ou 1 se muito caro
                    if (amount > 0 && price > amount * 25) return@async null // muito caro para o aporte

                    val detail = when (val d = repo.getAssetDetail(ticker, ChartPeriod.Y1, forceRefresh = false)) {
                        is DataResult.Success -> d.data
                        else -> null
                    }
                    val ind = detail?.indicators
                    val isStock = kindOf(ticker) == "Ação"
                    val graham = if (isStock) Valuation.grahamPrice(ind?.lpa, ind?.vpa, price, ind?.pl, ind?.pvp) else null
                    val breakdown = if (isStock) Valuation.bazinBreakdown(price, ind?.dividendYield, ind?.dividendPerShare) else null
                    val bazin = breakdown?.ceilingPrice
                    val (sec, _) = sectorOf(ticker)
                    Cand(
                        ticker = ticker,
                        name = quote.name,
                        price = price,
                        dy = ind?.dividendYield?.let { if (it <= 0.25) it * 100 else if (it <= 1.0) it else it }.let { v ->
                            // normalize display same spirit as Valuation
                            when {
                                v == null || v <= 0 -> null
                                v > 1.0 -> v
                                v <= 0.25 -> v * 100
                                else -> v
                            }?.takeIf { it <= 40.0 }
                        } ?: breakdown?.let {
                            if (price > 0) (it.dividendsPerShare / price) * 100.0 else null
                        },
                        bazin = bazin,
                        graham = graham,
                        mBazin = Valuation.safetyMargin(bazin, price),
                        mGraham = Valuation.safetyMargin(graham, price),
                        sector = sec,
                        dps = breakdown?.dividendsPerShare,
                        bazinNote = breakdown?.dividendPeriod
                    )
                } catch (_: Exception) {
                    null
                }
            }
        }.awaitAll().filterNotNull()
    }

    fun corrHint(sector: String): String = when {
        sector in dominantSectors -> "alta (setor já dominante na carteira)"
        sector in underweightSectors || sectorShares.none { it.sector == sector } -> "baixa (setor pouco representado)"
        else -> "moderada"
    }

    fun score(c: Cand): Pair<Double, List<String>> {
        val reasons = mutableListOf<String>()
        var s = 0.0
        val weight = (holdings[c.ticker] ?: 0.0) / total * 100.0
        val shares = (amount / c.price).toInt()
        // Valor do aporte importa: favorece ativos em que o valor compra quantidade útil
        val affordBoost = when {
            shares >= 20 -> 14.0
            shares >= 10 -> 12.0
            shares >= 3 -> 8.0
            shares >= 1 -> 5.0
            amount >= c.price * 0.1 -> 2.0
            else -> -5.0
        }
        s += affordBoost
        // Faixas de aporte: valores maiores favorecem blue chips; menores, FIIs/preço acessível
        when {
            amount < 80 && c.price <= 25 -> { s += 10.0; reasons += "Preço acessível para aportes pequenos" }
            amount < 80 && kindOf(c.ticker) == "FII" -> s += 6.0
            amount in 80.0..300.0 && shares >= 1 -> s += 4.0
            amount > 300 && kindOf(c.ticker) == "Ação" && (c.mBazin ?: 0.0) > 5 -> s += 8.0
            amount > 500 && shares >= 5 -> s += 6.0
        }
        if (shares >= 1) reasons += "Com este aporte você compra cerca de $shares cota(s)"

        // Diversidade de classes
        when (kindOf(c.ticker)) {
            "FII" -> if (mode == AporteMode.RENDA || mode == AporteMode.INTELIGENTE) s += 6.0
            "ETF" -> if (mode == AporteMode.EQUILIBRAR || mode == AporteMode.INTELIGENTE) s += 5.0
        }

        when (mode) {
            AporteMode.RENDA -> {
                c.dy?.let {
                    s += it * 2.2
                    if (it >= 6) reasons += "Dividend yield atrativo (${"%.1f".format(it)}%)"
                    else if (it >= 3) reasons += "Dividend yield moderado (${"%.1f".format(it)}%)"
                }
                c.mBazin?.let {
                    if (it > 0) {
                        s += min(it, 40.0) * 0.45
                        reasons += "Abaixo do teto Bazin (margem ${"%.1f".format(it)}%)"
                    }
                }
            }
            AporteMode.EQUILIBRAR -> {
                if (c.sector in underweightSectors || sectorShares.none { it.sector == c.sector }) {
                    s += 28.0
                    reasons += "Setor ${c.sector} com menor peso na carteira"
                }
                if (c.sector in dominantSectors) s -= 18.0
                if (weight < 5) {
                    s += 12.0
                    reasons += "Baixa concentração neste ticker (${"%.1f".format(weight)}%)"
                }
                c.mBazin?.takeIf { it > 0 }?.let { s += min(it, 30.0) * 0.25 }
            }
            AporteMode.DESCONTO -> {
                c.mBazin?.let {
                    s += min(it, 50.0) * 1.1
                    if (it > 0) reasons += "Margem Bazin ${"%.1f".format(it)}%"
                }
                c.mGraham?.let {
                    s += min(it, 50.0) * 1.0
                    if (it > 0) reasons += "Margem Graham ${"%.1f".format(it)}%"
                }
                if (c.mBazin == null && c.mGraham == null) s -= 10.0
            }
            AporteMode.INTELIGENTE -> {
                c.dy?.let { s += it * 1.1 }
                c.mBazin?.let { if (it > 0) s += min(it, 40.0) * 0.7 }
                c.mGraham?.let { if (it > 0) s += min(it, 40.0) * 0.55 }
                if (c.sector in underweightSectors || sectorShares.none { it.sector == c.sector }) {
                    s += 16.0
                    reasons += "Ajuda a diversificar (${c.sector})"
                }
                if (weight < 8) s += 6.0
            }
        }
        // Penaliza peso absurdo após aporte (ex.: 59% em carteira pequena)
        val afterW = ((holdings[c.ticker] ?: 0.0) + amount) / (total + amount) * 100.0
        if (afterW > 35) s -= (afterW - 35) * 0.8

        if (reasons.isEmpty()) reasons += "Pontuação pelo modo ${mode.label}"
        return s to reasons
    }

    // Rank + diversidade: no máximo 2 por setor no top
    val ranked = candidates.map { c ->
        val (sc, reasons) = score(c)
        val weight = (holdings[c.ticker] ?: 0.0) / total * 100.0
        val afterW = ((holdings[c.ticker] ?: 0.0) + amount) / (total + amount) * 100.0
        val shares = (amount / c.price).toInt()
        val income = c.dy?.let { amount * (it / 100.0) }
        Suggestion(
            ticker = c.ticker,
            name = c.name,
            assetKind = kindOf(c.ticker),
            price = c.price,
            score = sc,
            dy = c.dy,
            bazin = c.bazin,
            graham = c.graham,
            marginBazin = c.mBazin,
            marginGraham = c.mGraham,
            currentWeight = weight,
            afterWeight = afterW,
            sharesAffordable = shares,
            sector = c.sector,
            corrHint = corrHint(c.sector),
            annualIncome = income,
            reasons = reasons,
            dpsUsed = c.dps,
            bazinNote = c.bazinNote
        )
    }.sortedByDescending { it.score }

    val picked = mutableListOf<Suggestion>()
    val sectorCount = mutableMapOf<String, Int>()
    for (s in ranked) {
        val n = sectorCount[s.sector] ?: 0
        if (n >= 2) continue
        picked.add(s)
        sectorCount[s.sector] = n + 1
        if (picked.size >= 8) break
    }

    return sectorShares to picked
}
