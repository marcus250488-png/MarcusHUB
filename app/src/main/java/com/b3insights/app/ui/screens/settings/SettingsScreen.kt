@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.screens.settings

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.b3insights.app.billing.ProConfig
import com.b3insights.app.billing.ProManager
import com.b3insights.app.ui.components.PaperBackground
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.theme.*
import com.b3insights.app.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    onSignedOut: () -> Unit,
    onToolsClick: () -> Unit = {},
    onCompareClick: () -> Unit = {},
    onAccountClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    viewModel: SettingsViewModel = viewModel()
) {
    val proState by ProManager.state.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val isPro = true
    val photoUrl = viewModel.photoUrl
    val name = viewModel.displayName
    val email = viewModel.currentUserEmail
    val context = LocalContext.current
PaperBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                "Configurações",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))

            // MINHA CONTA — em primeiro, seta à direita, sem botão Sair aqui
            Text(
                "MINHA CONTA",
                color = TextSecondary,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))

            SoftCard {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!photoUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            Modifier.size(48.dp).clip(CircleShape).background(BlueLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                (name ?: email ?: "?").take(1).uppercase(),
                                fontWeight = FontWeight.Bold,
                                color = NavyDark
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            name ?: "Minha conta",
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        Text(
                            email ?: "Toque para gerenciar",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    TextButton(
                        onClick = {
                            viewModel.signOut()
                            onSignedOut()
                        },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text("Sair", color = RedNegative, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            

            SoftCard(onClick = onToolsClick) {
                Text("Ferramentas", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(
                    "Alertas, proventos, rentabilidade, alocação, simulador, exportar",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SoftCard {
                Text("Sobre o aplicativo", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "B3 Insights — Tudo sobre seus ativos em um só lugar. Versão 1.0.",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SoftCard(onClick = onPrivacyClick) {
                Text("Política de privacidade", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(
                    "Como tratamos seus dados",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SoftCard(onClick = onTermsClick) {
                Text("Termos de uso", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                Text(
                    "Regras de uso do B3 Insights",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SoftCard {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text("Biometria", fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Text(
                            "Desbloquear o app com digital ou face",
                            color = TextSecondary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Switch(
                        checked = settings.biometricEnabled,
                        onCheckedChange = { viewModel.setBiometric(it) },
                        colors = SwitchDefaults.colors(checkedTrackColor = BluePrimary)
                    )
                }
            }

            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}
