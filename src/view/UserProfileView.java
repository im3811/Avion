package main.java.travelfinder.view;

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
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.User;
import main.java.travelfinder.utils.AlertUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

/**
 * View class for user profile screen
 */
public class UserProfileView {
    
    private final Stage primaryStage;
    private Scene userProfileScene;
    
    // UI components
    private TabPane tabPane;
    private TextField usernameField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneField;
    private DatePicker birthDatePicker;
    private TextArea addressArea;
    private VBox upcomingBookingsBox;
    private VBox pastBookingsBox;
    private VBox cancelledBookingsBox;
    private PasswordField currentPasswordField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;
    
    // Event handlers
    private Runnable onBackButtonClicked;
    private Consumer<UserDetails> onSaveProfileClicked;
    private Consumer<String> onSavePreferencesClicked;
    private BiConsumer<String, String> onChangePasswordClicked;
    private Consumer<Booking> onViewBookingClicked;
    private BiConsumer<Booking, String> onManageBookingClicked;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public UserProfileView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createUserProfileScene();
    }
    
    /**
     * Create the user profile scene
     */
    private void createUserProfileScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Home");
        backButton.setOnAction(e -> {
            if (onBackButtonClicked != null) {
                onBackButtonClicked.run();
            }
        });
        
        Text title = new Text("My Profile");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the profile content
        tabPane = new TabPane();
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
        
        usernameField = new TextField();
        usernameField.setDisable(true);
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        phoneField = new TextField();
        birthDatePicker = new DatePicker();
        addressArea = new TextArea();
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
        savePersonalInfoButton.setOnAction(e -> {
            if (onSaveProfileClicked != null) {
                UserDetails userDetails = new UserDetails(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    birthDatePicker.getValue(),
                    addressArea.getText()
                );
                onSaveProfileClicked.accept(userDetails);
            }
        });
        
        personalInfoContent.getChildren().addAll(personalInfoTitle, personalInfoGrid, savePersonalInfoButton);
        personalInfoTab.setContent(personalInfoContent);
        
        // Booking History Tab
        Tab bookingHistoryTab = new Tab("Booking History");
        VBox bookingHistoryContent = new VBox(15);
        bookingHistoryContent.setPadding(new Insets(20));
        
        Text bookingHistoryTitle = new Text("My Bookings");
        bookingHistoryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Create sub-tabs for upcoming, past, and cancelled bookings
        TabPane bookingTabs = new TabPane();
        bookingTabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Upcoming Bookings Tab
        Tab upcomingTab = new Tab("Upcoming");
        VBox upcomingContent = new VBox(15);
        upcomingContent.setPadding(new Insets(10));
        upcomingBookingsBox = new VBox(15);
        
        upcomingContent.getChildren().add(upcomingBookingsBox);
        ScrollPane upcomingScrollPane = new ScrollPane(upcomingContent);
        upcomingScrollPane.setFitToWidth(true);
        upcomingTab.setContent(upcomingScrollPane);
        
        // Past Bookings Tab
        Tab pastTab = new Tab("Past");
        VBox pastContent = new VBox(15);
        pastContent.setPadding(new Insets(10));
        pastBookingsBox = new VBox(15);
        
        pastContent.getChildren().add(pastBookingsBox);
        ScrollPane pastScrollPane = new ScrollPane(pastContent);
        pastScrollPane.setFitToWidth(true);
        pastTab.setContent(pastScrollPane);
        
        // Cancelled Bookings Tab
        Tab cancelledTab = new Tab("Cancelled");
        VBox cancelledContent = new VBox(15);
        cancelledContent.setPadding(new Insets(10));
        cancelledBookingsBox = new VBox(15);
        
        cancelledContent.getChildren().add(cancelledBookingsBox);
        ScrollPane cancelledScrollPane = new ScrollPane(cancelledContent);
        cancelledScrollPane.setFitToWidth(true);
        cancelledTab.setContent(cancelledScrollPane);
        
        // Add all booking tabs
        bookingTabs.getTabs().addAll(upcomingTab, pastTab, cancelledTab);
        
        bookingHistoryContent.getChildren().addAll(bookingHistoryTitle, bookingTabs);
        bookingHistoryTab.setContent(bookingHistoryContent);
        
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
        savePreferencesButton.setOnAction(e -> {
            if (onSavePreferencesClicked != null) {
                // In a real app, you would serialize preferences to JSON
                onSavePreferencesClicked.accept("preferences_json");
            }
        });
        
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
        
        currentPasswordField = new PasswordField();
        currentPasswordField.setPromptText("Current Password");
        
        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm New Password");
        
        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        changePasswordButton.setOnAction(e -> {
            if (onChangePasswordClicked != null && validatePasswordFields()) {
                onChangePasswordClicked.accept(
                    currentPasswordField.getText(),
                    newPasswordField.getText()
                );
            }
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
        tabPane.getTabs().addAll(personalInfoTab, bookingHistoryTab, preferencesTab, securityTab);
        
        root.setCenter(tabPane);
        
        userProfileScene = new Scene(root, 800, 600);
    }
    
    /**
     * Show the user profile view
     */
    public void show() {
        primaryStage.setScene(userProfileScene);
        primaryStage.setTitle("Travel Finder - My Profile");
    }
    
    /**
     * Display user information
     * @param user The user to display
     */
    public void displayUserInfo(User user) {
        usernameField.setText(user.getUsername());
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhoneNumber());
        birthDatePicker.setValue(user.getDateOfBirth());
        addressArea.setText(user.getAddress());
    }
    
    /**
     * Display booking history
     * @param upcomingBookings List of upcoming bookings
     * @param pastBookings List of past bookings
     * @param cancelledBookings List of cancelled bookings
     */
    public void displayBookingHistory(List<Booking> upcomingBookings, List<Booking> pastBookings, List<Booking> cancelledBookings) {
        // Clear previous bookings
        upcomingBookingsBox.getChildren().clear();
        pastBookingsBox.getChildren().clear();
        cancelledBookingsBox.getChildren().clear();
        
        // Add upcoming bookings
        if (upcomingBookings == null || upcomingBookings.isEmpty()) {
            Text noUpcoming = new Text("You have no upcoming bookings.");
            upcomingBookingsBox.getChildren().add(noUpcoming);
        } else {
            for (Booking booking : upcomingBookings) {
                upcomingBookingsBox.getChildren().add(createBookingCard(booking, true));
            }
        }
        
        // Add past bookings
        if (pastBookings == null || pastBookings.isEmpty()) {
            Text noPast = new Text("You have no past bookings.");
            pastBookingsBox.getChildren().add(noPast);
        } else {
            for (Booking booking : pastBookings) {
                pastBookingsBox.getChildren().add(createBookingCard(booking, false));
            }
        }
        
        // Add cancelled bookings
        if (cancelledBookings == null || cancelledBookings.isEmpty()) {
            Text noCancelled = new Text("You have no cancelled bookings.");
            cancelledBookingsBox.getChildren().add(noCancelled);
        } else {
            for (Booking booking : cancelledBookings) {
                cancelledBookingsBox.getChildren().add(createBookingCard(booking, false));
            }
        }
    }
    
    /**
     * Create a booking card for display
     * @param booking The booking to display
     * @param isUpcoming Whether the booking is upcoming
     * @return A VBox containing the booking details
     */
    private VBox createBookingCard(Booking booking, boolean isUpcoming) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Rectangle imagePlaceholder = new Rectangle(80, 60);
        imagePlaceholder.setFill(Color.LIGHTGRAY);
        
        VBox infoBox = new VBox(5);
        
        // In a real app, you would get the accommodation name from a service
        Text nameText = new Text("Accommodation #" + booking.getAccommodationId());
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Text datesText = new Text(booking.getCheckInDate().toString() + " - " + booking.getCheckOutDate().toString() + 
                                " (" + booking.getNights() + " nights)");
        
        Label statusLabel = new Label(getStatusText(booking.getStatusId()));
        statusLabel.setStyle(getStatusStyle(booking.getStatusId()));
        
        infoBox.getChildren().addAll(nameText, datesText, statusLabel);
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        VBox priceBox = new VBox(5);
        priceBox.setAlignment(Pos.CENTER_RIGHT);
        
        Text priceText = new Text("$" + booking.getTotalPrice());
        priceText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text referenceText = new Text("Ref: " + booking.getReferenceNumber());
        referenceText.setFont(Font.font(12));
        
        priceBox.getChildren().addAll(priceText, referenceText);
        
        header.getChildren().addAll(imagePlaceholder, infoBox, spacer, priceBox);
        
        // Details section
        VBox details = new VBox(5);
        
        Text guestsText = new Text("Guests: " + booking.getNumGuests() + " person(s)");
        
        details.getChildren().add(guestsText);
        
        // Actions section
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setOnAction(e -> {
            if (onViewBookingClicked != null) {
                onViewBookingClicked.accept(booking);
            }
        });
        
        if (isUpcoming) {
            Button modifyButton = new Button("Modify");
            modifyButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            modifyButton.setOnAction(e -> {
                if (onManageBookingClicked != null) {
                    onManageBookingClicked.accept(booking, "modify");
                }
            });
            
            Button cancelButton = new Button("Cancel");
            cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            cancelButton.setOnAction(e -> {
                if (onManageBookingClicked != null) {
                    onManageBookingClicked.accept(booking, "cancel");
                }
            });
            
            actions.getChildren().addAll(viewDetailsButton, modifyButton, cancelButton);
        } else if (booking.getStatusId() == 3) { // Completed
            Button reviewButton = new Button("Write Review");
            reviewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            reviewButton.setOnAction(e -> {
                if (onManageBookingClicked != null) {
                    onManageBookingClicked.accept(booking, "review");
                }
            });
            
            actions.getChildren().addAll(viewDetailsButton, reviewButton);
        } else {
            actions.getChildren().add(viewDetailsButton);
        }
        
        card.getChildren().addAll(header, new Separator(), details, actions);
        
        return card;
    }
    
    /**
     * Get the status text for a booking status ID
     * @param statusId The status ID
     * @return The status text
     */
    private String getStatusText(int statusId) {
        switch (statusId) {
            case 1: return "Pending";
            case 2: return "Confirmed";
            case 3: return "Completed";
            case 4: return "Cancelled";
            case 5: return "No-Show";
            default: return "Unknown";
        }
    }
    
    /**
     * Get the status style for a booking status ID
     * @param statusId The status ID
     * @return The status style
     */
    private String getStatusStyle(int statusId) {
        String baseStyle = "-fx-text-fill: white; -fx-padding: 2 5 2 5; -fx-background-radius: 3;";
        
        switch (statusId) {
            case 1: return "-fx-background-color: #f39c12; " + baseStyle; // Pending - Orange
            case 2: return "-fx-background-color: #2ecc71; " + baseStyle; // Confirmed - Green
            case 3: return "-fx-background-color: #3498db; " + baseStyle; // Completed - Blue
            case 4: return "-fx-background-color: #e74c3c; " + baseStyle; // Cancelled - Red
            case 5: return "-fx-background-color: #95a5a6; " + baseStyle; // No-Show - Gray
            default: return "-fx-background-color: #7f8c8d; " + baseStyle; // Unknown - Dark Gray
        }
    }
    
    /**
     * Validate password fields
     * @return true if the fields are valid, false otherwise
     */
    private boolean validatePasswordFields() {
        if (currentPasswordField.getText().trim().isEmpty()) {
            showAlert("Please enter your current password");
            return false;
        }
        
        if (newPasswordField.getText().trim().isEmpty()) {
            showAlert("Please enter a new password");
            return false;
        }
        
        if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("New password and confirmation do not match");
            return false;
        }
        
        return true;
    }
    
    /**
     * Show an alert
     * @param message The alert message
     */
    public void showAlert(String message) {
        AlertUtil.showAlert(Alert.AlertType.WARNING, "Profile Error", message);
    }
    
    // Setter methods for event handlers
    
    public void setOnBackButtonClicked(Runnable handler) {
        this.onBackButtonClicked = handler;
    }
    
    public void setOnSaveProfileClicked(Consumer<UserDetails> handler) {
        this.onSaveProfileClicked = handler;
    }
    
    public void setOnSavePreferencesClicked(Consumer<String> handler) {
        this.onSavePreferencesClicked = handler;
    }
    
    public void setOnChangePasswordClicked(BiConsumer<String, String> handler) {
        this.onChangePasswordClicked = handler;
    }
    
    public void setOnViewBookingClicked(Consumer<Booking> handler) {
        this.onViewBookingClicked = handler;
    }
    
    public void setOnManageBookingClicked(BiConsumer<Booking, String> handler) {
        this.onManageBookingClicked = handler;
    }
    
    /**
     * Class to hold user details
     */
    public class UserDetails {
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private LocalDate dateOfBirth;
        private String address;
        
        public UserDetails(String firstName, String lastName, String email, String phoneNumber,
                         LocalDate dateOfBirth, String address) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.dateOfBirth = dateOfBirth;
            this.address = address;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getPhoneNumber() {
            return phoneNumber;
        }
        
        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }
        
        public String getAddress() {
            return address;
        }
    }
}