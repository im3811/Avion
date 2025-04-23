package main.java.travelfinder.controller;

import main.java.travelfinder.dao.AccommodationDAO;
import main.java.travelfinder.dao.BookingDAO;
import main.java.travelfinder.dao.UserDAO;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.User;
import main.java.travelfinder.view.AdminView;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Controller for admin panel functionality
 */
public class AdminController {
    private AdminView view;
    private UserDAO userDAO;
    private AccommodationDAO accommodationDAO;
    private BookingDAO bookingDAO;
    
    private Runnable onBackToMain;
    private Consumer<User> onEditUser;
    private Consumer<Accommodation> onEditAccommodation;
    private Consumer<Booking> onViewBooking;
    
    public AdminController(AdminView view, UserDAO userDAO, 
                          AccommodationDAO accommodationDAO, BookingDAO bookingDAO) {
        this.view = view;
        this.userDAO = userDAO;
        this.accommodationDAO = accommodationDAO;
        this.bookingDAO = bookingDAO;
        
        // Setup view event handlers
        this.view.setOnBackButtonClicked(() -> {
            if (onBackToMain != null) {
                onBackToMain.run();
            }
        });
        
        this.view.setOnEditUserClicked(user -> {
            if (onEditUser != null) {
                onEditUser.accept(user);
            }
        });
        
        this.view.setOnEditAccommodationClicked(accommodation -> {
            if (onEditAccommodation != null) {
                onEditAccommodation.accept(accommodation);
            }
        });
        
        this.view.setOnViewBookingClicked(booking -> {
            if (onViewBooking != null) {
                onViewBooking.accept(booking);
            }
        });
        
        this.view.setOnUpdateUserStatusClicked((user, active) -> {
            updateUserStatus(user, active);
        });
        
        this.view.setOnUpdateAccommodationStatusClicked((accommodation, active) -> {
            updateAccommodationStatus(accommodation, active);
        });
        
        this.view.setOnUpdateBookingStatusClicked((booking, statusId) -> {
            updateBookingStatus(booking, statusId);
        });
        
        this.view.setOnGenerateReportClicked((reportType, startDate, endDate) -> {
            generateReport(reportType, startDate, endDate);
        });
    }
    
    public void showAdminPanel() {
        // Load all necessary data for admin panel
        loadUsersData();
        loadAccommodationsData();
        loadBookingsData();
        loadStatistics();
        
        view.show();
    }
    
    public void setOnBackToMain(Runnable handler) {
        this.onBackToMain = handler;
    }
    
    public void setOnEditUser(Consumer<User> handler) {
        this.onEditUser = handler;
    }
    
    public void setOnEditAccommodation(Consumer<Accommodation> handler) {
        this.onEditAccommodation = handler;
    }
    
    public void setOnViewBooking(Consumer<Booking> handler) {
        this.onViewBooking = handler;
    }
    
    private void loadUsersData() {
        List<User> users = userDAO.findAll();
        view.displayUsers(users);
    }
    
    private void loadAccommodationsData() {
        List<Accommodation> accommodations = accommodationDAO.findAll();
        view.displayAccommodations(accommodations);
    }
    
    private void loadBookingsData() {
        List<Booking> recentBookings = bookingDAO.findRecent(50); // Last 50 bookings
        view.displayBookings(recentBookings);
    }
    
    private void loadStatistics() {
        // Calculate statistics for dashboard
        int totalUsers = userDAO.countAll();
        int totalAccommodations = accommodationDAO.countAll();
        int totalBookings = bookingDAO.countAll();
        double totalRevenue = bookingDAO.calculateTotalRevenue();
        
        // Recent activity (last 30 days)
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        int newUsers = userDAO.countCreatedSince(thirtyDaysAgo);
        int newBookings = bookingDAO.countCreatedSince(thirtyDaysAgo);
        double monthlyRevenue = bookingDAO.calculateRevenueSince(thirtyDaysAgo);
        
        view.displayStatistics(totalUsers, totalAccommodations, totalBookings, totalRevenue,
                              newUsers, newBookings, monthlyRevenue);
    }
    
    private void updateUserStatus(User user, boolean active) {
        user.setActive(active);
        boolean success = userDAO.update(user);
        
        if (success) {
            view.showAlert(active ? "User activated successfully" : "User deactivated successfully");
            loadUsersData(); // Refresh the user list
        } else {
            view.showAlert("Failed to update user status");
        }
    }
    
    private void updateAccommodationStatus(Accommodation accommodation, boolean active) {
        accommodation.setActive(active);
        boolean success = accommodationDAO.update(accommodation);
        
        if (success) {
            view.showAlert(active ? "Accommodation activated successfully" : "Accommodation deactivated successfully");
            loadAccommodationsData(); // Refresh the accommodation list
        } else {
            view.showAlert("Failed to update accommodation status");
        }
    }
    
    private void updateBookingStatus(Booking booking, int statusId) {
        booking.setStatusId(statusId);
        boolean success = bookingDAO.update(booking);
        
        if (success) {
            view.showAlert("Booking status updated successfully");
            loadBookingsData(); // Refresh the booking list
        } else {
            view.showAlert("Failed to update booking status");
        }
    }
    
    private void generateReport(String reportType, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            view.showAlert("Start date must be before end date");
            return;
        }
        
        switch (reportType) {
            case "USERS":
                generateUsersReport(startDate, endDate);
                break;
            case "BOOKINGS":
                generateBookingsReport(startDate, endDate);
                break;
            case "REVENUE":
                generateRevenueReport(startDate, endDate);
                break;
            default:
                view.showAlert("Unknown report type");
        }
    }
    
    private void generateUsersReport(LocalDate startDate, LocalDate endDate) {
        List<User> users = userDAO.findCreatedBetween(startDate, endDate);
        view.displayUsersReport(users, startDate, endDate);
    }
    
    private void generateBookingsReport(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingDAO.findCreatedBetween(startDate, endDate);
        view.displayBookingsReport(bookings, startDate, endDate);
    }
    
    private void generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        // This would calculate revenue by day/week/month between the dates
        double totalRevenue = bookingDAO.calculateRevenueBetween(startDate, endDate);
        
        // In a real app, this would get more detailed revenue data
        view.displayRevenueReport(totalRevenue, startDate, endDate);
    }
}