package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.model.instagramapi.InstagramEnvelope;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class InstagramApi extends Api<InstagramEnvelope> {
    @Value("${crawler.instagram.apikey}")
    private String API_KEY;

    @Value("${crawler.instagram.apiurl}")
    private String API_URL;


    public InstagramApi() {
        super();
    }

    public InstagramEnvelope getRaw(String... args) {
        //TODO
        return null;
    }

}
