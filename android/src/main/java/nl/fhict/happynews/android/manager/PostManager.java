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
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.SourceSetting;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by Sander on 06/03/2017.
 */
public class PostManager {

    public static final int DEFAULT_PAGE_SIZE = 20;

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

    private PostManager(Context context) {

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
     * @param callback The callback when the page is loaded.
     */
    public void load(int page, int size, final Context context, final FutureCallback<Page> callback) {
        loadPage(page, size, context, callback);
    }

    /**
     * Sends content of a page to the FeedAdapter, filtered by the query.
     *
     * @param query    The search query.
     * @param page     The page number to load.
     * @param size     The amount of items on the page.
     * @param context  The context used to build the request.
     * @param callback The callback when the page is loaded.
     */
    public void load(String query, int page, int size, final Context context, final FutureCallback<Page> callback) {
        loadPage(query, page, size, context, callback);
    }


    /**
     * Sends content of the first page to the FeedAdapter.
     *
     * @param context  The Application context.
     * @param callback The callback when the page is loaded.
     */
    public void refresh(final Context context, final FutureCallback<Page> callback) {
        final int firstPage = 0;

        loadPage(firstPage, DEFAULT_PAGE_SIZE, context, callback);
    }

    /**
     * Sends content of the first page to the FeedAdapter, filtered by the query.
     *
     * @param query The search query.
     * @param context  The Application context.
     * @param callback The callback when the page is loaded.
     */
    public void refresh(String query, final Context context, final FutureCallback<Page> callback) {
        final int firstPage = 0;

        loadPage(query, firstPage, DEFAULT_PAGE_SIZE, context, callback);
    }


    /**
     * Loads a page from the server and returns its results in a FutureCallback.
     *
     * @param page     The requested page.
     * @param size     The requested pagesize.
     * @param context  The application context.
     * @param callback FutureCallback
     */
    private void loadPage(int page, int size, Context context, final FutureCallback<Page> callback) {
        Ion.with(context)
            .load(apiUrl + "/post?page=" + page + "&size=" + size + "&whitelist=" + generateWhitelist(context))
            .as(new TypeToken<Page>() {
            }).setCallback(callback);
    }

    /**
     * Loads a page from the server and returns its results in a FutureCallback.
     *
     * @param page     The requested page.
     * @param size     The requested pagesize.
     * @param context  The application context.
     * @param callback FutureCallback
     */
    private void loadPage(String query, int page, int size, Context context, final FutureCallback<Page> callback) {
        Ion.with(context)
            .load(apiUrl + "/post")
            .addQuery("page", String.valueOf(page))
            .addQuery("size", String.valueOf(size))
            .addQuery("query", query)
            .addQuery("whitelist", generateWhitelist(context))
            .as(new TypeToken<Page>() {
            }).setCallback(callback);
    }

    private String generateWhitelist(Context context) {
        String whitelist = "";
        List<SourceSetting> sources = SourceController.getInstance().getSources(context);

        for (SourceSetting source : sources) {
            if (source.isEnabled()) {
                whitelist += source.getName() + ",";
            }
        }
        return whitelist.substring(0, whitelist.length() - 1);
    }


    /**
     * Register an adapter to manage the date types as long values.
     *
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
}
