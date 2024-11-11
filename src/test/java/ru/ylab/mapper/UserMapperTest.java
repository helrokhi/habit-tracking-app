package ru.ylab.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    @Test
    void regToDtoForCreate() {
        RegUser regUser = new RegUser("thuel@yahoo.com", "00000000");

        UserAuthDto userAuthDto = userMapper.regToDtoForCreate(regUser);
        Assertions.assertEquals(regUser.getEmail(), userAuthDto.getEmail());
        Assertions.assertEquals(regUser.getPassword(), userAuthDto.getPassword());
        Assertions.assertEquals("USER", userAuthDto.getRole().name());
    }
}