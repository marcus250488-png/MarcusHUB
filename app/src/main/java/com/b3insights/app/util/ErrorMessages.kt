package com.b3insights.app.util

/** Mensagens amigáveis a partir dos códigos internos de erro da API. */
fun mapErrorMessage(code: String?): String = when (code) {
    "no_connection" -> "Sem conexão com a internet. Verifique o Wi‑Fi ou os dados móveis."
    "asset_not_found" -> "Ativo não encontrado. Confira o ticker e tente de novo."
    "update_error" -> "Não foi possível atualizar os dados agora. Tente novamente em instantes."
    "timeout" -> "A consulta demorou demais. Tente novamente."
    else -> code?.takeIf { it.isNotBlank() && !it.contains("_") } ?: "Algo deu errado. Tente novamente."
}
