package nl.fhict.happynews.android.controller;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.model.Source;
import nl.fhict.happynews.android.model.SourceSetting;
import nl.fhict.happynews.android.persistence.PreferenceJsonController;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Tobi on 08-May-17.
 */
public class SourceController {

    private static final String KEY = "SOURCES";
    private static PreferenceJsonController<SourceSetting> preferences = new PreferenceJsonController<>();

    private static SourceController instance = new SourceController();

    /**
     * Returns the instance.
     * @return An instance.
     */
    public static SourceController getInstance() {
        return instance;
    }

    private SourceController() {

    }

    /**
     * Initializes the Source Set.
     * @param context The context.
     */
    public void initialize(Context context) {
        if (preferences.get(context, KEY) == null) {
            preferences.put(context, KEY, new HashSet<SourceSetting>());
        }
    }

    /**
     * Gets the source.
     * @param context The context.
     * @param srcName The source name.
     * @return The source.
     */
    public SourceSetting getSource(Context context, String srcName) {
        for (SourceSetting src : getSources(context)) {
            if (src.getName().equals(srcName)) {
                return src;
            }
        }
        return null;
    }

    /**
     * Toggles the source.
     * @param context The context.
     * @param srcName The sourcename.
     */
    public void toggleSource(Context context, String srcName) {
        SourceSetting src = getSource(context, srcName);
        removeSource(context, src);
        src.setEnabled(!src.isEnabled());
        addSource(context, src);

    }

    /**
     * Gets all the sources that are blocked.
     * @param context The context.
     * @return All sources.
     */
    public Collection<SourceSetting> getSources(Context context) {
        return preferences.getAsCollection(context, KEY, new TypeToken<Collection<SourceSetting>>(){});
    }

    /**
     * Adds a source to the blocked list.
     * @param context The context.
     * @param source The source.
     */
    public void addSource(Context context, SourceSetting source) {
        Collection<SourceSetting> sources = getSources(context);
        sources.add(source);
        preferences.put(context, KEY, sources);
    }

    /**
     * Removes a source from the blocked list.
     * @param context The context.
     * @param source The source.
     */
    public void removeSource(Context context, SourceSetting source) {
        Collection<SourceSetting> sources = getSources(context);
        sources.remove(source);
        preferences.put(context, KEY, sources);
    }
}
