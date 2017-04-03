package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.shared.Post;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
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
    private String hashTags[] = {"#happy", "#positivethinking", "#positivemind", "#positivity", "#Happiness", "#success"};
    private String hashTag = "#happy";
    private int amountOfTweets = 200;

    public TwitterCrawler() {
        logger = LoggerFactory.getLogger(TwitterCrawler.class);
        twitter = TwitterFactory.getSingleton();
    }

    /**
     * adds new positive tweets to the database
     * checks the different hashtags for suitable happy tweets
     *
     */
    @Override
    public void crawl() {
        List<Post> positivePosts = new ArrayList<>();
        int i = 0;
        while (positivePosts.size() <= 25 && i < hashTags.length) {
            hashTag = hashTags[i];
            List<Status> rawData = getRaw();
            List<Post> posts = rawToPosts(rawData);
            logger.info("Filtered " + (amountOfTweets - posts.size()) + " tweets from the " + amountOfTweets + " with " + hashTag + "");
            positivePosts.addAll(posts);
            i++;
        }
        logger.info("Saving " + positivePosts.size() + " tweets to the database");
        savePosts(positivePosts);
    }

    /**
     * Gets tweets from twitter with hastag hashTag
     *
     * @return List<Status> list of tweets
     */
    @Override
    List<Status> getRaw() {
        logger.info("Start getting tweets from twitter with hastag " + hashTag);
        Query query = new Query(hashTag);
        query.count(200);
        List<Status> rawData = new ArrayList<>();
        try {
            QueryResult result = twitter.search(query);
            rawData.addAll(result.getTweets());
            logger.info("Received total of " + amountOfTweets + " tweets from twitter with hashtag " + hashTag);
        } catch (TwitterException e) {
            logger.error("TwitterException: " + e.getErrorMessage());
        }
        return rawData;
    }

    /**
     * Not implemented method, see method rawToPosts(List<Status> statuses)
     * @param entity The raw info object.
     * @return
     */
    @Override
    List<Post> rawToPosts(Status entity) {
        throw new NotImplementedException();
    }

    /**
     * Method that converts the status objects to posts
     * filters the tweets that are possibly sensitive
     * filters tweets that contain non ascii characters
     * filters tweets that are older than 1 hour
     * filters tweets that are retweeted less than 10 times
     *
     * @param statuses raw statuses from twitter (method getRaw)
     * @return List of Post objects
     */
    public List<Post> rawToPosts(List<Status> statuses) {
        Date d = new Date(System.currentTimeMillis() - 3600 * 1000);
        return statuses.stream()
                .filter(status -> !status.isPossiblySensitive())
                .filter(status -> status.getText().matches("\\A\\p{ASCII}*\\z"))
                .filter(status -> status.getCreatedAt().after(d))
                .filter(status -> status.getRetweetCount() >= 10)
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
