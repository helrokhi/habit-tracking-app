package ru.ylab.repository;

import ru.ylab.config.Database;

public interface DatabaseRepository {
    String URL_DB = new Database().getUrl();
    String USER_DB = new Database().getUser();
    String PASSWORD_DB = new Database().getPassword();
}
