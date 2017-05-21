package nl.fhict.happynews.android.activity;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import com.google.gson.Gson;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.SourceSettingsAdapter;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.model.Source;
import nl.fhict.happynews.android.model.SourceSetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SourcesSettingsActivity extends AppCompatActivity {

    private Collection<SourceSetting> sources;
    private SourceSettingsAdapter sourcesAdapter;
    private ListView sourcesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_settings_sources);

        sourcesListView = (ListView) findViewById(R.id.sourcesListView);
        sources = SourceController.getInstance().getSources(getApplicationContext());
        Collections.sort((List<SourceSetting>)sources);
        refreshList();
        sourcesAdapter.setParentActivity(this);
    }

    /**
     * Back button implementation.
     *
     * @param item menuItem
     * @return boolean start activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
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
        for (SourceSetting source : updatedSources) {
            SourceController.getInstance().addSource(getApplicationContext(), source);
        }
        refreshList();
    }

    /**
     * Refresh the list of sources.
     */
    private void refreshList() {
        sourcesAdapter = new SourceSettingsAdapter(this,
            R.layout.activity_sources_settings,
            (ArrayList<SourceSetting>) sources);
        sourcesListView.setAdapter(sourcesAdapter);
        sourcesAdapter.setParentActivity(this);
        sourcesAdapter.notifyDataSetChanged();
    }
}
