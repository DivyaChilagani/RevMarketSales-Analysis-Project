package com.revmarketsales.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String url;
    private static final String username;
    private static final String password;

    static  {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }

            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("config.url");
            username = properties.getProperty("config.username");
            password = properties.getProperty("config.password");
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new RuntimeException("Database connection failed");
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
//        System.out.println("Database connection is successful");
        return connection;
    }
}
