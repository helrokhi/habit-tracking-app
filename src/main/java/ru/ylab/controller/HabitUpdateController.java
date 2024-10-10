package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;
import ru.ylab.service.HabitService;
import ru.ylab.service.HabitUpdateService;
import ru.ylab.service.UserService;

import java.util.Scanner;

public class HabitUpdateController {
    private final Scanner scanner;

    private final Habit habit;

    private final Person person;
    private final Database database;

    public HabitUpdateController(Habit habit, Person person, Database database) {
        scanner = new Scanner(System.in);
        this.habit = habit;
        this.person = person;
        this.database = database;
    }

    public void habitUpdate() {
        System.out.println("\t\tРедактирование привычки " + habit);
        for (; ; ) {
            System.out.println(
                    "\t\t\tИзменить название нажмите 1\n" +
                            "\t\t\tИзменить описание нажмите 2\n" +
                            "\t\t\tИзменить частоту нажмите 3\n" +
                            "\t\t\tИзменить статус нажмите 4\n" +
                            "\t\t\tВернуться в личный кабинет нажмите 0");
            String query = scanner.nextLine().trim();

            switch (query) {
                case "1": {
                    System.out.println("Изменение названия привычки " + habit);
                    new HabitUpdateService(habit).updateTitle();
                    break;
                }
                case "2": {
                    System.out.println("Изменение описания привычки " + habit);
                    new HabitUpdateService(habit).updateText();
                    break;
                }
                case "3": {
                    System.out.println("Изменение частоты привычки " + habit);
                    new HabitUpdateService(habit).updateFrequency();
                    break;
                }
                case "4": {
                    System.out.println("Обновление статуса привычки " + habit);
                    new HabitUpdateService(habit).updateStatus();
                    break;
                }
                case "0": {
                    new HabitController(person, database).habit();
                    break;
                }
                default:
                    new HabitController(person, database).habit();
                    break;
            }
        }
    }
}
