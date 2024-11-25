package ru.ylab.service;

import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.RegUser;

import java.time.LocalDate;
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

    /**
     * Стартовое меню приложения регистрация/авторизация
     *
     * @return String данные из консоли о пункте меню
     */
    public String startMenu() {
        scanner = new Scanner(System.in);
        System.out.println("Приложение для отслеживания привычек\n");
        System.out.println(
                ONE + "Регистрация нового пользователя\n" +
                        TWO + "Авторизация пользователя");

        return scanner.nextLine().trim();
    }

    /**
     * Меню управления пользователем
     *
     * @return String данные из консоли о пункте меню
     */
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

    /**
     * Меню управления администратора
     *
     * @return String данные из консоли о пункте меню
     */
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

    /**
     * Меню редактирования профиля пользователя
     *
     * @return String данные из консоли о пункте меню
     */
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

    /**
     * Меню просмотра привычек пользователя
     *
     * @return String данные из консоли о пункте меню
     */
    public String menuHabits() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Просмотр привычек\n" +
                        TWO + "Работа с привычкой\n" +
                        THREE + "Уведомления\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Меню просмотра привычек пользователя и их сортировка
     *
     * @return String данные из консоли о пункте меню
     */
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

    /**
     * Меню управления привычками (создание, редактирование, удаление)
     *
     * @return String данные из консоли о пункте меню
     */
    public String habitManagementMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Создание привычки\n" +
                        TWO + "Редактирование привычки\n" +
                        DELETE + "Удаление привычки\n" +
                        ZERO + "Вернуться в предыдущее меню");
        return scanner.nextLine().trim();
    }

    /**
     * Меню редактирования привычки (изменение названия, описания и частоты)
     *
     * @return String данные из консоли о пункте меню
     */
    public String menuUpdateHabit() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Изменить название\n" +
                        TWO + "Изменить описание\n" +
                        THREE + "Изменить частоту\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Меню отслеживания выполнения привычек
     *
     * @return String данные из консоли о пункте меню
     */
    public String trackingHabitsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Отметить выполнение привычки\n" +
                        TWO + "Посмотреть историю выполнения привычки\n" +
                        THREE + "Статистика выполнения привычки за указанный период (день, неделя, месяц)\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Меню статистики и аналитики по привычкам пользователя
     *
     * @return String данные из консоли о пункте меню
     */
    public String statisticsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Подсчет текущих серий выполнения привычек\n" +
                        TWO + "Процент успешного выполнения привычек за определенный период\n" +
                        THREE + "Формирование отчета для пользователя по прогрессу выполнения\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Меню статистики выполнения привычки за указанный период
     *
     * @return String данные из консоли о пункте меню
     */
    public String habitFulfillmentStatisticsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                DAY + "Статистика выполнения привычки за день\n" +
                        WEEK + "Статистика выполнения привычки за неделю\n" +
                        MONTH + "Статистика выполнения привычки за месяц\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Меню администрирования для пользователя с ролью admin
     *
     * @return String данные из консоли о пункте меню
     */
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

    /**
     * Меню уведомлений
     *
     * @return String данные из консоли о пункте меню
     */
    public String notificationsMenu() {
        scanner = new Scanner(System.in);
        System.out.println(
                ONE + "Список пользователей\n" +
                        TWO + "Список привычек\n" +
                        THREE + "Блокировка пользователя\n" +
                        FOUR + "Удаление пользователя\n" +
                        ZERO + "Вернуться в личный кабинет");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных пользователя для регистрации/авторизации
     *
     * @return RegUser данные пользователя для регистрации/авторизации
     */
    public RegUser createRegUser() {
        String email = createEmail();
        String password = createPassword();

        RegUser regUser = new RegUser(email, password);
        return (!regUser.getEmail().isBlank() && !regUser.getPassword().isBlank()) ? regUser : null;
    }

    /**
     * Получение данных об адресе электронной почты из консоли
     *
     * @return String данные из консоли
     */
    public String createEmail() {
        scanner = new Scanner(System.in);
        System.out.println("Введите e-mail пользователя");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о пароле из консоли
     *
     * @return String данные из консоли
     */
    public String createPassword() {
        scanner = new Scanner(System.in);
        System.out.println("Введите пароль пользователя");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных об индексе привычки из консоли
     *
     * @return String данные из консоли
     */
    public String createIndexHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите индекс привычки");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных об индексе пользователя из консоли
     *
     * @return String данные из консоли
     */
    public String createIndexPerson() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите индекс пользователя");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных для создания новой привычки из консоли
     *
     * @return String данные из консоли
     */
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

    /**
     * Получение данных о дате начала периода из консоли
     *
     * @return String данные из консоли
     */
    public String createStartLocalDate() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите дату начала периода в формате «ГГГГ-ММ-ДД»");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о дате завершения периода из консоли
     *
     * @return String данные из консоли
     */
    public String createEndLocalDate() {
        scanner = new Scanner(System.in);
        System.out.println("Укажите дату завершения периода в формате «ГГГГ-ММ-ДД»");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новом имени пользователя из консоли
     *
     * @return String данные из консоли о новом имени пользователя
     */
    public String updateNamePerson(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение имени " + personDto.getName() + "\n" +
                "Добавить новое имя");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новом адресе электронной почты пользователя из консоли
     *
     * @return String данные из консоли о новом адресе электронной почты пользователя
     */
    public String updateEmail(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение email " + personDto + "\n" +
                "Добавить новый email");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новом пароле пользователя из консоли
     *
     * @return String данные из консоли о новом пароле пользователя
     */
    public String updatePassword(PersonDto personDto) {
        scanner = new Scanner(System.in);
        System.out.println("Изменение пароль " + personDto + "\n" +
                "Добавить новый пароль");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новом названии привычки из консоли
     *
     * @return String данные из консоли о новом названии привычки
     */
    public String updateTitleHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое название привычки");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новом описании привычки из консоли
     *
     * @return String данные из консоли о новом описании привычки
     */
    public String updateTextHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новое описание привычки");
        return scanner.nextLine().trim();
    }

    /**
     * Получение данных о новой частоте привычки из консоли
     *
     * @return String данные из консоли о новой частоте привычки
     */
    public String updateFrequencyHabit() {
        scanner = new Scanner(System.in);
        System.out.println("Напишите новую частоту привычки (ежедневно (daily), " +
                "еженедельно (weekly), " +
                "по умолчанию ежедневно (daily)) ");
        return scanner.nextLine().trim();
    }
}
