package nl.fhict.happynews.api.auth.oauth2;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoAccessTokenRepository extends MongoRepository<AuthenticationAccessToken, String> {

    AuthenticationAccessToken findByTokenId(String tokenId);

    AuthenticationAccessToken findByRefreshToken(String refreshToken);

    AuthenticationAccessToken findByAuthenticationId(String authenticationId);

    List<AuthenticationAccessToken> findByClientIdAndUserName(String clientId, String userName);

    List<AuthenticationAccessToken> findByClientId(String clientId);
}
