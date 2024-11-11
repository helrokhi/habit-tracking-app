package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

import java.util.List;

@AllArgsConstructor
public class TrackingHabitsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void tracking() {
        System.out.println("Отслеживание выполнения привычек пользователем " + person);
        UserService userService = new UserService();
        HabitService habitService = new HabitService();
        StatusService statusService = new StatusService();

        List<HabitDto> habits = habitService.getHabitsWithStatus(person.getId());

        switch (scannerService.trackingHabitsMenu()) {
            case "1" -> {
                List<HabitDto> habitsByStatusFalse = habitService.getHabitsByStatus(habits, false);
                System.out.println("Отметить выполнение привычки");

                if (habitsByStatusFalse.isEmpty()) {
                    System.out.println("Отметить выполнение невозможно список привычек пуст");
                    tracking();
                }
                habitService.toStringListHabits(habitsByStatusFalse);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.findHabitByIndex(Long.valueOf(index));

                statusService.createStatus(habit.getId());
                System.out.println("Выполнение отмечено " + habit);
                tracking();
            }
            case "2" -> {
                System.out.println("Посмотреть историю выполнения привычки");
                if (habits.isEmpty()) {
                    System.out.println("Посмотреть историю выполнения привычки" +
                            " невозможно список привычек пуст");
                    tracking();
                }
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.findHabitByIndex(Long.valueOf(index));
                System.out.println("История привычки " + habit);
                System.out.println(statusService.getHistory(habit.getId()));
                tracking();
            }
            case "3" -> {
                System.out.println("Статистика выполнения привычки " +
                        "за указанный период (день, неделя, месяц)");
                if (habits.isEmpty()) {
                    System.out.println("Посмотреть статистику выполнения привычки" +
                            " невозможно список привычек пуст");
                    tracking();
                }
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.findHabitByIndex(Long.valueOf(index));
                HabitFulfillmentStatisticsController habitFulfillmentStatisticsController =
                        new HabitFulfillmentStatisticsController(person);
                habitFulfillmentStatisticsController.fulfillment(habit);
            }
            default -> userService.account(person);
        }
    }
}
