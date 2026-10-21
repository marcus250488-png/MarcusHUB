package com.b3insights.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.b3insights.app.billing.ProManager
import com.b3insights.app.ui.navigation.B3InsightsNavGraph
import com.b3insights.app.ui.theme.B3InsightsTheme
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        val systemSplash = installSplashScreen()
        systemSplash.setKeepOnScreenCondition { false }
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        ProManager.startListening()
        handleProDeepLink(intent)

        setContent {
            // Apenas modo claro
            SideEffect {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        scrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        scrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT
                    )
                )
                val view = window.decorView
                WindowCompat.getInsetsController(window, view).apply {
                    // Fundo claro: ícones da status/nav escuros
                    isAppearanceLightStatusBars = true
                    isAppearanceLightNavigationBars = true
                }
            }

            B3InsightsTheme(darkTheme = false) {
                B3InsightsNavGraph()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleProDeepLink(intent)
    }

    override fun onResume() {
        super.onResume()
        ProManager.startListening()
        lifecycleScope.launch { ProManager.refreshFromCloud() }
    }

    private fun handleProDeepLink(intent: Intent?) {
        val data = intent?.data ?: return
        if (data.scheme == "b3insights" && data.host == "pro") {
            lifecycleScope.launch { ProManager.refreshFromCloud() }
        }
    }
}
