package nl.fhict.happynews.api.validator;

import nl.fhict.happynews.shared.Post;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Post} objects.
 */
public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Validate
    }
}
