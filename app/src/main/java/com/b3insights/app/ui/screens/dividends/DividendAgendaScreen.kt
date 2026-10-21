@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.dividends

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

enum class DividendCategory(val title: String, val tickers: List<String>) {
    CARTEIRA("Minha carteira", emptyList()),
    ACOES(
        "Ações",
        listOf(
            "ITUB4", "BBDC4", "BBAS3", "PETR4", "VALE3", "WEGE3", "ABEV3", "ITSA4",
            "TAEE11", "CMIG4", "EGIE3", "CPLE6", "SANB11", "VIVT3", "BBSE3", "CXSE3",
            "B3SA3", "RENT3", "EQTL3", "JBSS3", "GGBR4", "CSAN3", "RADL3", "PRIO3",
            "ELET3", "SBSP3", "ENGI11", "TRPL4", "KLBN11", "SUZB3"
        )
    ),
    FIIS(
        "FIIs",
        listOf(
            "MXRF11", "HGLG11", "KNRI11", "XPLG11", "VISC11", "BTLG11", "KNCR11",
            "HGRE11", "XPML11", "BCFF11", "IRDM11", "RECT11", "GARE11", "CPTS11",
            "TGAR11", "RBRR11", "MALL11", "HSML11", "PVBI11", "VILG11", "HGBS11",
            "JSRE11", "BRCO11", "LVBI11", "TRXF11", "DEVA11"
        )
    ),
    ETFS(
        "ETFs",
        listOf("BOVA11", "IVVB11", "SMAL11", "DIVO11", "FIND11", "MATB11", "XFIX11", "HASH11")
    )
}

data class AgendaItem(
    val ticker: String,
    val type: String,
    val exDate: Long,
    val paymentDate: Long?,
    val value: Double
)

data class AgendaUiState(
    val isLoading: Boolean = true,
    val items: List<AgendaItem> = emptyList()
)

class DividendAgendaViewModel : ViewModel() {
    private val repository = B3InsightsApp.instance.marketDataRepository
    private val portfolioRepo = B3InsightsApp.instance.portfolioRepository
    private val _ui = MutableStateFlow(AgendaUiState())
    val ui: StateFlow<AgendaUiState> = _ui.asStateFlow()

    fun load(category: DividendCategory, year: Int, month: Int) {
        viewModelScope.launch {
            _ui.value = AgendaUiState(isLoading = true)
            val calStart = Calendar.getInstance().apply {
                set(year, month, 1, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            val start = calStart.timeInMillis
            val end = Calendar.getInstance().apply {
                timeInMillis = start
                add(Calendar.MONTH, 1)
            }.timeInMillis

            val tickers = if (category == DividendCategory.CARTEIRA) {
                portfolioRepo.getAllPositions().first()
                    .map { it.ticker.uppercase() }
                    .distinct()
            } else category.tickers

            // Carrega em paralelo (mais rápido)
            val deferred = tickers.map { ticker ->
                viewModelScope.async(kotlinx.coroutines.Dispatchers.IO) {
                    when (val r = repository.getAssetDetail(ticker, ChartPeriod.Y1)) {
                        is DataResult.Success -> r.data.dividends.mapNotNull { d ->
                            if (d.exDate in start until end) {
                                AgendaItem(
                                    ticker = ticker,
                                    type = d.type,
                                    exDate = d.exDate,
                                    paymentDate = d.paymentDate,
                                    value = d.valuePerShare
                                )
                            } else null
                        }
                        else -> emptyList()
                    }
                }
            }
            val items = deferred.awaitAll().flatten()
            _ui.value = AgendaUiState(
                isLoading = false,
                items = items.sortedBy { it.exDate }
            )
        }
    }
}

@Composable
fun DividendAgendaScreen(
    onAssetClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: DividendAgendaViewModel = viewModel()
) {
    var category by remember { mutableStateOf(DividendCategory.CARTEIRA) }
    val cal = remember { Calendar.getInstance() }
    var year by remember { mutableIntStateOf(cal.get(Calendar.YEAR)) }
    var month by remember { mutableIntStateOf(cal.get(Calendar.MONTH)) }
    val ui by viewModel.ui.collectAsState()

    LaunchedEffect(category, year, month) {
        viewModel.load(category, year, month)
    }

    val monthName = remember(year, month) {
        val c = Calendar.getInstance().apply { set(year, month, 1) }
        java.text.SimpleDateFormat("MMMM yyyy", java.util.Locale("pt", "BR")).format(c.time)
            .replaceFirstChar { it.uppercase() }
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                }
                Text(
                    "Agenda de Dividendos",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(DividendCategory.entries.toList()) { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat.title) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NavyDark,
                            selectedLabelColor = BackgroundWhite
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    if (month == 0) { month = 11; year-- } else month--
                }) {
                    Icon(Icons.Filled.ChevronLeft, contentDescription = "Mês anterior", tint = TextPrimary)
                }
                Text(monthName, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                IconButton(onClick = {
                    if (month == 11) { month = 0; year++ } else month++
                }) {
                    Icon(Icons.Filled.ChevronRight, contentDescription = "Próximo mês", tint = TextPrimary)
                }
            }

            Spacer(Modifier.height(8.dp))

            when {
                ui.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = BluePrimary)
                        Spacer(Modifier.height(8.dp))
                        Text("Carregando proventos…", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                ui.items.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Nenhum provento encontrado neste mês\npara os ativos monitorados.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
                else -> LazyColumn(Modifier.fillMaxSize()) {
                    item {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ativo", Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                            Text("Data ex", Modifier.weight(1.1f), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                            Text("Tipo", Modifier.weight(1.2f), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium)
                            Text("Dividendos", Modifier.weight(1.0f), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Medium, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                        }
                    }
                    items(ui.items) { item ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                Modifier.weight(1.0f).clickable { onAssetClick(item.ticker) },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AssetLogo(ticker = item.ticker, size = 24.dp, corner = 6.dp)
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    item.ticker,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1
                                )
                            }
                            Text(
                                item.exDate.asDate(),
                                Modifier.weight(1.1f),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "Provento",
                                Modifier.weight(1.2f),
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                item.value.asCurrency(),
                                Modifier.weight(1.0f),
                                color = GreenPositive,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.End
                            )
                        }
                    }
                    item { Spacer(Modifier.height(24.dp)) }
                }
            }
        }
    }
}

