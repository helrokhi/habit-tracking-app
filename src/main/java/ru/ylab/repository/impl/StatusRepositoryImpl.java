package ru.ylab.repository.impl;

import ru.ylab.dto.StatusDto;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.StatusRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class StatusRepositoryImpl implements DatabaseRepository, StatusRepository {
    @Override
    public ArrayList<StatusDto> getHistory(Long habitId) {
        ArrayList<StatusDto> history = new ArrayList<>(0);

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            history = selectHistory(habitId, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return history;
    }

    @Override
    public StatusDto findStatusDtoById(Long statusId) {
        StatusDto statusDto = new StatusDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            statusDto = selectStatusById(statusId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return statusDto;
    }

    @Override
    public StatusDto createStatus(Long habitId) {
        StatusDto statusDto = StatusDto.builder()
                .time(OffsetDateTime.now())
                .build();
        Long statusId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            Long lastId = getLastId(connection);

            statusDto.setId(lastId + 1);
            statusDto.setHabitId(habitId);
            statusDto.setTime(OffsetDateTime.now());

            statusId = insertStatus(statusDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return findStatusDtoById(statusId);
    }

    @Override
    public StatusDto getLastStatusByHabitId(Long habitId) {
        Long statusId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {

            statusId = selectStatusIdByHabitId(habitId, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return findStatusDtoById(statusId);
    }

    private ArrayList<StatusDto> selectHistory(Long habitId, Connection connection) {
        ArrayList<StatusDto> history = new ArrayList<>(0);

        String sql =
                "SELECT " +
                        "id, " +
                        "habit_id, " +
                        "time " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                StatusDto statusDto = StatusDto.builder()
                        .id(resultSet.getLong(1))
                        .habitId(resultSet.getLong(2))
                        .time(resultSet.getTimestamp(3).toInstant().atOffset(ZoneOffset.UTC))
                        .build();

                history.add(statusDto);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return history;
    }

    private StatusDto selectStatusById(Long statusId, Connection connection) {
        StatusDto statusDto = new StatusDto();
        String sqlStatus =
                "SELECT " +
                        "id, " +
                        "habit_id, " +
                        "time " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.id = '" + statusId + "'";

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatus);
            while (resultSet.next()) {
                statusDto.setId(resultSet.getLong(1));
                statusDto.setHabitId(resultSet.getLong(2));
                statusDto.setTime(resultSet.getTimestamp(3).toInstant().atOffset(ZoneOffset.UTC));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return statusDto;
    }

    private Long getLastId(Connection connection) {
        String lastIdSql =
                "SELECT id FROM tracking_habit.habit_history ORDER BY id DESC LIMIT 1";
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

    private Long insertStatus(StatusDto statusDto, Connection connection) {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.habit_history " +
                        "(id, " +
                        "habit_id, " +
                        "time) " +
                        "VALUES " +
                        "(?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
            preparedStatement.setLong(1, statusDto.getId());
            preparedStatement.setLong(2, statusDto.getHabitId());
            preparedStatement.setTimestamp(3, Timestamp.from(statusDto.getTime().toInstant()));

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return findStatusDtoById(statusDto.getId()).getId();
    }

    private Long selectStatusIdByHabitId(Long habitId, Connection connection) {
        String lastIdSql =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "' " +
                        "ORDER BY h.time " +
                        "DESC LIMIT 1";

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
}
