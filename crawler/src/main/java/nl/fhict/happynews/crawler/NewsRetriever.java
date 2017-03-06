package nl.fhict.happynews.crawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import org.h2.util.New;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/** Connects to the api and retrieves a json file of news.
 * Created by daan_ on 6-3-2017.
 */
@Component
public class NewsRetriever {

    @Value("${crawler.apikey}")
    private String API_KEY;

    /**
     * Get newsposts from the api and return a string
     * @param source The newsapi.org source name
     */
    public NewsSource getNewsPerSource(String source){
        String url = "https://newsapi.org/v1/articles" +
                "?source=" + source +
                "&sortBy=latest" +
                "&apiKey=" +API_KEY;
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplate();
        NewsSource ns =  restTemplate.getForObject(url, NewsSource.class);
        return ns;
    }
}
