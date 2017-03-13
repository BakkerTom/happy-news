package nl.fhict.happynews.android;

import android.content.Context;
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
     * Method that returns the new posts from the api
     *
     * @param c context of the apps
     * @return
     */
    public void updatePosts(Context c) {
        newPosts = new ArrayList<>();
        Ion.with(c)
                .load(API_URL)
                .as(new TypeToken<List<Post>>() {
                })
                .setCallback(new FutureCallback<List<Post>>() {
                    @Override
                    public void onCompleted(Exception e, List<Post> posts) {
                        newPosts.addAll(posts);
                        postAdapter.updateData(newPosts);
                    }
                });
    }

    public void setPostAdapter(PostAdapter postAdapter) {
        this.postAdapter = postAdapter;
    }
}
