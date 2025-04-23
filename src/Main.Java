package main.java.travelfinder;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.travelfinder.controller.LoginController;
import main.java.travelfinder.controller.MainController;
import main.java.travelfinder.dao.AccommodationDAO;
import main.java.travelfinder.dao.AccommodationDAOImpl;
import main.java.travelfinder.dao.UserDAO;
import main.java.travelfinder.dao.UserDAOImpl;
import main.java.travelfinder.view.LoginView;
import main.java.travelfinder.view.MainView;

/**
 * Main entry point for the Travel Finder application
 */
public class Main extends Application {
    
    private Stage primaryStage;
    // DAOs
    private UserDAO userDAO;
    private AccommodationDAO accommodationDAO;
    
    // Controllers
    private LoginController loginController;
    private MainController mainController;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Travel Finder");
        
        // Initialize DAOs
        userDAO = new UserDAOImpl();
        accommodationDAO = new AccommodationDAOImpl();
        
        // Initialize views
        LoginView loginView = new LoginView(primaryStage);
        MainView mainView = new MainView(primaryStage);
        
        // Initialize controllers
        loginController = new LoginController(loginView, userDAO);
        mainController = new MainController(mainView, accommodationDAO);
        
        // Setup navigation between controllers
        loginController.setOnLoginSuccess(username -> {
            mainController.setCurrentUser(username);
            mainController.showView();
        });
        
        loginController.setOnGuestAccess(() -> {
            mainController.setCurrentUser("Guest");
            mainController.showView();
        });
        
        mainController.setOnLogout(() -> {
            loginController.showView();
        });
        
        // Start with login screen
        loginController.showView();
        
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        // Clean up resources
        try {
            // Close database connections
            // Any other cleanup
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}