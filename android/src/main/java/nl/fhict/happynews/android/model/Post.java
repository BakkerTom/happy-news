package nl.fhict.happynews.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class Post {

    private String uuid;
    private String source;
    private String sourceName;
    private String author;
    private String title;
    private String contentText;
    private String url;
    private List<String> imageUrls = new ArrayList<>();
    private String videoUrl;
    private Date publishedAt;
    private Date indexedAt;
    private double positivityScore;
    private Date expirationDate;
    private Type type;
    private List<String> tags = new ArrayList<>();

    public Post() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getSource() {
        return source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContentText() {
        return contentText;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getImageUrls() {
        return new ArrayList<>(imageUrls);
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Date getIndexedAt() {
        return indexedAt;
    }

    public double getPositivityScore() {
        return positivityScore;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public boolean isMarkedForDeletion() {
        return expirationDate == null;
    }

    public Type getType() {
        return type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setIndexedAt(Date indexedAt) {
        this.indexedAt = indexedAt;
    }

    public void setPositivityScore(double positivityScore) {
        this.positivityScore = positivityScore;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public enum Type {
        @SerializedName("article")
        ARTICLE,
        @SerializedName("tweet")
        TWEET,
        @SerializedName("quote")
        QUOTE
    }
}