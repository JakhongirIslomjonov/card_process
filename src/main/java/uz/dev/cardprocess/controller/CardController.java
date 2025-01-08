package uz.dev.cardprocess.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.cardprocess.dto.*;
import uz.dev.cardprocess.service.CardService;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/create-card")
    public DataDTO<CardResponseDTO> cardResponseDTODataDTO(@RequestHeader("Idempotency-Key") UUID idempotencyKey, @RequestBody @Valid CardRequestDTO cardRequestDTO) {
        return cardService.createCard(idempotencyKey, cardRequestDTO);
    }

    @GetMapping("/get/{carId}")
    public ResponseEntity<?> getCardById(@PathVariable UUID carId) {
        return cardService.getCardById(carId);
    }

    @PostMapping("/{cardId}/block")
    public DataDTO<String> blockCard(@RequestHeader("If-Match") String eTag, @PathVariable UUID cardId) {
        return cardService.blockCard(eTag, cardId);
    }

    @PostMapping("/{cardId}/un-block")
    public DataDTO<String> unBlockCard(@RequestHeader("If-Match") String eTag, @PathVariable UUID cardId) {
        return cardService.unBlockCard(eTag, cardId);
    }

    @PostMapping("/{cardId}/debit")
    public DataDTO<DebitResponseDTO> debitCard(@RequestHeader("Idempotency-Key") UUID idempotencyKey, @RequestBody DebitRequestDTO debitRequestDTO, @PathVariable UUID cardId) {
        return cardService.debitCard(idempotencyKey, debitRequestDTO, cardId);
    }

    @PostMapping("/{cardId}/credit")
    public DataDTO<CreditResponseDTO> creditResponseDTODataDTO(@RequestHeader("Idempotency-Key") UUID idempotencyKey, @RequestBody CreditRequestDTO creditRequestDTO, @PathVariable UUID cardId) {
        return cardService.creditCard(idempotencyKey, creditRequestDTO, cardId);
    }

}
