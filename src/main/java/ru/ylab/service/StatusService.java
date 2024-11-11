package ru.ylab.service;

import lombok.RequiredArgsConstructor;
import ru.ylab.dto.StatusDto;
import ru.ylab.repository.StatusRepository;
import ru.ylab.repository.impl.StatusRepositoryImpl;

import java.util.ArrayList;

@RequiredArgsConstructor
public class StatusService {
    /**
     * История выполнения для каждой привычки по id привычки
     *
     * @param habitId id привычки
     * @return история выполнения привычки
     */
    public ArrayList<StatusDto> getHistory(Long habitId) {
        StatusRepository statusRepository = new StatusRepositoryImpl();
        return statusRepository.getHistory(habitId);
    }

    /**
     * Отметка о выполнении привычки
     *
     * @param habitId id привычки
     * @return отметка о выполнении привычки
     */
    public StatusDto createStatus(Long habitId) {
        System.out.println("HabitDto " + habitId);
        StatusRepository statusRepository = new StatusRepositoryImpl();
        return statusRepository.createStatus(habitId);
    }
}
