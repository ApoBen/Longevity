package com.shealt.healthreport.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class ReportDao_Impl implements ReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReportEntity> __insertionAdapterOfReportEntity;

  private final EntityDeletionOrUpdateAdapter<ReportEntity> __deletionAdapterOfReportEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteReportById;

  public ReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReportEntity = new EntityInsertionAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `reports` (`id`,`dateString`,`filePath`,`createdAtTimestamp`,`stepCount`,`sleepScore`,`energyScore`,`avgHeartRate`,`workoutCount`,`sleepDurationMinutes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getDateString() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getDateString());
        }
        if (entity.getFilePath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getFilePath());
        }
        statement.bindLong(4, entity.getCreatedAtTimestamp());
        if (entity.getStepCount() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getStepCount());
        }
        if (entity.getSleepScore() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getSleepScore());
        }
        if (entity.getEnergyScore() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getEnergyScore());
        }
        if (entity.getAvgHeartRate() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getAvgHeartRate());
        }
        if (entity.getWorkoutCount() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getWorkoutCount());
        }
        if (entity.getSleepDurationMinutes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getSleepDurationMinutes());
        }
      }
    };
    this.__deletionAdapterOfReportEntity = new EntityDeletionOrUpdateAdapter<ReportEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reports` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReportEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteReportById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reports WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertReport(final ReportEntity report,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReportEntity.insertAndReturnId(report);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReport(final ReportEntity report,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReportEntity.handle(report);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteReportById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteReportById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteReportById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ReportEntity>> getAllReports() {
    final String _sql = "SELECT * FROM reports ORDER BY createdAtTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<List<ReportEntity>>() {
      @Override
      @NonNull
      public List<ReportEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateString = CursorUtil.getColumnIndexOrThrow(_cursor, "dateString");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfCreatedAtTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtTimestamp");
          final int _cursorIndexOfStepCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stepCount");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfEnergyScore = CursorUtil.getColumnIndexOrThrow(_cursor, "energyScore");
          final int _cursorIndexOfAvgHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "avgHeartRate");
          final int _cursorIndexOfWorkoutCount = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutCount");
          final int _cursorIndexOfSleepDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepDurationMinutes");
          final List<ReportEntity> _result = new ArrayList<ReportEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReportEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDateString;
            if (_cursor.isNull(_cursorIndexOfDateString)) {
              _tmpDateString = null;
            } else {
              _tmpDateString = _cursor.getString(_cursorIndexOfDateString);
            }
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final long _tmpCreatedAtTimestamp;
            _tmpCreatedAtTimestamp = _cursor.getLong(_cursorIndexOfCreatedAtTimestamp);
            final Integer _tmpStepCount;
            if (_cursor.isNull(_cursorIndexOfStepCount)) {
              _tmpStepCount = null;
            } else {
              _tmpStepCount = _cursor.getInt(_cursorIndexOfStepCount);
            }
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final Integer _tmpEnergyScore;
            if (_cursor.isNull(_cursorIndexOfEnergyScore)) {
              _tmpEnergyScore = null;
            } else {
              _tmpEnergyScore = _cursor.getInt(_cursorIndexOfEnergyScore);
            }
            final Integer _tmpAvgHeartRate;
            if (_cursor.isNull(_cursorIndexOfAvgHeartRate)) {
              _tmpAvgHeartRate = null;
            } else {
              _tmpAvgHeartRate = _cursor.getInt(_cursorIndexOfAvgHeartRate);
            }
            final Integer _tmpWorkoutCount;
            if (_cursor.isNull(_cursorIndexOfWorkoutCount)) {
              _tmpWorkoutCount = null;
            } else {
              _tmpWorkoutCount = _cursor.getInt(_cursorIndexOfWorkoutCount);
            }
            final Integer _tmpSleepDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfSleepDurationMinutes)) {
              _tmpSleepDurationMinutes = null;
            } else {
              _tmpSleepDurationMinutes = _cursor.getInt(_cursorIndexOfSleepDurationMinutes);
            }
            _item = new ReportEntity(_tmpId,_tmpDateString,_tmpFilePath,_tmpCreatedAtTimestamp,_tmpStepCount,_tmpSleepScore,_tmpEnergyScore,_tmpAvgHeartRate,_tmpWorkoutCount,_tmpSleepDurationMinutes);
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
  public Flow<ReportEntity> getLatestReport() {
    final String _sql = "SELECT * FROM reports ORDER BY createdAtTimestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reports"}, new Callable<ReportEntity>() {
      @Override
      @Nullable
      public ReportEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDateString = CursorUtil.getColumnIndexOrThrow(_cursor, "dateString");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfCreatedAtTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtTimestamp");
          final int _cursorIndexOfStepCount = CursorUtil.getColumnIndexOrThrow(_cursor, "stepCount");
          final int _cursorIndexOfSleepScore = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepScore");
          final int _cursorIndexOfEnergyScore = CursorUtil.getColumnIndexOrThrow(_cursor, "energyScore");
          final int _cursorIndexOfAvgHeartRate = CursorUtil.getColumnIndexOrThrow(_cursor, "avgHeartRate");
          final int _cursorIndexOfWorkoutCount = CursorUtil.getColumnIndexOrThrow(_cursor, "workoutCount");
          final int _cursorIndexOfSleepDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepDurationMinutes");
          final ReportEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDateString;
            if (_cursor.isNull(_cursorIndexOfDateString)) {
              _tmpDateString = null;
            } else {
              _tmpDateString = _cursor.getString(_cursorIndexOfDateString);
            }
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final long _tmpCreatedAtTimestamp;
            _tmpCreatedAtTimestamp = _cursor.getLong(_cursorIndexOfCreatedAtTimestamp);
            final Integer _tmpStepCount;
            if (_cursor.isNull(_cursorIndexOfStepCount)) {
              _tmpStepCount = null;
            } else {
              _tmpStepCount = _cursor.getInt(_cursorIndexOfStepCount);
            }
            final Integer _tmpSleepScore;
            if (_cursor.isNull(_cursorIndexOfSleepScore)) {
              _tmpSleepScore = null;
            } else {
              _tmpSleepScore = _cursor.getInt(_cursorIndexOfSleepScore);
            }
            final Integer _tmpEnergyScore;
            if (_cursor.isNull(_cursorIndexOfEnergyScore)) {
              _tmpEnergyScore = null;
            } else {
              _tmpEnergyScore = _cursor.getInt(_cursorIndexOfEnergyScore);
            }
            final Integer _tmpAvgHeartRate;
            if (_cursor.isNull(_cursorIndexOfAvgHeartRate)) {
              _tmpAvgHeartRate = null;
            } else {
              _tmpAvgHeartRate = _cursor.getInt(_cursorIndexOfAvgHeartRate);
            }
            final Integer _tmpWorkoutCount;
            if (_cursor.isNull(_cursorIndexOfWorkoutCount)) {
              _tmpWorkoutCount = null;
            } else {
              _tmpWorkoutCount = _cursor.getInt(_cursorIndexOfWorkoutCount);
            }
            final Integer _tmpSleepDurationMinutes;
            if (_cursor.isNull(_cursorIndexOfSleepDurationMinutes)) {
              _tmpSleepDurationMinutes = null;
            } else {
              _tmpSleepDurationMinutes = _cursor.getInt(_cursorIndexOfSleepDurationMinutes);
            }
            _result = new ReportEntity(_tmpId,_tmpDateString,_tmpFilePath,_tmpCreatedAtTimestamp,_tmpStepCount,_tmpSleepScore,_tmpEnergyScore,_tmpAvgHeartRate,_tmpWorkoutCount,_tmpSleepDurationMinutes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
