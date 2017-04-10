package nl.fhict.happynews.crawler.model.twitterapi;

import java.util.ArrayList;
import java.util.List;

import twitter4j.*;

/**
 * Created by Sander on 04/04/2017.
 */
public class TweetBundle {
    private List<Status> tweets;
    private String hashTag;

    public TweetBundle(String hashTag) {
        this.hashTag = hashTag;
        tweets = new ArrayList<>();
    }

    public List<Status> getTweets() {
        return tweets;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void addTweets(List<Status> newTweets){
        tweets.addAll(newTweets);
    }
}
