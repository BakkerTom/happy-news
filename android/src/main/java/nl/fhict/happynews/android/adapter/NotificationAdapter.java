package nl.fhict.happynews.android.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.CustomNotification;

import java.util.ArrayList;

/**
 * Created by Sander on 08/05/2017.
 */
public class NotificationAdapter  extends ArrayAdapter<CustomNotification> {

    private final Context context;
    private ArrayList<CustomNotification> notifications;

    /**
     * Constructor for notificationAdapter.
     * @param context app context
     * @param resource resource layout
     * @param notifications list of notifications
     */
    public NotificationAdapter(Context context,
                               @LayoutRes int resource,
                               @NonNull ArrayList<CustomNotification> notifications) {
        super(context, resource, notifications);
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_notification, null, true);

        CustomNotification notification = notifications.get(position);

        Switch notificationSwitch = (Switch) rowView.findViewById(R.id.notificationSwitch);

        notificationSwitch.setText(notification.getTime());
        notificationSwitch.setChecked(notification.isEnabled());

        return rowView;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}

