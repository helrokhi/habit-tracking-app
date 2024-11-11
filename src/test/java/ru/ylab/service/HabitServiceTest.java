package ru.ylab.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.StatusDto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {
    @InjectMocks
    private HabitService habitService;

    @InjectMocks
    private StatusService statusService;

    @Test
    void getHabitByIndex() {
        HabitDto habitDto = habitService.findHabitByIndex(1000L);
        Assertions.assertEquals("1000", habitDto.getId().toString());
        Assertions.assertEquals("10000", habitDto.getPersonId().toString());
        Assertions.assertEquals("habit 0", habitDto.getTitle());
        Assertions.assertEquals("text habit 0", habitDto.getText());
        Assertions.assertEquals("2023-11-10T12:05Z", habitDto.getTime().toString());
        Assertions.assertEquals("DAILY", habitDto.getFrequency().name());
    }

    @Test
    void getCurrentStatusWhenFrequencyIsDailyResultTrue() {
        OffsetDateTime lastExecuteTime = OffsetDateTime.parse("2024-08-13T16:10:05.829402900Z");
        OffsetDateTime today = OffsetDateTime.parse("2024-08-13T16:00:05.829402900Z");

        boolean result = today.toLocalDate().isEqual(lastExecuteTime.toLocalDate());
        Assertions.assertTrue(result);
    }

    @Test
    void getCurrentStatusWhenFrequencyIsDailyResultFalse() {
        OffsetDateTime lastExecuteTime = OffsetDateTime.parse("2024-08-10T16:10:05.829402900Z");
        OffsetDateTime today = OffsetDateTime.parse("2024-08-13T16:00:05.829402900Z");

        boolean result = today.toLocalDate().isEqual(lastExecuteTime.toLocalDate());
        Assertions.assertFalse(result);
    }

    @Test
    void getCurrentStatusWhenFrequencyIsWeeklyResultTrue() {
        LocalDate lastExecuteTime = OffsetDateTime.parse("2024-08-10T16:10:05.829402900Z").toLocalDate();

        LocalDate finish = OffsetDateTime.parse("2024-08-13T16:00:05.829402900Z").toLocalDate();
        LocalDate start = finish.minusDays(7);
        boolean result = start.isBefore(lastExecuteTime) && finish.isAfter(lastExecuteTime);
        Assertions.assertTrue(result);
    }

    @Test
    void getCurrentStatusWhenFrequencyIsWeeklyResultFalse() {
        LocalDate lastExecuteTime = OffsetDateTime.parse("2024-07-10T16:10:05.829402900Z").toLocalDate();

        LocalDate finish = OffsetDateTime.parse("2024-08-13T16:00:05.829402900Z").toLocalDate();
        LocalDate start = finish.minusDays(7);
        boolean result = start.isBefore(lastExecuteTime) && finish.isAfter(lastExecuteTime);
        Assertions.assertFalse(result);
    }

    @Test
    void findHabitByIndexWithStatusFalse() {
        HabitDto habitDto = habitService.findHabitByIndexWithStatus(1000L);
        Assertions.assertEquals("1000", habitDto.getId().toString());
        Assertions.assertEquals("10000", habitDto.getPersonId().toString());
        Assertions.assertEquals("habit 0", habitDto.getTitle());
        Assertions.assertEquals("text habit 0", habitDto.getText());
        Assertions.assertEquals("2023-11-10T12:05Z", habitDto.getTime().toString());
        Assertions.assertEquals("DAILY", habitDto.getFrequency().name());
        Assertions.assertFalse(habitDto.getIsDone());
    }

    @Test
    void findHabitByIndexWithStatusTrue() {
        StatusDto statusDto = statusService.createStatus(1000L);
        HabitDto habitDto = habitService.findHabitByIndexWithStatus(1000L);
        Assertions.assertEquals("1000", habitDto.getId().toString());
        Assertions.assertEquals("10000", habitDto.getPersonId().toString());
        Assertions.assertEquals("habit 0", habitDto.getTitle());
        Assertions.assertEquals("text habit 0", habitDto.getText());
        Assertions.assertEquals("2023-11-10T12:05Z", habitDto.getTime().toString());
        Assertions.assertEquals("DAILY", habitDto.getFrequency().name());
        Assertions.assertTrue(habitDto.getIsDone());
    }
}