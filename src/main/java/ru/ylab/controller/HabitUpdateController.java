package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;
import ru.ylab.dto.enums.Frequency;

@AllArgsConstructor
public class HabitUpdateController {
    private HabitDto habit;
    private PersonDto person;

    private final ScannerService scannerService = new ScannerService();

    public void habitUpdate() {
        HabitService habitService = new HabitService();
        System.out.println("Редактирование привычки " + habit);
        switch (scannerService.menuUpdateHabit()) {
            case "1" -> {
                System.out.println("Изменение названия привычки " + habit);
                String title = scannerService.updateTitleHabit();
                habit.setTitle(title);
                habitService.update(habit);
                habitUpdate();
            }
            case "2" -> {
                System.out.println("Изменение описания привычки " + habit);
                String text = scannerService.updateTextHabit();
                habit.setText(text);
                habitService.update(habit);
                habitUpdate();
            }
            case "3" -> {
                System.out.println("Изменение частоты привычки " + habit);
                String frequency = scannerService.updateFrequencyHabit();
                habit.setFrequency(Frequency.valueOf(frequency));
                habitService.update(habit);
                habitUpdate();
            }
            default -> new HabitController(person).habit();
        }
    }
}
