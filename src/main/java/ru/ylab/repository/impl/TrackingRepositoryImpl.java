package ru.ylab.repository.impl;

import ru.ylab.dto.enums.Frequency;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.TrackingRepository;

import java.sql.*;

public class TrackingRepositoryImpl implements DatabaseRepository, TrackingRepository {

    @Override
    public int getCount(Long personId) {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            count = selectCountHabits(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }

    @Override
    public int getCountIsDoneToday(Long personId) {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            count = selectCountHabitsIsDoneToday(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }

    @Override
    public int getCountNeedDoneToday(Long personId) {
        int count = 0;
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            count = selectCountHabitsNeedDoneToday(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }

    private int selectCountHabits(Long personId, Connection connection) {
        int count = 0;
        String sql =
                "SELECT " +
                        "count(*) " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }

    private int selectCountHabitsIsDoneToday(Long personId, Connection connection) {
        int count = 0;
        String sql =
                "SELECT " +
                        "count(*) " +
                        "FROM tracking_habit.habit_history s " +
                        "JOIN tracking_habit.habit h " +
                        "ON h.id = s.habit_id " +
                        "WHERE s.time >=  CURRENT_DATE " +
                        "AND s.time < CURRENT_DATE + interval '1 day' " +
                        "AND h.person_id = '" + personId + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }

    private int selectCountHabitsNeedDoneToday(Long personId, Connection connection) {
        int count = 0;
        String sql =
                "SELECT " +
                        "count(*) " +
                        "FROM tracking_habit.habit h " +
                        "LEFT JOIN " +
                            "(SELECT habit_id, MAX(time) " +
                            "FROM tracking_habit.habit_history " +
                            "GROUP BY habit_id" +
                            ") st " +
                        "ON h.id = st.habit_id " +
                        "WHERE " +
                        "(h.frequency = 'DAILY' " +
                        "OR st.max IS NULL " +
                        "OR (st.max < CURRENT_DATE  - interval '6 day' " +
                        "AND h.frequency = 'WEEKLY')) " +
                        "AND h.person_id = '" + personId + "' ";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return count;
    }
}
