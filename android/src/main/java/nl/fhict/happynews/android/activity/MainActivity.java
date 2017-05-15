package nl.fhict.happynews.android.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import nl.fhict.happynews.android.LoadListener;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.adapter.FeedAdapter;
import nl.fhict.happynews.android.controller.ReadingHistoryController;
import nl.fhict.happynews.android.manager.PostManager;
import nl.fhict.happynews.android.model.Page;
import nl.fhict.happynews.android.model.Post;
import nl.fhict.happynews.android.receiver.NotificationReceiver;
import java.util.ArrayList;
import java.util.Calendar;


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
        notificationManager.cancel(1);


        ReadingHistoryController.getInstance().initialize(this);


        setAlarms();
        
        postManager = PostManager.getInstance(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        feedAdapter = new FeedAdapter(this, new ArrayList<Post>());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setLayoutManager(layoutManager);

        postManager.setFeedAdapter(feedAdapter);

        swipeRefresh.setRefreshing(true);
        postManager.refresh(this, this);
        loading = true;

        addScrollListener();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postManager.refresh(MainActivity.this, MainActivity.this);
            }
        });
    }

    /**
     * Set alarms for notifications based on user preferences.
     */
    private void setAlarms() {
        //TODO Implement multiple alarms but have to wait for settings menu to be fixed.
        int hour = 16;
        int minute = 0;

        Calendar alarmTime = Calendar.getInstance();
        alarmTime.setTimeInMillis(System.currentTimeMillis());
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        if (alarmTime.before(calendar)) {
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day + 1);
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, alarmIntent);
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
