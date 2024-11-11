package ru.ylab.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "name", constant = "John Doe")
    @Mapping(target = "isBlocked",  constant = "false")
    PersonDto userDtoToPersonDtoForCreate(UserAuthDto userAuthDto);
}
