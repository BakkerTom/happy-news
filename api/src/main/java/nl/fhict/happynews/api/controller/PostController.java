package nl.fhict.happynews.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tobi on 06-Mar-17.
 */
@RestController
public class PostController {

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String getPost() {
        return "Hello";
    }
}
