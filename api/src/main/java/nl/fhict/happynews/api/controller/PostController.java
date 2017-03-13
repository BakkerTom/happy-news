package nl.fhict.happynews.api.controller;

import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Tobi on 06-Mar-17.
 */
@RestController
public class PostController {

    /**Automagically creates a repository**/
    @Autowired
    private PostRepository postRepository;

    /**
     * Handles a GET request by returning all posts.
     * @return The Posts in JSON.
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET, produces = "application/json")
    public Collection<Post> getAllPost() {
        return this.postRepository.findAll();
    }

    /**
     * Handles a GET request by returning a Post by it's UID.
     * @param uid The UID.
     * @return The Post in JSON.
     */
    @RequestMapping(value = "/post/uid/{uid}", method = RequestMethod.GET, produces = "application/json")
    public Post getPostByUid(@PathVariable("uid") String uid) {
        return this.postRepository.findByUid(uid);
    }

    /**
     * Handles a GET request by returning a collection of Post after a certain date.
     * @param date The date after which posts should be retrieved.
     * @return The Posts in JSON.
     */
    @RequestMapping(value = "/post/date/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<Post> getPostAfterDate(@PathVariable("date") Date date) {
        return this.postRepository.findByPublishedAtAfter(date);
    }


}
