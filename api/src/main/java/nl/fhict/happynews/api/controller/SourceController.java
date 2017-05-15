package nl.fhict.happynews.api.controller;

import nl.fhict.happynews.api.hibernate.SourceRepository;
import nl.fhict.happynews.shared.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by Sander on 09/05/2017.
 */
@RestController
public class SourceController {

    /**
     * Automagically creates a repository.
     **/
    @Autowired
    private SourceRepository sourceRepository;

    /**
     * Get all the sources from the database that come from an article.
     *
     * @return List of sources.
     */
    @RequestMapping(value = "/sources", method = RequestMethod.GET, produces = "application/json")
    public Collection<Source> getAllArticleSources() {
        return sourceRepository.findAll();
    }
}
