package nl.fhict.happynews.api.validator;

import nl.fhict.happynews.api.auth.PasswordChangeRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordChangeRequest.class.isAssignableFrom(clazz) || String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String password = null;

        if (target instanceof PasswordChangeRequest) {
            password = ((PasswordChangeRequest) target).getPassword();
        } else if (target instanceof String) {
            password = target.toString();
        }

        if (password != null && password.length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("password", "field.min.length", new Object[]{MINIMUM_PASSWORD_LENGTH},
                "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
        }

    }
}
