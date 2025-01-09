package uz.dev.cardprocess.mapper;

import org.mapstruct.*;
import uz.dev.cardprocess.dto.CreditResponseDTO;
import uz.dev.cardprocess.entity.Transaction;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    @Mapping(target = "cardId" ,source = "card.id")
    CreditResponseDTO toDto(Transaction transaction);


}