package nl.fhict.happynews.api.controller.admin;

import nl.fhict.happynews.api.auth.User;
import nl.fhict.happynews.api.auth.UserRepository;
import nl.fhict.happynews.api.auth.UserValidator;
import nl.fhict.happynews.api.exception.NotFoundException;
import nl.fhict.happynews.api.exception.UserCreationException;
import nl.fhict.happynews.api.exception.UsernameAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Admin REST controller for managing users.
 */
@RestController
@RequestMapping("/admin/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("unused")
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new UserValidator());
    }

    /**
     * Get all users.
     *
     * @return A list of all users.
     */
    @RequestMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Create a new user. The username has to be unique. The user will be validated using {@link UserValidator}.
     *
     * @param user The user to create.
     * @return The newly created user.
     */
    @RequestMapping(method = RequestMethod.POST)
    public User addUser(@Valid @RequestBody User user) {
        user.setUuid(null);

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Could not create user.", e);
            throw new UserCreationException("Could not create user.", e);
        }
    }

    /**
     * Get a user by its UUID.
     *
     * @param uuid The UUID.
     * @return The user.
     */
    @RequestMapping("/{uuid}")
    public User getUser(@PathVariable String uuid) {
        User user = userRepository.findOne(uuid);

        if (user == null) {
            throw new NotFoundException();
        }

        return user;
    }

    /**
     * Get a user by its username.
     *
     * @param username The username.
     * @return The user.
     */
    @RequestMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundException();
        }

        return user;
    }

    /**
     * Delete a user by its UUID.
     *
     * @param uuid The UUID.
     */
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String uuid) {
        userRepository.delete(uuid);
    }

    /**
     * Delete a user by its username.
     *
     * @param username The username.
     */
    @RequestMapping(value = "/username/{username}", method = RequestMethod.DELETE)
    public void deleteUserByUsername(@PathVariable String username) {
        userRepository.deleteByUsername(username);
    }
}
