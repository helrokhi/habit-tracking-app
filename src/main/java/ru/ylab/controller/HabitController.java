package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.service.HabitService;

import java.util.Scanner;

public class HabitController {
    private final Scanner scanner;
    private final Person person;
    private final Database database;

    public HabitController(Person person, Database database) {
        scanner = new Scanner(System.in);
        this.person = person;
        this.database = database;
    }

    public void habit() {
        System.out.println("\t\tРабота с привычкой пользователя" + person);
        while (true) {
            System.out.println(
                    "\t\t\tСоздание привычки нажмите 1\n" +
                            "\t\t\tРедактирование привычки: нажмите 2\n" +
                            "\t\t\tУдаление привычки: напишите delete\n" +
                            "\t\t\tВернуться в предыдущее меню нажмите 0");
            String query = scanner.nextLine().trim();

            switch (query) {
                case "1": {
                    System.out.println("Создание новой привычки");
                    Habit habit = new HabitService(person).create();
                    System.out.println("Новая привычка " + habit);
                    break;
                }
                case "2": {
                    Habit habit = new HabitService(person).update();
                    new HabitUpdateController(habit, person, database).habitUpdate();
                    System.out.println("Обновлена привычка " + habit);
                    break;
                }
                case "delete": {
                    System.out.println("Удаление привычки");
                    new HabitService(person).delete();
                    break;
                }
                case "0": {
                    new HabitsController(person, database).habits();
                    break;
                }
                default:
                    new HabitsController(person, database).habits();
                    break;
            }
        }
    }
}
