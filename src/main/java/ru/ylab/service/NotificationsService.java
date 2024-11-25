package ru.ylab.service;

import lombok.NoArgsConstructor;
import ru.ylab.dto.HabitDto;
import ru.ylab.dto.NotificationDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.repository.NotificationRepository;
import ru.ylab.repository.impl.NotificationRepositoryImpl;

import java.util.List;

@NoArgsConstructor
public class NotificationsService {
    /**
     * Получение списка непрочитанных уведомлений по id пользователя
     * со статусом (is_read) false
     *
     * @param personId id пользователя
     * @return List<NotificationDto>  список уведомлений пользователя
     */
    public List<NotificationDto> getNotifications(Long personId) {
        NotificationRepository notificationRepository = new NotificationRepositoryImpl();
        return notificationRepository.getNotifications(personId);
    }

    /**
     * Редактировать статус уведомления по id уведомления
     *
     * @param noticesId id уведомления
     */
    public void updateIsReadNotification(Long noticesId) {
        NotificationRepository notificationRepository = new NotificationRepositoryImpl();
        notificationRepository.updateIsReadNotification(noticesId);
    }

    /**
     * Создание нового уведомления
     *
     * @param habitDto dto привычки
     * @return NotificationDto уведомление о необходимости выполнения привычки
     */
    public NotificationDto createNotification(HabitDto habitDto, PersonDto personDto) {
        NotificationRepository notificationRepository = new NotificationRepositoryImpl();
        return notificationRepository.createNotification(habitDto, personDto);
    }
}
