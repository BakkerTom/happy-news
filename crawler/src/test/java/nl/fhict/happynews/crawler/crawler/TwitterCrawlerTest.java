package nl.fhict.happynews.crawler.crawler;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.shared.Post;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import twitter4j.*;
import com.mongodb.Mongo;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Sander on 03/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TwitterCrawlerTest {

    @Autowired
    private TwitterCrawler twitterCrawler;

    @BeforeClass
    public static void startDatabase() throws IOException {
        mongo();
    }

    @Test
    public void crawl() throws Exception {
        twitterCrawler.crawl();
    }

    @Test
    public void getRaw() throws Exception {
        List<Status> rawData = twitterCrawler.getRaw();
        assertTrue(rawData.size() > 0);
    }

    @Test
    public void rawToPosts() throws Exception {
        List<Status> rawData = twitterCrawler.getRaw();
        List<Post> posts = twitterCrawler.rawToPosts(rawData);
        assertTrue(rawData.size() > 0 && posts.size() > 0);
    }

    @Bean(destroyMethod = "close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    }
}