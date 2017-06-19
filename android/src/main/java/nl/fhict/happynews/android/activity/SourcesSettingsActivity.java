package nl.fhict.happynews.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.SourceManager;
import nl.fhict.happynews.android.adapter.SourceSettingsAdapter;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.model.Source;
import nl.fhict.happynews.android.model.SourceSetting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SourcesSettingsActivity extends AppCompatActivity {

    private List<SourceSetting> sources;
    private SourceSettingsAdapter sourcesAdapter;
    private ListView sourcesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_settings_sources);

        refreshActivity();
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

    /**
     * Refresh the activity.
     */
    public void refreshActivity() {
        sourcesListView = (ListView) findViewById(R.id.sourcesListView);
        sources = SourceController.getInstance().getSources(getApplicationContext());
        Collections.sort(sources);
        refreshList();
        sourcesAdapter.setParentActivity(this);
    }
}
