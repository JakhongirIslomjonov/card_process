package uz.dev.cardprocess.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.dto.CardResponseDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.service.CardService;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/create-card")
    public DataDTO<CardResponseDTO> cardResponseDTODataDTO(@RequestParam UUID idempotencyKey, @RequestBody CardRequestDTO cardRequestDTO) {
        return cardService.createCard(idempotencyKey, cardRequestDTO);
    }

}
