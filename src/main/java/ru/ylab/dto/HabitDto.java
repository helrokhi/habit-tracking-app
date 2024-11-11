package ru.ylab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ylab.dto.enums.Frequency;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HabitDto {
    private Long id;
    private Long personId;
    private String title;
    private String text;
    private OffsetDateTime time;
    private Frequency frequency;
    private Boolean isDone;
}
