package com.b3insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.b3insights.app.ui.theme.DividerColor
import com.b3insights.app.ui.screens.explore.FiiSector
import com.b3insights.app.ui.theme.NavyDark
import com.b3insights.app.util.LogoMisses
import com.b3insights.app.util.logoCandidates
import java.io.IOException

/** Cor do selo (usado quando não há logo) conforme o segmento do FII; demais ativos ficam no azul-marinho. */
private fun badgeColorFor(cleanTicker: String): Color {
    val sector = FiiSector.entries.firstOrNull { cleanTicker in it.tickerSet() } ?: return NavyDark
    return when (sector) {
        FiiSector.TIJOLO_SHOPPING -> Color(0xFF8E3B9E)
        FiiSector.TIJOLO_LOGISTICA -> Color(0xFFB26A00)
        FiiSector.TIJOLO_LAJES -> Color(0xFF37474F)
        FiiSector.TIJOLO_VAREJO -> Color(0xFFC2185B)
        FiiSector.TIJOLO_RESIDENCIAL -> Color(0xFF2E7D32)
        FiiSector.TIJOLO_HIBRIDO, FiiSector.HIBRIDO -> Color(0xFF00796B)
        FiiSector.PAPEL -> Color(0xFF1565C0)
        FiiSector.FOF -> Color(0xFF5E35B1)
        FiiSector.DESENVOLVIMENTO -> Color(0xFF455A64)
    }
}

/**
 * Ícone do ativo.
 * Tenta carregar o logo real (brapi / FMP / ícones de cripto, conforme o tipo do ativo) e,
 * enquanto carrega ou se nenhuma fonte tiver o logo, mostra um quadrado com as 2 primeiras
 * letras do ticker.
 *
 * @param logoUrl  URL de logo vinda da API (ex.: logourl da brapi), tentada primeiro
 * @param assetType nome do AssetType (ex.: "CRIPTO"); opcional — sem ele o tipo é inferido do ticker
 */
@Composable
fun AssetLogo(
    ticker: String,
    logoUrl: String? = null,
    size: Dp = 40.dp,
    corner: Dp = 10.dp,
    assetType: String? = null
) {
    val clean = ticker
        .uppercase()
        .removeSuffix(".SA")
        .removeSuffix("-USD")
        .removeSuffix("-BRL")
        .substringBefore(".")
        .take(12)

    val urls = remember(ticker, logoUrl, assetType) {
        logoCandidates(ticker, assetType, logoUrl).filterNot { LogoMisses.contains(it) }
    }
    var index by remember(urls) { mutableIntStateOf(0) }
    var loaded by remember(urls) { mutableStateOf(false) }
    val badgeColor = remember(clean) { badgeColorFor(clean) }
    // Selo: até 4 letras do ticker (ex.: HCRI, PETR) em tamanhos grandes; 2 nos pequenos.
    val badgeText = remember(clean, size) {
        val letters = clean.takeWhile { it.isLetter() }.ifEmpty { clean }
        letters.take(if (size >= 36.dp) 4 else 2).ifEmpty { "?" }
    }
    val currentUrl = urls.getOrNull(index)
    val shape = RoundedCornerShape(corner)

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(if (loaded) Color.White else badgeColor)
            .then(if (loaded) Modifier.border(1.dp, DividerColor, shape) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (!loaded) {
            Text(
                text = badgeText,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = if (badgeText.length >= 4) 10.sp else 11.sp
                )
            )
        }
        if (currentUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = clean,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(size * 0.14f),
                onSuccess = { loaded = true },
                onError = { state ->
                    // Só memoriza como "inexistente" se não for falha de rede.
                    if (state.result.throwable !is IOException) LogoMisses.add(currentUrl)
                    if (index < urls.lastIndex) index++
                }
            )
        }
    }
}
