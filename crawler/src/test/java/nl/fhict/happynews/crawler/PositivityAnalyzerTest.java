package nl.fhict.happynews.crawler;

import nl.fhict.happynews.crawler.analyzer.PositivityAnalyzer;
import nl.fhict.happynews.crawler.extractor.ArticleExtractor;
import nl.fhict.happynews.crawler.repository.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by daan_ on 27-3-2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PositivityAnalyzerTest {



    @Autowired
    private PositivityAnalyzer positivityAnalyzer;

    @Autowired
    private ArticleExtractor articleExtractor;

    @Autowired
    private CrawlerController crawlerController;


    @Test
    public void testAnalyzeText() {
        List<Post> posts = crawlerController.doGetNewsPosts();
        for(Post p : posts){
            String text = articleExtractor.extract(p);
            double score = positivityAnalyzer.analyzeText(text);
            System.out.println(","+p.getUrl());
        }


    }
}
