package nl.fhict.happynews.android.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.persistence.DatabaseHelper;
import nl.fhict.happynews.android.persistence.ReadingHistoryContract;

/**
 * Created by Tobi on 01-May-17.
 */
public class ReadingHistoryController {

    private static DatabaseHelper dbHelper;

    private static ReadingHistoryController instance = new ReadingHistoryController();

    private ReadingHistoryController() {

    }

    /**
     * Gets an instance of Filemanager.
     * @return An instance.
     */
    public static ReadingHistoryController getInstance() {
        return instance;
    }

    /**
     * Initializes the reading history.
     * @param context The context.
     */
    public void initialize(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    /**
     * Checks if a post is already read.
     * @param post The post to be checked.
     * @return True if post is read.
     */
    public boolean postIsRead(Post post) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean result = db.query(ReadingHistoryContract.HistoryEntry.TABLE_NAME,
            new String[]{ReadingHistoryContract.HistoryEntry.COLUMN_POST_UUID},
            ReadingHistoryContract.HistoryEntry.COLUMN_POST_UUID + " = ?",
            new String[]{ post.getUuid() },
            null,
            null,
            null
        ).getCount() > 0;
        db.close();
        return result;
    }

    /**
     * Adds a post to the reading history list.
     * @param post The post to add.
     */
    public void addReadPost(Post post) {
        if (postIsRead(post)) {
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ReadingHistoryContract.HistoryEntry.COLUMN_POST_UUID, post.getUuid());
        db.insert(ReadingHistoryContract.HistoryEntry.TABLE_NAME, null, val);
        db.close();
    }

}
