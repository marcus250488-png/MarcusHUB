package com.b3insights.app.ui.screens.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.model.StockCatalog
import com.b3insights.app.data.model.StockCatalogEntry
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.ui.theme.GreenPositive
import com.b3insights.app.ui.theme.RedNegative
import com.b3insights.app.util.Valuation
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.quoteTickerFor
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Radar de valuation multimétodo (Graham, Bazin, Lynch, Barsi, DCF).
 * Mesmo espírito da planilha de FIIs: lista ampla + filtros + transparência.
 * Não recomenda compra — só calcula e mostra desconto/prêmio vs cotação.
 */

data class MethodValue(
    val name: String,
    val estimate: Double?,
    val discountPct: Double? // (est - price)/price * 100; + = desconto
)

data class StockValRow(
    val entry: StockCatalogEntry,
    val price: Double? = null,
    val pl: Double? = null,
    val pvp: Double? = null,
    val dy: Double? = null,
    val roe: Double? = null,
    val graham: Double? = null,
    val bazin: Double? = null,
    val lynch: Double? = null,
    val barsi: Double? = null,
    val dcf: Double? = null,
    val loaded: Boolean = false
) {
    fun discount(est: Double?): Double? = Valuation.discountVsPrice(est, price)
    fun methodsAbovePrice(): Int {
        val p = price ?: return 0
        return listOfNotNull(graham, bazin, lynch, barsi, dcf).count { it > p }
    }
}

data class StockValFilter(
    val query: String = "",
    val sector: String = "Todos",
    /** Método em destaque na planilha (coluna com cor diferente) e base da ordenação. */
    val highlightMethod: String = "nenhum", // nenhum|graham|bazin|lynch|barsi|dcf
    val minGrahamDiscount: Float = 0f,
    val minBazinDiscount: Float = 0f,
    val minLynchDiscount: Float = 0f,
    val minDcfDiscount: Float = 0f,
    val minMethodsAbove: Int = 0,
    val sortBy: String = "ticker"
)

class StockValuationViewModel : ViewModel() {
    private val market = B3InsightsApp.instance.marketDataRepository
    private val _rows = MutableStateFlow<List<StockValRow>>(emptyList())
    val rows: StateFlow<List<StockValRow>> = _rows.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    private val _filter = MutableStateFlow(StockValFilter())
    val filter: StateFlow<StockValFilter> = _filter.asStateFlow()
    private val _draft = MutableStateFlow(StockValFilter())
    val draft: StateFlow<StockValFilter> = _draft.asStateFlow()

    init {
        _rows.value = StockCatalog.all.map { StockValRow(it) }
        loadBatch()
    }

    fun updateDraft(block: (StockValFilter) -> StockValFilter) {
        _draft.value = block(_draft.value)
    }

    fun applyDraft() {
        _filter.value = _draft.value
        loadForCurrentFilter()
    }

    fun resetDraft() {
        _draft.value = StockValFilter()
        _filter.value = StockValFilter()
    }

    fun openDraftFromCurrent() {
        _draft.value = _filter.value
    }

    fun filtered(): List<StockValRow> {
        val f = _filter.value
        var list = _rows.value.filter { row ->
            val q = f.query.trim().uppercase()
            val qOk = q.isEmpty() ||
                row.entry.ticker.contains(q) ||
                row.entry.name.uppercase().contains(q)
            val secOk = f.sector == "Todos" || row.entry.sector == f.sector
            if (!qOk || !secOk) return@filter false

            fun okDisc(min: Float, est: Double?): Boolean {
                if (min <= 0.1f) return true
                val d = row.discount(est) ?: return false
                return d >= min
            }
            if (!okDisc(f.minGrahamDiscount, row.graham)) return@filter false
            if (!okDisc(f.minBazinDiscount, row.bazin)) return@filter false
            if (!okDisc(f.minLynchDiscount, row.lynch)) return@filter false
            if (!okDisc(f.minDcfDiscount, row.dcf)) return@filter false
            if (f.minMethodsAbove > 0 && row.methodsAbovePrice() < f.minMethodsAbove) return@filter false
            true
        }
        val sortKey = if (f.highlightMethod != "nenhum") f.highlightMethod else f.sortBy
        list = when (sortKey) {
            "graham" -> list.sortedByDescending { it.discount(it.graham) ?: -999.0 }
            "bazin" -> list.sortedByDescending { it.discount(it.bazin) ?: -999.0 }
            "lynch" -> list.sortedByDescending { it.discount(it.lynch) ?: -999.0 }
            "barsi" -> list.sortedByDescending { it.discount(it.barsi) ?: -999.0 }
            "dcf" -> list.sortedByDescending { it.discount(it.dcf) ?: -999.0 }
            "conv" -> list.sortedByDescending { it.methodsAbovePrice() }
            "price" -> list.sortedBy { it.price ?: Double.MAX_VALUE }
            else -> list.sortedBy { it.entry.ticker }
        }
        return list
    }

