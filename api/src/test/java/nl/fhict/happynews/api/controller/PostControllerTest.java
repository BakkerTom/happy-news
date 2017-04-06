package nl.fhict.happynews.api.controller;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.api.Application;
import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tobi on 13-Mar-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class PostControllerTest {

    @Autowired
    PostRepository repo;

    @Autowired
    WebApplicationContext wac;
    @Autowired
    MockHttpSession session;
    @Autowired
    MockHttpServletRequest request;
    private MockMvc mockMvc;

    private List<Post> posts;

    @BeforeClass
    public static void startDatabase() throws IOException {
        mongo();
    }

    /**
     * Populate the repository with posts.
     */
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        posts = new LinkedList<>();

        posts.add(this.repo.save(new Post()));

        Post post1 = new Post();
        post1.setSource("the-post");
        post1.setSourceName("The Post");
        post1.setAuthor("Henry Hicker");
        post1.setTitle("People are terrible.");
        post1.setContentText("lorem ipsum enz.");
        post1.setUrl("http://www.fakeurl.com/whatisthis/this.html");
        post1.setPublishedAt(new Date(2000, 11, 10));
        posts.add(this.repo.save(post1));

        Post post2 = new Post();
        post2.setSource("ny-times");
        post2.setSourceName("The NY Times");
        post2.setAuthor("Harry Cochlear-Implant");
        post2.setTitle("What is a good person?");
        post2.setContentText("lorem ipsum enz.");
        post2.setUrl("http://www.fakeurl2.com/whatisthis/this.html");
        post2.setPublishedAt(new Date(2016, 1, 27));
        posts.add(this.repo.save(post2));

        Post post3 = new Post();
        post3.setSource("tilburger");
        post3.setSourceName("De Tilburger");
        post3.setAuthor("Jan Karel Klojo");
        post3.setTitle("Blah blah blah.");
        post3.setContentText("ipsum lorem.");
        post3.setUrl("http://www.neppetilburg.nl/dit");
        post3.setPublishedAt(new Date(2017, 11, 8));
        posts.add(this.repo.save(post3));

        Post post4 = new Post();
        post4.setSource("the-post-2");
        post4.setSourceName("The Post 2");
        post4.setAuthor("Henry Hicker");
        post4.setTitle("People are terrible.");
        post4.setContentText("lorem.");
        post4.setUrl("http://www.abc.nl");
        post4.setPublishedAt(new Date(1995, 11, 10));
        posts.add(this.repo.save(post4));

    }

    @After
    public void clear() throws Exception {
        this.repo.deleteAll();
    }

    @Test
    public void getAllPost() throws Exception {
        //regular get all posts
        this.mockMvc.perform(get("/post"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", hasSize(5)));

    }

    @Test
    public void getPostByUuid() throws Exception {
        this.mockMvc.perform(get("/post/uuid/{uuid}", posts.get(1).getUuid()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.sourceName", is("The Post")));
    }

    @Test
    public void getPostAfterDate() throws Exception {
        //regular get after date
        this.mockMvc.perform(get("/post/afterdate/{date}", new Date(2010, 11, 10)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].sourceName", is("De Tilburger")));
        //superfluous ordered parameter
        this.mockMvc.perform(get("/post/afterdate/{date}?ordered={ordered}", new Date(2010, 11, 10), true))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sourceName", is("De Tilburger")));
        //unordered list
        this.mockMvc.perform(get("/post/afterdate/{date}?ordered={ordered}", new Date(2010, 11, 10), false))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].sourceName", is("The NY Times")));
    }

    /**
     * @return A new in memory MongoDB.
     */
    @Bean(destroyMethod = "close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
            .version("2.4.5")
            .bindIp("127.0.0.1")
            .port(12345)
            .build();
    }
}
