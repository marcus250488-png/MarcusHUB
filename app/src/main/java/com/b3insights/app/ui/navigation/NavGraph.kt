@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.b3insights.app.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.b3insights.app.ui.components.B3BottomNavBar
import com.b3insights.app.ui.screens.asset.AssetDetailScreen
import com.b3insights.app.ui.screens.compare.CompareScreen
import com.b3insights.app.ui.screens.explore.ExploreScreen
import com.b3insights.app.ui.screens.favorites.FavoritesScreen
import com.b3insights.app.ui.screens.home.HomeScreen
import com.b3insights.app.ui.screens.login.LoginScreen
import com.b3insights.app.ui.screens.lock.BiometricLockScreen
import com.b3insights.app.B3InsightsApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.b3insights.app.ui.screens.splash.SplashScreen
import com.b3insights.app.ui.screens.portfolio.PortfolioScreen
import com.b3insights.app.ui.screens.portfolio.AddPositionScreen
import com.b3insights.app.ui.screens.ranking.RankingHubScreen
import com.b3insights.app.ui.screens.ranking.RankingMetricsScreen
import com.b3insights.app.ui.screens.ranking.RankingListScreen
import com.b3insights.app.ui.screens.dividends.DividendAgendaScreen
import com.b3insights.app.ui.screens.news.NewsScreen
import com.b3insights.app.ui.screens.tools.*
import com.b3insights.app.ui.screens.tools.AporteAssistantScreen
import com.b3insights.app.ui.screens.tools.FiiScreenerScreen
import com.b3insights.app.ui.screens.tools.StockValuationScreen
import com.b3insights.app.ui.screens.tools.RecommendedPortfoliosScreen
import com.b3insights.app.ui.screens.settings.SettingsScreen
import com.b3insights.app.ui.screens.settings.AccountScreen
import com.b3insights.app.ui.screens.settings.LegalScreen
import com.b3insights.app.ui.screens.settings.LegalDoc

private val bottomNavRoutes = setOf(
    Screen.Home.route, Screen.Favorites.route, Screen.Portfolio.route, Screen.Ranking.route, Screen.Search.route, Screen.Settings.route
)

