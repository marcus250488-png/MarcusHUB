@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.data.model.AssetQuote
import com.b3insights.app.ui.components.AssetListRow
import com.b3insights.app.ui.components.AssetLogo
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SectionTitle
import com.b3insights.app.ui.theme.BlueDeep
import com.b3insights.app.ui.theme.BlueLight
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.ui.theme.CatAcoes
import com.b3insights.app.ui.theme.CatAcoesBg
import com.b3insights.app.ui.theme.CatCripto
import com.b3insights.app.ui.theme.CatCriptoBg
import com.b3insights.app.ui.theme.CatEtfs
import com.b3insights.app.ui.theme.CatEtfsBg
import com.b3insights.app.ui.theme.CatFiis
import com.b3insights.app.ui.theme.CatFiisBg
import com.b3insights.app.ui.theme.GreenPositive
import com.b3insights.app.ui.theme.GreenPositiveBg
import com.b3insights.app.ui.theme.NavyDark
import com.b3insights.app.ui.theme.RedNegative
import com.b3insights.app.ui.theme.RedNegativeBg
import com.b3insights.app.util.asCurrency
import com.b3insights.app.util.asMoneyForTicker
import com.b3insights.app.viewmodel.HomeViewModel
import com.b3insights.app.viewmodel.PortfolioSummary
import java.util.Locale

private val BR = Locale("pt", "BR")

