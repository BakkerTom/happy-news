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
     * Retrieves a Post by it's UID.
     * @param uid The UID.
     * @return The Post.
     */
    Post findByUid(String uid);

    /**
     * Retrieves all Posts published after the date parameter.
     * @param date The date after which to retrieve posts from.
     * @return A collection of posts.
     */
    Collection<Post> findByPublishedAtAfter(Date date);
}
