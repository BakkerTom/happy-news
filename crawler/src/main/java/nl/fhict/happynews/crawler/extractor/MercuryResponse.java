package nl.fhict.happynews.crawler.extractor;

import java.util.Date;

public class MercuryResponse {

    private String title;
    private String content;
    private Date datePublished;
    private String leadImageUrl;
    private String dek;
    private String url;
    private String domain;
    private String excerpt;
    private int wordCount;
    private String direction;
    private int totalPages;
    private int renderedPages;
    private String nextPageUrl;

    public MercuryResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public String getLeadImageUrl() {
        return leadImageUrl;
    }

    public void setLeadImageUrl(String leadImageUrl) {
        this.leadImageUrl = leadImageUrl;
    }

    public String getDek() {
        return dek;
    }

    public void setDek(String dek) {
        this.dek = dek;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRenderedPages() {
        return renderedPages;
    }

    public void setRenderedPages(int renderedPages) {
        this.renderedPages = renderedPages;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}
