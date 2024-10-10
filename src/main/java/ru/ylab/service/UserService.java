package ru.ylab.service;

import ru.ylab.controller.AccountController;
import ru.ylab.controller.AuthController;
import ru.ylab.dto.Database;
import ru.ylab.dto.Person;

import java.util.Scanner;

public class UserService {
    private final Scanner scanner;
    private final Person person;
    private final Database database;

    public UserService(Person person, Database database) {
        scanner = new Scanner(System.in);
        this.person = person;
        this.database = database;
    }

    public void management() {
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
                    updateName();
                    break;
                }
                case "2": {
                    updateEmail();
                    break;
                }
                case "3": {
                    updatePassword();
                    break;
                }
                case "delete": {
                    delete();
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

    private void updateName() {
        System.out.println("Изменение имени " + person.getName() + "\n" +
                "Добавить новое имя");
        String query = scanner.nextLine().trim();
        person.setName(query);
        System.out.println("имя  пользователя изменено " + person);
    }

    private void updateEmail() {
        System.out.println("Изменение email " + person + "\n" +
                "Добавить новый email");
        String query = scanner.nextLine().trim();
        person.getUser().setEmail(query);
        System.out.println("Email пользователя изменен " + person);
    }

    private void updatePassword() {
        System.out.println("Изменение пароль " + person + "\n" +
                "Добавить новый пароль");
        String query = scanner.nextLine().trim();
        person.getUser().setPassword(query);
        System.out.println("Пароль пользователя изменен " + person);
    }

    private void delete() {
        database.removePerson(person);
        database.removeUser(person.getUser());
        System.out.println("Пользователь удален " + person);
    }
}
