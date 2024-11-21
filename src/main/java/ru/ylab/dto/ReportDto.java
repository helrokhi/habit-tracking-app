package ru.ylab.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportDto {
    private Integer numberOfHabitsToComplete;//Нужно выполнить
    private Integer numberOfHabitsCompleted;//выполнено привычек
}
