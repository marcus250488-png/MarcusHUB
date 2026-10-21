package com.b3insights.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.b3insights.app.data.local.dao.FavoriteAssetDao;
import com.b3insights.app.data.local.dao.FavoriteAssetDao_Impl;
import com.b3insights.app.data.local.dao.PatrimonioHistoricoDao;
import com.b3insights.app.data.local.dao.PatrimonioHistoricoDao_Impl;
import com.b3insights.app.data.local.dao.PortfolioDao;
import com.b3insights.app.data.local.dao.PortfolioDao_Impl;
import com.b3insights.app.data.local.dao.PriceAlertDao;
import com.b3insights.app.data.local.dao.PriceAlertDao_Impl;
import com.b3insights.app.data.local.dao.SearchedAssetDao;
import com.b3insights.app.data.local.dao.SearchedAssetDao_Impl;
import com.b3insights.app.data.local.dao.UserSettingsDao;
import com.b3insights.app.data.local.dao.UserSettingsDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SearchedAssetDao _searchedAssetDao;

  private volatile FavoriteAssetDao _favoriteAssetDao;

  private volatile PortfolioDao _portfolioDao;

  private volatile PatrimonioHistoricoDao _patrimonioHistoricoDao;

  private volatile UserSettingsDao _userSettingsDao;

  private volatile PriceAlertDao _priceAlertDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(7) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `searched_assets` (`ticker` TEXT NOT NULL, `name` TEXT, `assetType` TEXT, `lastSearchedAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`ticker`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorite_assets` (`ticker` TEXT NOT NULL, `name` TEXT, `assetType` TEXT, `addedAt` INTEGER NOT NULL, `lastKnownPrice` REAL, `lastKnownChangePercent` REAL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`ticker`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `portfolio_positions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ticker` TEXT NOT NULL, `assetType` TEXT NOT NULL, `quantity` REAL NOT NULL, `averagePrice` REAL NOT NULL, `purchasePrice` REAL NOT NULL, `purchaseDate` INTEGER NOT NULL, `notes` TEXT, `tags` TEXT, `side` TEXT NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `patrimonio_historico` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `totalValue` REAL NOT NULL, `totalInvested` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_settings` (`id` INTEGER NOT NULL, `darkTheme` INTEGER NOT NULL, `autoUpdateEnabled` INTEGER NOT NULL, `notificationsEnabled` INTEGER NOT NULL, `biometricEnabled` INTEGER NOT NULL, `googleAccountEmail` TEXT, `lastSyncAt` INTEGER, `targetAcoesPct` REAL NOT NULL, `targetFiisPct` REAL NOT NULL, `targetEtfsPct` REAL NOT NULL, `targetCriptoPct` REAL NOT NULL, `targetOutrosPct` REAL NOT NULL, `preferCryptoBrl` INTEGER NOT NULL, `targetPatrimonio` REAL NOT NULL, `targetRendaMensal` REAL NOT NULL, `allowEmptyCloudRestore` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `price_alerts` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ticker` TEXT NOT NULL, `targetPrice` REAL NOT NULL, `above` INTEGER NOT NULL, `enabled` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `triggeredAt` INTEGER, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bb3b9dd8066b0d1d5723784430fdd532')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `searched_assets`");
        db.execSQL("DROP TABLE IF EXISTS `favorite_assets`");
        db.execSQL("DROP TABLE IF EXISTS `portfolio_positions`");
        db.execSQL("DROP TABLE IF EXISTS `patrimonio_historico`");
        db.execSQL("DROP TABLE IF EXISTS `user_settings`");
        db.execSQL("DROP TABLE IF EXISTS `price_alerts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSearchedAssets = new HashMap<String, TableInfo.Column>(5);
        _columnsSearchedAssets.put("ticker", new TableInfo.Column("ticker", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchedAssets.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchedAssets.put("assetType", new TableInfo.Column("assetType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchedAssets.put("lastSearchedAt", new TableInfo.Column("lastSearchedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSearchedAssets.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSearchedAssets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSearchedAssets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSearchedAssets = new TableInfo("searched_assets", _columnsSearchedAssets, _foreignKeysSearchedAssets, _indicesSearchedAssets);
        final TableInfo _existingSearchedAssets = TableInfo.read(db, "searched_assets");
        if (!_infoSearchedAssets.equals(_existingSearchedAssets)) {
          return new RoomOpenHelper.ValidationResult(false, "searched_assets(com.b3insights.app.data.local.entity.SearchedAssetEntity).\n"
                  + " Expected:\n" + _infoSearchedAssets + "\n"
                  + " Found:\n" + _existingSearchedAssets);
        }
        final HashMap<String, TableInfo.Column> _columnsFavoriteAssets = new HashMap<String, TableInfo.Column>(7);
        _columnsFavoriteAssets.put("ticker", new TableInfo.Column("ticker", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("assetType", new TableInfo.Column("assetType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("lastKnownPrice", new TableInfo.Column("lastKnownPrice", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("lastKnownChangePercent", new TableInfo.Column("lastKnownChangePercent", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteAssets.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteAssets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoriteAssets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoriteAssets = new TableInfo("favorite_assets", _columnsFavoriteAssets, _foreignKeysFavoriteAssets, _indicesFavoriteAssets);
        final TableInfo _existingFavoriteAssets = TableInfo.read(db, "favorite_assets");
        if (!_infoFavoriteAssets.equals(_existingFavoriteAssets)) {
          return new RoomOpenHelper.ValidationResult(false, "favorite_assets(com.b3insights.app.data.local.entity.FavoriteAssetEntity).\n"
                  + " Expected:\n" + _infoFavoriteAssets + "\n"
                  + " Found:\n" + _existingFavoriteAssets);
        }
        final HashMap<String, TableInfo.Column> _columnsPortfolioPositions = new HashMap<String, TableInfo.Column>(11);
        _columnsPortfolioPositions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("ticker", new TableInfo.Column("ticker", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("assetType", new TableInfo.Column("assetType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("averagePrice", new TableInfo.Column("averagePrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("purchasePrice", new TableInfo.Column("purchasePrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("purchaseDate", new TableInfo.Column("purchaseDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("tags", new TableInfo.Column("tags", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("side", new TableInfo.Column("side", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPortfolioPositions.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPortfolioPositions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPortfolioPositions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPortfolioPositions = new TableInfo("portfolio_positions", _columnsPortfolioPositions, _foreignKeysPortfolioPositions, _indicesPortfolioPositions);
        final TableInfo _existingPortfolioPositions = TableInfo.read(db, "portfolio_positions");
        if (!_infoPortfolioPositions.equals(_existingPortfolioPositions)) {
          return new RoomOpenHelper.ValidationResult(false, "portfolio_positions(com.b3insights.app.data.local.entity.PortfolioPositionEntity).\n"
                  + " Expected:\n" + _infoPortfolioPositions + "\n"
                  + " Found:\n" + _existingPortfolioPositions);
        }
        final HashMap<String, TableInfo.Column> _columnsPatrimonioHistorico = new HashMap<String, TableInfo.Column>(4);
        _columnsPatrimonioHistorico.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatrimonioHistorico.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatrimonioHistorico.put("totalValue", new TableInfo.Column("totalValue", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPatrimonioHistorico.put("totalInvested", new TableInfo.Column("totalInvested", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPatrimonioHistorico = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPatrimonioHistorico = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPatrimonioHistorico = new TableInfo("patrimonio_historico", _columnsPatrimonioHistorico, _foreignKeysPatrimonioHistorico, _indicesPatrimonioHistorico);
        final TableInfo _existingPatrimonioHistorico = TableInfo.read(db, "patrimonio_historico");
        if (!_infoPatrimonioHistorico.equals(_existingPatrimonioHistorico)) {
          return new RoomOpenHelper.ValidationResult(false, "patrimonio_historico(com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity).\n"
                  + " Expected:\n" + _infoPatrimonioHistorico + "\n"
                  + " Found:\n" + _existingPatrimonioHistorico);
        }
        final HashMap<String, TableInfo.Column> _columnsUserSettings = new HashMap<String, TableInfo.Column>(16);
        _columnsUserSettings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("darkTheme", new TableInfo.Column("darkTheme", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("autoUpdateEnabled", new TableInfo.Column("autoUpdateEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("notificationsEnabled", new TableInfo.Column("notificationsEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("biometricEnabled", new TableInfo.Column("biometricEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("googleAccountEmail", new TableInfo.Column("googleAccountEmail", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("lastSyncAt", new TableInfo.Column("lastSyncAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetAcoesPct", new TableInfo.Column("targetAcoesPct", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetFiisPct", new TableInfo.Column("targetFiisPct", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetEtfsPct", new TableInfo.Column("targetEtfsPct", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetCriptoPct", new TableInfo.Column("targetCriptoPct", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetOutrosPct", new TableInfo.Column("targetOutrosPct", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("preferCryptoBrl", new TableInfo.Column("preferCryptoBrl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetPatrimonio", new TableInfo.Column("targetPatrimonio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("targetRendaMensal", new TableInfo.Column("targetRendaMensal", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserSettings.put("allowEmptyCloudRestore", new TableInfo.Column("allowEmptyCloudRestore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserSettings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserSettings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserSettings = new TableInfo("user_settings", _columnsUserSettings, _foreignKeysUserSettings, _indicesUserSettings);
        final TableInfo _existingUserSettings = TableInfo.read(db, "user_settings");
        if (!_infoUserSettings.equals(_existingUserSettings)) {
          return new RoomOpenHelper.ValidationResult(false, "user_settings(com.b3insights.app.data.local.entity.UserSettingsEntity).\n"
                  + " Expected:\n" + _infoUserSettings + "\n"
                  + " Found:\n" + _existingUserSettings);
        }
        final HashMap<String, TableInfo.Column> _columnsPriceAlerts = new HashMap<String, TableInfo.Column>(8);
        _columnsPriceAlerts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("ticker", new TableInfo.Column("ticker", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("targetPrice", new TableInfo.Column("targetPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("above", new TableInfo.Column("above", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("enabled", new TableInfo.Column("enabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("triggeredAt", new TableInfo.Column("triggeredAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPriceAlerts.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPriceAlerts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPriceAlerts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPriceAlerts = new TableInfo("price_alerts", _columnsPriceAlerts, _foreignKeysPriceAlerts, _indicesPriceAlerts);
        final TableInfo _existingPriceAlerts = TableInfo.read(db, "price_alerts");
        if (!_infoPriceAlerts.equals(_existingPriceAlerts)) {
          return new RoomOpenHelper.ValidationResult(false, "price_alerts(com.b3insights.app.data.local.entity.PriceAlertEntity).\n"
                  + " Expected:\n" + _infoPriceAlerts + "\n"
                  + " Found:\n" + _existingPriceAlerts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "bb3b9dd8066b0d1d5723784430fdd532", "0eef71eeae8ded58c51cd705aa423314");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "searched_assets","favorite_assets","portfolio_positions","patrimonio_historico","user_settings","price_alerts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `searched_assets`");
      _db.execSQL("DELETE FROM `favorite_assets`");
      _db.execSQL("DELETE FROM `portfolio_positions`");
      _db.execSQL("DELETE FROM `patrimonio_historico`");
      _db.execSQL("DELETE FROM `user_settings`");
      _db.execSQL("DELETE FROM `price_alerts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SearchedAssetDao.class, SearchedAssetDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoriteAssetDao.class, FavoriteAssetDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PortfolioDao.class, PortfolioDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PatrimonioHistoricoDao.class, PatrimonioHistoricoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserSettingsDao.class, UserSettingsDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PriceAlertDao.class, PriceAlertDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SearchedAssetDao searchedAssetDao() {
    if (_searchedAssetDao != null) {
      return _searchedAssetDao;
    } else {
      synchronized(this) {
        if(_searchedAssetDao == null) {
          _searchedAssetDao = new SearchedAssetDao_Impl(this);
        }
        return _searchedAssetDao;
      }
    }
  }

  @Override
  public FavoriteAssetDao favoriteAssetDao() {
    if (_favoriteAssetDao != null) {
      return _favoriteAssetDao;
    } else {
      synchronized(this) {
        if(_favoriteAssetDao == null) {
          _favoriteAssetDao = new FavoriteAssetDao_Impl(this);
        }
        return _favoriteAssetDao;
      }
    }
  }

  @Override
  public PortfolioDao portfolioDao() {
    if (_portfolioDao != null) {
      return _portfolioDao;
    } else {
      synchronized(this) {
        if(_portfolioDao == null) {
          _portfolioDao = new PortfolioDao_Impl(this);
        }
        return _portfolioDao;
      }
    }
  }

  @Override
  public PatrimonioHistoricoDao patrimonioHistoricoDao() {
    if (_patrimonioHistoricoDao != null) {
      return _patrimonioHistoricoDao;
    } else {
      synchronized(this) {
        if(_patrimonioHistoricoDao == null) {
          _patrimonioHistoricoDao = new PatrimonioHistoricoDao_Impl(this);
        }
        return _patrimonioHistoricoDao;
      }
    }
  }

  @Override
  public UserSettingsDao userSettingsDao() {
    if (_userSettingsDao != null) {
      return _userSettingsDao;
    } else {
      synchronized(this) {
        if(_userSettingsDao == null) {
          _userSettingsDao = new UserSettingsDao_Impl(this);
        }
        return _userSettingsDao;
      }
    }
  }

  @Override
  public PriceAlertDao priceAlertDao() {
    if (_priceAlertDao != null) {
      return _priceAlertDao;
    } else {
      synchronized(this) {
        if(_priceAlertDao == null) {
          _priceAlertDao = new PriceAlertDao_Impl(this);
        }
        return _priceAlertDao;
      }
    }
  }
}
