package nl.fhict.happynews.android;

import nl.fhict.happynews.shared.Post;
import com.koushikdutta.ion.Ion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

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

    public ArrayList<Post> getNewPosts(){
        ArrayList<Post> newPosts = new ArrayList<>();

        return newPosts;
    }

}
