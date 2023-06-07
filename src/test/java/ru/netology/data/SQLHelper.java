package ru.netology.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    public static Connection getConnection() throws SQLException {
        String url = System.getProperty("sut.url");
        String login = System.getProperty("db.user");
        String password = System.getProperty("db.password");
        final Connection connection = DriverManager.getConnection(url, login, password);
        return connection;
    }
}
