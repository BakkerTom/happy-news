package nl.fhict.happynews.api.validator;

import nl.fhict.happynews.api.auth.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link User} objects.
 */
public class UserValidator implements Validator {

    private final PasswordValidator passwordValidator = new PasswordValidator();

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");

        User user = (User) target;

        passwordValidator.validate(user.getRawPassword(), errors);
    }
}
