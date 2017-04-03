package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.shared.Post;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 27/03/2017.
 */
@Service
public class TwitterCrawler extends Crawler<Status> {

    private Twitter twitter;
    private String hashTag = "#goodnews";
    private int amountOfTweets = 100;


    public TwitterCrawler() {
        logger = LoggerFactory.getLogger(TwitterCrawler.class);
        twitter = TwitterFactory.getSingleton();
    }

    @Override
    public void crawl() {
        List<Status> rawData = getRaw();
        List<Post> posts = rawToPosts(rawData);
        savePosts(posts);
    }

    /**
     * Gets tweets from twitter with hastag hashTag
     *
     * @return List<Status> list of tweets
     */
    @Override
    List<Status> getRaw() {
        logger.info("Start getting tweets");
        Query query = new Query(hashTag);
        query.count(amountOfTweets);
        List<Status> rawData = new ArrayList<>();
        try {
            QueryResult result = twitter.search(query);
            rawData.addAll(result.getTweets());
            logger.info("Received total of " + amountOfTweets + " tweets from twitter with hashtag #happy");
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
     * filters the tweets that are possibly sensitive
     * filters tweets that contain non ascii characters
     *
     * @param statuses raw statuses from twitter (method getRaw)
     * @return List of Post objects
     */
    private List<Post> rawToPosts(List<Status> statuses) {
        return statuses.stream()
                .filter(status -> !status.isPossiblySensitive())
                .filter(status -> status.getText().matches("\\A\\p{ASCII}*\\z"))
                .map(this::convertStatusToPost)
                .collect(Collectors.toList());
    }

    /**
     * Convert a tweet (status object) to a post object
     *
     * @param status raw tweet object
     * @return Post object
     */
    private Post convertStatusToPost(Status status) {
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

        newPost.setSource("Twitter");
        newPost.setSourceName("Twitter");
        return newPost;
    }
}
