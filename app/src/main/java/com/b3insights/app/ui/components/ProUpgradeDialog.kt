package com.b3insights.app.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

/** Não bloqueia mais: todas as funções estão liberadas. */
@Composable
fun ProUpgradeDialog(
    onDismiss: () -> Unit,
    title: String = "Recurso liberado",
    message: String = "Todas as funções do app estão disponíveis.",
    detail: String = "Pagamento pela Play Store sera adicionado em uma atualizacao futura."
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Text(message + "\n\n" + detail)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        }
    )
}

fun requireProAccess(): Boolean = true
