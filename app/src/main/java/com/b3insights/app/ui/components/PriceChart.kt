package com.b3insights.app.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.b3insights.app.data.model.PricePoint
import com.b3insights.app.util.asCurrency
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Gráfico de preço com tooltip ao toque: data do ponto + valor.
 * Versão estável (sem split de datasets / sem highlight reentrante).
 */
@Composable
fun PriceChart(points: List<PricePoint>, modifier: Modifier = Modifier) {
    if (points.isEmpty()) {
        EmptyState("Dado não disponível")
        return
    }

    val isPositive = points.last().close >= points.first().close
    val lineColor = if (isPositive) AndroidColor.parseColor("#1E9E5A") else AndroidColor.parseColor("#E23D3D")
    var selected by remember { mutableStateOf<PricePoint?>(null) }
    val pointsRef = remember { mutableStateOf(points) }
    SideEffect { pointsRef.value = points }
    val dateFmt = remember { SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")) }

    androidx.compose.runtime.LaunchedEffect(points) {
        selected = null
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (selected != null) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .shadow(4.dp, RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1F2937))
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Data: ${dateFmt.format(Date(selected!!.timestamp))}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF9CA3AF)
                    )
                    Text(
                        selected!!.close.asCurrency(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            factory = { context ->
                LineChart(context).apply {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setTouchEnabled(true)
                    setPinchZoom(true)
                    isDragEnabled = true
                    setScaleEnabled(true)
                    setHighlightPerTapEnabled(true)
                    setHighlightPerDragEnabled(true)
                    axisRight.isEnabled = false
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    xAxis.textColor = AndroidColor.parseColor("#6B7280")
                    axisLeft.setDrawGridLines(true)
                    axisLeft.gridColor = AndroidColor.parseColor("#2A2F3A")
                    axisLeft.textColor = AndroidColor.parseColor("#6B7280")
                    setExtraOffsets(4f, 8f, 4f, 4f)
                    setNoDataText("Dado não disponível")
                    setBackgroundColor(AndroidColor.TRANSPARENT)
                    setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                        override fun onValueSelected(e: Entry?, h: Highlight?) {
                            if (e == null) {
                                selected = null
                                return
                            }
                            val pts = pointsRef.value
                            if (pts.isEmpty()) {
                                selected = null
                                return
                            }
                            val idx = e.x.toInt().coerceIn(0, pts.lastIndex)
                            selected = pts[idx]
                        }

                        override fun onNothingSelected() {
                            selected = null
                        }
                    })
                }
            },
            update = { chart ->
                val entries = points.mapIndexed { index, point ->
                    Entry(index.toFloat(), point.close.toFloat())
                }
                val dataSet = LineDataSet(entries, "Preço").apply {
                    color = lineColor
                    setDrawCircles(false)
                    lineWidth = 2f
                    setDrawFilled(true)
                    setFillColor(lineColor)
                    fillAlpha = 60
                    setDrawValues(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    highLightColor = AndroidColor.parseColor("#9CA3AF")
                    setDrawHorizontalHighlightIndicator(false)
                    setDrawVerticalHighlightIndicator(true)
                    highlightLineWidth = 1.2f
                    enableDashedHighlightLine(8f, 6f, 0f)
                }
                chart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val pts = pointsRef.value
                        if (pts.isEmpty()) return ""
                        val index = value.toInt().coerceIn(0, pts.size - 1)
                        val sdf = SimpleDateFormat("dd/MM", Locale("pt", "BR"))
                        return sdf.format(Date(pts[index].timestamp))
                    }
                }
                chart.data = LineData(dataSet)
                chart.invalidate()
            }
        )
    }
}
