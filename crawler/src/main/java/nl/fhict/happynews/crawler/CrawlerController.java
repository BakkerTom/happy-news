package nl.fhict.happynews.crawler;


import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Service that runs the gathering and exporting of the data
 * Created by daan_ on 6-3-2017.
 */
@Service
public class CrawlerController {
    
    @Autowired
    private NewsRetriever newsRetriever;

    /**
     * Get the data from the newsApi.org website on a fixed rate specified in application.yml.
     */
    @Scheduled(fixedDelayString = "${crawler.delay}")
    public List<Post> getNewsPosts() {
        String[] sources = {"the-next-web", "buzzfeed", "the-telegraph", "time"};

        List<Post> posts = new ArrayList<>();
        //Loop through all available sources
        for (String sourceUrl : sources) {
            //retrieve news from the source
            NewsSource ns = newsRetriever.getNewsPerSource(sourceUrl);
            for (Article a : ns.getArticles()) {
                //Create database ready objects
                Post p = new Post(ns.getSource(),a.getAuthor(),a.getTitle(),a.getDescription(),a.getUrl(),a.getUrlToImage(),a.getPublishedAt());
                posts.add(p);
            }
        }
        return posts;
    }


}
