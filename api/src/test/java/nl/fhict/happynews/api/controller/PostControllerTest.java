package nl.fhict.happynews.api.controller;

import nl.fhict.happynews.api.Application;
import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

    @Autowired PostRepository repo;

    @Autowired WebApplicationContext wac;
    @Autowired MockHttpSession session;
    @Autowired MockHttpServletRequest request;
    private MockMvc mockMvc;

    private List<Post> posts;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        posts = new LinkedList<Post>();

        posts.add(this.repo.save(new Post()));

        posts.add(this.repo.save(
                new Post("The Post",
                        "Henry Hicker",
                        "People are terrible.",
                        "lorem ipsum enz.",
                        "http://www.fakeurl.com/whatisthis/this.html",
                        "none",
                        new Date(1995, 11, 10)
                )));

        posts.add(this.repo.save(
                new Post("The NY Times",
                        "Harry Cochlear-Implant",
                        "What is a good person?",
                        "lorem ipsum enz.",
                        "http://www.fakeurl2.com/whatisthis/this.html",
                        "none",
                        new Date(2017, 1, 27)
                )));

        posts.add(this.repo.save(
                new Post("De Tilburger",
                        "Jan Karel Klojo",
                        "Blah blah blah.",
                        "ipsum lorem.",
                        "http://www.neppetilburg.nl/dit",
                        "none",
                        new Date(2016, 11, 8)
                )));

        posts.add(this.repo.save(
                new Post("The Post",
                        "Henry Hicker",
                        "People are terrible.",
                        "lorem.",
                        "http://www.abc.nl",
                        "none",
                        new Date(2000, 11, 10)
                )));

    }

    @After
    public void clear() throws Exception{
        this.repo.deleteAll();
    }

    @Test
    public void getAllPost() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void getPostByUuid() throws Exception {
        this.mockMvc.perform(get("/post/uuid/{uuid}", posts.get(1).getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is("The Post")));
    }

    @Test
    public void getPostAfterDate() throws Exception {
        this.mockMvc.perform(get("/post/afterdate/{date}", new Date(2010, 11, 10)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}