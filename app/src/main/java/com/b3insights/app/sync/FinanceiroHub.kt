package com.b3insights.app.sync

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import kotlin.math.roundToLong

/**
 * Ponte B3 Insights → Financeiro (Marcus Hub).
 *
 * Cada compra/venda marcada para "descontar no Financeiro" vira um documento em
 *   users/{uid}/b3_hub/aportes/itens/{id}
 * com processado=false. O app Financeiro lê esses itens ao abrir/voltar ao app e cria a
 * transferência Nubank → Banco Inter (Investimento) (compra) ou o contrário (venda),
 * marcando processado=true. O id é gerado aqui e usado como id do documento: publicar
 * duas vezes não duplica.
 *
 * Fila local: se não houver login/internet no momento, o aporte fica guardado em
 * SharedPreferences e é reenviado por [flush] (chamado após o login/sync e ao abrir o app).
 */
object FinanceiroHub {

    private const val TAG = "FinanceiroHub"
    private const val PREFS = "b3_hub_queue"
    private const val KEY_PENDING = "pending"
    private const val WRITE_TIMEOUT_MS = 20_000L

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()

    /** Um aporte (compra) ou resgate (venda) feito no B3 Insights. Valores em R$. */
    data class Aporte(
        val ticker: String,
        val assetType: String,
        val side: String,          // "BUY" | "SELL"
        val quantity: Double,
        val price: Double,
        val valorBrl: Double,      // total da operação já em reais
        val dataMillis: Long,
        val tags: String?,
        val notes: String?,
        val id: String = UUID.randomUUID().toString()
    )

    /** Guarda na fila e tenta enviar em segundo plano (não bloqueia a UI). */
    fun publicar(context: Context, aporte: Aporte) {
        if (aporte.valorBrl <= 0.0) return
        val app = context.applicationContext
        enqueue(app, aporte)
        scope.launch { flush(app) }
    }

    /** Envia tudo que estiver pendente. Seguro chamar várias vezes. */
    suspend fun flush(context: Context) {
        val app = context.applicationContext
        val uid = runCatching { FirebaseAuth.getInstance().currentUser?.uid }.getOrNull() ?: return
        val db = runCatching { FirebaseFirestore.getInstance() }.getOrNull() ?: return
        mutex.withLock {
            val pending = readQueue(app)
            if (pending.isEmpty()) return
            val enviados = mutableSetOf<String>()
            for (a in pending) {
                val ok = runCatching {
                    withTimeoutOrNull(WRITE_TIMEOUT_MS) {
                        db.collection("users").document(uid)
                            .collection("b3_hub").document("aportes")
                            .collection("itens").document(a.id)
                            .set(toMap(a))
                            .await()
                        true
                    } == true
                }.onFailure { Log.w(TAG, "falha ao publicar ${a.id}", it) }.getOrDefault(false)
                if (ok) enviados.add(a.id)
            }
            if (enviados.isNotEmpty()) {
                writeQueue(app, readQueue(app).filterNot { it.id in enviados })
            }
        }
    }

    private fun toMap(a: Aporte): Map<String, Any?> = mapOf(
        "ticker" to a.ticker,
        "assetType" to a.assetType,
        "side" to a.side,
        "quantity" to a.quantity,
        "price" to a.price,
        "valorCents" to (a.valorBrl * 100.0).roundToLong(),
        "dataMillis" to a.dataMillis,
        "tags" to a.tags,
        "notes" to a.notes,
        "processado" to false,
        "origem" to "B3 Insights",
        "criadoEm" to System.currentTimeMillis()
    )

    // ── fila local ───────────────────────────────────────────────

    private fun enqueue(ctx: Context, a: Aporte) = synchronized(this) {
        writeQueue(ctx, readQueue(ctx) + a)
    }

    private fun readQueue(ctx: Context): List<Aporte> {
        val raw = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_PENDING, null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                Aporte(
                    id = o.getString("id"),
                    ticker = o.getString("ticker"),
                    assetType = o.getString("assetType"),
                    side = o.getString("side"),
                    quantity = o.getDouble("quantity"),
                    price = o.getDouble("price"),
                    valorBrl = o.getDouble("valorBrl"),
                    dataMillis = o.getLong("dataMillis"),
                    tags = o.optString("tags").takeIf { it.isNotBlank() },
                    notes = o.optString("notes").takeIf { it.isNotBlank() }
                )
            }
        }.getOrDefault(emptyList())
    }

    private fun writeQueue(ctx: Context, list: List<Aporte>) {
        val arr = JSONArray()
        list.forEach { a ->
            arr.put(
                JSONObject()
                    .put("id", a.id).put("ticker", a.ticker).put("assetType", a.assetType)
                    .put("side", a.side).put("quantity", a.quantity).put("price", a.price)
                    .put("valorBrl", a.valorBrl).put("dataMillis", a.dataMillis)
                    .put("tags", a.tags ?: "").put("notes", a.notes ?: "")
            )
        }
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_PENDING, arr.toString()).apply()
    }
}
