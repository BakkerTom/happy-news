package nl.fhict.happynews.android;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostManager {
    private static PostManager ourInstance = new PostManager();

    public static PostManager getInstance() {
        return ourInstance;
    }

    private PostManager() {
    }




}
