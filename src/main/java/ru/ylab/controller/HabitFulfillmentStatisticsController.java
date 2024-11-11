package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;
import ru.ylab.dto.enums.PeriodType;

import java.time.LocalDate;

@AllArgsConstructor
public class HabitFulfillmentStatisticsController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void fulfillment(HabitDto habitDto) {
        TrackingHabitsController trackingHabitsController =
                new TrackingHabitsController(person);
        StatisticsService statisticsService = new StatisticsService();

        System.out.println("Статистика выполнения привычки " + habitDto);
        switch (scannerService.habitFulfillmentStatisticsMenu()) {
            case "DAY" -> {
                System.out.println("Статистика выполнения привычки " + habitDto + " за день");
                StatisticDto statisticDto = statisticsService.getStatistics(habitDto, PeriodType.DAY, LocalDate.now());
                statisticsService.toStringStatistics(statisticDto);

            }
            case "WEEK" -> {
                System.out.println("Статистика выполнения привычки " + habitDto + " за неделю");
                StatisticDto statisticDto = statisticsService.getStatistics(habitDto,PeriodType.WEEK, LocalDate.now());
                statisticsService.toStringStatistics(statisticDto);

            }
            case "MONTH" -> {
                System.out.println("Статистика выполнения привычки " + habitDto + " за месяц");
                StatisticDto statisticDto = statisticsService.getStatistics(habitDto,PeriodType.MONTH,LocalDate.now());
                statisticsService.toStringStatistics(statisticDto);
            }
            default -> trackingHabitsController.tracking();
        }
    }

}
