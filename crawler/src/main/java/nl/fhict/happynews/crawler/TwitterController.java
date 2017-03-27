package nl.fhict.happynews.crawler;

import javafx.geometry.Pos;
import nl.fhict.happynews.shared.Post;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sander on 27/03/2017.
 */
public class TwitterController {

    private Twitter twitter;
    private org.slf4j.Logger logger;

    public TwitterController() {
        logger = LoggerFactory.getLogger(TwitterController.class);
        twitter = TwitterFactory.getSingleton();
        List<Post> posts = getTweets();
    }

    public List<Post> getTweets() {
        logger.info("Start getting tweets");
        List<Post> posts = new ArrayList<>();
        Query query = new Query("#happynews");
        query.count(10); //max amount of tweets
        try {
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
            }

        } catch (TwitterException e) {
            logger.error("TwitterException: " + e.getErrorMessage());
        }
        return posts;
    }

    public Post convertToPost(Status status) {
        Post newPost = new Post();
        return newPost;
    }
}
