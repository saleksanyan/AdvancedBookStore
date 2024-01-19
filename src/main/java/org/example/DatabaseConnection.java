package org.example;
import java.sql.*;

/**
 * this class manages database connection
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/bookstoredb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
