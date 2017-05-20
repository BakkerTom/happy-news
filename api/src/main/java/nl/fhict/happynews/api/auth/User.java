package nl.fhict.happynews.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.GeneratedValue;
import java.util.HashSet;
import java.util.Set;

@Document
public class User {

    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    private String uuid;
    @Indexed(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(hidden = true)
    @Transient
    private String rawPassword;
    private Set<String> roles = new HashSet<>();

    public User() {
    }

    /**
     * Create a User.
     * @param username The username.
     * @param password The raw password.
     * @param roles The roles, as "ROLE_" + one of {@link nl.fhict.happynews.api.WebSecurityConfig.Roles}.
     */
    public User(String username, String password, Set<String> roles) {
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }

    /**
     * Deserialize a User from a {@link DBObject}.
     *
     * @param dbObject The serialized object.
     */
    @SuppressWarnings("unchecked")
    public User(DBObject dbObject) {
        uuid = dbObject.get("_id").toString();
        username = (String) dbObject.get("username");
        password = (String) dbObject.get("password");
        ((BasicDBList) dbObject.get("roles")).forEach(o -> roles.add((String) o));
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The encoded password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets and encodes the password.
     *
     * @param password The raw password
     */
    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
        this.rawPassword = password;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
