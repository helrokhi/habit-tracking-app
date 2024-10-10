package ru.ylab;

import ru.ylab.controller.AuthController;
import ru.ylab.dto.Database;

public class App {
    public static void main(String[] args) {
        Database database = new Database();
        new AuthController(database).start();
    }
}
