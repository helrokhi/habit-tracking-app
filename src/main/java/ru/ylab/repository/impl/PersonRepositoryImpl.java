package ru.ylab.repository.impl;

import ru.ylab.dto.PersonDto;
import ru.ylab.dto.UserAuthDto;
import ru.ylab.mapper.PersonMapper;
import ru.ylab.repository.DatabaseRepository;
import ru.ylab.repository.PersonRepository;
import ru.ylab.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;

public class PersonRepositoryImpl implements PersonRepository, DatabaseRepository {
    @Override
    public PersonDto getPersonDto(String email, String password) {
        PersonDto personDto = new PersonDto();

        UserRepository userRepository = new UserRepositoryImpl();
        UserAuthDto userAuthDto = userRepository.getUserAuthDto(email, password);

        Long userId = userAuthDto.getId();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            personDto = selectPerson(userId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    @Override
    public PersonDto getPersonDtoById(Long personId) {
        PersonDto personDto = new PersonDto();

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            personDto = selectPersonById(personId, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    @Override
    public PersonDto createPerson(UserAuthDto userAuthDto) {
        PersonDto personDto = PersonMapper.INSTANCE.userDtoToPersonDtoForCreate(userAuthDto);
        Long personId = 0L;

        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            Long lastId = getLastId(connection);

            personDto.setId(lastId + 1);

            personId = insertPerson(userAuthDto, personDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personId);
    }

    @Override
    public PersonDto updatePerson(PersonDto personDto) {
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            updatePersonDto(personDto, connection);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return getPersonDtoById(personDto.getId());
    }

    @Override
    public void deletePerson(PersonDto personDto) {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
            connection.setAutoCommit(false);

            deletePersonDto(personDto.getId(), connection);
            deleteUserAuthDto(personDto.getEmail(), connection);

            String habitIds = selectHabitIds(personDto.getId(), connection);

            if (!habitIds.isBlank()) {
                deleteHabitDtos(habitIds, connection);
                deleteStatusDtos(habitIds, connection);
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
    }

    @Override
    public ArrayList<PersonDto> getAllPersonDtos(PersonDto personDto) {
        ArrayList<PersonDto> personDtos = new ArrayList<>(0);
        try (Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)) {
            personDtos = selectAllPersons(personDto, connection);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDtos;
    }

    private ArrayList<PersonDto> selectAllPersons(PersonDto personDto, Connection connection) {
        ArrayList<PersonDto> personDtos = new ArrayList<>(0);
        Long personId = personDto.getId();
        String sql =
                "SELECT " +
                        "p.id, " +
                        "p.name, " +
                        "p.is_blocked, " +
                        "u.email, " +
                        "u.password " +
                        "FROM tracking_habit.person p, " +
                        "tracking_habit.user u " +
                        "WHERE p.id != '" + personId + "' " +
                        "AND p.user_id = u.id";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                PersonDto dto = new PersonDto();
                dto.setId(resultSet.getLong(1));
                dto.setName(resultSet.getString(2));
                dto.setIsBlocked(resultSet.getBoolean(3));
                dto.setEmail(resultSet.getString(4));
                dto.setPassword(resultSet.getString(5));
                personDtos.add(dto);
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDtos;
    }

    private PersonDto selectPerson(Long userId, Connection connection) {
        PersonDto personDto = new PersonDto();
        String sqlPerson =
                "SELECT " +
                        "p.id, " +
                        "p.name, " +
                        "p.is_blocked, " +
                        "u.email, " +
                        "u.password " +
                        "FROM tracking_habit.person p, " +
                        "tracking_habit.user u " +
                        "WHERE p.user_id = '" + userId + "' " +
                        "AND u.id = '" + userId + "'";

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPerson);
            while (resultSet.next()) {
                personDto.setId(resultSet.getLong(1));
                personDto.setName(resultSet.getString(2));
                personDto.setIsBlocked(resultSet.getBoolean(3));
                personDto.setEmail(resultSet.getString(4));
                personDto.setPassword(resultSet.getString(5));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    private PersonDto selectPersonById(Long personId, Connection connection) {
        PersonDto personDto = PersonDto.builder().build();

        String sqlPerson =
                "SELECT " +
                        "p.id, " +
                        "p.name, " +
                        "p.is_blocked, " +
                        "u.email, " +
                        "u.password " +
                        "FROM tracking_habit.person p, " +
                        "tracking_habit.user u " +
                        "WHERE p.id = '" + personId + "' " +
                        "AND u.id = p.user_id";

        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPerson);
            while (resultSet.next()) {
                personDto.setId(resultSet.getLong(1));
                personDto.setName(resultSet.getString(2));
                personDto.setIsBlocked(resultSet.getBoolean(3));
                personDto.setEmail(resultSet.getString(4));
                personDto.setPassword(resultSet.getString(5));
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto;
    }

    private void updatePersonDto(PersonDto personDto, Connection connection) {
        String personId = personDto.getId().toString();
        String name = personDto.getName();
        Boolean isBlocked = personDto.getIsBlocked();
        String email = personDto.getEmail();
        String password = personDto.getPassword();

        String updateDataSql =
                "UPDATE " +
                        "tracking_habit.person p " +
                        "JOIN tracking_habit.user u " +
                        "ON p.user_id = u.id" +
                        "SET p.name = '" + name + "', " +
                        "p.is_blocked = '" + isBlocked + "', " +
                        "u.email = '" + email + "', " +
                        "u.password = '" + password + "' " +
                        "WHERE p.id = '" + personId + "' " +
                        "AND p.user_id = u.id";

        try {
            Statement statement = connection.createStatement();
            statement.execute(updateDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deletePersonDto(Long personId, Connection connection) {
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.person p " +
                        "WHERE p.id = '" + personId + "'";

        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteUserAuthDto(String email, Connection connection) {
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.user u " +
                        "WHERE u.email = '" + email + "'";

        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteHabitDtos(String habitIds, Connection connection) {
        System.out.println("String " + habitIds);
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.id IN (" + habitIds + ")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void deleteStatusDtos(String habitIds, Connection connection) {
        System.out.println("String " + habitIds);
        String deleteDataSql =
                "DELETE " +
                        "FROM tracking_habit.habit_history h " +
                        "WHERE h.habit_id IN (" + habitIds + ")";

        try {
            Statement statement = connection.createStatement();
            statement.execute(deleteDataSql);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private Long getLastId(Connection connection) {
        String lastIdSql =
                "SELECT id FROM tracking_habit.person ORDER BY id DESC LIMIT 1";

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

    private String selectHabitIds(Long personId, Connection connection) {
        StringBuilder sb = new StringBuilder();
        String sql =
                "SELECT " +
                        "id " +
                        "FROM tracking_habit.habit h " +
                        "WHERE h.person_id = '" + personId + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                sb.append(!sb.isEmpty() ? ", " : "")
                        .append("'")
                        .append(resultSet.getLong(1))
                        .append("'");
            }
            resultSet.close();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return sb.toString();
    }

    private Long insertPerson(UserAuthDto userAuthDto, PersonDto personDto, Connection connection) {
        String insertDataSql =
                "INSERT INTO " +
                        "tracking_habit.person " +
                        "(id, " +
                        "user_id, " +
                        "name, " +
                        "is_blocked) " +
                        "VALUES " +
                        "(?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertDataSql);
            preparedStatement.setLong(1, personDto.getId());
            preparedStatement.setLong(2, userAuthDto.getId());
            preparedStatement.setString(3, personDto.getName());
            preparedStatement.setBoolean(4, personDto.getIsBlocked());
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return personDto.getId();
    }
}
