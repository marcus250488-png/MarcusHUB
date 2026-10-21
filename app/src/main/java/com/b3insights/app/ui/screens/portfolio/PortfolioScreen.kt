@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
package com.b3insights.app.ui.screens.portfolio

import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePicker
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.b3insights.app.util.InterNotaCorretagemParser
import com.b3insights.app.util.BinanceCsvParser
import com.b3insights.app.util.quoteTickerFor
import com.b3insights.app.util.BrokerageOperation
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.data.model.AssetType
import com.b3insights.app.ui.components.ChangePill
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.components.PortfolioAnalyticsSection
import com.b3insights.app.billing.ProConfig
import com.b3insights.app.billing.ProManager
import com.b3insights.app.ui.components.ProUpgradeDialog
import com.b3insights.app.ui.theme.*
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asDate
import com.b3insights.app.util.asDateTime
import com.b3insights.app.viewmodel.PortfolioItemUi
import com.b3insights.app.viewmodel.PortfolioViewModel

@Composable
fun PortfolioScreen(
    onAssetClick: (String) -> Unit,
    onAddClick: (ticker: String?, type: String?) -> Unit = { _, _ -> },
    initialBuyTicker: String? = null,
    initialBuyType: String? = null,
    viewModel: PortfolioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showProLimit by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<PortfolioItemUi?>(null) }
    var selectedCategory by remember { mutableStateOf<AssetType?>(null) }


    val settings by com.b3insights.app.B3InsightsApp.instance.database.userSettingsDao()
        .getSettings()
        .collectAsState(initial = null)
    val filtered = uiState.items.filter { selectedCategory == null || it.assetType == selectedCategory?.name }
    val tradItems = filtered.filter { it.assetType.uppercase() !in setOf("CRIPTO", "CRYPTO") && !it.ticker.contains("-USD") && !it.ticker.contains("-BRL") }
    val cryptoItems = filtered.filter { it.assetType.uppercase() in setOf("CRIPTO", "CRYPTO") || it.ticker.contains("-USD") || it.ticker.contains("-BRL") }
    // Scrolls independentes: cabeçalho e linhas de cada tabela andam juntos
    val tableHScrollTrad = rememberScrollState()
    val tableHScrollCrypto = rememberScrollState()

    PaperBackground {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Text(
                    "Meus Ativos",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                SoftCard {
                    Text("Patrimônio total", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                    Text(uiState.totalCurrent.asCurrency(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        Text("Investido: ${uiState.totalInvested.asCurrency()}", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.width(12.dp))
                        val profit = uiState.totalCurrent - uiState.totalInvested
                        val pct = if (uiState.totalInvested > 0) (profit / uiState.totalInvested) * 100 else 0.0
                        ChangePill(pct, compact = true)
                    }
                    if (uiState.fxFallback) {
                        Text(
                            text = "Câmbio USD/BRL indisponível — usando 5,00 (aprox.). Valores de cripto podem divergir.",
                            color = Color(0xFFF59E0B),
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    val tir = uiState.tirAnnualPct
                    if (tir != null && uiState.items.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        val inTarget = tir >= 12.0 && tir <= 15.0
                        val above = tir > 15.0
                        val tirColor = when {
                            inTarget || above -> GreenPositive
                            tir >= 0.0 -> Color(0xFFF59E0B)
                            else -> RedNegative
                        }
                        val bandLabel = when {
                            inTarget -> "Na meta 12-15% a.a."
                            above -> "Acima da meta 15% a.a."
                            tir >= 0.0 -> "Abaixo da meta 12% a.a."
                            else -> "TIR negativa"
                        }
                        Text(
                            text = "TIR da carteira",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = String.format(java.util.Locale("pt", "BR"), "%+.2f%% a.a.", tir),
                            fontWeight = FontWeight.Bold,
                            color = tirColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = bandLabel,
                            color = tirColor,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val progress = (tir.toFloat() / 20f).coerceIn(0f, 1f)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(12.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF374151))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(progress)
                                    .background(tirColor)
                            )
                        }
                        Text(
                            text = "TIR = retorno dos aportes nas datas reais vs valor atual. Com proventos: Ferramentas > Rentabilidade.",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                }
            }

            if (settings != null && uiState.items.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    SoftCard {
                        Text("Alocação vs meta", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        val total = uiState.totalCurrent.coerceAtLeast(0.0001)
                        val byType = uiState.items.groupBy { it.assetType.uppercase() }.mapValues { e ->
                            e.value.sumOf { it.currentValue ?: it.totalInvested }
                        }
                        fun pctOf(keys: List<String>): Float {
                            val v = keys.sumOf { byType[it] ?: 0.0 }
                            return ((v / total) * 100.0).toFloat()
                        }
                        val rows = listOf(
                            Triple("Ações", pctOf(listOf("ACAO", "STOCK")), settings!!.targetAcoesPct),
                            Triple("FIIs", pctOf(listOf("FII")), settings!!.targetFiisPct),
                            Triple("ETFs", pctOf(listOf("ETF")), settings!!.targetEtfsPct),
                            Triple("Cripto", pctOf(listOf("CRIPTO")), settings!!.targetCriptoPct)
                        )
                        rows.forEach { (label, actual, target) ->
                            val delta = actual - target
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(label, color = TextPrimary, style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "${"%.0f".format(actual)}% / ${target.toInt()}%  " +
                                        when {
                                            kotlin.math.abs(delta) < 2f -> "ok"
                                            delta > 0 -> "↑${"%.0f".format(delta)}pp"
                                            else -> "↓${"%.0f".format(-delta)}pp"
                                        },
                                    color = when {
                                        kotlin.math.abs(delta) < 2f -> GreenPositive
                                        else -> RedNegative
                                    },
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.items.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    PortfolioAnalyticsSection(uiState.items)
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf(null, AssetType.ACAO, AssetType.FII, AssetType.ETF, AssetType.CRIPTO)
                    items(categories.size) { index ->
                        val type = categories[index]
                        val label = type?.label ?: "Todos"
                        val selected = selectedCategory == type
                        AssistChip(
                            onClick = { selectedCategory = type },
                            label = { Text(label) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (selected) BluePrimary else SurfaceCard,
                                labelColor = if (selected) BackgroundWhite else TextSecondary
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (filtered.isEmpty()) {
                item {
                    EmptyState("Você ainda não cadastrou nenhum ativo na sua carteira. Toque no botão + para adicionar.")
                }
            } else {
                // —— Ações / FIIs / ETFs (colunas completas) ——
                if (tradItems.isNotEmpty()) {
                    item {
                        Text(
                            "Ações, FIIs e ETFs",
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        PortfolioTableHeader(hScroll = tableHScrollTrad, cryptoMode = false)
                    }
                    items(tradItems, key = { "t-" + it.ticker + it.assetType }) { item ->
                        val weight = if (uiState.totalCurrent > 0) {
                            ((item.currentValue ?: item.totalInvested) / uiState.totalCurrent) * 100.0
                        } else 0.0
                        PortfolioTableRow(
                            item = item,
                            portfolioWeightPct = weight,
                            hScroll = tableHScrollTrad,
                            cryptoMode = false,
                            onEdit = { editingItem = item },
                            onOpenDetail = { onAssetClick(quoteTickerFor(item.ticker, item.assetType)) }
                        )
                    }
                }
                // —— Criptomoedas (colunas enxutas, scroll próprio) ——
                if (cryptoItems.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Criptomoedas",
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            "Sem DY / YOC / P/VP — só preço, variação, saldo e peso na carteira.",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        PortfolioTableHeader(hScroll = tableHScrollCrypto, cryptoMode = true)
                    }
                    items(cryptoItems, key = { "c-" + it.ticker + it.assetType }) { item ->
                        val weight = if (uiState.totalCurrent > 0) {
                            ((item.currentValue ?: item.totalInvested) / uiState.totalCurrent) * 100.0
                        } else 0.0
                        PortfolioTableRow(
                            item = item,
                            portfolioWeightPct = weight,
                            hScroll = tableHScrollCrypto,
                            cryptoMode = true,
                            onEdit = { editingItem = item },
                            onOpenDetail = { onAssetClick(quoteTickerFor(item.ticker, item.assetType)) }
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                if (viewModel.canAddAsset("__new__") || ProManager.isProUser()) {
                    onAddClick(null, null)
                } else {
                    showProLimit = true
                }
            },
            containerColor = BluePrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 12.dp)
                .size(48.dp)
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Adicionar ativo",
                tint = BackgroundWhite,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (showProLimit) {
        ProUpgradeDialog(
            onDismiss = { showProLimit = false },
            title = "🔒 Limite do plano FREE",
            message = "No plano gratuito você pode acompanhar até ${ProConfig.FREE_MAX_ASSETS} ativos na carteira.",
            detail = "Vire PRO para adicionar ativos ilimitados e liberar rankings avançados."
        )
    }

    editingItem?.let { item ->
        EditPositionDialog(
            item = item,
            onDismiss = { editingItem = null },
            onSave = { qty, price, purchaseDate, notes ->
                viewModel.replacePosition(
                    ticker = item.ticker,
                    assetType = item.assetType,
                    quantity = qty,
                    purchasePrice = price,
                    purchaseDate = purchaseDate,
                    notes = notes,
                    oldPositions = item.positions
                )
                editingItem = null
            },
            onSell = { qty, price, saleDate, notes, syncFin, valorBrl ->
                viewModel.addPosition(
                    ticker = item.ticker,
                    assetType = item.assetType,
                    quantity = qty,
                    purchasePrice = price,
                    purchaseDate = saleDate,
                    notes = notes,
                    tags = null,
                    side = "SELL",
                    syncFinanceiro = syncFin,
                    valorBrl = valorBrl
                )
                editingItem = null
            },
            onDelete = {
                viewModel.deleteAllForTicker(item.positions)
                editingItem = null
            },
            onOpenDetail = {
                editingItem = null
                onAssetClick(quoteTickerFor(item.ticker, item.assetType))
            }
        )
    }
    }
}

@Composable
private fun PortfolioTableHeader(
    hScroll: androidx.compose.foundation.ScrollState,
    cryptoMode: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(hScroll)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (cryptoMode) {
            PortHeader("Ativo", 80.dp)
            PortHeader("Quant.", 88.dp)
            PortHeader("Preço Médio", 100.dp)
            PortHeader("Preço Atual", 100.dp)
            PortHeader("Variação", 72.dp)
            PortHeader("Rentab.", 68.dp)
            PortHeader("Saldo", 96.dp)
            PortHeader("% Cart.", 56.dp)
            PortHeader("Opções", 52.dp)
        } else {
            PortHeader("Ativo", 80.dp)
            PortHeader("Tipo", 48.dp)
            PortHeader("Quant.", 56.dp)
            PortHeader("Preço Médio", 80.dp)
            PortHeader("Preço Atual", 80.dp)
            PortHeader("Variação", 68.dp)
            PortHeader("Rentab.", 64.dp)
            PortHeader("Saldo", 80.dp)
            PortHeader("% Cart.", 52.dp)
            PortHeader("P/VP", 48.dp)
            PortHeader("P/L", 48.dp)
            PortHeader("DY", 48.dp)
            PortHeader("YOC", 52.dp)
            PortHeader("Opções", 52.dp)
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
}

@Composable
private fun PortHeader(text: String, width: androidx.compose.ui.unit.Dp) {
    Text(
        text,
        Modifier.width(width),
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        color = TextSecondary,
        maxLines = 1
    )
}

@Composable
private fun PortfolioTableRow(
    item: PortfolioItemUi,
    portfolioWeightPct: Double,
    hScroll: androidx.compose.foundation.ScrollState,
    cryptoMode: Boolean,
    onEdit: () -> Unit,
    onOpenDetail: () -> Unit
) {
    var menuOpen by remember { mutableStateOf(false) }
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(hScroll)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (cryptoMode) {
            Text(
                item.ticker,
                Modifier.width(80.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = TextPrimary,
                maxLines = 1
            )
            Text(
                formatQty(item.totalQuantity),
                Modifier.width(88.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                formatPriceForType(item.averagePrice, item.assetType),
                Modifier.width(100.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.currentPrice?.let { formatPriceForType(it, item.assetType) } ?: "—",
                Modifier.width(100.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Box(Modifier.width(72.dp)) {
                ChangePill(item.profitLossPercent, compact = true)
            }
            Text(
                item.profitLossPercent?.let { "%+.1f%%".format(it) } ?: "—",
                Modifier.width(68.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = rentabColor(item.profitLossPercent)
            )
            Text(
                (item.currentValue ?: item.totalInvested).asCurrency(),
                Modifier.width(96.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                "%.1f%%".format(portfolioWeightPct),
                Modifier.width(56.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Box(Modifier.width(52.dp)) {
                OptionsMenu(
                    menuOpen = menuOpen,
                    onOpen = { menuOpen = true },
                    onDismiss = { menuOpen = false },
                    onEdit = onEdit,
                    onOpenDetail = onOpenDetail
                )
            }
        } else {
            Text(
                item.ticker,
                Modifier.width(80.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = TextPrimary,
                maxLines = 1
            )
            Text(
                typeLabel(item.assetType),
                Modifier.width(48.dp),
                fontSize = 10.sp,
                color = TextSecondary,
                maxLines = 1
            )
            Text(
                formatQty(item.totalQuantity),
                Modifier.width(56.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                formatPriceForType(item.averagePrice, item.assetType),
                Modifier.width(80.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.currentPrice?.let { formatPriceForType(it, item.assetType) } ?: "—",
                Modifier.width(80.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Box(Modifier.width(68.dp)) {
                ChangePill(item.profitLossPercent, compact = true)
            }
            Text(
                item.profitLossPercent?.let { "%+.1f%%".format(it) } ?: "—",
                Modifier.width(64.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = rentabColor(item.profitLossPercent)
            )
            Text(
                (item.currentValue ?: item.totalInvested).asCurrency(),
                Modifier.width(80.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                "%.1f%%".format(portfolioWeightPct),
                Modifier.width(52.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.pvp?.let { "%.2f".format(it) } ?: "—",
                Modifier.width(48.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.pl?.let { "%.1f".format(it) } ?: "—",
                Modifier.width(48.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.dividendYield?.let { "%.1f%%".format(it) } ?: "—",
                Modifier.width(48.dp),
                fontSize = 11.sp,
                color = TextPrimary
            )
            Text(
                item.yieldOnCost?.let { "%.1f%%".format(it) } ?: "—",
                Modifier.width(52.dp),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Box(Modifier.width(52.dp)) {
                OptionsMenu(
                    menuOpen = menuOpen,
                    onOpen = { menuOpen = true },
                    onDismiss = { menuOpen = false },
                    onEdit = onEdit,
                    onOpenDetail = onOpenDetail
                )
            }
        }
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f),
        thickness = 0.5.dp
    )
}

@Composable
private fun OptionsMenu(
    menuOpen: Boolean,
    onOpen: () -> Unit,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onOpenDetail: () -> Unit
) {
    IconButton(onClick = onOpen, modifier = Modifier.size(32.dp)) {
        Icon(
            Icons.Filled.MoreVert,
            contentDescription = "Opções",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    DropdownMenu(expanded = menuOpen, onDismissRequest = onDismiss) {
        DropdownMenuItem(
            text = { Text("Editar") },
            onClick = {
                onDismiss()
                onEdit()
            }
        )
        DropdownMenuItem(
            text = { Text("Abrir detalhes") },
            onClick = {
                onDismiss()
                onOpenDetail()
            }
        )
    }
}

@Composable
private fun rentabColor(pct: Double?): androidx.compose.ui.graphics.Color {
    return when {
        pct == null -> MaterialTheme.colorScheme.onSurfaceVariant
        pct >= 0 -> GreenPositive
        else -> RedNegative
    }
}

private fun typeLabel(assetType: String): String = when (assetType.uppercase()) {
    "ACAO", "STOCK" -> "Ação"
    "FII" -> "FII"
    "ETF" -> "ETF"
    "CRIPTO" -> "Cripto"
    else -> assetType
}

@Composable
fun AddPositionScreen(
    initialTicker: String = "",
    initialType: String? = null,
    onBack: () -> Unit,
    onConfirm: (ticker: String, type: String, qty: Double, price: Double, purchaseDate: Long, notes: String?, tags: String?, side: String, syncFinanceiro: Boolean, valorBrl: Double) -> Unit,
    onConfirmBatch: (List<com.b3insights.app.util.BrokerageOperation>) -> Unit = {}
) {
    val onDismiss = onBack
    var ticker by remember {
        mutableStateOf(
            initialTicker.removeSuffix(".SA").removeSuffix("-USD").removeSuffix("-BRL").uppercase()
        )
    }
    var type by remember {
        mutableStateOf(
            when (initialType?.uppercase()) {
                "CRIPTO", "CRYPTO", "CRYPTOCURRENCY" -> AssetType.CRIPTO
                "FII" -> AssetType.FII
                "ETF" -> AssetType.ETF
                else -> when {
                    com.b3insights.app.util.detectAssetType(null, initialTicker, null) == AssetType.FII -> AssetType.FII
                    com.b3insights.app.util.detectAssetType(null, initialTicker, null) == AssetType.ETF -> AssetType.ETF
                    initialTicker.uppercase() in setOf("BTC", "ETH", "SOL", "BNB", "XRP", "ADA") -> AssetType.CRIPTO
                    else -> AssetType.ACAO
                }
            }
        )
    }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf("") }
    var side by remember { mutableStateOf("BUY") }
    // Marcus Hub: desconta/credita no app Financeiro (Nubank ⇄ Banco Inter Investimento)
    var syncFinanceiro by remember { mutableStateOf(true) }
    var preferBrl by remember { mutableStateOf(true) }
    var loadingPrice by remember { mutableStateOf(false) }
    var priceHintAuto by remember { mutableStateOf<String?>(null) }
    var usdBrl by remember { mutableStateOf(5.0) }
    var investedBrl by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    var showSuggestions by remember { mutableStateOf(false) }

    // Data + hora (padrão = agora)
    var purchaseMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    var purchaseDateText by remember {
        mutableStateOf(
            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale("pt", "BR"))
                .format(java.util.Date(purchaseMillis))
        )
    }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = purchaseMillis
    )

    // Câmbio só quando for cripto (evita travar ao abrir a tela)
    LaunchedEffect(type) {
        if (type != AssetType.CRIPTO) return@LaunchedEffect
        val r = com.b3insights.app.B3InsightsApp.instance.marketDataRepository.getQuote("USDBRL=X")
        if (r is com.b3insights.app.data.model.DataResult.Success) {
            r.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
        }
    }

    // Sugestões de ticker (debounce)
    LaunchedEffect(ticker, type) {
        val q = ticker.trim()
        if (q.length < 1) {
            suggestions = emptyList()
            showSuggestions = false
            return@LaunchedEffect
        }
        delay(320)
        val repo = com.b3insights.app.B3InsightsApp.instance.marketDataRepository
        when (val r = repo.searchAssets(q)) {
            is com.b3insights.app.data.model.DataResult.Success -> {
                suggestions = r.data
                    .filter {
                        when (type) {
                            AssetType.CRIPTO -> it.assetType == AssetType.CRIPTO || it.ticker.contains("-")
                            AssetType.FII -> it.assetType == AssetType.FII || it.ticker.endsWith("11")
                            AssetType.ETF -> it.assetType == AssetType.ETF
                            else -> true
                        }
                    }
                    .take(8)
                    .map { it.ticker.removeSuffix(".SA") to (it.name ?: it.ticker) }
                showSuggestions = suggestions.isNotEmpty()
            }
            else -> {
                val local = listOf(
                    "PETR4", "VALE3", "ITUB4", "BBDC4", "BBAS3", "WEGE3", "ABEV3", "B3SA3",
                    "MXRF11", "HGLG11", "XPLG11", "VISC11", "BOVA11", "IVVB11", "SMAL11",
                    "BTC", "ETH", "SOL", "BNB", "XRP"
                ).filter { it.startsWith(q.uppercase()) }
                suggestions = local.map { it to it }
                showSuggestions = suggestions.isNotEmpty()
            }
        }
    }

    // Preço automático ao digitar ticker
    LaunchedEffect(ticker, type, preferBrl, usdBrl) {
        val raw = ticker.trim().uppercase()
        if (raw.length < 2) {
            priceHintAuto = null
            return@LaunchedEffect
        }
        delay(450)
        loadingPrice = true
        priceHintAuto = null
        val repo = com.b3insights.app.B3InsightsApp.instance.marketDataRepository
        val candidates = buildList {
            when (type) {
                AssetType.CRIPTO -> {
                    val base = raw.removeSuffix("-USD").removeSuffix("-BRL").removeSuffix("USDT")
                    add("$base-USD")
                }
                else -> add(raw)
            }
        }.distinct()
        var filled = false
        for (symbol in candidates) {
            when (val result = repo.getQuote(symbol)) {
                is com.b3insights.app.data.model.DataResult.Success -> {
                    val pxUsd = result.data.currentPrice
                    if (pxUsd != null && pxUsd > 0) {
                        if (type == AssetType.CRIPTO && preferBrl) {
                            val pxBrl = pxUsd * usdBrl
                            price = String.format(java.util.Locale.US, "%.2f", pxBrl).trimEnd('0').trimEnd('.')
                            priceHintAuto = "Preço atual em R$ carregado"
                        } else if (type == AssetType.CRIPTO) {
                            price = String.format(java.util.Locale.US, "%.6f", pxUsd).trimEnd('0').trimEnd('.')
                            priceHintAuto = "Preço atual (USD) carregado"
                        } else {
                            price = String.format(java.util.Locale.US, "%.2f", pxUsd).trimEnd('0').trimEnd('.')
                            priceHintAuto = "Preço atual carregado"
                        }
                        filled = true
                        break
                    }
                }
                else -> Unit
            }
        }
        if (!filled) priceHintAuto = "Não foi possível carregar o preço — digite manualmente"
        loadingPrice = false
    }

    val tickerHint = when (type) {
        AssetType.CRIPTO -> "Ex.: BTC, ETH, SOL"
        AssetType.FII -> "Ex.: MXRF11, HGLG11"
        AssetType.ETF -> "Ex.: BOVA11, IVVB11"
        else -> "Ex.: ITUB4, PETR4"
    }
    val priceLabel = when {
        loadingPrice -> "Buscando preço…"
        type == AssetType.CRIPTO && preferBrl -> "Preço médio (R$)"
        type == AssetType.CRIPTO -> "Preço médio (US$)"
        else -> "Preço médio (R$)"
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val selected = datePickerState.selectedDateMillis
                        if (selected != null) {
                            // Data escolhida + hora atual do aparelho
                            val calSel = java.util.Calendar.getInstance().apply { timeInMillis = selected }
                            val calNow = java.util.Calendar.getInstance()
                            calSel.set(java.util.Calendar.HOUR_OF_DAY, calNow.get(java.util.Calendar.HOUR_OF_DAY))
                            calSel.set(java.util.Calendar.MINUTE, calNow.get(java.util.Calendar.MINUTE))
                            calSel.set(java.util.Calendar.SECOND, calNow.get(java.util.Calendar.SECOND))
                            purchaseMillis = calSel.timeInMillis
                            purchaseDateText = java.text.SimpleDateFormat(
                                "dd/MM/yyyy HH:mm",
                                java.util.Locale("pt", "BR")
                            ).format(calSel.time)
                        }
                        showDatePicker = false
                    }
                ) { Text("OK", color = BluePrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    PaperBackground {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
                }
                Column(Modifier.weight(1f)) {
                    Text(
                        "Adicionar à carteira",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Text(
                        "Preencha os dados do ativo",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Text("Tipo do ativo", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(Modifier.height(8.dp))
                val types = listOf(
                    AssetType.ACAO to "Ação",
                    AssetType.FII to "Fundo",
                    AssetType.ETF to "ETF",
                    AssetType.CRIPTO to "Criptomoeda"
                )
                types.chunked(2).forEach { row ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        row.forEach { (at, label) ->
                            FilterChip(
                                selected = type == at,
                                onClick = {
                                    if (type != at) {
                                        // Troca de tipo: limpa ticker e campos ligados ao ativo
                                        type = at
                                        ticker = ""
                                        price = ""
                                        quantity = ""
                                        investedBrl = ""
                                        priceHintAuto = null
                                        suggestions = emptyList()
                                        showSuggestions = false
                                    }
                                },
                                label = { Text(label) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(Modifier.height(6.dp))
                }

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = ticker,
                    onValueChange = {
                        ticker = it.uppercase().trim()
                        showSuggestions = true
                    },
                    label = { Text("Ticker / símbolo") },
                    placeholder = { Text(tickerHint, color = TextSecondary) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (loadingPrice) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                        }
                    }
                )
                if (showSuggestions && suggestions.isNotEmpty()) {
                    SoftCard(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 4.dp)) {
                        suggestions.forEach { (sym, name) ->
                            Text(
                                text = if (name != sym) "$sym · $name" else sym,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        ticker = sym.removeSuffix(".SA").uppercase()
                                        showSuggestions = false
                                    }
                                    .padding(vertical = 8.dp),
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantidade") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(priceLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                priceHintAuto?.let {
                    Text(
                        it,
                        color = if (it.startsWith("Não")) RedNegative else GreenPositive,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (type == AssetType.CRIPTO) {
                    Spacer(Modifier.height(6.dp))
                    Row {
                        FilterChip(selected = preferBrl, onClick = { preferBrl = true }, label = { Text("Preço em R$") })
                        Spacer(Modifier.width(8.dp))
                        FilterChip(selected = !preferBrl, onClick = { preferBrl = false }, label = { Text("Preço em US$") })
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = investedBrl,
                        onValueChange = { v ->
                            investedBrl = v
                            val brl = v.replace(",", ".").toDoubleOrNull()
                            val px = price.replace(",", ".").toDoubleOrNull()
                            if (brl != null && brl > 0 && px != null && px > 0) {
                                val qty = if (preferBrl) brl / px else brl / (px * usdBrl)
                                quantity = String.format(java.util.Locale.US, "%.8f", qty).trimEnd('0').trimEnd('.')
                            }
                        },
                        label = { Text("Valor investido (R$) — opcional") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(8.dp))
                // Campo de data: toque abre calendário (não edita à mão)
                OutlinedTextField(
                    value = purchaseDateText,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data e hora da operação") },
                    placeholder = { Text("Toque para escolher a data") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    trailingIcon = {
                        TextButton(onClick = { showDatePicker = true }) {
                            Text("📅", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                )
                Text(
                    "Toque no campo para abrir o calendário. A hora atual é registrada automaticamente.",
                    color = TextSecondary,
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(Modifier.height(12.dp))
                Text("Tipo de operação", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(Modifier.height(6.dp))
                Row {
                    FilterChip(selected = side == "BUY", onClick = { side = "BUY" }, label = { Text("Compra") })
                    Spacer(Modifier.width(8.dp))
                    FilterChip(selected = side == "SELL", onClick = { side = "SELL" }, label = { Text("Venda") })
                }

                Spacer(Modifier.height(12.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Lançar no Financeiro",
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            if (side == "BUY") "Transfere da Nubank para a conta Inter (Investimentos)"
                            else "Devolve da conta Inter (Investimentos) para a Nubank",
                            color = TextSecondary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Switch(checked = syncFinanceiro, onCheckedChange = { syncFinanceiro = it })
                }

                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tags,
                    onValueChange = { tags = it },
                    label = { Text("Tags (ex.: renda, longo prazo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Observações (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                    Text("Cancelar", color = TextSecondary)
                }
                Button(
                    onClick = {
                        val qty = quantity.replace(",", ".").toDoubleOrNull() ?: return@Button
                        val prc = price.replace(",", ".").toDoubleOrNull() ?: return@Button
                        if (ticker.isBlank()) return@Button
                        val symbol = if (type == AssetType.CRIPTO && !ticker.contains("-")) {
                            if (preferBrl) "$ticker-BRL" else "$ticker-USD"
                        } else ticker
                        // Total em R$: cripto cotada em US$ é convertida pelo câmbio da tela
                        val totalBrl = qty * prc * (if (symbol.endsWith("-USD")) usdBrl else 1.0)
                        onConfirm(
                            symbol,
                            type.name,
                            qty,
                            prc,
                            purchaseMillis,
                            notes.ifBlank { null },
                            tags.ifBlank { null },
                            side,
                            syncFinanceiro,
                            totalBrl
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Adicionar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun EditPositionDialog(
    item: PortfolioItemUi,
    onDismiss: () -> Unit,
    onSave: (qty: Double, price: Double, purchaseDate: Long, notes: String?) -> Unit,
    onSell: (qty: Double, price: Double, purchaseDate: Long, notes: String?, syncFinanceiro: Boolean, valorBrl: Double) -> Unit,
    onDelete: () -> Unit,
    onOpenDetail: () -> Unit
) {
    val isCrypto = item.assetType == "CRIPTO" || item.ticker.contains("-USD") || item.ticker.contains("-BRL")
    var quantity by remember {
        mutableStateOf(formatQty(item.totalQuantity))
    }
    var price by remember {
        mutableStateOf(
            if (isCrypto) String.format(java.util.Locale.US, "%.6f", item.averagePrice).trimEnd('0').trimEnd('.')
            else String.format(java.util.Locale.US, "%.2f", item.averagePrice).trimEnd('0').trimEnd('.')
        )
    }
    var purchaseDateText by remember {
        mutableStateOf(item.lastPurchaseDate?.asDateTime() ?: "")
    }
    var notes by remember { mutableStateOf(item.positions.firstOrNull()?.notes.orEmpty()) }
    var usdBrl by remember { mutableStateOf(5.0) }
    var showPurchases by remember { mutableStateOf(true) }
    // Modo "Vender": registra uma venda (e devolve o dinheiro Inter → Nubank no Financeiro)
    var sellMode by remember { mutableStateOf(false) }
    var sellQty by remember { mutableStateOf(formatQty(item.totalQuantity)) }
    var sellPrice by remember {
        mutableStateOf(
            (item.currentPrice ?: item.averagePrice).let {
                if (isCrypto) String.format(java.util.Locale.US, "%.6f", it).trimEnd('0').trimEnd('.')
                else String.format(java.util.Locale.US, "%.2f", it).trimEnd('0').trimEnd('.')
            }
        )
    }
    var sellDateText by remember {
        mutableStateOf(
            java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale("pt", "BR"))
                .format(java.util.Date())
        )
    }
    var sellSyncFinanceiro by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val r = com.b3insights.app.B3InsightsApp.instance.marketDataRepository.getQuote("USDBRL=X")
        if (r is com.b3insights.app.data.model.DataResult.Success) {
            r.data.currentPrice?.takeIf { it > 0 }?.let { usdBrl = it }
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Editar ${item.ticker}",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    "${item.positions.size} operação(ões) registrada(s)",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                TextButton(
                    onClick = { showPurchases = !showPurchases },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (showPurchases) "Ocultar resumo de compras" else "Abrir resumo de compras",
                        color = BluePrimary
                    )
                }
                if (showPurchases) {
                    SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("Resumo de compras", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(Modifier.height(8.dp))
                        item.positions.sortedByDescending { it.purchaseDate }.forEach { pos ->
                            val sideLabel = if (pos.side == "SELL") "Venda" else "Compra"
                            Text(
                                "$sideLabel • ${pos.purchaseDate.asDateTime()}",
                                fontWeight = FontWeight.Medium,
                                color = TextPrimary,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "Qtd ${formatQty(pos.quantity)} × ${formatPriceForType(pos.purchasePrice, item.assetType)}",
                                color = TextSecondary,
                                style = MaterialTheme.typography.bodySmall
                            )
                            if (!pos.notes.isNullOrBlank()) {
                                Text(pos.notes!!, color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    FilterChip(selected = !sellMode, onClick = { sellMode = false }, label = { Text("Editar") })
                    Spacer(Modifier.width(8.dp))
                    FilterChip(selected = sellMode, onClick = { sellMode = true }, label = { Text("Vender") })
                }
                Spacer(Modifier.height(8.dp))

                if (sellMode) {
                    val sQty = sellQty.replace(",", ".").toDoubleOrNull()
                    val sPx = sellPrice.replace(",", ".").toDoubleOrNull()
                    val fx = if (item.ticker.endsWith("-USD")) usdBrl else 1.0
                    val sTotal = if (sQty != null && sPx != null) sQty * sPx * fx else 0.0
                    OutlinedTextField(
                        value = sellQty,
                        onValueChange = { sellQty = it },
                        label = { Text("Quantidade a vender (máx. ${formatQty(item.totalQuantity)})") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = sellPrice,
                        onValueChange = { sellPrice = it },
                        label = { Text("Preço de venda" + if (item.ticker.endsWith("-USD")) " (US$)" else " (R$)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                        )
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = sellDateText,
                        onValueChange = { sellDateText = it },
                        label = { Text("Data e hora da venda") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (sTotal > 0) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Valor da venda ≈ R$ %.2f".format(java.util.Locale("pt", "BR"), sTotal),
                            color = BluePrimary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Observações (opcional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Lançar no Financeiro",
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimary
                            )
                            Text(
                                "Devolve da conta Inter (Investimentos) para a Nubank",
                                color = TextSecondary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Switch(checked = sellSyncFinanceiro, onCheckedChange = { sellSyncFinanceiro = it })
                    }
                    Spacer(Modifier.height(12.dp))
                    TextButton(onClick = onOpenDetail, modifier = Modifier.fillMaxWidth()) {
                        Text("Abrir página do ativo", color = BluePrimary)
                    }
                } else {
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantidade total") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Preço médio (R$)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )
                )
                val qtyN = quantity.replace(",", ".").toDoubleOrNull()
                val pxN = price.replace(",", ".").toDoubleOrNull()
                if (isCrypto && qtyN != null && pxN != null && qtyN > 0 && pxN > 0) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "Total investido ≈ R$ %.2f".format(qtyN * pxN),
                        color = BluePrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = purchaseDateText,
                    onValueChange = { purchaseDateText = it },
                    label = { Text("Data e hora da compra") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Observações (opcional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = onOpenDetail, modifier = Modifier.fillMaxWidth()) {
                    Text("Abrir página do ativo", color = BluePrimary)
                }
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDelete) {
                    Text("Excluir", color = RedNegative)
                }
                TextButton(onClick = onDismiss) {
                    Text("Cancelar", color = TextSecondary)
                }
                Button(
                    onClick = {
                        if (sellMode) {
                            val qty = sellQty.replace(",", ".").toDoubleOrNull() ?: return@Button
                            val prc = sellPrice.replace(",", ".").toDoubleOrNull() ?: return@Button
                            if (qty <= 0 || prc <= 0) return@Button
                            // não deixa vender mais do que a posição atual
                            if (qty > item.totalQuantity + 1e-9) return@Button
                            val date = parsePurchaseDate(sellDateText) ?: System.currentTimeMillis()
                            val fx = if (item.ticker.endsWith("-USD")) usdBrl else 1.0
                            onSell(qty, prc, date, notes.ifBlank { null }, sellSyncFinanceiro, qty * prc * fx)
                        } else {
                            val qty = quantity.replace(",", ".").toDoubleOrNull() ?: return@Button
                            val prc = price.replace(",", ".").toDoubleOrNull() ?: return@Button
                            if (qty <= 0 || prc < 0) return@Button
                            val date = parsePurchaseDate(purchaseDateText) ?: (item.lastPurchaseDate ?: System.currentTimeMillis())
                            onSave(qty, prc, date, notes.ifBlank { null })
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) { Text(if (sellMode) "Vender" else "Salvar") }
            }
        },
        dismissButton = {}
    )
}

private fun formatQty(q: Double): String {
    return when {
        q == 0.0 -> "0"
        q >= 1 -> String.format(java.util.Locale.US, "%.4f", q).trimEnd('0').trimEnd('.')
        else -> String.format(java.util.Locale.US, "%.8f", q).trimEnd('0').trimEnd('.')
    }
}

private fun formatPriceForType(price: Double, assetType: String): String {
    // Custo médio de cripto fica em R$ (import Binance BRL e lançamento manual preferBrl)
    return price.asCurrency()
}


private fun parsePurchaseDate(raw: String): Long? {
    val text = raw.trim()
    val formats = listOf(
        "dd/MM/yyyy HH:mm",
        "dd/MM/yyyy HH:mm:ss",
        "dd/MM/yyyy",
        "dd-MM-yyyy HH:mm",
        "dd-MM-yyyy"
    )
    for (pattern in formats) {
        try {
            val sdf = java.text.SimpleDateFormat(pattern, java.util.Locale("pt", "BR"))
            sdf.isLenient = false
            return sdf.parse(text)?.time
        } catch (_: Exception) {
        }
    }
    return null
}
