package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.service.HabitService;
import ru.ylab.service.ScannerService;

import java.util.ArrayList;

@AllArgsConstructor
public class HabitController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void habit() {
        HabitService habitService = new HabitService();
        System.out.println("Работа с привычкой пользователя " + person);
        ArrayList<HabitDto> habits = habitService.getHabits(person.getId());
        switch (scannerService.habitManagementMenu()) {
            case "1" -> {
                System.out.println("Создание новой привычки");
                RegHabit regHabit = scannerService.createRegHabit();
                HabitDto habitDto = habitService.create(person, regHabit);
                System.out.println("Новая привычка " + habitDto);
                habit();
            }
            case "2" -> {
                if (habits.isEmpty()) {
                    System.out.println("Обновление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Обновление привычки");
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.findHabitByIndex(Long.valueOf(index));
                new HabitUpdateController(habit, person).habitUpdate();
                System.out.println("Обновлена привычка " + habit);
                habit();
            }
            case "delete" -> {
                if (habits.isEmpty()) {
                    System.out.println("Удаление невозможно список привычек пуст");
                    habit();
                }
                System.out.println("Удаление привычки");
                habitService.toStringListHabits(habits);
                String index = scannerService.createIndexHabit();
                HabitDto habit = habitService.findHabitByIndex(Long.valueOf(index));
                habitService.delete(habit);
                habit();
            }
            default -> new HabitsController(person).habits();

        }
    }
}
