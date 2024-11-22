package ru.ylab.repository;

import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;

import java.util.ArrayList;

public interface HabitRepository {
    /**
     * Список привычек пользователя
     *
     * @param personId id пользователя
     * @return ArrayList<HabitDto> список HabitDto привычек пользователя
     */
    ArrayList<HabitDto> getHabits(Long personId);

    /**
     * Список всех привычек из базы данных для меню пользователя
     * с правами администратора
     *
     * @return ArrayList<HabitDto> список HabitDto привычек пользователя
     */
    ArrayList<HabitDto> getAllHabitDtos();

    /**
     * Создание привычки у пользователя
     *
     * @param personId id пользователя
     * @param regHabit данные привычки для регистрации
     * @return HabitDto новая привычка пользователя
     */
    HabitDto createHabit(Long personId, RegHabit regHabit);

    /**
     * Редактирование привычки (изменение информации о привычкеЖ название описание и частота)
     *
     * @param habitDto dto привычки
     * @return HabitDto dto привычки с обновленными данными
     */
    HabitDto updateHabitDto(HabitDto habitDto);

    /**
     * Получение данных о привычке по id привычки
     *
     * @param habitId id привычки
     * @return HabitDto dto привычки
     */
    HabitDto getHabitDtoById(Long habitId);

    /**
     * Удаление привычки по её id
     *
     * @param habitId id привычки
     */
    void deleteHabit(Long habitId);
}
