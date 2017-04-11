package nl.fhict.happynews.crawler.api;

import nl.fhict.happynews.crawler.model.quoteapi.QuoteEnvelope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Created by Tobi on 10-Apr-17.
 */
@Service
public class QuoteApi extends Api<QuoteEnvelope> {

    public QuoteApi() {
        super();
    }

    /**
     * Get the raw quote JSON.
     *
     * @param args [0] is a category
     * @return QuoteEnvelope
     */
    @Override
    public QuoteEnvelope getRaw(String... args) {
        String url = "http://quotes.rest/qod.json?category=" + args[0];
        QuoteEnvelope result = null;
        try {
            result = restTemplate.getForObject(url, QuoteEnvelope.class);
            logger.info("received daily quote from category " + args[0] + ".");
        } catch (HttpClientErrorException ex) {
            logger.error("Bad Request", ex);
        }
        return result;
    }
}
