package com.example.jaygold.examproject.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SlavikAndIrishka on 12.12.2017.
 */

public class SQLiteHeplper extends SQLiteOpenHelper {

    public SQLiteHeplper(Context context) {
        super(context, TableManager.DB_NAME, null, TableManager.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TableManager.RecordEntry.TABLE + " ( " +
                TableManager.RecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TableManager.RecordEntry.COL_RECORD_TITLE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableManager.RecordEntry.TABLE);
        onCreate(sqLiteDatabase);
    }
}
