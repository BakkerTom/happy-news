package nl.fhict.happynews.android.controller;

import android.content.Context;
import nl.fhict.happynews.android.persistence.PreferenceJsonController;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Tobi on 08-May-17.
 */
public class SourceController {

    private static final String KEY = "SOURCES";

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
        if (PreferenceJsonController.get(context, KEY) == null) {
            PreferenceJsonController.put(context, KEY, new HashSet<String>());
        }
    }

    /**
     * Gets all the sources that are blocked.
     * @param context The context.
     * @return All sources.
     */
    public Collection<String> getSources(Context context) {
        return (Collection<String>)PreferenceJsonController.getAsCollection(context, KEY);
    }

    /**
     * Adds a source to the blocked list.
     * @param context The context.
     * @param source The source.
     */
    public void addSource(Context context, String source) {
        Collection<String> sources = getSources(context);
        sources.add(source);
        PreferenceJsonController.put(context, KEY, sources);
    }

    /**
     * Removes a source from the blocked list.
     * @param context The context.
     * @param source The source.
     */
    public void removeSource(Context context, String source) {
        Collection<String> sources = getSources(context);
        sources.remove(source);
        PreferenceJsonController.put(context, KEY, sources);
    }
}
