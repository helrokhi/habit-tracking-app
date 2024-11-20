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

    /**
     * Получить количество привычек пользователя на определенную дату
     *
     * @param date     дата
     * @param personId id пользователя
     * @return количество привычек пользователя
     */
    int getCountByDate(String date, Long personId);

    /**
     * Получить количество привычек пользователя,
     * которые выполнены на определенную дату
     *
     * @param date     дата
     * @param personId id пользователя
     * @return количество привычек пользователя, которые выполнены на определенную дату
     */
    int getCountIsDoneByDate(String date, Long personId);

    /**
     * Получить количество привычек пользователя,
     * которые нужно выполнить на определенную дату
     *
     * @param date     дата
     * @param personId id пользователя
     * @return количество привычек пользователя, которые нужно выполнить на определенную дату
     */
    int getCountNeedDoneByDate(String date, Long personId);
}
