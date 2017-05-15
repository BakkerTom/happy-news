package nl.fhict.happynews.android.model;

/**
 * Created by Sander on 08/05/2017.
 */
public class SourceSetting {

    private SourceSetting parent;
    private boolean isEnabled = true;
    private String name;

    /**
     * Constructor for sourceSetting.
     * @param parent parent sourceSetting object (sources of articles).
     * @param name name of the source.
     */
    public SourceSetting(SourceSetting parent, String name) {
        this.name = name;
        this.parent = parent;
        if (!parent.isEnabled()) {
            isEnabled = false;
        }
    }

    public SourceSetting(String name) {
        this.name = name;
    }

    public SourceSetting getParent() {
        return parent;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    public boolean isParent() {
        return parent == null;
    }

    public void setParent(SourceSetting setting) {
        parent.setParent(setting);
    }
}