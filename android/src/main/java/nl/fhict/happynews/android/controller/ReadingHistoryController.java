package nl.fhict.happynews.android.controller;

import android.content.Context;
import nl.fhict.happynews.android.manager.FileManager;
import nl.fhict.happynews.android.model.Post;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tobi on 01-May-17.
 */
public class ReadingHistoryController {
    private static final String FILE = "readingHistory.set";
    private static Set<String> readingHistory;
    private static FileManager filemanager = FileManager.getInstance();

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
        if (!filemanager.fileExists(context, FILE)) {
            filemanager.writeFile(context, FILE, new HashSet<String>());
        }
        readingHistory = (Set<String>) filemanager.readFile(context, FILE);
    }

    /**
     * Checks if a post is already read.
     * @param post The post to be checked.
     * @return True if post is read.
     */
    public boolean postIsRead(Post post) {
        return readingHistory.contains(post.getUuid());
    }

    /**
     * Adds a post to the reading history list.
     * @param context The context.
     * @param post The post to add.
     */
    public void addReadPost(Context context, Post post) {
        readingHistory.add(post.getUuid());
        filemanager.writeFile(context, FILE, readingHistory);
    }

}
