@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.news

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.NewsItem
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asDateTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

data class NewsUiState(
    val isLoading: Boolean = true,
    val items: List<NewsItem> = emptyList(),
    val error: String? = null
)

class NewsViewModel : ViewModel() {
    private val _ui = MutableStateFlow(NewsUiState())
    val ui: StateFlow<NewsUiState> = _ui.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _ui.value = NewsUiState(isLoading = true)
            val items = withContext(Dispatchers.IO) { com.b3insights.app.data.remote.NewsFetcher.fetchMarketNews(limit = 25) }
            _ui.value = if (items.isEmpty()) {
                NewsUiState(isLoading = false, error = "Não foi possível carregar notícias agora.")
            } else {
                NewsUiState(isLoading = false, items = items)
            }
        }
    }
}

@Composable
fun NewsScreen(
    onBack: () -> Unit,
    viewModel: NewsViewModel = viewModel()
) {
    val ui by viewModel.ui.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.load() }

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
                    "Notícias de hoje",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            when {
                ui.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }
                ui.error != null && ui.items.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(ui.error!!, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                else -> LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ui.items) { news ->
                        SoftCard(
                            onClick = {
                                runCatching {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                                    )
                                }
                            }
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                if (!news.imageUrl.isNullOrBlank()) {
                                    AsyncImage(
                                        model = news.imageUrl,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(RoundedCornerShape(10.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(Modifier.width(12.dp))
                                }
                                Column(Modifier.weight(1f)) {
                                    Surface(
                                        color = BlueLight,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            news.source.uppercase(),
                                            Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                            color = NavyDark,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                    Spacer(Modifier.height(6.dp))
                                    Text(
                                        news.title,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    if (news.date > 0) {
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            news.date.asDateTime(),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}
