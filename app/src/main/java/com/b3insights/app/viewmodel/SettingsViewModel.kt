package com.b3insights.app.viewmodel

import androidx.lifecycle.ViewModel
import com.b3insights.app.sync.LocalSessionGuard
import com.b3insights.app.B3InsightsApp
import androidx.lifecycle.viewModelScope
import com.b3insights.app.sync.CloudSync
import com.b3insights.app.billing.ProManager
import com.b3insights.app.auth.AuthManager
import com.b3insights.app.data.local.entity.UserSettingsEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val userSettingsDao = B3InsightsApp.instance.database.userSettingsDao()
    private val authManager = AuthManager(B3InsightsApp.instance)

    private val _settings = MutableStateFlow(UserSettingsEntity())
    val settings: StateFlow<UserSettingsEntity> = _settings.asStateFlow()

    val currentUserEmail: String? get() = authManager.email
    val displayName: String? get() = authManager.displayName
    val photoUrl: String? get() = authManager.photoUrl

    init {
        viewModelScope.launch {
            userSettingsDao.getSettings().collect { it?.let { s -> _settings.value = s } }
        }
    }

    fun setAutoUpdate(enabled: Boolean) {
        viewModelScope.launch {
            userSettingsDao.upsert(_settings.value.copy(autoUpdateEnabled = enabled))
            CloudSync.pushAfterLocalChange()
        }
    }

    fun setDarkTheme(enabled: Boolean) {
        viewModelScope.launch {
            userSettingsDao.upsert(_settings.value.copy(darkTheme = enabled))
            CloudSync.pushAfterLocalChange()
        }
    }

    fun setPreferCryptoBrl(enabled: Boolean) {
        viewModelScope.launch {
            userSettingsDao.upsert(_settings.value.copy(preferCryptoBrl = enabled))
            CloudSync.pushAfterLocalChange()
        }
    }

    fun setNotifications(enabled: Boolean) {
        viewModelScope.launch {
            // FREE não pode ativar alertas Graham/Bazin
            val allow = !enabled || ProManager.isProUser()
            val value = if (allow) enabled else false
            userSettingsDao.upsert(_settings.value.copy(notificationsEnabled = value))
            CloudSync.pushAfterLocalChange()
            val ctx = B3InsightsApp.instance
            if (value) {
                com.b3insights.app.notifications.ValuationMonitor.schedule(ctx)
            } else {
                com.b3insights.app.notifications.ValuationMonitor.cancel(ctx)
            }
        }
    }

    fun setBiometric(enabled: Boolean) {
        viewModelScope.launch {
            userSettingsDao.upsert(_settings.value.copy(biometricEnabled = enabled))
            CloudSync.pushAfterLocalChange()
        }
    }

    private val _syncMsg = MutableStateFlow<String?>(null)
    val syncMsg: StateFlow<String?> = _syncMsg.asStateFlow()

    fun syncNow() {
        viewModelScope.launch {
            _syncMsg.value = "Sincronizando…"
            val r = CloudSync.syncOnLogin()
            _syncMsg.value = if (r.isSuccess) {
                "Sincronizado com a conta Google ✓"
            } else {
                "Falha: ${r.exceptionOrNull()?.message ?: "faça login e tente de novo"}"
            }
        }
    }

    fun restoreFromCloud() {
        viewModelScope.launch {
            _syncMsg.value = "Restaurando da nuvem…"
            val r = CloudSync.restoreFromCloud()
            _syncMsg.value = if (r.isSuccess) {
                "Dados restaurados da nuvem ✓ (reabra Meus Ativos)"
            } else {
                "Falha ao restaurar: ${r.exceptionOrNull()?.message ?: "sem dados na nuvem ou offline"}"
            }
        }
    }

    fun clearSyncMsg() { _syncMsg.value = null }

    fun signOut() {
        viewModelScope.launch {
            runCatching { LocalSessionGuard.onSignedOut(B3InsightsApp.instance) }
            authManager.signOut()
        }
    }
}
