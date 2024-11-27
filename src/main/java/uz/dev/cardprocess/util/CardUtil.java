package uz.dev.cardprocess.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uz.dev.cardprocess.dto.CurrencyRateDTO;
import uz.dev.cardprocess.dto.DebitRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.repository.CardRepository;

import java.util.List;
import java.util.UUID;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class CardUtil {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CardRepository cardRepository;

    @Cacheable(value = "currencyRate")
    public long fetchCurrencyRate() {
        String url = "https://cbu.uz/uz/arkhiv-kursov-valyut/json/USD/";
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                List<CurrencyRateDTO> rates = objectMapper.readValue(jsonResponse, new TypeReference<>() {
                });
                if (rates.isEmpty()) {
                    throw new BadRequestException("The currency could not be loaded.");
                }
                return Math.round(rates.get(0).getRate());
            }
            throw new BadRequestException("The currency could not be loaded.");
        } catch (JsonProcessingException e) {
            throw new BadRequestException("The currency could not be parsed.");
        }

    }

    @CacheEvict(value = "currencyRate", allEntries = true)
    @Scheduled(cron = "0 0 1 * * *")
    public void clearCache() {
    }

    public long sumWithCurrencyRate(Card card, DebitRequestDTO transactionDto, long currencyRate) {
        long sum;
        if (transactionDto.getCurrency().equals(Currency.USD)) {
            sum = transactionDto.getAmount() * currencyRate;
            if (sum > card.getBalance()) {
                throw new BadRequestException("Insufficient funds.");
            }
            return sum;
        } else {
            sum = transactionDto.getAmount() / currencyRate;
            if (sum > card.getBalance()) {
                throw new BadRequestException("Insufficient funds.");
            }
            return sum;
        }
    }

    public Card checkCardExistence(UUID cardId) {
        var cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            throw new BadRequestException("Card with such id not exists in processing.");
        }
        return cardOptional.get();
    }
}