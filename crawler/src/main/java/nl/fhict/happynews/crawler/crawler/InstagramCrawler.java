package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.api.InstagramAPI;
import nl.fhict.happynews.crawler.models.instagramapi.InstagramEnvelope;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class InstagramCrawler extends Crawler<InstagramEnvelope> {

    @Autowired
    private InstagramAPI InstagramAPI;

    @Override
    void crawl() {
        //TODO
    }

    @Override
    List<InstagramEnvelope> getRaw() {
        logger.info("Start getting posts from instagram api.");
        List<InstagramEnvelope> envelopes = new ArrayList<>();
        envelopes = null;
        logger.info("Received total of " + envelopes.size() + " articles");
        return envelopes;
    }

    @Override
    List<Post> rawToPosts(InstagramEnvelope entity) {
        //TODO
        return null;
    }
}
