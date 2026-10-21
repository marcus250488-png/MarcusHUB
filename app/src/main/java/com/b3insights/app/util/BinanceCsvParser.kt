package com.b3insights.app.util

import android.content.Context
import android.net.Uri
import com.b3insights.app.data.model.AssetType
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Parser do CSV "Histórico de Compras com Fiduciária" da Binance (pt-BR).
 *
 * Colunas típicas:
 * Tempo, Método, Valor a gastar, Receber Quantia, Taxa, Preço, Status, ID da Transação
 * 2026-09-10 11:53:03,Saldo,10.00 BRL,0.00002524 BTC,0.00 BRL,396196.51 BTC/BRL,Successful,N0179...
 */
object BinanceCsvParser {

    private val dateFormats = listOf(
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US),
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US),
        SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR")),
        SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    ).onEach { it.isLenient = true }

    fun parse(context: Context, uri: Uri): Result<List<BrokerageOperation>> {
        return try {
            val text = context.contentResolver.openInputStream(uri)?.use { input ->
                input.bufferedReader(Charsets.UTF_8).readText()
            } ?: return Result.failure(IllegalArgumentException("Não foi possível abrir o CSV"))
            Result.success(parseText(text))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun parseText(raw: String): List<BrokerageOperation> {
        val text = raw.removePrefix("\uFEFF") // BOM
        val lines = text.lines().map { it.trim() }.filter { it.isNotEmpty() }
        if (lines.isEmpty()) return emptyList()

        val header = splitCsvLine(lines.first()).map { it.trim().lowercase(Locale.ROOT) }
        val dataLines = if (looksLikeHeader(header)) lines.drop(1) else lines

        val ops = mutableListOf<BrokerageOperation>()
        for (line in dataLines) {
            parseRow(splitCsvLine(line), header)?.let { ops.add(it) }
        }
        return ops
    }

    private fun looksLikeHeader(cols: List<String>): Boolean {
        val joined = cols.joinToString(" ")
        return listOf("tempo", "time", "data", "status", "preço", "price", "quantidade", "amount", "transação", "transaction")
            .any { it in joined }
    }

    private fun parseRow(cols: List<String>, header: List<String>): BrokerageOperation? {
        if (cols.isEmpty()) return null

        fun col(vararg names: String): String? {
            for (n in names) {
                val i = header.indexOfFirst { it.contains(n) }
                if (i >= 0 && i < cols.size) return cols[i].trim()
            }
            return null
        }

        // Status: só Successful / Concluído
        val status = (col("status") ?: cols.getOrNull(6) ?: "").lowercase(Locale.ROOT)
        if (status.isNotEmpty() && status !in listOf("successful", "success", "concluído", "concluido", "completed", "filled")) {
            // se header tem status e não é sucesso, pula
            if (header.any { "status" in it }) return null
        }

        val timeRaw = col("tempo", "time", "data", "date") ?: cols.getOrNull(0) ?: return null
        val dateMs = parseDate(timeRaw) ?: System.currentTimeMillis()

        // "0.00002524 BTC" ou quantidade + asset separados
        val receiveRaw = col("receber", "quantidade", "amount", "executed", "qty") ?: cols.getOrNull(3) ?: ""
        val (qty, assetFromQty) = parseAmountAsset(receiveRaw)

        // "10.00 BRL" gasto
        val spentRaw = col("valor a gastar", "gastar", "total", "spend") ?: cols.getOrNull(2) ?: ""
        val (spent, spentCcy) = parseAmountAsset(spentRaw)

        // "396196.51347068 BTC/BRL"
        val priceRaw = col("preço", "preco", "price") ?: cols.getOrNull(5) ?: ""
        val (priceNum, pricePair) = parsePrice(priceRaw)

        val asset = assetFromQty
            ?: pricePair?.substringBefore("/")
            ?: return null

        val quantity = qty ?: return null
        if (quantity <= 0) return null

        // Preço médio em BRL (preferência do app)
        val priceBrl = when {
            priceNum != null && pricePair?.endsWith("BRL", ignoreCase = true) == true -> priceNum
            priceNum != null && spentCcy.equals("BRL", true) && quantity > 0 && spent != null ->
                // confere com gasto/qtd se o preço vier em outro par
                spent / quantity
            spent != null && spentCcy.equals("BRL", true) && quantity > 0 -> spent / quantity
            priceNum != null -> priceNum
            else -> return null
        }

        val ticker = normalizeCryptoTicker(asset)
        val txId = col("id da transação", "transaction", "order id", "id") ?: cols.lastOrNull() ?: ""

        return BrokerageOperation(
            ticker = ticker,
            assetType = AssetType.CRIPTO.name,
            quantity = quantity,
            price = priceBrl,
            total = spent ?: (quantity * priceBrl),
            side = "BUY", // este CSV é "Compras com Fiduciária"
            purchaseDate = dateMs,
            titleRaw = listOfNotNull(asset, txId.takeIf { it.isNotBlank() }).joinToString(" · ")
        )
    }

    /** "0.00002524 BTC" → qty + symbol */
    private fun parseAmountAsset(raw: String): Pair<Double?, String?> {
        val t = raw.trim()
        if (t.isEmpty()) return null to null
        val m = Regex(
            """([0-9]+(?:[.,][0-9]+)?)\s*([A-Za-z0-9]{2,10})?"""
        ).find(t) ?: return null to null
        val num = m.groupValues[1].replace(",", "").toDoubleOrNull()
        val sym = m.groupValues.getOrNull(2)?.uppercase(Locale.ROOT)?.ifBlank { null }
        return num to sym
    }

    /** "396196.51 BTC/BRL" → price + pair */
    private fun parsePrice(raw: String): Pair<Double?, String?> {
        val t = raw.trim()
        if (t.isEmpty()) return null to null
        val m = Regex(
            """([0-9]+(?:[.,][0-9]+)?)\s*([A-Za-z0-9]{2,10}/[A-Za-z0-9]{2,10})?"""
        ).find(t) ?: return null to null
        val num = m.groupValues[1].replace(",", "").toDoubleOrNull()
        val pair = m.groupValues.getOrNull(2)?.uppercase(Locale.ROOT)?.ifBlank { null }
        return num to pair
    }

    private fun parseDate(raw: String): Long? {
        val cleaned = raw.trim().substringBefore(".") // remove millis se houver
        for (fmt in dateFormats) {
            try {
                // Binance costuma ser horário local do export (UTC-3 no nome do arquivo)
                return fmt.parse(cleaned)?.time
            } catch (_: Exception) {
            }
        }
        return null
    }

    /**
     * Guarda ticker "BTC", "ETH"… (sem -USD) para o preço médio em R$
     * não ser multiplicado de novo pelo câmbio na carteira.
     * A cotação atual ainda resolve BTC → BTC-USD no repositório.
     */
    private fun normalizeCryptoTicker(symbol: String): String {
        val s = symbol.uppercase(Locale.ROOT)
            .substringBefore("/")
            .removeSuffix("USDT").removeSuffix("BUSD").removeSuffix("BRL").removeSuffix("USD")
            .trim()
        return when {
            s == "XBT" -> "BTC"
            s.contains("-") -> s.substringBefore("-")
            s.isNotBlank() -> s
            else -> symbol.uppercase(Locale.ROOT)
        }
    }

    private fun splitCsvLine(line: String): List<String> {
        val result = mutableListOf<String>()
        val cur = StringBuilder()
        var inQuotes = false
        for (ch in line) {
            when {
                ch == '"' -> inQuotes = !inQuotes
                ch == ',' && !inQuotes -> {
                    result.add(cur.toString())
                    cur.clear()
                }
                else -> cur.append(ch)
            }
        }
        result.add(cur.toString())
        return result
    }
}
