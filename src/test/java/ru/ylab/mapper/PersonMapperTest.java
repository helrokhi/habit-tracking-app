package ru.ylab.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;

class PersonMapperTest {
    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);
    @Test
    void userDtoToPersonDtoForCreate() {
        UserAuthDto userAuthDto = UserAuthDto.builder().build();
        userAuthDto.setEmail("thuel@yahoo.com");
        userAuthDto.setPassword("00000000");

        PersonDto personDto = personMapper.userDtoToPersonDtoForCreate(userAuthDto);
        Assertions.assertEquals(userAuthDto.getEmail(), personDto.getEmail());
        Assertions.assertEquals(userAuthDto.getPassword(), personDto.getPassword());
        Assertions.assertEquals("John Doe", personDto.getName());
        Assertions.assertFalse(personDto.getIsBlocked());
    }
}