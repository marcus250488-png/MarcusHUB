package com.b3insights.app.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.data.model.PricePoint
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.util.asCurrency
import com.b3insights.app.viewmodel.PortfolioItemUi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

private data class SeriesPoint(val timestamp: Long, val values: Map<String, Double>)

private data class AllocationSlice(
    val label: String,
    val value: Double,
    val rentability: Double?,
    val color: Int
)

@Composable
fun PortfolioAnalyticsSection(items: List<PortfolioItemUi>) {
    if (items.isEmpty()) return

    var loading by remember { mutableStateOf(true) }
    var evolution by remember { mutableStateOf<List<SeriesPoint>>(emptyList()) }
    var allocation by remember { mutableStateOf<List<AllocationSlice>>(emptyList()) }
    var rentability by remember { mutableStateOf<List<SeriesPoint>>(emptyList()) }

    LaunchedEffect(items) {
        loading = true
        withContext(Dispatchers.IO) {
            val built = buildAnalytics(items)
            evolution = built.first
            allocation = built.second
            rentability = built.third
        }
        loading = false
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "Análises da carteira",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Evolução, alocação e comparação com benchmarks",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (loading) {
            SoftCard {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BluePrimary)
                }
            }
        } else {
        SoftCard {
            Text("Evolução patrimonial", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                "Patrimônio atualizado x valor investido",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            MultiLineChart(
                series = evolution,
                keys = listOf("Patrimônio" to AndroidColor.parseColor("#1D4ED8"), "Valor Investido" to AndroidColor.parseColor("#0EA5E9"),),
                valueAsMoney = true
            )
            ChartLegend(
                listOf(
                    Color(0xFF1D4ED8) to "Patrimônio",
                    Color(0xFF0EA5E9) to "Valor Investido"
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SoftCard {
            Text("Alocação por classe de ativo", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                "Distribuição do patrimônio por classe",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            AllocationDonut(allocation)
            Spacer(modifier = Modifier.height(8.dp))
            val allocTotal = allocation.sumOf { it.value }.coerceAtLeast(0.01)
            allocation.forEach { slice ->
                val pct = 100.0 * slice.value / allocTotal
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1.2f)) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(slice.color))
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Column {
                            Text(slice.label, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.bodyMedium)
                            Text("%.1f%%".format(pct), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Text(
                        slice.value.asCurrency(),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        slice.rentability?.let { "%+.2f%%".format(it) } ?: "—",
                        color = when {
                            slice.rentability == null -> MaterialTheme.colorScheme.onSurfaceVariant
                            slice.rentability >= 0 -> Color(0xFF1E9E5A)
                            else -> Color(0xFFE23D3D)
                        },
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SoftCard {
            Text("Rentabilidade", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                "Carteira × CDI · Ibovespa · IFIX · IVVB11",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            MultiLineChart(
                series = rentability,
                keys = listOf(
                    "Carteira" to AndroidColor.parseColor("#1D4ED8"),
                    "CDI" to AndroidColor.parseColor("#0EA5E9"),
                    "Ibovespa" to AndroidColor.parseColor("#16A34A"),
                    "IFIX" to AndroidColor.parseColor("#CA8A04"),
                    "IVVB11" to AndroidColor.parseColor("#7C3AED"),
                ),
                valueAsMoney = false
            )
            ChartLegend(
                listOf(
                    Color(0xFF1D4ED8) to "Carteira",
                    Color(0xFF0EA5E9) to "CDI",
                    Color(0xFF16A34A) to "Ibovespa",
                    Color(0xFFCA8A04) to "IFIX",
                    Color(0xFF7C3AED) to "IVVB11"
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        } // else loading
    }
}

@Composable
private fun ChartLegend(items: List<Pair<Color, String>>) {
    Column(modifier = Modifier.padding(top = 6.dp)) {
        items.chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { (c, label) ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(c))
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun AllocationDonut(slices: List<AllocationSlice>) {
    if (slices.isEmpty()) {
        EmptyState("Sem dados de alocação")
        return
    }
    var selected by remember { mutableStateOf<AllocationSlice?>(null) }
    val slicesRef = remember { mutableStateOf(slices) }
    androidx.compose.runtime.SideEffect { slicesRef.value = slices }
    val total = slices.sumOf { it.value }.coerceAtLeast(0.01)

    Column {
        if (selected != null) {
            val pct = 100.0 * selected!!.value / total
            Box(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(selected!!.label, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    Text("%.2f%%".format(pct), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
                    Text(selected!!.value.asCurrency(), style = MaterialTheme.typography.bodyMedium, color = Color(0xFF6B7280))
                }
            }
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(220.dp),
            factory = { ctx ->
                PieChart(ctx).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setUsePercentValues(true)
                    setDrawEntryLabels(false)
                    // Pizza cheia — sem furo escuro no centro
                    holeRadius = 0f
                    transparentCircleRadius = 0f
                    setDrawHoleEnabled(false)
                    setTouchEnabled(true)
                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            if (e !is PieEntry) {
                                selected = null
                                return
                            }
                            val s = slicesRef.value
                            selected = s.find { it.label == e.label } ?: s.getOrNull(h?.x?.toInt() ?: -1)
                        }
                        override fun onNothingSelected() {
                            selected = null
                        }
                    })
                }
            },
            update = { chart ->
                val entries = slices.map { PieEntry(it.value.toFloat().coerceAtLeast(0.01f), it.label) }
                val dataSet = PieDataSet(entries, "").apply {
                    colors = slices.map { it.color }
                    sliceSpace = 2f
                    setDrawValues(false)
                    selectionShift = 6f
                }
                chart.data = PieData(dataSet)
                chart.setDrawHoleEnabled(false)
                chart.invalidate()
            }
        )
    }
}

@Composable
private fun MultiLineChart(
    series: List<SeriesPoint>,
    keys: List<Pair<String, Int>>,
    valueAsMoney: Boolean
) {
    if (series.isEmpty()) {
        EmptyState("Dados insuficientes para o gráfico")
        return
    }
    var selected by remember { mutableStateOf<SeriesPoint?>(null) }
    val seriesRef = remember { mutableStateOf(series) }
    androidx.compose.runtime.SideEffect { seriesRef.value = series }
    val dateFmt = remember { SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")) }

    Column {
        if (selected != null) {
            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp), contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(dateFmt.format(Date(selected!!.timestamp)), style = MaterialTheme.typography.bodySmall, color = Color(0xFF6B7280))
                    keys.forEach { (name, color) ->
                        val v = selected!!.values[name] ?: return@forEach
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(name, color = Color(color), style = MaterialTheme.typography.bodySmall)
                            Text(
                                if (valueAsMoney) v.asCurrency() else "%+.2f%%".format(v),
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF111827)
                            )
                        }
                    }
                }
            }
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth().height(220.dp),
            factory = { ctx ->
                LineChart(ctx).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setTouchEnabled(true)
                    setPinchZoom(false)
                    setScaleEnabled(false)
                    setHighlightPerTapEnabled(true)
                    axisRight.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    xAxis.textColor = AndroidColor.parseColor("#6B7280")
                    xAxis.textSize = 9f
                    axisLeft.setDrawGridLines(true)
                    axisLeft.gridColor = AndroidColor.parseColor("#EAECF0")
                    axisLeft.textColor = AndroidColor.parseColor("#6B7280")
                    setExtraOffsets(4f, 8f, 4f, 4f)
                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            if (e == null) { selected = null; return }
                            val s = seriesRef.value
                            if (s.isEmpty()) { selected = null; return }
                            selected = s[e.x.toInt().coerceIn(0, s.lastIndex)]
                        }
                        override fun onNothingSelected() { selected = null }
                    })
                }
            },
            update = { chart ->
                val dataSets = keys.mapNotNull { (name, color) ->
                    val entries = series.mapIndexedNotNull { i, sp ->
                        val v = sp.values[name] ?: return@mapIndexedNotNull null
                        Entry(i.toFloat(), v.toFloat())
                    }
                    if (entries.isEmpty()) return@mapNotNull null
                    LineDataSet(entries, name).apply {
                        this.color = color
                        lineWidth = 2f
                        setDrawCircles(false)
                        setDrawValues(false)
                        setDrawFilled(name == "Patrimônio" || name == "Carteira")
                        setFillColor(color)
                        fillAlpha = 40
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        highLightColor = AndroidColor.parseColor("#9CA3AF")
                        setDrawHorizontalHighlightIndicator(false)
                        enableDashedHighlightLine(8f, 6f, 0f)
                        if (name == "CDI") enableDashedLine(10f, 8f, 0f)
                    }
                }
                chart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val s = seriesRef.value
                        if (s.isEmpty()) return ""
                        val idx = value.toInt().coerceIn(0, s.lastIndex)
                        return SimpleDateFormat("dd/MM", Locale("pt", "BR")).format(Date(s[idx].timestamp))
                    }
                }
                chart.data = LineData(dataSets)
                chart.invalidate()
            }
        )
    }
}

