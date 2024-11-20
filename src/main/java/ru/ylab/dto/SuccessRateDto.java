package ru.ylab.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccessRateDto {
    private float successRate;
    private String message;
}
