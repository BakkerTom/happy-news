package nl.fhict.happynews.crawler.model.instagramapi;

import java.util.Date;

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
    private Date createdTime;
    private String[] usersInPhoto;
    private String type;
    private InstagramUser user;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public String[] getUsersInPhoto() {
        return usersInPhoto;
    }

    public String getType() {
        return type;
    }

    public InstagramUser getUser() {
        return user;
    }

    public String getComments() {
        return comments;
    }

    public String getFilter() {
        return filter;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public void setCaption(String[] caption) {
        this.caption = caption;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public void setUsersInPhoto(String[] usersInPhoto) {
        this.usersInPhoto = usersInPhoto;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser(InstagramUser user) {
        this.user = user;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", tags = "+tags+", location = "+location+", link = "+link+", likes = "+likes+", images = "+images+", caption = "+caption+", createdTime = "+ createdTime +", usersInPhoto = "+ usersInPhoto +", type = "+type+", user = "+user+", comments = "+comments+", filter = "+filter+"]";
    }

    public class InstagramUser {
        private String username;
        private String profilePicture;
        private long id;
        private String fullName;

        public String getUsername() {
            return username;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public long getId() {
            return id;
        }

        public String getFullName() {
            return fullName;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}