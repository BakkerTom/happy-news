package nl.fhict.happynews.android;

import android.content.Context;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.Adapters.PostAdapter;
import nl.fhict.happynews.android.Models.Post;
import com.koushikdutta.ion.Ion;

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

    private String API_URL = "https://happynews-api.svendubbeld.nl/post";
    private ArrayList<Post> newPosts;
    private PostAdapter postAdapter;

    private PostManager() {
    }

    /**
     * Method that updates the postAdapter with a new list of posts from the api
     *
     * @param c context of the apps
     */
    public void updatePosts(Context c) {
        Ion.with(c)
                .load(API_URL)
                .as(new TypeToken<List<Post>>() {
                })
                .setCallback(new FutureCallback<List<Post>>() {
                    @Override
                    public void onCompleted(Exception e, List<Post> posts) {
                        if (e == null) {
                            postAdapter.updateData((ArrayList<Post>) posts);
                        } else {
                            Log.e("PostManager", "JSON Exception", e);
                        }
                    }
                });
    }

    /**
     * Assigns a postAdapter to the PostManager
     *
     * @param postAdapter
     */
    public void setPostAdapter(PostAdapter postAdapter) {
        this.postAdapter = postAdapter;
    }
}
