package main.java.travelfinder.controller;

import javafx.application.Platform;
import main.java.travelfinder.view.MainView;

public class MainController {
    
    private final MainView mainView;
    private String currentUser;
    private Runnable onLogout;
    
    private SearchController searchController;
    private AccommodationController accommodationController;
    private BookingController bookingController;
    private UserController userController;
    private AdminController adminController;
    
    public MainController(MainView mainView) {
        this.mainView = mainView;
        
        this.searchController = new SearchController();
        this.accommodationController = new AccommodationController();
        this.bookingController = new BookingController();
        this.userController = new UserController();
        
        this.mainView.setOnSearchAction(criteria -> searchController.performSearch(criteria));
        this.mainView.setOnViewAccommodation(id -> accommodationController.showAccommodationDetails(id));
        this.mainView.setOnMyBookings(() -> bookingController.showUserBookings(currentUser));
        this.mainView.setOnUserProfile(() -> userController.showUserProfile(currentUser));
        this.mainView.setOnLogout(() -> handleLogout());
        this.mainView.setOnExit(() -> Platform.exit());
    }
    
    public void showView() {
        mainView.show();
        mainView.updateUserInfo(currentUser);
        
        // Simple role check based on username for now
        if (currentUser != null && currentUser.equals("admin_user")) {
            adminController = new AdminController();
            mainView.showAdminMenu(() -> adminController.showAdminPanel());
        } else {
            mainView.hideAdminMenu();
        }
    }

    public String getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(String username) {
        this.currentUser = username;
        if (bookingController != null) {
            bookingController.setCurrentUser(username);
        }
    }
    
    public void setOnLogout(Runnable onLogout) {
        this.onLogout = onLogout;
    }
    
    private void handleLogout() {
        if (onLogout != null) {
            onLogout.run();
        }
    }
}