package nl.fhict.happynews.shared;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/** Class that holds the source and the type(latest or top) information necesarry for the newsapi.org apicall
 * Created by daan_ on 13-3-2017.
 */
@Document
public class Source {
    @Id
    private String uuid;

    @Indexed(unique = true)
    private String name;
    private String cleanName;
    private String type;

    /**
     * Source is source.
     * @param name The name.
     * @param cleanName The clean name.
     * @param type The type.
     */
    public Source(String name, String cleanName, String type) {
        this.name = name;
        this.cleanName = cleanName;
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

    public String getCleanName() {
        return cleanName;
    }

    public void setCleanName(String cleanName) {
        this.cleanName = cleanName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
