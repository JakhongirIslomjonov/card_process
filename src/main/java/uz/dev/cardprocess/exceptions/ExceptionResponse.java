package uz.dev.cardprocess.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponse(HttpStatus status, Integer errorCode, String message, String timestamp) {

    public ExceptionResponse(HttpStatus status, String message, LocalDateTime timestamp) {
        this(status, status.value(), message, timestamp.toString());
    }
}
