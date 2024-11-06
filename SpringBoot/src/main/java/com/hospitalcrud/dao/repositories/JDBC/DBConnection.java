package com.hospitalcrud.dao.repositories.JDBC;

import com.hospitalcrud.config.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBConnection {
    private Configuration config;

    public DBConnection() {
        this.config = Configuration.getInstance();
    }

    public Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(config.getProperty("urlDB"),
                config.getProperty("user_name"),
                config.getProperty("password"));
    }
}
