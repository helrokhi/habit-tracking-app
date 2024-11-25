package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.NotificationDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.service.HabitService;
import ru.ylab.service.NotificationsService;
import ru.ylab.service.ScannerService;

import java.util.List;

@AllArgsConstructor
public class NotificationsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void notices() {
        HabitService habitService = new HabitService();
        NotificationsService notificationsService = new NotificationsService();
        System.out.println("Уведомления пользователя " + person);
        List<HabitDto> habits = habitService.getHabitsWithStatus(person.getId());
        List<HabitDto> habitsByStatusFalse = habitService.getHabitsByStatus(habits, false);
        List<NotificationDto> notifications = notificationsService.getNotifications(person.getId());
        for (HabitDto habitDto : habitsByStatusFalse) {
            notifications.add(notificationsService.createNotification(habitDto, person));
        }

    }
}
