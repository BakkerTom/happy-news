package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.models.instagramapi.InstagramEnvelope;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class InstagramAPI extends API<InstagramEnvelope> {
    @Value("${instagram.apikey}")
    private String API_KEY;

    @Value("${instagram.apiurl}")
    private String API_URL;


    public InstagramAPI() {
        super();
    }

    public InstagramEnvelope getRaw(String... args) {
        //TODO
        return null;
    }

}
