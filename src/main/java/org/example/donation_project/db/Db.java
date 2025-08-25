package org.example.donation_project.db;

import org.example.donation_project.AppConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Db {
    private Db() {}

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
            // JDBC 4+ auto-loads via SPI; explicit load helps in some environments.
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(AppConfig.JDBC_URL, AppConfig.DB_USER, AppConfig.DB_PASS);
    }
}
