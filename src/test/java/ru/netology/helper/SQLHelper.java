package ru.netology.helper;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner;
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        runner = new QueryRunner();
        conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void setDown() {
        setUp();
        reloadVerifyCodeTable();
        var sqlQueryOne = "DELETE FROM card_transactions;";
        var sqlQueryTwo = "DELETE FROM cards;";
        var sqlQueryThree = "DELETE FROM users;";
        runner.update(conn, sqlQueryOne);
        runner.update(conn, sqlQueryTwo);
        runner.update(conn, sqlQueryThree);
    }

    @SneakyThrows
    public static void reloadVerifyCodeTable() {
        setUp();
        var sqlQuery = "DELETE FROM auth_codes;";
        runner.update(conn, sqlQuery);
    }

    @SneakyThrows
    public static void reloadBalanceCards(String id, int balance) {
        setUp();
        var sqlQuery = "UPDATE cards " +
                "SET balance_in_kopecks = ? " +
                "WHERE id IN (?);";
        runner.update(conn, sqlQuery, balance * 100, id);
    }

    @SneakyThrows
    public static String getVerifyCodeByLogin(String login) {
        setUp();
        var sqlQuery = "SELECT code FROM auth_codes " +
                "JOIN users ON user_id = users.id " +
                "WHERE login IN (?) " +
                "ORDER BY created DESC LIMIT 1;";
        return runner.query(conn, sqlQuery, new ScalarHandler<String>(), login);
    }

    @SneakyThrows
    public static String getNumberCardById(String id) {
        setUp();
        var sqlQuery = "SELECT number FROM cards WHERE id IN (?);";
        return runner.query(conn, sqlQuery, new ScalarHandler<String>(), id);
    }

    @SneakyThrows
    public static int getBalanceCardById(String id) {
        setUp();
        var sqlQuery = "SELECT balance_in_kopecks FROM cards WHERE id IN (?);";
        return runner.query(conn, sqlQuery, new ScalarHandler<Integer>(), id) / 100;
    }
}
