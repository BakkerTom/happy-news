package nl.fhict.happynews.android;

import android.content.Context;
import android.util.Log;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.model.Post;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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
    private FeedAdapter feedAdapter;

    private PostManager() {}

    /**
     * Method that updates the feedAdapter with a new list of posts from the api
     *
     * @param context context of the apps
     */
    public void updatePosts(Context context) {
        Ion.with(context);
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();

        Ion.getDefault(context).configure().setGson(gson);
        Ion.with(context)
                .load(API_URL)
                .as(new TypeToken<List<Post>>() {
                })
                .setCallback(new FutureCallback<List<Post>>() {
                    @Override
                    public void onCompleted(Exception e, List<Post> posts) {
                        if (e == null) {
                            feedAdapter.updateData((ArrayList<Post>) posts);
                        } else {
                            Log.e("PostManager", "JSON Exception", e);
                        }
                    }
                });
    }

    /**
     * Assigns a feedAdapter to the PostManager
     *
     * @param feedAdapter
     */
    public void setFeedAdapter(FeedAdapter feedAdapter) {
        this.feedAdapter = feedAdapter;
    }
}