private suspend fun buildAnalytics(items: List<PortfolioItemUi>): Triple<List<SeriesPoint>, List<AllocationSlice>, List<SeriesPoint>> {
    val repo = B3InsightsApp.instance.marketDataRepository
    val colors = listOf(
        AndroidColor.parseColor("#0EA5E9"),
        AndroidColor.parseColor("#CA8A04"),
        AndroidColor.parseColor("#16A34A"),
        AndroidColor.parseColor("#7C3AED"),
        AndroidColor.parseColor("#E11D48")
    )

    // --- Alocação ---
    val byClass = linkedMapOf(
        "Ação" to (0.0 to 0.0),
        "FII" to (0.0 to 0.0),
        "ETF" to (0.0 to 0.0),
        "Cripto" to (0.0 to 0.0),
        "Outros" to (0.0 to 0.0)
    )
    items.forEach { it ->
        val key = when (it.assetType.uppercase()) {
            "ACAO" -> "Ação"
            "FII" -> "FII"
            "ETF" -> "ETF"
            "CRIPTO" -> "Cripto"
            else -> "Outros"
        }
        val cur = it.currentValue ?: it.totalInvested
        val inv = it.totalInvested
        val prev = byClass[key] ?: (0.0 to 0.0)
        byClass[key] = (prev.first + cur) to (prev.second + inv)
    }
    val allocation = byClass.entries
        .filter { it.value.first > 0.0001 }
        .mapIndexed { idx, e ->
            val cur = e.value.first
            val inv = e.value.second
            val rent = if (inv > 0) ((cur - inv) / inv) * 100.0 else null
            AllocationSlice(e.key, cur, rent, colors[idx % colors.size])
        }

    // --- Evolução: pontos por data de compra ---
    val purchases = items.flatMap { item ->
        item.positions.map { pos ->
            val d = if (pos.purchaseDate > 0) pos.purchaseDate else System.currentTimeMillis()
            Triple(d, pos.quantity * pos.averagePrice, item)
        }
    }.sortedBy { it.first }

    val totalInvestedNow = items.sumOf { it.totalInvested }.coerceAtLeast(0.01)
    val totalCurrentNow = items.sumOf { it.currentValue ?: it.totalInvested }
    val plFactor = totalCurrentNow / totalInvestedNow

    val evo = mutableListOf<SeriesPoint>()
    if (purchases.isNotEmpty()) {
        var cumInvested = 0.0
        val start = purchases.first().first
        // ponto zero um dia antes
        evo.add(SeriesPoint(start - 86_400_000L, mapOf("Patrimônio" to 0.0, "Valor Investido" to 0.0)))
        purchases.forEach { (ts, cost, _) ->
            cumInvested += cost
            // patrimônio estimado proporcional ao PL atual (simplificação)
            evo.add(
                SeriesPoint(
                    ts,
                    mapOf(
                        "Valor Investido" to cumInvested,
                        "Patrimônio" to cumInvested * plFactor
                    )
                )
            )
        }
        // ponto de hoje
        evo.add(
            SeriesPoint(
                System.currentTimeMillis(),
                mapOf(
                    "Valor Investido" to totalInvestedNow,
                    "Patrimônio" to totalCurrentNow
                )
            )
        )
    }

    // --- Rentabilidade vs benchmarks ---
    val period = ChartPeriod.M3
    suspend fun history(symbol: String): List<PricePoint> =
        when (val r = repo.getAssetDetail(symbol, period)) {
            is DataResult.Success -> r.data.priceHistory
            else -> emptyList()
        }

    suspend fun historyAny(vararg symbols: String): List<PricePoint> {
        for (s in symbols) {
            val h = history(s)
            if (h.isNotEmpty()) return h
        }
        return emptyList()
    }

    val ibov = historyAny("^BVSP", "BOVA11", "BOVA11.SA")
    // IFIX: índice e proxies líquidos (XFIX11 / carteira de FIIs se índice falhar)
    var ifix = historyAny("IFIX11", "XFIX11", "IFIX.SA", "^IFIX")
    if (ifix.isEmpty()) {
        // Proxy: média de FIIs líquidos (comportamento próximo ao IFIX)
        val fiiSyms = listOf("HGLG11", "XPLG11", "KNRI11", "VISC11", "MXRF11", "BTLG11")
        val series = fiiSyms.map { history(it) }.filter { it.size >= 5 }
        if (series.isNotEmpty()) {
            val minLen = series.minOf { it.size }
            ifix = (0 until minLen).map { i ->
                val ts = series.map { it[it.size - minLen + i].timestamp }.average().toLong()
                val close = series.map { it[it.size - minLen + i].close }.average()
                PricePoint(ts, close)
            }
        }
    }
    val ivvb = historyAny("IVVB11", "IVVB11.SA", "SPXI11")

    fun toCumulativeReturn(pts: List<PricePoint>): List<Pair<Long, Double>> {
        if (pts.isEmpty()) return emptyList()
        val base = pts.first().close.takeIf { it > 0 } ?: return emptyList()
        return pts.map { it.timestamp to ((it.close / base) - 1.0) * 100.0 }
    }

    fun valueAt(rets: List<Pair<Long, Double>>, ts: Long): Double? {
        if (rets.isEmpty()) return null
        return rets.minByOrNull { kotlin.math.abs(it.first - ts) }?.second
    }

    val ibovRet = toCumulativeReturn(ibov)
    val ifixRet = toCumulativeReturn(ifix)
    val ivvbRet = toCumulativeReturn(ivvb)

    // CDI ~ 14% a.a. / 252 pregões
    val cdiDaily = 0.14 / 252.0
    val portfolioPct = ((totalCurrentNow - totalInvestedNow) / totalInvestedNow) * 100.0

    // Eixo temporal comum (prioriza Ibovespa; senão IFIX/IVVB)
    val timeline: List<Long> = when {
        ibovRet.isNotEmpty() -> ibovRet.map { it.first }
        ifixRet.isNotEmpty() -> ifixRet.map { it.first }
        ivvbRet.isNotEmpty() -> ivvbRet.map { it.first }
        else -> {
            val cal = Calendar.getInstance()
            (0 until 60).map { i ->
                cal.timeInMillis = System.currentTimeMillis()
                cal.add(Calendar.DAY_OF_YEAR, -(59 - i))
                cal.timeInMillis
            }
        }
    }

    val rentPoints = timeline.mapIndexed { i, ts ->
        val tFrac = if (timeline.size <= 1) 1.0 else i.toDouble() / (timeline.size - 1)
        val map = mutableMapOf(
            "Carteira" to portfolioPct * tFrac,
            "CDI" to ((Math.pow(1.0 + cdiDaily, i.toDouble()) - 1.0) * 100.0)
        )
        valueAt(ibovRet, ts)?.let { map["Ibovespa"] = it }
        valueAt(ifixRet, ts)?.let { map["IFIX"] = it }
        valueAt(ivvbRet, ts)?.let { map["IVVB11"] = it }
        SeriesPoint(ts, map)
    }

    return Triple(evo, allocation, rentPoints)
}
