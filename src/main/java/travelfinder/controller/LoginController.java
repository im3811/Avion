package main.java.travelfinder.controller;

import javafx.application.Platform;
import main.java.travelfinder.view.LoginView;
import main.java.travelfinder.utils.AlertUtil;

import java.util.function.Consumer;

public class LoginController {
    
    private final LoginView loginView;
    private Consumer<String> onLoginSuccess;
    private Runnable onGuestAccess;
    
    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        
        this.loginView.setOnLoginRequest((username, password) -> handleLogin(username, password));
        this.loginView.setOnGuestAccess(() -> handleGuestAccess());
        this.loginView.setOnExit(() -> Platform.exit());
    }
    
    public void showView() {
        loginView.show();
    }
    
    public void setOnLoginSuccess(Consumer<String> onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }
    
    public void setOnGuestAccess(Runnable onGuestAccess) {
        this.onGuestAccess = onGuestAccess;
    }
    
    private void handleLogin(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            AlertUtil.showError("Login Failed", "Username is required");
            return;
        }
        
        if (password == null || password.trim().isEmpty()) {
            AlertUtil.showError("Login Failed", "Password is required");
            return;
        }
        
        // For testing purposes - will be replaced with actual authentication using DAO
        if (username.equals("john_doe") && password.equals("password123")) {
            if (onLoginSuccess != null) {
                onLoginSuccess.accept(username);
            }
        } else if (username.equals("admin_user") && password.equals("admin123")) {
            if (onLoginSuccess != null) {
                onLoginSuccess.accept(username);
            }
        } else {
            AlertUtil.showError("Login Failed", "Invalid username or password");
        }
    }
    
    private void handleGuestAccess() {
        if (onGuestAccess != null) {
            onGuestAccess.run();
        }
    }
}