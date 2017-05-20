package nl.fhict.happynews.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested object could not be found.")
public class NotFoundException extends RuntimeException {
}