@Composable
fun HomeScreen(
    onAssetClick: (String) -> Unit,
    onSettingsClick: () -> Unit = {},
    onExploreClick: (String) -> Unit = {},
    onPortfolioClick: () -> Unit = {},
    onRankingClick: () -> Unit = {},
    onAccountClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var valuesHidden by rememberSaveable { mutableStateOf(false) }

    PaperBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(
                    start = 16.dp, end = 16.dp, top = 12.dp, bottom = 24.dp
                )
            ) {
                // ── Cabeçalho ───────────────────────────────────────────
                item {
                    HomeHeader(
                        onNotificationsClick = onSettingsClick,
                        onProfileClick = onAccountClick
                    )
                    Spacer(Modifier.height(16.dp))
                    SearchBar(
                        query = uiState.query,
                        onQueryChange = viewModel::onQueryChange,
                        onClear = viewModel::clearResults,
                        onSearch = { onAssetClick(uiState.query.trim().uppercase()) }
                    )
                    Spacer(Modifier.height(16.dp))
                }

                if (uiState.searchResults.isNotEmpty()) {
                    item { SectionTitle("Resultados") }
                    items(uiState.searchResults) { asset ->
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
                } else if (uiState.query.isNotBlank()) {
                    // digitando: ainda sem resultado
                    uiState.errorMessage?.let { msg -> item { EmptyState(mapErrorMessage(msg)) } }
                } else {
                    // ── Cartão azul "Meus Ativos" ───────────────────────
                    item {
                        PortfolioCard(
                            summary = uiState.summary,
                            hidden = valuesHidden,
                            onToggleHidden = { valuesHidden = !valuesHidden },
                            onClick = onPortfolioClick
                        )
                        Spacer(Modifier.height(24.dp))
                    }

                    // ── Categorias ──────────────────────────────────────
                    item {
                        SectionHeader(
                            icon = Icons.Filled.Apps,
                            title = "Categorias",
                            subtitle = null,
                            actionLabel = "Ver todas",
                            onAction = { onExploreClick("ACOES") }
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            CategoryCard(
                                Modifier.weight(1f), "Ações", "Empresas da B3",
                                Icons.Filled.BarChart, CatAcoes, CatAcoesBg
                            ) { onExploreClick("ACOES") }
                            CategoryCard(
                                Modifier.weight(1f), "FIIs", "Renda Imobiliária",
                                Icons.Filled.Home, CatFiis, CatFiisBg
                            ) { onExploreClick("FIIS") }
                            CategoryCard(
                                Modifier.weight(1f), "ETFs", "Diversificação",
                                Icons.Filled.Storage, CatEtfs, CatEtfsBg
                            ) { onExploreClick("ETFS") }
                            CategoryCard(
                                Modifier.weight(1f), "Criptomoedas", "Mercado global",
                                null, CatCripto, CatCriptoBg
                            ) { onExploreClick("CRIPTO") }
                        }
                        Spacer(Modifier.height(24.dp))
                    }

                    // ── Destaques do Momento ────────────────────────────
                    if (uiState.highlights.isNotEmpty()) {
                        item {
                            HighlightsCard(
                                quotes = uiState.highlights,
                                onAssetClick = onAssetClick,
                                onSeeAll = onRankingClick
                            )
                            Spacer(Modifier.height(24.dp))
                        }
                    }

                    // ── Meus Ativos ─────────────────────────────────────
                    item {
                        SectionHeader(
                            icon = Icons.Filled.Storage,
                            title = "Meus Ativos",
                            subtitle = "Acompanhe seus investimentos",
                            actionLabel = "Ver todos",
                            onAction = onPortfolioClick
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                    // Carteira vazia: mostra os mais pesquisados para a tela não ficar vazia
                    val myAssets = uiState.myAssets.ifEmpty { uiState.highlights.take(4) }
                    items(myAssets) { asset ->
                        MyAssetRow(asset = asset, onClick = { onAssetClick(asset.ticker) })
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    LaunchedEffect(uiState.query) {
        if (uiState.query.isNotBlank()) {
            kotlinx.coroutines.delay(250)
            viewModel.search()
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Cabeçalho e busca
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun HomeHeader(onNotificationsClick: () -> Unit, onProfileClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        B3Mark(Modifier.size(40.dp))
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Row {
                Text("B3 ", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = NavyDark)
                Text("Insights", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = BluePrimary)
            }
            Text(
                "Informação hoje, melhores decisões amanhã",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(Modifier.clickable(onClick = onNotificationsClick).padding(8.dp)) {
            Icon(
                Icons.Filled.Notifications, contentDescription = "Notificações",
                tint = NavyDark, modifier = Modifier.size(22.dp)
            )
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .size(9.dp)
                    .background(BluePrimary, CircleShape)
            )
        }
        Spacer(Modifier.width(6.dp))
        Box(
            Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BlueLight)
                .clickable(onClick = onProfileClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = "Conta", tint = BluePrimary, modifier = Modifier.size(22.dp))
        }
    }
}

/** Marca do app: barras + seta de alta (azul). */
@Composable
private fun B3Mark(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val barColor = Brush.verticalGradient(listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8)))
        val barW = w * 0.17f
        val bars = listOf(0.12f to 0.34f, 0.38f to 0.50f, 0.64f to 0.66f)
        bars.forEach { (x, bh) ->
            drawRoundRect(
                brush = barColor,
                topLeft = Offset(w * x, h * (0.92f - bh)),
                size = Size(barW, h * bh),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(barW * 0.25f)
            )
        }
        val stroke = Stroke(width = w * 0.09f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        val line = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.04f, h * 0.60f)
            lineTo(w * 0.34f, h * 0.36f)
            lineTo(w * 0.52f, h * 0.48f)
            lineTo(w * 0.90f, h * 0.12f)
        }
        drawPath(line, Color(0xFF2563EB), style = stroke)
        val head = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.66f, h * 0.10f)
            lineTo(w * 0.92f, h * 0.10f)
            lineTo(w * 0.92f, h * 0.36f)
        }
        drawPath(head, Color(0xFF2563EB), style = stroke)
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(BlueLight.copy(alpha = 0.75f))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Search, contentDescription = null, tint = NavyDark, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(6.dp))
        TextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    "Digite o ticker (ex.: ITUB4, MXRF11, PETR4)",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            modifier = Modifier.weight(1f),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = BluePrimary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )
        if (query.isNotEmpty()) {
            IconButton(onClick = onClear) {
                Icon(Icons.Filled.Clear, contentDescription = "Limpar", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Cartão azul
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun PortfolioCard(
    summary: PortfolioSummary,
    hidden: Boolean,
    onToggleHidden: () -> Unit,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(28.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(onClick = onClick)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(Color(0xFF2B6CE0), Color(0xFF1B58CB))))
        ) {
            // marca-d'água: gráfico subindo com seta
            Canvas(
                Modifier
                    .matchParentSize()
                    .padding(start = 120.dp)
            ) { drawWatermarkChart() }

            Column(Modifier.padding(start = 24.dp, end = 20.dp, top = 22.dp, bottom = 18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Meus Ativos", color = Color.White.copy(alpha = 0.92f), fontSize = 18.sp)
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (hidden) "Mostrar valores" else "Ocultar valores",
                        tint = Color.White.copy(alpha = 0.92f),
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable(onClick = onToggleHidden)
                    )
                    Spacer(Modifier.weight(1f))
                    Icon(
                        Icons.Filled.ChevronRight, contentDescription = null,
                        tint = Color.White, modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    if (hidden) "R$ ••••••" else summary.totalValue.asCurrency("BRL"),
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(4.dp))
                val up = summary.dayChangePercent >= 0
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (up) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                        contentDescription = null,
                        tint = if (up) Color(0xFF4ADE80) else Color(0xFFFF8A8A),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    val pct = String.format(BR, "%s%.2f%%", if (up) "+" else "-", kotlin.math.abs(summary.dayChangePercent))
                    Text(
                        if (hidden) "•••• hoje" else "$pct hoje",
                        color = if (up) Color(0xFF4ADE80) else Color(0xFFFF8A8A),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        // faixa inferior com os 4 tipos
        Row(
            Modifier
                .fillMaxWidth()
                .background(BlueDeep)
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TypeStat(Icons.Filled.BarChart, "Ações", summary.acoes)
            TypeStat(Icons.Filled.AccountBalance, "FIIs", summary.fiis)
            TypeStat(Icons.Filled.Storage, "ETFs", summary.etfs)
            TypeStat(null, "Cripto", summary.cripto)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWatermarkChart() {
    val w = size.width
    val h = size.height
    val c = Color.White.copy(alpha = 0.13f)
    val stroke = Stroke(width = 9f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.00f, h * 0.88f)
        lineTo(w * 0.22f, h * 0.60f)
        lineTo(w * 0.40f, h * 0.74f)
        lineTo(w * 0.62f, h * 0.40f)
        lineTo(w * 0.78f, h * 0.52f)
        lineTo(w * 0.97f, h * 0.18f)
    }
    drawPath(path, c, style = stroke)
    val head = androidx.compose.ui.graphics.Path().apply {
        moveTo(w * 0.82f, h * 0.16f)
        lineTo(w * 0.97f, h * 0.18f)
        lineTo(w * 0.94f, h * 0.36f)
    }
    drawPath(head, c, style = stroke)
}

@Composable
private fun TypeStat(icon: ImageVector?, label: String, count: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
        } else {
            Box(
                Modifier.size(24.dp).background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) { Text("₿", color = BlueDeep, fontWeight = FontWeight.Bold, fontSize = 18.sp) }
        }
        Spacer(Modifier.height(4.dp))
        Text(label, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Text(
            "$count ${if (count == 1) "ativo" else "ativos"}",
            color = Color.White.copy(alpha = 0.85f),
            fontSize = 15.sp
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Seções
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    actionLabel: String,
    onAction: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = NavyDark)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        SeeAllPill(actionLabel, onAction)
    }
}

@Composable
private fun SeeAllPill(label: String, onClick: () -> Unit, background: Color = Color.Transparent) {
    Row(
        Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .border(BorderStroke(1.dp, BluePrimary.copy(alpha = 0.28f)), RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = BluePrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun CategoryCard(
    modifier: Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector?,
    accent: Color,
    bg: Color,
    onClick: () -> Unit
) {
    Column(
        modifier
            .clip(RoundedCornerShape(22.dp))
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 14.dp)
    ) {
        Box(
            Modifier.size(40.dp).background(accent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(22.dp))
            } else {
                Text("₿", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }
        Spacer(Modifier.height(18.dp))
        Text(
            title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NavyDark,
            maxLines = 1, overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2, modifier = Modifier.weight(1f), lineHeight = 12.sp
            )
            Icon(
                Icons.Filled.ChevronRight, contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun HighlightsCard(
    quotes: List<AssetQuote>,
    onAssetClick: (String) -> Unit,
    onSeeAll: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(BlueLight.copy(alpha = 0.85f))
            .padding(top = 16.dp, bottom = 12.dp)
    ) {
        Row(Modifier.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(46.dp).background(BluePrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Star, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp)) }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Destaques do Momento", fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, color = NavyDark)
                Text(
                    "Ativos mais pesquisados e em alta",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            SeeAllPill("Ver todos", onSeeAll, background = Color.White)
        }
        Spacer(Modifier.height(14.dp))
        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quotes) { q -> HighlightTile(q) { onAssetClick(q.ticker) } }
        }
        Spacer(Modifier.height(10.dp))
        // 4 bolinhas = 4 "páginas" de cards
        val dots = 4
        val perDot = ((quotes.size + dots - 1) / dots).coerceAtLeast(1)
        val active by derivedStateOf { (listState.firstVisibleItemIndex / perDot).coerceIn(0, dots - 1) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            repeat(dots) { i ->
                Box(
                    Modifier
                        .padding(horizontal = 3.dp)
                        .height(6.dp)
                        .width(if (i == active) 20.dp else 6.dp)
                        .background(
                            if (i == active) BluePrimary else BluePrimary.copy(alpha = 0.22f),
                            RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun HighlightTile(q: AssetQuote, onClick: () -> Unit) {
    val chg = q.dayChangePercent
    val up = (chg ?: 0.0) >= 0
    Column(
        Modifier
            .width(104.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        AssetLogo(ticker = q.ticker, logoUrl = q.logoUrl, size = 44.dp, corner = 12.dp, assetType = q.assetType.name)
        Spacer(Modifier.height(8.dp))
        Text(q.ticker, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = NavyDark, maxLines = 1)
        Text(
            q.currentPrice.asMoneyForTicker(q.ticker),
            fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, maxLines = 1
        )
        Spacer(Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (up) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                contentDescription = null,
                tint = if (up) GreenPositive else RedNegative,
                modifier = Modifier.size(14.dp)
            )
            Text(
                pctText(chg),
                color = if (up) GreenPositive else RedNegative,
                fontWeight = FontWeight.SemiBold, fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun MyAssetRow(asset: AssetQuote, onClick: () -> Unit) {
    val chg = asset.dayChangePercent
    val up = (chg ?: 0.0) >= 0
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssetLogo(ticker = asset.ticker, logoUrl = asset.logoUrl, size = 50.dp, corner = 14.dp, assetType = asset.assetType.name)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(asset.ticker, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = NavyDark)
            Text(
                asset.name ?: asset.assetType.label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            Modifier
                .clip(RoundedCornerShape(50))
                .background(if (up) GreenPositiveBg else RedNegativeBg)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                pctText(chg),
                color = if (up) GreenPositive else RedNegative,
                fontWeight = FontWeight.SemiBold, fontSize = 15.sp
            )
        }
        Spacer(Modifier.width(8.dp))
        Icon(
            Icons.Filled.ChevronRight, contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun pctText(chg: Double?): String {
    if (chg == null) return "—"
    return String.format(BR, "%s%.2f%%", if (chg >= 0) "+" else "-", kotlin.math.abs(chg))
}

private fun mapErrorMessage(code: String): String = when (code) {
    "asset_not_found" -> "Ativo não encontrado"
    "no_connection" -> "Sem conexão com a internet."
    else -> "Não foi possível atualizar os dados. Tente novamente."
}
