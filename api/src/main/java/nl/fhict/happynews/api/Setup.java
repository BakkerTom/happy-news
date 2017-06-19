package nl.fhict.happynews.api;

import nl.fhict.happynews.api.auth.User;
import nl.fhict.happynews.api.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("!test")
public class Setup {

    private static final Logger logger = LoggerFactory.getLogger(Setup.class);

    private final UserRepository userRepository;

    @Value("${default-user.username}")
    private String defaultUsername;
    @Value("${default-user.password}")
    private String defaultPassword;

    @Autowired
    public Setup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Run setup when application starts.
     */
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        logger.info("Running setup");

        if (userRepository.count() == 0) {
            logger.info("No users have been configured, creating default user");
            User user = new User(defaultUsername,
                defaultPassword,
                Collections.singleton("ROLE_" + WebSecurityConfig.Roles.ADMIN));
            userRepository.save(user);
            logger.info("Created user \"{}\" with password \"{}\". Change this password for security reasons!",
                defaultUsername,
                defaultPassword);
        }

        logger.info("Setup completed");
    }
}
