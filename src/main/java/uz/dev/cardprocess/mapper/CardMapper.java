package uz.dev.cardprocess.mapper;

import org.mapstruct.*;
import uz.dev.cardprocess.dto.CardRequestDTO;
import uz.dev.cardprocess.entity.Card;
import uz.dev.cardprocess.dto.CardResponseDTO;
import uz.dev.cardprocess.repository.UserRepository;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CardMapper {
    @Mapping(source = "userId", target = "user")
    Card toEntity(CardResponseDTO car);

    @Mapping(target = "user", source = "userId")
    Card toEntity(CardRequestDTO cardRequestDTO, @Context UserRepository userRepository);


    @Mapping(source = "user", target = "userId")
    CardResponseDTO toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    Card partialUpdate(CardResponseDTO car, @MappingTarget Card card);
}