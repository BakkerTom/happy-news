package nl.fhict.happynews.android;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.model.Page;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostManager {

    private static PostManager ourInstance = new PostManager();

    public static PostManager getInstance() {
        return ourInstance;
    }

    private static final String API_URL = "https://happynews-api.svendubbeld.nl";
    private FeedAdapter feedAdapter;

    private PostManager() {
    }

    /**
     * Sends content of a page to the FeedAdapter.
     *
     * @param page     The page number to load.
     * @param size     The amount of items on the page.
     * @param context  The context used to build the request.
     * @param listener The listener to notify when loading is completed.
     */
    public void loadPage(int page, int size, Context context, final LoadListener listener) {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json,
                                    Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();

        Ion.getDefault(context).configure().setGson(gson);
        Ion.with(context)
            .load(API_URL + "/post?page=" + page + "&size=" + size)
            .as(new TypeToken<Page>() {
            })
            .setCallback(new FutureCallback<Page>() {
                @Override
                public void onCompleted(Exception e, Page result) {
                    if (e == null) {
                        Log.d("PostManager", "Loaded page: " + result.getNumber());

                        feedAdapter.addPage(result);

                        if (listener != null) {
                            listener.onFinishedLoading(); //Notify the listening activity when the loading is finished
                        }
                    } else {
                        Log.e("PostManager", "Json Exception: ", e);
                    }
                }
            });
    }


    /**
     * Sends content of the first page to the FeedAdapter
     * @param context
     * @param listener
     */
    public void refresh(Context context, final LoadListener listener) {
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
                .as(new TypeToken<Page>() {
                })
                .setCallback(new FutureCallback<Page>() {
                    @Override
                    public void onCompleted(Exception e, Page result) {
                        if (e == null){
                            Log.d("PostManager", "Loaded page: " + result.getNumber());

                            feedAdapter.setPage(result);

                            if (listener != null){
                                listener.onFinishedLoading();
                            }
                        } else {
                            Log.e("PostManager", "Json Exception: ", e);
                        }
                    }
                });
    }

    /**
     * Assigns a feedAdapter to the PostManager.
     *
     * @param feedAdapter The feed adapter.
     */
    public void setFeedAdapter(FeedAdapter feedAdapter) {
        this.feedAdapter = feedAdapter;
    }


}
