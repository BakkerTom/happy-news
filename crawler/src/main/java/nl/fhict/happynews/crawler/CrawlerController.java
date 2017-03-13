package nl.fhict.happynews.crawler;


import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Service that runs the gathering and exporting of the data
 * Created by daan_ on 6-3-2017.
 */
@Service
public class CrawlerController {

    @Autowired
    private NewsRetriever newsRetriever;

    @Autowired
    private PostRepository postRepository;

    private Logger logger;


    public CrawlerController() {
        logger = LoggerFactory.getLogger(CrawlerController.class);
    }

    /**
     * Get the data from the newsApi.org website on a fixed rate specified in application.yml.
     */
    @Scheduled(fixedDelayString = "${crawler.delay}")
    public List<Post> getNewsPosts() {
        String[] sources = {"the-next-web", "associated-press", "buzzfeed", "the-telegraph", "time","business-insider","business-insider-uk","daily-mail","engadget"};
        logger.info("Start getting posts from newsapi.org");
        List<Post> posts = new ArrayList<>();

        for (String sourceUrl : sources) {
            //retrieve news from the source
            NewsSource newsSource = newsRetriever.getNewsPerSource(sourceUrl, "latest");
            posts = convertToPost(newsSource);
        }
        logger.info("Received total of " + posts.size() + " articles");
        savePosts(posts);
        return posts;
    }


    /**
     * Converts the newssource object to database ready posts
     * @param newsSource newssource containing news url and list of articles
     * @return list of database ready tests
     */
    public List<Post> convertToPost(NewsSource newsSource) {
        List<Post> posts = new ArrayList<>();
        if(newsSource != null) { //Check if request was successful
            for (Article a : newsSource.getArticles()) {
                //Create database ready objects
                Post p = new Post(newsSource.getSource(), a.getAuthor(), a.getTitle(), a.getDescription(), a.getUrl(), a.getUrlToImage(), a.getPublishedAt());
                posts.add(p);
            }
        }
        return posts;
    }

    /**
     * Save the posts to the database
     * @param posts list of posts to be added
     */
    private void savePosts(List<Post> posts){
        for(Post p : posts){
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("url", ExampleMatcher.GenericPropertyMatchers.exact());

            if(!postRepository.exists(Example.of(p, matcher))) {
                logger.info("Inserting " + p.getUrl());
                postRepository.save(p);
            }
            else{
                logger.info("Duplicate post. Not inserted "+p.getUrl());
            }
        }
    }


}
