@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.tools

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.local.entity.PriceAlertEntity
import com.b3insights.app.data.local.entity.UserSettingsEntity
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.quoteTickerFor
import com.b3insights.app.util.isCryptoTicker
import com.b3insights.app.util.asPercent
import com.b3insights.app.util.PortfolioReturnCalculator
import com.b3insights.app.util.PortfolioCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow

/* ───────────────── Hub ───────────────── */

@Composable
fun ToolsHubScreen(
    onBack: () -> Unit,
    onOpen: (String) -> Unit
) {
    data class ToolItem(val key: String, val title: String, val subtitle: String)
    val items = listOf(
        ToolItem("daily", "Resumo diário da carteira", "Patrimônio de hoje, variação do dia, maior alta e maior baixa"),
        ToolItem("goal", "Metas (patrimônio e renda)", "Objetivo de patrimônio em R$ e meta de proventos mensais"),
        ToolItem("alerts", "Alertas de preço", "Aviso quando um ativo atingir o preço que você escolheu"),
        ToolItem("proventos", "Proventos e yield on cost", "Dividendos dos últimos 12 meses e renda sobre o que você pagou"),
        ToolItem("performance", "Rentabilidade (TIR e TWR)", "Retorno real com proventos: TIR e TWR — não só variação de preço"),
        ToolItem("allocation", "Metas de alocação por classe", "Quanto % em ações, FIIs, ETFs e cripto — e se está fora do alvo"),
        ToolItem("simulator", "Simulador de aporte", "Projete o valor futuro com aportes mensais"),
        ToolItem("aporte", "Assistente de Aporte", "Com X reais, onde encaixa melhor (renda, equilíbrio ou desconto)"),
        ToolItem("fii_screener", "Filtro de FIIs (planilha)", "Liquidez, P/VP, DY, vacância e segmento — estilo Fundamentus / Léo"),
        ToolItem("stock_valuation", "Valuation de Ações", "Graham, Bazin, Lynch, Barsi e DCF — radar multimétodo da B3"),
        ToolItem("recommended", "Carteiras modelo", "Modelos prontos (dividendos, FIIs, etc.) — não confundir com a sua carteira"),
        ToolItem("export", "Exportar carteira (CSV)", "Baixe suas posições para planilha")
    )
    PaperBackground {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary)
                }
                Text(
                    "Ferramentas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                "Atalhos para acompanhar e planejar seus investimentos",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            items.forEach { item ->
                SoftCard(
                    modifier = Modifier.padding(bottom = 10.dp),
                    onClick = { onOpen(item.key) }
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                item.title,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                item.subtitle,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Icon(
                            Icons.Filled.ChevronRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(Modifier.height(80.dp))
        }
    }
}

/* ───────────────── Alertas ───────────────── */

class AlertsViewModel : ViewModel() {
    private val repo = B3InsightsApp.instance.alertsRepository
    private val market = B3InsightsApp.instance.marketDataRepository
    val alerts = repo.getAll()

    private val _livePrices = MutableStateFlow<Map<String, Double>>(emptyMap())
    val livePrices: StateFlow<Map<String, Double>> = _livePrices.asStateFlow()

    fun refreshPrices() {
        viewModelScope.launch {
            val all = B3InsightsApp.instance.database.priceAlertDao().getAllOnce()
            val map = mutableMapOf<String, Double>()
            for (a in all.distinctBy { it.ticker.uppercase() }) {
                when (val q = market.getQuote(a.ticker)) {
                    is DataResult.Success -> q.data.currentPrice?.let { map[a.ticker.uppercase()] = it }
                    else -> Unit
                }
            }
            _livePrices.value = map
        }
    }

    fun add(ticker: String, target: Double, above: Boolean) {
        viewModelScope.launch {
            repo.add(ticker, target, above)
            refreshPrices()
        }
    }

    fun delete(a: PriceAlertEntity) {
        viewModelScope.launch { repo.delete(a) }
    }

    fun reenable(a: PriceAlertEntity) {
        viewModelScope.launch {
            repo.update(a.copy(enabled = true, triggeredAt = null, updatedAt = System.currentTimeMillis()))
            refreshPrices()
        }
    }

    fun checkNow(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val enabled = repo.getEnabled()
            if (enabled.isEmpty()) {
                onResult("Nenhum alerta ativo.")
                return@launch
            }
            val triggered = mutableListOf<String>()
            val priceMap = _livePrices.value.toMutableMap()
            for (a in enabled) {
                when (val q = market.getQuote(a.ticker)) {
                    is DataResult.Success -> {
                        val px = q.data.currentPrice ?: continue
                        priceMap[a.ticker.uppercase()] = px
                        val hit = if (a.above) px >= a.targetPrice else px <= a.targetPrice
                        if (hit) {
                            triggered += "${a.ticker}: ${px.asCurrency()} (meta ${a.targetPrice.asCurrency()})"
                            repo.update(a.copy(triggeredAt = System.currentTimeMillis(), enabled = false, updatedAt = System.currentTimeMillis()))
                        }
                    }
                    else -> Unit
                }
            }
            _livePrices.value = priceMap
            onResult(
                if (triggered.isEmpty()) "Nenhum alerta disparou agora. Preços atualizados."
                else "Dispararam:\n" + triggered.joinToString("\n")
            )
        }
    }
}

