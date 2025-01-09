package uz.dev.cardprocess.exceptions.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.sentry.Sentry;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import uz.dev.cardprocess.dto.AppErrorDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.exceptions.ExceptionResponse;
import uz.dev.cardprocess.exceptions.NotFoundException;



import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public HttpEntity<?> handleAccessDeniedException(UsernameNotFoundException e) {
        Sentry.captureException(e); // Sentryga yuborish
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(
                        HttpStatus.FORBIDDEN,
                        "You do not have permission to access this resource!",
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public HttpEntity<?> handleAccessDeniedException(ExpiredJwtException e) {
        Sentry.captureException(e); // Sentryga yuborish
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(
                        HttpStatus.FORBIDDEN,
                        "Authentication token is expired!",
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        Sentry.captureException(e); // Sentryga yuborish
        String errorMessage = String.format("Validation failed: '%s' for parameter '%s'", e.getValue(), e.getName());
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolation(ConstraintViolationException e) {
        Sentry.captureException(e); // Sentryga yuborish
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> String.format("'%s' : %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.joining(", "));

        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed: " + errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Sentry.captureException(e); // Sentryga yuborish
        String errorMessage = "Malformed JSON request or invalid data format";
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DataDTO<?>> handleMethodArgumentTypeMismatch(BadRequestException e) {
        Sentry.captureException(e); // Sentryga yuborish
        return ResponseEntity.ok(new DataDTO<>(new AppErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchRuntime(RuntimeException e) {
        Sentry.captureException(e); // Sentryga yuborish
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException e) {
        Sentry.captureException(e); // Sentryga yuborish
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication credentials are missing or invalid",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException e) {
        Sentry.captureException(e); // Sentryga yuborish
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Sentry.captureException(e); // Sentryga yuborish
        StringBuilder msg = new StringBuilder("Check field(s) format: ");
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            msg.append(fieldName).append(" - ").append(errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST,
                        msg.toString(),
                        LocalDateTime.now()));
    }
}
