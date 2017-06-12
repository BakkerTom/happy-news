package nl.fhict.happynews.android.controller;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import nl.fhict.happynews.android.SourceManager;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.model.Source;
import nl.fhict.happynews.android.model.SourceSetting;
import nl.fhict.happynews.android.persistence.PreferenceJsonController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
     *
     * @return An instance.
     */
    public static SourceController getInstance() {
        return instance;
    }

    private SourceController() {

    }

    /**
     * Initializes the Source Set.
     *
     * @param context The context.
     */
    public void initialize(Context context) {
        if (preferences.get(context, KEY) == null) {
            List<SourceSetting> sources = new ArrayList<>();
            SourceSetting twitterSourceSetting = new SourceSetting("twitter", "Twitter");
            SourceSetting quoteSourceSetting = new SourceSetting("quote", "Inspirational Quote");
            SourceSetting articleSourceSetting = new SourceSetting("article", "Article");

            sources.add(twitterSourceSetting);
            sources.add(quoteSourceSetting);
            sources.add(articleSourceSetting);

            SourceManager sourceManager = SourceManager.getInstance(context);
            Collection<Source> sourcesFromApi = sourceManager.getSources();

            for (Source s : sourcesFromApi) {
                SourceSetting src = new SourceSetting(s.getName(), s.getCleanName());
                src.setParent(articleSourceSetting);
                sources.add(src);
            }

            Collections.sort(sources);
            preferences.put(context, KEY, sources);
        }
    }

    /**
     * Gets the source.
     *
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
     *
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
     * Toggles the source's children.
     *
     * @param context The context.
     * @param srcName The sourcename.
     */
    public void toggleSourceChildren(Context context, String srcName, boolean value) {
        for (SourceSetting sourcesetting : getSources(context)) {
            if (sourcesetting.getParent() != null
                && sourcesetting.getParent().getName().equals(srcName)
                && sourcesetting.isEnabled() != value) {
                toggleSource(context, sourcesetting.getName());
            }
        }
    }

    /**
     * Gets all the sources that are blocked.
     *
     * @param context The context.
     * @return All sources.
     */
    public List<SourceSetting> getSources(Context context) {
        return preferences.getAsList(context, KEY, new TypeToken<List<SourceSetting>>() {
        });
    }

    /**
     * Adds a source to the blocked list.
     *
     * @param context The context.
     * @param source  The source.
     */
    public void addSource(Context context, SourceSetting source) {
        Collection<SourceSetting> sources = getSources(context);
        sources.add(source);
        preferences.put(context, KEY, sources);
    }

    /**
     * Removes a source from the blocked list.
     *
     * @param context The context.
     * @param source  The source.
     */
    public void removeSource(Context context, SourceSetting source) {
        Collection<SourceSetting> sources = getSources(context);
        for (SourceSetting src : sources) {
            if (src.getName().equals(source.getName())) {
                sources.remove(src);
                break;
            }
        }
        preferences.put(context, KEY, sources);
    }
}
