package ru.ylab.repository;

import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;

import java.util.ArrayList;

public interface PersonRepository {
    /**
     * Получить пользователя для профиля по данным регистрации
     *
     * @param email    адрес электронной почты
     * @param password пароль
     * @return PersonDto dto пользователя для профиля
     */
    PersonDto getPersonDto(String email, String password);

    /**
     * Получить пользователя для профиля по id пользователя
     *
     * @param personId id пользователя для профиля
     * @return PersonDto dto пользователя для профиля
     */
    PersonDto getPersonDtoById(Long personId);

    /**
     * Создание профиля нового пользователя
     *
     * @param userAuthDto dto пользователя для авторизации
     * @return PersonDto dto пользователя для профиля
     */
    PersonDto createPerson(UserAuthDto userAuthDto);

    /**
     * Редактирование данных пользователя для профиля
     *
     * @param personDto dto пользователя для профиля
     * @return PersonDto dto пользователя для профиля
     */
    PersonDto updatePerson(PersonDto personDto);

    /**
     * Удаление пользователя по id пользователя
     *
     * @param personDto dto пользователя для профиля
     */
    void deletePerson(PersonDto personDto);

    /**
     * Получение администратором списка пользователей
     *
     * @param personDto dto администратора
     * @return ArrayList<PersonDto> список пользователей
     */
    ArrayList<PersonDto> getAllPersonDtos(PersonDto personDto);
}
