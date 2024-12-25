package uz.dev.cardprocess.mapper;

import org.mapstruct.*;
import uz.dev.cardprocess.dto.CreditResponseDTO;
import uz.dev.cardprocess.entity.Transaction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
    @Mapping(source = "cardId", target = "card.id")
    Transaction toEntity(CreditResponseDTO creditResponseDTO);

    @Mapping(source = "card.id", target = "cardId")
    CreditResponseDTO toDto(Transaction transaction);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "cardId", target = "card.id")
    Transaction partialUpdate(CreditResponseDTO creditResponseDTO, @MappingTarget Transaction transaction);
}