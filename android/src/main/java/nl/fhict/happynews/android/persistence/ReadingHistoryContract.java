package nl.fhict.happynews.android.persistence;

import android.provider.BaseColumns;

/**
 * Created by Tobi on 08-May-17.
 */
public final class ReadingHistoryContract {
    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "database.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String COMMA_SEP          = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ReadingHistoryContract() {}

    /* Inner class that defines the table contents */
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_NAME       = "History";
        public static final String COLUMN_POST_UUID = "Post_UUID";

        public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME
            + " ("
            + _ID
            + " INTEGER PRIMARY KEY,"
            + COLUMN_POST_UUID
            + TEXT_TYPE
            + " )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
