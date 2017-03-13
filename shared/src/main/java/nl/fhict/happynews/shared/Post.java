package nl.fhict.happynews.shared;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

package nl.fhict.happynews.shared;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/** The class that contains information about a newspost
 * Created by daan_ on 6-3-2017.
 */
@Document
public class Post {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private String uuid;
    private String source;
    private String author;
    private String title;
    private String description;
    @Indexed(unique = true)
    private String url;
    private String imageUrl;
    private Date publishedAt;

    public Post() {
    }

    public Post(String source, String author, String title, String description, String url, String imageUrl, Date publishedAt) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.publishedAt = publishedAt;
    }
    public String getUid() {
        return uuid;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
}
