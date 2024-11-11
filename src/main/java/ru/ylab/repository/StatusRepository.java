package ru.ylab.repository;

import ru.ylab.dto.StatusDto;

import java.util.ArrayList;

public interface StatusRepository {
    ArrayList<StatusDto> getHistory(Long habitId);

    StatusDto findStatusDtoById(Long statusId);

    StatusDto createStatus(Long habitId);

    StatusDto getLastStatusByHabitId(Long habitId);
}
