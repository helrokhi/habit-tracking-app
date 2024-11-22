package ru.ylab.repository;

import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;

public interface UserRepository {
    /**
     * Получить данные пользователя по данным регистрации
     *
     * @param email    адрес электронной почты
     * @param password пароль
     * @return UserAuthDto dto пользователя для авторизации
     */
    UserAuthDto getUserAuthDto(String email, String password);

    /**
     * Получить данные пользователя по адресу электронной почты
     *
     * @param email адрес электронной почты
     * @return UserAuthDto dto пользователя для авторизации
     */
    UserAuthDto getUserAuthDtoByEmail(String email);

    /**
     * Получить данные пользователя по id
     *
     * @param userId id пользователя для авторизации
     * @return UserAuthDto dto пользователя для авторизации
     */
    UserAuthDto getUserAuthDtoById(Long userId);

    /**
     * Создание нового пользователя
     *
     * @param regUser данные пользователя для регистрации
     * @return UserAuthDto dto пользователя для авторизации
     */
    UserAuthDto createUser(RegUser regUser);

    /**
     * Редактировать профиль пользователя
     *
     * @param userAuthDto dto пользователя для авторизации
     * @return UserAuthDto dto пользователя для авторизации
     */
    UserAuthDto updateUser(UserAuthDto userAuthDto);
}
