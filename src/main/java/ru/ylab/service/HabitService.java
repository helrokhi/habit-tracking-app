package ru.ylab.service;

import ru.ylab.dto.Habit;
import ru.ylab.dto.Person;

import java.util.Scanner;

public class HabitService {
    private final Scanner scanner;
    private final Person person;

    public HabitService(Person person) {
        scanner = new Scanner(System.in);
        this.person = person;
    }

    public Habit create() {
        System.out.println("Напишите название привычки");
        String title = scanner.nextLine().trim();
        System.out.println("Напишите описание привычки");
        String text = scanner.nextLine().trim();
        System.out.println("Напишите частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), ежемесячно (monthly), " +
                "по умолчанию единожды (once)) ");
        String frequency = scanner.nextLine().trim();
        Habit habit = new Habit(title, text, frequency);

        person.addHabit(habit);
        return habit;
    }

    public Habit update() {
        System.out.println("Укажите индекс привычки");
        String index = scanner.nextLine().trim();
        Habit habit = null;
        try {
            int i  = Integer.parseInt(index);
            habit = person.getHabits().get(i);

        } catch (Exception  ex) {
            System.out.println("Такой привычки нет. Повторите попытку");
            update();
        }
        return habit;
    }

    public void delete() {
        System.out.println("Укажите индекс привычки");
        String index = scanner.nextLine().trim();
        try {
            int i  = Integer.parseInt(index);
            person.getHabits().remove(i);
            System.out.println("Привычка удалена");
        } catch (Exception  ex) {
            System.out.println("Такой привычки нет. Повторите попытку");
            delete();
        }
    }
}
