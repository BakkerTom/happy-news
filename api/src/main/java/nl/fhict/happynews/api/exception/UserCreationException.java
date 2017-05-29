package nl.fhict.happynews.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Could not create user.")
public class UserCreationException extends RuntimeException {
    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
