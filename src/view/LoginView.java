package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.travelfinder.utils.AlertUtil;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * View class for login screen
 */
public class LoginView {
    
    private final Stage primaryStage;
    private Scene loginScene;
    
    // UI components
    private TextField usernameField;
    private PasswordField passwordField;
    private Text errorMsg;
    
    // Event handlers
    private BiConsumer<String, String> onLoginButtonClicked;
    private Runnable onSkipLoginButtonClicked;
    private Runnable onRegisterLinkClicked;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createLoginScene();
    }
    
    /**
     * Create the login scene
     */
    private void createLoginScene() {
        // Create a VBox for the login form
        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(50));
        loginBox.setMaxWidth(400);
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        
        // Title
        Text title = new Text("Travel Finder");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web("#2c3e50"));
        
        // Description
        Text description = new Text("Your journey begins here");
        description.setFont(Font.font("Arial", 14));
        description.setFill(Color.web("#7f8c8d"));
        
        // Logo placeholder
        ImageView logoView = new ImageView();
        logoView.setFitHeight(120);
        logoView.setFitWidth(120);
        logoView.setPreserveRatio(true);
        
        // Try to load the logo image
        try {
            Image logo = new Image("/resources/images/logo.png");
            logoView.setImage(logo);
        } catch (Exception e) {
            System.out.println("Logo image not found");
        }
        
        // Username field
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        
        // Password field
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        
        // Login button
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setPrefWidth(300);
        
        // Skip login button
        Button skipLoginButton = new Button("Skip Login (Browse as Guest)");
        skipLoginButton.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");
        skipLoginButton.setPrefWidth(300);
        
        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        
        // Error message text
        errorMsg = new Text();
        errorMsg.setFill(Color.RED);
        errorMsg.setVisible(false);
        
        // Add components to the VBox
        loginBox.getChildren().addAll(
                logoView, 
                title, 
                description, 
                new Separator(), 
                usernameField, 
                passwordField, 
                loginButton,
                skipLoginButton,
                errorMsg,
                registerLink
        );
        
        // Set action for login button
        loginButton.setOnAction(e -> {
            if (onLoginButtonClicked != null) {
                onLoginButtonClicked.accept(usernameField.getText(), passwordField.getText());
            }
        });
        
        // Set action for skip login button
        skipLoginButton.setOnAction(e -> {
            if (onSkipLoginButtonClicked != null) {
                onSkipLoginButtonClicked.run();
            }
        });
        
        // Set action for register link
        registerLink.setOnAction(e -> {
            if (onRegisterLinkClicked != null) {
                onRegisterLinkClicked.run();
            }
        });
        
        // Create a background pane for styling
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #3498db, #9b59b6);");
        root.getChildren().add(loginBox);
        
        loginScene = new Scene(root, 800, 600);
    }
    
    /**
     * Show the login view
     */
    public void show() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Travel Finder - Login");
        
        // Set focus on username field
        usernameField.requestFocus();
    }
    
    /**
     * Set the handler for login button click
     * @param handler The BiConsumer to handle username and password
     */
    public void setOnLoginButtonClicked(BiConsumer<String, String> handler) {
        this.onLoginButtonClicked = handler;
    }
    
    /**
     * Set the handler for skip login button click
     * @param handler The Runnable to handle skip login
     */
    public void setOnSkipLoginButtonClicked(Runnable handler) {
        this.onSkipLoginButtonClicked = handler;
    }
    
    /**
     * Set the handler for register link click
     * @param handler The Runnable to handle register request
     */
    public void setOnRegisterLinkClicked(Runnable handler) {
        this.onRegisterLinkClicked = handler;
    }
    
    /**
     * Show an error message on the login form
     * @param message The error message to display
     */
    public void showErrorMessage(String message) {
        errorMsg.setText(message);
        errorMsg.setVisible(true);
    }
    
    /**
     * Clear the login form
     */
    public void clearForm() {
        usernameField.clear();
        passwordField.clear();
        errorMsg.setVisible(false);
    }
    
    /**
     * Show the registration dialog
     */
    public void showRegistrationDialog() {
        // Create the custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Register New Account");
        dialog.setHeaderText("Please fill in your information");
        
        // Set the button types
        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
        
        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField username = new TextField();
        username.setPromptText("Username");
        TextField email = new TextField();
        email.setPromptText("Email");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        PasswordField confirmPassword = new PasswordField();
        confirmPassword.setPromptText("Confirm Password");
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone Number (Optional)");
        
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Email:"), 0, 1);
        grid.add(email, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(password, 1, 2);
        grid.add(new Label("Confirm Password:"), 0, 3);
        grid.add(confirmPassword, 1, 3);
        grid.add(new Label("First Name:"), 0, 4);
        grid.add(firstName, 1, 4);
        grid.add(new Label("Last Name:"), 0, 5);
        grid.add(lastName, 1, 5);
        grid.add(new Label("Phone Number:"), 0, 6);
        grid.add(phoneNumber, 1, 6);
        
        dialog.getDialogPane().setContent(grid);
        
        // Request focus on the username field by default
        username.requestFocus();
        
        // Show the dialog and wait for user input
        Optional<Pair<String, String>> result = dialog.showAndWait();
        
        // In a real application, you would save this information to the database
        // For now, just show a success message
        AlertUtil.showInfo("Registration Successful", null, 
                "Thank you for registering! You can now log in.");
    }
}