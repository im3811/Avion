package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;
import java.util.HashMap;
import java.util.function.Consumer;

public class MainView {
    
    private final Stage primaryStage;
    private Scene scene;
    private BorderPane mainLayout;
    private Label userLabel;
    private MenuItem adminMenuItem;
    
    private Consumer<Map<String, Object>> onSearchAction;
    private Consumer<Integer> onViewAccommodation;
    private Runnable onMyBookings;
    private Runnable onUserProfile;
    private Runnable onLogout;
    private Runnable onExit;
    private Runnable onAdminPanel;
    
    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUI();
    }
    
    private void createUI() {
        // Main layout with menu
        mainLayout = new BorderPane();
        
        // Create menu bar
        MenuBar menuBar = new MenuBar();
        
        Menu fileMenu = new Menu("File");
        MenuItem logoutItem = new MenuItem("Logout");
        logoutItem.setOnAction(e -> {
            if (onLogout != null) {
                onLogout.run();
            }
        });
        
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> {
            if (onExit != null) {
                onExit.run();
            }
        });
        
        fileMenu.getItems().addAll(logoutItem, new SeparatorMenuItem(), exitItem);
        
        Menu bookingsMenu = new Menu("Bookings");
        MenuItem myBookingsItem = new MenuItem("My Bookings");
        myBookingsItem.setOnAction(e -> {
            if (onMyBookings != null) {
                onMyBookings.run();
            }
        });
        
        bookingsMenu.getItems().add(myBookingsItem);
        
        Menu userMenu = new Menu("User");
        MenuItem profileItem = new MenuItem("My Profile");
        profileItem.setOnAction(e -> {
            if (onUserProfile != null) {
                onUserProfile.run();
            }
        });
        
        userMenu.getItems().add(profileItem);
        
        Menu adminMenu = new Menu("Admin");
        adminMenuItem = new MenuItem("Admin Panel");
        adminMenuItem.setOnAction(e -> {
            if (onAdminPanel != null) {
                onAdminPanel.run();
            }
        });
        
        adminMenu.getItems().add(adminMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, bookingsMenu, userMenu, adminMenu);
        
        // User info and search bar at top
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));
        
        userLabel = new Label("Welcome, Guest");
        
        TextField searchField = new TextField();
        searchField.setPromptText("Search destinations, accommodations...");
        searchField.setPrefWidth(300);
        
        ComboBox<String> locationCombo = new ComboBox<>();
        locationCombo.setPromptText("Location");
        locationCombo.getItems().addAll("All Locations", "Florida", "Miami", "California", "Los Angeles");
        
        DatePicker checkInDate = new DatePicker();
        checkInDate.setPromptText("Check-in Date");
        
        DatePicker checkOutDate = new DatePicker();
        checkOutDate.setPromptText("Check-out Date");
        
        ComboBox<Integer> guestsCombo = new ComboBox<>();
        guestsCombo.setPromptText("Guests");
        guestsCombo.getItems().addAll(1, 2, 3, 4, 5, 6);
        
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            if (onSearchAction != null) {
                Map<String, Object> searchCriteria = new HashMap<>();
                searchCriteria.put("keyword", searchField.getText());
                searchCriteria.put("location", locationCombo.getValue());
                searchCriteria.put("checkIn", checkInDate.getValue());
                searchCriteria.put("checkOut", checkOutDate.getValue());
                searchCriteria.put("guests", guestsCombo.getValue());
                
                onSearchAction.accept(searchCriteria);
            }
        });
        
        topBar.getChildren().addAll(userLabel, searchField, locationCombo, 
                                   checkInDate, checkOutDate, guestsCombo, searchButton);
        
        // Featured accommodations in center
        VBox centerContent = new VBox(10);
        centerContent.setPadding(new Insets(10));
        
        Label featuredLabel = new Label("Featured Accommodations");
        featuredLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        
        // This would be populated with actual accommodation data
        ListView<String> featuredList = new ListView<>();
        featuredList.getItems().addAll(
            "Miami Beach Resort - 4.5 stars",
            "Hollywood Hotel - 4.0 stars"
        );
        
        featuredList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) { // Double click
                String selected = featuredList.getSelectionModel().getSelectedItem();
                if (selected != null && onViewAccommodation != null) {
                    // In a real app, we'd extract the ID properly
                    int accommodationId = selected.contains("Miami") ? 1 : 2;
                    onViewAccommodation.accept(accommodationId);
                }
            }
        });
        
        centerContent.getChildren().addAll(featuredLabel, featuredList);
        
        // Assemble the layout
        mainLayout.setTop(new VBox(menuBar, topBar));
        mainLayout.setCenter(centerContent);
        
        // Create scene
        scene = new Scene(mainLayout, 800, 600);
    }
    
    public void show() {
        primaryStage.setTitle("Travel Finder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void updateUserInfo(String username) {
        userLabel.setText("Welcome, " + (username != null ? username : "Guest"));
    }
    
    public void showAdminMenu(Runnable onAdminPanelAction) {
        this.onAdminPanel = onAdminPanelAction;
        adminMenuItem.setVisible(true);
    }
    
    public void hideAdminMenu() {
        adminMenuItem.setVisible(false);
    }
    
    public void setContent(javafx.scene.Node content) {
        mainLayout.setCenter(content);
    }
    
    public void setOnSearchAction(Consumer<Map<String, Object>> onSearchAction) {
        this.onSearchAction = onSearchAction;
    }
    
    public void setOnViewAccommodation(Consumer<Integer> onViewAccommodation) {
        this.onViewAccommodation = onViewAccommodation;
    }
    
    public void setOnMyBookings(Runnable onMyBookings) {
        this.onMyBookings = onMyBookings;
    }
    
    public void setOnUserProfile(Runnable onUserProfile) {
        this.onUserProfile = onUserProfile;
    }
    
    public void setOnLogout(Runnable onLogout) {
        this.onLogout = onLogout;
    }
    
    public void setOnExit(Runnable onExit) {
        this.onExit = onExit;
    }
}