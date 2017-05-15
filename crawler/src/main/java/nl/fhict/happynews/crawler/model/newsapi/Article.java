package nl.fhict.happynews.crawler.model.newsapi;

import org.joda.time.DateTime;

/**
 * Article from a news source according to newsapi.org format
 * Created by daan_ on 6-3-2017.
 */
public class Article {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private DateTime publishedAt;


    public Article() {
    }

    /**
     * Create a new article from a news source.
     *
     * @param author      The name of the author.
     * @param title       The title of the article.
     * @param description A short description of the article.
     * @param url         The url to the article.
     * @param imageUrl    An optional link to the image associated with the article.
     * @param publishedAt The date the article is published.
     */
    public Article(String author, String title, String description, String url, String imageUrl, DateTime publishedAt) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = imageUrl;
        this.publishedAt = publishedAt;

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

    public String getUrlToImage() {
        return urlToImage;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
