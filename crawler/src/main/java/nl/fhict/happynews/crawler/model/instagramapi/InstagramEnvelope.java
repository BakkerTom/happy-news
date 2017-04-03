package nl.fhict.happynews.crawler.model.instagramapi;

import java.util.List;

/**
 * Created by Tobi on 27-Mar-17.
 */
public class InstagramEnvelope {

    public String[] meta;
    public List<InstagramPost> data;
    public String[] pagination;
}
