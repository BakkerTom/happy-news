package nl.fhict.happynews.api.controller;

import nl.fhict.happynews.api.TestConfig;
import nl.fhict.happynews.api.auth.User;
import nl.fhict.happynews.api.auth.UserRepository;
import nl.fhict.happynews.api.exception.UsernameAlreadyExistsException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class UserControllerTest {

    private static final String PATH = "/admin/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private User user;

    /**
     * Create default user.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();

        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setRoles(Collections.singleton("ROLE_ADMIN"));

        this.user = userRepository.save(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get(PATH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testAddUser() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "user");
        jsonObject.put("password", "password1");

        mockMvc.perform(
            post(PATH)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("user")))
            .andExpect(jsonPath("$.roles", hasSize(0)));

        assertThat(userRepository.count(), is(2L));
        assertThat(userRepository.existsByUsername("user"), is(true));
        assertThat(passwordEncoder.matches("password1", userRepository.findByUsername("user").getPassword()), is(true));
    }

    @Test
    public void testAddUserDuplicateUsername() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "password1");

        mockMvc.perform(
            post(PATH)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(status().reason("A user with the given username already exists."))
            .andExpect(result -> assertThat(result.getResolvedException(),
                is(instanceOf(UsernameAlreadyExistsException.class))));

        assertThat(userRepository.count(), is(1L));
        assertThat(passwordEncoder.matches("password", userRepository.findByUsername("admin").getPassword()), is(true));
    }

    @Test
    public void testAddUserShortPassword() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "user");
        jsonObject.put("password", "pass");

        mockMvc.perform(
            post(PATH)
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        assertThat(userRepository.count(), is(1L));
    }

    @Test
    public void testGetUserByUuid() throws Exception {
        mockMvc.perform(get(PATH + "/{uuid}", user.getUuid()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("admin")))
            .andExpect(jsonPath("$.uuid", is(user.getUuid())))
            .andExpect(jsonPath("$.roles", contains("ROLE_ADMIN")));
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        mockMvc.perform(get(PATH + "/username/{username}", user.getUsername()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is("admin")))
            .andExpect(jsonPath("$.uuid", is(user.getUuid())))
            .andExpect(jsonPath("$.roles", contains("ROLE_ADMIN")));
    }

    @Test
    public void testDeleteUserByUuid() throws Exception {
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));

        mockMvc.perform(delete(PATH + "/{uuid}", user.getUuid()))
            .andExpect(status().isOk());

        assertThat(userRepository.count(), is(0L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(false));
    }

    @Test
    public void testDeleteUserByUuidNotFound() throws Exception {
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));

        mockMvc.perform(delete(PATH + "/{uuid}", user.getUuid().substring(1)))
            .andExpect(status().isNotFound());

        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));
    }

    @Test
    public void testDeleteUserByUsername() throws Exception {
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));

        mockMvc.perform(delete(PATH + "/username/{username}", user.getUsername()))
            .andExpect(status().isOk());

        assertThat(userRepository.count(), is(0L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(false));
    }

    @Test
    public void testDeleteUserByUsernameNotFound() throws Exception {
        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));

        mockMvc.perform(delete(PATH + "/username/{username}", user.getUsername() + "1"))
            .andExpect(status().isNotFound());

        assertThat(userRepository.count(), is(1L));
        assertThat(userRepository.existsByUsername(user.getUsername()), is(true));
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

}
