package nl.fhict.happynews.api.hibernate;

import nl.fhict.happynews.shared.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Tobi on 06-Mar-17.
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String>, QueryDslPredicateExecutor<Post> {
}
