package com.b3insights.app.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.auth.AuthManager
import com.b3insights.app.sync.CloudSync
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val firebaseNotConfigured: Boolean = false,
    val signedIn: Boolean = false,
    /** Nome + e-mail para boas-vindas (só no 1º login desta conta neste aparelho). */
    val welcomeUser: Pair<String, String>? = null
)

class LoginViewModel : ViewModel() {
    private val authManager = AuthManager(B3InsightsApp.instance)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun getSignInIntent(): Intent? {
        val intent = authManager.getSignInIntent()
        if (intent == null) {
            _uiState.value = _uiState.value.copy(firebaseNotConfigured = true)
        }
        return intent
    }

    fun onSignInResult(data: Intent?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = authManager.handleSignInResult(data)
            result.onSuccess {
                runCatching { CloudSync.syncOnLogin() }
                    .onFailure { android.util.Log.e("Login", "sync failed", it) }

                val welcome = authManager.consumePendingWelcome()
                if (welcome != null) {
                    // Fila no Firestore para auditoria / Cloud Function futura
                    runCatching {
                        val uid = authManager.currentUser?.uid ?: return@runCatching
                        FirebaseFirestore.getInstance()
                            .collection("users").document(uid)
                            .collection("meta").document("welcome")
                            .set(
                                mapOf(
                                    "name" to welcome.first,
                                    "email" to welcome.second,
                                    "requestedAt" to System.currentTimeMillis(),
                                    "sent" to false
                                )
                            )
                            .await()
                    }.onFailure { android.util.Log.w("Login", "welcome queue failed", it) }
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    signedIn = true,
                    welcomeUser = welcome
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Não foi possível entrar com o Google. Tente novamente."
                )
            }
        }
    }

    fun continueWithoutLogin() {
        _uiState.value = _uiState.value.copy(signedIn = true)
    }
}
