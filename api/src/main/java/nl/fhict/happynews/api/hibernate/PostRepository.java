package nl.fhict.happynews.api.hibernate;

import nl.fhict.happynews.shared.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Tobi on 06-Mar-17.
 */
public interface PostRepository extends CrudRepository<Post, String> {

    /**
     * Retrieves all Posts.
     * @return A collection of posts.
     */
    Collection<Post> findAll();

    /**
     * Retrieves all Posts, ordered by publish date.
     * @return A collection of posts.
     */
    Collection<Post> findAllByOrderByPublishedAtDesc();

    /**
     * Retrieves a Post by it's UUID.
     * @param uuid The UUID.
     * @return The Post.
     */
    Post findByUuid(String uuid);

    /**
     * Retrieves all Posts published after the date parameter.
     * @param date The date after which to retrieve posts from.
     * @return A collection of posts.
     */
    Collection<Post> findByPublishedAtAfter(Date date);

    /**
     * Retrieves all Posts published after the date parameter, ordered by date (Descending).
     * @param date The date after which to retrieve posts from.
     * @return A collection of posts.
     */
    Collection<Post> findByPublishedAtAfterOrderByPublishedAtDesc(Date date);
}
