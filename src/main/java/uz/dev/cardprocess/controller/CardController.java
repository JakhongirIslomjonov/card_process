package uz.dev.cardprocess.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dev.cardprocess.dto.*;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.entity.enums.TransactionType;
import uz.dev.cardprocess.service.CardService;
import uz.dev.cardprocess.service.TransactionService;


import java.util.UUID;

@RestController
@RequestMapping("/v1/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final TransactionService transactionService;

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

    @GetMapping("/{cardId}/transaction")
    public DataDTO<?> transactionResponse(@PathVariable UUID cardId,
                                          @RequestParam(name = "type", required = false) TransactionType type,
                                       /*   @RequestParam(name = "transaction_id", required = false) UUID transactionId,
                                          @RequestParam(name = "external_id", required = false) String externalId,
                                          @RequestParam(name = "currency", required = false) Currency currency,*/
                                          @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return transactionService.getTransaction(cardId,type,/*transactionId,externalId,currency,*/page,size);
    }
}
