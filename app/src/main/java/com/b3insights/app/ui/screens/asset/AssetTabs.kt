package com.b3insights.app.ui.screens.asset

import com.b3insights.app.ui.theme.GreenPositive
import com.b3insights.app.ui.theme.RedNegative

import com.b3insights.app.data.model.FieldStatus
import com.b3insights.app.util.ValuationEngine
import com.b3insights.app.util.asCurrency

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.b3insights.app.data.model.*
import com.b3insights.app.ui.components.EmptyState
import com.b3insights.app.ui.components.IndicatorTile
import com.b3insights.app.ui.components.SectionTitle
import com.b3insights.app.ui.components.SoftCard
import com.b3insights.app.ui.components.ValuationMethodsCard
import com.b3insights.app.ui.theme.TextPrimary
import com.b3insights.app.ui.theme.TextSecondary
import com.b3insights.app.util.*
import com.b3insights.app.util.asMoneyForTicker

@Composable
fun OverviewTab(detail: AssetDetail) {
    val isCrypto = detail.quote.assetType == AssetType.CRIPTO
    Column(modifier = Modifier.fillMaxWidth()) {
        SoftCard {
            Text(
                if (isCrypto) "Sobre o ativo" else "Sobre a empresa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                detail.about
                    ?: if (isCrypto)
                        "Criptomoeda negociada globalmente. Preço em dólar (USD). Acompanhe o gráfico para ver o histórico."
                    else "Dado não disponível",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        SoftCard {
            Text(
                if (isCrypto) "Resumo" else "Indicadores",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (isCrypto) {
                KeyValueRow("Tipo", "Criptomoeda")
                KeyValueRow("Par", detail.quote.ticker)
                KeyValueRow("Preço", detail.quote.currentPrice.asMoneyForTicker(detail.quote.ticker))
                KeyValueRow("Variação 24h", detail.quote.dayChangePercent.asPercent())
                KeyValueRow("Volume", detail.quote.volume.asCompactVolume())
            } else {
                KeyValueRow("Setor", detail.quote.sector ?: "Dado não disponível")
                KeyValueRow("Tipo", detail.quote.assetType.label)
                KeyValueRow("Valor de mercado", detail.quote.marketCap.asCompactMoney())
                KeyValueRow("Free Float", detail.indicators.freeFloat.asPercent(showSign = false))
            }
        }
        if (!isCrypto) {
            Spacer(modifier = Modifier.height(12.dp))
            ValuationMethodsCard(
                price = detail.quote.currentPrice,
                lpa = detail.indicators.lpa,
                vpa = detail.indicators.vpa,
                pl = detail.indicators.pl,
                pvp = detail.indicators.pvp,
                dividendYieldPercent = detail.indicators.dividendYield,
                annualDividendsPerShare = detail.indicators.dividendPerShare
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SoftCard {
            Text("Desempenho", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "O desempenho por período (hoje, 1 semana, 1 mês, 6 meses, 1 ano, 5 anos) é " +
                    "calculado a partir do histórico de preço exibido no gráfico acima, ao selecionar cada período.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        SoftCard {
            Text("Últimas notícias", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(8.dp))
            if (detail.news.isEmpty()) {
                Text("Dado não disponível", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
            } else {
                detail.news.take(3).forEach { NewsRow(it) }
            }
        }
    }
}

@Composable
fun IndicatorsTab(indicators: AssetIndicators, assetType: AssetType) {
    val items = when (assetType) {
        AssetType.CRIPTO -> listOf(
            "Categoria" to "Criptomoeda",
            "Modelo" to "Ativo digital",
            "Dividendos" to "Não se aplica",
            "Balanço societário" to "Não se aplica"
        )
        AssetType.FII -> listOf(
            "P/VP" to indicators.pvp.asDecimal(),
            "DY" to indicators.dividendYield.asPercent(showSign = false),
            "Dividendo/cota" to indicators.dividendPerShare.asCurrency(),
            "Vacância" to indicators.vacancy.asPercent(showSign = false),
            "Patrimônio líquido" to indicators.netWorth.asCompactMoney(),
            "Valor de mercado" to indicators.marketCap.asCompactMoney(),
            "Nº de cotistas" to (indicators.numberOfShareholders?.toString() ?: "Dado não disponível"),
            "Nº de imóveis" to (indicators.numberOfProperties?.toString() ?: "Dado não disponível"),
            "Cap rate" to indicators.capRate.asPercent(showSign = false),
            "Rendimento mensal" to indicators.monthlyYield.asPercent(showSign = false),
            "Rendimento anual" to indicators.annualYield.asPercent(showSign = false),
            "Preço da cota" to indicators.quotaPrice.asCurrency(),
            "Valor patrimonial/cota" to indicators.quotaBookValue.asCurrency()
        )
        else -> listOf(
            "P/L" to indicators.pl.asDecimal(),
            "P/VP" to indicators.pvp.asDecimal(),
            "ROE" to indicators.roe.asPercent(showSign = false),
            "ROIC" to indicators.roic.asPercent(showSign = false),
            "DY" to indicators.dividendYield.asPercent(showSign = false),
            "Margem líquida" to indicators.netMargin.asPercent(showSign = false),
            "Margem EBITDA" to indicators.ebitdaMargin.asPercent(showSign = false),
            "Dív.líq./EBITDA" to indicators.netDebtToEbitda.asDecimal(),
            "LPA" to indicators.lpa.asCurrency(),
            "VPA" to indicators.vpa.asCurrency(),
            "Receita" to indicators.revenue.asCompactMoney(),
            "Lucro líquido" to indicators.netIncome.asCompactMoney(),
            "Cresc. receita" to indicators.revenueGrowth.asPercent(),
            "Cresc. lucro" to indicators.profitGrowth.asPercent(),
            "Valor de mercado" to indicators.marketCap.asCompactMoney(),
            "Free Float" to indicators.freeFloat.asPercent(showSign = false)
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 900.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { (label, value) ->
            IndicatorTile(label = label, value = value)
        }
    }
}

@Composable
fun DividendsTab(dividends: List<DividendEntry>) {
    if (dividends.isEmpty()) {
        EmptyState("Dado não disponível")
        return
    }
    Column {
        dividends.forEach { d ->
            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(d.type, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                        Text("Data ex: ${d.exDate.asDate()}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Data ex: ${d.exDate.asDate()}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Text(d.valuePerShare.asCurrency(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }
}

@Composable
fun BalanceTab(entries: List<BalanceSheetEntry>) {
    if (entries.isEmpty()) {
        EmptyState("Balanço patrimonial ainda não disponível para este ativo.\nOs dados são carregados quando a fonte libera o histórico.")
        return
    }
    Column {
        entries.forEach { e ->
            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(e.periodLabel, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(6.dp))
                KeyValueRow("Ativo total", e.totalAssets.asCompactMoney())
                KeyValueRow("Passivo", e.totalLiabilities.asCompactMoney())
                KeyValueRow("Patrimônio líquido", e.netWorth.asCompactMoney())
                KeyValueRow("Caixa", e.cash.asCompactMoney())
                KeyValueRow("Dívida total", e.totalDebt.asCompactMoney())
                KeyValueRow("Dívida líquida", e.netDebt.asCompactMoney())
            }
        }
    }
}

@Composable
fun ResultsTab(entries: List<ResultsEntry>) {
    if (entries.isEmpty()) {
        EmptyState("Demonstração de resultados ainda não disponível para este ativo.\nOs dados são carregados quando a fonte libera o histórico.")
        return
    }
    Column {
        entries.forEach { r ->
            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(r.periodLabel, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(6.dp))
                KeyValueRow("Receita", r.revenue.asCompactMoney())
                KeyValueRow("Lucro bruto", r.grossProfit.asCompactMoney())
                KeyValueRow("EBITDA", r.ebitda.asCompactMoney())
                KeyValueRow("EBIT", r.ebit.asCompactMoney())
                KeyValueRow("Lucro líquido", r.netIncome.asCompactMoney())
                KeyValueRow("Margem líquida", r.netMargin.asPercent(showSign = false))
            }
        }
    }
}

@Composable
fun NewsTab(news: List<NewsItem>) {
    if (news.isEmpty()) {
        EmptyState("Nenhuma notícia encontrada para este ativo no momento.")
        return
    }
    Column {
        Text(
            "Toque na notícia para ler completa",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        news.forEach { NewsRow(it) }
    }
}

@Composable
fun ReportsTab(reports: List<ReportItem>) {
    if (reports.isEmpty()) {
        EmptyState("Dado não disponível")
        return
    }
    Column {
        reports.forEach { r ->
            SoftCard(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(r.title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Text(r.date.asDate() + " • " + r.type, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                r.description?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun HistoryTab(history: List<PricePoint>) {
    if (history.isEmpty()) {
        EmptyState("Dado não disponível")
        return
    }
    Column {
        history.takeLast(30).reversed().forEach { p ->
            SoftCard(modifier = Modifier.padding(bottom = 6.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(p.timestamp.asDate(), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(p.close.asCurrency(), fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                }
            }
        }
    }
}

@Composable
private fun NewsRow(item: NewsItem) {
    val context = LocalContext.current
    SoftCard(
        modifier = Modifier.padding(bottom = 8.dp),
        onClick = {
            if (item.url.isNotBlank()) {
                runCatching {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
                }
            }
        }
    ) {
        Text(item.title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "${item.source} • ${item.date.asDate()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        item.summary?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 3)
        }
    }
}

@Composable
private fun KeyValueRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        Text(value, color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Medium, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CryptoMarketTab(detail: AssetDetail) {
    SoftCard {
        Text("Mercado", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(10.dp))
        KeyValueRow("Preço atual", detail.quote.currentPrice.asMoneyForTicker(detail.quote.ticker))
        KeyValueRow("Variação do dia", detail.quote.dayChangePercent.asPercent())
        KeyValueRow("Máxima do dia", detail.quote.dayHigh.asMoneyForTicker(detail.quote.ticker))
        KeyValueRow("Mínima do dia", detail.quote.dayLow.asMoneyForTicker(detail.quote.ticker))
        KeyValueRow("Volume", detail.quote.volume.asCompactVolume())
        KeyValueRow("Tipo", "Criptomoeda")
    }
    Spacer(modifier = Modifier.height(12.dp))
    SoftCard {
        Text("Sobre", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            detail.about
                ?: "Criptomoeda negociada em par com dólar (USD). Use o gráfico acima para ver o histórico de preço.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
fun ValuationTab(detail: AssetDetail) {
    val ind = detail.indicators
    val price = detail.quote.currentPrice
    val yearAgo = System.currentTimeMillis() - 365L * 24 * 3600 * 1000
    val annualDps = detail.dividends
        .filter { it.exDate >= yearAgo }
        .sumOf { it.valuePerShare }
        .takeIf { it > 0 }

    val snap = remember(detail) {
        ValuationEngine.compute(
            ticker = detail.quote.ticker,
            price = price,
            lpa = ind.lpa,
            vpa = ind.vpa,
            pl = ind.pl,
            pvp = ind.pvp,
            dividendYieldPercent = ind.dividendYield,
            annualDividendsPerShare = annualDps,
            profitGrowthPercent = ind.profitGrowth,
            revenueGrowthPercent = ind.revenueGrowth
        )
    }

    Column(Modifier.fillMaxWidth()) {
        SoftCard {
            Text(
                "Valuation multimétodo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Preço atual: ${price?.asCurrency() ?: "Indisponível"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Não é recomendação de compra. Cada método mostra fórmula e limitações.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(8.dp))
        listOf(snap.graham, snap.bazin, snap.lynch, snap.barsi, snap.dcf).forEach { m ->
            SoftCard {
                Text(m.title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(4.dp))
                val estVal = m.estimate.displayOrNull()
                if (estVal != null) {
                    Text(
                        estVal.asCurrency(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    val d = m.discountPercent.displayOrNull()
                    if (d != null) {
                        Text(
                            "vs cotação: %+.1f%%".format(d),
                            color = if (d >= 0) GreenPositive else RedNegative,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    val formula = m.estimate.formula
                    if (formula != null) {
                        Text("Fórmula: $formula", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    val note = m.estimate.note
                    if (note != null) {
                        Text(note, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    Text(
                        "Indisponível",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        m.estimate.note ?: "Dados insuficientes nas fontes consultadas",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Text(m.explanation, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.height(8.dp))
        }
        SoftCard {
            Text("Fontes", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(
                "Cotação e fundamentos: brapi / Yahoo (via app ou proxy Firebase quando MARKET_API_BASE estiver configurado). Cálculos: ValuationEngine no app.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
