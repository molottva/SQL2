package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CardData {
    private final String id;
    private String number;
    private final String balance;
}
