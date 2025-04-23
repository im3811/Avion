package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class LoginView {
    
    private final Stage primaryStage;
    private Scene scene;
    private TextField usernameField;
    private PasswordField passwordField;
    
    private BiConsumer<String, String> onLoginRequest;
    private Runnable onGuestAccess;
    private Runnable onExit;
    
    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }
    
    private void createUI() {
        // Create the root container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Title
        Text title = new Text("Travel Finder");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);
        
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        grid.add(usernameField, 1, 0);
        
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);
        
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        grid.add(passwordField, 1, 1);
        
        // Buttons
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            if (onLoginRequest != null) {
                onLoginRequest.accept(usernameField.getText(), passwordField.getText());
            }
        });
        
        Button guestButton = new Button("Continue as Guest");
        guestButton.setOnAction(e -> {
            if (onGuestAccess != null) {
                onGuestAccess.run();
            }
        });
        
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, guestButton, exitButton);
        
        // Add all to root
        root.getChildren().addAll(title, grid, buttonBox);
        
        // Create scene
        scene = new Scene(root, 400, 300);
    }
    
    public void show() {
        primaryStage.setTitle("Travel Finder - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void setOnLoginRequest(BiConsumer<String, String> onLoginRequest) {
        this.onLoginRequest = onLoginRequest;
    }
    
    public void setOnGuestAccess(Runnable onGuestAccess) {
        this.onGuestAccess = onGuestAccess;
    }
    
    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }
    
    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
    }
}