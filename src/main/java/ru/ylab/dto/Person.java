package ru.ylab.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Person {
    private final User user;
    private String name;
    private Boolean isBlocked;
    private Set<Habit> habits;

    public Person(User user) {
        this.user = user;
        name = "";
        isBlocked = false;
        habits = new HashSet<>(0);
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return Objects.equals(user, person.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Person{" +
                "user=" + user.getEmail() +
                ", name='" + name + '\'' +
                ", isBlocked=" + isBlocked +
                ", habits=" + habits +
                '}';
    }
}
