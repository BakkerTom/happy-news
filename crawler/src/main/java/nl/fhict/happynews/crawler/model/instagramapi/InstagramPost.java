package nl.fhict.happynews.crawler.model.instagramapi;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class InstagramPost {

    private String id;
    private String[] tags;
    private String location;
    private String link;
    private int likes;
    private String[] images;
    private String[] caption;
    private String createdTime;
    private String[] usersInPhoto;
    private String type;
    private String[] user;
    private String comments;
    private String filter;

    public InstagramPost() {
    }

    public String getId() {
        return id;
    }

    public String[] getTags() {
        return tags;
    }

    public String getLocation() {
        return location;
    }

    public String getLink() {
        return link;
    }

    public int getLikes() {
        return likes;
    }

    public String[] getImages() {
        return images;
    }

    public String[] getCaption() {
        return caption;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String[] getUsersInPhoto() {
        return usersInPhoto;
    }

    public String getType() {
        return type;
    }

    public String[] getUser() {
        return user;
    }

    public String getComments() {
        return comments;
    }

    public String getFilter() {
        return filter;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id
            + ", tags = " + tags
            + ", location = " + location
            + ", link = " + link
            + ", likes = " + likes
            + ", images = " + images
            + ", caption = " + caption
            + ", createdTime = " + createdTime
            + ", usersInPhoto = " + usersInPhoto
            + ", type = " + type
            + ", user = " + user
            + ", comments = " + comments
            + ", filter = " + filter
            + "]";
    }
}
