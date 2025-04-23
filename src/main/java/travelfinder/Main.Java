package main.java.travelfinder;

import javafx.application.Application;
import javafx.stage.Stage;

import main.java.travelfinder.controller.*;
import main.java.travelfinder.view.*;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.utils.DatabaseConnection;

/**
 * Main entry point for the Travel Finder application
 */
public class Main extends Application {
    
    private Stage primaryStage;
    
    // Views
    private LoginView loginView;
    private MainView mainView;
    private AccommodationDetailsView accommodationDetailsView;
    private BookingView bookingView;
    private SearchResultsView searchResultsView;
    private UserProfileView userProfileView;
    private AdminView adminView;
    
    // Controllers
    private LoginController loginController;
    private MainController mainController;
    private AccommodationController accommodationController;
    private BookingController bookingController;
    private SearchController searchController;
    private UserController userController;
    private AdminController adminController;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            primaryStage.setTitle("Travel Finder");
            
            // Initialize views
            initializeViews();
            
            // Initialize controllers
            initializeControllers();
            
            // Setup navigation
            setupNavigation();
            
            // Start with login screen
            loginController.showView();
            
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);
            primaryStage.show();
            
        } catch (Exception e) {
            AlertUtil.showError("Application Error", "Failed to start application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeViews() {
        // Initialize all views
        loginView = new LoginView(primaryStage);
        mainView = new MainView(primaryStage);
        accommodationDetailsView = new AccommodationDetailsView();
        bookingView = new BookingView();
        searchResultsView = new SearchResultsView();
        userProfileView = new UserProfileView();
        adminView = new AdminView();
    }
    
    private void initializeControllers() {
        // Initialize all controllers and connect them to views
        loginController = new LoginController(loginView);
        
        mainController = new MainController(mainView);
        
        accommodationController = new AccommodationController();
        accommodationController.setDetailsView(accommodationDetailsView);
        
        bookingController = new BookingController();
        bookingController.setBookingView(bookingView);
        
        searchController = new SearchController();
        searchController.setSearchResultsView(searchResultsView);
        searchController.setAccommodationController(accommodationController);
        
        userController = new UserController();
        userController.setProfileView(userProfileView);
        
        adminController = new AdminController();
        adminController.setAdminView(adminView);
    }
    
    private void setupNavigation() {
        // Set up navigation between controllers
        
        // Login -> Main
        loginController.setOnLoginSuccess(username -> {
            mainController.setCurrentUser(username);
            mainController.showView();
        });
        
        loginController.setOnGuestAccess(() -> {
            mainController.setCurrentUser("Guest");
            mainController.showView();
        });
        
        // Main -> Login (Logout)
        mainController.setOnLogout(() -> {
            loginController.showView();
        });
        
        // Connect controllers
        accommodationController.setBookingController(bookingController);
        
        // Main controller to other controllers
        mainView.setOnSearchAction(criteria -> searchController.performSearch(criteria));
        mainView.setOnViewAccommodation(id -> accommodationController.showAccommodationDetails(id));
        mainView.setOnMyBookings(() -> bookingController.showUserBookings(mainController.getCurrentUser()));
        mainView.setOnUserProfile(() -> userController.showUserProfile(mainController.getCurrentUser()));
    }
    
    @Override
    public void stop() {
        // Clean up resources
        try {
            // Close database connections
            DatabaseConnection.closeConnection();
            
            // Any other cleanup
            System.out.println("Application closing, cleaning up resources...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}