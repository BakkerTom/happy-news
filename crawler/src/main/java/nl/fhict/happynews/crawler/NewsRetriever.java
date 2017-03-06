package nl.fhict.happynews.crawler;

import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/** Connects to the api and retrieves a json file of news.
 * Created by daan_ on 6-3-2017.
 */
@Component
public class NewsRetriever {

    @Value("${crawler.apikey}")
    private String API_KEY;

    @Value("${crawler.apiurl}")
    private String API_URL;

    /**
     * Get newsposts from the api and return a string
     * @param source The newsapi.org source name
     */
    public NewsSource getNewsPerSource(String source, String type){
        String url = API_URL +
                "source=" + source +
                "&sortBy=" + type +
                "&apiKey=" +API_KEY;
        System.out.println(url);

        NewsSource ns = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ns = restTemplate.getForObject(url, NewsSource.class);
        }
        catch(HttpClientErrorException ex) {
            Logger logger = LoggerFactory.getLogger(NewsRetriever.class);
            logger.error("Bad Request", ex);
        }
        return ns;
    }
}
