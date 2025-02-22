package uz.dev.cardprocess.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.dto.CardResponseDTO;
import uz.dev.cardprocess.dto.DataDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.entity.IdempotencyRecord;
import uz.dev.cardprocess.entity.enums.CardStatus;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.exceptions.BadRequestException;
import uz.dev.cardprocess.mapper.CardMapper;
import uz.dev.cardprocess.repository.CardRepository;
import uz.dev.cardprocess.repository.IdempotencyRecordRepository;
import uz.dev.cardprocess.service.impl.CardServiceImpl;
import uz.dev.cardprocess.service.impl.LogServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private IdempotencyRecordRepository idempotencyRecordRepository;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private LogServiceImpl logService;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void shouldNotCreateCardReplaceExistsCard() {

        UUID idempotencyKey = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setBalance(100000L);
        cardRequestDTO.setStatus(CardStatus.ACTIVE);
        cardRequestDTO.setCurrency(Currency.UZS);
        cardRequestDTO.setUserId(1L);

        IdempotencyRecord idempotencyRecord = new IdempotencyRecord();
        idempotencyRecord.setCardId(cardId);
        idempotencyRecord.setIdempotencyKey(idempotencyKey);

        Card existingCard = new Card();

        when(idempotencyRecordRepository.findById(idempotencyKey)).thenReturn(Optional.of(idempotencyRecord));
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(existingCard));
        when(cardMapper.toDto(existingCard)).thenReturn(new CardResponseDTO());


        DataDTO<CardResponseDTO> responseDTODataDTO = cardService.createCard(idempotencyKey, cardRequestDTO);

        assertNotNull(responseDTODataDTO);
        assertNotNull(responseDTODataDTO.getData());
        verify(cardRepository).findById(cardId);
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    void shouldFindActiveCardByUserId() {
        UUID idempotencyKey = UUID.randomUUID();
        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setUserId(1L);

        when(cardRepository.findActiveCardByUserId(cardRequestDTO.getUserId())).thenReturn(3);

        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> {
            cardService.createCard(idempotencyKey, cardRequestDTO);
        });

        verify(logService).writeLog("/log/create_card", "he card limit has been exceeded : ", 1L);

        assertEquals("The card limit has been exceeded ", badRequestException.getMessage());
    }

    @Test
    void shouldCreateCardWhenIdempotencyKeyNotExists() {

        UUID idempotencyKey = UUID.randomUUID();

        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setBalance(100000L);
        cardRequestDTO.setStatus(CardStatus.ACTIVE);
        cardRequestDTO.setCurrency(Currency.UZS);
        cardRequestDTO.setUserId(1L);

        Card card = new Card();
        CardResponseDTO cardResponseDTO = new CardResponseDTO();

        when(idempotencyRecordRepository.findById(idempotencyKey)).thenReturn(Optional.empty());
        when(cardRepository.findActiveCardByUserId(cardRequestDTO.getUserId())).thenReturn(0); 
        when(cardMapper.toEntity(cardRequestDTO)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardMapper.toDto(card)).thenReturn(cardResponseDTO);


        DataDTO<CardResponseDTO> responseDTODataDTO = cardService.createCard(idempotencyKey, cardRequestDTO);


        assertNotNull(responseDTODataDTO);
        assertNotNull(responseDTODataDTO.getData());
        verify(cardRepository).save(card);
        verify(idempotencyRecordRepository).save(any(IdempotencyRecord.class));
    }
}
