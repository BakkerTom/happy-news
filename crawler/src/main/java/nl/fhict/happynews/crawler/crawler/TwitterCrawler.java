package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.model.twitterapi.TweetBundle;
import nl.fhict.happynews.shared.Post;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sander on 27/03/2017.
 */
@Service
public class TwitterCrawler extends Crawler<TweetBundle> {

    private Twitter twitter;
    private String hashTags[];
    private String hashTag;
    private final static int AMOUNTOFTWEETS = 200;

    @Value("${crawler.twitter.enabled:true}")
    private boolean enabled;

    public TwitterCrawler() {
        logger = LoggerFactory.getLogger(TwitterCrawler.class);
        twitter = TwitterFactory.getSingleton();
        hashTag = "#happy";
        loadHashTags();
    }

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    /**
     * adds new positive tweets to the database
     * checks the different hashtags for suitable happy tweets
     */
    @Override
    public void crawl() {
        List<Post> positivePosts = new ArrayList<>();
        List<TweetBundle> tweetBundles = getRaw();
        for (TweetBundle bundle : tweetBundles) {
            positivePosts.addAll(rawToPosts(bundle));
        }
        logger.info("Filtered out " + (AMOUNTOFTWEETS * hashTags.length - positivePosts.size())
                + " out of " + AMOUNTOFTWEETS * hashTags.length + " tweets");
        logger.info("Saving " + positivePosts.size() + " tweets to the database");
        savePosts(positivePosts);
    }

    /**
     * Gets tweets from twitter with hastag hashTag
     *
     * @return List<Status> list of tweets
     */
    @Override
    List<TweetBundle> getRaw() {
        logger.info("Start getting tweets from twitter with hastag " + hashTag);
        List<TweetBundle> tweetBundles = new ArrayList();
        for (int i = 0; i < hashTags.length; i++) {
            hashTag = hashTags[i];
            Query query = new Query(hashTag);
            query.count(200);
            try {
                QueryResult result = twitter.search(query);
                TweetBundle rawTweets = new TweetBundle(hashTag);
                List<Status> rawData = result.getTweets();
                rawTweets.addTweets(rawData);
                tweetBundles.add(rawTweets);
                logger.info("Received total of " + AMOUNTOFTWEETS + " tweets from twitter with hashtag " + hashTag);
            } catch (TwitterException e) {
                logger.error("TwitterException: " + e.getErrorMessage());
            }
        }
        return tweetBundles;
    }

    /**
     * Method that converts the status objects to posts
     * filters the tweets that are possibly sensitive
     * filters tweets that contain non ascii characters
     * filters tweets that are older than 1 hour
     * filters tweets that are retweeted less than 10 times
     *
     * @param tweets raw statuses from twitter (method getRaw)
     * @return List of Post objects
     */
    public List<Post> rawToPosts(TweetBundle tweets) {
        Date d = new Date(System.currentTimeMillis() - 3600 * 1000);
        return tweets.getTweets().stream()
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

    private void loadHashTags() {
        String csvFile = "hashtags.csv";
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                hashTags = line.split(cvsSplitBy);
            }
        } catch (IOException e) {
            logger.error("IOException loading hastags: " + e.getMessage());
        }
    }
}
