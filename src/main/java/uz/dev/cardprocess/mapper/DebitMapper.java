package uz.dev.cardprocess.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.dev.cardprocess.dto.DebitResponseDTO;
import uz.dev.cardprocess.entity.Transaction;

@Mapper(componentModel = "spring")
public interface DebitMapper {
    @Mapping(source = "card.id", target = "cardId")
    DebitResponseDTO toDebitResponseDto(Transaction transaction);
}
