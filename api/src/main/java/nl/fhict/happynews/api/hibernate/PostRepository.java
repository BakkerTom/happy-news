package nl.fhict.happynews.api.hibernate;

import nl.fhict.happynews.shared.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Tobi on 06-Mar-17.
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String>, QueryDslPredicateExecutor<Post> {

    /**
     * Retrieves all Posts.
     *
     * @return A collection of posts.
     */
    List<Post> findAll();

    /**
     * Retrieves all Posts, ordered by publish date.
     *
     * @return A collection of posts.
     */
    Collection<Post> findAllByOrderByPublishedAtDesc();

    /**
     * Retrieves all Posts, ordered by publish date, in a Page format.
     *
     * @param pageable Pagination information.
     * @return A page with post content.
     */
    Page<Post> findAllByOrderByPublishedAtDesc(Pageable pageable);

    /**
     * Retrieves a Post by it's UUID.
     *
     * @param uuid The UUID.
     * @return The Post.
     */
    Post findByUuid(String uuid);

    /**
     * Retrieves all Posts published after the date parameter.
     *
     * @param date The date after which to retrieve posts from.
     * @return A collection of posts.
     */
    Collection<Post> findByPublishedAtAfter(Date date);

    /**
     * Retrieves all Posts published after the date parameter, ordered by date (Descending).
     *
     * @param date The date after which to retrieve posts from.
     * @return A collection of posts.
     */
    Collection<Post> findByPublishedAtAfterOrderByPublishedAtDesc(Date date);
}
