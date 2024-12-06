package uz.dev.cardprocess.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.dto.CardResponseDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.IdempotencyRecord;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.mapper.CardMapper;
import uz.dev.cardprocess.repository.CardRepository;
import uz.dev.cardprocess.repository.IdempotencyRecordRepository;
import uz.dev.cardprocess.repository.UserRepository;
import uz.dev.cardprocess.service.CardService;
import uz.dev.cardprocess.util.CardUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final IdempotencyRecordRepository idempotencyRecordRepository;
    private final CardUtil cardUtil;
    private final CardMapper cardMapper;
    private final UserRepository userRepository;

    @Override
    public DataDTO<CardResponseDTO> createCard(UUID idempotencyKey, CardRequestDTO cardRequestDTO) {
        if (idempotencyRecordRepository.findById(idempotencyKey).isPresent()) {
            Card card = cardUtil.checkCardExistence(idempotencyRecordRepository.findById(idempotencyKey).get().getCardId());
            return new DataDTO<>(cardMapper.toDto(card));
        }
        if (userRepository.findById(cardRequestDTO.getUserId()).isEmpty()) {
            throw new BadRequestException("user not found");
        }
        if (cardRepository.findActiveCardByUserId(cardRequestDTO.getUserId()) == 3) {
            throw new BadRequestException("The card limit has been exceeded ");
        }
        Card card = cardRepository.save(cardMapper.toEntity(cardRequestDTO, userRepository));
        idempotencyRecordRepository.save(new IdempotencyRecord(idempotencyKey, card.getId()));
        return new DataDTO<>(cardMapper.toDto(card));
    }
}
