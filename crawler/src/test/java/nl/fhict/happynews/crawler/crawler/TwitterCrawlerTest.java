package nl.fhict.happynews.crawler.crawler;

import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.crawler.model.twitterapi.TweetBundle;
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
import java.lang.reflect.Field;
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
        setHashTags();
        twitterCrawler.crawl();
    }

    @Test
    public void getRaw() throws Exception {
        setHashTags();
        List<TweetBundle> rawData = twitterCrawler.getRaw();
        assertTrue(rawData.size() > 0);
        assertTrue(!rawData.get(0).getHashTag().equals(""));
        assertTrue(rawData.get(0).getTweets().size() != 0);
    }

    @Test
    public void rawToPosts() throws Exception {
        setHashTags();
        List<TweetBundle> rawData = twitterCrawler.getRaw();
        List<Post> posts1 = twitterCrawler.rawToPosts(rawData.get(0));
        List<Post> posts2 = twitterCrawler.rawToPosts(rawData.get(1));
        List<Post> posts3 = twitterCrawler.rawToPosts(rawData.get(2));
        List<Post> posts4 = twitterCrawler.rawToPosts(rawData.get(3));
        List<Post> posts5 = twitterCrawler.rawToPosts(rawData.get(4));
        assertTrue(rawData.size() > 0 && (posts1.size() > 0 || posts2.size() > 0) || posts3.size() > 0 || posts4.size() > 0 || posts5.size() > 0);
    }

    @Bean(destroyMethod = "close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    }

    /**
     * sets the hashtags in twittercontroller
     * csv file can not be loaded in test environment
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void setHashTags() throws NoSuchFieldException, IllegalAccessException {
        Field reader = TwitterCrawler.class.getDeclaredField("hashTags");
        reader.setAccessible(true);
        String hashTags[] = {"#happy", "#positivethinking", "#positivemind", "#positivity", "#Happiness", "#success"};
        reader.set(twitterCrawler, hashTags);
    }
}