@Composable
fun AlertsScreen(onBack: () -> Unit, vm: AlertsViewModel = viewModel()) {
    val list by vm.alerts.collectAsState(initial = emptyList())
    val livePrices by vm.livePrices.collectAsState()
    var ticker by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var above by remember { mutableStateOf(true) }
    var status by remember { mutableStateOf<String?>(null) }
    var suggestions by remember { mutableStateOf<List<Triple<String, String, Double?>>>(emptyList()) }
    var showSuggestions by remember { mutableStateOf(false) }
    var selectedPrice by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(Unit) { vm.refreshPrices() }

    LaunchedEffect(ticker) {
        val q = ticker.trim()
        if (q.length < 1) {
            suggestions = emptyList()
            showSuggestions = false
            selectedPrice = null
            return@LaunchedEffect
        }
        kotlinx.coroutines.delay(280)
        val market = B3InsightsApp.instance.marketDataRepository
        when (val r = market.searchAssets(q)) {
            is DataResult.Success -> {
                suggestions = r.data.take(8).map { a ->
                    Triple(a.ticker.removeSuffix(".SA"), a.name ?: a.ticker, a.currentPrice)
                }
                showSuggestions = suggestions.isNotEmpty()
            }
            else -> {
                val local = listOf("PETR4","VALE3","ITUB4","BBDC4","BBAS3","WEGE3","MXRF11","HGLG11","BOVA11","BTC","ETH","CPFE3")
                    .filter { it.startsWith(q.uppercase()) }
                suggestions = local.map { Triple(it, it, null) }
                showSuggestions = suggestions.isNotEmpty()
            }
        }
    }

    fun setTargetFromPercent(pct: Double, current: Double, isAbove: Boolean) {
        val t = if (isAbove) current * (1.0 + pct / 100.0) else current * (1.0 - pct / 100.0)
        target = String.format(java.util.Locale.US, "%.2f", t)
    }

    PaperBackground {
        Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text("Alertas de preço", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }
            Text(
                "Receba aviso quando o ativo cruzar o preço que você definiu. Use atalhos ±5% / ±10% em cima da cotação atual.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(12.dp))
            SoftCard {
                OutlinedTextField(
                    value = ticker,
                    onValueChange = { ticker = it.uppercase(); showSuggestions = true },
                    label = { Text("Ticker") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (showSuggestions && suggestions.isNotEmpty()) {
                    Spacer(Modifier.height(6.dp))
                    suggestions.forEach { (sym, name, price) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    ticker = sym
                                    showSuggestions = false
                                    selectedPrice = price
                                    if (price != null && price > 0) {
                                        target = String.format(java.util.Locale.US, "%.2f", price)
                                    }
                                }
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                if (name != sym) "$sym · $name" else sym,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            if (price != null && price > 0) {
                                Text(price.asCurrency(), fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                            }
                        }
                    }
                }
                selectedPrice?.let { px ->
                    Spacer(Modifier.height(8.dp))
                    Text("Cotação de referência: ${px.asCurrency()}", color = BluePrimary, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(-10.0, -5.0, 5.0, 10.0).forEach { p ->
                            FilterChip(
                                selected = false,
                                onClick = {
                                    above = p > 0
                                    setTargetFromPercent(kotlin.math.abs(p), px, p > 0)
                                },
                                label = { Text(if (p > 0) "+${p.toInt()}%" else "${p.toInt()}%") }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(target, { target = it }, label = { Text("Preço alvo (R$)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
                Row {
                    FilterChip(selected = above, onClick = { above = true }, label = { Text("Acima (≥)") })
                    Spacer(Modifier.width(8.dp))
                    FilterChip(selected = !above, onClick = { above = false }, label = { Text("Abaixo (≤)") })
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        val t = target.replace(",", ".").toDoubleOrNull() ?: return@Button
                        if (ticker.isBlank()) return@Button
                        vm.add(ticker, t, above)
                        ticker = ""; target = ""; selectedPrice = null; status = "Alerta criado"
                        showSuggestions = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Adicionar alerta") }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = { vm.checkNow { status = it } }, modifier = Modifier.fillMaxWidth()) {
                    Text("Verificar agora")
                }
            }
            status?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = GreenPositive)
            }
            Spacer(Modifier.height(16.dp))
            Text("Seus alertas", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            if (list.isEmpty()) {
                SoftCard {
                    Text("Nenhum alerta ainda. Escolha um ticker, defina o alvo e adicione.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            list.forEach { a ->
                val live = livePrices[a.ticker.uppercase()]
                val dist = live?.let { px ->
                    val d = ((a.targetPrice - px) / px) * 100.0
                    d
                }
                SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            "${a.ticker}  ${if (a.above) "≥" else "≤"} ${a.targetPrice.asCurrency()}",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            if (a.enabled) "Ativo" else "Disparado",
                            color = if (a.enabled) GreenPositive else TextSecondary,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    if (live != null) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Agora: ${live.asCurrency()}" +
                                (dist?.let { " · falta ${String.format(java.util.Locale("pt","BR"), "%.1f", kotlin.math.abs(it))}% para o alvo" } ?: ""),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    a.triggeredAt?.let { ts ->
                        Text(
                            "Disparado em " + java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale("pt","BR")).format(java.util.Date(ts)),
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Row {
                        if (!a.enabled) {
                            TextButton(onClick = { vm.reenable(a) }) { Text("Reativar", color = BluePrimary) }
                        }
                        TextButton(onClick = { vm.delete(a) }) { Text("Remover", color = RedNegative) }
                    }
                }
            }
        }
    }
}

/* ───────────────── Proventos ───────────────── */

data class ProventoRow(
    val ticker: String,
    val type: String,
    val valuePerShare: Double,
    val qty: Double,
    val total: Double,
    val date: Long,
    val avgCost: Double = 0.0
)

data class YieldOnCostRow(
    val ticker: String,
    val invested: Double,
    val annualProventos: Double,
    val yocPct: Double?
)

class ProventosViewModel : ViewModel() {
    private val portfolio = B3InsightsApp.instance.portfolioRepository
    private val market = B3InsightsApp.instance.marketDataRepository
    private val settingsDao = B3InsightsApp.instance.database.userSettingsDao()
    private val _rows = MutableStateFlow<List<ProventoRow>>(emptyList())
    val rows: StateFlow<List<ProventoRow>> = _rows.asStateFlow()
    private val _yoc = MutableStateFlow<List<YieldOnCostRow>>(emptyList())
    val yoc: StateFlow<List<YieldOnCostRow>> = _yoc.asStateFlow()
    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()
    private val _portfolioYoc = MutableStateFlow<Double?>(null)
    val portfolioYoc: StateFlow<Double?> = _portfolioYoc.asStateFlow()
    private val _rendaMeta = MutableStateFlow(0.0)
    val rendaMeta: StateFlow<Double> = _rendaMeta.asStateFlow()
    private val _investedTotal = MutableStateFlow(0.0)
    val investedTotal: StateFlow<Double> = _investedTotal.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _loading.value = true
            val settings = settingsDao.getSettings().first()
            _rendaMeta.value = settings?.targetRendaMensal ?: 0.0
            val positions = portfolio.getAllPositions().first()
            val byTicker = positions.filter { it.side != "SELL" }.groupBy { it.ticker }
            val out = mutableListOf<ProventoRow>()
            val yocList = mutableListOf<YieldOnCostRow>()
            val yearAgo = System.currentTimeMillis() - 365L * 24 * 3600 * 1000
            var investedSum = 0.0
            var annualSum = 0.0
            for ((ticker, list) in byTicker) {
                val qty = list.sumOf { it.quantity }
                if (qty <= 0) continue
                val cost = list.sumOf { it.quantity * it.averagePrice }
                investedSum += cost
                val avg = if (qty > 0) cost / qty else 0.0
                var annualForTicker = 0.0
                when (val d = market.getAssetDetail(ticker, ChartPeriod.Y1)) {
                    is DataResult.Success -> {
                        d.data.dividends.filter { it.exDate >= yearAgo }.forEach { div ->
                            val tot = div.valuePerShare * qty
                            annualForTicker += tot
                            out += ProventoRow(
                                ticker = ticker,
                                type = div.type,
                                valuePerShare = div.valuePerShare,
                                qty = qty,
                                total = tot,
                                date = div.exDate,
                                avgCost = avg
                            )
                        }
                    }
                    else -> Unit
                }
                annualSum += annualForTicker
                yocList += YieldOnCostRow(
                    ticker = ticker,
                    invested = cost,
                    annualProventos = annualForTicker,
                    yocPct = if (cost > 0 && annualForTicker > 0) (annualForTicker / cost) * 100.0 else null
                )
            }
            _rows.value = out.sortedByDescending { it.date }
            _yoc.value = yocList.filter { it.annualProventos > 0 || it.invested > 0 }
                .sortedByDescending { it.yocPct ?: -1.0 }
            _total.value = out.sumOf { it.total }
            _investedTotal.value = investedSum
            _portfolioYoc.value = if (investedSum > 0 && annualSum > 0) (annualSum / investedSum) * 100.0 else null
            _loading.value = false
        }
    }
}

@Composable
fun ProventosScreen(onBack: () -> Unit, vm: ProventosViewModel = viewModel()) {
    val rows by vm.rows.collectAsState()
    val yoc by vm.yoc.collectAsState()
    val loading by vm.loading.collectAsState()
    val total by vm.total.collectAsState()
    val portfolioYoc by vm.portfolioYoc.collectAsState()
    val rendaMeta by vm.rendaMeta.collectAsState()
    val investedTotal by vm.investedTotal.collectAsState()
    LaunchedEffect(Unit) { vm.load() }
    val monthlyEst = total / 12.0
    val metaProgress = if (rendaMeta > 0) (monthlyEst / rendaMeta).toFloat().coerceIn(0f, 1.5f) else 0f

    PaperBackground {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text("Proventos e yield on cost", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }
            Text(
                "Yield on cost = proventos dos últimos 12 meses ÷ o que você pagou (preço médio). Diferente do DY de mercado, que usa a cotação de hoje.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(12.dp))
            SoftCard {
                Text("Proventos (12 meses)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(total.asCurrency(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = GreenPositive)
                Spacer(Modifier.height(6.dp))
                Text("Média mensal estimada: ${monthlyEst.asCurrency()}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                portfolioYoc?.let { y ->
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Yield on cost da carteira: ${String.format("%.2f", y)}% a.a.",
                        color = BluePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (investedTotal > 0) {
                    Text("Custo investido (base): ${investedTotal.asCurrency()}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
            }
            if (rendaMeta > 0) {
                Spacer(Modifier.height(10.dp))
                SoftCard {
                    Text("Meta de renda mensal: ${rendaMeta.asCurrency()}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { metaProgress.coerceAtMost(1f) },
                        modifier = Modifier.fillMaxWidth().height(10.dp),
                        color = if (monthlyEst >= rendaMeta) GreenPositive else BluePrimary,
                        trackColor = BlueLight
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        if (monthlyEst >= rendaMeta) "Você já cobre a meta com a média dos últimos 12 meses."
                        else "Faltam ${(rendaMeta - monthlyEst).asCurrency()}/mês em média de proventos.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text("Ajuste a meta em Ferramentas → Metas (patrimônio e renda).", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(Modifier.height(12.dp))
            if (loading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }
            } else {
                LazyColumn {
                    if (yoc.isNotEmpty()) {
                        item {
                            Text("Por ativo (yield on cost)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                            Spacer(Modifier.height(8.dp))
                        }
                        items(yoc) { r ->
                            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(r.ticker, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                    Text(
                                        r.yocPct?.let { String.format("%.2f%% a.a.", it) } ?: "—",
                                        color = BluePrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    "Proventos 12m ${r.annualProventos.asCurrency()} · Custo ${r.invested.asCurrency()}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        item {
                            Spacer(Modifier.height(12.dp))
                            Text("Histórico de proventos", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    if (rows.isEmpty()) {
                        item {
                            Text("Nenhum provento encontrado para os ativos da carteira nos últimos 12 meses.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    } else {
                        items(rows) { r ->
                            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                                Text("${r.ticker} · ${r.type}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                Text(
                                    "${r.valuePerShare.asCurrency()} × ${r.qty} = ${r.total.asCurrency()}",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale("pt", "BR")).format(java.util.Date(r.date)),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    item { Spacer(Modifier.height(40.dp)) }
                }
            }
        }
    }
}


/* ───────────────── Performance ───────────────── */

data class PerformanceMetrics(
    val invested: Double = 0.0,
    val current: Double = 0.0,
    val dividends: Double = 0.0,
    val simplePricePct: Double? = null,
    val totalReturnPct: Double? = null,
    val tirAnnualPct: Double? = null,
    val twrTotalPct: Double? = null,
    val twrAnnualPct: Double? = null,
    val years: Double = 0.0
)

class PerformanceViewModel : ViewModel() {
    private val portfolio = B3InsightsApp.instance.portfolioRepository
    private val market = B3InsightsApp.instance.marketDataRepository
    private val patrimonioDao = B3InsightsApp.instance.database.patrimonioHistoricoDao()

    private val _history = MutableStateFlow<List<Pair<Long, Double>>>(emptyList())
    val history: StateFlow<List<Pair<Long, Double>>> = _history.asStateFlow()
    private val _metrics = MutableStateFlow(PerformanceMetrics())
    val metrics: StateFlow<PerformanceMetrics> = _metrics.asStateFlow()
    private val _byClass = MutableStateFlow<List<Triple<String, Double, Double>>>(emptyList())
    val byClass: StateFlow<List<Triple<String, Double, Double>>> = _byClass.asStateFlow()
    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val allPositions = portfolio.getAllPositions().first()
                val lots = PortfolioCalculator.aggregate(allPositions)
                if (lots.isEmpty()) {
                    _metrics.value = PerformanceMetrics()
                    _byClass.value = emptyList()
                    _history.value = emptyList()
                    _loading.value = false
                    return@launch
                }
                var invested = 0.0
                var current = 0.0
                val classInv = mutableMapOf<String, Double>()
                val classCur = mutableMapOf<String, Double>()
                var usdBrl = 5.0
                when (val fx = market.getQuote("USDBRL=X")) {
                    is DataResult.Success -> fx.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
                    else -> Unit
                }

                val cashFlows = mutableListOf<PortfolioReturnCalculator.CashFlow>()
                var dividendsTotal = 0.0
                var firstBuyMs = Long.MAX_VALUE
                val now = System.currentTimeMillis()
                val yearAgo = now - 365L * 24 * 3600 * 1000
                val currentByTicker = mutableMapOf<String, Double>()

                // Fluxos: compras (-), vendas (+)
                for (p in allPositions) {
                    val d = if (p.purchaseDate > 0) p.purchaseDate else now
                    firstBuyMs = minOf(firstBuyMs, d)
                    val amt = p.quantity * (p.averagePrice.takeIf { it > 0 } ?: p.purchasePrice)
                    if (amt <= 0) continue
                    if (p.side.equals("SELL", true)) {
                        cashFlows += PortfolioReturnCalculator.CashFlow(d, amt)
                    } else {
                        cashFlows += PortfolioReturnCalculator.CashFlow(d, -amt)
                    }
                }

                for (lot in lots) {
                    val ticker = lot.ticker.uppercase()
                    val qty = lot.quantity
                    if (qty <= 0) continue
                    val cost = lot.invested
                    invested += cost
                    val type = lot.assetType.uppercase()
                    val isCrypto = type.contains("CRIPTO") || isCryptoTicker(ticker)
                    val label = when {
                        type.contains("FII") -> "FIIs"
                        type.contains("ETF") -> "ETFs"
                        type.contains("CRIPTO") -> "Cripto"
                        else -> "Ações"
                    }
                    classInv[label] = (classInv[label] ?: 0.0) + cost
                    val quoteSym = quoteTickerFor(lot.ticker, lot.assetType)
                    val rawPx = when (val q = market.getQuote(quoteSym)) {
                        is DataResult.Success -> q.data.currentPrice
                        else -> null
                    }
                    val px = when {
                        rawPx == null -> lot.averagePrice
                        isCrypto -> rawPx * usdBrl
                        else -> rawPx
                    }
                    val cur = qty * px
                    current += cur
                    currentByTicker[ticker] = cur
                    classCur[label] = (classCur[label] ?: 0.0) + cur

                    // Proventos 12m estimados (quantidade atual × eventos)
                    when (val d = market.getAssetDetail(ticker, ChartPeriod.Y1)) {
                        is DataResult.Success -> {
                            d.data.dividends.filter { it.exDate >= yearAgo }.forEach { div ->
                                val tot = div.valuePerShare * qty
                                dividendsTotal += tot
                                val divDate = if (div.exDate > 0) div.exDate else now
                                cashFlows += PortfolioReturnCalculator.CashFlow(divDate, tot)
                            }
                        }
                        else -> Unit
                    }
                }
                // Valor de mercado atual como entrada final (como se “vendesse” tudo)
                cashFlows += PortfolioReturnCalculator.CashFlow(now, current)

                if (firstBuyMs == Long.MAX_VALUE) firstBuyMs = now
                val years = PortfolioReturnCalculator.yearsBetween(firstBuyMs, now)

                val simplePrice = if (invested > 0) ((current - invested) / invested) else null
                val totalRet = if (invested > 0) ((current - invested + dividendsTotal) / invested) else null
                val tir = PortfolioReturnCalculator.xirr(cashFlows)

                // TWR via Modified Dietz + snapshots de patrimônio se existirem
                val snaps = runCatching { patrimonioDao.getHistorico().first() }.getOrDefault(emptyList())
                    .sortedBy { it.date }
                val twrTotal: Double? = if (snaps.size >= 2) {
                    val pts = snaps.map { it.date to it.totalValue }
                    val external = cashFlows.filter { it.amount < 0 } // só aportes
                    PortfolioReturnCalculator.twr(pts, external)
                } else {
                    PortfolioReturnCalculator.modifiedDietz(
                        beginValue = invested * 0.0, // início sem patrimônio = 0; usa Dietz a partir dos fluxos
                        endValue = current,
                        flows = cashFlows.filter { it.amount < 0 },
                        periodStartMs = firstBuyMs,
                        periodEndMs = now
                    )
                }
                // Modified Dietz com begin=0 distorce; se não há snaps, estima TWR ≈ total return composto sem peso de aporte grande
                val twrUse = if (snaps.size >= 2) twrTotal else {
                    // Fallback: Dietz com BMV = primeiro aporte
                    val firstAmt = cashFlows.filter { it.amount < 0 }.minByOrNull { it.dateMs }?.amount?.let { -it } ?: invested
                    PortfolioReturnCalculator.modifiedDietz(
                        beginValue = firstAmt,
                        endValue = current,
                        flows = cashFlows.filter { it.amount < 0 && it.dateMs > firstBuyMs },
                        periodStartMs = firstBuyMs,
                        periodEndMs = now
                    )
                }
                val twrAnnual = twrUse?.let {
                    val dec = it / 100.0
                    PortfolioReturnCalculator.annualizeCompound(dec, years)
                }

                _metrics.value = PerformanceMetrics(
                    invested = invested,
                    current = current,
                    dividends = dividendsTotal,
                    simplePricePct = simplePrice?.times(100.0),
                    totalReturnPct = totalRet?.times(100.0),
                    tirAnnualPct = tir,
                    twrTotalPct = twrUse,
                    twrAnnualPct = twrAnnual,
                    years = years
                )
                _byClass.value = classInv.keys.union(classCur.keys).map { k ->
                    Triple(k, classInv[k] ?: 0.0, classCur[k] ?: 0.0)
                }.sortedByDescending { it.third }

                // histórico
                val hist = snaps.map { it.date to it.totalValue }.toMutableList()
                if (hist.none { abs(it.first - now) < 60_000 }) {
                    hist += now to current
                    runCatching {
                        patrimonioDao.insert(
                            com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity(
                                date = now,
                                totalValue = current,
                                totalInvested = invested
                            )
                        )
                    }
                }
                _history.value = hist.sortedBy { it.first }
            } catch (e: Exception) {
                _error.value = e.message ?: "Erro ao calcular rentabilidade"
            }
            _loading.value = false
        }
    }
}

@Composable
fun PerformanceScreen(onBack: () -> Unit, vm: PerformanceViewModel = viewModel()) {
    val metrics by vm.metrics.collectAsState()
    val byClass by vm.byClass.collectAsState()
    val history by vm.history.collectAsState()
    val loading by vm.loading.collectAsState()
    val error by vm.error.collectAsState()
    LaunchedEffect(Unit) { vm.load() }

    @Composable
    fun pctColor(v: Double?) = when {
        v == null -> MaterialTheme.colorScheme.onSurfaceVariant
        v >= 0 -> GreenPositive
        else -> RedNegative
    }
    fun fmtPct(v: Double?) = v?.let { String.format("%+.2f%%", it) } ?: "—"

    PaperBackground {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text(
                    "Rentabilidade (TIR e TWR)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                "Não use só a variação do preço. Ativos que pagam dividendos/JCP precisam incluir o fluxo de caixa. Anualize com juros compostos, nunca dividindo o total pelos anos.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(12.dp))
            if (loading) {
                Box(Modifier.fillMaxWidth().height(120.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }
            } else {
                SoftCard {
                    Text("Investido", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(metrics.invested.asCurrency(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    Text("Valor atual", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(metrics.current.asCurrency(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    Text("Proventos estimados (12m)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(metrics.dividends.asCurrency(), fontWeight = FontWeight.Bold, color = GreenPositive)
                    if (metrics.years > 0) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Período ≈ ${String.format("%.1f", metrics.years)} ano(s)",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("TIR (taxa interna de retorno)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Ponderada pelo dinheiro: considera quando e quanto você aportou e os proventos. Ideal para ver o retorno do seu bolso.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        fmtPct(metrics.tirAnnualPct) + " a.a.",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = pctColor(metrics.tirAnnualPct)
                    )
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("TWR (retorno ponderado pelo tempo)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Remove o peso do timing dos aportes. Mostra a performance da carteira/ativo em si. Gestores e bancos usam TWR para comparar fundos.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Acumulado: ${fmtPct(metrics.twrTotalPct)}",
                        fontWeight = FontWeight.SemiBold,
                        color = pctColor(metrics.twrTotalPct)
                    )
                    Text(
                        "Anualizado: ${fmtPct(metrics.twrAnnualPct)} a.a.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = pctColor(metrics.twrAnnualPct)
                    )
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("Referências (não use sozinhas)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "Variação só de preço: ${fmtPct(metrics.simplePricePct)} — ignora dividendos (erro comum em FIIs).",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "Retorno total simples (preço + proventos ÷ investido): ${fmtPct(metrics.totalReturnPct)} — não anualiza nem pondera datas.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Fórmula de anualização (composta): (1 + R)^(1/anos) − 1. Nunca R ÷ anos.",
                        color = BluePrimary,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (byClass.isNotEmpty()) {
                    Spacer(Modifier.height(16.dp))
                    Text("Por classe", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    byClass.forEach { (name, inv, cur) ->
                        val p = if (inv > 0) ((cur - inv) / inv) * 100.0 else null
                        SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                            Text(name, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                            Text(
                                "Investido ${inv.asCurrency()} → Atual ${cur.asCurrency()}",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(fmtPct(p), color = pctColor(p), fontWeight = FontWeight.Medium)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("Histórico de patrimônio", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Snapshots ao abrir esta tela. Com o tempo o TWR fica mais preciso.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
                if (history.isEmpty()) {
                    SoftCard {
                        Text(
                            "Ainda sem histórico. Volte depois de movimentar a carteira.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    val max = history.maxOf { it.second }.coerceAtLeast(1.0)
                    history.takeLast(14).forEach { (date, value) ->
                        val frac = (value / max).toFloat().coerceIn(0.05f, 1f)
                        Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                java.text.SimpleDateFormat("dd/MM", java.util.Locale("pt", "BR")).format(java.util.Date(date)),
                                Modifier.width(48.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall
                            )
                            LinearProgressIndicator(
                                progress = { frac },
                                modifier = Modifier.weight(1f).height(10.dp),
                                color = BluePrimary,
                                trackColor = BlueLight
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(value.asCurrency(), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                }
            }
            error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = RedNegative)
            }
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = { vm.load() }, modifier = Modifier.fillMaxWidth()) {
                Text("Atualizar agora")
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

/* ───────────────── Alocação ───────────────── */

class AllocationViewModel : ViewModel() {
    private val settingsDao = B3InsightsApp.instance.database.userSettingsDao()
    private val portfolio = B3InsightsApp.instance.portfolioRepository
    private val market = B3InsightsApp.instance.marketDataRepository

    private val _settings = MutableStateFlow(UserSettingsEntity())
    val settings: StateFlow<UserSettingsEntity> = _settings.asStateFlow()

    private val _actualPct = MutableStateFlow<Map<String, Float>>(emptyMap())
    val actualPct: StateFlow<Map<String, Float>> = _actualPct.asStateFlow()

    private val _savedMsg = MutableStateFlow<String?>(null)
    val savedMsg: StateFlow<String?> = _savedMsg.asStateFlow()

    init {
        viewModelScope.launch {
            settingsDao.getSettings().collect { s ->
                _settings.value = s ?: UserSettingsEntity()
            }
        }
        viewModelScope.launch { refreshActual() }
    }

    fun refreshActual() {
        viewModelScope.launch {
            val positions = portfolio.getAllPositions().first()
            val values = mutableMapOf(
                "ACAO" to 0.0,
                "FII" to 0.0,
                "ETF" to 0.0,
                "CRIPTO" to 0.0,
                "OUTRO" to 0.0
            )
            val byTicker = positions.filter { it.side != "SELL" }.groupBy { it.ticker }
            for ((ticker, rows) in byTicker) {
                val qty = rows.sumOf { it.quantity }
                if (qty <= 0) continue
                val type = rows.first().assetType.uppercase()
                val key = when {
                    type.contains("FII") -> "FII"
                    type.contains("ETF") -> "ETF"
                    type.contains("CRIPTO") || ticker.contains("-USD") || ticker.contains("-BRL") -> "CRIPTO"
                    type.contains("ACAO") || type.contains("STOCK") -> "ACAO"
                    else -> "OUTRO"
                }
                val px = when (val q = market.getQuote(ticker)) {
                    is DataResult.Success -> q.data.currentPrice ?: rows.first().averagePrice
                    else -> rows.first().averagePrice
                }
                values[key] = (values[key] ?: 0.0) + qty * px
            }
            val total = values.values.sum().coerceAtLeast(0.0001)
            _actualPct.value = values.mapValues { ((it.value / total) * 100.0).toFloat() }
        }
    }

    fun save(s: UserSettingsEntity) {
        viewModelScope.launch {
            settingsDao.upsert(s.copy(id = 0))
            _settings.value = s.copy(id = 0)
            _savedMsg.value = "Metas salvas com sucesso"
            refreshActual()
            com.b3insights.app.sync.CloudSync.pushAfterLocalChange()
        }
    }

    fun clearMsg() { _savedMsg.value = null }
}

@Composable
fun AllocationScreen(
    actual: Map<String, Double> = emptyMap(),
    onBack: () -> Unit,
    vm: AllocationViewModel = viewModel()
) {
    val settings by vm.settings.collectAsState()
    val actualPct by vm.actualPct.collectAsState()
    val savedMsg by vm.savedMsg.collectAsState()
    var acoes by remember { mutableStateOf(settings.targetAcoesPct) }
    var fiis by remember { mutableStateOf(settings.targetFiisPct) }
    var etfs by remember { mutableStateOf(settings.targetEtfsPct) }
    var cripto by remember { mutableStateOf(settings.targetCriptoPct) }
    LaunchedEffect(settings) {
        acoes = settings.targetAcoesPct
        fiis = settings.targetFiisPct
        etfs = settings.targetEtfsPct
        cripto = settings.targetCriptoPct
    }
    LaunchedEffect(Unit) { vm.refreshActual() }

    PaperBackground {
        Column(Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text("Metas de alocação", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }
            Text(
                "Defina quanto % da carteira você quer em cada classe (ações, FIIs, ETFs, cripto). " +
                    "Isso não recomenda tickers — só mostra se a sua carteira está alinhada ao plano. " +
                    "Em oportunidades você pode desbalancear e depois reequilibrar com novos aportes.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(8.dp))
            SoftCard {
                Text("Dica de leitura", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Ações e FIIs costumam ser o núcleo de uma estratégia de renda. ETFs ajudam na diversificação (ex.: exterior). Cripto é satélite (menor %). Ajuste aos seus objetivos.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(Modifier.height(12.dp))
            AllocationSlider("Ações", acoes) { acoes = it }
            AllocationSlider("FIIs", fiis) { fiis = it }
            AllocationSlider("ETFs", etfs) { etfs = it }
            AllocationSlider("Cripto", cripto) { cripto = it }
            val sum = acoes + fiis + etfs + cripto
            Text("Soma dos alvos: ${sum.toInt()}%", color = if (sum in 95f..105f) GreenPositive else RedNegative)
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    vm.save(
                        settings.copy(
                            targetAcoesPct = acoes,
                            targetFiisPct = fiis,
                            targetEtfsPct = etfs,
                            targetCriptoPct = cripto
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                modifier = Modifier.fillMaxWidth()
            ) { Text("Salvar metas") }
            savedMsg?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = GreenPositive, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(24.dp))
            Text("Alocação atual vs meta", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(Modifier.height(8.dp))
            listOf(
                Triple("ACAO", "Ações", acoes),
                Triple("FII", "FIIs", fiis),
                Triple("ETF", "ETFs", etfs),
                Triple("CRIPTO", "Cripto", cripto)
            ).forEach { (key, label, target) ->
                val actual = actualPct[key] ?: 0f
                val delta = actual - target
                SoftCard(modifier = Modifier.padding(bottom = 10.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(label, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            "Atual ${"%.1f".format(actual)}% · Meta ${target.toInt()}%",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (actual / 100f).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth().height(10.dp),
                        color = BluePrimary,
                        trackColor = BlueLight
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        when {
                            kotlin.math.abs(delta) < 2f -> "Dentro da meta"
                            delta > 0 -> "Acima da meta em ${"%.1f".format(delta)} pp"
                            else -> "Abaixo da meta em ${"%.1f".format(-delta)} pp"
                        },
                        color = when {
                            kotlin.math.abs(delta) < 2f -> GreenPositive
                            else -> RedNegative
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun AllocationSlider(label: String, value: Float, onChange: (Float) -> Unit) {
    Text("$label: ${value.toInt()}%", color = MaterialTheme.colorScheme.onBackground)
    Slider(
        value = value,
        onValueChange = onChange,
        valueRange = 0f..100f,
        colors = SliderDefaults.colors(thumbColor = BluePrimary, activeTrackColor = BluePrimary)
    )
}

/* ───────────────── Simulador ───────────────── */

@Composable
fun SimulatorScreen(onBack: () -> Unit) {
    // Campos no estilo da calculadora Ward (vídeo Geração Dividendos ~min 7–12)
    var goalIncome by remember { mutableStateOf("10000") }      // renda passiva mensal desejada
    var initial by remember { mutableStateOf("0") }             // investimento inicial
    var monthly by remember { mutableStateOf("287") }           // aporte mensal
    var years by remember { mutableStateOf("35") }              // prazo em anos
    var monthlyRatePct by remember { mutableStateOf("0,85") }   // rentabilidade a.m. % (0,85% ≈ 10% a.a.)
    var annualYieldPct by remember { mutableStateOf("8") }      // DY da carteira % a.a. (renda passiva)
    var adjustContributionIpca by remember { mutableStateOf(false) }
    var ipcaPct by remember { mutableStateOf("4,5") }           // IPCA a.a. %
    var reinvestDividends by remember { mutableStateOf(true) }

    data class SimResult(
        val patrimonio: Double,
        val totalAportado: Double,
        val jurosGanhos: Double,
        val rendaPassivaMensal: Double,
        val pctMeta: Double?,
        val taxaAnualEfetiva: Double
    )

    fun parseNum(s: String): Double? =
        s.replace("%", "").replace(" ", "").replace(".", "").replace(",", ".").toDoubleOrNull()
            ?: s.replace("%", "").replace(" ", "").replace(",", ".").toDoubleOrNull()

    val result = remember(
        goalIncome, initial, monthly, years, monthlyRatePct,
        annualYieldPct, adjustContributionIpca, ipcaPct, reinvestDividends
    ) {
        val g = parseNum(goalIncome) ?: return@remember null
        val init = parseNum(initial) ?: 0.0
        val ap0 = parseNum(monthly) ?: return@remember null
        val y = years.toIntOrNull()?.coerceIn(1, 80) ?: return@remember null
        val rmPct = parseNum(monthlyRatePct) ?: return@remember null
        val rm = rmPct / 100.0
        val dyAnnual = (parseNum(annualYieldPct) ?: 8.0) / 100.0
        val ipca = (parseNum(ipcaPct) ?: 4.5) / 100.0
        val months = y * 12

        var balance = init
        var totalContrib = init
        var aporte = ap0
        for (m in 1..months) {
            // rendimento do mês sobre o saldo
            balance *= (1.0 + rm)
            // aporte do mês (opcionalmente corrigido pelo IPCA a cada 12 meses)
            if (adjustContributionIpca && m > 1 && (m - 1) % 12 == 0) {
                aporte *= (1.0 + ipca)
            }
            balance += aporte
            totalContrib += aporte
            // reinvestir dividendos: já embutido na rentabilidade mensal total;
            // se não reinvestir, tira DY/12 do saldo (aprox.)
            if (!reinvestDividends && dyAnnual > 0) {
                balance -= balance * (dyAnnual / 12.0)
            }
        }
        val rendaMensal = balance * (dyAnnual / 12.0)
        val pctMeta = if (g > 0) (rendaMensal / g) * 100.0 else null
        val taxaAnual = ((1.0 + rm).pow(12.0) - 1.0) * 100.0
        SimResult(
            patrimonio = balance,
            totalAportado = totalContrib,
            jurosGanhos = balance - totalContrib,
            rendaPassivaMensal = rendaMensal,
            pctMeta = pctMeta,
            taxaAnualEfetiva = taxaAnual
        )
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = BluePrimary)
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        "Simulador de aporte",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "Estilo Ward / Geração Dividendos — projeção educativa",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                SoftCard {
                    Text("1. Seu plano", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = goalIncome,
                        onValueChange = { goalIncome = it },
                        label = { Text("Renda passiva mensal desejada (R$)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = initial,
                        onValueChange = { initial = it },
                        label = { Text("Investimento inicial (R$)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Pode ser 0 se estiver começando do zero") }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = monthly,
                        onValueChange = { monthly = it },
                        label = { Text("Aporte mensal (R$)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Ex.: 10% da renda — Léo usa R$ 287 no exemplo") }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = years,
                        onValueChange = { years = it },
                        label = { Text("Prazo (anos)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Ex.: idade de aposentadoria − idade atual") }
                    )
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("2. Rentabilidade", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = monthlyRatePct,
                        onValueChange = { monthlyRatePct = it },
                        label = { Text("Rentabilidade a.m. (%)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = {
                            val a = parseNum(monthlyRatePct)?.let { ((1 + it / 100).pow(12) - 1) * 100 }
                            Text(
                                if (a != null) "≈ ${"%.1f".format(a)}% ao ano — Léo sugere ~0,85% a.m. para iniciantes"
                                else "Ex.: 0,85 (≈10% a.a.)"
                            )
                        }
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = annualYieldPct,
                        onValueChange = { annualYieldPct = it },
                        label = { Text("Dividend yield da carteira (% a.a.)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Usado para estimar a renda passiva mensal no final") }
                    )
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("3. Opções avançadas", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(4.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Reinvestir dividendos", color = MaterialTheme.colorScheme.onBackground)
                        Switch(checked = reinvestDividends, onCheckedChange = { reinvestDividends = it })
                    }
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Corrigir aporte pelo IPCA", color = MaterialTheme.colorScheme.onBackground)
                            Text(
                                "Aumenta o aporte 1× por ano com a inflação",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(checked = adjustContributionIpca, onCheckedChange = { adjustContributionIpca = it })
                    }
                    if (adjustContributionIpca) {
                        OutlinedTextField(
                            value = ipcaPct,
                            onValueChange = { ipcaPct = it },
                            label = { Text("IPCA estimado (% a.a.)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                SoftCard {
                    Text("Resultado da projeção", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    if (result == null) {
                        Text(
                            "Preencha os campos com números válidos.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        val r = result
                        ResultLine("Patrimônio estimado", r.patrimonio.asCurrency(), highlight = true)
                        ResultLine("Total aportado por você", r.totalAportado.asCurrency())
                        ResultLine("Juros / ganho de capital (aprox.)", r.jurosGanhos.asCurrency())
                        ResultLine(
                            "Renda passiva mensal estimada",
                            r.rendaPassivaMensal.asCurrency(),
                            highlight = true
                        )
                        r.pctMeta?.let { pct ->
                            ResultLine(
                                "% da meta de renda alcançada",
                                "%.1f%%".format(pct),
                                highlight = pct >= 100
                            )
                        }
                        ResultLine(
                            "Taxa efetiva usada",
                            "%.2f%% a.a. (composta de %.2f%% a.m.)".format(
                                r.taxaAnualEfetiva,
                                parseNum(monthlyRatePct) ?: 0.0
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Projeção educativa (juros compostos mensais). Não é recomendação de investimento. " +
                                "A renda passiva usa o DY informado sobre o patrimônio final.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ResultLine(label: String, value: String, highlight: Boolean = false) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        Text(
            value,
            fontWeight = FontWeight.Bold,
            color = if (highlight) GreenPositive else MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun ExportScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val portfolio = B3InsightsApp.instance.portfolioRepository
    var message by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    PaperBackground {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text("Exportar carteira", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }
            Text("Gera um CSV com ticker, quantidade, preço, data, lado, tags e notas.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        val rows = portfolio.getAllPositions().first()
                        val sb = StringBuilder("ticker,tipo,lado,quantidade,preco,data,tags,notas\n")
                        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale("pt", "BR"))
                        rows.forEach { p ->
                            sb.append("${p.ticker},${p.assetType},${p.side},${p.quantity},${p.purchasePrice},${sdf.format(java.util.Date(p.purchaseDate))},\"${p.tags.orEmpty()}\",\"${p.notes.orEmpty()}\"\n")
                        }
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/csv"
                            putExtra(Intent.EXTRA_SUBJECT, "Carteira B3Insights")
                            putExtra(Intent.EXTRA_TEXT, sb.toString())
                        }
                        context.startActivity(Intent.createChooser(intent, "Exportar carteira"))
                        message = "CSV pronto para compartilhar."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) { Text("Exportar CSV") }
            message?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = GreenPositive)
            }
        }
    }
}

/* ───────────────── Resumo diário ───────────────── */

data class DailyLine(
    val ticker: String,
    val qty: Double,
    val price: Double?,
    val changePct: Double?,
    val value: Double
)

data class DailySummaryState(
    val loading: Boolean = true,
    val totalValue: Double = 0.0,
    val totalInvested: Double = 0.0,
    val dayPnl: Double = 0.0,
    val dayPnlPct: Double = 0.0,
    val best: DailyLine? = null,
    val worst: DailyLine? = null,
    val lines: List<DailyLine> = emptyList(),
    val message: String? = null
)

class DailySummaryViewModel : ViewModel() {
    private val portfolio = B3InsightsApp.instance.portfolioRepository
    private val market = B3InsightsApp.instance.marketDataRepository
    private val _ui = MutableStateFlow(DailySummaryState())
    val ui: StateFlow<DailySummaryState> = _ui.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _ui.value = DailySummaryState(loading = true)
            val positions = portfolio.getAllPositions().first()
            if (positions.isEmpty()) {
                _ui.value = DailySummaryState(
                    loading = false,
                    message = "Adicione ativos em Meus ativos para ver o resumo."
                )
                return@launch
            }
            // Mesma lógica de Meus Ativos: cripto em USD × câmbio USD/BRL
            var usdBrl = 5.0
            when (val fx = market.getQuote("USDBRL=X")) {
                is DataResult.Success -> fx.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
                else -> Unit
            }
            val byTicker = positions.groupBy { it.ticker.uppercase() }
            val lines = mutableListOf<DailyLine>()
            var totalValue = 0.0
            var totalInvested = 0.0
            var dayPnl = 0.0
            for ((ticker, list) in byTicker) {
                val buyRows = list.filter { it.side != "SELL" }
                val sellQty = list.filter { it.side == "SELL" }.sumOf { it.quantity }
                val buyQty = buyRows.sumOf { it.quantity }
                val qty = (buyQty - sellQty).coerceAtLeast(0.0)
                if (qty <= 0) continue
                val invested = buyRows.sumOf { it.quantity * it.averagePrice }
                totalInvested += invested
                val assetType = list.firstOrNull()?.assetType
                val isCrypto = assetType == "CRIPTO" || isCryptoTicker(ticker)
                val quoteSymbol = quoteTickerFor(ticker, assetType)
                val quote = when (val r = market.getQuote(quoteSymbol)) {
                    is DataResult.Success -> r.data
                    else -> null
                }
                val rawPx = quote?.currentPrice
                val chg = quote?.dayChangePercent
                // preço unitário em R$ (cripto: USD * câmbio)
                val priceBrl = when {
                    rawPx == null -> list.first().averagePrice
                    isCrypto -> rawPx * usdBrl
                    else -> rawPx
                }
                val value = priceBrl * qty
                totalValue += value
                if (rawPx != null && chg != null) {
                    val prevUsd = rawPx / (1 + chg / 100.0)
                    val prevBrl = if (isCrypto) prevUsd * usdBrl else prevUsd
                    dayPnl += (priceBrl - prevBrl) * qty
                }
                val label = when {
                    ticker == "BTC" || ticker.startsWith("BTC-") -> "BTC · Bitcoin"
                    ticker == "ETH" || ticker.startsWith("ETH-") -> "ETH · Ethereum"
                    ticker == "SOL" || ticker.startsWith("SOL-") -> "SOL · Solana"
                    else -> ticker
                }
                lines.add(DailyLine(label, qty, priceBrl, chg, value))
            }
            val best = lines.filter { it.changePct != null }.maxByOrNull { it.changePct!! }
            val worst = lines.filter { it.changePct != null }.minByOrNull { it.changePct!! }
            val dayPct = if (totalValue - dayPnl != 0.0) (dayPnl / (totalValue - dayPnl)) * 100.0 else 0.0
            _ui.value = DailySummaryState(
                loading = false,
                totalValue = totalValue,
                totalInvested = totalInvested,
                dayPnl = dayPnl,
                dayPnlPct = dayPct,
                best = best,
                worst = worst,
                lines = lines.sortedByDescending { it.value }
            )
        }
    }
}

@Composable
fun DailySummaryScreen(onBack: () -> Unit, vm: DailySummaryViewModel = viewModel()) {
    val ui by vm.ui.collectAsState()
    LaunchedEffect(Unit) { vm.load() }
    PaperBackground {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary)
                }
                Text(
                    "Resumo diário",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(Modifier.height(12.dp))
            if (ui.loading) {
                Box(Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }
            } else if (ui.message != null) {
                SoftCard { Text(ui.message!!, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            } else {
                SoftCard {
                    Text("Patrimônio hoje", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelMedium)
                    Text(ui.totalValue.asCurrency(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    val pnlColor = if (ui.dayPnl >= 0) GreenPositive else RedNegative
                    Text(
                        "Variação do dia: ${ui.dayPnl.asCurrency()} (${String.format("%+.2f", ui.dayPnlPct)}%)",
                        color = pnlColor,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text("Total investido: ${ui.totalInvested.asCurrency()}", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                }
                Spacer(Modifier.height(12.dp))
                ui.best?.let { b ->
                    SoftCard {
                        Text("Maior alta", fontWeight = FontWeight.SemiBold, color = GreenPositive)
                        Text("${b.ticker}  ${String.format("%+.2f", b.changePct ?: 0.0)}%", color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(Modifier.height(8.dp))
                }
                ui.worst?.let { w ->
                    SoftCard {
                        Text("Maior baixa", fontWeight = FontWeight.SemiBold, color = RedNegative)
                        Text("${w.ticker}  ${String.format("%+.2f", w.changePct ?: 0.0)}%", color = MaterialTheme.colorScheme.onBackground)
                    }
                    Spacer(Modifier.height(12.dp))
                }
                Text("Posições", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(8.dp))
                ui.lines.forEach { line ->
                    SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(line.ticker, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                Text("Qtd ${formatToolQty(line.qty)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(line.value.asCurrency(), fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                                val ch = line.changePct
                                if (ch != null) {
                                    Text(String.format("%+.2f%%", ch), color = if (ch >= 0) GreenPositive else RedNegative, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/* ───────────────── Meta de patrimônio ───────────────── */

class PatrimonioGoalViewModel : ViewModel() {
    private val settingsDao = B3InsightsApp.instance.database.userSettingsDao()
    private val portfolio = B3InsightsApp.instance.portfolioRepository
    private val market = B3InsightsApp.instance.marketDataRepository
    private val _target = MutableStateFlow(0.0)
    val target: StateFlow<Double> = _target.asStateFlow()
    private val _renda = MutableStateFlow(0.0)
    val renda: StateFlow<Double> = _renda.asStateFlow()
    private val _current = MutableStateFlow(0.0)
    val current: StateFlow<Double> = _current.asStateFlow()
    private val _msg = MutableStateFlow<String?>(null)
    val msg: StateFlow<String?> = _msg.asStateFlow()

    init {
        viewModelScope.launch {
            settingsDao.getSettings().collect { s ->
                _target.value = s?.targetPatrimonio ?: 0.0
                _renda.value = s?.targetRendaMensal ?: 0.0
            }
        }
        refreshCurrent()
    }

    fun refreshCurrent() {
        viewModelScope.launch {
            val positions = portfolio.getAllPositions().first().filter { it.side != "SELL" }
            if (positions.isEmpty()) {
                _current.value = 0.0
                return@launch
            }
            // Mesma lógica da tela Meus Ativos (quoteTickerFor + cripto USD→BRL)
            var usdBrl = 5.0
            when (val fx = market.getQuote("USDBRL=X")) {
                is DataResult.Success -> fx.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
                else -> Unit
            }
            val byTicker = positions.groupBy { it.ticker.uppercase() }
            var total = 0.0
            for ((ticker, rows) in byTicker) {
                val qty = rows.sumOf { it.quantity }
                if (qty <= 0) continue
                val type = rows.first().assetType
                val isCrypto = type.equals("CRIPTO", true) || isCryptoTicker(ticker)
                val quoteSym = quoteTickerFor(ticker, type)
                val rawPx = when (val q = market.getQuote(quoteSym)) {
                    is DataResult.Success -> q.data.currentPrice
                    else -> null
                }
                val pxBrl = when {
                    rawPx == null -> rows.first().averagePrice
                    isCrypto -> rawPx * usdBrl
                    else -> rawPx
                }
                total += qty * pxBrl
            }
            _current.value = total
        }
    }

    fun saveTarget(value: Double) {
        viewModelScope.launch {
            val cur = settingsDao.getSettingsOnce() ?: UserSettingsEntity()
            settingsDao.upsert(cur.copy(targetPatrimonio = value))
            _target.value = value
            _msg.value = "Meta de patrimônio salva"
            com.b3insights.app.sync.CloudSync.pushAfterLocalChange()
        }
    }

    fun saveRenda(value: Double) {
        viewModelScope.launch {
            val cur = settingsDao.getSettingsOnce() ?: UserSettingsEntity()
            settingsDao.upsert(cur.copy(targetRendaMensal = value))
            _renda.value = value
            _msg.value = "Meta de renda mensal salva"
            com.b3insights.app.sync.CloudSync.pushAfterLocalChange()
        }
    }
}

@Composable
fun PatrimonioGoalScreen(onBack: () -> Unit, vm: PatrimonioGoalViewModel = viewModel()) {
    val target by vm.target.collectAsState()
    val renda by vm.renda.collectAsState()
    val current by vm.current.collectAsState()
    val msg by vm.msg.collectAsState()
    var input by remember { mutableStateOf(if (target > 0) target.toLong().toString() else "") }
    var inputRenda by remember { mutableStateOf(if (renda > 0) renda.toLong().toString() else "") }
    LaunchedEffect(target) {
        if (target > 0) input = target.toLong().toString()
    }
    LaunchedEffect(renda) {
        if (renda > 0) inputRenda = renda.toLong().toString()
    }
    val progress = if (target > 0) (current / target).toFloat().coerceIn(0f, 1f) else 0f
    val missing = (target - current).coerceAtLeast(0.0)

    PaperBackground {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = TextPrimary) }
                Text(
                    "Metas (patrimônio e renda)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Text(
                "Duas metas diferentes: patrimônio é o valor total da carteira; renda mensal é quanto você quer receber em proventos por mês (acompanhada em Proventos e yield on cost).",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.height(16.dp))
            SoftCard {
                Text("Patrimônio atual", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(current.asCurrency(), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(12.dp))
                if (target > 0) {
                    Text("Meta de patrimônio: ${target.asCurrency()}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().height(10.dp),
                        color = BluePrimary,
                        trackColor = BlueLight
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (current >= target) "Parabéns! Você atingiu a meta de patrimônio."
                        else "Faltam ${missing.asCurrency()} (${String.format("%.1f", progress * 100)}%)",
                        color = if (current >= target) GreenPositive else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text("Defina uma meta de patrimônio abaixo.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.height(16.dp))
            SoftCard {
                Text("1) Meta de patrimônio", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text("Quanto você quer que a carteira valha no total.", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it.filter { c -> c.isDigit() || c == '.' || c == ',' } },
                    label = { Text("Meta de patrimônio (R$)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        val v = input.replace(',', '.').toDoubleOrNull() ?: 0.0
                        vm.saveTarget(v)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) { Text("Salvar meta de patrimônio") }
            }
            Spacer(Modifier.height(16.dp))
            SoftCard {
                Text("2) Meta de renda mensal (proventos)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Quanto você quer receber por mês em dividendos/rendimentos. O progresso usa a média dos proventos dos últimos 12 meses (veja em Proventos e yield on cost).",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.height(8.dp))
                if (renda > 0) {
                    Text("Meta atual: ${renda.asCurrency()}/mês", color = BluePrimary, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.height(8.dp))
                }
                OutlinedTextField(
                    value = inputRenda,
                    onValueChange = { inputRenda = it.filter { c -> c.isDigit() || c == '.' || c == ',' } },
                    label = { Text("Meta de renda mensal (R$)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        val v = inputRenda.replace(',', '.').toDoubleOrNull() ?: 0.0
                        vm.saveRenda(v)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) { Text("Salvar meta de renda") }
            }
            msg?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = GreenPositive)
            }
            Spacer(Modifier.height(40.dp))
        }
    }
}


private fun formatToolQty(q: Double): String {
    return when {
        q == 0.0 -> "0"
        q >= 1 -> String.format(java.util.Locale.US, "%.4f", q).trimEnd('0').trimEnd('.')
        else -> String.format(java.util.Locale.US, "%.8f", q).trimEnd('0').trimEnd('.')
    }
}
