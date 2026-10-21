package com.b3insights.app.ui.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0017\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001dB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0017\u001e\u001f !\"#$%&\'()*+,-./01234\u00a8\u00065"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "Account", "AssetDetail", "Compare", "DividendAgenda", "Explore", "Favorites", "Home", "Lock", "Login", "News", "Portfolio", "PortfolioAdd", "PortfolioBuy", "Privacy", "Ranking", "RankingList", "RankingMetrics", "Search", "Settings", "Splash", "Terms", "Tools", "ToolsDetail", "Lcom/b3insights/app/ui/navigation/Screen$Account;", "Lcom/b3insights/app/ui/navigation/Screen$AssetDetail;", "Lcom/b3insights/app/ui/navigation/Screen$Compare;", "Lcom/b3insights/app/ui/navigation/Screen$DividendAgenda;", "Lcom/b3insights/app/ui/navigation/Screen$Explore;", "Lcom/b3insights/app/ui/navigation/Screen$Favorites;", "Lcom/b3insights/app/ui/navigation/Screen$Home;", "Lcom/b3insights/app/ui/navigation/Screen$Lock;", "Lcom/b3insights/app/ui/navigation/Screen$Login;", "Lcom/b3insights/app/ui/navigation/Screen$News;", "Lcom/b3insights/app/ui/navigation/Screen$Portfolio;", "Lcom/b3insights/app/ui/navigation/Screen$PortfolioAdd;", "Lcom/b3insights/app/ui/navigation/Screen$PortfolioBuy;", "Lcom/b3insights/app/ui/navigation/Screen$Privacy;", "Lcom/b3insights/app/ui/navigation/Screen$Ranking;", "Lcom/b3insights/app/ui/navigation/Screen$RankingList;", "Lcom/b3insights/app/ui/navigation/Screen$RankingMetrics;", "Lcom/b3insights/app/ui/navigation/Screen$Search;", "Lcom/b3insights/app/ui/navigation/Screen$Settings;", "Lcom/b3insights/app/ui/navigation/Screen$Splash;", "Lcom/b3insights/app/ui/navigation/Screen$Terms;", "Lcom/b3insights/app/ui/navigation/Screen$Tools;", "Lcom/b3insights/app/ui/navigation/Screen$ToolsDetail;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Account;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Account extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Account INSTANCE = null;
        
        private Account() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$AssetDetail;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "ticker", "app_debug"})
    public static final class AssetDetail extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.AssetDetail INSTANCE = null;
        
        private AssetDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Compare;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Compare extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Compare INSTANCE = null;
        
        private Compare() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$DividendAgenda;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class DividendAgenda extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.DividendAgenda INSTANCE = null;
        
        private DividendAgenda() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Explore;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "category", "app_debug"})
    public static final class Explore extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Explore INSTANCE = null;
        
        private Explore() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String category) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Favorites;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Favorites extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Favorites INSTANCE = null;
        
        private Favorites() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Home;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Home extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Home INSTANCE = null;
        
        private Home() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Lock;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Lock extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Lock INSTANCE = null;
        
        private Lock() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Login;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Login extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Login INSTANCE = null;
        
        private Login() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$News;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class News extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.News INSTANCE = null;
        
        private News() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Portfolio;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Portfolio extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Portfolio INSTANCE = null;
        
        private Portfolio() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0004\u00a8\u0006\u0007"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$PortfolioAdd;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "ticker", "type", "app_debug"})
    public static final class PortfolioAdd extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.PortfolioAdd INSTANCE = null;
        
        private PortfolioAdd() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.Nullable()
        java.lang.String ticker, @org.jetbrains.annotations.Nullable()
        java.lang.String type) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u00a8\u0006\u0007"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$PortfolioBuy;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "ticker", "type", "app_debug"})
    public static final class PortfolioBuy extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.PortfolioBuy INSTANCE = null;
        
        private PortfolioBuy() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String ticker, @org.jetbrains.annotations.NotNull()
        java.lang.String type) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Privacy;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Privacy extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Privacy INSTANCE = null;
        
        private Privacy() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Ranking;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Ranking extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Ranking INSTANCE = null;
        
        private Ranking() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004\u00a8\u0006\u0007"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$RankingList;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "category", "metric", "app_debug"})
    public static final class RankingList extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.RankingList INSTANCE = null;
        
        private RankingList() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String category, @org.jetbrains.annotations.NotNull()
        java.lang.String metric) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$RankingMetrics;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "category", "app_debug"})
    public static final class RankingMetrics extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.RankingMetrics INSTANCE = null;
        
        private RankingMetrics() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String category) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Search;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Search extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Search INSTANCE = null;
        
        private Search() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Settings;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Settings extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Settings INSTANCE = null;
        
        private Settings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Splash;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Splash extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Splash INSTANCE = null;
        
        private Splash() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Terms;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Terms extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Terms INSTANCE = null;
        
        private Terms() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$Tools;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Tools extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.Tools INSTANCE = null;
        
        private Tools() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004\u00a8\u0006\u0006"}, d2 = {"Lcom/b3insights/app/ui/navigation/Screen$ToolsDetail;", "Lcom/b3insights/app/ui/navigation/Screen;", "()V", "createRoute", "", "tool", "app_debug"})
    public static final class ToolsDetail extends com.b3insights.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.b3insights.app.ui.navigation.Screen.ToolsDetail INSTANCE = null;
        
        private ToolsDetail() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String createRoute(@org.jetbrains.annotations.NotNull()
        java.lang.String tool) {
            return null;
        }
    }
}