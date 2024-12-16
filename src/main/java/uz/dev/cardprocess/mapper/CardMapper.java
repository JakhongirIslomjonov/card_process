package uz.dev.cardprocess.mapper;

import org.mapstruct.*;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.dto.CardResponseDTO;
import uz.dev.cardprocess.repository.UserRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = UserRepository.class)
public interface CardMapper {

    Card toEntity(CardRequestDTO cardRequestDTO);

    CardResponseDTO toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardResponseDTO car, @MappingTarget Card card);
}
