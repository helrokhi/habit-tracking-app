package ru.ylab.repository.impl;

import ru.ylab.dto.RegUser;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.dto.enums.Role;
import ru.ylab.mapper.UserMapper;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.UserRepository;

import java.sql.*;
import java.util.Locale;

public class UserRepositoryImpl implements DatabaseRepository, UserRepository {
    @Override
    public UserAuthDto getUserAuthDto(String email, String password) {
        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            userAuthDto = selectUser(email, password, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    @Override
    public UserAuthDto getUserAuthDtoByEmail(String email) {
        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            userAuthDto = selectUserByEmail(email, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    @Override
    public UserAuthDto getUserAuthDtoById(Long userId) {
        UserAuthDto userAuthDto = new UserAuthDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            userAuthDto = selectUserById(userId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    @Override
    public UserAuthDto createUser(RegUser regUser) {
        Long userId = 0L;

        UserAuthDto userAuthDto = UserMapper.INSTANCE.regToDtoForCreate(regUser);

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            Long lastId = getLastId(connection);

            userAuthDto.setId(lastId + 1);

            userId = insertUser(userAuthDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getUserAuthDtoById(userId);
    }

    @Override
    public UserAuthDto updateUser(UserAuthDto userAuthDto) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            updateUserAuthDto(userAuthDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getUserAuthDtoById(userAuthDto.getId());
    }

    private UserAuthDto selectUser(String email, String password, Connection connection) {
        UserAuthDto userAuthDto = UserAuthDto.builder().build();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.email = '" + email + "'" + " AND " + "u.password = '" + password + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlUser);
            while (resultSet.next()) {

                userAuthDto.setId(resultSet.getLong(1));
                userAuthDto.setEmail(resultSet.getString(2));
                userAuthDto.setPassword(resultSet.getString(3));
                userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    private UserAuthDto selectUserByEmail(String email, Connection connection) {
        UserAuthDto userAuthDto = new UserAuthDto();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.email = '" + email + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlUser);
            while (resultSet.next()) {

                userAuthDto.setId(resultSet.getLong(1));
                userAuthDto.setEmail(resultSet.getString(2));
                userAuthDto.setPassword(resultSet.getString(3));
                userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    private UserAuthDto selectUserById(Long userId, Connection connection) {
        UserAuthDto userAuthDto = new UserAuthDto();
        String sqlUser =
                "SELECT " +
                        "id, " +
                        "email, " +
                        "password, " +
                        "role " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.id = '" + userId + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlUser);
            while (resultSet.next()) {
                userAuthDto.setId(resultSet.getLong(1));
                userAuthDto.setEmail(resultSet.getString(2));
                userAuthDto.setPassword(resultSet.getString(3));
                userAuthDto.setRole(Role.valueOf(resultSet.getString(4)));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto;
    }

    private Long insertUser(UserAuthDto userAuthDto, Connection connection) {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.user " +
                        "(id, " +
                        "email, " +
                        "password, " +
                        "role) " +
                        "VALUES " +
                        "(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
            preparedStatement.setLong(1, userAuthDto.getId());
            preparedStatement.setString(2, userAuthDto.getEmail());
            preparedStatement.setString(3, userAuthDto.getPassword());
            preparedStatement.setString(4, userAuthDto.getRole().name().toUpperCase(Locale.ROOT));
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return userAuthDto.getId();
    }

    private void updateUserAuthDto(UserAuthDto userAuthDto, Connection connection) {
        String userId = userAuthDto.getId().toString();
        String password = userAuthDto.getPassword();
        String email = userAuthDto.getEmail();

        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.user u " +
                        "SET email = '" + email + "' " +
                        "SET password = '" + password + "' " +
                        "WHERE u.id = '" + userId + "'";

        try {
            Statement statement = connection.createStatement();
            statement.execute(updateDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Long getLastId(Connection connection) {
        String lastIdSql =
                "SELECT id FROM tracking_habit.user ORDER BY id DESC LIMIT 1";

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
