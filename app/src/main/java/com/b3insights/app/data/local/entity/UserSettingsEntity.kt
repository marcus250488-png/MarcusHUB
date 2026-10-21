package com.b3insights.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey val id: Int = 0,
    val darkTheme: Boolean = false,
    val autoUpdateEnabled: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val biometricEnabled: Boolean = false,
    val googleAccountEmail: String? = null,
    val lastSyncAt: Long? = null,
    val targetAcoesPct: Float = 40f,
    val targetFiisPct: Float = 30f,
    val targetEtfsPct: Float = 10f,
    val targetCriptoPct: Float = 10f,
    val targetOutrosPct: Float = 10f,
    val preferCryptoBrl: Boolean = true,
    /** Meta de patrimônio em R$ (0 = não definida). */
    val targetPatrimonio: Double = 0.0,
    /** Meta de renda mensal em proventos (R$/mês). 0 = não definida. */
    val targetRendaMensal: Double = 0.0,
    /** Se true, pull com nuvem vazia pode limpar o local (confirmação do usuário). */
    val allowEmptyCloudRestore: Boolean = false
)
