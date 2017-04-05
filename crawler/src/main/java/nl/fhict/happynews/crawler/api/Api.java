package nl.fhict.happynews.crawler.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Tobi on 27-Mar-17.
 */
public abstract class Api<T> {

    protected RestTemplate restTemplate;
    protected Logger logger;

    public Api() {
        restTemplate = new RestTemplate();
        logger = LoggerFactory.getLogger(NewsApi.class);
    }

    abstract T getRaw(String... args);
}
