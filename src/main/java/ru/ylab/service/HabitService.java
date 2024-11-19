package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.StatusDto;
import ru.ylab.dto.enums.Frequency;
import ru.ylab.repository.HabitRepository;
import ru.ylab.repository.StatusRepository;
import ru.ylab.repository.impl.HabitRepositoryImpl;
import ru.ylab.repository.impl.StatusRepositoryImpl;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class HabitService {
    /**
     * Добавление новой привычки в базу данных
     * используя id пользователя и данных новой привычки (названия, описания и частоты)
     */
    public HabitDto create(PersonDto personDto, RegHabit regHabit) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        HabitDto habitDto = habitRepository.createHabit(personDto.getId(), regHabit);
        System.out.println("Создана привычка " + habitDto);
        return habitDto;
    }

    /**
     * Найти привычку по id привычки
     */
    public HabitDto findHabitByIndex(Long habitId) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        return habitRepository.getHabitDtoById(habitId);
    }

    /**
     * Найти привычку по id привычки
     * с добавлением текущего статуса привычки
     */
    public HabitDto findHabitByIndexWithStatus(Long habitId) {
        HabitRepository habitRepository = new HabitRepositoryImpl();

        HabitDto habitDto = habitRepository.getHabitDtoById(habitId);
        habitDto.setIsDone(isDoneHabit(habitDto));
        return habitDto;
    }

    /**
     * Удалить привычку по id привычки
     *
     * @param habit
     */
    public void delete(HabitDto habit) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        habitRepository.deleteHabit(habit.getId());
        System.out.println("Привычка удалена " + habit);
    }

    /**
     * Получить список всех привычек пользователя по id пользователя
     *
     * @param personId id пользователя
     * @return список привычек пользователя
     */
    public ArrayList<HabitDto> getHabits(Long personId) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        return habitRepository.getHabits(personId);
    }

    /**
     * Получить список всех привычек из базы данных
     * для личного кабинета пользователя с ролью admin
     *
     * @return список всех привычек
     */
    public ArrayList<HabitDto> getAllHabits() {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        return habitRepository.getAllHabitDtos();
    }

    /**
     * Обновить данные привычки (название, описание и частоту)
     *
     * @param habitDto dto привычки
     * @return dto привычки с обновленными данными
     */
    public HabitDto update(HabitDto habitDto) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        return habitRepository.updateHabitDto(habitDto);
    }

    /**
     * Вывод списка привычек в консоль
     *
     * @param habits список привычек
     */
    public void toStringListHabits(List<HabitDto> habits) {
        if (habits.isEmpty()) {
            System.out.println("Нет привычек у пользователя");
        } else {
            System.out.println("Список привычек пользователя");
            for (HabitDto habit : habits) {
                System.out.println("\t" + habit.getId() + ": " + habit);
            }
        }
    }

    /**
     * Сортировать список привычек по дате создания
     *
     * @param habits список привычек
     * @return список привычек, отсортированный по дате создания привычки
     */
    public List<HabitDto> getSortHabitsByTime(List<HabitDto> habits) {
        return habits
                .stream()
                .sorted(Comparator.comparing(HabitDto::getTime))
                .collect(Collectors.toList());
    }

    /**
     * Сортировать список привычек по статусу (выполнена или не выполнена)
     *
     * @param habits список привычек
     * @param isDone статус привычки
     * @return список привычек с определенным статусом
     */
    public List<HabitDto> getHabitsByStatus(List<HabitDto> habits, Boolean isDone) {
        return habits.stream()
                .filter(habitDto -> habitDto.getIsDone().equals(isDone))
                .collect(Collectors.toList());
    }

    /**
     * Получить список привычек пользователя
     * с добавлением текущего статуса привычки
     *
     * @param personId id пользователя
     * @return список привычек с из текущим статусом
     */
    public List<HabitDto> getHabitsWithStatus(Long personId) {
        HabitRepository habitRepository = new HabitRepositoryImpl();
        ArrayList<HabitDto> habitDtos = habitRepository.getHabits(personId);
        for (HabitDto habitDto : habitDtos) {
            habitDto.setIsDone(isDoneHabit(habitDto));
        }
        return habitDtos;
    }

    /**
     * Получить последний записанный в базу данных
     * статус выполнения привычки по id привычки
     *
     * @param habitId id привычки
     * @return dto о выполнении привычки из БД
     */
    public StatusDto getLastStatus(Long habitId) {
        StatusRepository statusRepository = new StatusRepositoryImpl();
        return statusRepository.getLastStatusByHabitId(habitId);
    }

    /**
     * Получить текущий статус привычки
     *
     * @param habitDto dto привычки
     * @return true or false (текущий статус привычки)
     */
    private boolean isDoneHabit(HabitDto habitDto) {
        return habitDto.getFrequency().equals(Frequency.WEEKLY) ?
                getCurrentStatusWhenFrequencyIsWeekly(habitDto.getId()) :
                getCurrentStatusWhenFrequencyIsDaily(habitDto.getId());
    }

    /**
     * Узнать текущий статус привычки если частота привычки DAILY
     *
     * @param habitId id привычки
     * @return true or false (текущий статус привычки если частота привычки DAILY)
     */
    private boolean getCurrentStatusWhenFrequencyIsDaily(Long habitId) {
        LocalDate lastExecuteTime = getLastExecuteTimeByHabit(habitId);
        LocalDate today = OffsetDateTime.now().toLocalDate();

        return today.isEqual(lastExecuteTime);
    }

    /**
     * Узнать текущий статус привычки если частота привычки WEEKLY
     *
     * @param habitId id привычки
     * @return true or false (текущий статус привычки, если частота привычки WEEKLY)
     */
    private boolean getCurrentStatusWhenFrequencyIsWeekly(Long habitId) {
        LocalDate lastExecuteTime = getLastExecuteTimeByHabit(habitId);
        LocalDate start = OffsetDateTime.now().toLocalDate().minusDays(7);
        LocalDate finish = OffsetDateTime.now().toLocalDate();

        return start.isBefore(lastExecuteTime) && finish.isAfter(lastExecuteTime);
    }

    /**
     * Получить из базы данных сведения о дате последнего выполнения привычки
     * по id привычки
     *
     * @param habitId id привычки
     * @return дата последнего выполнения привычки в формате LocalDate
     */
    private LocalDate getLastExecuteTimeByHabit(Long habitId) {
        StatusRepository statusRepository = new StatusRepositoryImpl();
        return statusRepository
                .getLastStatusByHabitId(habitId)
                .getTime()
                .toLocalDate();
    }
}
