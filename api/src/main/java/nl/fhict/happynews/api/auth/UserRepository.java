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

    /**
     * Checks if a username is already taken.
     *
     * @param username The username.
     * @return Whether a user with the given username exists.
     */
    boolean existsByUsername(String username);

    /**
     * Delete a user with the given username.
     *
     * @param username The username.
     */
    void deleteByUsername(String username);
}
