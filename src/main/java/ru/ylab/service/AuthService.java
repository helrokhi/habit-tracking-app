package ru.ylab.service;

import lombok.AllArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.repository.PersonRepository;
import ru.ylab.repository.UserRepository;
import ru.ylab.repository.impl.PersonRepositoryImpl;
import ru.ylab.repository.impl.UserRepositoryImpl;

@AllArgsConstructor
public class AuthService {
    /**
     * Добавление нового профиля пользователя в базу данных
     * используя email и password
     */
    public PersonDto personRegistration(RegUser regUser) {
        UserAuthDto userAuthDto = userAuthorization(regUser);
        PersonRepository personRepository = new PersonRepositoryImpl();
        return (regUser != null) ? personRepository.createPerson(userAuthDto) : null;
    }

    /**
     * Получение профиля пользователя из базы данных
     * используя email и password
     */
    public PersonDto personAuthorization(RegUser user) {
        PersonRepository personRepository = new PersonRepositoryImpl();
        return personRepository.getPersonDto(user.getEmail(), user.getPassword());
    }

    /**
     * Получение данных о пользователе из базы данных
     * используя email и password
     */
    public UserAuthDto userAuthorization(RegUser user) {
        UserRepository userRepository = new UserRepositoryImpl();
        return (user != null) ? userRepository.getUserAuthDto(user.getEmail(), user.getPassword()) : null;
    }

    /**
     * Добавление нового пользователя в базу данных
     * используя email и password
     */
    public UserAuthDto userRegistration(RegUser user) {
        UserRepository userRepository = new UserRepositoryImpl();
        return (user != null) ? userRepository.createUser(user) : null;
    }
}
