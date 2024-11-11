package ru.ylab.controller;

import lombok.NoArgsConstructor;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.ScannerService;

@NoArgsConstructor
public class AccountController {
    private final ScannerService scannerService = new ScannerService();

    public void account(PersonDto person) {
        AuthController authController = new AuthController();
        UserController userController = new UserController(person);
        HabitsController habitsController = new HabitsController(person);
        TrackingHabitsController trackingHabitsController =
                new TrackingHabitsController(person);
        StatisticsController statisticsController = new StatisticsController(person);

        System.out.println("Добро пожаловать в личный кабинет пользователь: " + person);
        switch (scannerService.menuAccount()) {
            case "0" -> {
                System.out.println("Вы вышли из личного кабинета");
                authController.start();
            }
            case "1" -> userController.user();
            case "2" -> habitsController.habits();
            case "3" -> trackingHabitsController.tracking();
            case "4" -> statisticsController.statistics();
            default -> authController.start();
        }
    }
}
