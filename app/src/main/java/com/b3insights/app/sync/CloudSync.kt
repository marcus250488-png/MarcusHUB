package com.b3insights.app.sync

import android.util.Log
import com.b3insights.app.B3InsightsApp
import com.b3insights.app.data.local.entity.FavoriteAssetEntity
import com.b3insights.app.data.local.entity.PortfolioPositionEntity
import com.b3insights.app.data.local.entity.PriceAlertEntity
import com.b3insights.app.data.local.entity.SearchedAssetEntity
import com.b3insights.app.data.local.entity.UserSettingsEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Sincroniza carteira, favoritos, alertas, pesquisas e ajustes com Firestore
 * em users/{uid}/...
 *
 * Regras de segurança:
 * - Nunca apaga a nuvem se o local estiver vazio.
 * - No login: prioriza puxar da nuvem; só envia local se a nuvem estiver vazia.
 * - Merge: une posições locais + nuvem quando os dois têm dados.
 */
object CloudSync {

    private const val TAG = "CloudSync"

    private fun uid(): String? =
        runCatching { FirebaseAuth.getInstance().currentUser?.uid }.getOrNull()

    private fun db(): FirebaseFirestore? =
        runCatching { FirebaseFirestore.getInstance() }.getOrNull()

    private fun userRoot(uid: String) = db()?.collection("users")?.document(uid)

