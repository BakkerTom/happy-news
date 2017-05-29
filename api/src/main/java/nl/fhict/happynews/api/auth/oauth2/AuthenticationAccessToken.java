package nl.fhict.happynews.api.auth.oauth2;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.GeneratedValue;

@Document
public class AuthenticationAccessToken {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private String uuid;
    private String tokenId;
    @Field("oAuth2AccessToken")
    private OAuth2AccessToken accessToken;
    private String authenticationId;
    private String userName;
    private String clientId;
    private OAuth2Authentication authentication;
    private String refreshToken;

    public AuthenticationAccessToken() {
    }

    /**
     * Create a new OAuth2 Access Token holder.
     *
     * @param accessToken      The Access Token.
     * @param authentication   The authentication.
     * @param authenticationId The id of the authentication.
     */
    public AuthenticationAccessToken(final OAuth2AccessToken accessToken,
                                     final OAuth2Authentication authentication,
                                     final String authenticationId) {
        this.tokenId = accessToken.getValue();
        this.accessToken = accessToken;
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = authentication;
        this.refreshToken = accessToken.getRefreshToken().getValue();
    }

    public String getUuid() {
        return uuid;
    }

    public String getTokenId() {
        return tokenId;
    }

    public OAuth2AccessToken getAccessToken() {
        return accessToken;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public String getClientId() {
        return clientId;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
