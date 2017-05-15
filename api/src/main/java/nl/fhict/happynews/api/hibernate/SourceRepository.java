package nl.fhict.happynews.api.hibernate;

import nl.fhict.happynews.shared.Source;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by daan_ on 13-3-2017.
 */
public interface SourceRepository extends MongoRepository<Source, String> {
}
