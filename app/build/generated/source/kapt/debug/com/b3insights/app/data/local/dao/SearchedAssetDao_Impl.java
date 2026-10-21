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
import com.b3insights.app.data.local.entity.SearchedAssetEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class SearchedAssetDao_Impl implements SearchedAssetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SearchedAssetEntity> __insertionAdapterOfSearchedAssetEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public SearchedAssetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSearchedAssetEntity = new EntityInsertionAdapter<SearchedAssetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `searched_assets` (`ticker`,`name`,`assetType`,`lastSearchedAt`,`updatedAt`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SearchedAssetEntity entity) {
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
        statement.bindLong(4, entity.getLastSearchedAt());
        statement.bindLong(5, entity.getUpdatedAt());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM searched_assets";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final SearchedAssetEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSearchedAssetEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<SearchedAssetEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSearchedAssetEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
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
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SearchedAssetEntity>> getRecentSearches() {
    final String _sql = "SELECT * FROM searched_assets ORDER BY lastSearchedAt DESC LIMIT 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"searched_assets"}, new Callable<List<SearchedAssetEntity>>() {
      @Override
      @NonNull
      public List<SearchedAssetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfLastSearchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSearchedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<SearchedAssetEntity> _result = new ArrayList<SearchedAssetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SearchedAssetEntity _item;
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
            final long _tmpLastSearchedAt;
            _tmpLastSearchedAt = _cursor.getLong(_cursorIndexOfLastSearchedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new SearchedAssetEntity(_tmpTicker,_tmpName,_tmpAssetType,_tmpLastSearchedAt,_tmpUpdatedAt);
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
  public Object getAllOnce(final Continuation<? super List<SearchedAssetEntity>> $completion) {
    final String _sql = "SELECT * FROM searched_assets ORDER BY lastSearchedAt DESC LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SearchedAssetEntity>>() {
      @Override
      @NonNull
      public List<SearchedAssetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTicker = CursorUtil.getColumnIndexOrThrow(_cursor, "ticker");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAssetType = CursorUtil.getColumnIndexOrThrow(_cursor, "assetType");
          final int _cursorIndexOfLastSearchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSearchedAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<SearchedAssetEntity> _result = new ArrayList<SearchedAssetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SearchedAssetEntity _item;
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
            final long _tmpLastSearchedAt;
            _tmpLastSearchedAt = _cursor.getLong(_cursorIndexOfLastSearchedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new SearchedAssetEntity(_tmpTicker,_tmpName,_tmpAssetType,_tmpLastSearchedAt,_tmpUpdatedAt);
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
