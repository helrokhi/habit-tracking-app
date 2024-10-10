package ru.ylab.service;

import ru.ylab.dto.Person;

import java.util.Scanner;

public class HabitService {
    private final Scanner scanner;
    private final Person person;


    public HabitService(Person person) {
        scanner = new Scanner(System.in);
        this.person = person;
    }

    public  void management() {
        System.out.println("Управление привычками");
    }
}
