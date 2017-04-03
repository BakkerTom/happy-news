package nl.fhict.happynews.crawler.extractor;

import nl.fhict.happynews.shared.Post;

/**
 * An extractor for getting the actual content of a post, e.g. the article body.
 */
@FunctionalInterface
public interface ContentExtractor {

    /**
     * Get the content of a post, e.g. the article body.
     *
     * @param post The post to extract from.
     * @return The content of the url.
     */
    String extract(Post post);
}
