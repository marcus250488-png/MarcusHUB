@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.b3insights.app.data.model.AssetType
import com.b3insights.app.ui.components.AssetListRow
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.theme.*
import com.b3insights.app.viewmodel.FavoritesViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FavoritesScreen(
    onAssetClick: (String) -> Unit,
    viewModel: FavoritesViewModel = viewModel()
) {
    val favorites by viewModel.favorites.collectAsState()
    val live by viewModel.liveQuotes.collectAsState()
    val lastUpdated by viewModel.lastUpdated.collectAsState()
    val timeFormat = remember { SimpleDateFormat("HH:mm:ss", Locale.getDefault()) }

    // Atualiza ao abrir e a cada 30s, só enquanto a tela está visível.
    LaunchedEffect(Unit) {
        while (true) {
            viewModel.refreshQuotes()
            delay(30_000)
        }
    }

    PaperBackground {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
            Text(
                "Favoritos",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            Text(
                text = lastUpdated?.let { "Atualizado às ${timeFormat.format(Date(it))}" }
                    ?: "Atualizando cotações…",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            if (favorites.isEmpty()) {
                EmptyState("Você ainda não tem ativos favoritos. Toque na estrela na página de um ativo para adicioná-lo aqui.")
            } else {
                LazyColumn {
                    items(favorites, key = { it.ticker }) { fav ->
                        // Preço/variação ATUAIS (live); o valor salvo no banco só entra como reserva.
                        val q = live[fav.ticker]
                        AssetListRow(
                            ticker = fav.ticker,
                            name = q?.name ?: fav.name,
                            typeLabel = runCatching { AssetType.valueOf(fav.assetType ?: "OUTRO") }
                                .getOrDefault(AssetType.OUTRO).label,
                            price = q?.price ?: fav.lastKnownPrice,
                            changePercent = q?.changePercent ?: fav.lastKnownChangePercent,
                            logoUrl = null,
                            assetType = fav.assetType,
                            onClick = { onAssetClick(fav.ticker) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}
