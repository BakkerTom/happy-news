package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Tobi on 27-Mar-17.
 * Classes extending crawler class also need the @Service annotation
 */
@Service
public abstract class Crawler<T> {

    @Autowired
    private PostRepository postRepository;

    protected Logger logger;

    public Crawler() {
        logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * @return Whether this crawler automatically craws.
     */
    protected abstract boolean isEnabled();

    abstract void crawl();

    /**
     * retrieves raw info from the source.
     *
     * @return A list of raw info objects.
     */
    abstract List<T> getRaw();

    /**
     * Converts raw info objects to database ready objects.
     *
     * @param entity The raw info object.
     * @return A list of database ready posts.
     */
    abstract List<Post> rawToPosts(T entity);

    /**
     * Saves a post to the database.
     *
     * @param post The post to save.
     */
    protected void savePost(Post post) {
        try {
            postRepository.save(post);
        } catch (DuplicateKeyException ex) {
            logger.error("Unexpected duplicate key error, post not inserted " + post.getUrl());
        }
    }

    /**
     * Saves a collection of posts.
     *
     * @param posts A list of posts.
     */
    protected void savePosts(List<Post> posts) {
        int inserted = 0;

        for (Post p : posts) {
            ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("url", ExampleMatcher.GenericPropertyMatchers.exact());
            if (!postRepository.exists(Example.of(p, matcher))) {
                logger.info("Inserting " + p.getUrl());
                savePost(p);

                inserted++;
            } else {
                logger.info("Duplicate post. Not inserted " + p.getUrl());
            }
        }

        logger.info("Inserted " + inserted + " items");
    }

    /**
     * Periodically run a crawl. The crawl will only be executed if {@link #isEnabled()} is <code>true</code>.
     */
    @Scheduled(fixedDelayString = "${crawler.delay}")
    public void run() {
        if (!isEnabled()) {
            logger.info("Crawler disabled, ignoring crawl request");
            return;
        } else {
            logger.info("Starting crawl");
        }

        crawl();

        logger.info("Crawl completed");
    }
}
