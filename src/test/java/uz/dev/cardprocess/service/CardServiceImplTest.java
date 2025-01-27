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
import uz.dev.cardprocess.entity.enums.CardStatus;
import uz.dev.cardprocess.entity.enums.Currency;
import uz.dev.cardprocess.mapper.CardMapper;
import uz.dev.cardprocess.repository.CardRepository;
import uz.dev.cardprocess.repository.IdempotencyRecordRepository;
import uz.dev.cardprocess.service.impl.CardServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private IdempotencyRecordRepository idempotencyRecordRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void shouldCreateCardWhenIdempotencyKeyNotExists() {
        // Arrange
        UUID idempotencyKey = UUID.randomUUID();

        CardRequestDTO cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setBalance(100000L);
        cardRequestDTO.setStatus(CardStatus.ACTIVE);
        cardRequestDTO.setCurrency(Currency.UZS);
        cardRequestDTO.setUserId(1L);

        // Mocking behavior
        when(idempotencyRecordRepository.findById(idempotencyKey)).thenReturn(Optional.empty());
        when(cardRepository.findActiveCardByUserId(cardRequestDTO.getUserId())).thenReturn(0); // 0 active cards
        Card card = new Card();
        when(cardMapper.toEntity(cardRequestDTO)).thenReturn(card); // Mock toEntity
        when(cardRepository.save(card)).thenReturn(card);          // Mock save
        when(cardMapper.toDto(card)).thenReturn(new CardResponseDTO());

        // Act
        DataDTO<CardResponseDTO> responseDTODataDTO = cardService.createCard(idempotencyKey, cardRequestDTO);

        // Assert
        assertNotNull(responseDTODataDTO);
        assertNotNull(responseDTODataDTO.getData());
        verify(cardRepository).save(card); // Ensure save was called with correct card
    }




}
