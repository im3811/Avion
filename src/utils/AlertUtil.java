package main.java.travelfinder.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

/**
 * Utility class for displaying JavaFX alerts
 */
public class AlertUtil {
    
    /**
     * Show an information alert
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     */
    public static void showInfo(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Show an error alert
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     */
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Show a warning alert
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     */
    public static void showWarning(String title, String header, String content) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Show a confirmation alert
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     * @return true if OK/Yes was clicked, false otherwise
     */
    public static boolean showConfirmation(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    /**
     * Show a confirmation alert with Yes/No buttons
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     * @return true if Yes was clicked, false otherwise
     */
    public static boolean showYesNoConfirmation(String title, String header, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonData.NO);
        
        alert.getButtonTypes().setAll(yesButton, noButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().getButtonData() == ButtonData.YES;
    }
    
    /**
     * Show a confirmation alert with custom buttons
     * @param title The alert title
     * @param header The alert header (can be null)
     * @param content The alert content
     * @param buttons The button types to display
     * @return The ButtonType that was clicked, or empty if dialog was closed
     */
    public static Optional<ButtonType> showCustomConfirmation(String title, String header, String content, ButtonType... buttons) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        
        alert.getButtonTypes().setAll(buttons);
        
        return alert.showAndWait();
    }
    
    /**
     * Show a login required alert with options to login or register
     * @param owner The owner window
     * @return "LOGIN", "REGISTER", or "CANCEL"
     */
    public static String showLoginRequiredAlert(Window owner) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Login Required");
        alert.setHeaderText("Please Login or Register");
        alert.setContentText("You need to be logged in to perform this action.");
        
        if (owner != null) {
            alert.initOwner(owner);
        }
        
        ButtonType loginButton = new ButtonType("Login");
        ButtonType registerButton = new ButtonType("Register");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(loginButton, registerButton, cancelButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent()) {
            if (result.get() == loginButton) {
                return "LOGIN";
            } else if (result.get() == registerButton) {
                return "REGISTER";
            }
        }
        
        return "CANCEL";
    }
    
    /**
     * Show a booking confirmation alert
     * @param referenceNumber The booking reference number
     */
    public static void showBookingConfirmation(String referenceNumber) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Booking Confirmation");
        alert.setHeaderText("Your booking has been confirmed!");
        alert.setContentText("Booking reference: " + referenceNumber + "\n\n" +
                            "A confirmation email has been sent to your email address.\n\n" +
                            "Thank you for choosing Travel Finder!");
        alert.showAndWait();
    }
    
    /**
     * Show a simple alert with no header
     * @param type The alert type
     * @param title The alert title
     * @param message The alert message
     */
    public static void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}