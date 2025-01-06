package uz.dev.cardprocess.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.Sentry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import uz.dev.cardprocess.exceptions.ExceptionResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public abstract class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        // Set response status and content type
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        System.out.println("request.getHeader(\"Authorization\") = " + request.getHeader("Authorization"));

        // Create an exception response
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication token is missing or invalid.",
                LocalDateTime.now()
        );

        // Log the original exception to Sentry
        Sentry.captureException(authException);

        // Write the response as JSON
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
