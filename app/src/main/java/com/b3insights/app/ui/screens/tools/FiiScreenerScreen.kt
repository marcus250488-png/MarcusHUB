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
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.model.FiiCatalog
import com.b3insights.app.data.model.FiiCatalogEntry
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.ui.theme.GreenPositive
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
 * Planilha de FIIs no espírito do Fundamentus + screening do Léo.
 *
 * 1) Mostra TODOS os fundos do Fundamentus (569+)
 * 2) Usuário aplica filtros numa tela dedicada (liquidez, P/VP, DY, vacância, segmento)
 * 3) Presets: Ancoragem / Oportunidade / Papel — só quando escolhidos
 * 4) Filtros só valem de verdade quando o usuário toca em "Buscar"
 *
 * Colunas alinhadas ao Fundamentus:
 * Papel | Segmento | Cotação | DY | P/VP | Liquidez | Vacância
 */
data class FiiRow(
    val entry: FiiCatalogEntry,
    val price: Double? = null,
    val dy: Double? = null,
    val pvp: Double? = null,
    val liquidity: Double? = null,
    val vacancy: Double? = null
)

data class FiiFilterState(
    val segment: String = "Todos",
    val minDy: Float = 0f,
    val maxPvp: Float = 3f,
    val minPvp: Float = 0f,
    val minLiquidity: Float = 0f,
    val maxVacancy: Float = 100f,
    val preset: String = "Nenhum",
    val sortBy: String = "ticker" // ticker | dy | pvp | liq
)

/** Estado de filtro pronto para cada preset — usado tanto pela ViewModel quanto pela prévia da tela de filtros. */
fun presetFilterState(name: String): FiiFilterState = when (name) {
    // Léo: liquidez alta + P/VP elevado (ancoragem)
    "Ancoragem" -> FiiFilterState(
        segment = "Todos",
        minDy = 6f,
        minPvp = 0.95f,
        maxPvp = 1.35f,
        minLiquidity = 500_000f,
        maxVacancy = 15f,
        preset = "Ancoragem",
        sortBy = "liq"
    )
    // Léo: P/VP ~0,85–0,95 + liquidez (oportunidade / desconto)
    "Oportunidade" -> FiiFilterState(
        segment = "Todos",
        minDy = 7f,
        minPvp = 0.80f,
        maxPvp = 0.98f,
        minLiquidity = 300_000f,
        maxVacancy = 20f,
        preset = "Oportunidade",
        sortBy = "pvp"
    )
    "Papel renda" -> FiiFilterState(
        segment = "Papel",
        minDy = 10f,
        minPvp = 0.85f,
        maxPvp = 1.15f,
        minLiquidity = 200_000f,
        maxVacancy = 100f,
        preset = "Papel renda",
        sortBy = "dy"
    )
    else -> FiiFilterState(preset = "Nenhum") // volta aos 569
}

/** Filtro rigoroso: se o usuário mexeu no slider, aplica; métrica ausente só passa se o limite estiver no “sem filtro”. */
fun filterRows(rows: List<FiiRow>, f: FiiFilterState): List<FiiRow> {
    val strictDy = f.minDy > 0.01f
    val strictPvp = f.minPvp > 0.01f || f.maxPvp < 2.99f
    val strictLiq = f.minLiquidity > 1f
    val strictVac = f.maxVacancy < 99.9f

    var list = rows.filter { row ->
        val segOk = f.segment == "Todos" || row.entry.segment == f.segment
        if (!segOk) return@filter false

        val dy = row.dy ?: row.entry.dy12m
        val pvp = row.pvp ?: row.entry.pvp
        val liq = row.liquidity ?: row.entry.liquidityBrl
        val vac = row.vacancy ?: row.entry.vacancyPct

        if (strictDy) {
            if (dy == null) return@filter false
            if (dy < f.minDy) return@filter false
        }
        if (strictPvp) {
            if (pvp == null) return@filter false
            if (pvp < f.minPvp || pvp > f.maxPvp) return@filter false
        }
        if (strictLiq) {
            if (liq == null) return@filter false
            if (liq < f.minLiquidity) return@filter false
        }
        if (strictVac) {
            if (vac == null) return@filter false
            if (vac > f.maxVacancy) return@filter false
        }
        true
    }

    list = when (f.sortBy) {
        "dy" -> list.sortedByDescending { it.dy ?: -1.0 }
        "pvp" -> list.sortedBy { it.pvp ?: 999.0 }
        "liq" -> list.sortedByDescending { it.liquidity ?: -1.0 }
        else -> list.sortedBy { it.entry.ticker }
    }
    return list
}

