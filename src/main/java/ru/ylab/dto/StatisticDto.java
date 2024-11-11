package ru.ylab.dto;

import lombok.Builder;
import lombok.Data;
import ru.ylab.dto.enums.Frequency;
import ru.ylab.dto.enums.PeriodType;

import java.time.LocalDate;
@Data
@Builder
public class StatisticDto {
    private PeriodType periodType;
    private Frequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer countPlan;
    private Integer countFact;
    private String message;
}
