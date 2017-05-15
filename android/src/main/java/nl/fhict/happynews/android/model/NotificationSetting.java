package nl.fhict.happynews.android.model;

/**
 * Created by Sander on 08/05/2017.
 */
public class NotificationSetting {
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
}
