package ru.ylab.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
@JsonAutoDetect
public class RegUser {
    private final String email;
    private final String password;

    public RegUser(String email, String password) {
        this.email = setEmail(email);
        this.password = password;
    }

    public String setEmail(String email) {
        return  (isValidEmail(email))? email : "";
    }

    public String setPassword(String password) {
        return (isValidPassword(password))? password : "";
    }


    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public boolean isValidPassword(String password) {
        String regexPassword = "(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}";
        return password.matches(regexPassword);
    }

    @Override
    public String toString() {
        return "RegUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
