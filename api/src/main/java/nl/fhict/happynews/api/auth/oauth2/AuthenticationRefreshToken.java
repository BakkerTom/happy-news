package nl.fhict.happynews.api.auth.oauth2;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.persistence.GeneratedValue;

@Document
public class AuthenticationRefreshToken {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private String uuid;
    private String tokenId;
    @Field("oAuth2RefreshToken")
    private OAuth2RefreshToken refreshToken;
    private OAuth2Authentication authentication;

    /**
     * Create a new OAuth2 Refresh Token holder.
     *
     * @param refreshToken   The Refresh Token.
     * @param authentication The authentication.
     */
    public AuthenticationRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        this.refreshToken = refreshToken;
        this.authentication = authentication;
        this.tokenId = refreshToken.getValue();
    }

    public String getUuid() {
        return uuid;
    }

    public String getTokenId() {
        return tokenId;
    }

    public OAuth2RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }
}
