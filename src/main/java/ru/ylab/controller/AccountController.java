package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.HabitService;
import ru.ylab.service.OutService;
import ru.ylab.service.UserService;

import java.util.Scanner;

public class AccountController {

    private final Person person;
    private final Scanner scanner;
    private final Database database;

    public AccountController(Person person, Database database) {
        scanner = new Scanner(System.in);
        this.person = person;
        this.database = database;
    }

    public void account() {
        System.out.println(
                "Добро пожаловать в личный кабинет пользователь: " +
                        person);
        System.out.println(
                "\tУправление пользователем нажмите 1\n" +
                        "\tУправление привычками нажмите 2\n" +
                        "\tВыйти из личного кабинета нажмите 0");

        String query = scanner.nextLine().trim();
        switch (query) {
            case "0": {
                new OutService().management();
                new AuthController(database).start();
                break;
            }
            case "1":
                new UserService(person, database).management();
                break;
            case "2":
                new HabitService(person).management();
                break;
            default: {
                new AuthController(database).start();
                break;
            }
        }
    }
}
