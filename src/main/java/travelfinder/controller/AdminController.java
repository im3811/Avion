package main.java.travelfinder.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.User;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.view.AdminView;

public class AdminController {
    
    private AdminView adminView;
    
    public AdminController() {
    }
    
    public void setAdminView(AdminView adminView) {
        this.adminView = adminView;
    }
    
    public void showAdminPanel() {
        if (adminView != null) {
            adminView.setupAdminPanel();
            
            adminView.setOnManageUsers(() -> showUserManagement());
            adminView.setOnManageAccommodations(() -> showAccommodationManagement());
            adminView.setOnGenerateReports(() -> generateReports());
            
            adminView.show();
        }
    }
    
    private void showUserManagement() {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database queries
            List<User> users = getMockUsers();
            
            if (adminView != null) {
                adminView.displayUsersList(users);
                
                adminView.setOnAddUser(this::addUser);
                adminView.setOnEditUser(this::editUser);
                adminView.setOnDeactivateUser(this::deactivateUser);
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not load users: " + e.getMessage());
        }
    }
    
    private void showAccommodationManagement() {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database queries
            List<Accommodation> accommodations = getMockAccommodations();
            
            if (adminView != null) {
                adminView.displayAccommodationsList(accommodations);
                
                adminView.setOnAddAccommodation(this::addAccommodation);
                adminView.setOnEditAccommodation(this::editAccommodation);
                adminView.setOnDeactivateAccommodation(this::deactivateAccommodation);
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not load accommodations: " + e.getMessage());
        }
    }
    
    private void generateReports() {
        if (adminView != null) {
            adminView.showReportOptions();
            
            adminView.setOnGenerateBookingReport(this::generateBookingReport);
            adminView.setOnGenerateRevenueReport(this::generateRevenueReport);
            adminView.setOnGenerateOccupancyReport(this::generateOccupancyReport);
        }
    }
    
    private boolean addUser(User user) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("User Added", "User has been added successfully");
                showUserManagement();
            } else {
                AlertUtil.showError("Add Failed", "Could not add user");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Add Error", "Could not add user: " + e.getMessage());
            return false;
        }
    }
    
    private boolean editUser(User user) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("User Updated", "User has been updated successfully");
                showUserManagement();
            } else {
                AlertUtil.showError("Update Failed", "Could not update user");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Update Error", "Could not update user: " + e.getMessage());
            return false;
        }
    }
    
    private boolean deactivateUser(int userId) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("User Deactivated", "User has been deactivated successfully");
                showUserManagement();
            } else {
                AlertUtil.showError("Deactivation Failed", "Could not deactivate user");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Deactivation Error", "Could not deactivate user: " + e.getMessage());
            return false;
        }
    }
    
    private boolean addAccommodation(Accommodation accommodation) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Accommodation Added", "Accommodation has been added successfully");
                showAccommodationManagement();
            } else {
                AlertUtil.showError("Add Failed", "Could not add accommodation");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Add Error", "Could not add accommodation: " + e.getMessage());
            return false;
        }
    }
    
    private boolean editAccommodation(Accommodation accommodation) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Accommodation Updated", "Accommodation has been updated successfully");
                showAccommodationManagement();
            } else {
                AlertUtil.showError("Update Failed", "Could not update accommodation");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Update Error", "Could not update accommodation: " + e.getMessage());
            return false;
        }
    }
    
    private boolean deactivateAccommodation(int accommodationId) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Accommodation Deactivated", "Accommodation has been deactivated successfully");
                showAccommodationManagement();
            } else {
                AlertUtil.showError("Deactivation Failed", "Could not deactivate accommodation");
            }
            
            return success;
        } catch (Exception e) {
            AlertUtil.showError("Deactivation Error", "Could not deactivate accommodation: " + e.getMessage());
            return false;
        }
    }
    
    private void generateBookingReport(String[] dateRange) {
        String fromDate = dateRange[0];
        String toDate = dateRange[1];
        // Placeholder for report generation logic
        AlertUtil.showInfo("Report Generated", "Booking report has been generated for period: " + fromDate + " to " + toDate);
    }
    
    private void generateRevenueReport(String[] dateRange) {
        String fromDate = dateRange[0];
        String toDate = dateRange[1];
        // Placeholder for report generation logic
        AlertUtil.showInfo("Report Generated", "Revenue report has been generated for period: " + fromDate + " to " + toDate);
    }
    
    private void generateOccupancyReport(String[] dateRange) {
        String fromDate = dateRange[0];
        String toDate = dateRange[1];
        // Placeholder for report generation logic
        AlertUtil.showInfo("Report Generated", "Occupancy report has been generated for period: " + fromDate + " to " + toDate);
    }
    
    // Mock methods to provide sample data - will be replaced with DAO
    private List<User> getMockUsers() {
        List<User> users = new ArrayList<>();
        // Add some mock users for testing
        User admin = new User(1, "admin_user", "admin@travelfinder.com", "Admin", "User", "admin");
        admin.setActive(true);
        users.add(admin);
        
        User traveler = new User(2, "john_doe", "john@example.com", "John", "Doe", "traveler");
        traveler.setActive(true);
        users.add(traveler);
        
        User inactive = new User(3, "inactive_user", "inactive@example.com", "Inactive", "User", "traveler");
        inactive.setActive(false);
        users.add(inactive);
        
        return users;
    }
    
    private List<Accommodation> getMockAccommodations() {
        List<Accommodation> accommodations = new ArrayList<>();
        // Add some mock accommodations for testing
        Accommodation miami = new Accommodation(1, "Miami Beach Resort", 5, "Beachfront resort", 3, "123 Beach Drive, Miami, FL 33139", 4.5, 299.99);
        miami.setTypeName("Resort");
        miami.setActive(true);
        accommodations.add(miami);
        
        Accommodation hollywood = new Accommodation(2, "Hollywood Hotel", 1, "Central hotel", 5, "789 Hollywood Blvd, Los Angeles, CA 90028", 4.0, 249.99);
        hollywood.setTypeName("Hotel");
        hollywood.setActive(true);
        accommodations.add(hollywood);
        
        Accommodation inactive = new Accommodation(3, "Inactive Property", 2, "Inactive apartment", 3, "456 Test Ave, Miami, FL 33139", 3.5, 199.99);
        inactive.setTypeName("Apartment");
        inactive.setActive(false);
        accommodations.add(inactive);
        
        return accommodations;
    }
}