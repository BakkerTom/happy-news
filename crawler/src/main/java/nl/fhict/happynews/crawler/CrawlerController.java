package nl.fhict.happynews.crawler;


import com.mongodb.MongoClient;
import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.crawler.models.newsapi.Source;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.crawler.repository.SourceRepository;
import nl.fhict.happynews.shared.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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

    @Autowired
    private SourceRepository sourceRepository;

    private Logger logger;


    public CrawlerController() {
        logger = LoggerFactory.getLogger(CrawlerController.class);
    }


    /**
     * Insert sources into database after server startup.
     */
    @PostConstruct
    public void insertSources() {
        List<Source> sources = new ArrayList<>();
        sources.add(new Source("the-next-web", "latest"));
        sources.add(new Source("associated-press", "latest"));
        sources.add(new Source("bbc-news", "top"));
        sources.add(new Source("bloomberg", "top"));
        sources.add(new Source("business-insider", "latest"));
        sources.add(new Source("buzzfeed", "latest"));
        sources.add(new Source("cnbc", "top"));
        sources.add(new Source("cnn", "top"));
        sources.add(new Source("daily-mail", "latest"));
        sources.add(new Source("entertainment-weekly", "top"));
        sources.add(new Source("espn", "top"));
        sources.add(new Source("financial-times", "latest"));
        for (Source s : sources) {
            try {
                sourceRepository.save(s);
                logger.info(s.getName() + " added as source.");
            } catch (DuplicateKeyException ex) {
                logger.info("Source already in Database", ex);
            }
        }

    }


    /**
     * Get the data from the newsApi.org website on a fixed rate specified in application.yml.
     */
    @Scheduled(fixedDelayString = "${crawler.delay}")
    public List<Post> getNewsPosts() {
        List<Source> sources = getSources();
        logger.info("Start getting posts from newsapi.org");
        List<Post> posts = new ArrayList<>();

        for (Source s : sources) {
            //retrieve news from the source
            NewsSource newsSource = newsRetriever.getNewsPerSource(s.getName(), s.getType());
            posts.addAll(convertToPost(newsSource));
        }
        logger.info("Received total of " + posts.size() + " articles");
        savePosts(posts);
        return posts;
    }


    /**
     * Get sources from database
     *
     * @return list of sources
     */
    public List<Source> getSources() {
        return sourceRepository.findAll();

    }

    /**
     * Converts the newssource object to database ready posts
     *
     * @param newsSource newssource containing news url and list of articles
     * @return list of database ready tests
     */
    public List<Post> convertToPost(NewsSource newsSource) {
        List<Post> posts = new ArrayList<>();
        if (newsSource != null) { //Check if request was successful
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
     *
     * @param posts list of posts to be added
     */
    public void savePosts(List<Post> posts) {
        for (Post p : posts) {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("url", ExampleMatcher.GenericPropertyMatchers.exact());

            if (!postRepository.exists(Example.of(p, matcher))) {
                logger.info("Inserting " + p.getUrl());
                try {
                    postRepository.save(p);
                } catch (DuplicateKeyException ex) {
                    logger.error("unexpected duplicate key error", ex);
                }
            } else {
                logger.info("Duplicate post. Not inserted " + p.getUrl());
            }
        }
    }


}
