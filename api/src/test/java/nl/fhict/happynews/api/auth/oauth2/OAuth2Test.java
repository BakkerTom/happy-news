package nl.fhict.happynews.api.auth.oauth2;

import nl.fhict.happynews.api.TestConfig;
import nl.fhict.happynews.api.auth.User;
import nl.fhict.happynews.api.auth.UserRepository;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class OAuth2Test {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private User user;

    @Value("${oauth2.client.client-id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String clientSecret;

    /**
     * Create default user.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();

        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setRoles(Collections.singleton("ROLE_ADMIN"));

        this.user = userRepository.save(user);
    }

    @Test
    public void testToken() throws Exception {

        mockMvc.perform(get("/admin/users"))
            .andExpect(status().isUnauthorized());

        MvcResult mvcResult = mockMvc.perform(
            post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("grant_type", "password")
                .param("username", user.getUsername())
                .param("password", user.getRawPassword())
        )
            .andExpect(status().isOk())
            .andReturn();

        JSONObject body = new JSONObject(mvcResult.getResponse().getContentAsString());

        String accessToken = body.getString("access_token");

        mockMvc.perform(
            get("/admin/users")
                .header("Authorization", "Bearer " + accessToken)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }
}