class FiiScreenerViewModel : ViewModel() {
    private val market = B3InsightsApp.instance.marketDataRepository
    private val _rows = MutableStateFlow<List<FiiRow>>(emptyList())
    val rows: StateFlow<List<FiiRow>> = _rows.asStateFlow()
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    private val _filter = MutableStateFlow(FiiFilterState())
    val filter: StateFlow<FiiFilterState> = _filter.asStateFlow()

    init {
        // Planilha completa do Fundamentus — sem filtro aplicado
        _rows.value = FiiCatalog.all.map {
            FiiRow(
                entry = it,
                price = it.lastPrice,
                dy = it.dy12m,
                pvp = it.pvp,
                liquidity = it.liquidityBrl,
                vacancy = it.vacancyPct
            )
        }
    }

    fun updateFilter(block: (FiiFilterState) -> FiiFilterState) {
        _filter.value = block(_filter.value)
    }

    fun setFilter(newFilter: FiiFilterState) {
        _filter.value = newFilter
    }

    fun applyPreset(name: String) {
        _filter.value = presetFilterState(name)
    }

    fun filtered(): List<FiiRow> = filterRows(_rows.value, _filter.value)

    fun refreshQuotes() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val base = FiiCatalog.all
                // atualiza preço ao vivo só dos mais líquidos (top 100 por liquidez do snapshot)
                val top = base.sortedByDescending { it.liquidityBrl ?: 0.0 }.take(100)
                val quotes = coroutineScope {
                    top.map { e ->
                        async {
                            val sym = quoteTickerFor(e.ticker, "FII")
                            val px = when (val q = market.getQuote(sym)) {
                                is DataResult.Success -> q.data.currentPrice
                                else -> null
                            }
                            e.ticker to px
                        }
                    }.awaitAll().toMap()
                }
                _rows.value = base.map { e ->
                    FiiRow(
                        entry = e,
                        price = quotes[e.ticker] ?: e.lastPrice,
                        dy = e.dy12m,
                        pvp = e.pvp,
                        liquidity = e.liquidityBrl,
                        vacancy = e.vacancyPct
                    )
                }
            } finally {
                _loading.value = false
            }
        }
    }
}

private fun fmtLiq(v: Double?): String {
    if (v == null) return "—"
    return when {
        v >= 1_000_000 -> "%.1fM".format(v / 1_000_000)
        v >= 1_000 -> "%.0fK".format(v / 1_000)
        else -> "%.0f".format(v)
    }
}

