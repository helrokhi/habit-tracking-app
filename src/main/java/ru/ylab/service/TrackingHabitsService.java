package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.ReportDto;
import ru.ylab.dto.SuccessRateDto;
import ru.ylab.dto.TrackingDto;
import ru.ylab.repository.TrackingRepository;
import ru.ylab.repository.impl.TrackingRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class TrackingHabitsService {
    /**
     * Текущая серия выполнения привычек пользователя
     *
     * @param personId id пользователя
     * @return TrackingDto
     */
    public TrackingDto streak(Long personId) {
        TrackingRepository trackingRepository = new TrackingRepositoryImpl();
        int count = trackingRepository.getCount(personId);
        int countIsDoneToday = trackingRepository.getCountIsDoneToday(personId);
        int countNeedDoneToday = trackingRepository.getCountNeedDoneToday(personId);
        return TrackingDto.builder()
                .numberOfHabits(count)
                .numberOfHabitsNeedDone(countNeedDoneToday)
                .numberOfHabitsIsDone(countIsDoneToday)
                .build();
    }

    /**
     * Вывод в консоль текущей серии выполнения привычек пользователя
     *
     * @param trackingDto dto c данными по текущей серии выполнения привычек пользователя
     */
    public void toStringStreak(TrackingDto trackingDto) {
        StringBuilder sb = new StringBuilder()
                .append("Всего привычек ")
                .append(trackingDto.getNumberOfHabits())
                .append(". Сегодня нужно выполнить ")
                .append(trackingDto.getNumberOfHabitsNeedDone())
                .append(" привычек. Сегодня выполнено ")
                .append(trackingDto.getNumberOfHabitsIsDone())
                .append(" привычек.");
        System.out.println(sb);
    }

    /**
     * Процент успешного выполнения привычек за определенный период
     *
     * @param startLocalDate начало периода
     * @param endLocalDate   конец периода
     * @param personId       id пользователя
     * @return процент выполнения привычек
     */
    public SuccessRateDto getSuccessRate(String startLocalDate, String endLocalDate, Long personId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        SuccessRateDto successRateDto = SuccessRateDto.builder().build();

        if (isValidLocalDate(startLocalDate) || isValidLocalDate(endLocalDate)) {
            successRateDto.setMessage("Неверный формат даты");
            return successRateDto;
        }

        LocalDate start = LocalDate.parse(startLocalDate, formatter);
        LocalDate end = LocalDate.parse(endLocalDate, formatter);

        if (!isValidPeriod(start, end)) {
            successRateDto.setMessage("Период для расчета процента выполнения некорректный");
            return successRateDto;
        }

        List<TrackingDto> trackingDtoList = getTrackingDtoList(start, end, personId);
        successRateDto.setSuccessRate(successRate(trackingDtoList));
        return successRateDto;
    }

    /**
     * Вывод в консоль текущей серии выполнения привычек пользователя
     *
     * @param successRate    Процент успешного выполнения привычек
     * @param startLocalDate начало периода
     * @param endLocalDate   конец периода
     */
    public void toStringSuccessRate(SuccessRateDto successRate, String startLocalDate, String endLocalDate) {
        StringBuilder sb = successRate.getMessage().isEmpty() ?
                new StringBuilder()
                        .append("Процент успешного выполнения привычек за период c ")
                        .append(startLocalDate)
                        .append(" по ")
                        .append(endLocalDate)
                        .append(" равен ")
                        .append(successRate.getSuccessRate() * 100)
                        .append(" .") :
                new StringBuilder()
                        .append(successRate.getMessage());
        System.out.println(sb);
    }

    /**
     * Отчет для пользователя по прогрессу выполнения привычек
     *
     * @param personId id пользователя
     * @return ReportDto
     */
    public ReportDto getReport(Long personId) {
        TrackingRepository trackingRepository = new TrackingRepositoryImpl();
        int countToComplete = trackingRepository.getNumberOfHabitsToComplete(personId);
        int countCompleted = trackingRepository.getNumberOfHabitsCompleted(personId);
        return ReportDto.builder()
                .numberOfHabitsToComplete(countToComplete)
                .numberOfHabitsCompleted(countCompleted)
                .build();
    }

    /**
     * Вывод в консоль отчета для пользователя по прогрессу выполнения привычек
     *
     * @param reportDto dto c данными для отчета
     */
    public void toStringReport(ReportDto reportDto) {
        StringBuilder sb = new StringBuilder()
                .append("Прогресс выполнения привычек: ")
                .append(reportDto.getNumberOfHabitsCompleted())
                .append("/")
                .append(reportDto.getNumberOfHabitsToComplete())
                .append(".");
        System.out.println(sb);
    }

    /**
     * Можно ди преобразовать строку в дату
     *
     * @param date дата в формате строки
     * @return false, если строку можно преобразовать в дату
     */
    private boolean isValidLocalDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return false;
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return true;
        }
    }

    /**
     * Проверка существует ли период между датами
     *
     * @param start начало периода
     * @param end   завершение периода
     * @return true, если период существует
     */
    private boolean isValidPeriod(LocalDate start, LocalDate end) {
        return start.isBefore(end) || start.isEqual(end);
    }

    /**
     * Серия выполнения привычек пользователя на определенную дату
     *
     * @param date     дата
     * @param personId id пользователя
     * @return TrackingDto
     */
    private TrackingDto streakByDate(String date, Long personId) {
        TrackingRepository trackingRepository = new TrackingRepositoryImpl();
        int countByDate = trackingRepository.getCountByDate(date, personId);
        int countIsDoneByDate = trackingRepository.getCountIsDoneByDate(date, personId);
        int countNeedDoneByDate = trackingRepository.getCountNeedDoneByDate(date, personId);
        return TrackingDto.builder()
                .numberOfHabits(countByDate)
                .numberOfHabitsNeedDone(countNeedDoneByDate)
                .numberOfHabitsIsDone(countIsDoneByDate)
                .build();
    }

    /**
     * Список серий выполнения привычек за период
     *
     * @param start    начало периода
     * @param end      завершение периода
     * @param personId id пользователя
     * @return список серий выполнения привычек
     */
    private List<TrackingDto> getTrackingDtoList(LocalDate start, LocalDate end, Long personId) {
        List<TrackingDto> trackingDtoList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = start;
        while (date.isBefore(end) || date.equals(end)) {
            TrackingDto trackingDto = streakByDate(date.format(formatter), personId);
            trackingDtoList.add(trackingDto);
            date = date.plusDays(1);
        }
        return trackingDtoList;
    }

    /**
     * Расчет процента выполнения из списка TrackingDto
     *
     * @param trackingDtoList список серий выполнения привычек пользователя
     * @return процент выполнения
     */
    private float successRate(List<TrackingDto> trackingDtoList) {
        float successRate = 0F;

        int countIsDone = 0;
        int countNeedDone = 0;

        for (TrackingDto trackingDto : trackingDtoList) {
            countIsDone += trackingDto.getNumberOfHabitsIsDone();
            countNeedDone += trackingDto.getNumberOfHabitsNeedDone();
        }
        if (countNeedDone != 0) successRate = (float) countIsDone / countNeedDone;
        return successRate;
    }
}
