package ru.ylab.service;

import ru.ylab.dto.Habit;

import java.util.Scanner;

public class HabitUpdateService {
    private final Scanner scanner;
    private final Habit habit;

    public HabitUpdateService(Habit habit) {
        scanner = new Scanner(System.in);
        this.habit = habit;
    }

    public void updateTitle() {
        System.out.println("Напишите новое название привычки");
        String title = scanner.nextLine().trim();
        habit.setTitle(title);
    }

    public void updateText() {
        System.out.println("Напишите новое описание привычки");
        String text = scanner.nextLine().trim();
        habit.setText(text);
    }

    public void updateFrequency() {
        System.out.println("Напишите новую частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), ежемесячно (monthly), " +
                "по умолчанию единожды (once)) ");
        String frequency = scanner.nextLine().trim();
        habit.setFrequency(frequency);
    }

    public void updateStatus() {

    }

}
