package com.b3insights.app.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

private val brLocale = Locale("pt", "BR")
private val usLocale = Locale.US

fun Double?.asCurrency(currencyCode: String = "BRL"): String {
    if (this == null) return "Dado não disponível"
    val code = currencyCode.uppercase()
    return try {
        val locale = if (code == "BRL") brLocale else usLocale
        val format = NumberFormat.getCurrencyInstance(locale)
        format.currency = Currency.getInstance(code)
        format.format(this)
    } catch (e: Exception) {
        val symbol = when (code) {
            "USD" -> "US$ "
            "EUR" -> "€ "
            else -> "R$ "
        }
        symbol + String.format(brLocale, "%,.2f", this)
    }
}

/** Formata preço conforme o ticker: cripto em USD usa US$; demais em R$. */
fun Double?.asMoneyForTicker(ticker: String?): String {
    val t = ticker?.uppercase().orEmpty()
    return when {
        t.endsWith("-USD") || t.endsWith("USDT") -> asCurrency("USD")
        t.endsWith("-BRL") -> asCurrency("BRL")
        isUsStockTicker(t) -> asCurrency("USD")
        else -> asCurrency("BRL")
    }
}

fun Double?.asPercent(showSign: Boolean = true): String {
    if (this == null) return "Dado não disponível"
    val sign = if (showSign && this > 0) "+" else ""
    return "$sign${String.format(brLocale, "%.2f", this)}%"
}

fun Double?.asDecimal(digits: Int = 2): String {
    if (this == null) return "Dado não disponível"
    return String.format(brLocale, "%.${digits}f", this)
}

fun Long?.asCompactVolume(): String {
    if (this == null) return "Dado não disponível"
    return when {
        this >= 1_000_000_000 -> String.format(brLocale, "%.1f B", this / 1_000_000_000.0)
        this >= 1_000_000 -> String.format(brLocale, "%.1f M", this / 1_000_000.0)
        this >= 1_000 -> String.format(brLocale, "%.1f K", this / 1_000.0)
        else -> this.toString()
    }
}

fun Double?.asCompactMoney(): String {
    if (this == null) return "Dado não disponível"
    val v = this
    return when {
        v >= 1_000_000_000 -> "R$ " + String.format(brLocale, "%.1f bi", v / 1_000_000_000.0)
        v >= 1_000_000 -> "R$ " + String.format(brLocale, "%.1f mi", v / 1_000_000.0)
        else -> v.asCurrency("BRL")
    }
}

fun Long?.asDateTime(): String {
    if (this == null) return "Dado não disponível"
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", brLocale)
    return sdf.format(Date(this))
}

fun Long?.asDate(): String {
    if (this == null) return "Dado não disponível"
    val sdf = SimpleDateFormat("dd/MM/yyyy", brLocale)
    return sdf.format(Date(this))
}
