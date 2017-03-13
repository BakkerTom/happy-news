package nl.fhict.happynews.crawler;


import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.shared.Post;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by daan_ on 13-3-2017.
 */
public class CrawlerControllerTest {

    @Test
    public void convertToPost() {
        CrawlerController controller = new CrawlerController();

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

}