@Composable
fun B3InsightsNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.Unspecified,
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                B3BottomNavBar(currentRoute = currentRoute) { screen ->
                    if (screen.route == Screen.Home.route) {
                        // Sempre volta para a primeira tela (Início limpo)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(padding),
            enterTransition = { fadeIn(animationSpec = tween(150)) },
            exitTransition = { fadeOut(animationSpec = tween(100)) },
            popEnterTransition = { fadeIn(animationSpec = tween(150)) },
            popExitTransition = { fadeOut(animationSpec = tween(100)) },
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(onFinished = {
                    val loggedIn = runCatching {
                        FirebaseAuth.getInstance().currentUser != null
                    }.getOrDefault(false)
                    val bioEnabled = runCatching {
                        runBlocking {
                            B3InsightsApp.instance.database.userSettingsDao()
                                .getSettingsOnce()?.biometricEnabled == true
                        }
                    }.getOrDefault(false)
                    val dest = when {
                        loggedIn && bioEnabled -> Screen.Lock.route
                        loggedIn -> Screen.Home.route
                        else -> Screen.Login.route
                    }
                    navController.navigate(dest) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Lock.route) {
                BiometricLockScreen(onUnlocked = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Lock.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Login.route) {
                LoginScreen(onLoggedIn = {
                    val bioEnabled = runCatching {
                        runBlocking {
                            B3InsightsApp.instance.database.userSettingsDao()
                                .getSettingsOnce()?.biometricEnabled == true
                        }
                    }.getOrDefault(false)
                    val dest = if (bioEnabled) Screen.Lock.route else Screen.Home.route
                    navController.navigate(dest) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                })
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onExploreClick = { category -> navController.navigate(Screen.Explore.createRoute(category)) },
                    onPortfolioClick = {
                        navController.navigate(Screen.Portfolio.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onRankingClick = {
                        navController.navigate(Screen.Ranking.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onAccountClick = { navController.navigate(Screen.Account.route) }
                )
            }
            composable(Screen.Search.route) {
                HomeScreen(
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onExploreClick = { category -> navController.navigate(Screen.Explore.createRoute(category)) },
                    onPortfolioClick = {
                        navController.navigate(Screen.Portfolio.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onRankingClick = {
                        navController.navigate(Screen.Ranking.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onAccountClick = { navController.navigate(Screen.Account.route) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) })
            }
            composable(Screen.Portfolio.route) {
                PortfolioScreen(
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onAddClick = { t, ty ->
                        navController.navigate(Screen.PortfolioAdd.createRoute(t, ty))
                    }
                )
            }
            composable(
                route = Screen.PortfolioBuy.route,
                arguments = listOf(
                    navArgument("ticker") { },
                    navArgument("type") { }
                )
            ) { entry ->
                val buyTicker = entry.arguments?.getString("ticker")?.takeIf { it != "_" }
                val buyType = entry.arguments?.getString("type")?.takeIf { it != "_" }
                val vm: com.b3insights.app.viewmodel.PortfolioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                AddPositionScreen(
                    initialTicker = buyTicker.orEmpty(),
                    initialType = buyType,
                    onBack = { navController.popBackStack() },
                    onConfirm = { ticker, type, qty, price, purchaseDate, notes, tags, side, syncFin, valorBrl ->
                        if (vm.canAddAsset(ticker)) {
                            vm.addPosition(ticker, type, qty, price, purchaseDate, notes, tags, side, syncFin, valorBrl)
                            navController.popBackStack()
                        }
                    },
                    onConfirmBatch = { ops ->
                        for (op in ops) {
                            if (!vm.canAddAsset(op.ticker)) break
                            vm.addPosition(op.ticker, op.assetType, op.quantity, op.price, op.purchaseDate, "Importado", null, op.side)
                        }
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Screen.PortfolioAdd.route,
                arguments = listOf(
                    navArgument("ticker") { type = androidx.navigation.NavType.StringType; defaultValue = "_" },
                    navArgument("type") { type = androidx.navigation.NavType.StringType; defaultValue = "_" }
                )
            ) { entry ->
                val buyTicker = entry.arguments?.getString("ticker")?.takeIf { it != "_" }
                val buyType = entry.arguments?.getString("type")?.takeIf { it != "_" }
                val vm: com.b3insights.app.viewmodel.PortfolioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                AddPositionScreen(
                    initialTicker = buyTicker.orEmpty(),
                    initialType = buyType,
                    onBack = { navController.popBackStack() },
                    onConfirm = { ticker, type, qty, price, purchaseDate, notes, tags, side, syncFin, valorBrl ->
                        if (vm.canAddAsset(ticker)) {
                            vm.addPosition(ticker, type, qty, price, purchaseDate, notes, tags, side, syncFin, valorBrl)
                            navController.popBackStack()
                        }
                    },
                    onConfirmBatch = { ops ->
                        var blocked = false
                        for (op in ops) {
                            if (!vm.canAddAsset(op.ticker)) { blocked = true; break }
                            vm.addPosition(
                                ticker = op.ticker,
                                assetType = op.assetType,
                                quantity = op.quantity,
                                purchasePrice = op.price,
                                purchaseDate = op.purchaseDate,
                                notes = "Importado",
                                tags = null,
                                side = op.side
                            )
                        }
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Compare.route) {
                CompareScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onToolsClick = { navController.navigate(Screen.Tools.route) },
                    onCompareClick = { navController.navigate(Screen.Compare.route) },
                    onAccountClick = { navController.navigate(Screen.Account.route) },
                    onPrivacyClick = { navController.navigate(Screen.Privacy.route) },
                    onTermsClick = { navController.navigate(Screen.Terms.route) },
                    onSignedOut = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Account.route) {
                AccountScreen(
                    onBack = { navController.popBackStack() },
                    onSignedOut = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Privacy.route) {
                LegalScreen(doc = LegalDoc.PRIVACY, onBack = { navController.popBackStack() })
            }
            composable(Screen.Terms.route) {
                LegalScreen(doc = LegalDoc.TERMS, onBack = { navController.popBackStack() })
            }
            composable(
                route = Screen.AssetDetail.route,
                arguments = listOf(navArgument("ticker") { })
            ) { backStackEntry ->
                val ticker = backStackEntry.arguments?.getString("ticker") ?: ""
                AssetDetailScreen(
                    ticker = ticker,
                    onBack = { navController.popBackStack() },
                    onBuy = { t, type ->
                        navController.navigate(Screen.PortfolioAdd.createRoute(t, type))
                    }
                )
            }
            composable(
                route = Screen.Explore.route,
                arguments = listOf(navArgument("category") { })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "ACOES"
                ExploreScreen(
                    categoryKey = category,
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.Ranking.route) {
                RankingHubScreen(
                    onCategoryClick = { cat ->
                        navController.navigate(Screen.RankingMetrics.createRoute(cat.key))
                    },
                    onDividendAgendaClick = {
                        navController.navigate(Screen.DividendAgenda.route)
                    },
                    onNewsClick = {
                        navController.navigate(Screen.News.route)
                    }
                )
            }
            composable(Screen.DividendAgenda.route) {
                DividendAgendaScreen(
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.News.route) {
                NewsScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.Tools.route) {
                ToolsHubScreen(
                    onBack = { navController.popBackStack() },
                    onOpen = { tool -> navController.navigate(Screen.ToolsDetail.createRoute(tool)) }
                )
            }
            composable(Screen.ToolsDetail.route) { entry ->
                val tool = entry.arguments?.getString("tool") ?: "alerts"
                when (tool) {
                    "daily" -> DailySummaryScreen(onBack = { navController.popBackStack() })
                    "goal" -> PatrimonioGoalScreen(onBack = { navController.popBackStack() })
                    "alerts" -> AlertsScreen(onBack = { navController.popBackStack() })
                    "proventos" -> ProventosScreen(onBack = { navController.popBackStack() })
                    "performance" -> {
                        // valores aproximados — tela recalcula snapshot
                        PerformanceScreen(onBack = { navController.popBackStack() })
                    }
                    "allocation" -> AllocationScreen(actual = emptyMap(), onBack = { navController.popBackStack() })
                    "simulator" -> SimulatorScreen(onBack = { navController.popBackStack() })
                    "aporte" -> AporteAssistantScreen(
                        onBack = { navController.popBackStack() },
                        onAssetClick = { t -> navController.navigate(Screen.AssetDetail.createRoute(t)) }
                    )
                    "recommended" -> RecommendedPortfoliosScreen(
                        onBack = { navController.popBackStack() },
                        onAssetClick = { t -> navController.navigate(Screen.AssetDetail.createRoute(t)) }
                    )
                    "export" -> ExportScreen(onBack = { navController.popBackStack() })
                    "fii_screener" -> FiiScreenerScreen(
                        onBack = { navController.popBackStack() },
                        onAssetClick = { t -> navController.navigate(Screen.AssetDetail.createRoute(t)) }
                    )
                    "stock_valuation" -> StockValuationScreen(
                        onBack = { navController.popBackStack() },
                        onAssetClick = { t -> navController.navigate(Screen.AssetDetail.createRoute(t)) }
                    )
                    else -> AlertsScreen(onBack = { navController.popBackStack() })
                }
            }
            composable(
                route = Screen.RankingMetrics.route,
                arguments = listOf(navArgument("category") { })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "ACOES"
                RankingMetricsScreen(
                    categoryKey = category,
                    onMetricClick = { metric ->
                        navController.navigate(Screen.RankingList.createRoute(category, metric.name))
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(
                route = Screen.RankingList.route,
                arguments = listOf(navArgument("category") { }, navArgument("metric") { })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: "ACOES"
                val metric = backStackEntry.arguments?.getString("metric") ?: "MAIOR_CAPITALIZACAO"
                RankingListScreen(
                    categoryKey = category,
                    metricKey = metric,
                    onAssetClick = { ticker -> navController.navigate(Screen.AssetDetail.createRoute(ticker)) },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}