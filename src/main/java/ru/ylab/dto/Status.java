package ru.ylab.dto;

import ru.ylab.dto.enums.StatusType;

import java.time.OffsetDateTime;

public class Status {
    private StatusType statusType;
    private OffsetDateTime time;

    public Status(StatusType statusType, OffsetDateTime time) {
        this.statusType = statusType;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusType=" + statusType +
                ", time=" + time +
                '}';
    }
}
