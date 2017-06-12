package nl.fhict.happynews.api.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.ApiOperation;
import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.api.util.FlagRequest;
import nl.fhict.happynews.shared.Post;
import nl.fhict.happynews.shared.QPost;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by Tobi on 06-Mar-17.
 */
@RequestMapping("/post")
@RestController
public class PostController {

    /**
     * Automagically creates a repository.
     **/
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Handles a GET request by returning posts in a paginated format. Default page is 0, and default size = 20
     *
     * @param pageable the page and page size
     * @return A Page with Post information
     */
    @ApiOperation("Get all posts in a paginated format")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getAllByPage(Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.DESC, "publishedAt");
        Pageable sortedPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return postRepository.findAll(notHidden(), sortedPageable);
    }

    /**
     * Handles a GET request by returning a Post by its UUID.
     *
     * @param uuid The UUID.
     * @return The Post in JSON.
     */
    @ApiOperation("Get a post by its UUID")
    @RequestMapping(value = "/uuid/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getPostByUuid(@PathVariable("uuid") String uuid) {
        return postRepository.findOne(uuid);
    }

    /**
     * Handles a GET request by returning a collection of Post after a certain date.
     *
     * @param date    The date after which posts should be retrieved.
     * @param ordered Whether the list should be ordered by latest or not.
     * @return The Posts in JSON.
     */
    @ApiOperation("Get posts after a given date")
    @RequestMapping(value = "/afterdate/{date}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Post> getPostAfterDate(
        @PathVariable("date") long date,
        @RequestParam(required = false, defaultValue = "true", value = "ordered") boolean ordered) {

        BooleanExpression predicate = notHidden().and(QPost.post.publishedAt.after(new DateTime(date)));
        Sort sort = new Sort(Sort.Direction.DESC, "publishedAt");

        if (ordered) {
            return postRepository.findAll(predicate, sort);
        } else {
            return postRepository.findAll(predicate);
        }
    }

    private BooleanExpression notHidden() {
        return QPost.post.hidden.isFalse();
    }

    /**
     * Handles a put request for flagging a post
     *
     * @param uuid id of the post.
     * @param body reason for flagging.
     */
    @ApiOperation("flag a post by it's UUID")
    @RequestMapping(value = "/{uuid}/flag", method = RequestMethod.POST)
    public void flagPost(@PathVariable("uuid") String uuid,
                         @RequestBody FlagRequest body) {
        System.out.println("Request handle" + body.getReason());
        Post p = postRepository.findOne(uuid);
        p.addFlagReason(body.getReason());
        postRepository.save(p);
    }
}
