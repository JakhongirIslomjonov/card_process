package uz.dev.cardprocess.mapper;

import org.mapstruct.*;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.repository.UserRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardRequestMapper {

    @Mapping(target = "user", source = "userId")
    Card toEntity(CardRequestDTO cardRequestDTO, @Context UserRepository userRepository);


    CardRequestDTO toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Card partialUpdate(CardRequestDTO cardRequestDTO, @MappingTarget Card card);
}