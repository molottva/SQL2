package ru.netology.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.CardData;
import ru.netology.data.TransferData;
import ru.netology.data.UserData;
import ru.netology.data.UserVerifyData;

import static org.testng.AssertJUnit.assertEquals;
import static ru.netology.helper.APIHelper.*;
import static ru.netology.helper.SQLHelper.*;

public class TransferTest {
    UserData user;
    String token;
    CardData[] cards;
    int indexCartTo;
    int indexCartFrom;
    int balanceCartTo;
    int balanceCartFrom;

    @BeforeMethod
    public void setUp() {
        reloadVerifyCodeTable();
        user = new UserData();
        auth(user);
        var verifyData = new UserVerifyData(user.getLogin(), getVerifyCodeByLogin(user.getLogin()));
        token = verification(verifyData);
        cards = getCards(token);
        int i = 0;
        for (CardData card : cards) {
            card.setNumber(getNumberCardById(card.getId()));
            i++;
        }
    }

    @AfterMethod
    public void reloadBalance() {
        reloadBalanceCards(cards[indexCartTo].getId(), balanceCartTo);
        reloadBalanceCards(cards[indexCartFrom].getId(), balanceCartFrom);
    }

    @AfterClass
    public void setDown() {
        setDown();
    }

    @Test
    public void shouldTransferHappyPath() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = balanceCartFrom / 2;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo + amount, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom - amount, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    //todo bug
    @Test
    public void shouldNoTransferBoundaryValueOne() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = -1;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    @Test
    public void shouldNoTransferBoundaryValueTwo() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = 0;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    @Test
    public void shouldTransferBoundaryValueThree() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = 1;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo + amount, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom - amount, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    @Test
    public void shouldTransferBoundaryValueFour() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = balanceCartFrom - 1;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo + amount, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom - amount, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    @Test
    public void shouldTransferBoundaryValueFive() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = balanceCartFrom;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo + amount, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom - amount, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    //todo bug
    @Test
    public void shouldNoTransferBoundaryValueSix() {
        indexCartTo = 0;
        indexCartFrom = 1;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = balanceCartFrom + 1;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom, getBalanceCardById(cards[indexCartFrom].getId()));
    }

    @Test
    public void shouldNoTransferSingleCard() {
        indexCartTo = 0;
        indexCartFrom = 0;
        balanceCartTo = Integer.parseInt(cards[indexCartTo].getBalance());
        balanceCartFrom = Integer.parseInt(cards[indexCartFrom].getBalance());
        int amount = balanceCartFrom / 2;

        var transferData = new TransferData(cards[indexCartFrom].getNumber(),
                cards[indexCartTo].getNumber(), String.valueOf(amount));
        transfer(transferData, token);
        assertEquals(balanceCartTo, getBalanceCardById(cards[indexCartTo].getId()));
        assertEquals(balanceCartFrom, getBalanceCardById(cards[indexCartFrom].getId()));
    }
}
