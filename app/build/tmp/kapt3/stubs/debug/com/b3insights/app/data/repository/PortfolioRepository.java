package com.b3insights.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006Jr\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0014\u001a\u00020\n2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001cJ\u0012\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001f0\u001eJ\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0\u001f0\u001eJ\u001a\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001f0\u001e2\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\r2\u0006\u0010%\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010&J\u0016\u0010\'\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006("}, d2 = {"Lcom/b3insights/app/data/repository/PortfolioRepository;", "", "portfolioDao", "Lcom/b3insights/app/data/local/dao/PortfolioDao;", "patrimonioHistoricoDao", "Lcom/b3insights/app/data/local/dao/PatrimonioHistoricoDao;", "(Lcom/b3insights/app/data/local/dao/PortfolioDao;Lcom/b3insights/app/data/local/dao/PatrimonioHistoricoDao;)V", "addPosition", "", "ticker", "", "assetType", "quantity", "", "averagePrice", "purchasePrice", "purchaseDate", "", "notes", "tags", "side", "syncFinanceiro", "", "valorBrl", "(Ljava/lang/String;Ljava/lang/String;DDDJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;ZDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePosition", "entity", "Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;", "(Lcom/b3insights/app/data/local/entity/PortfolioPositionEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPositions", "Lkotlinx/coroutines/flow/Flow;", "", "getPatrimonioHistorico", "Lcom/b3insights/app/data/local/entity/PatrimonioHistoricoEntity;", "getPositionsForTicker", "recordPatrimonioSnapshot", "totalValue", "totalInvested", "(DDLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePosition", "app_debug"})
public final class PortfolioRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.PortfolioDao portfolioDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.b3insights.app.data.local.dao.PatrimonioHistoricoDao patrimonioHistoricoDao = null;
    
    public PortfolioRepository(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.dao.PortfolioDao portfolioDao, @org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.dao.PatrimonioHistoricoDao patrimonioHistoricoDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity>> getAllPositions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.PortfolioPositionEntity>> getPositionsForTicker(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addPosition(@org.jetbrains.annotations.NotNull()
    java.lang.String ticker, @org.jetbrains.annotations.NotNull()
    java.lang.String assetType, double quantity, double averagePrice, double purchasePrice, long purchaseDate, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String tags, @org.jetbrains.annotations.NotNull()
    java.lang.String side, boolean syncFinanceiro, double valorBrl, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePosition(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.PortfolioPositionEntity entity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deletePosition(@org.jetbrains.annotations.NotNull()
    com.b3insights.app.data.local.entity.PortfolioPositionEntity entity, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity>> getPatrimonioHistorico() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordPatrimonioSnapshot(double totalValue, double totalInvested, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}