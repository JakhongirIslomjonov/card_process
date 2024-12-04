package uz.dev.cardprocess.config;

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
}
