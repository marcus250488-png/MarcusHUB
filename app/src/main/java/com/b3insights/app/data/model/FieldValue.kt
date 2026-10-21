package com.b3insights.app.data.model

/**
 * Valor de indicador com status explícito.
 * Nunca substituir ausência por 0 — use UNAVAILABLE.
 */
enum class FieldStatus {
    AVAILABLE,   // veio de fonte externa
    CALCULATED,  // derivado por fórmula do app/backend
    UNAVAILABLE, // fonte não retornou
    SOURCE_CONFLICT, // fontes divergem
    STALE        // cache antigo
}

data class FieldValue(
    val value: Double? = null,
    val status: FieldStatus = FieldStatus.UNAVAILABLE,
    val source: String? = null,
    val sourceType: String? = null, // BRAPI | YAHOO | CVM | APP | BACKEND
    val updatedAt: Long? = null,
    val note: String? = null,
    val formula: String? = null,
    val inputs: Map<String, Double?>? = null
) {
    companion object {
        fun available(
            value: Double,
            source: String,
            sourceType: String = "BRAPI",
            updatedAt: Long = System.currentTimeMillis(),
            note: String? = null
        ) = FieldValue(value, FieldStatus.AVAILABLE, source, sourceType, updatedAt, note)

        fun calculated(
            value: Double,
            formula: String,
            inputs: Map<String, Double?>,
            source: String = "APP",
            updatedAt: Long = System.currentTimeMillis(),
            note: String? = null
        ) = FieldValue(
            value = value,
            status = FieldStatus.CALCULATED,
            source = source,
            sourceType = "APP",
            updatedAt = updatedAt,
            note = note,
            formula = formula,
            inputs = inputs
        )

        fun unavailable(note: String = "Não encontrado nas fontes consultadas") =
            FieldValue(status = FieldStatus.UNAVAILABLE, note = note)
    }

    fun displayOrNull(): Double? = value.takeIf {
        status == FieldStatus.AVAILABLE || status == FieldStatus.CALCULATED || status == FieldStatus.STALE
    }
}
