package nl.fhict.happynews.android.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.util.Collection;

/**
 * Created by Tobi on 08-May-17.
 */
public class PreferenceJsonController {

    private static final String PREFERENCE = "HappyNews";
    private static Gson parser = new Gson();

    /**
     * Gets the value as an object.
     * @param context The context.
     * @param key The key.
     * @return An object.
     */
    public static Object get(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return parser.fromJson(settings.getString(key, ""), Object.class);
    }

    /**
     * Gets the value as an int.
     * @param context The context.
     * @param key The key.
     * @return An integer.
     */
    public static Integer getAsInt(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return parser.fromJson(settings.getString(key, ""), Integer.class);
    }

    /**
     * Gets the value as a collection.
     * @param context The context.
     * @param key the key.
     * @return A collection.
     */
    public static Collection<?> getAsCollection(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        return parser.fromJson(settings.getString(key, ""), Collection.class);
    }

    /**
     * Puts an object in the sharedPrefs.
     * @param context The context.
     * @param key The key.
     * @param value The object.
     */
    public static void put(Context context, String key, Object value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, parser.toJson(value));
        editor.commit();
    }
}
