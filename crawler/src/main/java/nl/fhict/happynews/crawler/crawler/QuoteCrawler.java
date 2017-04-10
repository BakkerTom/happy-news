package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.api.QuoteAPI;
import nl.fhict.happynews.crawler.model.quoteapi.Quote;
import nl.fhict.happynews.crawler.model.quoteapi.QuoteEnvelope;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Tobi on 10-Apr-17.
 */
@Service
public class QuoteCrawler extends Crawler<QuoteEnvelope> {

    private static final String[] categories = {"inspire", "management", "sports", "life", "funny", "art", "students"};
    private static int catIndex;


    @Autowired
    private QuoteAPI quoteAPI;

    @Value("${crawler.quotes.enabled:true}")
    private boolean enabled;

    public QuoteCrawler(){
        super();
        catIndex = 0;
    }

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    @Override
    void crawl() {
        List<QuoteEnvelope> envelopes = getRaw();
        List<Post> posts = new ArrayList<>();
        for (QuoteEnvelope envelope : envelopes) {
            posts.addAll(rawToPosts(envelope));
        }
        savePosts(posts);
    }

    @Override
    List<QuoteEnvelope> getRaw() {
        List<QuoteEnvelope> result = new ArrayList<>();
        result.add(quoteAPI.getRaw(categories[catIndex]));
        if(catIndex++ > categories.length){
            catIndex = 0;
        }
        return result;
    }

    @Override
    List<Post> rawToPosts(QuoteEnvelope entity) {
        List<Post> result = new ArrayList<>();
        if(entity == null || entity.getContents() == null){
            logger.info("No quotes/contents found.");
            return result;
        }
        for(Quote quote : entity.getContents().getQuotes()) {
            Post toAdd = new Post();
            toAdd.setTitle(quote.getTitle());
            toAdd.setUrl(quote.getPermalink());
            toAdd.setType(Post.Type.QUOTE);
            toAdd.setContentText(quote.getQuote());
            toAdd.setTags(Arrays.asList(quote.getTags()));
            toAdd.setAuthor(quote.getAuthor());
            toAdd.setIndexedAt(new Date());
            result.add(toAdd);
        }
        return result;
    }

    @Scheduled(fixedDelayString = "${crawler.quotes.delay}")
    @Override
    public void run() {
        super.run();
    }
}
