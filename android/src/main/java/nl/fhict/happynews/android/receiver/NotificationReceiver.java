package nl.fhict.happynews.android.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import nl.fhict.happynews.android.R;

/**
 * Created by daan_ on 8-5-2017.
 */
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

        //}

        NotificationManager notificationManager;
        notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.articlebadge)
                .setContentTitle("My notification")
                .setContentText("Hello World!").build();

        notificationManager.notify(R.string.app_name, notification);
    }
}
