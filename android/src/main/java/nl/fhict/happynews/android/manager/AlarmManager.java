package nl.fhict.happynews.android.manager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.fhict.happynews.android.R;
import nl.fhict.happynews.android.model.NotificationSetting;
import nl.fhict.happynews.android.receiver.NotificationReceiver;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Sets the alarms for the notifications.
 * Created by daan_ on 15-5-2017.
 */
public class AlarmManager {

    /**
     * Set alarms for notifications based on user preferences.
     * @param context application context.
     */
    public static void setAlarms(Context context) {
        List<NotificationSetting> alarms = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences(
            context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String notificationsGsonString = preferences.getString(context.getString(R.string.preference_notifications),
            "");
        Type type = new TypeToken<List<NotificationSetting>>() {
        }.getType();
        if (!notificationsGsonString.equals("")) {
            alarms = new Gson().fromJson(notificationsGsonString, type);
        }
        
        for (int i = 0; i < alarms.size(); i++) {
            int hour = alarms.get(i).getHour();
            int minute = alarms.get(i).getMinute();

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
            calendar.set(Calendar.MINUTE, minute);

            android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NotificationReceiver.class);
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, i, intent, 0);
            alarmManager.setRepeating(android.app.AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                android.app.AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }
}