    /**
     * Não dispara 100+ requests ao abrir.
     * Só carrega valuation do setor (ou da busca) quando o usuário aplica o filtro.
     */
    fun loadBatch() {
        // lista vazia de preços — a planilha já mostra os tickers
        if (_rows.value.isEmpty()) {
            _rows.value = StockCatalog.all.map { StockValRow(it) }
        }
    }

    /** Carrega cotação + fundamentos só dos tickers visíveis (setor/busca). */
    fun loadForCurrentFilter() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val f = _filter.value
                val targets = StockCatalog.all.filter { e ->
                    val secOk = f.sector == "Todos" || e.sector == f.sector
                    val q = f.query.trim().uppercase()
                    val qOk = q.isEmpty() || e.ticker.contains(q) || e.name.uppercase().contains(q)
                    secOk && qOk
                }
                // Se "Todos" sem busca, limita a 40 para não matar a rede
                val slice = if (f.sector == "Todos" && f.query.isBlank()) {
                    targets.take(40)
                } else {
                    targets
                }
                val detailed = coroutineScope {
                    slice.map { entry ->
                        async { computeRow(entry) }
                    }.awaitAll()
                }
                val byTicker = detailed.associateBy { it.entry.ticker }
                _rows.value = _rows.value.map { row ->
                    byTicker[row.entry.ticker] ?: row
                }
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun computeRow(entry: StockCatalogEntry): StockValRow {
        val sym = quoteTickerFor(entry.ticker, "ACAO")
        var price: Double? = null
        var pl: Double? = null
        var pvp: Double? = null
        var dy: Double? = null
        var lpa: Double? = null
        var vpa: Double? = null
        var profitGrowth: Double? = null
        var revenueGrowth: Double? = null
        var annualDps: Double? = null

        when (val q = market.getQuote(sym)) {
            is DataResult.Success -> {
                price = q.data.currentPrice
            }
            else -> Unit
        }
        when (val d = market.getAssetDetail(entry.ticker, ChartPeriod.Y1)) {
            is DataResult.Success -> {
                val ind = d.data.indicators
                if (price == null) price = d.data.quote.currentPrice
                pl = ind.pl
                pvp = ind.pvp
                dy = ind.dividendYield
                lpa = ind.lpa
                vpa = ind.vpa
                profitGrowth = ind.profitGrowth
                revenueGrowth = ind.revenueGrowth
                // DPS LTM aproximado pela soma de proventos 12m se houver
                val yearAgo = System.currentTimeMillis() - 365L * 24 * 3600 * 1000
                val divs = d.data.dividends.filter { it.exDate >= yearAgo }
                if (divs.isNotEmpty()) {
                    annualDps = divs.sumOf { it.valuePerShare }
                }
            }
            else -> Unit
        }

        val graham = Valuation.grahamPrice(lpa, vpa, price, pl, pvp)
        val bazin = Valuation.bazinCeiling(price, dy, annualDps)
        val lynch = Valuation.lynchEstimate(lpa, price, pl, profitGrowth, revenueGrowth)
        val barsi = Valuation.barsiEstimate(price, dy, annualDps)
        val dcf = Valuation.dcfEstimate(lpa, price, pl, profitGrowth)

        return StockValRow(
            entry = entry,
            price = price,
            pl = pl,
            pvp = pvp,
            dy = dy,
            graham = graham,
            bazin = bazin,
            lynch = lynch,
            barsi = barsi,
            dcf = dcf,
            loaded = true
        )
    }
}

