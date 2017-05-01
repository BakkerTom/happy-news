package nl.fhict.happynews.android.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Switch;
import nl.fhict.happynews.android.R;

public class SourcesSettingsActivity extends AppCompatActivity {

    private Switch twitterSwitch;
    private Switch articleSwitch;
    private Switch quotesSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.settings_sources_title);

        twitterSwitch = (Switch) findViewById(R.id.switch_twitter);
        articleSwitch = (Switch) findViewById(R.id.switch_articles);
        quotesSwitch = (Switch) findViewById(R.id.switch_quotes);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    /**
     * back button implementation.
     *
     * @param item menuItem
     * @return boolean start activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
