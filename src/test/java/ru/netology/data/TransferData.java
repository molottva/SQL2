package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TransferData {
    private final String from;
    private final String to;
    private final String amount;
}
