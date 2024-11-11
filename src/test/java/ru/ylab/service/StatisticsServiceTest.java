package ru.ylab.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.StatisticDto;
import ru.ylab.dto.enums.PeriodType;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {
    @InjectMocks
    private HabitService habitService;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void getStatisticsMonthlyWhenFrequencyDaily() {
        HabitDto habitDto = habitService.findHabitByIndex(1000L);
        PeriodType periodType = PeriodType.MONTH;

        LocalDate endDate = LocalDate.parse("2023-11-28");

        LocalDate startDate = habitDto.getTime().toLocalDate();

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(18, statisticDto.getCountPlan());
        Assertions.assertEquals(10, statisticDto.getCountFact());
        Assertions.assertEquals("", statisticDto.getMessage());
    }

    @Test
    void getStatisticsForWeekWhenFrequencyDaily() {
        HabitDto habitDto = habitService.findHabitByIndex(1000L);
        PeriodType periodType = PeriodType.WEEK;

        LocalDate endDate = LocalDate.parse("2023-11-28");

        LocalDate startDate = endDate.minusDays(7);

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(7, statisticDto.getCountPlan());
        Assertions.assertEquals(3, statisticDto.getCountFact());
        Assertions.assertEquals("", statisticDto.getMessage());
    }

    @Test
    void getStatisticsPerDayWhenFrequencyDaily() {
        HabitDto habitDto = habitService.findHabitByIndex(1000L);
        PeriodType periodType = PeriodType.DAY;

        LocalDate endDate = LocalDate.parse("2023-11-28");

        LocalDate startDate = endDate.minusDays(1);

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(1, statisticDto.getCountPlan());
        Assertions.assertEquals(1, statisticDto.getCountFact());
        Assertions.assertEquals("", statisticDto.getMessage());
    }

    @Test
    void getStatisticsMonthlyWhenFrequencyWeekly() {
        HabitDto habitDto = habitService.findHabitByIndex(1004L);
        PeriodType periodType = PeriodType.MONTH;

        LocalDate endDate = LocalDate.parse("2023-12-19");

        LocalDate startDate = endDate.minusDays(periodType.getId());

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(4, statisticDto.getCountPlan());
        Assertions.assertEquals(0, statisticDto.getCountFact());
        Assertions.assertEquals("", statisticDto.getMessage());
    }

    @Test
    void getStatisticsForWeekWhenFrequencyWeekly() {
        HabitDto habitDto = habitService.findHabitByIndex(1004L);
        PeriodType periodType = PeriodType.WEEK;

        LocalDate endDate = LocalDate.parse("2023-12-28");

        LocalDate startDate = endDate.minusDays(periodType.getId());

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(1, statisticDto.getCountPlan());
        Assertions.assertEquals(0, statisticDto.getCountFact());
        Assertions.assertEquals("", statisticDto.getMessage());
    }

    @Test
    void getStatisticsPerDayWhenFrequencyWeekly() {
        HabitDto habitDto = habitService.findHabitByIndex(1004L);
        PeriodType periodType = PeriodType.DAY;

        LocalDate endDate = LocalDate.parse("2023-12-28");

        LocalDate startDate = endDate.minusDays(1);

        StatisticDto statisticDto = statisticsService.getStatistics(habitDto, periodType, endDate);
        statisticsService.toStringStatistics(statisticDto);

        Assertions.assertEquals(periodType, statisticDto.getPeriodType());
        Assertions.assertEquals(startDate, statisticDto.getStartDate());
        Assertions.assertEquals(endDate, statisticDto.getEndDate());
        Assertions.assertEquals(0, statisticDto.getCountPlan());
        Assertions.assertEquals(0, statisticDto.getCountFact());
        Assertions.assertEquals("Для данной привычки нет статистики за день", statisticDto.getMessage());
    }
}