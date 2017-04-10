package nl.fhict.happynews.api.controller;

import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.shared.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Tobi on 06-Mar-17.
 */
@RestController
public class PostController {

    /**
     * Automagically creates a repository.
     **/
    @Autowired
    private PostRepository postRepository;

    /**
     * Handles a GET request by returning posts in a paginated format. Default page is 0, and default size = 20
     *
     * @param pageable the page and page size
     * @return A Page with Post information
     */
    @RequestMapping(value = "/post", method = RequestMethod.GET, produces = "application/json")
    public Page<Post> getAllByPage(Pageable pageable) {
        return this.postRepository.findAllByOrderByPublishedAtDesc(pageable);
    }

    /**
     * Handles a GET request by returning a Post by it's UUID.
     *
     * @param uuid The UUID.
     * @return The Post in JSON.
     */
    @RequestMapping(value = "/post/uuid/{uuid}", method = RequestMethod.GET, produces = "application/json")
    public Post getPostByUuid(@PathVariable("uuid") String uuid) {
        return this.postRepository.findByUuid(uuid);
    }

    /**
     * Handles a GET request by returning a collection of Post after a certain date.
     *
     * @param date    The date after which posts should be retrieved.
     * @param ordered Whether the list should be ordered by latest or not.
     * @return The Posts in JSON.
     */
    @RequestMapping(value = "/post/afterdate/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<Post> getPostAfterDate(
        @PathVariable("date") String date,
        @RequestParam(required = false, defaultValue = "true", value = "ordered") boolean ordered) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d hh:mm:ss z yyyy");
        Date properdate = null;
        try {
            properdate = sdf.parse(date);
        } catch (ParseException e) {
            Logger logger = LoggerFactory.getLogger(PostRepository.class);
            logger.error("Date cannot be parsed.", e);
        }
        if (ordered) {
            return this.postRepository.findByPublishedAtAfterOrderByPublishedAtDesc(properdate);
        } else {
            return this.postRepository.findByPublishedAtAfter(properdate);
        }
    }
}
