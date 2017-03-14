package nl.fhict.happynews.crawler.models.newsapi;

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
    private String type;

    public Source(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Source(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
