package ru.ylab.service;

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

    public void updateName() {
        System.out.println("Изменение имени " + person.getName() + "\n" +
                "Добавить новое имя");
        String query = scanner.nextLine().trim();
        person.setName(query);
        System.out.println("имя  пользователя изменено " + person);
    }

    public void updateEmail() {
        System.out.println("Изменение email " + person + "\n" +
                "Добавить новый email");
        String query = scanner.nextLine().trim();
        person.getUser().setEmail(query);
        System.out.println("Email пользователя изменен " + person);
    }

    public void updatePassword() {
        System.out.println("Изменение пароль " + person + "\n" +
                "Добавить новый пароль");
        String query = scanner.nextLine().trim();
        person.getUser().setPassword(query);
        System.out.println("Пароль пользователя изменен " + person);
    }

    public void delete() {
        database.removePerson(person);
        database.removeUser(person.getUser());
        System.out.println("Пользователь удален " + person);
    }
}
