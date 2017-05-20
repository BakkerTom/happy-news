package nl.fhict.happynews.android.manager;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.model.Page;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostManager {

    private static PostManager ourInstance;

    /**
     * Get (or create if it doesn't exist) the post manager.
     *
     * @param context Preferably a {@link Context} that will continue to exist (like the
     *                <code>ApplicationContext</code>).
     * @return The post manager instance.
     */
    public static synchronized PostManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new PostManager(context);
        }

        return ourInstance;
    }

    private String apiUrl;
    private FeedAdapter feedAdapter;
    private Context context;

    private PostManager(Context context) {
        this.context = context;

        apiUrl = context.getString(R.string.api_url);

        GsonBuilder builder = new GsonBuilder();
        registerTypeAdapter(builder);

        Gson gson = builder.create();

        Ion.getDefault(context)
            .configure()
            .setGson(gson);
    }

    /**
     * Sends content of a page to the FeedAdapter.
     *
     * @param page     The page number to load.
     * @param size     The amount of items on the page.
     * @param context  The context used to build the request.
     * @param listener The listener to notify when loading is completed.
     */
    public void load(int page, int size, Context context, final LoadListener listener) {
        loadPage(page, size, context, new FutureCallback<Page>() {
            @Override
            public void onCompleted(Exception e, Page result) {
                feedAdapter.addPage(result);

                if (listener != null) {
                    listener.onFinishedLoading();
                }
            }
        });
    }


    /**
     * Sends content of the first page to the FeedAdapter.
     * @param context The Application context.
     * @param listener Implementing the LoadListener Interface.
     */
    public void refresh(Context context, final LoadListener listener) {
        final int firstPage = 0;
        final int defaultPageSize = 20;

        loadPage(firstPage, defaultPageSize, context, new FutureCallback<Page>() {
            @Override
            public void onCompleted(Exception e, Page result) {
                feedAdapter.setPage(result);

                if (listener != null) {
                    listener.onFinishedLoading();
                }
            }
        });
    }


    /**
     * Loads a page from the server and returns its results in a FutureCallback.
     * @param page The requested page.
     * @param size The requested pagesize.
     * @param context The application context.
     * @param callback FutureCallback
     */
    private void loadPage(int page, int size, Context context, final FutureCallback<Page> callback) {
        Ion.with(context)
            .load(apiUrl + "/post?page=" + page + "&size=" + size)
            .as(new TypeToken<Page>() {
            }).setCallback(callback);
    }


    /**
     * Register an adapter to manage the date types as long values.
     * @param builder The GsonBuilder to register the TypeAdapter to.
     */
    private void registerTypeAdapter(GsonBuilder builder) {
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
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
