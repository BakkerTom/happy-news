package nl.fhict.happynews.crawler.model.newsapi;

import java.util.List;

/** Class that conforms to the newsapi.org format for easy json parsing
 * Created by daan_ on 6-3-2017.
 */
public class NewsSource {

    private String status;
    private String source;
    private String sourceName;
    private String sortBy;
    private List<Article> articles;

    public NewsSource() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
