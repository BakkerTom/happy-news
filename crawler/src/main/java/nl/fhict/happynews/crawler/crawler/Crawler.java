package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.CrawlerController;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.crawler.repository.SourceRepository;
import nl.fhict.happynews.shared.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tobi on 27-Mar-17.
 * Classes extending crawler class also need the @Service annotation
 *
 */
@Service
public abstract class Crawler<T> {

    @Autowired
    private PostRepository postRepository;

    protected Logger logger;

    public Crawler() {
        logger = LoggerFactory.getLogger(Crawler.class);
    }

    abstract void crawl();

    /**
     * retrieves raw info from the source.
     * @return A list of raw info objects.
     */
    abstract List<T> getRaw();

    /**
     * Converts raw info objects to database ready objects.
     * @param entity The raw info object.
     * @return A list of database ready posts.
     */
    abstract List<Post> rawToPosts(T entity);

    /**
     * Saves a post to the database.
     * @param post The post to save.
     */
    protected void savePost(Post post){
        try {
            postRepository.save(post);
        } catch (DuplicateKeyException ex) {
            logger.error("Unexpected duplicate key error, post not inserted "+ post.getUrl());
        }
    }

    /**
     * Saves a collection of posts.
     * @param posts A list of posts.
     */
    protected void savePosts(List<Post> posts){
        for (Post p : posts) {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withMatcher("url", ExampleMatcher.GenericPropertyMatchers.exact());
            if (!postRepository.exists(Example.of(p, matcher))) {
                logger.info("Inserting " + p.getUrl());
                savePost(p);
            } else {
                logger.info("Duplicate post. Not inserted " + p.getUrl());
            }
        }
    }
}
