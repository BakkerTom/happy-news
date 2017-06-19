package nl.fhict.happynews.android.model;

/**
 * Created by Sander on 08/05/2017.
 */
public class SourceSetting implements Comparable<SourceSetting> {

    private SourceSetting parent;
    private boolean isEnabled = true;
    private String name;
    private String cleanName;

    /**
     * Constructor for sourceSetting.
     * @param parent parent sourceSetting object (sources of articles).
     * @param name name of the source.
     */
    public SourceSetting(SourceSetting parent, String name, String cleanName) {
        this.name = name;
        this.parent = parent;
        this.cleanName = cleanName;
        if (!parent.isEnabled()) {
            isEnabled = false;
        }
    }

    public SourceSetting(String name, String cleanName) {
        this.name = name;
        this.cleanName = cleanName;
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
        this.parent = setting;
    }

    public String getCleanName() {
        return cleanName;
    }

    public void setCleanName(String cleanName) {
        this.cleanName = cleanName;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.isEnabled;
    }

    @Override
    public int compareTo(SourceSetting o) {
        return this.getName().compareTo(o.getName());
    }
}
