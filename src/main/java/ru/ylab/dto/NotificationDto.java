package ru.ylab.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private OffsetDateTime sentTime;
    private Long personId;
    private Long habitId;
    private String contact;
    private Boolean isRead;
}
