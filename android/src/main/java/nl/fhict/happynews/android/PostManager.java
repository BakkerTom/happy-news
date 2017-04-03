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

    /**
     * Sends content of a page to the FeedAdapter
     * @param page
     * @param size
     * @param context
     */
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
                .load(API_URL + "?page=" + page + "&size=" + size)
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

    /**
     * Subscribes an Activity to the PostManager to notify when finished loading
     * @param activity
     */
    public void subscribeActivity(MainActivity activity){
        this.activity = activity;
    }
}
