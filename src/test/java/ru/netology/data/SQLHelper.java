package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;

public class SQLHelper {
    private static final String datasource = System.getProperty("datasource");

    @SneakyThrows
    public static void databaseCleanUp() {
        var runner = new QueryRunner();
        var deleteFromOrder = "DELETE FROM order_entity;";
        var deleteFromCredit = "DELETE FROM credit_request_entity;";
        var deleteFromPayment = "DELETE FROM payment_entity;";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            runner.update(connection, deleteFromOrder);
            runner.update(connection, deleteFromCredit);
            runner.update(connection, deleteFromPayment);
        }
    }

    @SneakyThrows
    public static CardInfo getCreditInfo() {
        var runner = new QueryRunner();
        var creditRequestInfo = "SELECT * FROM credit_request_entity WHERE created = (SELECT MAX(created) FROM credit_request_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "adm", "9mRE")) {
            return runner.query(connection, creditRequestInfo, new BeanHandler<>(CardInfo.class));
        }
    }

    @SneakyThrows
    public static CardInfo getPaymentInfo() {
        var runner = new QueryRunner();
        var paymentInfo = "SELECT * FROM payment_entity WHERE created = (SELECT MAX(created) FROM payment_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, paymentInfo, new BeanHandler<>(CardInfo.class));
        }
    }

    @SneakyThrows
    public static CardInfo getOrderInfo() {
        var runner = new QueryRunner();
        var orderInfo = "SELECT * FROM order_entity WHERE created = (SELECT MAX(created) FROM order_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, orderInfo, new BeanHandler<>(CardInfo.class));
        }
    }
}