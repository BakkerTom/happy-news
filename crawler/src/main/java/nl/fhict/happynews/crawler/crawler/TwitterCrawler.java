package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.model.twitterapi.TweetBundle;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private String[] hashTags;
    private String hashTag;
    private static final int AMOUNT_OF_TWEETS = 200;

    @Value("${crawler.twitter.enabled:true}")
    private boolean enabled;
    @Value("${crawler.twitter.consumerkey}")
    private String consumerKey;
    @Value("${crawler.twitter.consumersecret}")
    private String consumerSecret;
    @Value("${crawler.twitter.accestoken}")
    private String accesToken;
    @Value("${crawler.twitter.accestokensecret}")
    private String accesTokenSecret;
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Create a new crawler for tweets.
     */
    public TwitterCrawler() {
        hashTag = "#happy";
    }

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    /**
     * Adds new positive tweets to the database.
     * Checks the different hashtags for suitable happy tweets.
     */
    @Override
    public void crawl() {
        if (hashTags == null) {
            loadHashTags();
        }
        if (twitter == null) {
            configureTwitterAuthentication();
        }
        List<Post> positivePosts = new ArrayList<>();
        List<TweetBundle> tweetBundles = getRaw();
        for (TweetBundle bundle : tweetBundles) {
            positivePosts.addAll(rawToPosts(bundle));
        }
        logger.info("Filtered out " + (AMOUNT_OF_TWEETS * hashTags.length - positivePosts.size())
            + " out of " + AMOUNT_OF_TWEETS * hashTags.length + " tweets");
        logger.info("Saving " + positivePosts.size() + " tweets to the database");
        savePosts(positivePosts);
    }

    /**
     * Gets tweets from Twitter with hashtag {@link #hashTag}.
     *
     * @return List of tweets
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
                logger.info("Received total of " + AMOUNT_OF_TWEETS + " tweets from twitter with hashtag " + hashTag);
            } catch (TwitterException e) {
                logger.error("TwitterException: " + e.getErrorMessage());
            }
        }
        return tweetBundles;
    }

    /**
     * Converts the status objects to posts.
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
            .filter(status -> !status.isPossiblySensitive()
                && status.getText().matches("\\A\\p{ASCII}*\\z")
                && status.getCreatedAt().after(d)
                && status.getRetweetCount() >= 10)
            .map(this::convertStatusToPost)
            .collect(Collectors.toList());
    }

    /**
     * Convert a tweet (status object) to a post object.
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
        /*
        URLEntity urls[] = status.getURLEntities();
        List<String> links = new ArrayList<>();
        for (URLEntity url1 : urls) {
            links.add(url1.getURL());
        }
        */

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
        Resource resource = applicationContext.getResource("classpath:/" + csvFile);
        String line = "";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            while ((line = br.readLine()) != null) {
                hashTags = line.split(cvsSplitBy);
            }
        } catch (IOException e) {
            logger.error("IOException loading hastags: " + e.getMessage());
        }
    }

    /**
     * Builds the configuration for TwitterFactory.
     */
    protected void configureTwitterAuthentication() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accesToken)
            .setOAuthAccessTokenSecret(accesTokenSecret);
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter = twitterFactory.getInstance();
    }
}
