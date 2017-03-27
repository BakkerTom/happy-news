package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
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
public class NewsAPI extends API<NewsSource> {

    @Value("${news.apikey}")
    private String API_KEY;

    @Value("${news.apiurl}")
    private String API_URL;


    public NewsAPI() {
        super();
    }

    /**
     * Get newsposts from the api and return a string
     *
     * @param args [0] = source, [1] = type
     * @return NewsSource object containing list of articles and source information
     */
    public NewsSource getRaw(String... args) {
        NewsSource newsSource = null;

        String url = API_URL +
                "?source=" + args[0] +
                "&sortBy=" + args[1] +
                "&apiKey=" + API_KEY;

        try {
            newsSource = restTemplate.getForObject(url, NewsSource.class);
            logger.info("received " + newsSource.getArticles().size() + " articles from " + args[0]);
        } catch (HttpClientErrorException ex) {
            logger.error("Bad Request", ex);
        }
        return newsSource;
    }
}
