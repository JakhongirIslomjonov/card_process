package uz.dev.cardprocess.service;

import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.dto.CardResponseDTO;

import java.util.UUID;

@Service
public interface CardService {
    DataDTO<CardResponseDTO> createCard(UUID idempotencyKey, CardRequestDTO cardRequestDTO);
}
