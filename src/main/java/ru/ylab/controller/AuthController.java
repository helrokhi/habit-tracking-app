package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Person;
import ru.ylab.dto.User;

import java.util.Scanner;

public class AuthController {
    private final Scanner scanner;
    private final Database database;
    private final String EMAIL_MESSAGE = "Введите e-mail пользователя";
    private final String PASSWORD_MESSAGE = "Введите пароль пользователя";

    public AuthController(Database database) {
        scanner = new Scanner(System.in);
        this.database = database;
    }

    public void start() {
        System.out.println("Приложение для отслеживания привычек\n");

        System.out.println(
                "Для регистрации нового пользователь нажмите 1\n" +
                        "Для авторизации нажмите 2");

        String query = scanner.nextLine().trim();
        Person person = login(query);
    }

    public Person login(String query) {
        while (true) {
            switch (query) {
                case "1": {
                    System.out.println("Регистрация пользователя");
                    System.out.println(EMAIL_MESSAGE);
                    String email = scanner.nextLine().trim();
                    if (!database.isSuchUser(email)) {
                        System.out.println(PASSWORD_MESSAGE);
                        String password = scanner.nextLine().trim();
                        User user = new User(email, password);

                        return registration(user);
                    }
                    System.out.println("Пользователь с таким e-mail существует");
                    break;
                }
                case "2": {
                    System.out.println("Авторизация пользователя");
                    System.out.println(EMAIL_MESSAGE);
                    String email = scanner.nextLine().trim();
                    if (database.isSuchUser(email)) {
                        System.out.println(PASSWORD_MESSAGE);
                        String password = scanner.nextLine().trim();
                        User user = new User(email, password);

                        return authorization(user);
                    }
                    System.out.println("Пользователя с таким e-mail не существует");
                    query = "1";
                    break;
                }
                default: {
                    start();
                    break;
                }
            }
        }
    }

    private Person registration(User user) {
        database.addUser(user);
        Person person = new Person(user);
        database.addPerson(person);
        new AccountController(person, database).account();
        return person;
    }

    private Person authorization(User user) {
        Person person = database.getPerson(user);
        new AccountController(person, database).account();
        return person;
    }

    private boolean isValidEmail(String email) {
        String regexEmail = "^[a-z0-9._%+\\-]+@[a-z0-9.\\-]+(\\.[a-z]{2,}|\\.xn--[a-z0-9]+)$";
        return email.matches(regexEmail);
    }

    private boolean isValidPassword(String password) {
        String regexPassword = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
        return password.matches(regexPassword);
    }
}
