package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.activity.NotificationSettingsActivity;
import nl.fhict.happynews.android.model.NotificationSetting;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sander on 08/05/2017.
 */
public class NotificationAdapter extends ArrayAdapter<NotificationSetting> {

    private final Context context;
    private ArrayList<NotificationSetting> notifications;
    private NotificationSettingsActivity parentActivity;

    /**
     * Constructor for notificationAdapter.
     *
     * @param context       app context
     * @param resource      resource layout
     * @param notifications list of notifications
     */
    public NotificationAdapter(Context context,
                               @LayoutRes int resource,
                               @NonNull ArrayList<NotificationSetting> notifications) {
        super(context, resource, notifications);
        this.notifications = notifications;
        Collections.sort(notifications);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_notification, null, true);

        final NotificationSetting notification = notifications.get(position);
        final Switch notificationSwitch = (Switch) rowView.findViewById(R.id.notificationSwitch);
        final TextView notificationTextView = (TextView) rowView.findViewById(R.id.notificationTextView);

        notificationSwitch.setChecked(notification.isEnabled());
        notificationTextView.setText(notification.getTime());
        if (!notification.isEnabled()) {
            notificationTextView.setTextColor(Color.GRAY);
        }

        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.setEnabled(notificationSwitch.isChecked());
                if (notificationSwitch.isChecked()) {
                    notificationTextView.setTextColor(Color.BLACK);
                } else {
                    notificationTextView.setTextColor(Color.GRAY);
                }
                parentActivity.updateChanges(notifications);
            }
        });

        return rowView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public void setParentActivity(NotificationSettingsActivity parentActivity) {
        this.parentActivity = parentActivity;
    }
}

