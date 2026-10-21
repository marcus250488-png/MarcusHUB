package com.b3insights.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.b3insights.app.data.local.entity.PatrimonioHistoricoEntity;
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
public final class PatrimonioHistoricoDao_Impl implements PatrimonioHistoricoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PatrimonioHistoricoEntity> __insertionAdapterOfPatrimonioHistoricoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public PatrimonioHistoricoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPatrimonioHistoricoEntity = new EntityInsertionAdapter<PatrimonioHistoricoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `patrimonio_historico` (`id`,`date`,`totalValue`,`totalInvested`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PatrimonioHistoricoEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindDouble(3, entity.getTotalValue());
        statement.bindDouble(4, entity.getTotalInvested());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM patrimonio_historico";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final PatrimonioHistoricoEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPatrimonioHistoricoEntity.insert(entity);
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
  public Flow<List<PatrimonioHistoricoEntity>> getHistorico() {
    final String _sql = "SELECT * FROM patrimonio_historico ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"patrimonio_historico"}, new Callable<List<PatrimonioHistoricoEntity>>() {
      @Override
      @NonNull
      public List<PatrimonioHistoricoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalValue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalValue");
          final int _cursorIndexOfTotalInvested = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInvested");
          final List<PatrimonioHistoricoEntity> _result = new ArrayList<PatrimonioHistoricoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PatrimonioHistoricoEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalValue;
            _tmpTotalValue = _cursor.getDouble(_cursorIndexOfTotalValue);
            final double _tmpTotalInvested;
            _tmpTotalInvested = _cursor.getDouble(_cursorIndexOfTotalInvested);
            _item = new PatrimonioHistoricoEntity(_tmpId,_tmpDate,_tmpTotalValue,_tmpTotalInvested);
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
  public Object getByDay(final long dayStart,
      final Continuation<? super PatrimonioHistoricoEntity> $completion) {
    final String _sql = "SELECT * FROM patrimonio_historico WHERE date = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, dayStart);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PatrimonioHistoricoEntity>() {
      @Override
      @Nullable
      public PatrimonioHistoricoEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfTotalValue = CursorUtil.getColumnIndexOrThrow(_cursor, "totalValue");
          final int _cursorIndexOfTotalInvested = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInvested");
          final PatrimonioHistoricoEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final double _tmpTotalValue;
            _tmpTotalValue = _cursor.getDouble(_cursorIndexOfTotalValue);
            final double _tmpTotalInvested;
            _tmpTotalInvested = _cursor.getDouble(_cursorIndexOfTotalInvested);
            _result = new PatrimonioHistoricoEntity(_tmpId,_tmpDate,_tmpTotalValue,_tmpTotalInvested);
          } else {
            _result = null;
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
