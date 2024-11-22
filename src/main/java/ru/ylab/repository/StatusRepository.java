package ru.ylab.repository;

import ru.ylab.dto.StatusDto;

import java.util.ArrayList;

public interface StatusRepository {
    /**
     * История привычки
     *
     * @param habitId id привычки
     * @return список StatusDto с историей выполнения привычки
     */
    ArrayList<StatusDto> getHistory(Long habitId);

    /**
     * Получение отметки о выполнении привычки по id статуса
     *
     * @param statusId id отметки о выполнении привычки
     * @return StatusDto отметка о выполнении привычки
     */
    StatusDto findStatusDtoById(Long statusId);

    /**
     * Создание нового статуса привычки (отметка о выполнении привычки)
     *
     * @param habitId id привычки
     * @return StatusDto отметка о выполнении привычки
     */
    StatusDto createStatus(Long habitId);

    /**
     * Получить из истории привычки последнюю отметку о выполнении привычки
     *
     * @param habitId id привычки
     * @return StatusDto последняя отметка о выполнении привычки
     */
    StatusDto getLastStatusByHabitId(Long habitId);
}
