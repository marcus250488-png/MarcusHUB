package com.b3insights.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.model.AssetDetail
import com.b3insights.app.data.model.ChartPeriod
import com.b3insights.app.data.model.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AssetDetailUiState(
    val ticker: String = "",
    val isLoading: Boolean = true,
    val detail: AssetDetail? = null,
    val period: ChartPeriod = ChartPeriod.D1,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null,
    val fromCache: Boolean = false
)

class AssetDetailViewModel : ViewModel() {
    private val repository = B3InsightsApp.instance.marketDataRepository

    private val _uiState = MutableStateFlow(AssetDetailUiState())
    val uiState: StateFlow<AssetDetailUiState> = _uiState.asStateFlow()

    fun load(ticker: String) {
        _uiState.value = _uiState.value.copy(ticker = ticker.uppercase())
        fetch()
        observeFavorite(ticker)
    }

    private fun observeFavorite(ticker: String) {
        viewModelScope.launch {
            repository.isFavorite(ticker).collect { fav ->
                _uiState.value = _uiState.value.copy(isFavorite = fav)
            }
        }
    }

    fun onPeriodSelected(period: ChartPeriod) {
        if (_uiState.value.period == period && _uiState.value.detail != null) return
        _uiState.value = _uiState.value.copy(period = period)
        fetch(forceRefresh = true)
    }

    fun retry() = fetch(forceRefresh = true)

    private fun fetch(forceRefresh: Boolean = false) {
        val ticker = _uiState.value.ticker
        if (ticker.isBlank()) return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            when (val result = repository.getAssetDetail(ticker, _uiState.value.period, forceRefresh = forceRefresh)) {
                is DataResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        detail = result.data,
                        fromCache = result.fromCache
                    )
                    repository.recordSearch(ticker, result.data.quote.name, result.data.quote.assetType.name)
                }
                is DataResult.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                }
                DataResult.Loading -> Unit
            }
        }
    }

    fun toggleFavorite() {
        val detail = _uiState.value.detail ?: return
        viewModelScope.launch {
            repository.toggleFavorite(detail.quote, _uiState.value.isFavorite)
        }
    }
}
