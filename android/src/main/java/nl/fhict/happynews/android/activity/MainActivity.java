package nl.fhict.happynews.android.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.controller.ReadingHistoryController;
import nl.fhict.happynews.android.controller.SourceController;
import nl.fhict.happynews.android.manager.AlarmManager;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.receiver.NotificationReceiver;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoadListener {

    private PostManager postManager;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FeedAdapter feedAdapter;


    private boolean loading;
    private int pastVisiblesItems;
    private int visibleItemCount;
    private int totalItemCount;
    private static final int PAGE_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager;
        notificationManager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationReceiver.NOTIFICATION_ID);

        ReadingHistoryController.getInstance().initialize(this);
        SourceController.getInstance().initialize(this);

        AlarmManager.setAlarms(this);

        postManager = PostManager.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        feedAdapter = new FeedAdapter(this, new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        postManager.setFeedAdapter(feedAdapter);

        // Display an error if not connected on start of this activity
        notConnectedError();

        swipeRefresh.setRefreshing(true);
        loading = true;
        postManager.refresh(this, this);

        addScrollListener();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Display an error if not connected
                notConnectedError();

                //Refresh the posts
                postManager.refresh(MainActivity.this, MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
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

    private void addScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //Check if scrolled down
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            Page lastPage = feedAdapter.getLastPage();
                            if (!lastPage.isLast()) {
                                postManager.load(
                                    lastPage.getNumber() + 1,
                                    PAGE_SIZE,
                                    MainActivity.this,
                                    MainActivity.this);
                            }
                        }
                    }
                }
            }
        });
    }


    /**
     * Shows an error message when the app is not connected to the internet.
     */
    public void notConnectedError() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        if (!isConnected) {
            AlertDialog alert = new AlertDialog.Builder(this).create();

            alert.setTitle("Uh oh...");
            alert.setMessage(getString(R.string.no_internet_message));
            alert.setButton(RESULT_OK, "Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Display an error if still not connected
                    notConnectedError();

                    postManager.refresh(MainActivity.this, MainActivity.this);
                }
            });

            alert.show();
        }
    }

    /**
     * When this activity is subscribed to a PostManager,
     * this notification will be called when content
     * is finished loading.
     */
    @Override
    public void onFinishedLoading() {
        loading = false;
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        feedAdapter.notifyDataSetChanged();
    }
}
