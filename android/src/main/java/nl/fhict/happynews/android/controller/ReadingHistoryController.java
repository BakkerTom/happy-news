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
    private Set<String> readingHistory;
    private FileManager filemanager = FileManager.getInstance();

    /**
     * Creates the ReadingHistoryController.
     * @param context The context.
     */
    public ReadingHistoryController(Context context) {
        initialize(context);
    }

    /**
     * Initializes the reading history.
     * @param context The context.
     */
    private void initialize(Context context) {
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
