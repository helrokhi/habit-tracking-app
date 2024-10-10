package ru.ylab.controller;

import ru.ylab.dto.Database;
import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;

import java.util.ArrayList;
import java.util.Scanner;

public class HabitsController {
    private final Scanner scanner;
    private final Person person;
    private final Database database;

    public HabitsController(Person person, Database database) {
        scanner = new Scanner(System.in);
        this.person = person;
        this.database = database;
    }

    public void habits() {
        System.out.println("\t\tУправление привычками " + person);
        while (true) {
            System.out.println(
                    "\t\t\tПросмотр привычек нажмите 1\n" +
                            "\t\t\tРабота с привычкой нажмите 2\n" +
                            "\t\t\tВернуться в личный кабинет нажмите 0");
            String query = scanner.nextLine().trim();

            switch (query) {
                case "1": {
                    ArrayList<Habit> habits = person.getHabits();
                    if (habits.isEmpty()) {
                        System.out.println("Нет привычек у пользователя " + person);
                        new HabitController(person, database).habit();
                        break;
                    } else {
                        getAllHabits();
                        habits();
                    }
                    break;
                }
                case "2": {
                    new HabitController(person, database).habit();
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

    private void getAllHabits() {
        ArrayList<Habit> habits = person.getHabits();
        System.out.println("\t\tПривычки " + person);
        for (int i = 0; i < habits.size(); i++) {
            System.out.println("\t\t" + i + ": " + habits.get(i));
        }
    }
}
