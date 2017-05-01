package nl.fhict.happynews.api.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a user by their username.
     *
     * @param username The username.
     * @return The user.
     */
    User findByUsername(String username);
}
