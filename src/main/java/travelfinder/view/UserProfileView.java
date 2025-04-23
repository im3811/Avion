package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.java.travelfinder.model.User;
import main.java.travelfinder.utils.AlertUtil;

import java.time.LocalDate;
import java.util.function.Function;

@FunctionalInterface
interface PasswordChangeHandler {
    boolean onSubmit(String username, String oldPassword, String newPassword);
}

public class UserProfileView {
    
    private final Stage stage;
    private User currentUser;
    private TextField usernameField;
    private TextField emailField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField phoneField;
    private DatePicker dateOfBirthPicker;
    private TextArea addressArea;
    private Function<User, Boolean> onUpdateProfile;
    private PasswordChangeHandler onChangePassword;
    
    public UserProfileView() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("User Profile");
        stage.setMinWidth(500);
        stage.setMinHeight(400);
    }
    
    public void displayUserProfile(User user) {
        this.currentUser = user;
        
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        
        // Header
        Label headerLabel = new Label("User Profile");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        // Tab pane for profile info and password change
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Profile tab
        Tab profileTab = new Tab("Profile Information");
        profileTab.setContent(createProfileTab());
        
        // Password tab
        Tab passwordTab = new Tab("Change Password");
        passwordTab.setContent(createPasswordTab());
        
        tabPane.getTabs().addAll(profileTab, passwordTab);
        
        // Assemble layout
        VBox container = new VBox(10);
        container.getChildren().addAll(headerLabel, tabPane);
        
        layout.setCenter(container);
        
        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        layout.setBottom(closeButton);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
    }
    
    private VBox createProfileTab() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        
        // Form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        int row = 0;
        
        // Username
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, row);
        
        usernameField = new TextField();
        usernameField.setText(currentUser.getUsername());
        usernameField.setDisable(true); // Username cannot be changed
        grid.add(usernameField, 1, row++);
        
        // Email
        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, row);
        
        emailField = new TextField();
        emailField.setText(currentUser.getEmail());
        grid.add(emailField, 1, row++);
        
        // First Name
        Label firstNameLabel = new Label("First Name:");
        grid.add(firstNameLabel, 0, row);
        
        firstNameField = new TextField();
        firstNameField.setText(currentUser.getFirstName());
        grid.add(firstNameField, 1, row++);
        
        // Last Name
        Label lastNameLabel = new Label("Last Name:");
        grid.add(lastNameLabel, 0, row);
        
        lastNameField = new TextField();
        lastNameField.setText(currentUser.getLastName());
        grid.add(lastNameField, 1, row++);
        
        // Phone
        Label phoneLabel = new Label("Phone Number:");
        grid.add(phoneLabel, 0, row);
        
        phoneField = new TextField();
        phoneField.setText(currentUser.getPhoneNumber());
        grid.add(phoneField, 1, row++);
        
        // Date of Birth
        Label dobLabel = new Label("Date of Birth:");
        grid.add(dobLabel, 0, row);
        
        dateOfBirthPicker = new DatePicker();
        dateOfBirthPicker.setValue(currentUser.getDateOfBirth());
        grid.add(dateOfBirthPicker, 1, row++);
        
        // Address
        Label addressLabel = new Label("Address:");
        grid.add(addressLabel, 0, row);
        
        addressArea = new TextArea();
        addressArea.setText(currentUser.getAddress());
        addressArea.setPrefRowCount(3);
        grid.add(addressArea, 1, row++);
        
        // Save button
        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> saveProfileChanges());
        grid.add(saveButton, 1, row);
        
        container.getChildren().add(grid);
        return container;
    }

    @FunctionalInterface
public interface PasswordChangeHandler {
    boolean onSubmit(String username, String oldPassword, String newPassword);
}

    
    private VBox createPasswordTab() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        
        // Form grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        int row = 0;
        
        // Current password
        Label currentPasswordLabel = new Label("Current Password:");
        grid.add(currentPasswordLabel, 0, row);
        
        PasswordField currentPasswordField = new PasswordField();
        grid.add(currentPasswordField, 1, row++);
        
        // New password
        Label newPasswordLabel = new Label("New Password:");
        grid.add(newPasswordLabel, 0, row);
        
        PasswordField newPasswordField = new PasswordField();
        grid.add(newPasswordField, 1, row++);
        
        // Confirm new password
        Label confirmPasswordLabel = new Label("Confirm New Password:");
        grid.add(confirmPasswordLabel, 0, row);
        
        PasswordField confirmPasswordField = new PasswordField();
        grid.add(confirmPasswordField, 1, row++);
        
        // Change button
        Button changeButton = new Button("Change Password");
        changeButton.setOnAction(e -> {
            String currentPassword = currentPasswordField.getText();
            String newPassword = newPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                AlertUtil.showError("Missing Information", "Please fill in all password fields");
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                AlertUtil.showError("Password Mismatch", "New password and confirmation do not match");
                return;
            }
            
            if (onChangePassword != null) {
                boolean success = onChangePassword.onSubmit(
                    currentUser.getUsername(),
                    currentPassword,
                    newPassword
                );
                
                if (success) {
                    currentPasswordField.clear();
                    newPasswordField.clear();
                    confirmPasswordField.clear();
                }
            }
        });
        grid.add(changeButton, 1, row);
        
        container.getChildren().add(grid);
        return container;
    }
    
    private void saveProfileChanges() {
        if (onUpdateProfile != null) {
            // Create updated user
            User updatedUser = new User();
            updatedUser.setUserId(currentUser.getUserId());
            updatedUser.setUsername(currentUser.getUsername());
            updatedUser.setEmail(emailField.getText());
            updatedUser.setFirstName(firstNameField.getText());
            updatedUser.setLastName(lastNameField.getText());
            updatedUser.setPhoneNumber(phoneField.getText());
            updatedUser.setDateOfBirth(dateOfBirthPicker.getValue());
            updatedUser.setAddress(addressArea.getText());
            updatedUser.setRole(currentUser.getRole());
            
            boolean success = onUpdateProfile.apply(updatedUser);
            
            if (success) {
                // Update current user with the changes
                currentUser.setEmail(updatedUser.getEmail());
                currentUser.setFirstName(updatedUser.getFirstName());
                currentUser.setLastName(updatedUser.getLastName());
                currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
                currentUser.setDateOfBirth(updatedUser.getDateOfBirth());
                currentUser.setAddress(updatedUser.getAddress());
            }
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    public void setOnUpdateProfile(Function<User, Boolean> onUpdateProfile) {
        this.onUpdateProfile = onUpdateProfile;
    }
    
    public void setOnChangePassword(PasswordChangeHandler onChangePassword) {
        this.onChangePassword = onChangePassword;
    }
}