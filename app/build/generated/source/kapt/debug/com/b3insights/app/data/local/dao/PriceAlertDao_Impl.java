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
import com.b3insights.app.data.local.entity.PriceAlertEntity;
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
public final class PriceAlertDao_Impl implements PriceAlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PriceAlertEntity> __insertionAdapterOfPriceAlertEntity;

  private final EntityDeletionOrUpdateAdapter<PriceAlertEntity> __deletionAdapterOfPriceAlertEntity;

  private final EntityDeletionOrUpdateAdapter<PriceAlertEntity> __updateAdapterOfPriceAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PriceAlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPriceAlertEntity = new EntityInsertionAdapter<PriceAlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `price_alerts` (`id`,`ticker`,`targetPrice`,`above`,`enabled`,`createdAt`,`triggeredAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PriceAlertEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicker() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicker());
        }
        statement.bindDouble(3, entity.getTargetPrice());
        final int _tmp = entity.getAbove() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.getEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        statement.bindLong(6, entity.getCreatedAt());
        if (entity.getTriggeredAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getTriggeredAt());
        }
        statement.bindLong(8, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfPriceAlertEntity = new EntityDeletionOrUpdateAdapter<PriceAlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `price_alerts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PriceAlertEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPriceAlertEntity = new EntityDeletionOrUpdateAdapter<PriceAlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `price_alerts` SET `id` = ?,`ticker` = ?,`targetPrice` = ?,`above` = ?,`enabled` = ?,`createdAt` = ?,`triggeredAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PriceAlertEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicker() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicker());
        }
        statement.bindDouble(3, entity.getTargetPrice());
        final int _tmp = entity.getAbove() ? 1 : 0;
        statement.bindLong(4, _tmp);
        final int _tmp_1 = entity.getEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp_1);
        statement.bindLong(6, entity.getCreatedAt());
        if (entity.getTriggeredAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getTriggeredAt());
        }
        statement.bindLong(8, entity.getUpdatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM price_alerts";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PriceAlertEntity entity,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPriceAlertEntity.insertAndReturnId(entity);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<PriceAlertEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPriceAlertEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PriceAlertEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPriceAlertEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PriceAlertEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPriceAlertEntity.handle(entity);
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
  public Flow<List<PriceAlertEntity>> getAll() {
    final String _sql = "SELECT * FROM price_alerts ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"price_alerts"}, new Callable<List<PriceAlertEntity>>() {
      @Override
      @NonNull
      public List<PriceAlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfTargetPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPrice");
          final int _cursorIndexOfAbove = CursorUtil.getColumnIndexOrThrow(_cursor, "above");
          final int _cursorIndexOfEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "enabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PriceAlertEntity> _result = new ArrayList<PriceAlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PriceAlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final double _tmpTargetPrice;
            _tmpTargetPrice = _cursor.getDouble(_cursorIndexOfTargetPrice);
            final boolean _tmpAbove;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAbove);
            _tmpAbove = _tmp != 0;
            final boolean _tmpEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfEnabled);
            _tmpEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpTriggeredAt;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmpTriggeredAt = null;
            } else {
              _tmpTriggeredAt = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PriceAlertEntity(_tmpId,_tmpTicker,_tmpTargetPrice,_tmpAbove,_tmpEnabled,_tmpCreatedAt,_tmpTriggeredAt,_tmpUpdatedAt);
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
  public Object getEnabled(final Continuation<? super List<PriceAlertEntity>> $completion) {
    final String _sql = "SELECT * FROM price_alerts WHERE enabled = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PriceAlertEntity>>() {
      @Override
      @NonNull
      public List<PriceAlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfTargetPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPrice");
          final int _cursorIndexOfAbove = CursorUtil.getColumnIndexOrThrow(_cursor, "above");
          final int _cursorIndexOfEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "enabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PriceAlertEntity> _result = new ArrayList<PriceAlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PriceAlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final double _tmpTargetPrice;
            _tmpTargetPrice = _cursor.getDouble(_cursorIndexOfTargetPrice);
            final boolean _tmpAbove;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAbove);
            _tmpAbove = _tmp != 0;
            final boolean _tmpEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfEnabled);
            _tmpEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpTriggeredAt;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmpTriggeredAt = null;
            } else {
              _tmpTriggeredAt = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PriceAlertEntity(_tmpId,_tmpTicker,_tmpTargetPrice,_tmpAbove,_tmpEnabled,_tmpCreatedAt,_tmpTriggeredAt,_tmpUpdatedAt);
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
  public Object getAllOnce(final Continuation<? super List<PriceAlertEntity>> $completion) {
    final String _sql = "SELECT * FROM price_alerts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PriceAlertEntity>>() {
      @Override
      @NonNull
      public List<PriceAlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfTargetPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPrice");
          final int _cursorIndexOfAbove = CursorUtil.getColumnIndexOrThrow(_cursor, "above");
          final int _cursorIndexOfEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "enabled");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfTriggeredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "triggeredAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<PriceAlertEntity> _result = new ArrayList<PriceAlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PriceAlertEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTicker;
            if (_cursor.isNull(_cursorIndexOfTicker)) {
              _tmpTicker = null;
            } else {
              _tmpTicker = _cursor.getString(_cursorIndexOfTicker);
            }
            final double _tmpTargetPrice;
            _tmpTargetPrice = _cursor.getDouble(_cursorIndexOfTargetPrice);
            final boolean _tmpAbove;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAbove);
            _tmpAbove = _tmp != 0;
            final boolean _tmpEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfEnabled);
            _tmpEnabled = _tmp_1 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpTriggeredAt;
            if (_cursor.isNull(_cursorIndexOfTriggeredAt)) {
              _tmpTriggeredAt = null;
            } else {
              _tmpTriggeredAt = _cursor.getLong(_cursorIndexOfTriggeredAt);
            }
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new PriceAlertEntity(_tmpId,_tmpTicker,_tmpTargetPrice,_tmpAbove,_tmpEnabled,_tmpCreatedAt,_tmpTriggeredAt,_tmpUpdatedAt);
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
