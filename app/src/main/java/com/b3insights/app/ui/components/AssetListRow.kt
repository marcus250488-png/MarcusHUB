package com.b3insights.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.util.asMoneyForTicker

@Composable
fun AssetListRow(
    ticker: String,
    name: String?,
    typeLabel: String?,
    price: Double?,
    changePercent: Double?,
    onClick: () -> Unit,
    logoUrl: String? = null,
    assetType: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AssetLogo(ticker = ticker, logoUrl = logoUrl, size = 40.dp, corner = 10.dp, assetType = assetType)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = ticker, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                text = name ?: typeLabel ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
        if (price != null) {
            Column(horizontalAlignment = Alignment.End) {
                Text(text = price.asMoneyForTicker(ticker), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(4.dp))
                ChangePill(changePercent, compact = true)
            }
        }
    }
}
