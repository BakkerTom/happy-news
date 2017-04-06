package nl.fhict.happynews.crawler.repository;

import nl.fhict.happynews.crawler.model.newsapi.Source;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by daan_ on 13-3-2017.
 */
public interface SourceRepository extends MongoRepository<Source, String> {
}
