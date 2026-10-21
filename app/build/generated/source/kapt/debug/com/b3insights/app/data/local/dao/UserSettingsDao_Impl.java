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
import com.b3insights.app.data.local.entity.UserSettingsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserSettingsDao_Impl implements UserSettingsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserSettingsEntity> __insertionAdapterOfUserSettingsEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public UserSettingsDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserSettingsEntity = new EntityInsertionAdapter<UserSettingsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_settings` (`id`,`darkTheme`,`autoUpdateEnabled`,`notificationsEnabled`,`biometricEnabled`,`googleAccountEmail`,`lastSyncAt`,`targetAcoesPct`,`targetFiisPct`,`targetEtfsPct`,`targetCriptoPct`,`targetOutrosPct`,`preferCryptoBrl`,`targetPatrimonio`,`targetRendaMensal`,`allowEmptyCloudRestore`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserSettingsEntity entity) {
        statement.bindLong(1, entity.getId());
        final int _tmp = entity.getDarkTheme() ? 1 : 0;
        statement.bindLong(2, _tmp);
        final int _tmp_1 = entity.getAutoUpdateEnabled() ? 1 : 0;
        statement.bindLong(3, _tmp_1);
        final int _tmp_2 = entity.getNotificationsEnabled() ? 1 : 0;
        statement.bindLong(4, _tmp_2);
        final int _tmp_3 = entity.getBiometricEnabled() ? 1 : 0;
        statement.bindLong(5, _tmp_3);
        if (entity.getGoogleAccountEmail() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getGoogleAccountEmail());
        }
        if (entity.getLastSyncAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getLastSyncAt());
        }
        statement.bindDouble(8, entity.getTargetAcoesPct());
        statement.bindDouble(9, entity.getTargetFiisPct());
        statement.bindDouble(10, entity.getTargetEtfsPct());
        statement.bindDouble(11, entity.getTargetCriptoPct());
        statement.bindDouble(12, entity.getTargetOutrosPct());
        final int _tmp_4 = entity.getPreferCryptoBrl() ? 1 : 0;
        statement.bindLong(13, _tmp_4);
        statement.bindDouble(14, entity.getTargetPatrimonio());
        statement.bindDouble(15, entity.getTargetRendaMensal());
        final int _tmp_5 = entity.getAllowEmptyCloudRestore() ? 1 : 0;
        statement.bindLong(16, _tmp_5);
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM user_settings";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final UserSettingsEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserSettingsEntity.insert(entity);
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
  public Flow<UserSettingsEntity> getSettings() {
    final String _sql = "SELECT * FROM user_settings WHERE id = 0 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"user_settings"}, new Callable<UserSettingsEntity>() {
      @Override
      @Nullable
      public UserSettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDarkTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "darkTheme");
          final int _cursorIndexOfAutoUpdateEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUpdateEnabled");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfBiometricEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "biometricEnabled");
          final int _cursorIndexOfGoogleAccountEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "googleAccountEmail");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTargetAcoesPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetAcoesPct");
          final int _cursorIndexOfTargetFiisPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetFiisPct");
          final int _cursorIndexOfTargetEtfsPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetEtfsPct");
          final int _cursorIndexOfTargetCriptoPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetCriptoPct");
          final int _cursorIndexOfTargetOutrosPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetOutrosPct");
          final int _cursorIndexOfPreferCryptoBrl = CursorUtil.getColumnIndexOrThrow(_cursor, "preferCryptoBrl");
          final int _cursorIndexOfTargetPatrimonio = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPatrimonio");
          final int _cursorIndexOfTargetRendaMensal = CursorUtil.getColumnIndexOrThrow(_cursor, "targetRendaMensal");
          final int _cursorIndexOfAllowEmptyCloudRestore = CursorUtil.getColumnIndexOrThrow(_cursor, "allowEmptyCloudRestore");
          final UserSettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final boolean _tmpDarkTheme;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDarkTheme);
            _tmpDarkTheme = _tmp != 0;
            final boolean _tmpAutoUpdateEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoUpdateEnabled);
            _tmpAutoUpdateEnabled = _tmp_1 != 0;
            final boolean _tmpNotificationsEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp_2 != 0;
            final boolean _tmpBiometricEnabled;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfBiometricEnabled);
            _tmpBiometricEnabled = _tmp_3 != 0;
            final String _tmpGoogleAccountEmail;
            if (_cursor.isNull(_cursorIndexOfGoogleAccountEmail)) {
              _tmpGoogleAccountEmail = null;
            } else {
              _tmpGoogleAccountEmail = _cursor.getString(_cursorIndexOfGoogleAccountEmail);
            }
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            final float _tmpTargetAcoesPct;
            _tmpTargetAcoesPct = _cursor.getFloat(_cursorIndexOfTargetAcoesPct);
            final float _tmpTargetFiisPct;
            _tmpTargetFiisPct = _cursor.getFloat(_cursorIndexOfTargetFiisPct);
            final float _tmpTargetEtfsPct;
            _tmpTargetEtfsPct = _cursor.getFloat(_cursorIndexOfTargetEtfsPct);
            final float _tmpTargetCriptoPct;
            _tmpTargetCriptoPct = _cursor.getFloat(_cursorIndexOfTargetCriptoPct);
            final float _tmpTargetOutrosPct;
            _tmpTargetOutrosPct = _cursor.getFloat(_cursorIndexOfTargetOutrosPct);
            final boolean _tmpPreferCryptoBrl;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfPreferCryptoBrl);
            _tmpPreferCryptoBrl = _tmp_4 != 0;
            final double _tmpTargetPatrimonio;
            _tmpTargetPatrimonio = _cursor.getDouble(_cursorIndexOfTargetPatrimonio);
            final double _tmpTargetRendaMensal;
            _tmpTargetRendaMensal = _cursor.getDouble(_cursorIndexOfTargetRendaMensal);
            final boolean _tmpAllowEmptyCloudRestore;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfAllowEmptyCloudRestore);
            _tmpAllowEmptyCloudRestore = _tmp_5 != 0;
            _result = new UserSettingsEntity(_tmpId,_tmpDarkTheme,_tmpAutoUpdateEnabled,_tmpNotificationsEnabled,_tmpBiometricEnabled,_tmpGoogleAccountEmail,_tmpLastSyncAt,_tmpTargetAcoesPct,_tmpTargetFiisPct,_tmpTargetEtfsPct,_tmpTargetCriptoPct,_tmpTargetOutrosPct,_tmpPreferCryptoBrl,_tmpTargetPatrimonio,_tmpTargetRendaMensal,_tmpAllowEmptyCloudRestore);
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
  public Object getSettingsOnce(final Continuation<? super UserSettingsEntity> $completion) {
    final String _sql = "SELECT * FROM user_settings WHERE id = 0 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserSettingsEntity>() {
      @Override
      @Nullable
      public UserSettingsEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDarkTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "darkTheme");
          final int _cursorIndexOfAutoUpdateEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "autoUpdateEnabled");
          final int _cursorIndexOfNotificationsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationsEnabled");
          final int _cursorIndexOfBiometricEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "biometricEnabled");
          final int _cursorIndexOfGoogleAccountEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "googleAccountEmail");
          final int _cursorIndexOfLastSyncAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastSyncAt");
          final int _cursorIndexOfTargetAcoesPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetAcoesPct");
          final int _cursorIndexOfTargetFiisPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetFiisPct");
          final int _cursorIndexOfTargetEtfsPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetEtfsPct");
          final int _cursorIndexOfTargetCriptoPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetCriptoPct");
          final int _cursorIndexOfTargetOutrosPct = CursorUtil.getColumnIndexOrThrow(_cursor, "targetOutrosPct");
          final int _cursorIndexOfPreferCryptoBrl = CursorUtil.getColumnIndexOrThrow(_cursor, "preferCryptoBrl");
          final int _cursorIndexOfTargetPatrimonio = CursorUtil.getColumnIndexOrThrow(_cursor, "targetPatrimonio");
          final int _cursorIndexOfTargetRendaMensal = CursorUtil.getColumnIndexOrThrow(_cursor, "targetRendaMensal");
          final int _cursorIndexOfAllowEmptyCloudRestore = CursorUtil.getColumnIndexOrThrow(_cursor, "allowEmptyCloudRestore");
          final UserSettingsEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final boolean _tmpDarkTheme;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDarkTheme);
            _tmpDarkTheme = _tmp != 0;
            final boolean _tmpAutoUpdateEnabled;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfAutoUpdateEnabled);
            _tmpAutoUpdateEnabled = _tmp_1 != 0;
            final boolean _tmpNotificationsEnabled;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfNotificationsEnabled);
            _tmpNotificationsEnabled = _tmp_2 != 0;
            final boolean _tmpBiometricEnabled;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfBiometricEnabled);
            _tmpBiometricEnabled = _tmp_3 != 0;
            final String _tmpGoogleAccountEmail;
            if (_cursor.isNull(_cursorIndexOfGoogleAccountEmail)) {
              _tmpGoogleAccountEmail = null;
            } else {
              _tmpGoogleAccountEmail = _cursor.getString(_cursorIndexOfGoogleAccountEmail);
            }
            final Long _tmpLastSyncAt;
            if (_cursor.isNull(_cursorIndexOfLastSyncAt)) {
              _tmpLastSyncAt = null;
            } else {
              _tmpLastSyncAt = _cursor.getLong(_cursorIndexOfLastSyncAt);
            }
            final float _tmpTargetAcoesPct;
            _tmpTargetAcoesPct = _cursor.getFloat(_cursorIndexOfTargetAcoesPct);
            final float _tmpTargetFiisPct;
            _tmpTargetFiisPct = _cursor.getFloat(_cursorIndexOfTargetFiisPct);
            final float _tmpTargetEtfsPct;
            _tmpTargetEtfsPct = _cursor.getFloat(_cursorIndexOfTargetEtfsPct);
            final float _tmpTargetCriptoPct;
            _tmpTargetCriptoPct = _cursor.getFloat(_cursorIndexOfTargetCriptoPct);
            final float _tmpTargetOutrosPct;
            _tmpTargetOutrosPct = _cursor.getFloat(_cursorIndexOfTargetOutrosPct);
            final boolean _tmpPreferCryptoBrl;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfPreferCryptoBrl);
            _tmpPreferCryptoBrl = _tmp_4 != 0;
            final double _tmpTargetPatrimonio;
            _tmpTargetPatrimonio = _cursor.getDouble(_cursorIndexOfTargetPatrimonio);
            final double _tmpTargetRendaMensal;
            _tmpTargetRendaMensal = _cursor.getDouble(_cursorIndexOfTargetRendaMensal);
            final boolean _tmpAllowEmptyCloudRestore;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfAllowEmptyCloudRestore);
            _tmpAllowEmptyCloudRestore = _tmp_5 != 0;
            _result = new UserSettingsEntity(_tmpId,_tmpDarkTheme,_tmpAutoUpdateEnabled,_tmpNotificationsEnabled,_tmpBiometricEnabled,_tmpGoogleAccountEmail,_tmpLastSyncAt,_tmpTargetAcoesPct,_tmpTargetFiisPct,_tmpTargetEtfsPct,_tmpTargetCriptoPct,_tmpTargetOutrosPct,_tmpPreferCryptoBrl,_tmpTargetPatrimonio,_tmpTargetRendaMensal,_tmpAllowEmptyCloudRestore);
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
