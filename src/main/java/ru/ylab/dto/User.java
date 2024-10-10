package ru.ylab.dto;

import ru.ylab.dto.enums.Role;

import java.util.Objects;

public class User {
    private String email;
    private String password;
    private final Role role;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        role = Role.USER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
