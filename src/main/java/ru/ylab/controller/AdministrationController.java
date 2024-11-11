package ru.ylab.controller;

import lombok.AllArgsConstructor;
import ru.ylab.dto.*;
import ru.ylab.service.*;

import java.util.ArrayList;

@AllArgsConstructor
public class AdministrationController {
    private PersonDto person;
    private final ScannerService scannerService = new ScannerService();

    public void administration() {
        UserService userService = new UserService();
        PersonService personService = new PersonService();
        HabitService habitService = new HabitService();
        switch (scannerService.administrationMenu()) {
            case "1" -> {
                System.out.println("Список пользователей\n");
                ArrayList<PersonDto> allPersons = personService.getAllPersons(person);
                personService.toStringListPersons(allPersons);
            }
            case "2" -> {
                System.out.println("Список привычек\n");
                ArrayList<HabitDto> allHabits = habitService.getAllHabits();
                habitService.toStringListHabits(allHabits);
            }
            case "3" -> {
                System.out.println("Блокировка пользователя\n");
                String index = scannerService.createIndexPerson();
                PersonDto personDto = personService.getPersonById(Long.valueOf(index));
                personDto.setIsBlocked(true);
                personService.update(personDto);
            }
            case "4" -> {
                System.out.println("Удаление пользователя\n");
                String index = scannerService.createIndexPerson();
                PersonDto personDto = personService.getPersonById(Long.valueOf(index));
                personService.delete(personDto);
            }
            default -> {
                System.out.println("Вернуться в личный кабинет");
                userService.account(person);
            }
        }

    }
}
