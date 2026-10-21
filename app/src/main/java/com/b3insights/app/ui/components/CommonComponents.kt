@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.ui.theme.*
import com.b3insights.app.util.asPercent

/** Card com cantos arredondados — adapta cor ao tema claro/escuro. */
@Composable
fun SoftCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardColor = SurfaceCard
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
            colors = CardDefaults.cardColors(
                containerColor = cardColor,
                contentColor = TextPrimary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor),
            colors = CardDefaults.cardColors(
                containerColor = cardColor,
                contentColor = TextPrimary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), content = content)
        }
    }
}

/** Pílula verde/vermelha mostrando variação percentual, com seta de alta/baixa. */
@Composable
fun ChangePill(changePercent: Double?, compact: Boolean = false) {
    val positive = (changePercent ?: 0.0) >= 0
    val bg = if (positive) GreenPositiveBg else RedNegativeBg
    val fg = if (positive) GreenPositive else RedNegative
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (positive) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = fg,
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = changePercent.asPercent(),
            color = fg,
            fontWeight = FontWeight.SemiBold,
            style = if (compact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.bodyMedium
        )
    }
}

/** Card pequeno para um indicador (ex.: "P/L: 8,72"). */
@Composable
fun IndicatorTile(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(14.dp)
    ) {
        // Máxima/mínima e demais indicadores em azul (legível no tema escuro)
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = BluePrimary)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = value, style = MaterialTheme.typography.titleMedium, color = BluePrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun EmptyState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
    }
}
