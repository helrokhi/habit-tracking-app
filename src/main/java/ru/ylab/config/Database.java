package ru.ylab.config;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class Database {
    private final String url;
    private final String user;
    private final String password;

    public Database() {
        this.url = driver("db.url");
        this.user = driver("db.user");
        this.password = driver("db.password");
    }

    /**
     * Получение данных из файла config.properties
     */
    public String driver(String quest) {
        String path = "C:\\Users\\Asus\\IdeaProjects\\habit-tracking-app\\src\\main\\resources\\" +
                "config.properties";
        Properties property = new Properties();
        String result = "";

        try (InputStream input = new FileInputStream(path)) {
            property.load(input);

            result = property.getProperty(quest);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }
}
