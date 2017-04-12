package nl.fhict.happynews.crawler.crawler;

import com.mongodb.Mongo;
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
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.User;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        twitterCrawler.configureTwitterAuthentication();
        setHashTags();
        List<TweetBundle> rawData = twitterCrawler.getRaw();
        assertTrue(rawData.size() > 0);
        assertTrue(!rawData.get(0).getHashTag().equals(""));
        assertTrue(rawData.get(0).getTweets().size() != 0);
    }

    @Test
    public void rawToPosts() throws Exception {
        Status mockStatus = mock(Status.class);
        when(mockStatus.getText()).thenReturn("mocktext");
        User user = mock(User.class);
        when(user.getScreenName()).thenReturn("testuser");
        when(mockStatus.getUser()).thenReturn(user);
        when(mockStatus.getCreatedAt()).thenReturn(new Date());
        when(mockStatus.getId()).thenReturn((long) 5412);
        when(mockStatus.getRetweetCount()).thenReturn(100);

        HashtagEntity hashtag = mock(HashtagEntity.class);
        when(hashtag.getText()).thenReturn("hastak");
        HashtagEntity[] entities = new HashtagEntity[]{hashtag};
        when(mockStatus.getHashtagEntities()).thenReturn(entities);

        ArrayList<Status> rawTweets = new ArrayList<>();
        rawTweets.add(mockStatus);

        TweetBundle tweetBundle = new TweetBundle("hastak");
        tweetBundle.addTweets(rawTweets);
        List<Post> posts = twitterCrawler.rawToPosts(tweetBundle);

        assertTrue(posts.size() > 0);
    }

    /**
     * @return A new in memory MongoDB.
     */
    @Bean(destroyMethod = "close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
            .version("2.4.5")
            .bindIp("127.0.0.1")
            .port(12345)
            .build();
    }

    /**
     * Sets the hashtags in twittercontroller.
     * csv file can not be loaded in test environment
     */
    public void setHashTags() throws NoSuchFieldException, IllegalAccessException {
        Field reader = TwitterCrawler.class.getDeclaredField("hashTags");
        reader.setAccessible(true);
        String[] hashTags = {"#happy", "#positivethinking", "#positivemind", "#positivity", "#Happiness", "#success"};
        reader.set(twitterCrawler, hashTags);
    }
}
