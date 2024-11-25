package ru.ylab.repository.impl;

import ru.ylab.dto.HabitDto;
import ru.ylab.dto.NotificationDto;
import ru.ylab.dto.PersonDto;
import ru.ylab.mapper.NotificationMapper;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.NotificationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepository, DatabaseRepository {
    @Override
    public List<NotificationDto> getNotifications(Long personId) {
        ArrayList<NotificationDto> dtos = new ArrayList<>(0);
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            dtos = selectNotifications(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return dtos;
    }

    @Override
    public void updateIsReadNotification(Long noticesId) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            update(noticesId, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public NotificationDto createNotification(HabitDto habitDto, PersonDto personDto) {
        NotificationDto dto = NotificationMapper.INSTANCE.habitToNoticesDtoForCreate(habitDto, personDto);
        Long noticeId = 0L;
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
            connection.setAutoCommit(false);
            Long lastId = getLastId(connection);

            dto.setId(lastId + 1);
            noticeId = isExistsNotification(dto, connection);
            if (noticeId == 0) {
                noticeId = insertNotification(dto, connection);
                System.out.println("Создано уведомление " + dto);
            }
            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
        return getNotificationDtoById(noticeId);
    }

    @Override
    public NotificationDto getNotificationDtoById(Long noticeId) {
        NotificationDto notificationDto = new NotificationDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            notificationDto = selectNotificationById(noticeId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return notificationDto;
    }

    private ArrayList<NotificationDto> selectNotifications(Long personId, Connection connection) {
        ArrayList<NotificationDto> dtos = new ArrayList<>(0);
        String sql =
                "SELECT " +
                        "id, " +
                        "sent_time, " +
                        "habit_id, " +
                        "contact " +
                        "FROM tracking_habit.notification n " +
                        "WHERE n.person_id = '" + personId + "' " +
                        "AND n.is_read = 'FALSE'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                NotificationDto dto = NotificationDto.builder()
                        .id(resultSet.getLong(1))
                        .sentTime(resultSet.getTimestamp(2).toInstant().atOffset(ZoneOffset.UTC))
                        .personId(personId)
                        .habitId(resultSet.getLong(3))
                        .contact(resultSet.getString(4))
                        .isRead(false)
                        .build();
                dtos.add(dto);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return dtos;
    }

    private void update(Long noticesId, Connection connection) {
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.notification n " +
                        "SET n.is_read = 'TRUE' " +
                        "WHERE n.id = '" + noticesId + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(updateDataSql);
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Long getLastId(Connection connection) {
        String lastIdSql =
                "SELECT id FROM tracking_habit.notification ORDER BY id DESC LIMIT 1";
        long lastId = 0L;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(lastIdSql);

            while (resultSet.next()) {
                lastId = resultSet.getLong(1);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return lastId;
    }

    private NotificationDto selectNotificationById(Long noticeId, Connection connection) {
        NotificationDto notificationDto = new NotificationDto();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "sent_time, " +
                        "person_id, " +
                        "habit_id, " +
                        "contact, " +
                        "is_read " +
                        "FROM tracking_habit.notification n " +
                        "WHERE n.id = '" + noticeId + "'";
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPerson);
            while (resultSet.next()) {
                notificationDto.setId(resultSet.getLong(1));
                notificationDto.setSentTime(resultSet.getTimestamp(2).toInstant().atOffset(ZoneOffset.UTC));
                notificationDto.setPersonId(resultSet.getLong(3));
                notificationDto.setHabitId(resultSet.getLong(4));
                notificationDto.setContact(resultSet.getString(5));
                notificationDto.setIsRead(resultSet.getBoolean(6));
            }
            resultSet.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return notificationDto;
    }

    private Long insertNotification(NotificationDto notificationDto, Connection connection) {
        System.out.println("NotificationDto " + notificationDto);
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.notification " +
                        "(" +
                        "id, " +
                        "sent_time, " +
                        "person_id, " +
                        "habit_id, " +
                        "contact, " +
                        "is_read " +
                        ") " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertDataSql);

            preparedStatement.setLong(1, notificationDto.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(notificationDto.getSentTime().toLocalDateTime()));
            preparedStatement.setLong(3, notificationDto.getPersonId());
            preparedStatement.setLong(4, notificationDto.getHabitId());
            preparedStatement.setString(5, notificationDto.getContact());
            preparedStatement.setBoolean(6, notificationDto.getIsRead());

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return notificationDto.getId();
    }

    private Long isExistsNotification(NotificationDto notificationDto, Connection connection) {
        LocalDate localDate = notificationDto.getSentTime().toLocalDate();
        Long personId = notificationDto.getPersonId();
        Long habitId = notificationDto.getHabitId();
        long noticeId = 0L;
        String sqlPerson =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.notification n " +
                        "WHERE n.person_id = '" + personId + "' " +
                        "AND n.habit_id = '" + habitId + "' " +
                        "AND DATE_TRUNC('day',n.sent_time) = '" + localDate + "'";
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPerson);
            while (resultSet.next()) {
                noticeId = resultSet.getLong(1);
            }
            resultSet.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return noticeId;
    }
}
