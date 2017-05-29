package nl.fhict.happynews.android.model;

import java.util.Calendar;

/**
 * Created by Sander on 08/05/2017.
 */
public class NotificationSetting implements Comparable {
    private int hour;
    private int minute;
    private boolean enabled;

    /**
     * Constructor for notifications.
     * enabled = true by default.
     *
     * @param hour   hour of the notification
     * @param minute minutes for the notifications
     */
    public NotificationSetting(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        enabled = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns the time in a readable format.
     * Used by notificationAdapter.
     *
     * @return String
     */
    public String getTime() {
        return String.format("%d:%02d", hour, minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }


    @Override
    public int compareTo(Object o) {
        Calendar time = Calendar.getInstance();
        time.getTimeInMillis();
        Calendar other = Calendar.getInstance();
        other.getTimeInMillis();

        time.set(Calendar.HOUR_OF_DAY, this.hour);
        time.set(Calendar.MINUTE, this.minute);

        NotificationSetting otherNs = (NotificationSetting) o;
        other.set(Calendar.HOUR_OF_DAY, otherNs.hour);
        other.set(Calendar.MINUTE, otherNs.minute);

        if (time.before(other)) {
            return -1;
        } else if (time.after(other)) {
            return 1;
        }
        return 0;
    }
}
