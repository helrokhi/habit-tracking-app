package ru.ylab.dto;

import ru.ylab.dto.enums.Frequency;
import ru.ylab.dto.enums.StatusType;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Set;


public class Habit {
    private String title;
    private String text;
    private Frequency frequency;

    private OffsetDateTime time;
    private Set<Status> history;

    private Status status;

    public Habit(String title, String text, String frequency) {
        this.title = title;
        this.text = text;
        this.frequency = getFrequency(frequency);
        this.time = OffsetDateTime.now();
        this.history = Collections.emptySet();
        this.status = new Status(StatusType.NO, OffsetDateTime.now());
    }

    private Frequency getFrequency(String query) {
        switch (query) {
            case "daily": return Frequency.DAILY;
            case "weekly": return Frequency.WEEKLY;
            case "monthly": return Frequency.MONTHLY;
            default: return Frequency.ONCE;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrequency(String query) {
        this.frequency = getFrequency(query);
    }

    @Override
    public String toString() {
        return "Habit{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", frequency=" + frequency +
                ", time=" + time +
                ", history=" + history +
                ", status=" + status +
                '}';
    }
}
