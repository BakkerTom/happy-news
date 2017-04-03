package nl.fhict.happynews.android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import nl.fhict.happynews.android.activity.MainActivity;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.model.Page;
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

    private String API_URL = "https://happynews-api.svendubbeld.nl/";
    private ArrayList<Post> newPosts;
    private FeedAdapter feedAdapter;

    private MainActivity activity;

    private PostManager() {}

    public void subscribeActivity(MainActivity activity){
        this.activity = activity;
    }

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
                .load(API_URL + "/post")
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

    public void loadPage(int page, int size, Context context){

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
                .load("http://10.0.2.2:8080/postpage?page=" + page + "&size=" + size)
                .as(new TypeToken<Page>() {
                })
                .setCallback(new FutureCallback<Page>() {
                    @Override
                    public void onCompleted(Exception e, Page result) {
                        if (e == null){
                            Log.d("PostManager", "Loaded page: " + result.getNumber());
                            feedAdapter.addPage(result);

                            if (activity != null){
                                activity.didFinishLoading(); //Notifiy activity that loading is done
                            }
                        } else {
                            Log.e("PostManager", "Json Exception: ", e);
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
