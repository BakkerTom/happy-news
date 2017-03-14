package nl.fhict.happynews.crawler;

import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Connects to the api and retrieves a json file of news.
 * Created by daan_ on 6-3-2017.
 */
@Component
public class NewsRetriever {

    @Value("${crawler.apikey}")
    private String API_KEY;

    @Value("${crawler.apiurl}")
    private String API_URL;


    private RestTemplate restTemplate;
    private Logger logger;

    public NewsRetriever() {
        restTemplate = new RestTemplate();
        logger = LoggerFactory.getLogger(NewsRetriever.class);
    }

    /**
     * Get newsposts from the api and return a string
     *
     * @param source The newsapi.org source name
     * @param type   Type of requeest(latest or top)
     * @return NewsSource object containing list of articles and source information
     */
    public NewsSource getNewsPerSource(String source, String type) {
        NewsSource newsSource = null;

        String url = API_URL +
                "?source=" + source +
                "&sortBy=" + type +
                "&apiKey=" + API_KEY;

        try {
            newsSource = restTemplate.getForObject(url, NewsSource.class);
            logger.info("received " + newsSource.getArticles().size() + " articles from " + source);
        } catch (HttpClientErrorException ex) {
            logger.error("Bad Request", ex);
        }
        return newsSource;
    }
}
