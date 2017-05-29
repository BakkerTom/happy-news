package nl.fhict.happynews.api.util;


public class HideRequest {

    private boolean hidden;

    protected HideRequest() {
    }

    public HideRequest(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }
}
