package ru.ylab.repository;

import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;

import java.util.ArrayList;

public interface HabitRepository {

    ArrayList<HabitDto> getHabits(Long personId);

    ArrayList<HabitDto> getAllHabitDtos();

    HabitDto createHabit(Long personId, RegHabit regHabit);

    HabitDto updateHabitTitle(Long habitId, String title);

    HabitDto updateHabitText(Long habitId, String text);

    HabitDto updateHabitFrequency(Long habitId, String frequency);

    HabitDto updateHabitDto(HabitDto habitDto);

    HabitDto getHabitDtoById(Long habitId);

    void deleteHabit(Long habitId);
}
