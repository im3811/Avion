package main.java.travelfinder.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.utils.AlertUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

/**
 * View class for main application screen with search functionality
 */
public class MainView {
    
    private final Stage primaryStage;
    private Scene mainScene;
    
    // UI components
    private ComboBox<String> locationCombo;
    private DatePicker checkInPicker;
    private DatePicker checkOutPicker;
    private ComboBox<String> guestsCombo;
    private Label userLabel;
    private Button profileButton;
    private Button bookingsButton;
    private Button adminButton;
    private GridPane featuredGrid;
    
    // Event handlers
    private Consumer<String> onSearchButtonClicked;
    private Runnable onLogoutButtonClicked;
    private Runnable onProfileButtonClicked;
    private Runnable onBookingsButtonClicked;
    private Runnable onAdminButtonClicked;
    private Consumer<Accommodation> onAccommodationSelected;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createMainScene();
    }
    
    /**
     * Create the main scene
     */
    private void createMainScene() {
        BorderPane root = new BorderPane();
        
        // Create top menu bar
        VBox topContainer = new VBox();
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> primaryStage.close());
        fileMenu.getItems().add(exitItem);
        
        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().add(aboutItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        // Create toolbar with user info and actions
        ToolBar toolBar = new ToolBar();
        
        userLabel = new Label("Welcome, Guest");
        userLabel.setStyle("-fx-font-weight: bold;");
        
        profileButton = new Button("My Profile");
        profileButton.setOnAction(e -> {
            if (onProfileButtonClicked != null) {
                onProfileButtonClicked.run();
            }
        });
        
        bookingsButton = new Button("My Bookings");
        bookingsButton.setOnAction(e -> {
            if (onBookingsButtonClicked != null) {
                onBookingsButtonClicked.run();
            }
        });
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            if (onLogoutButtonClicked != null) {
                onLogoutButtonClicked.run();
            }
        });
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        toolBar.getItems().addAll(userLabel, spacer, profileButton, bookingsButton, logoutButton);
        
        adminButton = new Button("Admin Panel");
        adminButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        adminButton.setOnAction(e -> {
            if (onAdminButtonClicked != null) {
                onAdminButtonClicked.run();
            }
        });
        
        topContainer.getChildren().addAll(menuBar, toolBar);
        root.setTop(topContainer);
        
        // Create the main content with search functionality
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-background-color: #f5f5f5;");
        
        Text searchTitle = new Text("Find Your Perfect Accommodation");
        searchTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Create search form
        GridPane searchForm = new GridPane();
        searchForm.setHgap(10);
        searchForm.setVgap(10);
        searchForm.setPadding(new Insets(20));
        searchForm.setAlignment(Pos.CENTER);
        searchForm.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        // Location dropdown
        locationCombo = new ComboBox<>();
        locationCombo.getItems().addAll("Miami, FL", "Los Angeles, CA", "New York, NY", "Chicago, IL", "San Francisco, CA");
        locationCombo.setPromptText("Where are you going?");
        locationCombo.setPrefWidth(250);
        
        // Check-in date
        checkInPicker = new DatePicker(LocalDate.now());
        checkInPicker.setPrefWidth(250);
        
        // Check-out date
        checkOutPicker = new DatePicker(LocalDate.now().plusDays(7));
        checkOutPicker.setPrefWidth(250);
        
        // Guests dropdown
        guestsCombo = new ComboBox<>();
        guestsCombo.getItems().addAll("1 Adult", "2 Adults", "2 Adults, 1 Child", "2 Adults, 2 Children", "3 Adults", "4 Adults");
        guestsCombo.setValue("2 Adults");
        guestsCombo.setPrefWidth(250);
        
        // Search button
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        searchButton.setPrefWidth(250);
        searchButton.setOnAction(e -> {
            if (locationCombo.getValue() == null) {
                AlertUtil.showAlert(Alert.AlertType.WARNING, "Search Error", "Please select a location");
                return;
            }
            
            if (onSearchButtonClicked != null) {
                onSearchButtonClicked.accept(locationCombo.getValue());
            }
        });
        
        // Add form fields
        searchForm.add(new Label("Location:"), 0, 0);
        searchForm.add(locationCombo, 1, 0);
        searchForm.add(new Label("Check-in:"), 0, 1);
        searchForm.add(checkInPicker, 1, 1);
        searchForm.add(new Label("Check-out:"), 0, 2);
        searchForm.add(checkOutPicker, 1, 2);
        searchForm.add(new Label("Guests:"), 0, 3);
        searchForm.add(guestsCombo, 1, 3);
        searchForm.add(searchButton, 1, 4);
        
        // Featured destinations section
        Text featuredTitle = new Text("Featured Destinations");
        featuredTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        
        // Create a grid of featured destinations
        featuredGrid = new GridPane();
        featuredGrid.setHgap(20);
        featuredGrid.setVgap(20);
        featuredGrid.setPadding(new Insets(10));
        
        // Add all components to the main content
        content.getChildren().addAll(searchTitle, searchForm, featuredTitle, featuredGrid);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        
        root.setCenter(scrollPane);
        
        // Create footer
        HBox footer = new HBox(10);
        footer.setPadding(new Insets(15));
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        
        Text copyright = new Text("© 2025 Travel Finder - All Rights Reserved");
        copyright.setFill(Color.WHITE);
        
        footer.getChildren().add(copyright);
        root.setBottom(footer);
        
        mainScene = new Scene(root, 800, 600);
    }
    
    /**
     * Show the main view
     */
    public void show() {
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Travel Finder");
    }
    
    /**
     * Update the user information and UI based on logged in status
     * @param username The username of the logged in user
     * @param isAdmin Whether the user is an admin
     */
    public void updateUserInfo(String username, boolean isAdmin) {
        userLabel.setText("Welcome, " + username);
        
        boolean isGuest = "Guest".equals(username);
        
        // Update UI for guest user
        if (isGuest) {
            profileButton.setDisable(true);
            bookingsButton.setDisable(true);
        } else {
            profileButton.setDisable(false);
            bookingsButton.setDisable(false);
        }
        
        // Add admin button if the user is an admin
        ToolBar toolBar = (ToolBar) ((VBox) ((BorderPane) mainScene.getRoot()).getTop()).getChildren().get(1);
        
        // Remove admin button if it exists and user is not admin
        if (!isAdmin) {
            toolBar.getItems().remove(adminButton);
        }
        
        // Add admin button if user is admin and button is not already there
        if (isAdmin && !toolBar.getItems().contains(adminButton)) {
            toolBar.getItems().add(3, adminButton);
        }
    }
    
    /**
     * Load the featured destinations
     * @param accommodations List of featured accommodations
     */
    public void loadFeaturedDestinations(List<Accommodation> accommodations) {
        featuredGrid.getChildren().clear();
        
        int column = 0;
        
        if (accommodations != null) {
            for (Accommodation accommodation : accommodations) {
                VBox destination = createDestinationCard(
                        accommodation.getName(),
                        accommodation.getDescription(),
                        "$" + accommodation.getBasePrice() + "/night",
                        accommodation
                );
                featuredGrid.add(destination, column++, 0);
            }
        }
    }
    
    /**
     * Load mock featured destinations when no real data is available
     */
    public void loadMockFeaturedDestinations() {
        featuredGrid.getChildren().clear();
        
        for (int i = 0; i < 3; i++) {
            VBox destination = createDestinationCard(
                    i == 0 ? "Miami Beach, FL" : i == 1 ? "Hollywood, CA" : "South Beach, FL",
                    i == 0 ? "Sunny beaches and vibrant nightlife" : i == 1 ? "Stars and entertainment" : "Art deco and ocean views",
                    i == 0 ? "$299/night" : i == 1 ? "$249/night" : "$319/night",
                    null
            );
            featuredGrid.add(destination, i, 0);
        }
    }
    
    /**
     * Create a destination card for display
     * @param name The destination name
     * @param description The destination description
     * @param price The destination price
     * @param accommodation The accommodation object (null for mock data)
     * @return A VBox containing the destination card
     */
    private VBox createDestinationCard(String name, String description, String price, Accommodation accommodation) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        card.setPrefWidth(200);
        card.setAlignment(Pos.CENTER);
        
        // Image placeholder
        Rectangle imagePlaceholder = new Rectangle(180, 120);
        imagePlaceholder.setFill(Color.LIGHTGRAY);
        
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setWrapText(true);
        
        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        
        Label priceLabel = new Label("From " + price);
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        
        Button viewButton = new Button("View Details");
        viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        viewButton.setOnAction(e -> {
            if (onAccommodationSelected != null) {
                if (accommodation != null) {
                    onAccommodationSelected.accept(accommodation);
                } else {
                    // For mock data, alert that this is just a demo
                    AlertUtil.showInfo("Demo Feature", null, 
                            "This is a demo feature. In the full application, this would show details for: " + name);
                }
            }
        });
        
        card.getChildren().addAll(imagePlaceholder, nameLabel, descLabel, priceLabel, viewButton);
        return card;
    }
    
    /**
     * Show the about dialog
     */
    private void showAboutDialog() {
        AlertUtil.showInfo("About Travel Finder", "Travel Finder v1.0",
                           "Travel Finder is a comprehensive travel booking application.\n\n" +
                           "Developed by: Your Name\n\n" +
                           "© 2025 Travel Finder - All Rights Reserved");
    }
    
    /**
     * Show an alert message
     * @param message The message to show
     */
    public void showAlert(String message) {
        AlertUtil.showAlert(Alert.AlertType.INFORMATION, "Information", message);
    }
    
    /**
     * Show the search results screen with the specified criteria
     */
    public void showSearchResults(String location, LocalDate checkInDate, LocalDate checkOutDate, String guests, List<Accommodation> accommodations) {
        // This method would be called by the controller to show search results
        // Implementation depends on how you want to manage screen transitions
    }
    
    /**
     * Show the user profile screen
     */
    public void showUserProfile() {
        // This method would be called by the controller to show user profile
        // Implementation depends on how you want to manage screen transitions
    }
    
    /**
     * Show the booking history screen
     */
    public void showBookingHistory() {
        // This method would be called by the controller to show booking history
        // Implementation depends on how you want to manage screen transitions
    }
    
    /**
     * Show the admin panel
     */
    public void showAdminPanel() {
        // This method would be called by the controller to show admin panel
        // Implementation depends on how you want to manage screen transitions
    }
    
    // Setter methods for event handlers
    
    public void setOnSearchButtonClicked(Consumer<String> handler) {
        this.onSearchButtonClicked = handler;
    }
    
    public void setOnLogoutButtonClicked(Runnable handler) {
        this.onLogoutButtonClicked = handler;
    }
    
    public void setOnProfileButtonClicked(Runnable handler) {
        this.onProfileButtonClicked = handler;
    }
    
    public void setOnBookingsButtonClicked(Runnable handler) {
        this.onBookingsButtonClicked = handler;
    }
    
    public void setOnAdminButtonClicked(Runnable handler) {
        this.onAdminButtonClicked = handler;
    }
    
    public void setOnAccommodationSelected(Consumer<Accommodation> handler) {
        this.onAccommodationSelected = handler;
    }
    
    // Getter methods for form values
    
    public String getSelectedLocation() {
        return locationCombo.getValue();
    }
    
    public LocalDate getCheckInDate() {
        return checkInPicker.getValue();
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutPicker.getValue();
    }
    
    public String getGuests() {
        return guestsCombo.getValue();
    }
}