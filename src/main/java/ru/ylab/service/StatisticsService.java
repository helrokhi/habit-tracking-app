package ru.ylab.service;

import ru.ylab.dto.*;
import ru.ylab.dto.enums.*;
import ru.ylab.repository.StatusRepository;
import ru.ylab.repository.impl.StatusRepositoryImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class StatisticsService {
    public StatisticDto getStatistics(HabitDto habitDto, PeriodType periodType, LocalDate endDate) {
        StatusRepository statusRepository = new StatusRepositoryImpl();
        ArrayList<StatusDto> history = statusRepository.getHistory(habitDto.getId());
        LocalDate createDate = habitDto.getTime().toLocalDate();

        LocalDate startDate = getStartDate(endDate, createDate, periodType);

        int count = getNumberOfHabitRunsPerPeriod(history, startDate, endDate);
        int period = getPeriodForHabit(habitDto.getFrequency(), startDate, endDate, periodType);
        String message = "";

        if (period == 0) {
            message = "Для данной привычки нет статистики за день";
        }

        return StatisticDto.builder()
                .periodType(periodType)
                .frequency(habitDto.getFrequency())
                .startDate(startDate)
                .endDate(endDate)
                .countPlan(period)
                .countFact(count)
                .message(message)
                .build();
    }

    /**
     * Вывод в консоль статистики по привычке
     *
     * @param statisticDto dto c данными по статистике привычки
     */
    public void toStringStatistics(StatisticDto statisticDto) {
        StringBuilder sb = statisticDto.getMessage().isEmpty() ?
                new StringBuilder()
                        .append("Статистика за ")
                        .append(statisticDto.getPeriodType().getCode())
                        .append(". Период с ")
                        .append(statisticDto.getStartDate())
                        .append(" по ")
                        .append(statisticDto.getEndDate())
                        .append(". Частота привычки - ")
                        .append(statisticDto.getFrequency().name())
                        .append(". Необходимое выполнить ")
                        .append(statisticDto.getCountPlan())
                        .append(". Фактически выполнено ")
                        .append(statisticDto.getCountFact()) :
                new StringBuilder()
                        .append(statisticDto.getMessage())
                        .append(". Частота привычки - ")
                        .append(statisticDto.getFrequency().name());
        System.out.println(sb);
    }

    /**
     * Из истории выполнения привычки выбираем количество выполнений привычки
     * между указанными датами
     *
     * @param history   история привычки
     * @param startDate дата начала периода
     * @param endDate   дата завершения периода
     * @return количество фактических выполнений для привычки за
     * указанный период
     */
    private int getNumberOfHabitRunsPerPeriod(ArrayList<StatusDto> history,
                                              LocalDate startDate,
                                              LocalDate endDate) {
        return history.stream()
                .filter(statusDto ->
                        (statusDto.getTime().toLocalDate().isBefore(endDate)
                                || statusDto.getTime().toLocalDate().isEqual(endDate)) &&
                                (statusDto.getTime().toLocalDate().isAfter(startDate)
                                        || statusDto.getTime().toLocalDate().isEqual(startDate)))
                .toList()
                .size();
    }

    /**
     * В указанном периоде определяем количество выполнений привычки,
     * которое предусмотрено частотой привычки
     *
     * @param frequency  частота привычки
     * @param startDate  дата начала периода, за который берем статистику
     * @param endDate    дата окончания периода, за который берем статистику
     * @param periodType тип периода, за который берем статистику
     * @return количество выполнений для привычки,
     * которое предусмотрено частотой привычки
     */
    private int getPeriodForHabit(Frequency frequency,
                                  LocalDate startDate,
                                  LocalDate endDate,
                                  PeriodType periodType) {
        int period = (int) ChronoUnit.DAYS.between(startDate, endDate);
        if (!frequency.equals(Frequency.DAILY) && periodType.equals(PeriodType.DAY)) {
            period = 0;
        }
        if (frequency.equals(Frequency.WEEKLY) && !periodType.equals(PeriodType.DAY)) {
            period = period / PeriodType.WEEK.getId();
        }

        return period;
    }

    /**
     * Получение даты начала периода, за который берем статистику,
     * или даты создания привычки
     *
     * @param endDate    текущая дата
     * @param createDate дата создания привычки
     * @param periodType тип периода, за который берем статистику
     * @return дата начала периода, за который берем статистику,
     * или дата создания привычки
     */
    private LocalDate getStartDate(LocalDate endDate, LocalDate createDate, PeriodType periodType) {
        return endDate
                .minusDays(periodType.getId())
                .isBefore(createDate) ?
                createDate : endDate.minusDays(periodType.getId());
    }
}
