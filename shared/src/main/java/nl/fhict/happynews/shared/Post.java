package nl.fhict.happynews.shared;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/** The class that contains information about a newspost
 * Created by daan_ on 6-3-2017.
 */
@Entity
public class Post {

    private String uid;
    private String source;
    private String author;
    private String title;
    private String description;
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