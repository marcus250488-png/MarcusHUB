package com.b3insights.app.sync

import android.content.Context
import android.util.Log
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.local.entity.UserSettingsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Isolamento de dados por conta (auditoria C-03).
 * Room ainda é um banco por aparelho, mas no logout e na troca de UID
 * limpamos tabelas de usuário e caches em memória para não misturar contas.
 */
object LocalSessionGuard {
    private const val TAG = "LocalSessionGuard"
    private const val PREFS = "b3_session"
    private const val KEY_UID = "last_uid"

    fun lastUid(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_UID, null)

    fun rememberUid(context: Context, uid: String?) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .apply {
                if (uid.isNullOrBlank()) remove(KEY_UID) else putString(KEY_UID, uid)
            }
            .apply()
    }

    /**
     * Se o UID logado for diferente do anterior, limpa dados locais antes do sync.
     * Evita enviar carteira da conta A para a conta B.
     */
    suspend fun onAuthenticated(context: Context, uid: String) = withContext(Dispatchers.IO) {
        val prev = lastUid(context)
        if (prev != null && prev != uid) {
            Log.w(TAG, "Troca de conta $prev → $uid — limpando dados locais")
            clearUserLocalData()
        }
        rememberUid(context, uid)
    }

    suspend fun onSignedOut(context: Context) = withContext(Dispatchers.IO) {
        Log.i(TAG, "Logout — limpando dados locais e caches")
        clearUserLocalData()
        rememberUid(context, null)
    }

    suspend fun clearUserLocalData() = withContext(Dispatchers.IO) {
        val app = B3InsightsApp.instance
        val db = app.database
        runCatching { db.portfolioDao().deleteAll() }
        runCatching { db.favoriteAssetDao().deleteAll() }
        runCatching { db.priceAlertDao().deleteAll() }
        runCatching { db.searchedAssetDao().clearAll() }
        runCatching { db.patrimonioHistoricoDao().deleteAll() }
        runCatching { db.userSettingsDao().deleteAll() }
        // recria settings padrão
        runCatching {
            db.userSettingsDao().upsert(
                UserSettingsEntity(id = 0)
            )
        }
        runCatching { app.marketDataRepository.clearMemoryCaches() }
    }
}
