package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.api.NewsAPI;
import nl.fhict.happynews.crawler.models.newsapi.Article;
import nl.fhict.happynews.crawler.models.newsapi.NewsSource;
import nl.fhict.happynews.crawler.models.newsapi.Source;
import nl.fhict.happynews.crawler.repository.SourceRepository;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class NewsCrawler extends Crawler<NewsSource> {

    @Autowired
    private NewsAPI newsAPI;

    @Autowired
    private SourceRepository sourceRepository;

    @Override
    public void crawl() {
        List<NewsSource> raw = getRaw();

        List<Post> posts = new ArrayList<>();
        for(NewsSource n : raw){
            posts.addAll(rawToPosts(n));
        }
        savePosts(posts);
    }

    @Override
    List<NewsSource> getRaw() {
        List<Source> sources = getSources();
        logger.info("Start getting posts from newsapi.org");
        List<NewsSource> newsSources = new ArrayList<>();

        for (Source s : sources) {
            //retrieve news from the source
            NewsSource newsSource = newsAPI.getRaw(s.getName(), s.getType());
            newsSources.add(newsSource);
        }
        logger.info("Received total of " + newsSources.size() + " articles");
        return newsSources;
    }

    @Override
    List<Post> rawToPosts(NewsSource entity) {
        List<Post> posts = new ArrayList<>();
        if (entity != null) { //Check if request was successful
            for (Article a : entity.getArticles()) {
                //Create database ready objects
                Post p = new Post(entity.getSource(), a.getAuthor(), a.getTitle(), a.getDescription(), a.getUrl(), a.getUrlToImage(), a.getPublishedAt());
                posts.add(p);
            }
        }
        return posts;
    }

    private List<Source> getSources() {
        return sourceRepository.findAll();
    }
}
