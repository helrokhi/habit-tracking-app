package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

@AllArgsConstructor
public class UserController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void user() {
        System.out.println("\t\tУправление пользователем " + person);

        AuthController authController = new AuthController();

        UserService userService = new UserService();
        PersonService personService = new PersonService();

        switch (scannerService.userManagementMenu()) {
            case "1" -> {
                String name = scannerService.updateNamePerson(person);
                person.setName(name);
                personService.update(person);
                user();
            }
            case "2" -> {
                String email = scannerService.updateEmail(person);
                person.setEmail(email);
                personService.update(person);
                user();
            }
            case "3" -> {
                String password = scannerService.updatePassword(person);
                person.setPassword(password);
                personService.update(person);
                user();
            }
            case "DELETE" -> {
                personService.delete(person);
                authController.start();
            }
            default -> userService.account(person);
        }
    }
}
