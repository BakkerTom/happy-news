package nl.fhict.happynews.crawler.crawler;

import nl.fhict.happynews.crawler.api.InstagramAPI;
import nl.fhict.happynews.crawler.model.instagramapi.InstagramEnvelope;
import nl.fhict.happynews.crawler.model.instagramapi.InstagramPost;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * Created by Tobi on 27-Mar-17.
 */
@Service
public class InstagramCrawler extends Crawler<InstagramEnvelope> {

    @Autowired
    private InstagramAPI InstagramAPI;

    @Value("${crawler.instagram.enabled:true}")
    private boolean enabled;

    @Override
    protected boolean isEnabled() {
        return enabled;
    }
  
     public InstagramCrawler() {
         super();
     }

    @Override
    public void crawl() {
        List<Post> posts = new ArrayList<>();
        getRaw().forEach(env -> posts.addAll(rawToPosts(env)));
        savePosts(posts);
    }

    @Override
    List<InstagramEnvelope> getRaw() {
        logger.info("Start getting posts from instagram api.");
        List<InstagramEnvelope> envelopes = new ArrayList<>();
        envelopes.add(InstagramAPI.getRaw("dogs")); //TODO: make a list of hashtags or something
        logger.info("Received total of " + envelopes.size() + " articles");
        return envelopes;
    }

    @Override
    List<Post> rawToPosts(InstagramEnvelope entity) {
        List<Post> result = new ArrayList<>();
        for(InstagramPost ipost : entity.data){
            Post post = new Post();
            post.setAuthor(ipost.getUser().getFullName());
            post.setContentText(null); //TODO
            post.setExpirationDate(null);
            post.setImageUrls(Arrays.asList(ipost.getImages()));
            post.setIndexedAt(new Date());
            post.setPublishedAt(ipost.getCreatedTime());
            post.setSource(ipost.getUser().getUsername());
            post.setSourceName(ipost.getUser().getUsername());
            post.setTags(Arrays.asList(ipost.getTags()));
            post.setTitle(null); //TODO
            post.setType(Post.Type.INSTA);
            post.setVideoUrl(null); //TODO
            post.setUrl(null); //TODO

            result.add(post);
        }
        return result;
    }
}
