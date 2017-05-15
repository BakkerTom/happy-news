package nl.fhict.happynews.api.auth.oauth2;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRefreshTokenRepository extends MongoRepository<AuthenticationRefreshToken, String> {

    AuthenticationRefreshToken findByTokenId(String tokenId);
}
