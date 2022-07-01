package ru.netology.helper;

import lombok.Data;
import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class UserData {
        private final String login;
        private final String password;
    }

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    @Value
    public static class VerifyCode {
        private final String login;
        private final String code;
    }

    public static VerifyCode getValidCode(String login) {
        return new VerifyCode(login, SQLHelper.getVerifyCodeByLogin(login));
    }

    @Data
    public static class CardData {
        private final String id;
        private String number;
        private final String balance;
    }

    @Value
    public static class TransferData {
        private final String from;
        private final String to;
        private final String amount;
    }
}
