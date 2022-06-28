package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserVerifyData {
    private final String login;
    private final String code;
}
