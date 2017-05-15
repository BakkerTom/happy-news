package nl.fhict.happynews.crawler.extractor;

import nl.fhict.happynews.shared.Post;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * An extractor for the body of an article.
 */
@Service
public class ArticleExtractor implements ContentExtractor {

    private final Logger logger = LoggerFactory.getLogger(ArticleExtractor.class);

    private RestTemplate restTemplate = new RestTemplate();
    private HttpEntity entity;
    private Whitelist whitelist = new Whitelist();

    @Value("${extractor.article.apiurl}")
    private String apiPrefix;

    @Value("${extractor.article.apikey}")
    private String apiKey;

    public ArticleExtractor() {
    }

    @PostConstruct
    private void init() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);

        entity = new HttpEntity(headers);
    }

    @Override
    public String extract(Post post) {
        logger.info("Extracting " + post.getUrl());
        StringBuilder builder = new StringBuilder();

        String nextUrl = apiPrefix + post.getUrl();
        try {
            MercuryResponse response;
            do {
                ResponseEntity<MercuryResponse> exchange = restTemplate.exchange(nextUrl,
                    HttpMethod.GET,
                    entity,
                    MercuryResponse.class);

                if (!exchange.hasBody()) {
                    break;
                }

                response = exchange.getBody();

                if (response.getContent() != null) {
                    builder.append(Jsoup.clean(response.getContent(), whitelist));
                }

                nextUrl = apiPrefix + response.getNextPageUrl();
            } while (response.getNextPageUrl() != null);

        } catch (RestClientException e) {
            logger.warn("Could not read from " + nextUrl, e);
        }

        String result = builder.toString();

        if (result.isEmpty()) {
            return post.getContentText() != null ? post.getContentText() : "";
        } else {
            return result;
        }
    }
}
