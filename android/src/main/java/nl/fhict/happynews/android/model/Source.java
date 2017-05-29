package nl.fhict.happynews.android.model;

/** Class that holds the source and the type(latest or top) information necesarry for the newsapi.org apicall
 * Created by daan_ on 13-3-2017.
 */
public class Source {
    private String uuid;

    private String name;
    private String sourceName;
    private String type;

    /**
     * Source is source.
     * @param name The name.
     * @param sourceName The clean name.
     * @param type The type.
     */
    public Source(String name, String sourceName, String type) {
        this.name = name;
        this.sourceName = sourceName;
        this.type = type;
    }

    /**
     * Source is source.
     */
    public Source(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
