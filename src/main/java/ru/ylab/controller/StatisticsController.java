package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

@AllArgsConstructor
public class StatisticsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void statistics() {
        System.out.println("Статистика и аналитика пользователя " + person);

        UserService userService = new UserService();
        switch (scannerService.statisticsMenu()) {
            case "1" -> {
                System.out.println("Подсчет текущих серий выполнения привычек\n");
                TrackingHabitsService trackingHabitsService =
                        new TrackingHabitsService();
                TrackingDto trackingDto = trackingHabitsService.streak(person.getId());
                trackingHabitsService.toStringStreak(trackingDto);
                statistics();
            }
            case "2" -> {
                System.out.println("Процент успешного выполнения привычек за определенный период");
                //* какой-то код
                statistics();
            }
            case "3" -> {
                System.out.println("Формирование отчета для пользователя по прогрессу выполнения");
                //* какой-то код
                statistics();
            }

            default -> userService.account(person);
        }
    }
}
