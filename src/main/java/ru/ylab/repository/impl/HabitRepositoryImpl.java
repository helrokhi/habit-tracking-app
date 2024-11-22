package ru.ylab.repository.impl;

import ru.ylab.dto.HabitDto;
import ru.ylab.dto.RegHabit;
import ru.ylab.dto.enums.Frequency;
import ru.ylab.mapper.HabitMapper;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.HabitRepository;

import java.sql.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Locale;

public class HabitRepositoryImpl implements HabitRepository, DatabaseRepository {
    @Override
    public ArrayList<HabitDto> getHabits(Long personId) {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            habitDtos = selectHabits(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDtos;
    }

    @Override
    public ArrayList<HabitDto> getAllHabitDtos() {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            habitDtos = selectAllHabits(connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDtos;
    }

    @Override
    public HabitDto createHabit(Long personId, RegHabit regHabit) {
        HabitDto habitDto = HabitMapper.INSTANCE.regToDtoForCreate(regHabit);
        Long habitId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            Long lastId = getLastId(connection);

            habitDto.setId(lastId + 1);
            habitDto.setPersonId(personId);

            habitId = insertHabit(habitDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitId);
    }

    @Override
    public HabitDto updateHabitDto(HabitDto habitDto) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            update(habitDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getHabitDtoById(habitDto.getId());
    }

    @Override
    public HabitDto getHabitDtoById(Long habitId) {
        HabitDto habitDto = new HabitDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            habitDto = selectHabitById(habitId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDto;
    }

    @Override
    public void deleteHabit(Long habitId) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
            connection.setAutoCommit(false);

            deleteHabitDto(habitId, connection);
            deleteStatusDto(habitId, connection);

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
    }

    private ArrayList<HabitDto> selectHabits(Long personId, Connection connection) {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        String sql =
                "SELECT " +
                        "id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                HabitDto habitDto = HabitDto.builder()
                        .id(resultSet.getLong(1))
                        .personId(personId)
                        .title(resultSet.getString(2))
                        .text(resultSet.getString(3))
                        .time(resultSet.getTimestamp(4).toInstant().atOffset(ZoneOffset.UTC))
                        .frequency(Frequency.valueOf(resultSet.getString(5).toUpperCase(Locale.ROOT)))
                        .build();
                habitDtos.add(habitDto);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDtos;
    }

    private ArrayList<HabitDto> selectAllHabits(Connection connection) {
        ArrayList<HabitDto> habitDtos = new ArrayList<>(0);
        String sql =
                "SELECT " +
                        "id, " +
                        "person_id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency " +
                        "FROM tracking_habit.habit h ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                HabitDto habitDto = HabitDto.builder()
                        .id(resultSet.getLong(1))
                        .personId(resultSet.getLong(2))
                        .title(resultSet.getString(3))
                        .text(resultSet.getString(4))
                        .time(resultSet.getTimestamp(5).toInstant().atOffset(ZoneOffset.UTC))
                        .frequency(Frequency.valueOf(resultSet.getString(6).toUpperCase(Locale.ROOT)))
                        .build();
                habitDtos.add(habitDto);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDtos;
    }

    private HabitDto selectHabitById(Long habitId, Connection connection) {
        HabitDto habitDto = new HabitDto();
        String sqlPerson =
                "SELECT " +
                        "id, " +
                        "person_id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id = '" + habitId + "'";
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPerson);
            while (resultSet.next()) {
                habitDto.setId(resultSet.getLong(1));
                habitDto.setPersonId(resultSet.getLong(2));
                habitDto.setTitle(resultSet.getString(3));
                habitDto.setText(resultSet.getString(4));
                habitDto.setTime(resultSet.getTimestamp(5).toInstant().atOffset(ZoneOffset.UTC));
                habitDto.setFrequency(Frequency.valueOf(resultSet.getString(6).toUpperCase(Locale.ROOT)));
            }
            resultSet.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDto;
    }

    private Long getLastId(Connection connection) {
        String lastIdSql =
                "SELECT id FROM tracking_habit.habit ORDER BY id DESC LIMIT 1";
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

    private Long insertHabit(HabitDto habitDto, Connection connection) {
        System.out.println("HabitDto " + habitDto);
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.habit " +
                        "(id, " +
                        "person_id, " +
                        "title, " +
                        "text, " +
                        "time, " +
                        "frequency) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(insertDataSql);

            preparedStatement.setLong(1, habitDto.getId());
            preparedStatement.setLong(2, habitDto.getPersonId());
            preparedStatement.setString(3, habitDto.getTitle());
            preparedStatement.setString(4, habitDto.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(habitDto.getTime().toLocalDateTime()));
            preparedStatement.setString(6, habitDto.getFrequency().name().toUpperCase(Locale.ROOT));

            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return habitDto.getId();
    }

    private void update(HabitDto habitDto, Connection connection) {
        String title = habitDto.getTitle();
        String text = habitDto.getTitle();
        String frequency = habitDto.getFrequency().name();
        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.habit h " +
                        "SET h.title = '" + title + "', " +
                        "h.text = '" + text + "', " +
                        "h.frequency = '" + frequency + "' " +
                        "WHERE h.id = '" + habitDto.getId() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(updateDataSql);
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteHabitDto(Long habitId, Connection connection) {
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id = '" + habitId + "'";
        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteStatusDto(Long habitId, Connection connection) {
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id = '" + habitId + "'";
        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
