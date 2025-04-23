package main.java.travelfinder.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Singleton class for managing database connections
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Database configuration - can be loaded from a properties file
    private String url;
    private String user;
    private String password;
    private String driver;
    
    /**
     * Private constructor to prevent instantiation
     */
    private DatabaseConnection() {
        loadDatabaseProperties();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found: " + driver);
            e.printStackTrace();
        }
    }
    
    /**
     * Get the singleton instance
     * @return The DatabaseConnection instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Get a database connection
     * @return A Connection object
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Database connection established");
            }
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Load database properties from a properties file
     * If file loading fails, default values are used
     */
    private void loadDatabaseProperties() {
        Properties props = new Properties();
        try {
            // Try to load from properties file first
            props.load(new FileInputStream("config/database.properties"));
            
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            
            System.out.println("Database properties loaded from file");
        } catch (IOException e) {
            // Use default values if properties file not found
            System.out.println("Database properties file not found, using defaults");
            url = "jdbc:mysql://localhost:3306/travel_finder?useSSL=false&serverTimezone=UTC";
            user = "root";
            password = "password";
            driver = "com.mysql.cj.jdbc.Driver";
        }
    }
    
    /**
     * Begin a transaction (disable auto-commit)
     */
    public void beginTransaction() {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Error beginning transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Commit the current transaction
     */
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error committing transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Rollback the current transaction
     */
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}