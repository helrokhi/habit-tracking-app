package ru.ylab.controller;

import lombok.NoArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

@NoArgsConstructor
public class AuthController {
    private final ScannerService scannerService = new ScannerService();

    public void start() {
        login(scannerService.startMenu());
    }

    public void login(String query) {
        AuthService authService = new AuthService();
        UserService userService = new UserService();

        PersonDto personDto;
        while (true) {
            switch (query) {
                case "1" -> {
                    System.out.println("Регистрация пользователя");
                    RegUser regUser = scannerService.createRegUser();
                    if (regUser == null) {
                        System.out.println("Пользователя создать невозможно");
                        start();
                    }

                    UserAuthDto userAuthDto = userService.getUserByEmail(regUser);

                    if (userAuthDto.getId() == null) {
                        System.out.println("Создаем пользователя " + regUser);
                        UserAuthDto user = authService.userRegistration(regUser);
                        personDto = authService.personRegistration(regUser);
                        userService.account(personDto);
                    }
                    System.out.println("Пользователь с таким e-mail существует");
                    start();
                }
                case "2" -> {
                    System.out.println("Авторизация пользователя");
                    RegUser user = scannerService.createRegUser();

                    UserAuthDto userAuthDto = userService.getUserByEmail(user);

                    if (userAuthDto != null && user.getPassword().equals(userAuthDto.getPassword())) {
                        personDto = authService.personAuthorization(user);
                        userService.account(personDto);
                    }

                    System.out.println("Такого пользователя не существует");
                    start();
                }
                default -> start();
            }
        }
    }
}
