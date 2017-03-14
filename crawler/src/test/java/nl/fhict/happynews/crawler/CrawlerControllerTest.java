package nl.fhict.happynews.crawler;


import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daan_ on 13-3-2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
public class CrawlerControllerTest {


    @Autowired
    PostRepository repo;


    private List<Post> posts;


    private CrawlerController controller;


    @BeforeClass
    public static void startDatabase() throws IOException {
        mongo();
    }


    @Test
    public void savePosts() throws Exception {
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
        controller = new CrawlerController();
        controller.savePosts(posts);


    }


    @Test
    public void convertToPost() {
        controller = new CrawlerController();

        List<Post> instance = controller.convertToPost(null);

        Assert.assertEquals(0, instance.size());

        //Set up newsSource class
        NewsSource newsSource = new NewsSource();
        Article a = new Article("Jan Janssen","Dit is het nieuws van vandaag","vandaag zijn er dingen gebeurd","http://www.google.com","https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png", new Date());
        Article b = new Article("Henk Henson","Dit is het nieuws van gisteren","gisteren zijn er dingen gebeurd","http://www.google.com","https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png", new Date());
        List<Article> articles = new ArrayList<>();
        newsSource.setStatus("ok");
        newsSource.setArticles(articles);
        newsSource.setSortBy("latest");
        newsSource.setSource("the-next-web");



        //Test with 0 articles
        instance = controller.convertToPost(newsSource);
        Assert.assertEquals(0,instance.size());


        //add articles and test with articles
        articles.add(a);
        articles.add(b);
        instance = controller.convertToPost(newsSource);
        Assert.assertEquals(2,instance.size());

    }



    @Bean(destroyMethod="close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    }



}