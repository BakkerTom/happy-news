package nl.fhict.happynews.android;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.Models.Post;
import com.koushikdutta.ion.Ion;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostManager {
    private static PostManager ourInstance = new PostManager();

    public static PostManager getInstance() {
        return ourInstance;
    }

    private String API_URL = "";

    private PostManager() {
    }

    /**
     * Method that returns the new posts from the api
     *
     * @param c context of the apps
     * @return
     */
    public ArrayList<Post> getNewPosts(Context c){
        final ArrayList<Post> newPosts = new ArrayList<>();
        Ion.with(c)
                .load(API_URL)
                .as(new TypeToken<List<Post>>(){})
                .setCallback(new FutureCallback<List<Post>>() {
                    @Override
                    public void onCompleted(Exception e, List<Post> posts) {
                       newPosts.addAll(posts);
                    }
                });
        return newPosts;
    }

}
