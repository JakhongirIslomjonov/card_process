package uz.dev.cardprocess.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.*;
import uz.dev.cardprocess.entity.Transaction;

import java.util.Map;
import java.util.UUID;

@Service
public interface CardService {

    DataDTO<CardResponseDTO> createCard(UUID idempotencyKey, CardRequestDTO cardRequestDTO);

    ResponseEntity<?> getCardById(UUID cardId);

    DataDTO<String> blockCard(String eTag, UUID cardId);

    DataDTO<String> unBlockCard(String eTag, UUID cardId);

    DataDTO<DebitResponseDTO> debitCard(UUID idempotencyKey, DebitRequestDTO debitRequestDTO, UUID carId);

    DataDTO<CreditResponseDTO> creditCard(UUID idempotencyKey, CreditRequestDTO creditRequestDTO, UUID carId);
}
