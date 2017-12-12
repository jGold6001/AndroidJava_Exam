package com.example.jaygold.examproject.dataBase;

import android.provider.BaseColumns;

/**
 * Created by SlavikAndIrishka on 12.12.2017.
 */

public class TableManager {
    public static final String DB_NAME = "organazer.db";
    public static final int DB_VERSION = 1;

    public class RecordEntry implements BaseColumns {
        public static final String TABLE = "records";
        public static final String COL_RECORD_TITLE = "title";
    }
}
