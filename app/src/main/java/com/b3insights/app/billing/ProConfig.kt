package com.b3insights.app.billing

/**
 * Acesso completo liberado no app.
 * Pagamento (Google Play Billing) será adicionado depois da publicação na Play Store.
 */
object ProConfig {
    const val PRICE_LABEL = "Grátis"
    const val PRICE_DETAIL = "Todas as funções liberadas"
    const val PLAN = "open_access"

    /** Sem limite de ativos enquanto não houver Play Billing. */
    const val FREE_MAX_ASSETS = Int.MAX_VALUE

    const val OWNER_EMAIL = "marcus.25.04.88@gmail.com"
}
