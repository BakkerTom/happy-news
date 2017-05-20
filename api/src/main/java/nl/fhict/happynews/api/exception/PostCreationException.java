package nl.fhict.happynews.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Could not create post.")
public class PostCreationException extends RuntimeException {
    public PostCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
