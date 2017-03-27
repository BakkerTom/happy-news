package nl.fhict.happynews.crawler;


import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import nl.fhict.happynews.crawler.model.newsapi.Article;
import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import nl.fhict.happynews.crawler.model.newsapi.Source;
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
        posts = new ArrayList<>();

        Post post1 = new Post();
        post1.setSource("the-post");
        post1.setSourceName("The Post");
        post1.setAuthor("Henry Hicker");
        post1.setTitle("People are terrible");
        post1.setContentText("lorem ipsum enz.");
        post1.setUrl("http://www.fakeurl.com/whatisthis/this.html");
        post1.setPublishedAt(new Date(1995, 11, 10));
        posts.add(post1);

        Post post2 = new Post();
        post2.setSource("ny-times");
        post2.setSourceName("The NY Times");
        post2.setAuthor("Harry Cochlear-Implant");
        post2.setTitle("What is a good person?");
        post2.setContentText("lorem ipsum enz.");
        post2.setUrl("http://www.fakeurl2.com/whatisthis/this.html");
        post2.setPublishedAt(new Date(2017, 1, 27));
        posts.add(post2);

        Post post3 = new Post();
        post3.setSource("tilburger");
        post3.setSourceName("De Tilburger");
        post3.setAuthor("Jan Karel Klojo");
        post3.setTitle("Blah blah blah.");
        post3.setContentText("ipsum lorem.");
        post3.setUrl("http://www.neppetilburg.nl/dit");
        post3.setPublishedAt(new Date(2016, 11, 8));
        posts.add(post3);

        Post post4 = new Post();
        post4.setSource("the-post");
        post4.setSourceName("The Post");
        post4.setAuthor("Henry Hicker");
        post4.setTitle("People are terrible");
        post4.setContentText("lorem ipsum enz.");
        post4.setUrl("http://www.abc.nl");
        post4.setPublishedAt(new Date(2000, 11, 10));
        posts.add(post4);

        controller.savePosts(posts);

        Assert.assertEquals(4, postRepository.findAll().size());

        posts = new ArrayList<>();
        posts.add(post4);

        controller.savePosts(posts);

        Assert.assertEquals(4, postRepository.findAll().size());


    }


    @Test
    public void getSources() {
        List<Source> sources = new ArrayList<>();
        sourceRepository.deleteAll();
        sources.add(new Source("the-next-web", "latest"));
        sources.add(new Source("cnn", "latest"));
        sources.add(new Source("bbc", "top"));
        sourceRepository.save(sources);

        Assert.assertEquals(3, controller.getSources().size());
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