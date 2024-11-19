package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.TrackingDto;
import ru.ylab.repository.TrackingRepository;
import ru.ylab.repository.impl.TrackingRepositoryImpl;

@NoArgsConstructor
public class TrackingHabitsService {
    /**
     * Текущая серия выполнения привычек пользователя
     *
     * @param personId id пользователя
     * @return TrackingDto
     */
    public TrackingDto streak(Long personId) {
        TrackingRepository trackingRepository = new TrackingRepositoryImpl();
        int count = trackingRepository.getCount(personId);
        int countIsDoneToday = trackingRepository.getCountIsDoneToday(personId);
        int countNeedDoneToday = trackingRepository.getCountNeedDoneToday(personId);
        return TrackingDto.builder()
                .numberOfHabits(count)
                .numberOfHabitsNeedDoneToday(countNeedDoneToday)
                .numberOfHabitsIsDoneToday(countIsDoneToday)
                .build();
    }

    /**
     * Вывод в консоль текущей серии выполнения привычек пользователя
     *
     * @param trackingDto dto c данными по текущей серии выполнения привычек пользователя
     */
    public void toStringStreak(TrackingDto trackingDto) {
        StringBuilder sb = new StringBuilder()
                        .append("Всего привычек ")
                        .append(trackingDto.getNumberOfHabits())
                        .append(". Сегодня нужно выполнить ")
                        .append(trackingDto.getNumberOfHabitsNeedDoneToday())
                        .append(" привычек. Сегодня выполнено ")
                        .append(trackingDto.getNumberOfHabitsIsDoneToday())
                        .append(" привычек.");
        System.out.println(sb);
    }

}
