package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.model.instagramapi.InstagramEnvelope;
import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Tobi on 27-Mar-17.
 */
@Service
public class InstagramAPI extends API<InstagramEnvelope> {
    @Value("${crawler.instagram.apikey}")
    private String API_KEY;

    @Value("${crawler.instagram.apiurl}")
    private String API_URL;


    public InstagramAPI() {
        super();
    }

    /**
     *
     * @param args [0] = tag
     * @return
     */
    public InstagramEnvelope getRaw(String... args) {
        InstagramEnvelope result = null;

        String url = API_URL +
                "tags/" + args[0] +
                "/media/recent?access_token=" + API_KEY;
        try{
            result = restTemplate.getForObject(url, InstagramEnvelope.class);
            logger.info("received " + result.data.size() + " posts with tag " + args[0]);
        }catch(Exception e){
            logger.info("Could not receive posts. Error: " + e.getMessage());
        }


        return result;
    }

}
