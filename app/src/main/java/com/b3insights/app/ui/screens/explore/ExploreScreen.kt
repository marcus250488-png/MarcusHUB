@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.explore

import com.b3insights.app.data.model.FiiCatalog

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.data.model.AssetType
import com.b3insights.app.data.model.DataResult
import com.b3insights.app.ui.components.AssetListRow
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.theme.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


/** Categorias de FIIs (tijolo, papel, híbrido, FOF, desenvolvimento…). */
enum class FiiSector(val label: String, val tickers: List<String>) {
    TIJOLO_SHOPPING(
        "Shoppings",
        listOf("XPML11", "VISC11", "HSML11", "MALL11", "HGBS11", "FIGS11", "CPTS11").sorted()
    ),
    TIJOLO_LOGISTICA(
        "Logística",
        (listOf("HGLG11", "XPLG11", "BTLG11", "VILG11", "BRCO11", "GGRC11", "LVBI11", "RBRP11") + FiiCatalog.bySegment("Logística").map { it.ticker }).distinct().sorted()
    ),
    TIJOLO_LAJES(
        "Lajes corporativas",
        listOf("KNRI11", "HGRE11", "JSRE11", "RCRB11", "BROF11", "GTWR11").sorted()
    ),
    TIJOLO_VAREJO(
        "Varejo",
        listOf("TRXF11", "RBVA11", "HGRU11").sorted()
    ),
    TIJOLO_RESIDENCIAL(
        "Residencial",
        listOf("MFII11", "HGCR11", "CACR11").sorted()
    ),
    TIJOLO_HIBRIDO(
        "Tijolo híbrido",
        listOf("GARE11", "RECT11", "ALZR11", "TGAR11", "RBRR11").sorted()
    ),
    PAPEL(
        "Papel (CRI)",
        FiiCatalog.bySegment("Papel").map { it.ticker }.ifEmpty { listOf("KNCR11", "MXRF11", "IRDM11") }.sorted()
    ),
    HIBRIDO(
        "Híbridos",
        listOf("GARE11", "RECT11", "HGRU11", "RBRR11", "TGAR11").sorted()
    ),
    FOF(
        "FOFs",
        listOf("BCFF11", "KFOF11", "RBRF11", "HGFF11", "BPFF11", "XPSF11", "OUFF11").sorted()
    ),
    DESENVOLVIMENTO(
        "Desenvolvimento",
        listOf("MFII11", "PATL11", "RBCO11", "HCTR11").sorted()
    );

    fun tickerSet(): Set<String> = tickers.map { it.uppercase() }.toSet()

    companion object {
        fun allTickers(): List<String> = entries.flatMap { it.tickers }.distinct().sorted()
    }
}

/** Setores das abas de ações (sem Perene). */
enum class StockSector(val label: String, val tickers: List<String>) {
    ENERGIA(
        "Energia",
        listOf(
            "PETR3", "PETR4", "PRIO3", "RECV3", "RRRP3", "CSAN3", "UGPA3", "VBBR3",
            "ENAT3", "ELET3", "ELET6", "EQTL3", "ENGI11", "CPFE3", "CMIG3", "CMIG4",
            "CPLE3", "CPLE6", "EGIE3", "TAEE11", "AURE3", "NEOE3", "SRNA3", "ONZE3"
        ).sorted()
    ),
    SANEAMENTO(
        "Saneamento",
        listOf("SBSP3", "SAPR3", "SAPR4", "SAPR11", "CSMG3", "CASN3", "ORVR3", "AMBP3").sorted()
    ),
    BANCO(
        "Banco",
        listOf(
            "ABCB4", "BBAS3", "BBDC3", "BBDC4", "BBSE3", "BPAC11", "BRSR6", "ITSA4",
            "ITUB3", "ITUB4", "PSSA3", "SANB11", "BPAN4", "BMGB4"
        ).sorted()
    ),
    TELECOM(
        "Telecom",
        listOf("VIVT3", "TIMS3", "OIBR3", "OIBR4", "TELB3", "TELB4").sorted()
    ),
    CONSUMO_NAO_CICLICO(
        "Consumo não cíclico",
        listOf(
            "ABEV3", "ASAI3", "BEEF3", "BRFS3", "CAML3", "CRFB3", "GMAT3", "HYPE3",
            "JBSS3", "MDIA3", "MRFG3", "NTCO3", "PCAR3", "RADL3", "SMTO3", "TTEN3"
        ).sorted()
    );

