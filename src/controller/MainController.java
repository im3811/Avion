package main.java.travelfinder.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.travelfinder.dao.AccommodationDAO;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.view.MainView;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for the main screen with search functionality
 */
public class MainController {
    private MainView view;
    private AccommodationDAO accommodationDAO;
    private String currentUser;
    private boolean isAdmin = false;
    
    private Runnable onLogout;
    
    public MainController(MainView view, AccommodationDAO accommodationDAO) {
        this.view = view;
        this.accommodationDAO = accommodationDAO;
        
        // Setup view event handlers
        this.view.setOnSearchButtonClicked((location, checkInDate, checkOutDate, guests) -> {
            handleSearch(location, checkInDate, checkOutDate, guests);
        });
        
        this.view.setOnLogoutButtonClicked(() -> {
            handleLogout();
        });
        
        this.view.setOnProfileButtonClicked(() -> {
            handleShowProfile();
        });
        
        this.view.setOnBookingsButtonClicked(() -> {
            handleShowBookings();
        });
        
        this.view.setOnAdminButtonClicked(() -> {
            handleShowAdminPanel();
        });
        
        // Load featured destinations
        loadFeaturedDestinations();
    }
    
    public void showView() {
        // Update UI based on user type
        view.updateUserInfo(currentUser, isAdmin);
        view.show();
    }
    
    public void setCurrentUser(String username) {
        this.currentUser = username;
        this.isAdmin = username.equals("admin");
    }
    
    public void setOnLogout(Runnable onLogout) {
        this.onLogout = onLogout;
    }
    
    private void handleSearch(String location, LocalDate checkInDate, LocalDate checkOutDate, String guests) {
        // Validate inputs
        if (location == null || location.trim().isEmpty()) {
            view.showAlert("Please select a location");
            return;
        }
        
        // Use the DAO to get search results
        List<Accommodation> accommodations = accommodationDAO.search(location);
        
        // Pass search criteria and results to the SearchController
        view.showSearchResults(location, checkInDate, checkOutDate, guests, accommodations);
    }
    
    private void loadFeaturedDestinations() {
        // In a real app, this would load from the database
        // For now, use mock data
        ObservableList<Accommodation> featuredAccommodations = FXCollections.observableArrayList();
        
        // Get from DAO or use mock data
        List<Accommodation> featured = accommodationDAO.getFeatured();
        if (featured != null && !featured.isEmpty()) {
            featuredAccommodations.addAll(featured);
        } else {
            // Add mock data if DAO returns empty
            // This can be removed once the database is connected
            view.loadMockFeaturedDestinations();
        }
    }
    
    private void handleLogout() {
        if (onLogout != null) {
            onLogout.run();
        }
    }
    
    private void handleShowProfile() {
        if ("Guest".equals(currentUser)) {
            view.showAlert("Please login to view your profile");
            return;
        }
        
        view.showUserProfile();
    }
    
    private void handleShowBookings() {
        if ("Guest".equals(currentUser)) {
            view.showAlert("Please login to view your bookings");
            return;
        }
        
        view.showBookingHistory();
    }
    
    private void handleShowAdminPanel() {
        if (!isAdmin) {
            view.showAlert("Access denied: Admin privileges required");
            return;
        }
        
        view.showAdminPanel();
    }
}