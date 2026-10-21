package com.b3insights.app.data.remote

import com.b3insights.app.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * Chama o backend Firebase (marketQuote / marketSearch) quando MARKET_API_BASE estiver definido.
 * Token da brapi NÃO fica no APK nesse fluxo.
 */
object MarketProxyClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    fun isEnabled(): Boolean = BuildConfig.MARKET_API_BASE.isNotBlank()

    fun base(): String = BuildConfig.MARKET_API_BASE.trimEnd('/') + "/"

    /** GET marketQuote?ticker=PETR4 — devolve JSON bruto da brapi ou null se desabilitado/erro. */
    fun quoteJson(ticker: String): String? {
        if (!isEnabled()) return null
        return runCatching {
            val url = base() + "marketQuote?ticker=" + ticker.trim().uppercase()
            val req = Request.Builder().url(url).get().build()
            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return null
                resp.body?.string()
            }
        }.getOrNull()
    }
}
