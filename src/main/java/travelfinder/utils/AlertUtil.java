package main.java.travelfinder.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

/**
 * Utility class for displaying various alerts to the user
 */
public class AlertUtil {
    
    /**
     * Display an information alert
     * 
     * @param title The alert title
     * @param message The alert message
     */
    public static void showInfo(String title, String message) {
        showAlert(AlertType.INFORMATION, title, message);
    }
    
    /**
     * Display a warning alert
     * 
     * @param title The alert title
     * @param message The alert message
     */
    public static void showWarning(String title, String message) {
        showAlert(AlertType.WARNING, title, message);
    }
    
    /**
     * Display an error alert
     * 
     * @param title The alert title
     * @param message The alert message
     */
    public static void showError(String title, String message) {
        showAlert(AlertType.ERROR, title, message);
    }
    
    /**
     * Display a confirmation alert
     * 
     * @param title The alert title
     * @param message The alert message
     * @return true if OK is clicked, false otherwise
     */
    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    /**
     * Display a confirmation alert with custom button types
     * 
     * @param title The alert title
     * @param message The alert message
     * @param buttonTypes The button types to display
     * @return The button type that was clicked
     */
    public static ButtonType showConfirmation(String title, String message, ButtonType... buttonTypes) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        alert.getButtonTypes().setAll(buttonTypes);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(ButtonType.CANCEL);
    }
    
    /**
     * Display an alert with the specified type
     * 
     * @param alertType The type of alert
     * @param title The alert title
     * @param message The alert message
     */
    private static void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Display an alert with the specified type and owner window
     * 
     * @param alertType The type of alert
     * @param title The alert title
     * @param message The alert message
     * @param owner The owner window
     */
    public static void showAlert(AlertType alertType, String title, String message, Window owner) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}