@Composable
fun StockValuationScreen(
    onBack: () -> Unit,
    onAssetClick: (String) -> Unit,
    vm: StockValuationViewModel = viewModel()
) {
    val filter by vm.filter.collectAsState()
    val loading by vm.loading.collectAsState()
    val allRows by vm.rows.collectAsState()
    val list = remember(filter, allRows) { vm.filtered() }
    var showFilters by remember { mutableStateOf(false) }
    var detailRow by remember { mutableStateOf<StockValRow?>(null) }

    if (showFilters) {
        StockValFilterScreen(
            vm = vm,
            onBack = { showFilters = false },
            onApply = {
                vm.applyDraft()
                showFilters = false
            }
        )
        return
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = BluePrimary)
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        "Valuation de Ações",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "Radar multimétodo · ${list.size} de ${StockCatalog.all.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { vm.loadBatch() }) {
                    Icon(Icons.Filled.Refresh, "Atualizar", tint = BluePrimary)
                }
                IconButton(onClick = {
                    vm.openDraftFromCurrent()
                    showFilters = true
                }) {
                    Icon(Icons.Filled.FilterList, "Filtros", tint = BluePrimary)
                }
            }

            Text(
                "Abra Filtros → escolha setor + método → Aplicar. A coluna do método fica destacada. Não é recomendação de compra.",
                Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Busca rápida
            var localQuery by remember { mutableStateOf("") }
            OutlinedTextField(
                value = localQuery,
                onValueChange = { q ->
                    localQuery = q
                    vm.updateDraft { it.copy(query = q) }
                    vm.applyDraft()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                placeholder = { Text("Buscar ticker ou nome") },
                leadingIcon = { Icon(Icons.Filled.Search, null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            if (loading) {
                LinearProgressIndicator(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            val hScroll = rememberScrollState()
            val hi = filter.highlightMethod
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(hScroll)
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Header("Ticker", 88.dp, false)
                Header("Setor", 72.dp, false)
                Header("Preço", 64.dp, false)
                Header("Graham", 72.dp, hi == "graham")
                Header("Bazin", 72.dp, hi == "bazin")
                Header("Lynch", 72.dp, hi == "lynch")
                Header("Barsi", 72.dp, hi == "barsi")
                Header("DCF", 72.dp, hi == "dcf")
                Header("Conv.", 48.dp, hi == "conv")
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 32.dp)) {
                items(list, key = { it.entry.ticker }) { row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .horizontalScroll(hScroll)
                            .clickable {
                                detailRow = row
                            }
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(Modifier.width(88.dp), verticalAlignment = Alignment.CenterVertically) {
                            AssetLogo(row.entry.ticker, size = 26.dp)
                            Spacer(Modifier.width(4.dp))
                            Text(
                                row.entry.ticker,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Text(row.entry.sector, Modifier.width(72.dp), fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                        Text(row.price?.asCurrency() ?: "—", Modifier.width(64.dp), fontSize = 11.sp, color = MaterialTheme.colorScheme.onBackground)
                        EstCell(row.graham, row.discount(row.graham), 72.dp, hi == "graham")
                        EstCell(row.bazin, row.discount(row.bazin), 72.dp, hi == "bazin")
                        EstCell(row.lynch, row.discount(row.lynch), 72.dp, hi == "lynch")
                        EstCell(row.barsi, row.discount(row.barsi), 72.dp, hi == "barsi")
                        EstCell(row.dcf, row.discount(row.dcf), 72.dp, hi == "dcf")
                        Text(
                            "${row.methodsAbovePrice()}/5",
                            Modifier.width(48.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (row.methodsAbovePrice() >= 3) GreenPositive else MaterialTheme.colorScheme.onBackground
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }

    detailRow?.let { row ->
        StockValDetailSheet(
            row = row,
            onDismiss = { detailRow = null },
            onOpenAsset = {
                detailRow = null
                onAssetClick(row.entry.ticker)
            }
        )
    }
}

@Composable
private fun Header(text: String, width: androidx.compose.ui.unit.Dp, highlight: Boolean) {
    val bg = if (highlight) BluePrimary.copy(alpha = 0.25f) else Color.Transparent
    Text(
        text,
        Modifier
            .width(width)
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(vertical = 2.dp, horizontal = 2.dp),
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = if (highlight) BluePrimary else MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun EstCell(est: Double?, disc: Double?, width: androidx.compose.ui.unit.Dp, highlight: Boolean) {
    val bg = if (highlight) BluePrimary.copy(alpha = 0.18f) else Color.Transparent
    Column(
        Modifier
            .width(width)
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(vertical = 2.dp, horizontal = 2.dp)
    ) {
        Text(
            est?.asCurrency() ?: "—",
            fontSize = 10.sp,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (disc != null) {
            val color = if (disc >= 0) GreenPositive else RedNegative
            Text(
                "%+.1f%%".format(disc),
                fontSize = 9.sp,
                fontWeight = if (highlight) FontWeight.SemiBold else FontWeight.Normal,
                color = color
            )
        }
    }
}

@Composable
private fun StockValFilterScreen(
    vm: StockValuationViewModel,
    onBack: () -> Unit,
    onApply: () -> Unit
) {
    val draft by vm.draft.collectAsState()
    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, null, tint = BluePrimary)
                }
                Text(
                    "Filtros de valuation",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }
            Column(
                Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text("1. Setor", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(6.dp))
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    (listOf("Todos") + StockCatalog.sectors).forEach { s ->
                        FilterChip(
                            selected = draft.sector == s,
                            onClick = { vm.updateDraft { it.copy(sector = s) } },
                            label = { Text(s, fontSize = 11.sp) }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("2. Método em destaque (ordena e color a coluna)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Escolha um método. Na planilha a coluna dele fica azul e a lista ordena por esse critério. Os outros métodos continuam aparecendo.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(6.dp))
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        "nenhum" to "Nenhum",
                        "graham" to "Graham",
                        "bazin" to "Bazin",
                        "lynch" to "Lynch",
                        "barsi" to "Barsi",
                        "dcf" to "DCF"
                    ).forEach { (k, label) ->
                        FilterChip(
                            selected = draft.highlightMethod == k,
                            onClick = {
                                vm.updateDraft {
                                    it.copy(
                                        highlightMethod = k,
                                        sortBy = if (k == "nenhum") "ticker" else k
                                    )
                                }
                            },
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("3. Filtros avançados (opcional)", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text("Desconto mínimo por método", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                Text(
                    "Positivo = cotação abaixo da estimativa. 0 = sem filtro.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DiscSlider("Graham ≥ ${"%.0f".format(draft.minGrahamDiscount)}%", draft.minGrahamDiscount) {
                    vm.updateDraft { d -> d.copy(minGrahamDiscount = it) }
                }
                DiscSlider("Bazin ≥ ${"%.0f".format(draft.minBazinDiscount)}%", draft.minBazinDiscount) {
                    vm.updateDraft { d -> d.copy(minBazinDiscount = it) }
                }
                DiscSlider("Lynch ≥ ${"%.0f".format(draft.minLynchDiscount)}%", draft.minLynchDiscount) {
                    vm.updateDraft { d -> d.copy(minLynchDiscount = it) }
                }
                DiscSlider("DCF ≥ ${"%.0f".format(draft.minDcfDiscount)}%", draft.minDcfDiscount) {
                    vm.updateDraft { d -> d.copy(minDcfDiscount = it) }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Convergência: pelo menos ${draft.minMethodsAbove} métodos com estimativa acima da cotação",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Slider(
                    value = draft.minMethodsAbove.toFloat(),
                    onValueChange = { vm.updateDraft { d -> d.copy(minMethodsAbove = it.toInt()) } },
                    valueRange = 0f..5f,
                    steps = 4
                )
                Spacer(Modifier.height(12.dp))
                Text("Ordenar por", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        "ticker" to "A–Z",
                        "graham" to "Desc. Graham",
                        "bazin" to "Desc. Bazin",
                        "lynch" to "Desc. Lynch",
                        "dcf" to "Desc. DCF",
                        "conv" to "Convergência",
                        "price" to "Preço"
                    ).forEach { (k, label) ->
                        FilterChip(
                            selected = draft.sortBy == k,
                            onClick = { vm.updateDraft { it.copy(sortBy = k) } },
                            label = { Text(label, fontSize = 11.sp) }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                TextButton(onClick = { vm.resetDraft() }) {
                    Text("Limpar filtros")
                }
            }
            Button(
                onClick = onApply,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Aplicar, carregar dados e ver planilha", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun DiscSlider(label: String, value: Float, onChange: (Float) -> Unit) {
    Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Slider(value = value, onValueChange = onChange, valueRange = 0f..40f)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockValDetailSheet(
    row: StockValRow,
    onDismiss: () -> Unit,
    onOpenAsset: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(row.entry.ticker, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(row.entry.name, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Setor: ${row.entry.sector}", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(8.dp))
            Text("Preço atual: ${row.price?.asCurrency() ?: "—"}", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
            Text("Valuation", fontWeight = FontWeight.Bold)
            MethodLine("Graham", row.graham, row.discount(row.graham), "√(22,5 × LPA × VPA) — régua clássica")
            MethodLine("Bazin", row.bazin, row.discount(row.bazin), "DPS 12m ÷ 0,06 (yield-alvo 6%)")
            MethodLine("Peter Lynch", row.lynch, row.discount(row.lynch), "LPA × crescimento % (PEG≈1) — estimativa")
            MethodLine("Barsi (renda)", row.barsi, row.discount(row.barsi), "DPS ÷ yield-alvo — critérios de dividendos")
            MethodLine("DCF simplificado", row.dcf, row.discount(row.dcf), "Gordon com LPA como proxy de fluxo")
            Spacer(Modifier.height(8.dp))
            Text(
                "Convergência: ${row.methodsAbovePrice()} de 5 métodos com estimativa acima da cotação.",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Isso não é recomendação de compra. Use como apoio à análise.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Button(onClick = onOpenAsset, modifier = Modifier.fillMaxWidth()) {
                Text("Abrir página do ativo")
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MethodLine(name: String, est: Double?, disc: Double?, note: String) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(name, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text(est?.asCurrency() ?: "—", fontWeight = FontWeight.Medium)
            if (disc != null) {
                Spacer(Modifier.width(8.dp))
                Text(
                    "%+.1f%%".format(disc),
                    color = if (disc >= 0) GreenPositive else RedNegative,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Text(note, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
