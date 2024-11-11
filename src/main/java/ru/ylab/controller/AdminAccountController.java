package ru.ylab.controller;

import lombok.NoArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.ScannerService;

@NoArgsConstructor
public class AdminAccountController {
    private final ScannerService scannerService = new ScannerService();

    public void admin(PersonDto person) {
        AuthController authController = new AuthController();
        UserController userController = new UserController(person);
        HabitsController habitsController = new HabitsController(person);
        AdministrationController administrationController = new AdministrationController(person);
        TrackingHabitsController trackingHabitsController =
                new TrackingHabitsController(person);
        StatisticsController statisticsController = new StatisticsController(person);

        System.out.println("Добро пожаловать в личный кабинет администратор: " + person);
        switch (scannerService.menuAdminAccount()) {
            case "0" -> {
                System.out.println("Вы вышли из личного кабинета");
                authController.start();
            }
            case "1" -> userController.user();
            case "2" -> habitsController.habits();
            case "3" -> trackingHabitsController.tracking();
            case "4" -> statisticsController.statistics();
            case "5" -> administrationController.administration();
            default -> authController.start();
        }
    }
}
