package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.shared.Post;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.mongodb.Mongo;

import static nl.fhict.happynews.crawler.CrawlerControllerTest.mongo;
import static org.junit.Assert.*;

/**
 * Created by Sander on 03/04/2017.
 */
@RunWith(SpringRunner.class)
public class TwitterCrawlerTest {

    private static TwitterCrawler tc;

    @BeforeClass
    public static void startDatabase() throws IOException {
        tc = new TwitterCrawler();
        mongo();
    }

    @Test
    public void crawl() throws Exception {
        throw new NotImplementedException();
    }

    @Test
    public void getRaw() throws Exception {
        List<Status> rawData = tc.getRaw();
        assertTrue(rawData.size() > 0);
    }

    @Test
    public void rawToPosts() throws Exception {
        List<Status> rawData = tc.getRaw();
        List<Post> posts = tc.rawToPosts(rawData);
        assertTrue(rawData.size() > 0 && posts.size() > 0);
    }

}