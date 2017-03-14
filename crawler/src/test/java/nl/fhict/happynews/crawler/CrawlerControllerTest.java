package nl.fhict.happynews.crawler;


import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.crawler.models.newsapi.Source;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.crawler.repository.SourceRepository;
import nl.fhict.happynews.shared.Post;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by daan_ on 13-3-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerControllerTest {


    @Autowired
    PostRepository postRepository;

    @Autowired
    SourceRepository sourceRepository;


    private List<Post> posts;

    @Autowired
    private CrawlerController controller;


    @BeforeClass
    public static void startDatabase() throws IOException {
        mongo();
    }


    @Test
    public void savePosts() throws Exception {
        posts = new ArrayList<Post>();

        posts.add(
                new Post("The Post",
                        "Henry Hicker",
                        "People are terrible.",
                        "lorem ipsum enz.",
                        "http://www.fakeurl.com/whatisthis/this.html",
                        "none",
                        new Date(1995, 11, 10)
                ));

        posts.add(
                new Post("The NY Times",
                        "Harry Cochlear-Implant",
                        "What is a good person?",
                        "lorem ipsum enz.",
                        "http://www.fakeurl2.com/whatisthis/this.html",
                        "none",
                        new Date(2017, 1, 27)
                ));

        posts.add(
                new Post("De Tilburger",
                        "Jan Karel Klojo",
                        "Blah blah blah.",
                        "ipsum lorem.",
                        "http://www.neppetilburg.nl/dit",
                        "none",
                        new Date(2016, 11, 8)
                ));

        posts.add(
                new Post("The Post",
                        "Henry Hicker",
                        "People are terrible.",
                        "lorem.",
                        "http://www.abc.nl",
                        "none",
                        new Date(2000, 11, 10)
                ));
        controller.savePosts(posts);

        Assert.assertEquals(4,postRepository.findAll().size());

        posts = new ArrayList<>();
        posts.add(
                new Post("The Post",
                        "Henry Hicker",
                        "People are terrible.",
                        "lorem.",
                        "http://www.abc.nl",
                        "none",
                        new Date(2000, 11, 10)
                ));

        controller.savePosts(posts);

        Assert.assertEquals(4,postRepository.findAll().size());


    }


    @Test
    public void getSources(){
        List<Source> sources = new ArrayList<>();
        sources.add(new Source("the-next-web", "latest"));
        sources.add(new Source("cnn", "latest"));
        sources.add(new Source("bbc", "top"));
        sourceRepository.save(sources);

        Assert.assertEquals(3,controller.getSources().size());
    }

    @Test
    public void convertToPost() {
        List<Post> instance = controller.convertToPost(null);

        Assert.assertEquals(0, instance.size());

        //Set up newsSource class
        NewsSource newsSource = new NewsSource();
        Article a = new Article("Jan Janssen", "Dit is het nieuws van vandaag", "vandaag zijn er dingen gebeurd", "http://www.google.com", "https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png", new Date());
        Article b = new Article("Henk Henson", "Dit is het nieuws van gisteren", "gisteren zijn er dingen gebeurd", "http://www.google.com", "https://www.google.nl/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png", new Date());
        List<Article> articles = new ArrayList<>();
        newsSource.setStatus("ok");
        newsSource.setArticles(articles);
        newsSource.setSortBy("latest");
        newsSource.setSource("the-next-web");


        //Test with 0 articles
        instance = controller.convertToPost(newsSource);
        Assert.assertEquals(0, instance.size());


        //add articles and test with articles
        articles.add(a);
        articles.add(b);
        instance = controller.convertToPost(newsSource);
        Assert.assertEquals(2, instance.size());

    }


    @Bean(destroyMethod = "close")
    public static Mongo mongo() throws IOException {
        return new EmbeddedMongoBuilder()
                .version("2.4.5")
                .bindIp("127.0.0.1")
                .port(12345)
                .build();
    }


}