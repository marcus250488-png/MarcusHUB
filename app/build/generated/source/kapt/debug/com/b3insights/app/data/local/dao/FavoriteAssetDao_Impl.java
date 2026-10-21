package com.b3insights.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.b3insights.app.data.local.entity.FavoriteAssetEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
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
public final class FavoriteAssetDao_Impl implements FavoriteAssetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteAssetEntity> __insertionAdapterOfFavoriteAssetEntity;

  private final SharedSQLiteStatement __preparedStmtOfRemoveByTicker;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastKnownPrice;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public FavoriteAssetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteAssetEntity = new EntityInsertionAdapter<FavoriteAssetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_assets` (`ticker`,`name`,`assetType`,`addedAt`,`lastKnownPrice`,`lastKnownChangePercent`,`updatedAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteAssetEntity entity) {
        if (entity.getTicker() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getTicker());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAssetType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAssetType());
        }
        statement.bindLong(4, entity.getAddedAt());
        if (entity.getLastKnownPrice() == null) {
          statement.bindNull(5);
        } else {
          statement.bindDouble(5, entity.getLastKnownPrice());
        }
        if (entity.getLastKnownChangePercent() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLastKnownChangePercent());
        }
        statement.bindLong(7, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfRemoveByTicker = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorite_assets WHERE ticker = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateLastKnownPrice = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE favorite_assets SET lastKnownPrice = ?, lastKnownChangePercent = ? WHERE ticker = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorite_assets";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final FavoriteAssetEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteAssetEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<FavoriteAssetEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteAssetEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object removeByTicker(final String ticker, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfRemoveByTicker.acquire();
        int _argIndex = 1;
        if (ticker == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, ticker);
        }
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
          __preparedStmtOfRemoveByTicker.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastKnownPrice(final String ticker, final double price,
      final double changePercent, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastKnownPrice.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, price);
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, changePercent);
        _argIndex = 3;
        if (ticker == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, ticker);
        }
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
          __preparedStmtOfUpdateLastKnownPrice.release(_stmt);
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
  public Flow<List<FavoriteAssetEntity>> getAllFavorites() {
    final String _sql = "SELECT * FROM favorite_assets ORDER BY addedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favorite_assets"}, new Callable<List<FavoriteAssetEntity>>() {
      @Override
      @NonNull
      public List<FavoriteAssetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final int _cursorIndexOfLastKnownPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPrice");
          final int _cursorIndexOfLastKnownChangePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownChangePercent");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<FavoriteAssetEntity> _result = new ArrayList<FavoriteAssetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteAssetEntity _item;
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpAssetType;
            if (_cursor.isNull(_cursorIndexOfAssetType)) {
              _tmpAssetType = null;
            } else {
              _tmpAssetType = _cursor.getString(_cursorIndexOfAssetType);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Double _tmpLastKnownPrice;
            if (_cursor.isNull(_cursorIndexOfLastKnownPrice)) {
              _tmpLastKnownPrice = null;
            } else {
              _tmpLastKnownPrice = _cursor.getDouble(_cursorIndexOfLastKnownPrice);
            }
            final Double _tmpLastKnownChangePercent;
            if (_cursor.isNull(_cursorIndexOfLastKnownChangePercent)) {
              _tmpLastKnownChangePercent = null;
            } else {
              _tmpLastKnownChangePercent = _cursor.getDouble(_cursorIndexOfLastKnownChangePercent);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new FavoriteAssetEntity(_tmpTicker,_tmpName,_tmpAssetType,_tmpAddedAt,_tmpLastKnownPrice,_tmpLastKnownChangePercent,_tmpUpdatedAt);
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
  public Flow<Boolean> isFavorite(final String ticker) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorite_assets WHERE ticker = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ticker == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ticker);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favorite_assets"}, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
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
  public Object getAllOnce(final Continuation<? super List<FavoriteAssetEntity>> $completion) {
    final String _sql = "SELECT * FROM favorite_assets";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FavoriteAssetEntity>>() {
      @Override
      @NonNull
      public List<FavoriteAssetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfAddedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "addedAt");
          final int _cursorIndexOfLastKnownPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownPrice");
          final int _cursorIndexOfLastKnownChangePercent = CursorUtil.getColumnIndexOrThrow(_cursor, "lastKnownChangePercent");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<FavoriteAssetEntity> _result = new ArrayList<FavoriteAssetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteAssetEntity _item;
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpAssetType;
            if (_cursor.isNull(_cursorIndexOfAssetType)) {
              _tmpAssetType = null;
            } else {
              _tmpAssetType = _cursor.getString(_cursorIndexOfAssetType);
            }
            final long _tmpAddedAt;
            _tmpAddedAt = _cursor.getLong(_cursorIndexOfAddedAt);
            final Double _tmpLastKnownPrice;
            if (_cursor.isNull(_cursorIndexOfLastKnownPrice)) {
              _tmpLastKnownPrice = null;
            } else {
              _tmpLastKnownPrice = _cursor.getDouble(_cursorIndexOfLastKnownPrice);
            }
            final Double _tmpLastKnownChangePercent;
            if (_cursor.isNull(_cursorIndexOfLastKnownChangePercent)) {
              _tmpLastKnownChangePercent = null;
            } else {
              _tmpLastKnownChangePercent = _cursor.getDouble(_cursorIndexOfLastKnownChangePercent);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new FavoriteAssetEntity(_tmpTicker,_tmpName,_tmpAssetType,_tmpAddedAt,_tmpLastKnownPrice,_tmpLastKnownChangePercent,_tmpUpdatedAt);
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
