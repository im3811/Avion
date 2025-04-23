package main.java.travelfinder.controller;

import main.java.travelfinder.dao.BookingDAO;
import main.java.travelfinder.dao.UserDAO;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.User;
import main.java.travelfinder.view.UserProfileView;

import java.util.List;
import java.util.function.Consumer;

/**
 * Controller for user profile management
 */
public class UserController {
    private UserProfileView view;
    private UserDAO userDAO;
    private BookingDAO bookingDAO;
    
    private String currentUsername;
    private User currentUser;
    
    private Runnable onBackToMain;
    private Consumer<Booking> onViewBookingDetails;
    
    public UserController(UserProfileView view, UserDAO userDAO, BookingDAO bookingDAO) {
        this.view = view;
        this.userDAO = userDAO;
        this.bookingDAO = bookingDAO;
        
        // Setup view event handlers
        this.view.setOnBackButtonClicked(() -> {
            if (onBackToMain != null) {
                onBackToMain.run();
            }
        });
        
        this.view.setOnSaveProfileClicked(userDetails -> {
            saveUserProfile(userDetails);
        });
        
        this.view.setOnSavePreferencesClicked(preferences -> {
            saveUserPreferences(preferences);
        });
        
        this.view.setOnChangePasswordClicked((currentPassword, newPassword) -> {
            changePassword(currentPassword, newPassword);
        });
        
        this.view.setOnViewBookingClicked(booking -> {
            if (onViewBookingDetails != null) {
                onViewBookingDetails.accept(booking);
            }
        });
    }
    
    public void showUserProfile(String username) {
        this.currentUsername = username;
        
        // Load user data
        loadUserData();
        
        // Load booking history
        loadBookingHistory();
        
        view.show();
    }
    
    public void setOnBackToMain(Runnable handler) {
        this.onBackToMain = handler;
    }
    
    public void setOnViewBookingDetails(Consumer<Booking> handler) {
        this.onViewBookingDetails = handler;
    }
    
    private void loadUserData() {
        User user = userDAO.findByUsername(currentUsername);
        
        if (user != null) {
            this.currentUser = user;
            
            // Update view with user data
            view.displayUserInfo(user);
        } else {
            view.showAlert("Error loading user data");
            
            if (onBackToMain != null) {
                onBackToMain.run();
            }
        }
    }
    
    private void loadBookingHistory() {
        if (currentUser != null) {
            List<Booking> upcomingBookings = bookingDAO.findUpcomingByUserId(currentUser.getUserId());
            List<Booking> pastBookings = bookingDAO.findPastByUserId(currentUser.getUserId());
            List<Booking> cancelledBookings = bookingDAO.findCancelledByUserId(currentUser.getUserId());
            
            view.displayBookingHistory(upcomingBookings, pastBookings, cancelledBookings);
        }
    }
    
    private void saveUserProfile(UserProfileView.UserDetails userDetails) {
        if (currentUser == null) {
            view.showAlert("User data not loaded");
            return;
        }
        
        // Update user object with new details
        currentUser.setFirstName(userDetails.getFirstName());
        currentUser.setLastName(userDetails.getLastName());
        currentUser.setEmail(userDetails.getEmail());
        currentUser.setPhoneNumber(userDetails.getPhoneNumber());
        currentUser.setDateOfBirth(userDetails.getDateOfBirth());
        currentUser.setAddress(userDetails.getAddress());
        
        // Save to database
        boolean success = userDAO.update(currentUser);
        
        if (success) {
            view.showAlert("Profile updated successfully!");
        } else {
            view.showAlert("Failed to update profile");
        }
    }
    
    private void saveUserPreferences(String preferences) {
        if (currentUser == null) {
            view.showAlert("User data not loaded");
            return;
        }
        
        // Update user preferences
        currentUser.setPreferences(preferences);
        
        // Save to database
        boolean success = userDAO.update(currentUser);
        
        if (success) {
            view.showAlert("Preferences saved successfully!");
        } else {
            view.showAlert("Failed to save preferences");
        }
    }
    
    private void changePassword(String currentPassword, String newPassword) {
        if (currentUser == null) {
            view.showAlert("User data not loaded");
            return;
        }
        
        // Verify current password
        if (!userDAO.validateCredentials(currentUsername, currentPassword)) {
            view.showAlert("Current password is incorrect");
            return;
        }
        
        // Update password (in a real app, this would hash the password)
        currentUser.setPasswordHash(newPassword);
        
        // Save to database
        boolean success = userDAO.update(currentUser);
        
        if (success) {
            view.showAlert("Password changed successfully!");
        } else {
            view.showAlert("Failed to change password");
        }
    }
}