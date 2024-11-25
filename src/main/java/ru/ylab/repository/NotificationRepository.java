package ru.ylab.repository;

import ru.ylab.dto.HabitDto;
import ru.ylab.dto.NotificationDto;
import ru.ylab.dto.PersonDto;

import java.util.List;

public interface NotificationRepository {
    /**
     * Получение списка непрочитанных уведомлений по id пользователя
     * со статусом (is_read) false
     *
     * @param personId id пользователя
     * @return List<NotificationDto>  список уведомлений пользователя
     */
    List<NotificationDto> getNotifications(Long personId);

    /**
     * Редактировать статус уведомления по id уведомления
     *
     * @param noticesId id уведомления
     */
    void updateIsReadNotification(Long noticesId);

    /**
     * Создание нового уведомления
     *
     * @param habitDto dto привычки
     * @return NotificationDto уведомление о необходимости выполнения привычки
     */
    NotificationDto createNotification(HabitDto habitDto, PersonDto personDto);

    /**
     * Получение данных об уведомлении по id уведомления
     *
     * @param noticeId id уведомления
     * @return NotificationDto dto уведомления
     */
    NotificationDto getNotificationDtoById(Long noticeId);
}
