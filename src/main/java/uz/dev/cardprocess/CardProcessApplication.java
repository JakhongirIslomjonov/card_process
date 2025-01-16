package uz.dev.cardprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import uz.dev.cardprocess.exceptions.BadRequestException;
@EnableAsync
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class CardProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardProcessApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
