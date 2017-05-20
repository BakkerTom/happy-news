package nl.fhict.happynews.android.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.google.gson.Gson;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.SourceManager;
import nl.fhict.happynews.android.adapter.SourceSettingsAdapter;
import nl.fhict.happynews.android.model.Source;
import nl.fhict.happynews.android.model.SourceSetting;

import java.util.ArrayList;

public class SourcesSettingsActivity extends AppCompatActivity {

    private ArrayList<SourceSetting> sources;
    private SharedPreferences preferences;
    private SourceSettingsAdapter sourcesAdapter;
    private ListView sourcesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_settings_sources);

        preferences = getApplicationContext().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        sourcesListView = (ListView) findViewById(R.id.sourcesListView);
        sources = createSourcesObjects();
        refreshList();
        sourcesAdapter.setParentActivity(this);
    }

    /**
     * Create sources for twitter quote and article,
     * Get the article sources from the api.
     *
     * @return list of source settings.
     */
    private ArrayList<SourceSetting> createSourcesObjects() {
        sources = new ArrayList<>();
        SourceSetting twitterSourceSetting = new SourceSetting("Twitter");
        SourceSetting quoteSourceSetting = new SourceSetting("Quote");
        SourceSetting articleSourceSetting = new SourceSetting("Article");

        sources.add(twitterSourceSetting);
        sources.add(quoteSourceSetting);
        sources.add(articleSourceSetting);

        SourceManager sourceManager = SourceManager.getInstance(this);
        ArrayList<Source> sourcesFromApi = sourceManager.getSources();

        for (Source s : sourcesFromApi) {
            sources.add(new SourceSetting(articleSourceSetting, s.getName()));
        }

        return sources;
    }

    /**
     * Updates the persisted notifications settings.
     *
     * @param updatedSources Arraylist of sourceSettings.
     */
    public void updateChanges(ArrayList<SourceSetting> updatedSources) {
        String sourcesGsonString = new Gson().toJson(updatedSources);
        SharedPreferences.Editor preferenesEditor = preferences.edit();
        preferenesEditor.putString(getString(R.string.preference_sources), sourcesGsonString);
        preferenesEditor.apply();
        refreshList();
    }

    /**
     * Refresh the list of sources.
     */
    private void refreshList() {
        sourcesAdapter = new SourceSettingsAdapter(this,
            R.layout.activity_sources_settings,
            sources);
        sourcesListView.setAdapter(sourcesAdapter);
        sourcesAdapter.setParentActivity(this);
        sourcesAdapter.notifyDataSetChanged();
    }
}