    fun tickerSet(): Set<String> = tickers.map { it.uppercase() }.toSet()
}

enum class ExploreCategory(val title: String, val tickers: List<String>) {
    ACOES("Ações populares", emptyList()), // carregado da API
    FIIS(
        "FIIs em destaque",
        FiiSector.allTickers()
    ),
    ETFS(
        "ETFs",
        listOf(
            "ACWI11", "BOVA11", "BOVV11", "BREW11", "ESGB11", "EURP11", "FIND11",
            "GOLD11", "HASH11", "ISUS11", "IVVB11", "MATB11", "NASD11", "PIBB11",
            "SMAL11", "SPXI11", "XFIX11", "XINA11"
        ).sorted()
    ),
    CRIPTO(
        "Criptomoedas",
        listOf(
            "ADA-USD", "APT-USD", "ARB-USD", "ATOM-USD", "AVAX-USD", "BCH-USD",
            "BNB-USD", "BTC-USD", "DOGE-USD", "DOT-USD", "ETH-USD", "LINK-USD",
            "LTC-USD", "NEAR-USD", "OP-USD", "SOL-USD", "SUI-USD", "TON-USD",
            "UNI-USD", "XRP-USD"
        ).sorted()
    ),
    EXTERIOR(
        "Ações do exterior",
        listOf(
            "AAPL", "ABNB", "AMD", "AMZN", "AVGO", "BA", "BABA", "BAC", "BRK-B",
            "COIN", "CRM", "DIS", "GOOGL", "GS", "IBM", "INTC", "JNJ", "JPM",
            "KO", "MA", "META", "MSFT", "NFLX", "NKE", "NVDA", "ORCL", "PEP",
            "PFE", "PYPL", "QCOM", "SBUX", "SHOP", "TSLA", "UBER", "V", "WMT", "XOM"
        ).sorted()
    );

    companion object {
        fun fromKey(key: String): ExploreCategory =
            entries.find { it.name.equals(key, ignoreCase = true) } ?: ACOES
    }
}

data class ExploreUiState(
    val isLoading: Boolean = true,
    val items: List<AssetQuote> = emptyList(),
    val error: String? = null
)

class ExploreViewModel : ViewModel() {
    private val repo = B3InsightsApp.instance.marketDataRepository
    private val _ui = MutableStateFlow(ExploreUiState())
    val ui: StateFlow<ExploreUiState> = _ui.asStateFlow()

    fun load(category: ExploreCategory) {
        viewModelScope.launch {
            _ui.value = ExploreUiState(isLoading = true)
            when (category) {
                ExploreCategory.ACOES -> loadAllStocks()
                else -> loadTickers(category.tickers)
            }
        }
    }

    private suspend fun loadAllStocks() {
        when (val r = repo.listAllStocks(500)) {
            is DataResult.Success -> {
                // Garante tickers dos setores presentes
                val have = r.data.map { it.ticker.uppercase() }.toSet()
                val missing = StockSector.entries
                    .flatMap { it.tickers }
                    .map { it.uppercase() }
                    .filter { it !in have }
                    .distinct()
                val extra = if (missing.isEmpty()) emptyList() else fetchQuotes(missing)
                val merged = (r.data + extra)
                    .distinctBy { it.ticker.uppercase() }
                    .sortedBy { it.ticker }
                _ui.value = ExploreUiState(isLoading = false, items = merged)
            }
            is DataResult.Error -> {
                // Fallback: setores + lista fixa popular
                val fallback = (
                    StockSector.entries.flatMap { it.tickers } + listOf(
                        "VALE3", "B3SA3", "RENT3", "SUZB3", "RAIL3", "GGBR4", "WEGE3",
                        "LREN3", "MGLU3", "HAPV3", "TOTS3", "EMBR3", "CCRO3", "MULT3"
                    )
                    ).distinct().sorted()
                loadTickers(fallback)
            }
            DataResult.Loading -> Unit
        }
    }

    private suspend fun loadTickers(tickers: List<String>) {
        val quotes = fetchQuotes(tickers)
        _ui.value = ExploreUiState(
            isLoading = false,
            items = quotes.sortedBy { it.ticker }
        )
    }

    private suspend fun fetchQuotes(tickers: List<String>): List<AssetQuote> =
        coroutineScope {
            tickers.map { t ->
                async {
                    when (val r = repo.getQuote(t)) {
                        is DataResult.Success -> r.data
                        else -> null
                    }
                }
            }.awaitAll().filterNotNull()
        }
}

