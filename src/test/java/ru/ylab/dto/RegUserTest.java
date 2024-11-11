package ru.ylab.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.ylab.dto.RegUser;

class RegUserTest {

    @Test
    void isValidEmailResultTrue() {
        String email = "thuel@yahoo.com";

        RegUser regUser = new RegUser(email, "00000000");

        boolean isValid = regUser.isValidEmail(email);
        Assertions.assertTrue(isValid);
    }

    @Test
    void isValidEmailResultFalse() {
        String email = "123456";

        RegUser regUser = new RegUser(email, "00000000");

        boolean isValid = regUser.isValidEmail(email);
        Assertions.assertFalse(isValid);
    }
}