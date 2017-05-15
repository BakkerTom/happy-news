package nl.fhict.happynews.api;

import nl.fhict.happynews.api.auth.User;
import nl.fhict.happynews.api.auth.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
            User user = new User("admin", "password", Collections.singleton("ROLE_" + WebSecurityConfig.Roles.ADMIN));
            userRepository.save(user);
            logger.info("Created user \"{}\" with password \"{}\". Change this password for security reasons!",
                user.getUsername(),
                "password");
        }

        logger.info("Setup completed");
    }
}
