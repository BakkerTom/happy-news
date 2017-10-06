package nl.fhict.happynews.android.activity;

import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.ads.MobileAds;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.controller.ReadingHistoryController;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.fragments.MainFragment;
import nl.fhict.happynews.android.fragments.SearchFragment;
import nl.fhict.happynews.android.manager.AlarmManager;
import nl.fhict.happynews.android.receiver.NotificationReceiver;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private State state = State.DEFAULT;

    private MainFragment mainFragment;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationReceiver.NOTIFICATION_ID);

        ReadingHistoryController.getInstance().initialize(getApplicationContext());
        SourceController.getInstance().initialize(getApplicationContext());

        AlarmManager.setAlarms(getApplicationContext());

        mainFragment = new MainFragment();
        searchFragment = new SearchFragment();

        getFragmentManager().beginTransaction()
            .replace(R.id.fragment, mainFragment)
            .commit();

        MobileAds.initialize(this, "ca-app-pub-5654718756177389~7192583916");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);

        final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true; // Handle query here, do not launch search intent
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.isEmpty()) {
            Log.d("Search", "Query empty");

            if (state == State.SEARCH) {
                Log.d("Search", "Closing SearchFragment");

                state = State.DEFAULT;
                getFragmentManager().popBackStack("search", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        } else {
            if (state != State.SEARCH) {
                Log.d("Search", "Opening SearchFragment");

                state = State.SEARCH;
                getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, searchFragment)
                    .addToBackStack("search")
                    .commit();

                getFragmentManager().executePendingTransactions();
            }

            searchFragment.onSearch(query);
        }

        return true;
    }

    private enum State {
        DEFAULT,
        SEARCH
    }
}
