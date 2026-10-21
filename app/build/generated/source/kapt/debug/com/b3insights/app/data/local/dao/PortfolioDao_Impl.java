package com.b3insights.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.b3insights.app.data.local.entity.PortfolioPositionEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PortfolioDao_Impl implements PortfolioDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PortfolioPositionEntity> __insertionAdapterOfPortfolioPositionEntity;

  private final EntityInsertionAdapter<PortfolioPositionEntity> __insertionAdapterOfPortfolioPositionEntity_1;

  private final EntityDeletionOrUpdateAdapter<PortfolioPositionEntity> __deletionAdapterOfPortfolioPositionEntity;

  private final EntityDeletionOrUpdateAdapter<PortfolioPositionEntity> __updateAdapterOfPortfolioPositionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PortfolioDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPortfolioPositionEntity = new EntityInsertionAdapter<PortfolioPositionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `portfolio_positions` (`id`,`ticker`,`assetType`,`quantity`,`averagePrice`,`purchasePrice`,`purchaseDate`,`notes`,`tags`,`side`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PortfolioPositionEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicker() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicker());
        }
        if (entity.getAssetType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAssetType());
        }
        statement.bindDouble(4, entity.getQuantity());
        statement.bindDouble(5, entity.getAveragePrice());
        statement.bindDouble(6, entity.getPurchasePrice());
        statement.bindLong(7, entity.getPurchaseDate());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getTags() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTags());
        }
        if (entity.getSide() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSide());
        }
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__insertionAdapterOfPortfolioPositionEntity_1 = new EntityInsertionAdapter<PortfolioPositionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `portfolio_positions` (`id`,`ticker`,`assetType`,`quantity`,`averagePrice`,`purchasePrice`,`purchaseDate`,`notes`,`tags`,`side`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PortfolioPositionEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicker() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicker());
        }
        if (entity.getAssetType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAssetType());
        }
        statement.bindDouble(4, entity.getQuantity());
        statement.bindDouble(5, entity.getAveragePrice());
        statement.bindDouble(6, entity.getPurchasePrice());
        statement.bindLong(7, entity.getPurchaseDate());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getTags() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTags());
        }
        if (entity.getSide() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSide());
        }
        statement.bindLong(11, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfPortfolioPositionEntity = new EntityDeletionOrUpdateAdapter<PortfolioPositionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `portfolio_positions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PortfolioPositionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPortfolioPositionEntity = new EntityDeletionOrUpdateAdapter<PortfolioPositionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `portfolio_positions` SET `id` = ?,`ticker` = ?,`assetType` = ?,`quantity` = ?,`averagePrice` = ?,`purchasePrice` = ?,`purchaseDate` = ?,`notes` = ?,`tags` = ?,`side` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PortfolioPositionEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicker() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicker());
        }
        if (entity.getAssetType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAssetType());
        }
        statement.bindDouble(4, entity.getQuantity());
        statement.bindDouble(5, entity.getAveragePrice());
        statement.bindDouble(6, entity.getPurchasePrice());
        statement.bindLong(7, entity.getPurchaseDate());
        if (entity.getNotes() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getNotes());
        }
        if (entity.getTags() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getTags());
        }
        if (entity.getSide() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSide());
        }
        statement.bindLong(11, entity.getUpdatedAt());
        statement.bindLong(12, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM portfolio_positions";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PortfolioPositionEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPortfolioPositionEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<PortfolioPositionEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPortfolioPositionEntity_1.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PortfolioPositionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPortfolioPositionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PortfolioPositionEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPortfolioPositionEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PortfolioPositionEntity>> getAllPositions() {
    final String _sql = "SELECT * FROM portfolio_positions ORDER BY purchaseDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"portfolio_positions"}, new Callable<List<PortfolioPositionEntity>>() {
      @Override
      @NonNull
      public List<PortfolioPositionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfAveragePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePrice");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfSide = CursorUtil.getColumnIndexOrThrow(_cursor, "side");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PortfolioPositionEntity> _result = new ArrayList<PortfolioPositionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PortfolioPositionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final String _tmpAssetType;
            if (_cursor.isNull(_cursorIndexOfAssetType)) {
              _tmpAssetType = null;
            } else {
              _tmpAssetType = _cursor.getString(_cursorIndexOfAssetType);
            }
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final double _tmpAveragePrice;
            _tmpAveragePrice = _cursor.getDouble(_cursorIndexOfAveragePrice);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpSide;
            if (_cursor.isNull(_cursorIndexOfSide)) {
              _tmpSide = null;
            } else {
              _tmpSide = _cursor.getString(_cursorIndexOfSide);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PortfolioPositionEntity(_tmpId,_tmpTicker,_tmpAssetType,_tmpQuantity,_tmpAveragePrice,_tmpPurchasePrice,_tmpPurchaseDate,_tmpNotes,_tmpTags,_tmpSide,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<PortfolioPositionEntity>> getPositionsForTicker(final String ticker) {
    final String _sql = "SELECT * FROM portfolio_positions WHERE ticker = ? ORDER BY purchaseDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ticker == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ticker);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"portfolio_positions"}, new Callable<List<PortfolioPositionEntity>>() {
      @Override
      @NonNull
      public List<PortfolioPositionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfAveragePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePrice");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfSide = CursorUtil.getColumnIndexOrThrow(_cursor, "side");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PortfolioPositionEntity> _result = new ArrayList<PortfolioPositionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PortfolioPositionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final String _tmpAssetType;
            if (_cursor.isNull(_cursorIndexOfAssetType)) {
              _tmpAssetType = null;
            } else {
              _tmpAssetType = _cursor.getString(_cursorIndexOfAssetType);
            }
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final double _tmpAveragePrice;
            _tmpAveragePrice = _cursor.getDouble(_cursorIndexOfAveragePrice);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpSide;
            if (_cursor.isNull(_cursorIndexOfSide)) {
              _tmpSide = null;
            } else {
              _tmpSide = _cursor.getString(_cursorIndexOfSide);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PortfolioPositionEntity(_tmpId,_tmpTicker,_tmpAssetType,_tmpQuantity,_tmpAveragePrice,_tmpPurchasePrice,_tmpPurchaseDate,_tmpNotes,_tmpTags,_tmpSide,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getDistinctTickers(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT ticker FROM portfolio_positions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllOnce(final Continuation<? super List<PortfolioPositionEntity>> $completion) {
    final String _sql = "SELECT * FROM portfolio_positions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PortfolioPositionEntity>>() {
      @Override
      @NonNull
      public List<PortfolioPositionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfAveragePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "averagePrice");
          final int _cursorIndexOfPurchasePrice = CursorUtil.getColumnIndexOrThrow(_cursor, "purchasePrice");
          final int _cursorIndexOfPurchaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "purchaseDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfSide = CursorUtil.getColumnIndexOrThrow(_cursor, "side");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PortfolioPositionEntity> _result = new ArrayList<PortfolioPositionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PortfolioPositionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final String _tmpAssetType;
            if (_cursor.isNull(_cursorIndexOfAssetType)) {
              _tmpAssetType = null;
            } else {
              _tmpAssetType = _cursor.getString(_cursorIndexOfAssetType);
            }
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final double _tmpAveragePrice;
            _tmpAveragePrice = _cursor.getDouble(_cursorIndexOfAveragePrice);
            final double _tmpPurchasePrice;
            _tmpPurchasePrice = _cursor.getDouble(_cursorIndexOfPurchasePrice);
            final long _tmpPurchaseDate;
            _tmpPurchaseDate = _cursor.getLong(_cursorIndexOfPurchaseDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpSide;
            if (_cursor.isNull(_cursorIndexOfSide)) {
              _tmpSide = null;
            } else {
              _tmpSide = _cursor.getString(_cursorIndexOfSide);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PortfolioPositionEntity(_tmpId,_tmpTicker,_tmpAssetType,_tmpQuantity,_tmpAveragePrice,_tmpPurchasePrice,_tmpPurchaseDate,_tmpNotes,_tmpTags,_tmpSide,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
