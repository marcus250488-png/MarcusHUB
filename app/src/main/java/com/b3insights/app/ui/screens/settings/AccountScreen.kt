@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.os.Build
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.b3insights.app.billing.ProConfig
import com.b3insights.app.billing.ProManager
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.*
import com.b3insights.app.viewmodel.SettingsViewModel

/**
 * Tela "Meus dados" — aberta pela seta em Minha conta.
 * Biometria, notificações e Sair ficam só aqui (sem duplicar em Configurações).
 */
@Composable
fun AccountScreen(
    onBack: () -> Unit,
    onSignedOut: () -> Unit,
    viewModel: SettingsViewModel = viewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val proState by ProManager.state.collectAsState()
    val isPro = true
    val photoUrl = viewModel.photoUrl
    val name = viewModel.displayName
    val email = viewModel.currentUserEmail
    val context = LocalContext.current
val notifPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* resultado tratado ao ativar o switch */ }
PaperBackground {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar", tint = TextPrimary)
            }

            // Avatar + email
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!photoUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = null,
                        modifier = Modifier.size(72.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        Modifier.size(72.dp).clip(CircleShape).background(BlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            (name ?: email ?: "?").take(1).uppercase(),
                            fontWeight = FontWeight.Bold,
                            color = NavyDark,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    email ?: "Sem e-mail",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            Text("Meus dados", fontWeight = FontWeight.Bold, color = TextPrimary)
            Spacer(Modifier.height(12.dp))

            SoftCard {
                Text("Nome completo", color = TextSecondary, style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    name ?: "—",
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
            }

            Spacer(Modifier.height(16.dp))

            SoftCard {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Biometria", color = TextPrimary)
                    Switch(
                        checked = settings.biometricEnabled,
                        onCheckedChange = { viewModel.setBiometric(it) },
                        colors = SwitchDefaults.colors(checkedTrackColor = BluePrimary)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            SoftCard {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Notificações Graham / Bazin", color = TextPrimary)
                            if (!isPro) {
                                Spacer(Modifier.width(6.dp))
                                Text("🔒", style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                    Switch(
                        checked = isPro && settings.notificationsEnabled,
                        onCheckedChange = { enabled ->
                            if (!isPro) {
                                /* pagamento desativado */
                                return@Switch
                            }
                            if (enabled && Build.VERSION.SDK_INT >= 33) {
                                val granted = ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_GRANTED
                                if (!granted) {
                                    notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                            viewModel.setNotifications(enabled)
                        },
                        colors = SwitchDefaults.colors(checkedTrackColor = BluePrimary)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Liberar PRO
            SoftCard(onClick = {
                // Pagamento desativado — acesso ja liberado
            }) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFC9A227))
                    Spacer(Modifier.width(10.dp))
                    Text(
                        if (isPro) "Você já tem os benefícios B3 Insights PRO"
                        else "Liberar acesso aos benefícios B3 Insights PRO",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                    if (!isPro) {
                        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            TextButton(
                onClick = {
                    viewModel.signOut()
                    onSignedOut()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sair", color = RedNegative, fontWeight = FontWeight.SemiBold)
            }

            Spacer(Modifier.height(48.dp))
        }
    }
}
