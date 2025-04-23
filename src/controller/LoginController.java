package main.java.travelfinder.controller;

import main.java.travelfinder.dao.UserDAO;
import main.java.travelfinder.model.User;
import main.java.travelfinder.view.LoginView;

import java.util.function.Consumer;

/**
 * Controller for login functionality
 */
public class LoginController {
    private LoginView view;
    private UserDAO userDAO;
    
    private Consumer<String> onLoginSuccess;
    private Runnable onGuestAccess;
    
    public LoginController(LoginView view, UserDAO userDAO) {
        this.view = view;
        this.userDAO = userDAO;
        
        // Setup view event handlers
        this.view.setOnLoginButtonClicked((username, password) -> {
            handleLogin(username, password);
        });
        
        this.view.setOnSkipLoginButtonClicked(() -> {
            handleGuestAccess();
        });
        
        this.view.setOnRegisterLinkClicked(() -> {
            handleRegisterRequest();
        });
    }
    
    public void showView() {
        view.show();
    }
    
    public void setOnLoginSuccess(Consumer<String> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }
    
    public void setOnGuestAccess(Runnable onGuestAccess) {
        this.onGuestAccess = onGuestAccess;
    }
    
    private void handleLogin(String username, String password) {
        // For demo purposes, using mock data
        if (isValidLogin(username, password)) {
            // Successful login
            if (onLoginSuccess != null) {
                onLoginSuccess.accept(username);
            }
        } else {
            // Failed login
            view.showErrorMessage("Invalid username or password");
        }
    }
    
    private void handleGuestAccess() {
        if (onGuestAccess != null) {
            onGuestAccess.run();
        }
    }
    
    private void handleRegisterRequest() {
        view.showRegistrationDialog();
    }
    
    // Mock validation - will be replaced with actual DB validation
    private boolean isValidLogin(String username, String password) {
        if (userDAO != null) {
            return userDAO.validateCredentials(username, password);
        }
        
        // Mock validation for testing
        return (username.equals("admin") && password.equals("admin123")) ||
               (username.equals("user") && password.equals("user123"));
    }
}