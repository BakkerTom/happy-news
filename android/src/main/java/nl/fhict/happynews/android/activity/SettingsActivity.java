package nl.fhict.happynews.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import nl.fhict.happynews.android.R;

public class SettingsActivity extends AppCompatActivity {

    private Button languageButton;
    private Button notificationsButton;
    private Button sourcesButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.settings_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        languageButton = (Button) findViewById(R.id.btn_settings_language);
        notificationsButton = (Button) findViewById(R.id.btn_settings_notifications);
        sourcesButton = (Button) findViewById(R.id.btn_settings_sources);
        aboutButton = (Button) findViewById(R.id.btn_settings_about);

        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), LanguageSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), NotificationSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        sourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), SourcesSettingsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    /**
     * back button implementation.
     * @param item menuItem
     * @return boolean start activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
