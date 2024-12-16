package uz.dev.cardprocess.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.CardResponseDTO;

import java.util.Map;
import java.util.UUID;

@Service
public interface CardService {

    DataDTO<CardResponseDTO> createCard(UUID idempotencyKey, CardRequestDTO cardRequestDTO);

    ResponseEntity<?> getCardById(UUID cardId);

    DataDTO<String> blockCard(String eTag, UUID cardId);

}
