package nl.fhict.happynews.api.controller.admin;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.swagger.annotations.ApiOperation;
import nl.fhict.happynews.api.exception.NotFoundException;
import nl.fhict.happynews.api.exception.PostCreationException;
import nl.fhict.happynews.api.hibernate.PostRepository;
import nl.fhict.happynews.api.util.HideRequest;
import nl.fhict.happynews.api.validator.PostValidator;
import nl.fhict.happynews.shared.Post;
import nl.fhict.happynews.shared.QPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/admin/posts")
public class AdminPostController {

    private static final Logger logger = LoggerFactory.getLogger(AdminPostController.class);

    private final PostRepository postRepository;

    @Autowired
    public AdminPostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @SuppressWarnings("unused")
    @InitBinder("post")
    protected void initPostBinder(WebDataBinder binder) {
        binder.addValidators(new PostValidator());
    }

    /**
     * Handles a GET request by returning posts in a paginated format. Default page is 0, and default size = 20
     *
     * @param pageable the page and page size
     * @param isFiltered only displays flagged posts if true, false by default
     * @return A Page with Post information
     */
    @ApiOperation("Get all posts in a paginated format")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Post> getAllByPage(Pageable pageable,
                                   @RequestParam(required = false, defaultValue = "false") Boolean isFiltered) {
        Sort sort = new Sort(Sort.Direction.DESC, "publishedAt");
        Pageable sortedPageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if (isFiltered) {
            BooleanExpression predecate = QPost.post.flagReasons.isNotEmpty();
            return postRepository.findAll(predecate, sortedPageable);
        }

        return postRepository.findAll(sortedPageable);
    }

    /**
     * Handles a GET request by returning a Post by its UUID.
     *
     * @param uuid The UUID.
     * @return The Post in JSON.
     */
    @ApiOperation("Get a post by its UUID")
    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getPostByUuid(@PathVariable("uuid") String uuid) {
        Post post = postRepository.findOne(uuid);

        if (post == null) {
            throw new NotFoundException();
        }

        return post;
    }

    /**
     * Create a new post. The post will be validated using {@link PostValidator}.
     *
     * @param post The post to create.
     * @return The newly created post.
     */
    @ApiOperation("Create a new post")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> addPost(@Valid @RequestBody Post post) {
        post.setUuid(null);

        try {
            Post saved = postRepository.save(post);
            return ResponseEntity
                .created(URI.create("/admin/posts/" + saved.getUuid()))
                .body(saved);
        } catch (Exception e) {
            logger.error("Could not create post.", e);
            throw new PostCreationException("Could not create post.", e);
        }
    }

    /**
     * Create or replace a post. The post will be validated using {@link PostValidator}.
     *
     * @param uuid The UUID.
     * @param post The post content.
     * @return The new state of the post.
     */
    @ApiOperation("Create or replace a post")
    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> updatePost(@PathVariable String uuid, @Valid @RequestBody Post post) {
        post.setUuid(uuid);

        try {
            return ResponseEntity
                .ok(postRepository.save(post));
        } catch (Exception e) {
            logger.error("Could not update post.", e);
            throw new PostCreationException("Could not update post.", e);
        }
    }

    /**
     * Delete a post by its UUID.
     *
     * @param uuid The UUID.
     */
    @ApiOperation("Delete a post")
    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> deletePost(@PathVariable String uuid) {
        if (postRepository.exists(uuid)) {
            postRepository.delete(uuid);
            return ResponseEntity
                .ok()
                .build();
        } else {
            return ResponseEntity
                .notFound()
                .build();
        }
    }

    /**
     * Hide or un-hide a post.
     *
     * @param uuid The UUID.
     * @return The new state of the post.
     */
    @ApiOperation("Hide or un-hide a post")
    @RequestMapping(value = "/{uuid}/hide", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> hide(@PathVariable String uuid, @RequestBody HideRequest body) {
        Post post = postRepository.findOne(uuid);

        if (post == null) {
            return ResponseEntity
                .notFound()
                .build();
        }

        post.setHidden(body.isHidden());

        return ResponseEntity
            .ok(postRepository.save(post));
    }
}
