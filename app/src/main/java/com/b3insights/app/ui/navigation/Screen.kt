package com.b3insights.app.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Lock : Screen("lock")
    object Home : Screen("home")
    object Search : Screen("search")
    object Favorites : Screen("favorites")
    object Portfolio : Screen("portfolio")
    object PortfolioAdd : Screen("portfolio_add/{ticker}/{type}") {
        fun createRoute(ticker: String? = null, type: String? = null): String {
            val t = (ticker ?: "_").replace("/", "_").replace(".", "_").ifBlank { "_" }
            val ty = (type ?: "_").replace("/", "_").ifBlank { "_" }
            return "portfolio_add/$t/$ty"
        }
    }
    object PortfolioBuy : Screen("portfolio_buy/{ticker}/{type}") {
        fun createRoute(ticker: String, type: String): String {
            val t = ticker.replace("/", "_").replace(".", "_")
            val ty = type.replace("/", "_")
            return "portfolio_buy/$t/$ty"
        }
    }
    object Settings : Screen("settings")
    object Account : Screen("account")
    object Privacy : Screen("privacy")
    object Terms : Screen("terms")
    object Compare : Screen("compare")
    object Ranking : Screen("ranking")
    object DividendAgenda : Screen("dividend_agenda")
    object News : Screen("news")
    object Tools : Screen("tools")
    object ToolsDetail : Screen("tools/{tool}") {
        fun createRoute(tool: String) = "tools/$tool"
    }

    object AssetDetail : Screen("asset/{ticker}") {
        fun createRoute(ticker: String) = "asset/$ticker"
    }

    object Explore : Screen("explore/{category}") {
        fun createRoute(category: String) = "explore/$category"
    }

    object RankingMetrics : Screen("ranking_metrics/{category}") {
        fun createRoute(category: String) = "ranking_metrics/$category"
    }

    object RankingList : Screen("ranking_list/{category}/{metric}") {
        fun createRoute(category: String, metric: String) = "ranking_list/$category/$metric"
    }
}
