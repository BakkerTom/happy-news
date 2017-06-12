package nl.fhict.happynews.api.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.ApiOperation;
import nl.fhict.happynews.api.hibernate.PostRepository;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
     * @param pageable         the page and page size
     * @param sourcesWhitelist optional list of requested sources
     * @return A Page with Post information
     */
    @ApiOperation("Get all posts in a paginated format")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getAllByPage(Pageable pageable, @RequestParam(required = false, defaultValue = "") String query) {
    public Page<Post> getAllByPage(Pageable pageable, @PathVariable(required = false) String[] sourcesWhitelist) {
        Sort sort = new Sort(Sort.Direction.DESC, "publishedAt");
        Pageable sortedPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (query.isEmpty()) {
            return postRepository.findAll(notHidden(), sortedPageable);
        } else {
            return postRepository.findAll(notHidden()
                .and(
                    QPost.post.title.containsIgnoreCase(query)
                        .or(QPost.post.contentText.containsIgnoreCase(query))
                        .or(QPost.post.sourceName.containsIgnoreCase(query))
                ), sortedPageable);
        }
        if (sourcesWhitelist != null) {
            return postRepository.findAll(notHidden().and(isAllowed(sourcesWhitelist)), sortedPageable);
        } else {
            return postRepository.findAll(notHidden(), sortedPageable);
        }
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
     * @param date             The date after which posts should be retrieved.
     * @param ordered          Whether the list should be ordered by latest or not.
     * @param sourcesWhitelist optional list of requested sources
     * @return The Posts in JSON.
     */
    @ApiOperation("Get posts after a given date")
    @RequestMapping(value = "/afterdate/{date}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Post> getPostAfterDate(
        @PathVariable("date") long date,
        @RequestParam(required = false, defaultValue = "true", value = "ordered") boolean ordered,
        @PathVariable(required = false) String[] sourcesWhitelist) {

        BooleanExpression predicate = null;
        if (sourcesWhitelist != null) {
            predicate = notHidden()
                .and(QPost.post.publishedAt.after(new DateTime(date)))
                .and(isAllowed(sourcesWhitelist));
        } else {
            predicate = notHidden()
                .and(QPost.post.publishedAt.after(new DateTime(date)));
        }
        Sort sort = new Sort(Sort.Direction.DESC, "publishedAt");

        if (ordered) {
            return postRepository.findAll(predicate, sort);
        } else {
            return postRepository.findAll(predicate);
        }
    }

    private BooleanExpression isAllowed(String[] sourcesWhitelist) {
        return QPost.post.source.in(sourcesWhitelist);
    }

    private BooleanExpression notHidden() {
        return QPost.post.hidden.isFalse();
    }
}
