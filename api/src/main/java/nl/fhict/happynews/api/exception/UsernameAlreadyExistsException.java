package nl.fhict.happynews.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "A user with the given username already exists.")
public class UsernameAlreadyExistsException extends RuntimeException {
}
