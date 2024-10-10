package ru.ylab.dto;

import ru.ylab.dto.enums.Frequency;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


public class Habit {
    private String title;
    private String text;
    private Frequency frequency;

    private OffsetDateTime time;
    private Set<OffsetDateTime> history;

    public Habit(String title, String text, Frequency frequency) {
        this.title = title;
        this.text = text;
        this.frequency = frequency;
        this.history = new HashSet<>(0);
    }
}
