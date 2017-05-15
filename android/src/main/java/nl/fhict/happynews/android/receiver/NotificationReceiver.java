package nl.fhict.happynews.android.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.activity.MainActivity;

/**
 * Notification Receiver that waits for an alarm to go off to send notification to user.
 * Created by daan_ on 8-5-2017.
 */
public class NotificationReceiver extends BroadcastReceiver {


    /**
     * Sends a notification to the user when called by an alarm.
     *
     * @param context applicationcontext
     * @param intent  the intent to open when called.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            );


        NotificationManager notificationManager;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_insert_emoticon_black_24dp)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.notification_text))
            .setContentIntent(resultPendingIntent)
            .build();

        notificationManager.notify(1, notification);
    }
}
