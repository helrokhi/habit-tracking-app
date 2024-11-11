package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

import java.util.List;

@AllArgsConstructor
public class ViewHabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void view() {
        HabitsController habitsController = new HabitsController(person);

        HabitService habitService = new HabitService();
        List<HabitDto> habits = habitService.getHabitsWithStatus(person.getId());

        System.out.println("Просмотр привычек:");
        switch (scannerService.menuViewHabits()) {
            case "1" -> {
                System.out.println("Список всех привычек пользователя");
                habitService.toStringListHabits(habits);
                view();
            }
            case "SORT" -> {
                System.out.println("Список всех привычек пользователя," +
                        "отсортированный по дате создания");
                List<HabitDto> sortHabits = habitService.getSortHabitsByTime(habits);
                habitService.toStringListHabits(sortHabits);
                view();
            }
            case "EXECUTE" -> {
                System.out.println("Список всех привычек пользователя со статусом «Выполнена»");
                List<HabitDto> executeHabits = habitService.getHabitsByStatus(habits, true);
                habitService.toStringListHabits(executeHabits);
                view();
            }
            case "NO" -> {
                System.out.println("Список всех привычек пользователя со статусом «Не выполнена»");
                List<HabitDto> executeHabits = habitService.getHabitsByStatus(habits, false);
                habitService.toStringListHabits(executeHabits);
                view();
            }
            default -> habitsController.habits();
        }
    }
}
