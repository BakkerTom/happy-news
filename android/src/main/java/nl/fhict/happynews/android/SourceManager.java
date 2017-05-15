package nl.fhict.happynews.android;

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
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Source;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Sander on 09/05/2017.
 */
public class SourceManager {
    private static SourceManager ourInstance;

    private String apiUrl;
    private Context context;

    /**
     * Get (or create if it doesn't exist) the source manager.
     *
     * @param context Preferably a {@link Context} that will continue to exist (like the
     *                <code>ApplicationContext</code>).
     * @return The source manager instance.
     */
    public static synchronized SourceManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SourceManager(context);
        }

        return ourInstance;
    }

    /**
     * Constructor for the sourceManager.
     *
     * @param context applicationContext.
     */
    private SourceManager(Context context) {
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

    /**
     * Get all the Sources from the API.
     *
     * @return list of sources
     */
    public ArrayList<Source> getSources() {
        try {
            return Ion.with(context)
                .load(apiUrl + "/sources")
                .as(new TypeToken<ArrayList<Source>>() {
                }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
