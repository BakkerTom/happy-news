package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import nl.fhict.happynews.shared.Post;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sander on 27/03/2017.
 */
public class TwitterCrawler extends Crawler<NewsSource> {

    private Twitter twitter;
    private org.slf4j.Logger logger;

    public TwitterCrawler() {
        logger = LoggerFactory.getLogger(TwitterCrawler.class);
        twitter = TwitterFactory.getSingleton();
        List<Post> posts = getTweets();
    }

    @Override
    void crawl() {
        //TODO
    }

    @Override
    List<NewsSource> getRaw() {
        //TODO
        return null;
    }

    @Override
    List<Post> rawToPosts(NewsSource entity) {
        return null;
    }

    public List<Post> getTweets() {
        logger.info("Start getting tweets");
        List<Post> posts = new ArrayList<>();
        Query query = new Query("#happy");
        query.count(10); //max amount of tweets
        try {
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                posts.add(convertToPost(status));
            }

        } catch (TwitterException e) {
            logger.error("TwitterException: " + e.getErrorMessage());
        }
        return posts;
    }

    public Post convertToPost(Status status) {
        Post newPost = new Post();

        newPost.setAuthor(status.getUser().getScreenName());
        newPost.setType(Post.Type.TWEET);
        newPost.setContentText(status.getText());
        newPost.setIndexedAt(new Date());
        newPost.setPublishedAt(status.getCreatedAt());

        //retrieve URLS
        URLEntity urls[] = status.getURLEntities();
        List<String> imageURLS = new ArrayList<>();
        for (URLEntity url1 : urls) {
            imageURLS.add(url1.getURL());
        }
        newPost.setImageUrls(imageURLS);

        //Retrieve HashTags
        HashtagEntity[] hashtags = status.getHashtagEntities();
        List<String> hashtagList = new ArrayList<>();
        for (HashtagEntity hashtag : hashtags) {
            hashtagList.add(hashtag.getText());
        }
        newPost.setTags(hashtagList);

        String url = "https://twitter.com/" + status.getUser().getScreenName()
                + "/status/" + status.getId();
        newPost.setUrl(url);

        newPost.setSource(status.getSource());
        newPost.setSourceName(status.getSource());

        return newPost;
    }
}
