package nl.fhict.happynews.crawler.repository;

import nl.fhict.happynews.shared.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by daan_ on 7-3-2017.
 */
public interface PostRepository extends MongoRepository<Post,String> {

}