@Composable
fun FiiScreenerScreen(
    onBack: () -> Unit,
    onAssetClick: (String) -> Unit,
    vm: FiiScreenerViewModel = viewModel()
) {
    val filter by vm.filter.collectAsState()
    val loading by vm.loading.collectAsState()
    val allRows by vm.rows.collectAsState()
    val list = remember(filter, allRows) { vm.filtered() }
    var showFilters by remember { mutableStateOf(false) }

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
                        "Planilha de FIIs",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "Fundamentus · ${list.size} de ${FiiCatalog.all.size} fundos",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { vm.refreshQuotes() }) {
                    Icon(Icons.Filled.Refresh, "Atualizar preços", tint = BluePrimary)
                }
                IconButton(onClick = { showFilters = true }) {
                    Icon(Icons.Filled.FilterList, "Filtros", tint = BluePrimary)
                }
            }

            Text(
                "Começa com todos os FIIs. Toque no filtro para escolher liquidez → P/VP → DY → vacância.",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (loading) {
                LinearProgressIndicator(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            // Cabeçalho estilo Fundamentus
            val hScroll = rememberScrollState()
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(hScroll)
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeaderCell("Papel", 96.dp)
                HeaderCell("Segmento", 88.dp)
                HeaderCell("Cotação", 72.dp)
                HeaderCell("DY", 52.dp)
                HeaderCell("P/VP", 52.dp)
                HeaderCell("Liquidez", 72.dp)
                HeaderCell("Vacância", 64.dp)
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            LazyColumn(
                Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(list, key = { it.entry.ticker }) { row ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .horizontalScroll(hScroll)
                            .clickable { onAssetClick(row.entry.ticker) }
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(Modifier.width(96.dp), verticalAlignment = Alignment.CenterVertically) {
                            AssetLogo(row.entry.ticker, size = 26.dp)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                row.entry.ticker,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Text(
                            row.entry.segment,
                            Modifier.width(88.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                        Text(
                            row.price?.asCurrency() ?: "—",
                            Modifier.width(72.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            row.dy?.let { "%.1f%%".format(it) } ?: "—",
                            Modifier.width(52.dp),
                            fontSize = 11.sp,
                            color = if ((row.dy ?: 0.0) >= 10) GreenPositive else MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            row.pvp?.let { "%.2f".format(it) } ?: "—",
                            Modifier.width(52.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            fmtLiq(row.liquidity),
                            Modifier.width(72.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            row.vacancy?.let { "%.1f%%".format(it) } ?: "—",
                            Modifier.width(64.dp),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onBackground
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

    if (showFilters) {
        FiiFilterScreen(
            initial = filter,
            totalCount = FiiCatalog.all.size,
            previewCount = { draft -> filterRows(allRows, draft).size },
            onApply = { draft ->
                vm.setFilter(draft)
                showFilters = false
            },
            onDismiss = { showFilters = false }
        )
    }
}

@Composable
private fun HeaderCell(text: String, width: androidx.compose.ui.unit.Dp) {
    Text(
        text,
        Modifier.width(width),
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

// ---------------------------------------------------------------------------
// Tela de filtros — abre em tela cheia. Os ajustes ficam num rascunho local;
// só valem de verdade quando o usuário toca em "Buscar N fundos".
// ---------------------------------------------------------------------------

private data class FiiPresetInfo(
    val key: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector
)

private val FII_PRESETS = listOf(
    FiiPresetInfo("Nenhum", "Todos", "Sem filtro", Icons.Filled.Apps),
    FiiPresetInfo("Ancoragem", "Ancoragem", "Liquidez alta, P/VP justo", Icons.Filled.Bolt),
    FiiPresetInfo("Oportunidade", "Oportunidade", "Desconto no P/VP", Icons.Filled.LocalFireDepartment),
    FiiPresetInfo("Papel renda", "Papel renda", "DY alto em CRIs", Icons.Filled.Payments)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FiiFilterScreen(
    initial: FiiFilterState,
    totalCount: Int,
    previewCount: (FiiFilterState) -> Int,
    onApply: (FiiFilterState) -> Unit,
    onDismiss: () -> Unit
) {
    var draft by remember { mutableStateOf(initial) }
    val matchCount = remember(draft) { previewCount(draft) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars)
            ) {
                // Barra superior
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, "Fechar", tint = MaterialTheme.colorScheme.onBackground)
                    }
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Filtrar fundos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            "$matchCount de $totalCount fundos encontrados",
                            style = MaterialTheme.typography.bodySmall,
                            color = BluePrimary
                        )
                    }
                    TextButton(onClick = { draft = FiiFilterState() }) {
                        Icon(Icons.Filled.RestartAlt, null, tint = BluePrimary, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Limpar", color = BluePrimary, fontSize = 13.sp)
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Column(
                    Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {
                    // Presets do Léo
                    Column {
                        Text(
                            "Presets do Léo",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(
                            Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            FII_PRESETS.forEach { p ->
                                PresetCard(
                                    icon = p.icon,
                                    title = p.title,
                                    subtitle = p.subtitle,
                                    selected = draft.preset == p.key,
                                    onClick = {
                                        draft = if (p.key == "Nenhum") FiiFilterState() else presetFilterState(p.key)
                                    }
                                )
                            }
                        }
                    }

                    // Segmento
                    Column {
                        Text(
                            "Segmento",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(
                            Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            (listOf("Todos") + FiiCatalog.segments).forEach { s ->
                                FilterChip(
                                    selected = draft.segment == s,
                                    onClick = { draft = draft.copy(segment = s, preset = "Nenhum") },
                                    label = { Text(s, fontSize = 12.sp) }
                                )
                            }
                        }
                    }

                    FilterSection(
                        icon = Icons.Filled.TrendingUp,
                        title = "Dividend Yield mínimo",
                        valueLabel = "${"%.0f".format(draft.minDy)}%"
                    ) {
                        Slider(
                            value = draft.minDy,
                            onValueChange = { v -> draft = draft.copy(minDy = v, preset = "Nenhum") },
                            valueRange = 0f..25f
                        )
                    }

                    FilterSection(
                        icon = Icons.Filled.CompareArrows,
                        title = "P/VP (preço sobre valor patrimonial)",
                        valueLabel = "${"%.2f".format(draft.minPvp)} – ${"%.2f".format(draft.maxPvp)}"
                    ) {
                        RangeSlider(
                            value = draft.minPvp..draft.maxPvp,
                            onValueChange = { range ->
                                draft = draft.copy(
                                    minPvp = range.start,
                                    maxPvp = range.endInclusive,
                                    preset = "Nenhum"
                                )
                            },
                            valueRange = 0f..3f
                        )
                    }

                    FilterSection(
                        icon = Icons.Filled.WaterDrop,
                        title = "Liquidez diária mínima",
                        valueLabel = "R$ ${fmtLiq(draft.minLiquidity.toDouble())}"
                    ) {
                        Slider(
                            value = draft.minLiquidity,
                            onValueChange = { v -> draft = draft.copy(minLiquidity = v, preset = "Nenhum") },
                            valueRange = 0f..2_000_000f
                        )
                    }

                    FilterSection(
                        icon = Icons.Filled.Business,
                        title = "Vacância máxima",
                        valueLabel = "${"%.0f".format(draft.maxVacancy)}%"
                    ) {
                        Slider(
                            value = draft.maxVacancy,
                            onValueChange = { v -> draft = draft.copy(maxVacancy = v, preset = "Nenhum") },
                            valueRange = 0f..100f
                        )
                    }

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Sort, null, tint = BluePrimary, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Ordenar por",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(
                            Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(
                                "ticker" to "A–Z",
                                "dy" to "Maior DY",
                                "pvp" to "Menor P/VP",
                                "liq" to "Liquidez"
                            ).forEach { (k, label) ->
                                FilterChip(
                                    selected = draft.sortBy == k,
                                    onClick = { draft = draft.copy(sortBy = k) },
                                    label = { Text(label, fontSize = 12.sp) }
                                )
                            }
                        }
                    }
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Row(Modifier.fillMaxWidth().padding(16.dp)) {
                    Button(
                        onClick = { onApply(draft) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BluePrimary,
                            contentColor = Color.White
                        ),
                        enabled = matchCount > 0
                    ) {
                        Icon(Icons.Filled.Search, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (matchCount > 0) "Buscar $matchCount fundos" else "Nenhum fundo com esses filtros",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PresetCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) BluePrimary else MaterialTheme.colorScheme.surface
    val fg = if (selected) Color.White else MaterialTheme.colorScheme.onBackground
    val sub = if (selected) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
    Column(
        modifier = Modifier
            .width(136.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Icon(icon, null, tint = fg, modifier = Modifier.size(20.dp))
        Spacer(Modifier.height(10.dp))
        Text(title, color = fg, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Spacer(Modifier.height(2.dp))
        Text(subtitle, color = sub, fontSize = 10.sp, maxLines = 2)
    }
}

@Composable
private fun FilterSection(
    icon: ImageVector,
    title: String,
    valueLabel: String,
    content: @Composable ColumnScope.() -> Unit
) {
    SoftCard {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = BluePrimary, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f)
            )
            Text(valueLabel, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = BluePrimary)
        }
        Spacer(Modifier.height(2.dp))
        content()
    }
}
