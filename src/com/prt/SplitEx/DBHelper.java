package com.prt.SplitEx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DBNAME = "UserLog.db";

    // Table LOG Constants
    private static final String TABLE_LOG = "LogTable";
    private static final String LOG_KEY_COUNT = "LogCount";
    private static final String LOG_KEY_ID = "LogId";
    private static final String LOG_KEY_PASS = "LogPass";
    private static final String TABLE_LOG_CREATE = "CREATE TABLE IF NOT EXISTS LogTable("
            + "LogCount INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "LogId VARCHAR UNIQUE NOT NULL, LogPass VARCHAR NOT NULL)";

    private static DBHelper mDBHelper;

    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static DBHelper getInstance(Context context) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context.getApplicationContext());
        }
        return mDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_LOG_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        onCreate(sqLiteDatabase);
    }

    private ContentValues getValues(String id, String pass) {
        ContentValues cv = new ContentValues();
        cv.put(LOG_KEY_ID, id);
        cv.put(LOG_KEY_PASS, pass);
        return cv;
    }

    public void addLog(String LogID, String LogPass) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_LOG, null, getValues(LogID, LogPass));
    }

    private static String StringFormatForSQL(String val) {
        return '\'' + val + '\'';
    }
    public boolean doesLogExist(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_LOG,
                new String[]{LOG_KEY_ID, LOG_KEY_PASS},
                LOG_KEY_ID + " = " + StringFormatForSQL(id),
                null,
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
}
