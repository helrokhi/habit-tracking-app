package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.service.UserService;

import java.util.Scanner;

public class UserController {
    private final Scanner scanner;
    private final Person person;
    private final Database database;

    public UserController(Person person, Database database) {
        scanner = new Scanner(System.in);
        this.person = person;
        this.database = database;
    }

    public void user() {
        System.out.println("\t\tУправление пользователем " + person);
        while (true) {
            System.out.println(
                    "\t\t\tИзменить имя нажмите 1\n" +
                            "\t\t\tИзменить email нажмите 2\n" +
                            "\t\t\tИзменить пароль нажмите 3\n" +
                            "\t\t\tУдалить аккаунт напишите delete\n" +
                            "\t\t\tВернуться в личный кабинет нажмите 0");
            String query = scanner.nextLine().trim();

            switch (query) {
                case "1": {
                    new UserService(person, database).updateName();
                    break;
                }
                case "2": {
                    new UserService(person, database).updateEmail();
                    break;
                }
                case "3": {
                    new UserService(person, database).updatePassword();
                    break;
                }
                case "delete": {
                    new UserService(person, database).delete();
                    new AuthController(database).start();
                    break;
                }
                case "0": {
                    new AccountController(person, database).account();
                    break;
                }
                default:
                    new AccountController(person, database).account();
                    break;
            }
        }
    }
}
