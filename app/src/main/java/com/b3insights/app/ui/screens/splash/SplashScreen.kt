package com.b3insights.app.ui.screens.splash

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.delay

/**
 * Abertura "Marcus Hub — um ecossistema conectado" (HTML animado em assets/splash.html).
 * Mesma abertura de todos os apps do ecossistema. Termina quando o HTML avisa
 * (splash:ready → AndroidSplash.onReady, ~6 s) ou pelo timeout de segurança.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var finalizado by remember { mutableStateOf(false) }

    fun concluir() {
        if (!finalizado) {
            finalizado = true
            onFinished()
        }
    }

    // Timeout de segurança (o HTML dispara o evento em ~6 s)
    LaunchedEffect(Unit) {
        delay(6_800)
        concluir()
    }

    Box(Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(AndroidColor.BLACK)
                    isVerticalScrollBarEnabled = false
                    isHorizontalScrollBarEnabled = false
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        cacheMode = WebSettings.LOAD_NO_CACHE
                        allowFileAccess = true
                        mediaPlaybackRequiresUserGesture = false
                    }
                    addJavascriptInterface(object {
                        @JavascriptInterface
                        fun onReady() {
                            post { concluir() }
                        }
                    }, "AndroidSplash")
                    webViewClient = WebViewClient()
                    loadUrl("file:///android_asset/splash.html")
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
