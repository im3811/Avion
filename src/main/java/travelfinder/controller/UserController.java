package main.java.travelfinder.controller;

import java.time.LocalDate;
import java.util.function.Function;
import main.java.travelfinder.model.User;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.view.UserProfileView;

public class UserController {
    
    private UserProfileView profileView;
    
    public UserController() {
    }
    
    public void setProfileView(UserProfileView profileView) {
        this.profileView = profileView;
    }
    
    public void showUserProfile(String username) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database queries
            User user = getMockUser(username);
            
            if (user == null) {
                AlertUtil.showError("Error", "User not found");
                return;
            }
            
            if (profileView != null) {
                profileView.displayUserProfile(user);
                profileView.setOnUpdateProfile(this::updateUserProfile);
                
                // Use lambda instead of method reference to match the expected interface
                profileView.setOnChangePassword((un, oldPass, newPass) -> 
                    changePassword(un, oldPass, newPass));
                
                profileView.show();
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not load user profile: " + e.getMessage());
        }
    }
    
    private boolean updateUserProfile(User updatedUser) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Profile Updated", "Your profile has been updated successfully");
            } else {
                AlertUtil.showError("Update Failed", "Could not update your profile");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Update Error", "Could not update profile: " + e.getMessage());
            return false;
        }
    }
    
    private boolean changePassword(String username, String oldPassword, String newPassword) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Password Changed", "Your password has been changed successfully");
            } else {
                AlertUtil.showError("Password Change Failed", "Could not change your password");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Password Change Error", "Could not change password: " + e.getMessage());
            return false;
        }
    }
    
    public String getUserRole(String username) {
        // Placeholder for DAO implementation
        // This would be replaced with actual database queries
        if (username.equals("admin_user")) {
            return "admin";
        } else {
            return "traveler";
        }
    }
    
    // Mock method to provide sample data - will be replaced with DAO
    private User getMockUser(String username) {
        // Create mock users for demo/testing
        if (username.equals("admin_user")) {
            User admin = new User(1, "admin_user", "admin@travelfinder.com", "Admin", "User", "admin");
            admin.setPhoneNumber("+1-555-000-0000");
            admin.setDateOfBirth(LocalDate.of(1980, 1, 1));
            admin.setAddress("123 Admin Street, Admin City, AC 12345");
            admin.setActive(true);
            return admin;
        } else if (username.equals("john_doe")) {
            User traveler = new User(2, "john_doe", "john@example.com", "John", "Doe", "traveler");
            traveler.setPhoneNumber("+1-555-123-4567");
            traveler.setDateOfBirth(LocalDate.of(1985, 5, 15));
            traveler.setAddress("456 Main Street, Anytown, AT 67890");
            traveler.setActive(true);
            return traveler;
        } else if (username.equals("Guest")) {
            // Limited guest profile
            User guest = new User(0, "Guest", "guest@example.com", "Guest", "User", "traveler");
            guest.setActive(true);
            return guest;
        }
        
        // Username not found
        return null;
    }
}