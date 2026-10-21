package com.b3insights.app.ui.screens.lock

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.b3insights.app.R
import com.b3insights.app.auth.BiometricHelper
import com.b3insights.app.ui.theme.BluePrimary

@Composable
fun BiometricLockScreen(onUnlocked: () -> Unit) {
    val context = LocalContext.current
    var error by remember { mutableStateOf<String?>(null) }
    val available = remember { BiometricHelper.canAuthenticate(context) }

    fun launchPrompt() {
        val activity = context as? FragmentActivity
        if (activity == null || !available) {
            onUnlocked()
            return
        }
        BiometricHelper.authenticate(
            activity = activity,
            onSuccess = onUnlocked,
            onError = { error = it },
            onFailed = { error = "Não reconhecido. Tente de novo." }
        )
    }

    LaunchedEffect(Unit) { launchPrompt() }

    Box(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.logo_b3insights),
                contentDescription = "B3 Insights",
                modifier = Modifier.size(140.dp).clip(RoundedCornerShape(28.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(28.dp))
            Text(
                "B3 Insights bloqueado",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                if (available) "Confirme sua identidade para continuar"
                else "Biometria indisponível neste aparelho",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
            error?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = Color(0xFFFF8A80), style = MaterialTheme.typography.bodySmall)
            }
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = { launchPrompt() },
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Icon(Icons.Filled.Fingerprint, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Desbloquear")
            }
        }
    }
}
