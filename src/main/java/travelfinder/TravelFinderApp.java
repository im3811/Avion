// TravelFinderApp.java
package main.java.travelfinder;

import javafx.application.Application;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.LocalDate;
import java.util.Optional;

public class TravelFinderApp extends Application {

    // Mock data - would be replaced with actual database access
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "user123";
    
    private Stage primaryStage;
    private Scene loginScene;
    private Scene mainScene;
    private Scene searchResultsScene;
    private Scene accommodationDetailsScene;
    private Scene bookingScene;
    private Scene userProfileScene;
    private Scene bookingHistoryScene;
    
    private String currentUser;
    private boolean isAdmin = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Travel Finder");
        
        // Create the login scene
        createLoginScene();
        
        // Set initial scene
        primaryStage.setScene(loginScene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
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
        
        // Logo placeholder (replace with actual logo)
        ImageView logoView = new ImageView();
        logoView.setFitHeight(120);
        logoView.setFitWidth(120);
        logoView.setPreserveRatio(true);
        
        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        
        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        
        // Login button
        Button loginButton = new Button("Log In");
        loginButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        loginButton.setPrefWidth(300);
        
        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here");
        
        // Error message text
        Text errorMsg = new Text();
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
                errorMsg,
                registerLink
        );
        
        // Set action for login button
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (validateLogin(username, password)) {
                // Successful login
                currentUser = username;
                errorMsg.setVisible(false);
                createMainScene();
                primaryStage.setScene(mainScene);
            } else {
                // Failed login
                errorMsg.setText("Invalid username or password");
                errorMsg.setVisible(true);
            }
        });
        
        // Set action for register link
        registerLink.setOnAction(e -> {
            showRegistrationDialog();
        });
        
        // Create a background pane for styling
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #3498db, #9b59b6);");
        root.getChildren().add(loginBox);
        
        loginScene = new Scene(root, 800, 600);
    }
    
    private boolean validateLogin(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            isAdmin = true;
            return true;
        } else if (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD)) {
            isAdmin = false;
            return true;
        }
        return false;
    }
    
    private void showRegistrationDialog() {
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registration Successful");
        alert.setHeaderText(null);
        alert.setContentText("Thank you for registering! You can now log in.");
        alert.showAndWait();
    }
    
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
        
        Label userLabel = new Label("Welcome, " + currentUser);
        userLabel.setStyle("-fx-font-weight: bold;");
        
        Button profileButton = new Button("My Profile");
        profileButton.setOnAction(e -> showUserProfile());
        
        Button bookingsButton = new Button("My Bookings");
        bookingsButton.setOnAction(e -> showBookingHistory());
        
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setScene(loginScene);
            currentUser = null;
            isAdmin = false;
        });
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        toolBar.getItems().addAll(userLabel, spacer, profileButton, bookingsButton, logoutButton);
        
        // Admin-specific controls
        if (isAdmin) {
            Button adminButton = new Button("Admin Panel");
            adminButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            adminButton.setOnAction(e -> showAdminPanel());
            toolBar.getItems().add(3, adminButton);
        }
        
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
        ComboBox<String> locationCombo = new ComboBox<>();
        locationCombo.getItems().addAll("Miami, FL", "Los Angeles, CA", "New York, NY", "Chicago, IL", "San Francisco, CA");
        locationCombo.setPromptText("Where are you going?");
        locationCombo.setPrefWidth(250);
        
        // Check-in date
        DatePicker checkInPicker = new DatePicker(LocalDate.now());
        checkInPicker.setPrefWidth(250);
        
        // Check-out date
        DatePicker checkOutPicker = new DatePicker(LocalDate.now().plusDays(7));
        checkOutPicker.setPrefWidth(250);
        
        // Guests dropdown
        ComboBox<String> guestsCombo = new ComboBox<>();
        guestsCombo.getItems().addAll("1 Adult", "2 Adults", "2 Adults, 1 Child", "2 Adults, 2 Children", "3 Adults", "4 Adults");
        guestsCombo.setValue("2 Adults");
        guestsCombo.setPrefWidth(250);
        
        // Search button
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        searchButton.setPrefWidth(250);
        searchButton.setOnAction(e -> {
            if (locationCombo.getValue() == null) {
                showAlert("Please select a location");
                return;
            }
            createSearchResultsScene(locationCombo.getValue(), 
                                    checkInPicker.getValue(), 
                                    checkOutPicker.getValue(), 
                                    guestsCombo.getValue());
            primaryStage.setScene(searchResultsScene);
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
        GridPane featuredGrid = new GridPane();
        featuredGrid.setHgap(20);
        featuredGrid.setVgap(20);
        featuredGrid.setPadding(new Insets(10));
        
        // Add featured destinations (these would come from the database in a real app)
        for (int i = 0; i < 3; i++) {
            VBox destination = createDestinationCard(
                    i == 0 ? "Miami Beach, FL" : i == 1 ? "Hollywood, CA" : "South Beach, FL",
                    i == 0 ? "Sunny beaches and vibrant nightlife" : i == 1 ? "Stars and entertainment" : "Art deco and ocean views",
                    i == 0 ? "$299/night" : i == 1 ? "$249/night" : "$319/night"
            );
            featuredGrid.add(destination, i, 0);
        }
        
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
    
    private VBox createDestinationCard(String name, String description, String price) {
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
            createAccommodationDetailsScene(name, description, price);
            primaryStage.setScene(accommodationDetailsScene);
        });
        
        card.getChildren().addAll(imagePlaceholder, nameLabel, descLabel, priceLabel, viewButton);
        return card;
    }
    
    private void createSearchResultsScene(String location, LocalDate checkIn, LocalDate checkOut, String guests) {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Search");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));
        
        Text title = new Text("Search Results: " + location);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the search results content
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        Text resultsInfo = new Text("Showing results for " + location + " from " + 
                                    checkIn.toString() + " to " + checkOut.toString() + 
                                    " for " + guests);
        
        // Create filter section
        TitledPane filtersPane = new TitledPane();
        filtersPane.setText("Filters");
        
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);
        filterGrid.setPadding(new Insets(10));
        
        // Price range slider
        Label priceLabel = new Label("Price Range:");
        Slider priceSlider = new Slider(0, 1000, 500);
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        priceSlider.setMajorTickUnit(250);
        
        // Star rating checkboxes
        Label ratingLabel = new Label("Star Rating:");
        VBox ratingBox = new VBox(5);
        for (int i = 5; i >= 1; i--) {
            CheckBox starCheck = new CheckBox(i + " Stars");
            starCheck.setSelected(true);
            ratingBox.getChildren().add(starCheck);
        }
        
        // Property type checkboxes
        Label typeLabel = new Label("Property Type:");
        VBox typeBox = new VBox(5);
        for (String type : new String[]{"Hotel", "Apartment", "Vacation Home", "Hostel", "Resort"}) {
            CheckBox typeCheck = new CheckBox(type);
            typeCheck.setSelected(true);
            typeBox.getChildren().add(typeCheck);
        }
        
        // Amenities checkboxes
        Label amenitiesLabel = new Label("Amenities:");
        VBox amenitiesBox = new VBox(5);
        for (String amenity : new String[]{"WiFi", "Swimming Pool", "Free Parking", "Air Conditioning", "Breakfast Included"}) {
            CheckBox amenityCheck = new CheckBox(amenity);
            amenitiesBox.getChildren().add(amenityCheck);
        }
        
        // Apply filters button
        Button applyFiltersButton = new Button("Apply Filters");
        applyFiltersButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        // Add filter components to grid
        filterGrid.add(priceLabel, 0, 0);
        filterGrid.add(priceSlider, 1, 0);
        filterGrid.add(ratingLabel, 0, 1);
        filterGrid.add(ratingBox, 1, 1);
        filterGrid.add(typeLabel, 0, 2);
        filterGrid.add(typeBox, 1, 2);
        filterGrid.add(amenitiesLabel, 0, 3);
        filterGrid.add(amenitiesBox, 1, 3);
        filterGrid.add(applyFiltersButton, 1, 4);
        
        filtersPane.setContent(filterGrid);
        filtersPane.setExpanded(false);
        
        // Create results list
        VBox resultsList = new VBox(15);
        
        // Add mock results (in a real app, these would come from the database)
        for (int i = 0; i < 5; i++) {
            resultsList.getChildren().add(createSearchResultItem(
                    i == 0 ? "Miami Beach Resort" : 
                    i == 1 ? "Beachfront Apartment" : 
                    i == 2 ? "Luxury Downtown Hotel" :
                    i == 3 ? "Ocean View Villa" :
                    "Budget Friendly Hostel",
                    
                    i == 0 ? "5-star luxury resort with ocean views" : 
                    i == 1 ? "Modern 2-bedroom apartment near the beach" : 
                    i == 2 ? "Central location with premium amenities" :
                    i == 3 ? "Private villa with pool access" :
                    "Affordable option with shared facilities",
                    
                    i == 0 ? "$299.99" : 
                    i == 1 ? "$189.99" : 
                    i == 2 ? "$249.99" :
                    i == 3 ? "$329.99" :
                    "$89.99",
                    
                    (5 - i % 2)
            ));
        }
        
        // Add components to content
        content.getChildren().addAll(resultsInfo, filtersPane, resultsList);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        
        root.setCenter(scrollPane);
        
        searchResultsScene = new Scene(root, 800, 600);
    }
    
    private HBox createSearchResultItem(String name, String description, String price, int starRating) {
        HBox item = new HBox(15);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 2, 0, 0, 0);");
        
        // Image placeholder
        Rectangle imagePlaceholder = new Rectangle(120, 90);
        imagePlaceholder.setFill(Color.LIGHTGRAY);
        
        // Details section
        VBox details = new VBox(5);
        details.setAlignment(Pos.CENTER_LEFT);
        details.setPrefWidth(400);
        
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Star rating
        HBox stars = new HBox(2);
        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(14));
            star.setTextFill(i < starRating ? Color.GOLD : Color.LIGHTGRAY);
            stars.getChildren().add(star);
        }
        
        Label descLabel = new Label(description);
        descLabel.setWrapText(true);
        
        // Amenities icons
        HBox amenities = new HBox(10);
        for (String amenity : new String[]{"WiFi", "Pool", "Parking"}) {
            Label amenityLabel = new Label(amenity);
            amenityLabel.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 2 5 2 5; -fx-background-radius: 3;");
            amenities.getChildren().add(amenityLabel);
        }
        
        details.getChildren().addAll(nameLabel, stars, descLabel, amenities);
        
        // Price and booking section
        VBox bookingBox = new VBox(5);
        bookingBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label priceLabel = new Label(price);
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label perNightLabel = new Label("per night");
        perNightLabel.setFont(Font.font(12));
        
        Button viewButton = new Button("View Details");
        viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        viewButton.setOnAction(e -> {
            createAccommodationDetailsScene(name, description, price);
            primaryStage.setScene(accommodationDetailsScene);
        });
        
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        bookButton.setOnAction(e -> {
            createBookingScene(name, price);
            primaryStage.setScene(bookingScene);
        });
        
        bookingBox.getChildren().addAll(priceLabel, perNightLabel, viewButton, bookButton);
        
        // Add all sections to the main container
        item.getChildren().addAll(imagePlaceholder, details, bookingBox);
        HBox.setHgrow(details, Priority.ALWAYS);
        
        return item;
    }
    
    private void createAccommodationDetailsScene(String name, String description, String price) {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Results");
        backButton.setOnAction(e -> primaryStage.setScene(searchResultsScene));
        
        Text title = new Text(name);
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the details content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        
        // Image gallery (placeholder)
        HBox gallery = new HBox(10);
        gallery.setAlignment(Pos.CENTER);
        Rectangle mainImage = new Rectangle(400, 300);
        mainImage.setFill(Color.LIGHTGRAY);
        
        VBox thumbnails = new VBox(10);
        for (int i = 0; i < 3; i++) {
            Rectangle thumb = new Rectangle(100, 75);
            thumb.setFill(Color.LIGHTGREY);
            thumbnails.getChildren().add(thumb);
        }
        
        gallery.getChildren().addAll(mainImage, thumbnails);
        
        // Property details
        VBox propertyDetails = new VBox(10);
        propertyDetails.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text propertyName = new Text(name);
        propertyName.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Star rating
        HBox stars = new HBox(2);
        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(18));
            star.setTextFill(i < 4 ? Color.GOLD : Color.LIGHTGRAY);
            stars.getChildren().add(star);
        }
        
        Label locationLabel = new Label("Miami Beach, FL");
        locationLabel.setFont(Font.font(14));
        
        Text descriptionText = new Text(description + "\n\nThis is an extended description that would provide more details about the accommodation. It would include information about the rooms, the services offered, and the surrounding area. This text is just a placeholder and would be replaced with actual data from the database in a real application.");
        descriptionText.setWrappingWidth(700);
        
        propertyDetails.getChildren().addAll(propertyName, stars, locationLabel, new Separator(), descriptionText);
        
        // Amenities section
        TitledPane amenitiesPane = new TitledPane();
        amenitiesPane.setText("Amenities");
        
        GridPane amenitiesGrid = new GridPane();
        amenitiesGrid.setHgap(20);
        amenitiesGrid.setVgap(10);
        amenitiesGrid.setPadding(new Insets(10));
        
        String[] amenities = {
            "WiFi", "Swimming Pool", "Free Parking", "Air Conditioning", "Breakfast",
            "Fitness Center", "Spa", "Restaurant", "Bar", "Room Service"
        };
        
        for (int i = 0; i < amenities.length; i++) {
            Label amenityLabel = new Label("✓ " + amenities[i]);
            amenitiesGrid.add(amenityLabel, i % 3, i / 3);
        }
        
        amenitiesPane.setContent(amenitiesGrid);
        
        // Room selection section
        VBox roomSelection = new VBox(10);
        roomSelection.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text roomsTitle = new Text("Available Rooms");
        roomsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Room options
        VBox roomsList = new VBox(15);
        
        for (int i = 0; i < 3; i++) {
            HBox roomItem = new HBox(15);
            roomItem.setPadding(new Insets(10));
            roomItem.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
            
            // Room image placeholder
            Rectangle roomImage = new Rectangle(100, 75);
            roomImage.setFill(Color.LIGHTGRAY);
            
            // Room details
            VBox roomDetails = new VBox(5);
            roomDetails.setPrefWidth(400);
            
            Label roomName = new Label(i == 0 ? "Standard Room" : i == 1 ? "Deluxe Room" : "Suite");
            roomName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            
            Label roomDesc = new Label(i == 0 ? "1 Queen Bed, City View" : 
                                     i == 1 ? "1 King Bed, Partial Ocean View" : 
                                     "1 King Bed, Ocean View, Separate Living Area");
            
            Label roomCapacity = new Label(i == 0 ? "Sleeps 2" : i == 1 ? "Sleeps 2" : "Sleeps 4");
            
            roomDetails.getChildren().addAll(roomName, roomDesc, roomCapacity);
            
            // Price and booking section
            VBox roomBooking = new VBox(5);
            roomBooking.setAlignment(Pos.CENTER_RIGHT);
            
            Label roomPrice = new Label("$" + (Integer.parseInt(price.substring(1, price.indexOf('.'))) + (i * 50)) + ".99");
            roomPrice.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            
            Label perNightLabel = new Label("per night");
            perNightLabel.setFont(Font.font(12));
            
            Button bookButton = new Button("Book Now");
            bookButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
            final int roomIndex = i;
            bookButton.setOnAction(e -> {
                createBookingScene(name + " - " + roomName.getText(), roomPrice.getText());
                primaryStage.setScene(bookingScene);
            });
            
            roomBooking.getChildren().addAll(roomPrice, perNightLabel, bookButton);
            
            // Add components to room item
            roomItem.getChildren().addAll(roomImage, roomDetails, roomBooking);
            HBox.setHgrow(roomDetails, Priority.ALWAYS);
            
            roomsList.getChildren().add(roomItem);
        }
        
        roomSelection.getChildren().addAll(roomsTitle, roomsList);
        
        // Reviews section
        TitledPane reviewsPane = new TitledPane();
        reviewsPane.setText("Guest Reviews");
        
        VBox reviewsBox = new VBox(15);
        reviewsBox.setPadding(new Insets(10));
        
        for (int i = 0; i < 3; i++) {
            VBox review = new VBox(5);
            review.setPadding(new Insets(10));
            review.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
            
            HBox reviewHeader = new HBox(10);
            reviewHeader.setAlignment(Pos.CENTER_LEFT);
            
            Label reviewer = new Label(i == 0 ? "John S." : i == 1 ? "Maria T." : "David R.");
            reviewer.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            
            // Star rating
            HBox reviewStars = new HBox(2);
            for (int j = 0; j < 5; j++) {
                Label star = new Label("★");
                star.setFont(Font.font(14));
                star.setTextFill(j < (5 - i % 2) ? Color.GOLD : Color.LIGHTGRAY);
                reviewStars.getChildren().add(star);
            }
            
            Label reviewDate = new Label("Stayed in " + (i == 0 ? "March 2025" : i == 1 ? "February 2025" : "January 2025"));
            reviewDate.setFont(Font.font(12));
            reviewDate.setTextFill(Color.GRAY);
            
            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            
            reviewHeader.getChildren().addAll(reviewer, reviewStars, spacer, reviewDate);
            
            Text reviewText = new Text(i == 0 ? "Great location and excellent service. The room was clean and comfortable. Would definitely stay here again." : 
                                     i == 1 ? "Beautiful property with amazing views. Staff was friendly and helpful. Only downside was the noise from the street." :
                                     "Good value for the price. The location is convenient for exploring the city. The breakfast could be improved.");
            reviewText.setWrappingWidth(700);
            
            review.getChildren().addAll(reviewHeader, reviewText);
            reviewsBox.getChildren().add(review);
        }
        
        Hyperlink moreReviews = new Hyperlink("Read more reviews (42)");
        reviewsBox.getChildren().add(moreReviews);
        
        reviewsPane.setContent(reviewsBox);
        
        // Location section
        TitledPane locationPane = new TitledPane();
        locationPane.setText("Location");
        
        VBox locationBox = new VBox(10);
        locationBox.setPadding(new Insets(10));
        
        // Map placeholder
        Rectangle mapPlaceholder = new Rectangle(700, 300);
        mapPlaceholder.setFill(Color.LIGHTGRAY);
        
        Label addressLabel = new Label("123 Beach Drive, Miami Beach, FL 33139");
        
        Text nearbyInfo = new Text("Nearby attractions:\n• South Beach (0.5 miles)\n• Art Deco Historic District (0.8 miles)\n• Ocean Drive (0.3 miles)\n• Miami Beach Convention Center (1.2 miles)");
        
        locationBox.getChildren().addAll(mapPlaceholder, addressLabel, nearbyInfo);
        
        locationPane.setContent(locationBox);
        
        // Policies section
        TitledPane policiesPane = new TitledPane();
        policiesPane.setText("Policies");
        
        GridPane policiesGrid = new GridPane();
        policiesGrid.setHgap(20);
        policiesGrid.setVgap(10);
        policiesGrid.setPadding(new Insets(10));
        
        Text checkInLabel = new Text("Check-in:");
        Text checkInValue = new Text("From 15:00");
        Text checkOutLabel = new Text("Check-out:");
        Text checkOutValue = new Text("Until 11:00");
        Text cancellationLabel = new Text("Cancellation policy:");
        Text cancellationValue = new Text("Free cancellation up to 48 hours before check-in. After that, the first night is non-refundable.");
        
        policiesGrid.add(checkInLabel, 0, 0);
        policiesGrid.add(checkInValue, 1, 0);
        policiesGrid.add(checkOutLabel, 0, 1);
        policiesGrid.add(checkOutValue, 1, 1);
        policiesGrid.add(cancellationLabel, 0, 2);
        policiesGrid.add(cancellationValue, 1, 2);
        
        policiesPane.setContent(policiesGrid);
        
        // Add all sections to content
        content.getChildren().addAll(
                gallery, 
                propertyDetails, 
                amenitiesPane, 
                roomSelection, 
                reviewsPane, 
                locationPane, 
                policiesPane
        );
        
        scrollPane.setContent(content);
        
        root.setCenter(scrollPane);
        
        accommodationDetailsScene = new Scene(root, 800, 600);
    }
    
    private void createBookingScene(String accommodation, String price) {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(accommodationDetailsScene));
        
        Text title = new Text("Book Your Stay");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create booking form content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        
        // Booking summary section
        VBox summaryBox = new VBox(10);
        summaryBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text summaryTitle = new Text("Booking Summary");
        summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane summaryGrid = new GridPane();
        summaryGrid.setHgap(10);
        summaryGrid.setVgap(10);
        
        Text accommodationLabel = new Text("Accommodation:");
        Text accommodationValue = new Text(accommodation);
        Text datesLabel = new Text("Dates:");
        Text datesValue = new Text("April 25, 2025 - April 30, 2025 (5 nights)");
        Text guestsLabel = new Text("Guests:");
        Text guestsValue = new Text("2 Adults");
        
        summaryGrid.add(accommodationLabel, 0, 0);
        summaryGrid.add(accommodationValue, 1, 0);
        summaryGrid.add(datesLabel, 0, 1);
        summaryGrid.add(datesValue, 1, 1);
        summaryGrid.add(guestsLabel, 0, 2);
        summaryGrid.add(guestsValue, 1, 2);
        
        summaryBox.getChildren().addAll(summaryTitle, summaryGrid);
        
        // Guest information form
        VBox guestInfoBox = new VBox(10);
        guestInfoBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text guestInfoTitle = new Text("Guest Information");
        guestInfoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane guestInfoGrid = new GridPane();
        guestInfoGrid.setHgap(10);
        guestInfoGrid.setVgap(10);
        
        // Pre-fill with current user info (in a real app, this would come from the database)
        TextField firstNameField = new TextField("John");
        TextField lastNameField = new TextField("Doe");
        TextField emailField = new TextField("john@example.com");
        TextField phoneField = new TextField("+1-555-123-4567");
        
        guestInfoGrid.add(new Label("First Name:"), 0, 0);
        guestInfoGrid.add(firstNameField, 1, 0);
        guestInfoGrid.add(new Label("Last Name:"), 0, 1);
        guestInfoGrid.add(lastNameField, 1, 1);
        guestInfoGrid.add(new Label("Email:"), 0, 2);
        guestInfoGrid.add(emailField, 1, 2);
        guestInfoGrid.add(new Label("Phone:"), 0, 3);
        guestInfoGrid.add(phoneField, 1, 3);
        
        // Special requests
        Label specialRequestsLabel = new Label("Special Requests (optional):");
        TextArea specialRequestsArea = new TextArea();
        specialRequestsArea.setPrefRowCount(3);
        
        guestInfoBox.getChildren().addAll(guestInfoTitle, guestInfoGrid, specialRequestsLabel, specialRequestsArea);
        
        // Payment information
        VBox paymentBox = new VBox(10);
        paymentBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text paymentTitle = new Text("Payment Information");
        paymentTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Payment method selection
        Label paymentMethodLabel = new Label("Payment Method:");
        ComboBox<String> paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Credit Card", "PayPal", "Bank Transfer");
        paymentMethodCombo.setValue("Credit Card");
        
        // Credit card information form (shown only when credit card is selected)
        VBox creditCardForm = new VBox(10);
        
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");
        
        HBox cardDetails = new HBox(10);
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        expiryField.setPrefWidth(100);
        
        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV");
        cvvField.setPrefWidth(70);
        
        TextField nameOnCardField = new TextField();
        nameOnCardField.setPromptText("Name on Card");
        
        cardDetails.getChildren().addAll(expiryField, cvvField);
        
        creditCardForm.getChildren().addAll(
                new Label("Card Number:"), cardNumberField,
                new Label("Expiry Date / CVV:"), cardDetails,
                new Label("Name on Card:"), nameOnCardField
        );
        
        // Price breakdown
        VBox priceBox = new VBox(5);
        priceBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-background-radius: 5;");
        
        Text priceBreakdownTitle = new Text("Price Breakdown");
        priceBreakdownTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        GridPane priceGrid = new GridPane();
        priceGrid.setHgap(10);
        priceGrid.setVgap(5);
        
        // Calculate mock total
        double nightlyPrice = Double.parseDouble(price.substring(1, price.indexOf('.')));
        double totalBeforeTax = nightlyPrice * 5; // 5 nights
        double tax = totalBeforeTax * 0.12; // 12% tax
        double totalPrice = totalBeforeTax + tax;
        
        Text roomPriceLabel = new Text(price + " x 5 nights:");
        Text roomPriceValue = new Text("$" + String.format("%.2f", totalBeforeTax));
        Text taxLabel = new Text("Taxes and fees (12%):");
        Text taxValue = new Text("$" + String.format("%.2f", tax));
        Text totalLabel = new Text("Total:");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text totalValue = new Text("$" + String.format("%.2f", totalPrice));
        totalValue.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        priceGrid.add(roomPriceLabel, 0, 0);
        priceGrid.add(roomPriceValue, 1, 0);
        priceGrid.add(taxLabel, 0, 1);
        priceGrid.add(taxValue, 1, 1);
        priceGrid.add(new Separator(), 0, 2, 2, 1);
        priceGrid.add(totalLabel, 0, 3);
        priceGrid.add(totalValue, 1, 3);
        
        priceBox.getChildren().addAll(priceBreakdownTitle, priceGrid);
        
        // Terms and conditions
        CheckBox termsCheckBox = new CheckBox("I agree to the terms and conditions");
        
        // Complete booking button
        Button completeBookingButton = new Button("Complete Booking");
        completeBookingButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        completeBookingButton.setPrefWidth(200);
        completeBookingButton.setOnAction(e -> {
            if (!termsCheckBox.isSelected()) {
                showAlert("Please agree to the terms and conditions");
                return;
            }
            showBookingConfirmation();
        });
        
        paymentBox.getChildren().addAll(
                paymentTitle, 
                paymentMethodLabel, 
                paymentMethodCombo, 
                creditCardForm, 
                priceBox,
                termsCheckBox, 
                completeBookingButton
        );
        
        // Add all sections to content
        content.getChildren().addAll(summaryBox, guestInfoBox, paymentBox);
        
        scrollPane.setContent(content);
        
        root.setCenter(scrollPane);
        
        bookingScene = new Scene(root, 800, 600);
    }
    
    private void showBookingConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Confirmation");
        alert.setHeaderText("Your booking has been confirmed!");
        alert.setContentText("Booking reference: BOOK" + System.currentTimeMillis() + "\n\n" +
                           "A confirmation email has been sent to your email address.\n\n" +
                           "Thank you for choosing Travel Finder!");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            createBookingHistoryScene();
            primaryStage.setScene(bookingHistoryScene);
        }
    }
    
    private void showUserProfile() {
        if (userProfileScene == null) {
            createUserProfileScene();
        }
        primaryStage.setScene(userProfileScene);
    }
    
    private void createUserProfileScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Home");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));
        
        Text title = new Text("My Profile");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the profile content
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Personal Information Tab
        Tab personalInfoTab = new Tab("Personal Information");
        VBox personalInfoContent = new VBox(15);
        personalInfoContent.setPadding(new Insets(20));
        
        Text personalInfoTitle = new Text("Personal Information");
        personalInfoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane personalInfoGrid = new GridPane();
        personalInfoGrid.setHgap(10);
        personalInfoGrid.setVgap(10);
        
        // Pre-fill with mock user data
        TextField usernameField = new TextField(currentUser);
        usernameField.setDisable(true);
        TextField firstNameField = new TextField("John");
        TextField lastNameField = new TextField("Doe");
        TextField emailField = new TextField("john@example.com");
        TextField phoneField = new TextField("+1-555-123-4567");
        DatePicker birthDatePicker = new DatePicker(LocalDate.of(1985, 6, 15));
        TextArea addressArea = new TextArea("123 Main Street\nApt 4B\nMiami, FL 33139");
        addressArea.setPrefRowCount(3);
        
        personalInfoGrid.add(new Label("Username:"), 0, 0);
        personalInfoGrid.add(usernameField, 1, 0);
        personalInfoGrid.add(new Label("First Name:"), 0, 1);
        personalInfoGrid.add(firstNameField, 1, 1);
        personalInfoGrid.add(new Label("Last Name:"), 0, 2);
        personalInfoGrid.add(lastNameField, 1, 2);
        personalInfoGrid.add(new Label("Email:"), 0, 3);
        personalInfoGrid.add(emailField, 1, 3);
        personalInfoGrid.add(new Label("Phone:"), 0, 4);
        personalInfoGrid.add(phoneField, 1, 4);
        personalInfoGrid.add(new Label("Birth Date:"), 0, 5);
        personalInfoGrid.add(birthDatePicker, 1, 5);
        personalInfoGrid.add(new Label("Address:"), 0, 6);
        personalInfoGrid.add(addressArea, 1, 6);
        
        Button savePersonalInfoButton = new Button("Save Changes");
        savePersonalInfoButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        savePersonalInfoButton.setOnAction(e -> showAlert("Profile updated successfully!"));
        
        personalInfoContent.getChildren().addAll(personalInfoTitle, personalInfoGrid, savePersonalInfoButton);
        personalInfoTab.setContent(personalInfoContent);
        
        // Payment Methods Tab
        Tab paymentMethodsTab = new Tab("Payment Methods");
        VBox paymentMethodsContent = new VBox(15);
        paymentMethodsContent.setPadding(new Insets(20));
        
        Text paymentMethodsTitle = new Text("Payment Methods");
        paymentMethodsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Existing payment methods (mock data)
        VBox existingPayments = new VBox(10);
        
        // Credit card
        HBox creditCardItem = new HBox(10);
        creditCardItem.setPadding(new Insets(10));
        creditCardItem.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        VBox cardDetails = new VBox(5);
        Label cardType = new Label("Visa");
        cardType.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label cardNumber = new Label("**** **** **** 1234");
        Label cardExpiry = new Label("Expires: 05/26");
        cardDetails.getChildren().addAll(cardType, cardNumber, cardExpiry);
        
        Pane cardSpacer = new Pane();
        HBox.setHgrow(cardSpacer, Priority.ALWAYS);
        
        Button editCardButton = new Button("Edit");
        Button removeCardButton = new Button("Remove");
        removeCardButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        
        VBox cardButtons = new VBox(5);
        cardButtons.getChildren().addAll(editCardButton, removeCardButton);
        
        creditCardItem.getChildren().addAll(cardDetails, cardSpacer, cardButtons);
        
        // PayPal account
        HBox paypalItem = new HBox(10);
        paypalItem.setPadding(new Insets(10));
        paypalItem.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        VBox paypalDetails = new VBox(5);
        Label paypalType = new Label("PayPal");
        paypalType.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label paypalEmail = new Label("john@example.com");
        paypalDetails.getChildren().addAll(paypalType, paypalEmail);
        
        Pane paypalSpacer = new Pane();
        HBox.setHgrow(paypalSpacer, Priority.ALWAYS);
        
        Button removePaypalButton = new Button("Remove");
        removePaypalButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        
        VBox paypalButtons = new VBox(5);
        paypalButtons.getChildren().addAll(removePaypalButton);
        
        paypalItem.getChildren().addAll(paypalDetails, paypalSpacer, paypalButtons);
        
        existingPayments.getChildren().addAll(creditCardItem, paypalItem);
        
        Button addPaymentMethodButton = new Button("Add Payment Method");
        addPaymentMethodButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        paymentMethodsContent.getChildren().addAll(paymentMethodsTitle, existingPayments, addPaymentMethodButton);
        paymentMethodsTab.setContent(paymentMethodsContent);
        
        // Preferences Tab
        Tab preferencesTab = new Tab("Preferences");
        VBox preferencesContent = new VBox(15);
        preferencesContent.setPadding(new Insets(20));
        
        Text preferencesTitle = new Text("Preferences");
        preferencesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Notification preferences
        VBox notificationPrefs = new VBox(10);
        notificationPrefs.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text notificationTitle = new Text("Notification Preferences");
        notificationTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        CheckBox emailNotificationsCheck = new CheckBox("Email notifications");
        emailNotificationsCheck.setSelected(true);
        
        CheckBox bookingConfirmationsCheck = new CheckBox("Booking confirmations");
        bookingConfirmationsCheck.setSelected(true);
        
        CheckBox specialOffersCheck = new CheckBox("Special offers and promotions");
        specialOffersCheck.setSelected(true);
        
        CheckBox travelTipsCheck = new CheckBox("Travel tips and recommendations");
        travelTipsCheck.setSelected(false);
        
        notificationPrefs.getChildren().addAll(
                notificationTitle, 
                emailNotificationsCheck, 
                bookingConfirmationsCheck, 
                specialOffersCheck, 
                travelTipsCheck
        );
        
        // Travel preferences
        VBox travelPrefs = new VBox(10);
        travelPrefs.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text travelPrefsTitle = new Text("Travel Preferences");
        travelPrefsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Preferred amenities
        Text amenitiesTitle = new Text("Preferred Amenities");
        
        FlowPane amenitiesPane = new FlowPane(10, 10);
        
        String[] amenities = {
            "WiFi", "Swimming Pool", "Breakfast", "Fitness Center", "Spa", 
            "Restaurant", "Bar", "Room Service", "Airport Shuttle", "Parking"
        };
        
        for (String amenity : amenities) {
            CheckBox amenityCheck = new CheckBox(amenity);
            amenityCheck.setPrefWidth(150);
            amenitiesPane.getChildren().add(amenityCheck);
        }
        
        // Preferred accommodation types
        Text accommodationTypesTitle = new Text("Preferred Accommodation Types");
        
        FlowPane accommodationTypesPane = new FlowPane(10, 10);
        
        String[] accommodationTypes = {
            "Hotel", "Resort", "Apartment", "Vacation Home", "Hostel"
        };
        
        for (String type : accommodationTypes) {
            CheckBox typeCheck = new CheckBox(type);
            typeCheck.setPrefWidth(150);
            typeCheck.setSelected(type.equals("Hotel") || type.equals("Resort"));
            accommodationTypesPane.getChildren().add(typeCheck);
        }
        
        travelPrefs.getChildren().addAll(
                travelPrefsTitle, 
                amenitiesTitle, 
                amenitiesPane, 
                accommodationTypesTitle, 
                accommodationTypesPane
        );
        
        Button savePreferencesButton = new Button("Save Preferences");
        savePreferencesButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        savePreferencesButton.setOnAction(e -> showAlert("Preferences saved successfully!"));
        
        preferencesContent.getChildren().addAll(preferencesTitle, notificationPrefs, travelPrefs, savePreferencesButton);
        preferencesTab.setContent(preferencesContent);
        
        // Security Tab
        Tab securityTab = new Tab("Security");
        VBox securityContent = new VBox(15);
        securityContent.setPadding(new Insets(20));
        
        Text securityTitle = new Text("Security");
        securityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Change password section
        VBox changePasswordBox = new VBox(10);
        changePasswordBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text changePasswordTitle = new Text("Change Password");
        changePasswordTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        PasswordField currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");
        
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");
        
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        changePasswordButton.setOnAction(e -> {
            // In a real application, you would validate the inputs and update the password in the database
            showAlert("Password changed successfully!");
        });
        
        changePasswordBox.getChildren().addAll(
                changePasswordTitle, 
                new Label("Current Password:"),
                currentPasswordField, 
                new Label("New Password:"),
                newPasswordField, 
                new Label("Confirm New Password:"),
                confirmPasswordField, 
                changePasswordButton
        );
        
        securityContent.getChildren().addAll(securityTitle, changePasswordBox);
        securityTab.setContent(securityContent);
        
        // Add all tabs to the tab pane
        tabPane.getTabs().addAll(personalInfoTab, paymentMethodsTab, preferencesTab, securityTab);
        
        root.setCenter(tabPane);
        
        userProfileScene = new Scene(root, 800, 600);
    }
    
    private void showBookingHistory() {
        if (bookingHistoryScene == null) {
            createBookingHistoryScene();
        }
        primaryStage.setScene(bookingHistoryScene);
    }
    
    private void createBookingHistoryScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Home");
        backButton.setOnAction(e -> primaryStage.setScene(mainScene));
        
        Text title = new Text("My Bookings");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the booking history content
        TabPane bookingTabs = new TabPane();
        bookingTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Upcoming Bookings Tab
        Tab upcomingTab = new Tab("Upcoming");
        VBox upcomingContent = new VBox(15);
        upcomingContent.setPadding(new Insets(20));
        
        // Add mock upcoming bookings
        upcomingContent.getChildren().add(createBookingCard(
            "Miami Beach Resort - Deluxe Room",
            "April 25, 2025 - April 30, 2025 (5 nights)",
            "2 Adults",
            "$1,499.99",
            "BOOK123456789",
            "Confirmed",
            true
        ));
        
        upcomingContent.getChildren().add(createBookingCard(
            "Los Angeles Downtown Hotel - Suite",
            "June 15, 2025 - June 20, 2025 (5 nights)",
            "2 Adults, 1 Child",
            "$1,899.99",
            "BOOK987654321",
            "Pending",
            true
        ));
        
        ScrollPane upcomingScrollPane = new ScrollPane(upcomingContent);
        upcomingScrollPane.setFitToWidth(true);
        upcomingTab.setContent(upcomingScrollPane);
        
        // Past Bookings Tab
        Tab pastTab = new Tab("Past");
        VBox pastContent = new VBox(15);
        pastContent.setPadding(new Insets(20));
        
        // Add mock past bookings
        pastContent.getChildren().add(createBookingCard(
            "New York City Hotel - Standard Room",
            "January 10, 2025 - January 15, 2025 (5 nights)",
            "2 Adults",
            "$1,299.99",
            "BOOK135792468",
            "Completed",
            false
        ));
        
        pastContent.getChildren().add(createBookingCard(
            "Chicago Downtown Apartment",
            "March 5, 2025 - March 10, 2025 (5 nights)",
            "2 Adults",
            "$999.99",
            "BOOK246813579",
            "Completed",
            false
        ));
        
        ScrollPane pastScrollPane = new ScrollPane(pastContent);
        pastScrollPane.setFitToWidth(true);
        pastTab.setContent(pastScrollPane);
        
        // Cancelled Bookings Tab
        Tab cancelledTab = new Tab("Cancelled");
        VBox cancelledContent = new VBox(15);
        cancelledContent.setPadding(new Insets(20));
        
        // Add mock cancelled bookings
        cancelledContent.getChildren().add(createBookingCard(
            "San Francisco Bay Hotel - Deluxe Room",
            "February 20, 2025 - February 25, 2025 (5 nights)",
            "2 Adults",
            "$1,499.99",
            "BOOK975318642",
            "Cancelled",
            false
        ));
        
        ScrollPane cancelledScrollPane = new ScrollPane(cancelledContent);
        cancelledScrollPane.setFitToWidth(true);
        cancelledTab.setContent(cancelledScrollPane);
        
        // Add all tabs to the tab pane
        bookingTabs.getTabs().addAll(upcomingTab, pastTab, cancelledTab);
        
        root.setCenter(bookingTabs);
        
        bookingHistoryScene = new Scene(root, 800, 600);
    }
    
    private VBox createBookingCard(String accommodation, String dates, String guests, 
                                 String totalPrice, String reference, String status, 
                                 boolean isUpcoming) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Rectangle imagePlaceholder = new Rectangle(80, 60);
        imagePlaceholder.setFill(Color.LIGHTGRAY);
        
        VBox infoBox = new VBox(5);
        
        Text nameText = new Text(accommodation);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Text datesText = new Text(dates);
        
        Label statusLabel = new Label(status);
        statusLabel.setStyle(
            status.equals("Confirmed") ? "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 3;" :
            status.equals("Pending") ? "-fx-background-color: #f39c12; -fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 3;" :
            status.equals("Completed") ? "-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 3;" :
            "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 3;"
        );
        
        infoBox.getChildren().addAll(nameText, datesText, statusLabel);
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        VBox priceBox = new VBox(5);
        priceBox.setAlignment(Pos.CENTER_RIGHT);
        
        Text priceText = new Text(totalPrice);
        priceText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text referenceText = new Text("Ref: " + reference);
        referenceText.setFont(Font.font(12));
        
        priceBox.getChildren().addAll(priceText, referenceText);
        
        header.getChildren().addAll(imagePlaceholder, infoBox, spacer, priceBox);
        
        // Details section
        VBox details = new VBox(5);
        
        Text guestsText = new Text("Guests: " + guests);
        
        details.getChildren().add(guestsText);
        
        // Actions section
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        
        Button viewDetailsButton = new Button("View Details");
        
        if (isUpcoming) {
            Button modifyButton = new Button("Modify");
            modifyButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            
            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            
            actions.getChildren().addAll(viewDetailsButton, modifyButton, cancelButton);
        } else if (status.equals("Completed")) {
            Button reviewButton = new Button("Write Review");
            reviewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            
            actions.getChildren().addAll(viewDetailsButton, reviewButton);
        } else {
            actions.getChildren().add(viewDetailsButton);
        }
        
        card.getChildren().addAll(header, new Separator(), details, actions);
        
        return card;
    }
    
    private void showAdminPanel() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin Panel");
        alert.setHeaderText("Admin Panel");
        alert.setContentText("The admin panel functionality would be implemented here. This would include:\n\n" +
                           "- Managing accommodations\n" +
                           "- Viewing and editing user accounts\n" +
                           "- Monitoring bookings\n" +
                           "- Generating reports\n\n" +
                           "This is a placeholder for demonstration purposes.");
        alert.showAndWait();
    }
    
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Travel Finder");
        alert.setHeaderText("Travel Finder v1.0");
        alert.setContentText("Travel Finder is a comprehensive travel booking application.\n\n" +
                           "Developed by: Ivan Miškić, Toni Gašpert, Toni Torbarac, Borna Škec\n\n" +
                           "© 2025 Travel Finder - All Rights Reserved");
        alert.showAndWait();
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}