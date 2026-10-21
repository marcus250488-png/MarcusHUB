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

data class CompareUiState(
    val tickers: List<String> = emptyList(),
    val details: Map<String, AssetDetail> = emptyMap(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/** Permite comparar até 4 ativos lado a lado (item 18 do escopo). */
class CompareViewModel : ViewModel() {
    private val repository = B3InsightsApp.instance.marketDataRepository

    private val _uiState = MutableStateFlow(CompareUiState())
    val uiState: StateFlow<CompareUiState> = _uiState.asStateFlow()

    fun addTicker(ticker: String) {
        val current = _uiState.value.tickers
        if (current.size >= 4 || current.contains(ticker.uppercase())) return
        _uiState.value = _uiState.value.copy(tickers = current + ticker.uppercase())
        fetch(ticker.uppercase())
    }

    fun removeTicker(ticker: String) {
        _uiState.value = _uiState.value.copy(
            tickers = _uiState.value.tickers - ticker,
            details = _uiState.value.details - ticker
        )
    }

    private fun fetch(ticker: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = repository.getAssetDetail(ticker, ChartPeriod.M3)) {
                is DataResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        details = _uiState.value.details + (ticker to result.data)
                    )
                }
                is DataResult.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = result.message)
                }
                DataResult.Loading -> Unit
            }
        }
    }
}
