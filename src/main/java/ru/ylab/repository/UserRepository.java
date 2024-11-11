package ru.ylab.repository;

import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;

public interface UserRepository {
    UserAuthDto getUserAuthDto(String email, String password);

    UserAuthDto getUserAuthDtoByEmail(String email);

    UserAuthDto getUserAuthDtoById(Long userId);

    UserAuthDto createUser(RegUser regUser);

    UserAuthDto updateUserEmail(Long userId, String email);

    UserAuthDto updateUserPassword(Long userId, String name);

    UserAuthDto updateUser(UserAuthDto userAuthDto);
}
