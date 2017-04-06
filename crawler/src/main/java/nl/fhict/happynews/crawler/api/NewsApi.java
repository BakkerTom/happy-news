package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Connects to the api and retrieves a json file of news.
 * Created by daan_ on 6-3-2017.
 */
@Component
public class NewsApi extends Api<NewsSource> {

    @Value("${crawler.news.apikey}")
    private String apiKey;

    @Value("${crawler.news.apiurl}")
    private String apiUrl;


    public NewsApi() {
        super();
    }

    /**
     * Get newsposts from the api.
     *
     * @param args [0] = source, [1] = type
     * @return NewsSource object containing list of articles and source information
     */
    public NewsSource getRaw(String... args) {
        NewsSource newsSource = null;

        String url = apiUrl
            + "?source=" + args[0]
            + "&sortBy=" + args[1]
            + "&apiKey=" + apiKey;

        try {
            newsSource = restTemplate.getForObject(url, NewsSource.class);
            logger.info("received " + newsSource.getArticles().size() + " articles from " + args[0]);
        } catch (HttpClientErrorException ex) {
            logger.error("Bad Request", ex);
        }
        return newsSource;
    }
}
