@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.b3insights.app.ui.navigation.Screen
import com.b3insights.app.ui.theme.BluePrimary
import com.b3insights.app.ui.theme.DividerColor
import com.b3insights.app.ui.theme.SurfaceCard
import com.b3insights.app.ui.theme.TextSecondary

data class BottomNavItem(val screen: Screen, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Início", Icons.Filled.Home),
    BottomNavItem(Screen.Favorites, "Favoritos", Icons.Filled.Star),
    BottomNavItem(Screen.Portfolio, "Ativos", Icons.Filled.AccountBalanceWallet),
    BottomNavItem(Screen.Ranking, "Ranking", Icons.Filled.EmojiEvents),
    BottomNavItem(Screen.Search, "Buscar", Icons.Filled.Search),
    BottomNavItem(Screen.Settings, "Ajustes", Icons.Filled.Settings)
)

/** Barra inferior clara: ícone + rótulo, e uma linha azul embaixo da aba selecionada. */
@Composable
fun B3BottomNavBar(currentRoute: String?, onNavigate: (Screen) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(SurfaceCard)
    ) {
        Box(Modifier.fillMaxWidth().height(1.dp).background(DividerColor))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(top = 6.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            bottomNavItems.forEach { item ->
                val selected = currentRoute == item.screen.route
                NavTab(item, selected) { onNavigate(item.screen) }
            }
        }
    }
}

@Composable
private fun RowScope.NavTab(item: BottomNavItem, selected: Boolean, onClick: () -> Unit) {
    val color = if (selected) BluePrimary else TextSecondary
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(item.icon, contentDescription = item.label, tint = color, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(2.dp))
        Text(
            item.label,
            color = color,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            maxLines = 1
        )
        Spacer(Modifier.height(3.dp))
        Box(
            Modifier
                .width(28.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (selected) BluePrimary else androidx.compose.ui.graphics.Color.Transparent)
        )
    }
}
