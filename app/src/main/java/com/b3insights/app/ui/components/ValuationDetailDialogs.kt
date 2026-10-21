package com.b3insights.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.util.Valuation
import com.b3insights.app.util.asCurrency
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ValuationMethodsCard(
    price: Double?,
    lpa: Double?,
    vpa: Double?,
    pl: Double?,
    pvp: Double?,
    dividendYieldPercent: Double?,
    annualDividendsPerShare: Double? = null,
    dataSource: String = "Yahoo Finance / brapi"
) {
    val graham = remember(lpa, vpa, price, pl, pvp) {
        Valuation.grahamBreakdown(lpa, vpa, price, pl, pvp)
    }
    val bazin = remember(price, dividendYieldPercent, annualDividendsPerShare) {
        Valuation.bazinBreakdown(price, dividendYieldPercent, annualDividendsPerShare)
    }
    val marginG = Valuation.safetyMargin(graham?.fairPrice, price)
    val marginB = Valuation.safetyMargin(bazin?.ceilingPrice, price)
    var showGraham by remember { mutableStateOf(false) }
    var showBazin by remember { mutableStateOf(false) }
    var showHow by remember { mutableStateOf(false) }
    val today = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(Date())
    }

    SoftCard {
        Text(
            "Valuation",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Réguas de Graham e Bazin — toque para ver o cálculo",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = graham != null) { showGraham = true }
                .padding(vertical = 6.dp)
        ) {
            Text("Valor por Graham", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            Text(
                graham?.fairPrice?.asCurrency() ?: "Dado não disponível",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            if (marginG != null) {
                Text(
                    "Margem de segurança: %+.1f%%".format(marginG),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (marginG >= 0) androidx.compose.ui.graphics.Color(0xFF1E9E5A)
                    else androidx.compose.ui.graphics.Color(0xFFE23D3D)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = bazin != null) { showBazin = true }
                .padding(vertical = 6.dp)
        ) {
            Text("Preço-teto Bazin", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            Text(
                bazin?.ceilingPrice?.asCurrency() ?: "Dado não disponível",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            if (marginB != null) {
                Text(
                    "Margem de segurança: %+.1f%%".format(marginB),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (marginB >= 0) androidx.compose.ui.graphics.Color(0xFF1E9E5A)
                    else androidx.compose.ui.graphics.Color(0xFFE23D3D)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Cotação atual: ${price?.asCurrency() ?: "—"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "ⓘ Como calculamos?",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable { showHow = true }
        )
    }

    if (showGraham && graham != null) {
        AlertDialog(
            onDismissRequest = { showGraham = false },
            title = { Text("Preço justo Graham", fontWeight = FontWeight.Bold) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text(graham.fairPrice.asCurrency(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    line("LPA", graham.lpa.asCurrency())
                    line("VPA", graham.vpa.asCurrency())
                    line("Origem LPA", graham.lpaSource)
                    line("Origem VPA", graham.vpaSource)
                    line("Fórmula", graham.formula)
                    line("Data de referência", today)
                    line("Fonte dos dados", dataSource)
                    line("Última atualização", today)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(graham.note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                TextButton(onClick = { showGraham = false }) { Text("Fechar") }
            }
        )
    }

    if (showBazin && bazin != null) {
        AlertDialog(
            onDismissRequest = { showBazin = false },
            title = { Text("Preço-teto Bazin", fontWeight = FontWeight.Bold) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text(bazin.ceilingPrice.asCurrency(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    line("Dividendos considerados", bazin.dividendsPerShare.asCurrency() + " por ação")
                    line("Período", bazin.dividendPeriod)
                    line("Yield mínimo", "%.0f%% a.a.".format(bazin.yieldTargetPercent))
                    line("Fórmula", bazin.formula)
                    line("Data de referência", today)
                    line("Fonte dos dados", dataSource)
                    line("Última atualização", today)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(bazin.note, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            },
            confirmButton = {
                TextButton(onClick = { showBazin = false }) { Text("Fechar") }
            }
        )
    }

    if (showHow) {
        AlertDialog(
            onDismissRequest = { showHow = false },
            title = { Text("Como calculamos?", fontWeight = FontWeight.Bold) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text("Graham (preço justo)", fontWeight = FontWeight.SemiBold)
                    Text(
                        "√(22,5 × LPA × VPA). O fator 22,5 combina P/L 15 e P/VP 1,5. " +
                            "Indica se a ação parece barata em relação a lucro e patrimônio.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Bazin (preço-teto)", fontWeight = FontWeight.SemiBold)
                    Text(
                        "Dividendos por ação ÷ 0,06. O 6% é o dividend yield mínimo clássico do método. " +
                            "Usamos os proventos dos últimos 12 meses (LTM), estimados pelo Dividend Yield × cotação " +
                            "(ou soma 12m quando a API fornecer). Não usamos um provento isolado.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Outras plataformas podem usar média de 5 anos de dividendos — o resultado muda. " +
                            "No B3 Insights o padrão é LTM (12 meses).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showHow = false }) { Text("Entendi") }
            }
        )
    }
}

@Composable
private fun line(label: String, value: String) {
    Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(6.dp))
}
