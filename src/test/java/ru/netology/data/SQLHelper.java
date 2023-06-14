package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String db = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    @SneakyThrows
    public static void databaseCleanUp() {
        var runner = new QueryRunner();
        var deleteFromOrder = "DELETE FROM order_entity;";
        var deleteFromCredit = "DELETE FROM credit_request_entity;";
        var deleteFromPayment = "DELETE FROM payment_entity;";

        try (var connection = DriverManager.getConnection(db, user, password)) {
            runner.update(connection, deleteFromOrder);
            runner.update(connection, deleteFromCredit);
            runner.update(connection, deleteFromPayment);
        }
    }

    public static String getPaymentStatus() {
        var statusSQL = "SELECT status FROM payment_entity;";
        return getData(statusSQL);
    }

    public static String getCreditStatus() {
        var statusSQL = "SELECT status FROM credit_request_entity;";
        return getData(statusSQL);
    }

    public static String getOrderCount() {
        var statusSQL = "SELECT COUNT(*) FROM order_entity;";
        var runner = new QueryRunner();
        Long count = null;
        try (var connection = DriverManager.getConnection(db, user, password)) {
            count = runner.query(connection, statusSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Long.toString(count);
    }

    private static String getData(String query) {
        var runner = new QueryRunner();
        String data = "";
        try (var connection = DriverManager.getConnection(db, user, password)) {
            data = runner.query(connection, query, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}