package ru.netology.data;

import lombok.Data;

@Data
public class UserData {
    private final String login;
    private final String password;

    public UserData() {
        this.login = "vasya";
        this.password = "qwerty123";
    }
}
