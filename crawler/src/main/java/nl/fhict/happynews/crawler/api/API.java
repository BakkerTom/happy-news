package nl.fhict.happynews.crawler.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Tobi on 27-Mar-17.
 */
@Service
public abstract class API<T> {

    protected RestTemplate restTemplate;
    protected Logger logger;

    public API() {
        restTemplate = new RestTemplate();
        logger = LoggerFactory.getLogger(NewsAPI.class);
    }

    abstract T getRaw(String... args);
}
