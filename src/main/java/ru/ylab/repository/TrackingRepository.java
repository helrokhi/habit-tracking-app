package ru.ylab.repository;

public interface TrackingRepository {
    /**
     * Получить количество привычек пользователя
     *
     * @param personId id пользователя
     * @return количество привычек пользователя
     */
    int getCount(Long personId);

    /**
     * Получить количество привычек пользователя,
     * которые выполнены сегодня
     *
     * @param personId id пользователя
     * @return количество привычек пользователя, которые выполнены сегодня
     */
    int getCountIsDoneToday(Long personId);

    /**
     * Получить количество привычек пользователя,
     * которые нужно выполнить сегодня
     *
     * @param personId id пользователя
     * @return количество привычек пользователя, которые нужно выполнить сегодня
     */
    int getCountNeedDoneToday(Long personId);
}
