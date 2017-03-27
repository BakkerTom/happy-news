package nl.fhict.happynews.crawler.models.instagramapi;

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
    private String created_time;
    private String[] users_in_photo;
    private String type;
    private String[] user;
    private String comments;
    private String filter;

    public InstagramPost(){

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

    public String getCreated_time() {
        return created_time;
    }

    public String[] getUsers_in_photo() {
        return users_in_photo;
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
    public String toString()
    {
        return "ClassPojo [id = "+id+", tags = "+tags+", location = "+location+", link = "+link+", likes = "+likes+", images = "+images+", caption = "+caption+", created_time = "+created_time+", users_in_photo = "+users_in_photo+", type = "+type+", user = "+user+", comments = "+comments+", filter = "+filter+"]";
    }
}
