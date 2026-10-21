package com.b3insights.app.billing

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

data class ProSubscriptionState(
    val isPro: Boolean = true,
    val status: String = "active",
    val currentPeriodEnd: Long? = null,
    val loading: Boolean = false
)

/**
 * Acesso completo liberado.
 * Quando integrar Google Play Billing, este objeto volta a validar a compra.
 */
object ProManager {

    private val _state = MutableStateFlow(
        ProSubscriptionState(isPro = true, status = "active", loading = false)
    )
    val state: StateFlow<ProSubscriptionState> = _state.asStateFlow()

    fun isEntitled(status: String): Boolean = true

    fun isProUser(): Boolean = true

    fun startListening() {
        _state.value = ProSubscriptionState(isPro = true, status = "active", loading = false)
    }

    fun stopListening() { }

    suspend fun refreshFromCloud() {
        _state.value = ProSubscriptionState(isPro = true, status = "active", loading = false)
    }

    fun observePro(): Flow<Boolean> = flowOf(true)

    /** Sem checkout por enquanto (Play Billing no futuro). */
    fun startCheckout(context: Context) { }
}
