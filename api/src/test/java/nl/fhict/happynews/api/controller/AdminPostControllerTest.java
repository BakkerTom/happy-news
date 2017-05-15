package nl.fhict.happynews.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import nl.fhict.happynews.api.TestConfig;
import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
public class AdminPostControllerTest {

    private static final String PATH = "/admin/posts";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private List<Post> posts;

    /**
     * Create some posts.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .build();

        posts = new LinkedList<>();

        Post post1 = new Post();
        post1.setSource("the-post");
        post1.setSourceName("The Post");
        post1.setAuthor("Henry Hicker");
        post1.setTitle("People are terrible.");
        post1.setContentText("lorem ipsum enz.");
        post1.setUrl("http://www.fakeurl.com/whatisthis/this.html");
        post1.setPublishedAt(new DateTime(2017, 5, 13, 14, 27));
        posts.add(postRepository.save(post1));

        Post post2 = new Post();
        post2.setSource("ny-times");
        post2.setSourceName("The NY Times");
        post2.setAuthor("Harry Cochlear-Implant");
        post2.setTitle("What is a good person?");
        post2.setContentText("lorem ipsum enz.");
        post2.setUrl("http://www.fakeurl2.com/whatisthis/this.html");
        post2.setPublishedAt(new DateTime(2017, 5, 15, 9, 13));
        posts.add(postRepository.save(post2));

        Post post3 = new Post();
        post3.setSource("tilburger");
        post3.setSourceName("De Tilburger");
        post3.setAuthor("Jan Karel Klojo");
        post3.setTitle("Blah blah blah.");
        post3.setContentText("ipsum lorem.");
        post3.setUrl("http://www.neppetilburg.nl/dit");
        post3.setPublishedAt(new DateTime(2003, 8, 28, 23, 11));
        posts.add(postRepository.save(post3));

        Post post4 = new Post();
        post4.setSource("the-post-2");
        post4.setSourceName("The Post 2");
        post4.setAuthor("Henry Hicker");
        post4.setTitle("People are terrible.");
        post4.setContentText("lorem.");
        post4.setUrl("http://www.abc.nl");
        post4.setPublishedAt(new DateTime(2015, 3, 9, 12, 12));
        posts.add(postRepository.save(post4));
    }

    @Test
    public void testGetAllPosts() throws Exception {
        mockMvc.perform(get(PATH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(4)));
    }

    @Test
    public void testGetPostByUuid() throws Exception {
        Post post = posts.get(0);
        mockMvc.perform(get(PATH + "/{uuid}", post.getUuid()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid", is(post.getUuid())))
            .andExpect(jsonPath("$.title", is(post.getTitle())))
            .andExpect(jsonPath("$.url", is(post.getUrl())));

    }

    @Test
    public void testGetPostByUuidNotFound() throws Exception {
        Post post = posts.get(0);
        mockMvc.perform(get(PATH + "/{uuid}", post.getUuid().substring(1)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePost() throws Exception {
        Post post = posts.get(0);
        post.setTitle("New Title");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());

        String json = objectMapper.writeValueAsString(post);

        mockMvc.perform(
            put(PATH + "/{uuid}", post.getUuid())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is("New Title")));

        assertThat(postRepository.findOne(post.getUuid()).getTitle(), is("New Title"));
    }

    @Test
    public void testHidePost()throws Exception {
        Post post = posts.get(0);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hidden", true);

        mockMvc.perform(
            post(PATH + "/{uuid}/hide", post.getUuid())
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid", is(post.getUuid())))
            .andExpect(jsonPath("$.hidden", is(true)));

        assertThat(postRepository.findOne(post.getUuid()).isHidden(), is(true));

        jsonObject.put("hidden", false);

        mockMvc.perform(
            post(PATH + "/{uuid}/hide", post.getUuid())
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.uuid", is(post.getUuid())))
            .andExpect(jsonPath("$.hidden", is(false)));

        assertThat(postRepository.findOne(post.getUuid()).isHidden(), is(false));
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
    }
}