    /** Login / abertura do app com usuário já autenticado. */
    suspend fun syncOnLogin(): Result<Unit> = withContext(Dispatchers.IO) {
        // Isolamento por conta antes de qualquer merge
        val id = uid() ?: return@withContext Result.failure(IllegalStateException("Sem login"))
        LocalSessionGuard.onAuthenticated(B3InsightsApp.instance, id)
        val root = userRoot(id) ?: return@withContext Result.failure(IllegalStateException("Firestore indisponível"))
        try {
            val cloudPortfolio = root.collection("portfolio").get().await()
            val cloudFav = root.collection("favorites").get().await()
            val cloudAlerts = root.collection("alerts").get().await()
            val cloudSettings = root.collection("settings").document("main").get().await()
            val cloudRecent = root.collection("recent_searches").get().await()

            val localPortfolio = B3InsightsApp.instance.database.portfolioDao().getAllOnce()
            val localFav = B3InsightsApp.instance.database.favoriteAssetDao().getAllOnce()

            val cloudHasData = !cloudPortfolio.isEmpty || !cloudFav.isEmpty ||
                !cloudAlerts.isEmpty || cloudSettings.exists() || !cloudRecent.isEmpty
            val localHasData = localPortfolio.isNotEmpty() || localFav.isNotEmpty()

            when {
                // Nuvem tem dados e local vazio → restaura da nuvem
                cloudHasData && !localHasData -> {
                    Log.i(TAG, "Pull: nuvem → local (local vazio)")
                    pullAll(id)
                }
                // Local tem dados e nuvem vazia → envia para nuvem
                localHasData && !cloudHasData -> {
                    Log.i(TAG, "Push: local → nuvem (nuvem vazia)")
                    pushAll(allowEmptyOverwrite = false)
                }
                // Os dois têm dados → merge (não perde nada)
                cloudHasData && localHasData -> {
                    Log.i(TAG, "Merge: local + nuvem")
                    mergePortfolio(root, localPortfolio)
                    mergeFavorites(root, localFav)
                    pullSettings(root)
                    // alertas e recentes: puxa se local vazio
                    if (B3InsightsApp.instance.database.priceAlertDao().getAllOnce().isEmpty()) {
                        pullAlerts(root)
                    } else {
                        pushAlerts(allowEmptyOverwrite = false)
                    }
                    pushRecentSearches(allowEmptyOverwrite = false)
                    // sobe o merge de volta
                    pushPortfolio(allowEmptyOverwrite = false)
                    pushFavorites(allowEmptyOverwrite = false)
                }
                else -> {
                    Log.i(TAG, "Nada para sincronizar (local e nuvem vazios)")
                }
            }

            root.collection("settings").document("main")
                .set(mapOf("lastSyncAt" to System.currentTimeMillis()), SetOptions.merge())
                .await()
            // Reenvia aportes pendentes para o Financeiro (offline/sem login na hora)
            runCatching { FinanceiroHub.flush(B3InsightsApp.instance) }
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "syncOnLogin failed", e)
            Result.failure(e)
        }
    }

    /**
     * @param allowEmptyOverwrite se false, não apaga coleção na nuvem quando local está vazio.
     */
    suspend fun pushAll(allowEmptyOverwrite: Boolean = false): Result<Unit> = withContext(Dispatchers.IO) {
        val id = uid() ?: return@withContext Result.failure(IllegalStateException("Sem login"))
        try {
            pushSettings()
            pushPortfolio(allowEmptyOverwrite)
            pushFavorites(allowEmptyOverwrite)
            pushAlerts(allowEmptyOverwrite)
            pushRecentSearches(allowEmptyOverwrite)
            userRoot(id)?.collection("settings")?.document("main")
                ?.set(mapOf("lastSyncAt" to System.currentTimeMillis()), SetOptions.merge())
                ?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "pushAll failed", e)
            Result.failure(e)
        }
    }

    suspend fun pullAll(userId: String? = null): Result<Unit> = withContext(Dispatchers.IO) {
        val id = userId ?: uid() ?: return@withContext Result.failure(IllegalStateException("Sem login"))
        val root = userRoot(id) ?: return@withContext Result.failure(IllegalStateException("Firestore indisponível"))
        try {
            pullSettings(root)
            pullPortfolio(root)
            pullFavorites(root)
            pullAlerts(root)
            pullRecentSearches(root)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "pullAll failed", e)
            Result.failure(e)
        }
    }

    // ── Settings ──────────────────────────────────────────────

    suspend fun pushSettings() {
        val id = uid() ?: return
        val s = B3InsightsApp.instance.database.userSettingsDao().getSettingsOnce()
            ?: UserSettingsEntity()
        val map = mapOf(
            "darkTheme" to s.darkTheme,
            "autoUpdateEnabled" to s.autoUpdateEnabled,
            "notificationsEnabled" to s.notificationsEnabled,
            "biometricEnabled" to s.biometricEnabled,
            "preferCryptoBrl" to s.preferCryptoBrl,
            "targetAcoesPct" to s.targetAcoesPct,
            "targetFiisPct" to s.targetFiisPct,
            "targetEtfsPct" to s.targetEtfsPct,
            "targetCriptoPct" to s.targetCriptoPct,
            "targetOutrosPct" to s.targetOutrosPct,
            "targetPatrimonio" to s.targetPatrimonio,
            "targetRendaMensal" to s.targetRendaMensal,
            "googleAccountEmail" to (s.googleAccountEmail ?: FirebaseAuth.getInstance().currentUser?.email),
            "lastSyncAt" to System.currentTimeMillis(),
            "allowEmptyCloudRestore" to s.allowEmptyCloudRestore
        )
        userRoot(id)?.collection("settings")?.document("main")
            ?.set(map, SetOptions.merge())?.await()
    }

    private suspend fun pullSettings(root: DocumentReference) {
        val snap = root.collection("settings").document("main").get().await()
        if (!snap.exists()) return
        val local = B3InsightsApp.instance.database.userSettingsDao().getSettingsOnce()
            ?: UserSettingsEntity()
        val merged = local.copy(
            darkTheme = snap.getBoolean("darkTheme") ?: local.darkTheme,
            autoUpdateEnabled = snap.getBoolean("autoUpdateEnabled") ?: local.autoUpdateEnabled,
            notificationsEnabled = snap.getBoolean("notificationsEnabled") ?: local.notificationsEnabled,
            biometricEnabled = snap.getBoolean("biometricEnabled") ?: local.biometricEnabled,
            preferCryptoBrl = snap.getBoolean("preferCryptoBrl") ?: local.preferCryptoBrl,
            targetAcoesPct = (snap.getDouble("targetAcoesPct") ?: local.targetAcoesPct.toDouble()).toFloat(),
            targetFiisPct = (snap.getDouble("targetFiisPct") ?: local.targetFiisPct.toDouble()).toFloat(),
            targetEtfsPct = (snap.getDouble("targetEtfsPct") ?: local.targetEtfsPct.toDouble()).toFloat(),
            targetCriptoPct = (snap.getDouble("targetCriptoPct") ?: local.targetCriptoPct.toDouble()).toFloat(),
            targetOutrosPct = (snap.getDouble("targetOutrosPct") ?: local.targetOutrosPct.toDouble()).toFloat(),
            targetPatrimonio = snap.getDouble("targetPatrimonio") ?: local.targetPatrimonio,
            targetRendaMensal = snap.getDouble("targetRendaMensal") ?: local.targetRendaMensal,
            googleAccountEmail = snap.getString("googleAccountEmail") ?: local.googleAccountEmail,
            lastSyncAt = snap.getLong("lastSyncAt") ?: System.currentTimeMillis(),
            allowEmptyCloudRestore = snap.getBoolean("allowEmptyCloudRestore") ?: local.allowEmptyCloudRestore
        )
        B3InsightsApp.instance.database.userSettingsDao().upsert(merged)
    }

    // ── Portfolio ─────────────────────────────────────────────

    suspend fun pushPortfolio(allowEmptyOverwrite: Boolean = false) {
        val id = uid() ?: return
        val dao = B3InsightsApp.instance.database.portfolioDao()
        val list = dao.getAllOnce()
        val col = userRoot(id)?.collection("portfolio") ?: return

        if (list.isEmpty()) {
            // Nunca apaga a nuvem por acidente após reinstalar com banco vazio
            if (!allowEmptyOverwrite) {
                Log.w(TAG, "pushPortfolio: local vazio — não sobrescreve nuvem")
                return
            }
        }

        val existing = col.get().await()
        // Apaga só o que não está mais no local (quando local tem dados)
        if (list.isNotEmpty()) {
            val localKeys = list.map { portfolioDocId(it) }.toSet()
            for (doc in existing.documents) {
                if (doc.id !in localKeys) doc.reference.delete().await()
            }
        }

        for (p in list) {
            col.document(portfolioDocId(p)).set(
                mapOf(
                    "id" to p.id,
                    "ticker" to p.ticker,
                    "assetType" to p.assetType,
                    "quantity" to p.quantity,
                    "averagePrice" to p.averagePrice,
                    "purchasePrice" to p.purchasePrice,
                    "purchaseDate" to p.purchaseDate,
                    "notes" to p.notes,
                    "tags" to p.tags,
                    "side" to p.side,
                    "updatedAt" to (if (p.updatedAt > 0) p.updatedAt else System.currentTimeMillis()),
                    "deviceId" to android.os.Build.MODEL
                ),
                SetOptions.merge()
            ).await()
        }
    }

    private fun portfolioDocId(p: PortfolioPositionEntity): String =
        if (p.id > 0) "p_${p.id}" else "${p.ticker}_${p.purchaseDate}_${p.quantity}"

    private suspend fun pullPortfolio(root: DocumentReference) {
        val snaps = root.collection("portfolio").get().await()
        val dao = B3InsightsApp.instance.database.portfolioDao()
        if (snaps.isEmpty) {
            // C-11: só limpa local se o usuário autorizou restaurar nuvem vazia
            val allow = B3InsightsApp.instance.database.userSettingsDao()
                .getSettingsOnce()?.allowEmptyCloudRestore == true
            if (allow) {
                Log.i(TAG, "pullPortfolio: nuvem vazia + allowEmptyCloudRestore — limpando local")
                dao.deleteAll()
            } else {
                Log.i(TAG, "pullPortfolio: nuvem vazia — mantém local (não é falha de leitura)")
            }
            return
        }
        dao.deleteAll()
        val entities = snaps.documents.mapNotNull { d -> mapPortfolioDoc(d.data) }
        if (entities.isNotEmpty()) dao.insertAll(entities)
        Log.i(TAG, "pullPortfolio: ${entities.size} posições")
    }

    private suspend fun mergePortfolio(root: DocumentReference, local: List<PortfolioPositionEntity>) {
        val snaps = root.collection("portfolio").get().await()
        val cloudEntities = snaps.documents.mapNotNull { d -> mapPortfolioDoc(d.data) }
        val dao = B3InsightsApp.instance.database.portfolioDao()

        // Chave de deduplicação: ticker + data + qtd + preço
        // Inclui notes+updatedAt para não fundir dois aportes idênticos no mesmo dia
        fun key(p: PortfolioPositionEntity) =
            "${p.ticker}|${p.purchaseDate}|${p.quantity}|${p.purchasePrice}|${p.side}|${p.notes.orEmpty()}|${p.updatedAt}"

        val byKey = LinkedHashMap<String, PortfolioPositionEntity>()
        for (p in local) byKey[key(p)] = p
        for (p in cloudEntities) {
            val k = key(p)
            val existing = byKey[k]
            if (existing == null) {
                byKey[k] = p
            } else {
                // C-04: vence o registro com updatedAt mais recente
                if (p.updatedAt > existing.updatedAt) byKey[k] = p
            }
        }
        dao.deleteAll()
        val merged = byKey.values.map { it.copy(id = 0) }.toList()
        if (merged.isNotEmpty()) dao.insertAll(merged)
        Log.i(TAG, "mergePortfolio: local=${local.size} cloud=${cloudEntities.size} merged=${merged.size}")
    }

    private fun mapPortfolioDoc(data: Map<String, Any>?): PortfolioPositionEntity? {
        if (data == null) return null
        val ticker = data["ticker"] as? String ?: return null
        return PortfolioPositionEntity(
            id = 0,
            ticker = ticker,
            assetType = data["assetType"] as? String ?: "ACAO",
            quantity = (data["quantity"] as? Number)?.toDouble() ?: 0.0,
            averagePrice = (data["averagePrice"] as? Number)?.toDouble() ?: 0.0,
            purchasePrice = (data["purchasePrice"] as? Number)?.toDouble() ?: 0.0,
            purchaseDate = (data["purchaseDate"] as? Number)?.toLong() ?: 0L,
            notes = data["notes"] as? String,
            tags = data["tags"] as? String,
            side = data["side"] as? String ?: "BUY",
            updatedAt = (data["updatedAt"] as? Number)?.toLong() ?: 0L
        )
    }

    // ── Favorites ─────────────────────────────────────────────

    suspend fun pushFavorites(allowEmptyOverwrite: Boolean = false) {
        val id = uid() ?: return
        val dao = B3InsightsApp.instance.database.favoriteAssetDao()
        val list = dao.getAllOnce()
        val col = userRoot(id)?.collection("favorites") ?: return
        if (list.isEmpty() && !allowEmptyOverwrite) {
            Log.w(TAG, "pushFavorites: local vazio — não sobrescreve nuvem")
            return
        }
        if (list.isNotEmpty()) {
            val keys = list.map { it.ticker.uppercase() }.toSet()
            val existing = col.get().await()
            for (doc in existing.documents) {
                if (doc.id !in keys) doc.reference.delete().await()
            }
        }
        for (f in list) {
            col.document(f.ticker.uppercase()).set(
                mapOf(
                    "ticker" to f.ticker.uppercase(),
                    "name" to f.name,
                    "assetType" to f.assetType,
                    "addedAt" to f.addedAt,
                    "lastKnownPrice" to f.lastKnownPrice,
                    "lastKnownChangePercent" to f.lastKnownChangePercent,
                    "updatedAt" to (if (f.updatedAt > 0) f.updatedAt else System.currentTimeMillis())
                ),
                SetOptions.merge()
            ).await()
        }
    }

    private suspend fun pullFavorites(root: DocumentReference) {
        val snaps = root.collection("favorites").get().await()
        if (snaps.isEmpty) return
        val dao = B3InsightsApp.instance.database.favoriteAssetDao()
        dao.deleteAll()
        val entities = snaps.documents.mapNotNull { d ->
            val ticker = d.getString("ticker") ?: return@mapNotNull null
            FavoriteAssetEntity(
                ticker = ticker.uppercase(),
                name = d.getString("name"),
                assetType = d.getString("assetType"),
                addedAt = d.getLong("addedAt") ?: System.currentTimeMillis(),
                lastKnownPrice = d.getDouble("lastKnownPrice"),
                lastKnownChangePercent = d.getDouble("lastKnownChangePercent"),
                updatedAt = d.getLong("updatedAt") ?: 0L
            )
        }
        if (entities.isNotEmpty()) dao.insertAll(entities)
    }

    private suspend fun mergeFavorites(root: DocumentReference, local: List<FavoriteAssetEntity>) {
        val snaps = root.collection("favorites").get().await()
        val cloud = snaps.documents.mapNotNull { d ->
            val ticker = d.getString("ticker") ?: return@mapNotNull null
            FavoriteAssetEntity(
                ticker = ticker.uppercase(),
                name = d.getString("name"),
                assetType = d.getString("assetType"),
                addedAt = d.getLong("addedAt") ?: System.currentTimeMillis(),
                lastKnownPrice = d.getDouble("lastKnownPrice"),
                lastKnownChangePercent = d.getDouble("lastKnownChangePercent"),
                updatedAt = d.getLong("updatedAt") ?: 0L
            )
        }
        val dao = B3InsightsApp.instance.database.favoriteAssetDao()
        val map = LinkedHashMap<String, FavoriteAssetEntity>()
        for (f in local) map[f.ticker.uppercase()] = f
        for (f in cloud) {
            val k = f.ticker.uppercase()
            val existing = map[k]
            if (existing == null || f.updatedAt > existing.updatedAt) map[k] = f
        }
        dao.deleteAll()
        if (map.isNotEmpty()) dao.insertAll(map.values.toList())
    }

    // ── Alerts ────────────────────────────────────────────────

    suspend fun pushAlerts(allowEmptyOverwrite: Boolean = false) {
        val id = uid() ?: return
        val dao = B3InsightsApp.instance.database.priceAlertDao()
        val list = dao.getAllOnce()
        val col = userRoot(id)?.collection("alerts") ?: return
        if (list.isEmpty() && !allowEmptyOverwrite) return
        if (list.isNotEmpty()) {
            val keys = list.map { alertDocId(it) }.toSet()
            for (doc in col.get().await().documents) {
                if (doc.id !in keys) doc.reference.delete().await()
            }
        }
        for (a in list) {
            col.document(alertDocId(a)).set(
                mapOf(
                    "ticker" to a.ticker,
                    "targetPrice" to a.targetPrice,
                    "above" to a.above,
                    "enabled" to a.enabled,
                    "createdAt" to a.createdAt,
                    "triggeredAt" to a.triggeredAt
                ),
                SetOptions.merge()
            ).await()
        }
    }

    private fun alertDocId(a: PriceAlertEntity): String =
        if (a.id > 0) "a_${a.id}" else "${a.ticker}_${a.createdAt}"

    private suspend fun pullAlerts(root: DocumentReference) {
        val snaps = root.collection("alerts").get().await()
        if (snaps.isEmpty) return
        val dao = B3InsightsApp.instance.database.priceAlertDao()
        dao.deleteAll()
        val entities = snaps.documents.mapNotNull { d ->
            val ticker = d.getString("ticker") ?: return@mapNotNull null
            PriceAlertEntity(
                id = 0,
                ticker = ticker,
                targetPrice = d.getDouble("targetPrice") ?: 0.0,
                above = d.getBoolean("above") ?: true,
                enabled = d.getBoolean("enabled") ?: true,
                createdAt = d.getLong("createdAt") ?: System.currentTimeMillis(),
                triggeredAt = d.getLong("triggeredAt")
            )
        }
        if (entities.isNotEmpty()) dao.insertAll(entities)
    }

    // ── Recent searches ───────────────────────────────────────

    suspend fun pushRecentSearches(allowEmptyOverwrite: Boolean = false) {
        val id = uid() ?: return
        val dao = B3InsightsApp.instance.database.searchedAssetDao()
        val list = dao.getAllOnce()
        val col = userRoot(id)?.collection("recent_searches") ?: return
        if (list.isEmpty() && !allowEmptyOverwrite) return
        if (list.isNotEmpty()) {
            val keys = list.map { it.ticker.uppercase() }.toSet()
            for (doc in col.get().await().documents) {
                if (doc.id !in keys) doc.reference.delete().await()
            }
        }
        for (s in list) {
            col.document(s.ticker.uppercase()).set(
                mapOf(
                    "ticker" to s.ticker.uppercase(),
                    "name" to s.name,
                    "assetType" to s.assetType,
                    "lastSearchedAt" to s.lastSearchedAt
                ),
                SetOptions.merge()
            ).await()
        }
    }

    private suspend fun pullRecentSearches(root: DocumentReference) {
        val snaps = root.collection("recent_searches").get().await()
        if (snaps.isEmpty) return
        val dao = B3InsightsApp.instance.database.searchedAssetDao()
        dao.clearAll()
        val entities = snaps.documents.mapNotNull { d ->
            val ticker = d.getString("ticker") ?: return@mapNotNull null
            SearchedAssetEntity(
                ticker = ticker.uppercase(),
                name = d.getString("name"),
                assetType = d.getString("assetType"),
                lastSearchedAt = d.getLong("lastSearchedAt") ?: System.currentTimeMillis()
            )
        }
        if (entities.isNotEmpty()) dao.insertAll(entities)
    }

    /** Envia alterações locais (nunca com overwrite vazio). */
    suspend fun pushAfterLocalChange() {
        if (uid() == null) return
        runCatching { pushAll(allowEmptyOverwrite = false) }
    }

    /** Força puxar da nuvem (botão em Ajustes). */
    suspend fun restoreFromCloud(): Result<Unit> = pullAll()
}
