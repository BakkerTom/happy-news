package nl.fhict.happynews.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.SettingsAdapter;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private ListView settingsList;
    final ArrayList<String> settings = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(R.string.title_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settings.add("Notifications");
        settings.add("Sources");
        settings.add("About");
        settings.add("Rate App");

        settingsList = (ListView) findViewById(R.id.settingsListView);
        SettingsAdapter settingsAdapter = new SettingsAdapter(this, R.layout.activity_settings, settings);
        settingsList.setAdapter(settingsAdapter);

        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chosenSetting = settings.get(position);

                switch (chosenSetting) {
                    case "Notifications":
                        Intent notificationSettingsIntent = new Intent(getApplicationContext(),
                            NotificationSettingsActivity.class);
                        startActivityForResult(notificationSettingsIntent, 0);
                        break;
                    case "Sources":
                        Intent sourcesSettingsIntent = new Intent(getApplicationContext(),
                            SourcesSettingsActivity.class);
                        startActivityForResult(sourcesSettingsIntent, 0);
                        break;
                    case "About":
                        Intent aboutActivityIntent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivityForResult(aboutActivityIntent, 0);
                        break;
                    case "Rate App":
                        openAppInAppStore();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * Opens the app in the app store.
     */
    private void openAppInAppStore() {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    /**
     * back button implementation.
     *
     * @param item menuItem
     * @return boolean start activity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
