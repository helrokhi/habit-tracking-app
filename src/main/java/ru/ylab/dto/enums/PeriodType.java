package ru.ylab.dto.enums;

import lombok.Getter;

@Getter
public enum PeriodType {
    DAY(1, "день", "DAY"),
    WEEK(7, "неделю", "WEEK"),
    MONTH(30, "месяц", "MONTH");

    private final int id;
    private final String code;
    private final String name;

    PeriodType(int id, String code, String name) {
        this.id = id;
        this.code= code;
        this.name = name;
    }
}
