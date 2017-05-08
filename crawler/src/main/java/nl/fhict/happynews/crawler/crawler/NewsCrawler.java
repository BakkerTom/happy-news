package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.analyzer.PositivityAnalyzer;
import nl.fhict.happynews.crawler.api.NewsApi;
import nl.fhict.happynews.crawler.extractor.ArticleExtractor;
import nl.fhict.happynews.crawler.model.newsapi.Article;
import nl.fhict.happynews.crawler.model.newsapi.NewsSource;
import nl.fhict.happynews.crawler.model.newsapi.Source;
import nl.fhict.happynews.crawler.repository.SourceRepository;
import nl.fhict.happynews.shared.Post;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tobi on 27-Mar-17.
 */
@Service
public class NewsCrawler extends Crawler<NewsSource> {

    @Autowired
    private NewsApi newsApi;

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ArticleExtractor articleExtractor;

    @Autowired
    private PositivityAnalyzer positivityAnalyzer;

    @Value("${crawler.news.enabled:true}")
    private boolean enabled;

    @Override
    protected boolean isEnabled() {
        return enabled;
    }

    /**
     * Insert sources into database after server startup.
     */
    @PostConstruct
    public void insertSources() {
        List<Source> sources = new ArrayList<>();
        sources.add(new Source("the-next-web", "latest"));
        sources.add(new Source("associated-press", "latest"));
        sources.add(new Source("bbc-news", "top"));
        sources.add(new Source("bloomberg", "top"));
        sources.add(new Source("business-insider", "latest"));
        sources.add(new Source("buzzfeed", "latest"));
        sources.add(new Source("cnbc", "top"));
        sources.add(new Source("cnn", "top"));
        sources.add(new Source("entertainment-weekly", "top"));
        sources.add(new Source("financial-times", "latest"));
        for (Source s : sources) {
            try {
                sourceRepository.save(s);
                logger.info(s.getName() + " added as source.");
            } catch (DuplicateKeyException ex) {
                logger.warn(s.getName() + " already in database, not inserted");
            }
        }

    }

    @Override
    public void crawl() {
        List<Post> posts = getRaw().stream()
                .flatMap(newsSource -> rawToPosts(newsSource).stream())
                .filter(post -> positivityAnalyzer.analyzeText(articleExtractor.extract(post)))
                .collect(Collectors.toList());

        savePosts(posts);
    }

    @Override
    List<NewsSource> getRaw() {
        List<Source> sources = getSources();
        logger.info("Start getting posts from newsapi.org");
        List<NewsSource> newsSources = new ArrayList<>();

        for (Source s : sources) {
            //retrieve news from the source
            NewsSource newsSource = newsApi.getRaw(s.getName(), s.getType());
            newsSources.add(newsSource);
        }
        logger.info("Received total of " + newsSources.size() + " articles");
        return newsSources;
    }

    @Override
    List<Post> rawToPosts(NewsSource entity) {
        List<Post> posts = new ArrayList<>();
        if (entity != null) { //Check if request was successful
            for (Article article : entity.getArticles()) {
                //Create database ready objects
                Post post = new Post();
                post.setSource(entity.getSource());
                post.setAuthor(article.getAuthor());
                post.setTitle(article.getTitle());
                post.setContentText(article.getDescription());
                post.setUrl(article.getUrl());

                if (article.getUrlToImage() != null) {
                    post.setImageUrls(Collections.singletonList(article.getUrlToImage()));
                }

                if (article.getPublishedAt() != null) {
                    post.setPublishedAt(article.getPublishedAt());
                }

                post.setIndexedAt(new DateTime());

                post.setType(Post.Type.ARTICLE);

                posts.add(post);
            }
        }
        return posts;
    }

    private List<Source> getSources() {
        return sourceRepository.findAll();
    }
}
