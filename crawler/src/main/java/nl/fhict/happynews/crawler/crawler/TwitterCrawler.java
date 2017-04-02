package nl.fhict.happynews.crawler.crawler;

import javafx.geometry.Pos;
import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import nl.fhict.happynews.shared.Post;
import org.slf4j.LoggerFactory;
import twitter4j.*;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 27/03/2017.
 */
public class TwitterCrawler extends Crawler<Status> {

    private Twitter twitter;
    private String API_KEY;
    private org.slf4j.Logger logger;

    public TwitterCrawler() {
        logger = LoggerFactory.getLogger(TwitterCrawler.class);
        twitter = TwitterFactory.getSingleton();
        crawl();
    }

    @Override
    void crawl() {
        List<Status> rawData = getRaw();
        List<Post> posts = rawToPosts(rawData);
        //savePosts(posts);
    }

    /**
     * Gets raw twitter data with
     *
     * @return
     */
    @Override
    List<Status> getRaw() {
        logger.info("Start getting tweets");
        Query query = new Query("#happy");
        query.count(100); //max amount of tweets
        List<Status> rawData = new ArrayList<>();
        try {
            QueryResult result = twitter.search(query);
            rawData.addAll(result.getTweets());
            System.out.println("received 10 articles from twitter with hastag #happy");
        } catch (TwitterException e) {
            logger.error("TwitterException: " + e.getErrorMessage());
        }
        return rawData;
    }

    @Override
    List<Post> rawToPosts(Status entity) {
        return null;
    }

    /**
     * Method that converts the status objects to posts
     *
     * @param statuses raw statuses from twitter (method getRaw)
     * @return List of Post objects
     */
    List<Post> rawToPosts(List<Status> statuses) {
        return statuses.stream().map(this::convertStatusToPost).collect(Collectors.toList());
    }


    public Post convertStatusToPost(Status status) {
        Post newPost = new Post();
        newPost.setAuthor(status.getUser().getScreenName());
        newPost.setType(Post.Type.TWEET);
        newPost.setContentText(status.getText());
        newPost.setIndexedAt(new Date());
        newPost.setPublishedAt(status.getCreatedAt());

        //retrieve link URLS. No list of links in Post yet
        URLEntity urls[] = status.getURLEntities();
        List<String> links = new ArrayList<>();
        for (URLEntity url1 : urls) {
            links.add(url1.getURL());
        }

        //retrieve image urls
        List<String> imageLinks = new ArrayList<>();
        MediaEntity[] media = status.getMediaEntities();
        for (MediaEntity m : media) {
            imageLinks.add(m.getMediaURL());
        }
        newPost.setImageUrls(imageLinks);

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

        if(status.isPossiblySensitive()){

        }

        return newPost;
    }
}
