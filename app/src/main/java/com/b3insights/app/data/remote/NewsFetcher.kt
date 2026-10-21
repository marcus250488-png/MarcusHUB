package com.b3insights.app.data.remote

import com.b3insights.app.data.model.NewsItem
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/**
 * Notícias de mercado via RSS (Google News / InfoMoney).
 * Yahoo Finance search frequentemente retorna news=[] ou 429 no mobile.
 */
object NewsFetcher {

    fun fetchNewsForTicker(ticker: String, limit: Int = 12): List<NewsItem> {
        val t = ticker.trim().uppercase().removeSuffix(".SA")
        val base = t.substringBefore("-") // BTC-USD -> BTC
        val queries = listOf(
            "https://news.google.com/rss/search?q=${java.net.URLEncoder.encode(base, "UTF-8")}+OR+${java.net.URLEncoder.encode(t, "UTF-8")}&hl=pt-BR&gl=BR&ceid=BR:pt-419",
            "https://news.google.com/rss/search?q=${java.net.URLEncoder.encode(base + " ação OR FII OR fundo", "UTF-8")}&hl=pt-BR&gl=BR&ceid=BR:pt-419"
        )
        val out = linkedMapOf<String, NewsItem>()
        for (src in queries) {
            if (out.size >= limit) break
            runCatching { parseRss(src) }.getOrDefault(emptyList()).forEach { item ->
                if (out.size >= limit) return@forEach
                val key = item.title.lowercase().take(80)
                if (key.isNotBlank()) out.putIfAbsent(key, item)
            }
        }
        // Fallback mercado se nada específico
        if (out.isEmpty()) {
            fetchMarketNews(limit).forEach { item ->
                if (out.size >= limit) return@forEach
                out.putIfAbsent(item.title.lowercase().take(80), item)
            }
        }
        return out.values.toList()
    }

    fun fetchMarketNews(limit: Int = 20): List<NewsItem> {
        val sources = listOf(
            "https://news.google.com/rss/search?q=Ibovespa+OR+%22Bolsa+de+Valores%22&hl=pt-BR&gl=BR&ceid=BR:pt-419",
            "https://news.google.com/rss/search?q=B3+mercado+a%C3%A7%C3%B5es&hl=pt-BR&gl=BR&ceid=BR:pt-419",
            "https://www.infomoney.com.br/feed/"
        )
        val out = linkedMapOf<String, NewsItem>()
        for (src in sources) {
            if (out.size >= limit) break
            runCatching { parseRss(src) }.getOrDefault(emptyList()).forEach { item ->
                if (out.size >= limit) return@forEach
                val key = item.title.lowercase().take(80)
                if (key.isNotBlank()) out.putIfAbsent(key, item)
            }
        }
        return out.values.toList()
    }

    private fun parseRss(feedUrl: String): List<NewsItem> {
        val conn = URL(feedUrl).openConnection()
        conn.setRequestProperty(
            "User-Agent",
            "Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36 Chrome/120.0.0.0 Mobile Safari/537.36"
        )
        conn.connectTimeout = 12000
        conn.readTimeout = 12000
        conn.getInputStream().use { input ->
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = false
            val parser = factory.newPullParser()
            parser.setInput(input, "UTF-8")

            val items = mutableListOf<NewsItem>()
            var event = parser.eventType
            var inItem = false
            var title = ""
            var link = ""
            var pubDate = ""
            var description = ""
            var source = "Mercado"
            var imageUrl: String? = null

            while (event != XmlPullParser.END_DOCUMENT) {
                val name = parser.name
                when (event) {
                    XmlPullParser.START_TAG -> {
                        when {
                            name.equals("item", true) -> {
                                inItem = true
                                title = ""; link = ""; pubDate = ""; description = ""
                                source = "Mercado"; imageUrl = null
                            }
                            inItem && name.equals("title", true) -> title = parser.nextText().orEmpty()
                            inItem && name.equals("link", true) -> link = parser.nextText().orEmpty()
                            inItem && name.equals("pubDate", true) -> pubDate = parser.nextText().orEmpty()
                            inItem && name.equals("description", true) -> description = parser.nextText().orEmpty()
                            inItem && name.equals("source", true) -> source = parser.nextText().orEmpty().ifBlank { "Mercado" }
                            inItem && (name.equals("enclosure", true) || name.equals("content", true)) -> {
                                val url = parser.getAttributeValue(null, "url")
                                if (!url.isNullOrBlank() && imageUrl == null) imageUrl = url
                            }
                            inItem && name.equals("thumbnail", true) -> {
                                val url = parser.getAttributeValue(null, "url")
                                if (!url.isNullOrBlank()) imageUrl = url
                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if (name.equals("item", true) && inItem) {
                            inItem = false
                            if (title.isNotBlank() && link.isNotBlank()) {
                                // Tenta extrair imagem da description HTML
                                if (imageUrl == null) {
                                    val img = Regex("""src=["']([^"']+)["']""").find(description)?.groupValues?.getOrNull(1)
                                    if (!img.isNullOrBlank()) imageUrl = img
                                }
                                // Fonte no final do título do Google: "Título - VEJA"
                                if (source == "Mercado" && title.contains(" - ")) {
                                    val parts = title.split(" - ")
                                    if (parts.size >= 2) {
                                        source = parts.last().trim()
                                        title = parts.dropLast(1).joinToString(" - ").trim()
                                    }
                                }
                                items.add(
                                    NewsItem(
                                        title = title,
                                        source = source,
                                        date = parseDate(pubDate),
                                        summary = description
                                            .replace(Regex("<[^>]+>"), " ")
                                            .replace("&nbsp;", " ")
                                            .replace(Regex("\\s+"), " ")
                                            .trim()
                                            .ifBlank { null },
                                        imageUrl = imageUrl,
                                        url = link
                                    )
                                )
                            }
                        }
                    }
                }
                event = parser.next()
            }
            return items
        }
    }

    private fun parseDate(raw: String): Long {
        if (raw.isBlank()) return 0L
        val patterns = listOf(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            "EEE, dd MMM yyyy HH:mm:ss zzz"
        )
        for (p in patterns) {
            try {
                val sdf = SimpleDateFormat(p, Locale.US)
                sdf.timeZone = TimeZone.getTimeZone("UTC")
                return sdf.parse(raw)?.time ?: continue
            } catch (_: Exception) {
            }
        }
        return 0L
    }
}
