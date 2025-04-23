package main.java.travelfinder.dao;

import main.java.travelfinder.model.User;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object interface for User entity
 */
public interface UserDAO {
    /**
     * Find a user by their ID
     * @param userId The ID of the user to find
     * @return The user object if found, null otherwise
     */
    User findById(int userId);
    
    /**
     * Find a user by their username
     * @param username The username to search for
     * @return The user object if found, null otherwise
     */
    User findByUsername(String username);
    
    /**
     * Find a user by their email address
     * @param email The email address to search for
     * @return The user object if found, null otherwise
     */
    User findByEmail(String email);
    
    /**
     * Get all users in the system
     * @return A list of all users
     */
    List<User> findAll();
    
    /**
     * Save a new user to the database
     * @param user The user object to save
     * @return true if successful, false otherwise
     */
    boolean save(User user);
    
    /**
     * Update an existing user in the database
     * @param user The user object to update
     * @return true if successful, false otherwise
     */
    boolean update(User user);
    
    /**
     * Delete a user from the database (soft delete)
     * @param userId The ID of the user to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int userId);
    
    /**
     * Validate user credentials for login
     * @param username The username provided
     * @param password The password provided (plain text, will be hashed for comparison)
     * @return true if credentials are valid, false otherwise
     */
    boolean validateCredentials(String username, String password);
    
    /**
     * Count the total number of users
     * @return The total count of users
     */
    int countAll();
    
    /**
     * Count users created since a specific date
     * @param since The date to count from
     * @return The number of users created since the given date
     */
    int countCreatedSince(LocalDate since);
    
    /**
     * Find users created between two dates
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of users created in the date range
     */
    List<User> findCreatedBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Update a user's last login time
     * @param userId The ID of the user
     * @return true if successful, false otherwise
     */
    boolean updateLastLogin(int userId);
}