@Composable
fun ExploreScreen(
    categoryKey: String,
    onBack: () -> Unit,
    onAssetClick: (String) -> Unit,
    viewModel: ExploreViewModel = viewModel()
) {
    val category = remember(categoryKey) { ExploreCategory.fromKey(categoryKey) }
    val uiState by viewModel.ui.collectAsState()
    var selectedSector by remember { mutableStateOf<StockSector?>(null) }
    var selectedFiiSector by remember { mutableStateOf<FiiSector?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(category) {
        selectedSector = null
        selectedFiiSector = null
        searchQuery = ""
        viewModel.load(category)
    }

    // Filtro por abas (ações/FIIs) + busca por ticker/nome
    val displayedItems = remember(uiState.items, selectedSector, selectedFiiSector, category, searchQuery) {
        val base = when (category) {
            ExploreCategory.ACOES -> {
                if (selectedSector == null) uiState.items.sortedBy { it.ticker }
                else {
                    val set = selectedSector!!.tickerSet()
                    uiState.items.filter { it.ticker.uppercase() in set }.sortedBy { it.ticker }
                }
            }
            ExploreCategory.FIIS -> {
                if (selectedFiiSector == null) uiState.items.sortedBy { it.ticker }
                else {
                    val set = selectedFiiSector!!.tickerSet()
                    uiState.items.filter { it.ticker.uppercase() in set }.sortedBy { it.ticker }
                }
            }
            else -> uiState.items.sortedBy { it.ticker }
        }
        val q = searchQuery.trim()
        if (q.isEmpty()) {
            base
        } else {
            val uq = q.uppercase()
            base.filter {
                it.ticker.uppercase().contains(uq) ||
                    (it.name?.uppercase()?.contains(uq) == true)
            }
        }
    }

    PaperBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        category.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        when {
                            category == ExploreCategory.ACOES && selectedSector == null ->
                                "${displayedItems.size} ações · ordem alfabética"
                            category == ExploreCategory.ACOES ->
                                "${selectedSector!!.label} · ${displayedItems.size} ações"
                            category == ExploreCategory.FIIS && selectedFiiSector == null ->
                                "${displayedItems.size} FIIs · ordem alfabética"
                            category == ExploreCategory.FIIS ->
                                "${selectedFiiSector!!.label} · ${displayedItems.size} FIIs"
                            else -> "Cotações atualizadas pela internet"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            if (category == ExploreCategory.ACOES) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SectorChip(
                        label = "Todos",
                        selected = selectedSector == null,
                        onClick = { selectedSector = null }
                    )
                    StockSector.entries.forEach { sector ->
                        SectorChip(
                            label = sector.label,
                            selected = selectedSector == sector,
                            onClick = { selectedSector = sector }
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            if (category == ExploreCategory.FIIS) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SectorChip(
                        label = "Todos",
                        selected = selectedFiiSector == null,
                        onClick = { selectedFiiSector = null }
                    )
                    FiiSector.entries.forEach { sector ->
                        SectorChip(
                            label = sector.label,
                            selected = selectedFiiSector == sector,
                            onClick = { selectedFiiSector = sector }
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            // Lupa acima da lista
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                singleLine = true,
                placeholder = { Text("Buscar ticker ou nome…") },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Limpar")
                        }
                    }
                },
                shape = RoundedCornerShape(14.dp)
            )

            when {
                uiState.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = BluePrimary)
                            Spacer(Modifier.height(12.dp))
                            Text(
                                "Carregando cotações…",
                                color = TextSecondary
                            )
                        }
                    }
                }

                displayedItems.isEmpty() -> {
                    EmptyState(
                        when {
                            selectedSector != null -> "Nenhuma ação deste setor na lista carregada."
                            selectedFiiSector != null -> "Nenhum FII desta categoria na lista carregada."
                            else -> "Não foi possível carregar a lista. Verifique a internet."
                        }
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
                    ) {
                        items(displayedItems, key = { it.ticker }) { asset ->
                            AssetListRow(
                                ticker = asset.ticker,
                                name = asset.name,
                                typeLabel = asset.assetType.label,
                                price = asset.currentPrice,
                                changePercent = asset.dayChangePercent,
                                logoUrl = asset.logoUrl,
                                assetType = asset.assetType.name,
                                onClick = { onAssetClick(asset.ticker) }
                            )
                        }
                        item { Spacer(Modifier.height(24.dp)) }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectorChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = BluePrimary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
