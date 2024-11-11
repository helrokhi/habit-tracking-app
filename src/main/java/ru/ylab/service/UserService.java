package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.controller.AccountController;
import ru.ylab.controller.AdminAccountController;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.dto.enums.Role;
import ru.ylab.repository.UserRepository;
import ru.ylab.repository.impl.UserRepositoryImpl;

@NoArgsConstructor
public class UserService {
    /**
     * Редактирование профиля пользователя
     * Обновление данные пользователя
     *
     * @param userAuthDto данные пользователя для обновления
     */
    public void update(UserAuthDto userAuthDto) {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.updateUser(userAuthDto);
        System.out.println("Данные пользователя обновлены " + userAuthDto);
    }

    /**
     * Получить пользователя по email
     *
     * @param regUser данные пользователя email и password
     * @return dto пользователя
     */
    public UserAuthDto getUserByEmail(RegUser regUser) {
        UserRepository userRepository = new UserRepositoryImpl();
        return (regUser != null) ? userRepository.getUserAuthDtoByEmail(regUser.getEmail()) : null;
    }

    /**
     * Получить пользователя по его id
     *
     * @param userId id пользователя
     * @return dto пользователя
     */
    public UserAuthDto getUserById(Long userId) {
        UserRepository userRepository = new UserRepositoryImpl();
        return userRepository.getUserAuthDtoById(userId);
    }

    /**
     * Вызов меню пользователя в зависимости от установленной ему роли
     *
     * @param person данные пользователя
     */
    public void account(PersonDto person) {
        AccountController accountController = new AccountController();
        AdminAccountController adminAccountController = new AdminAccountController();

        UserRepository userRepository = new UserRepositoryImpl();
        String email = person.getEmail();
        UserAuthDto userAuthDto = userRepository.getUserAuthDtoByEmail(email);
        if ((userAuthDto.getRole().equals(Role.ADMIN))) {
            adminAccountController.admin(person);
        } else {
            accountController.account(person);
        }
    }
}
