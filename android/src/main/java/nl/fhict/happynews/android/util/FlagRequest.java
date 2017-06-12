package nl.fhict.happynews.android.util;

/**
 * Holds the body for a flag request to the api
 * Created by daan_ on 12-6-2017.
 */
public class FlagRequest {

    private String reason;

    public FlagRequest() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
