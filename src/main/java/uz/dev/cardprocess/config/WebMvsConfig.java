package uz.dev.cardprocess.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081")  // Sizning Swagger UI va API'ni ishlatadigan domen
                .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
                .allowedHeaders("*");

    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
