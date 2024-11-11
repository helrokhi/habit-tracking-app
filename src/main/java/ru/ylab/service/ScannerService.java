package ru.ylab.service;

import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.RegUser;

import java.util.Scanner;

public class ScannerService {
    private Scanner scanner;
    private final String ONE = "-- 1 -- ";
    private final String TWO = "-- 2 -- ";
    private final String THREE = "-- 3 -- ";
    private final String FOUR = "-- 4 -- ";
    private final String FIVE = "-- 5 -- ";
    private final String ZERO = "-- 0 -- ";
    private final String DELETE = "-- DELETE -- ";
    private final String SORT = "-- SORT -- ";
    private final String EXECUTE = "-- EXECUTE -- ";
    private final String NO = "-- NO -- ";

    private final String DAY = "-- DAY -- ";
    private final String WEEK = "-- WEEK -- ";
    private final String MONTH = "-- MONTH -- ";


    public String startMenu() {
        scanner = new Scanner(System.in);
        System.out.println("Приложение для отслеживания привычек\n");
        System.out.println(
                ONE + "Регистрация нового пользователя\n" +
                        TWO + "Авторизация пользователя");

        return scanner.nextLine().trim();
    }

    public String menuAccount() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Управление пользователем\n" +
                        TWO + "Управление привычками\n" +
                        THREE + "Отслеживание выполнения привычек пользователя\n" +
                        FOUR + "Статистика и аналитика\n" +
                        ZERO + "Выйти из личного кабинета");
        return scanner.nextLine().trim();
    }

    public String menuAdminAccount() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Управление пользователем\n" +
                        TWO + "Управление привычками\n" +
                        THREE + "Отслеживание выполнения привычек пользователя\n" +
                        FOUR + "Статистика и аналитика\n" +
                        FIVE + "Администрирование\n" +
                        ZERO + "Выйти из личного кабинета");
        return scanner.nextLine().trim();
    }

    public String userManagementMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Изменить имя\n" +
                        TWO + "Изменить email\n" +
                        THREE + "Изменить пароль\n" +
                        DELETE + "Удалить аккаунт\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String menuHabits() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Просмотр привычек\n" +
                        TWO + "Работа с привычкой\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String menuViewHabits() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Список всех привычек пользователя\n" +
                        SORT + "Список всех привычек пользователя," +
                        "отсортированный по дате создания\n" +
                        EXECUTE + "Список всех привычек пользователя со статусом «Выполнена»\n" +
                        NO + "Список всех привычек пользователя со статусом «Не выполнена»\n" +
                        ZERO + "Вернуться в предыдущее меню");
        return scanner.nextLine().trim();
    }

    public String habitManagementMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Создание привычки\n" +
                        TWO + "Редактирование привычки\n" +
                        DELETE + "Удаление привычки\n" +
                        ZERO + "Вернуться в предыдущее меню");
        return scanner.nextLine().trim();
    }

    public String menuUpdateHabit() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Изменить название\n" +
                        TWO + "Изменить описание\n" +
                        THREE + "Изменить частоту\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String trackingHabitsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Отметить выполнение привычки\n" +
                        TWO + "Посмотреть историю выполнения привычки\n" +
                        THREE + "Статистика выполнения привычки за указанный период (день, неделя, месяц)\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String statisticsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Подсчет текущих серий выполнения привычек\n" +
                        TWO + "Процент успешного выполнения привычек за определенный период\n" +
                        THREE + "Формирование отчета для пользователя по прогрессу выполнения\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String habitFulfillmentStatisticsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                DAY + "Статистика выполнения привычки за день\n" +
                        WEEK + "Статистика выполнения привычки за неделю\n" +
                        MONTH + "Статистика выполнения привычки за месяц\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public String administrationMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Список пользователей\n" +
                        TWO + "Список привычек\n" +
                        THREE + "Блокировка пользователя\n" +
                        FOUR + "Удаление пользователя\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    public RegUser createRegUser() {
        String email = createEmail();
        String password = createPassword();

        RegUser regUser = new RegUser(email, password);
        return (!regUser.getEmail().isBlank() && !regUser.getPassword().isBlank()) ? regUser : null;
    }

    public String createEmail() {
        scanner = new Scanner(System.in);
        System.out.println("Введите e-mail пользователя");
        return scanner.nextLine().trim();
    }

    public String createPassword() {
        scanner = new Scanner(System.in);
        System.out.println("Введите пароль пользователя");
        return scanner.nextLine().trim();
    }

    public String createIndexHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите индекс привычки");
        return scanner.nextLine().trim();
    }

    public String createIndexPerson() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите индекс пользователя");
        return scanner.nextLine().trim();
    }

    public RegHabit createRegHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите название привычки");
        String title = scanner.nextLine().trim();
        System.out.println("Напишите описание привычки");
        String text = scanner.nextLine().trim();
        System.out.println("Напишите частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), по умолчанию ежедневно (daily)) ");
        String frequency = scanner.nextLine().trim();
        return new RegHabit(title, text, frequency);
    }

    public String updateNamePerson(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение имени " + personDto.getName() + "\n" +
                "Добавить новое имя");
        return scanner.nextLine().trim();
    }

    public String updateEmail(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение email " + personDto + "\n" +
                "Добавить новый email");
        return scanner.nextLine().trim();
    }

    public String updatePassword(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение пароль " + personDto + "\n" +
                "Добавить новый пароль");
        return scanner.nextLine().trim();
    }

    public String updateTitleHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое название привычки");
        return scanner.nextLine().trim();
    }

    public String updateTextHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое описание привычки");
        return scanner.nextLine().trim();
    }

    public String updateFrequencyHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новую частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), " +
                "по умолчанию ежедневно (daily)) ");
        return scanner.nextLine().trim();
    }
}
