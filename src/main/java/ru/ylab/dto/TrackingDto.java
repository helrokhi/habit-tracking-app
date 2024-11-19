package ru.ylab.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrackingDto {
    private Integer numberOfHabits;//Всего привычек
    private Integer numberOfHabitsNeedDoneToday;//Нужно выполнить
    private Integer numberOfHabitsIsDoneToday;//выполнено привычек
}
