package nl.fhict.happynews.android.activity;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.SwipeDismissListViewTouchListener;
import nl.fhict.happynews.android.adapter.NotificationAdapter;
import nl.fhict.happynews.android.fragments.TimePickerFragment;
import nl.fhict.happynews.android.model.NotificationSetting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationSettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private ListView notificationListView;
    private FloatingActionButton addNotificationFab;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationSetting> notifications;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.title_settings_notification);
        notificationListView = (ListView) findViewById(R.id.notificationListView);

        preferences = getApplicationContext().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        addNotificationFab = (FloatingActionButton) findViewById(R.id.timePickerFAB);
        addNotificationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        notifications = new ArrayList<>();
        String notificationsGsonString = preferences.getString(getString(R.string.preference_notifications), "");
        Type type = new TypeToken<List<NotificationSetting>>() {
        }.getType();
        if (!notificationsGsonString.equals("")) {
            notifications = new Gson().fromJson(notificationsGsonString, type);
        }
        refreshList();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        notifications.add(new NotificationSetting(hourOfDay, minute));
        updateChanges(notifications);
        refreshList();
    }

    /**
     * Updates the persisted notifications settings.
     *
     * @param updatedNotifications updated list
     */
    public void updateChanges(ArrayList<NotificationSetting> updatedNotifications) {
        String notificationsGsonString = new Gson().toJson(updatedNotifications);
        SharedPreferences.Editor preferenesEditor = preferences.edit();
        preferenesEditor.putString(getString(R.string.preference_notifications), notificationsGsonString);
        preferenesEditor.apply();
    }

    /**
     * Create a new adapter with the list of notifications.
     * sets adapter to listView and notify the adapter.
     */
    private void refreshList() {
        notificationAdapter = new NotificationAdapter(this,
            R.layout.activity_notification_settings,
            notifications);
        notificationListView.setAdapter(notificationAdapter);

        SwipeDismissListViewTouchListener touchListener =
            new SwipeDismissListViewTouchListener(
                notificationListView,
                new SwipeDismissListViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            notifications.remove(position);
                            updateChanges(notifications);
                        }
                    }
                });
        notificationListView.setOnTouchListener(touchListener);
        notificationAdapter.setParentActivity(this);
        notificationAdapter.notifyDataSetChanged();
    }
}
