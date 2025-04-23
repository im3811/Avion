package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.User;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AdminView {
    
    private final Stage stage;
    private TabPane tabPane;
    private VBox usersContainer;
    private VBox accommodationsContainer;
    
    private Runnable onManageUsers;
    private Runnable onManageAccommodations;
    private Runnable onGenerateReports;
    
    private Consumer<User> onAddUser;
    private Consumer<User> onEditUser;
    private Consumer<Integer> onDeactivateUser;
    
    private Consumer<Accommodation> onAddAccommodation;
    private Consumer<Accommodation> onEditAccommodation;
    private Consumer<Integer> onDeactivateAccommodation;
    
    private Consumer<String[]> onGenerateBookingReport;
    private Consumer<String[]> onGenerateRevenueReport;
    private Consumer<String[]> onGenerateOccupancyReport;
    
    public AdminView() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Admin Panel");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        createUI();
    }
    
    private void createUI() {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        
        // Admin menu sidebar
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(150);
        
        Button usersButton = new Button("Manage Users");
        usersButton.setPrefWidth(130);
        usersButton.setOnAction(e -> {
            if (onManageUsers != null) {
                onManageUsers.run();
            }
            tabPane.getSelectionModel().select(0);
        });
        
        Button accommodationsButton = new Button("Manage Accommodations");
        accommodationsButton.setPrefWidth(130);
        accommodationsButton.setOnAction(e -> {
            if (onManageAccommodations != null) {
                onManageAccommodations.run();
            }
            tabPane.getSelectionModel().select(1);
        });
        
        Button reportsButton = new Button("Generate Reports");
        reportsButton.setPrefWidth(130);
        reportsButton.setOnAction(e -> {
            if (onGenerateReports != null) {
                onGenerateReports.run();
            }
            tabPane.getSelectionModel().select(2);
        });
        
        sidebar.getChildren().addAll(usersButton, accommodationsButton, reportsButton);
        layout.setLeft(sidebar);
        
        // Main content with tabs
        tabPane = new TabPane();
        
        // Users tab
        Tab usersTab = new Tab("Users");
        usersTab.setClosable(false);
        usersContainer = new VBox(10);
        usersContainer.setPadding(new Insets(10));
        
        Button addUserButton = new Button("Add New User");
        addUserButton.setOnAction(e -> showAddUserDialog());
        
        usersContainer.getChildren().add(addUserButton);
        
        ScrollPane usersScroll = new ScrollPane(usersContainer);
        usersScroll.setFitToWidth(true);
        usersTab.setContent(usersScroll);
        
        // Accommodations tab
        Tab accommodationsTab = new Tab("Accommodations");
        accommodationsTab.setClosable(false);
        accommodationsContainer = new VBox(10);
        accommodationsContainer.setPadding(new Insets(10));
        
        Button addAccommodationButton = new Button("Add New Accommodation");
        addAccommodationButton.setOnAction(e -> showAddAccommodationDialog());
        
        accommodationsContainer.getChildren().add(addAccommodationButton);
        
        ScrollPane accommodationsScroll = new ScrollPane(accommodationsContainer);
        accommodationsScroll.setFitToWidth(true);
        accommodationsTab.setContent(accommodationsScroll);
        
        // Reports tab
        Tab reportsTab = new Tab("Reports");
        reportsTab.setClosable(false);
        VBox reportsContainer = new VBox(20);
        reportsContainer.setPadding(new Insets(10));
        
        // Booking reports
        VBox bookingReportBox = new VBox(10);
        Label bookingReportLabel = new Label("Booking Reports");
        
        HBox bookingDateBox = new HBox(10);
        DatePicker bookingFromDate = new DatePicker();
        bookingFromDate.setPromptText("From Date");
        DatePicker bookingToDate = new DatePicker();
        bookingToDate.setPromptText("To Date");
        Button generateBookingReport = new Button("Generate");
        generateBookingReport.setOnAction(e -> {
            if (onGenerateBookingReport != null) {
                String fromDate = bookingFromDate.getValue() != null ? bookingFromDate.getValue().toString() : "";
                String toDate = bookingToDate.getValue() != null ? bookingToDate.getValue().toString() : "";
                onGenerateBookingReport.accept(new String[]{fromDate, toDate});
            }
        });
        
        bookingDateBox.getChildren().addAll(new Label("Period:"), bookingFromDate, bookingToDate, generateBookingReport);
        bookingReportBox.getChildren().addAll(bookingReportLabel, bookingDateBox);
        
        // Revenue reports
        VBox revenueReportBox = new VBox(10);
        Label revenueReportLabel = new Label("Revenue Reports");
        
        HBox revenueDateBox = new HBox(10);
        DatePicker revenueFromDate = new DatePicker();
        revenueFromDate.setPromptText("From Date");
        DatePicker revenueToDate = new DatePicker();
        revenueToDate.setPromptText("To Date");
        Button generateRevenueReport = new Button("Generate");
        generateRevenueReport.setOnAction(e -> {
            if (onGenerateRevenueReport != null) {
                String fromDate = revenueFromDate.getValue() != null ? revenueFromDate.getValue().toString() : "";
                String toDate = revenueToDate.getValue() != null ? revenueToDate.getValue().toString() : "";
                onGenerateRevenueReport.accept(new String[]{fromDate, toDate});
            }
        });
        
        revenueDateBox.getChildren().addAll(new Label("Period:"), revenueFromDate, revenueToDate, generateRevenueReport);
        revenueReportBox.getChildren().addAll(revenueReportLabel, revenueDateBox);
        
        // Occupancy reports
        VBox occupancyReportBox = new VBox(10);
        Label occupancyReportLabel = new Label("Occupancy Reports");
        
        HBox occupancyDateBox = new HBox(10);
        DatePicker occupancyFromDate = new DatePicker();
        occupancyFromDate.setPromptText("From Date");
        DatePicker occupancyToDate = new DatePicker();
        occupancyToDate.setPromptText("To Date");
        Button generateOccupancyReport = new Button("Generate");
        generateOccupancyReport.setOnAction(e -> {
            if (onGenerateOccupancyReport != null) {
                String fromDate = occupancyFromDate.getValue() != null ? occupancyFromDate.getValue().toString() : "";
                String toDate = occupancyToDate.getValue() != null ? occupancyToDate.getValue().toString() : "";
                onGenerateOccupancyReport.accept(new String[]{fromDate, toDate});
            }
        });
        
        occupancyDateBox.getChildren().addAll(new Label("Period:"), occupancyFromDate, occupancyToDate, generateOccupancyReport);
        occupancyReportBox.getChildren().addAll(occupancyReportLabel, occupancyDateBox);
        
        reportsContainer.getChildren().addAll(bookingReportBox, revenueReportBox, occupancyReportBox);
        
        ScrollPane reportsScroll = new ScrollPane(reportsContainer);
        reportsScroll.setFitToWidth(true);
        reportsTab.setContent(reportsScroll);
        
        tabPane.getTabs().addAll(usersTab, accommodationsTab, reportsTab);
        layout.setCenter(tabPane);
        
        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        layout.setBottom(closeButton);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
    }
    
    public void setupAdminPanel() {
        // This method could do additional setup if needed
    }
    
    public void displayUsersList(List<User> users) {
        // Clear previous list except the Add button
        while (usersContainer.getChildren().size() > 1) {
            usersContainer.getChildren().remove(1);
        }
        
        if (users.isEmpty()) {
            Label noUsersLabel = new Label("No users found");
            usersContainer.getChildren().add(noUsersLabel);
            return;
        }
        
        for (User user : users) {
            HBox userRow = new HBox(10);
            userRow.setPadding(new Insets(5));
            
            Label usernameLabel = new Label(user.getUsername());
            usernameLabel.setPrefWidth(100);
            
            Label nameLabel = new Label(user.getFullName());
            nameLabel.setPrefWidth(150);
            
            Label roleLabel = new Label(user.getRole());
            roleLabel.setPrefWidth(80);
            
            Label statusLabel = new Label(user.isActive() ? "Active" : "Inactive");
            statusLabel.setPrefWidth(80);
            
            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> showEditUserDialog(user));
            
            Button deactivateButton = new Button(user.isActive() ? "Deactivate" : "Activate");
            deactivateButton.setOnAction(e -> {
                if (onDeactivateUser != null) {
                    onDeactivateUser.accept(user.getUserId());
                }
            });
            
            userRow.getChildren().addAll(usernameLabel, nameLabel, roleLabel, statusLabel, editButton, deactivateButton);
            usersContainer.getChildren().add(userRow);
        }
    }
    
    public void displayAccommodationsList(List<Accommodation> accommodations) {
        // Clear previous list except the Add button
        while (accommodationsContainer.getChildren().size() > 1) {
            accommodationsContainer.getChildren().remove(1);
        }
        
        if (accommodations.isEmpty()) {
            Label noAccommodationsLabel = new Label("No accommodations found");
            accommodationsContainer.getChildren().add(noAccommodationsLabel);
            return;
        }
        
        for (Accommodation accommodation : accommodations) {
            HBox accommodationRow = new HBox(10);
            accommodationRow.setPadding(new Insets(5));
            
            Label nameLabel = new Label(accommodation.getName());
            nameLabel.setPrefWidth(150);
            
            Label typeLabel = new Label(accommodation.getTypeName());
            typeLabel.setPrefWidth(100);
            
            Label locationLabel = new Label(accommodation.getLocation() != null ? 
                                           accommodation.getLocation().getName() : "Unknown");
            locationLabel.setPrefWidth(100);
            
            Label priceLabel = new Label(String.format("$%.2f", accommodation.getBasePrice()));
            priceLabel.setPrefWidth(80);
            
            Label statusLabel = new Label(accommodation.isActive() ? "Active" : "Inactive");
            statusLabel.setPrefWidth(80);
            
            Button editButton = new Button("Edit");
            editButton.setOnAction(e -> showEditAccommodationDialog(accommodation));
            
            Button deactivateButton = new Button(accommodation.isActive() ? "Deactivate" : "Activate");
            deactivateButton.setOnAction(e -> {
                if (onDeactivateAccommodation != null) {
                    onDeactivateAccommodation.accept(accommodation.getAccommodationId());
                }
            });
            
            accommodationRow.getChildren().addAll(nameLabel, typeLabel, locationLabel, priceLabel, 
                                                statusLabel, editButton, deactivateButton);
            accommodationsContainer.getChildren().add(accommodationRow);
        }
    }
    
    public void showReportOptions() {
        tabPane.getSelectionModel().select(2);
    }
    
    private void showAddUserDialog() {
        // In a real app, this would create a dialog to add a new user
        // For now, we'll just create a simple mock User and pass it to the controller
        User newUser = new User();
        newUser.setUsername("new_user");
        newUser.setFirstName("New");
        newUser.setLastName("User");
        newUser.setEmail("new@example.com");
        newUser.setRole("traveler");
        
        if (onAddUser != null) {
            onAddUser.accept(newUser);
        }
    }
    
    private void showEditUserDialog(User user) {
        // In a real app, this would create a dialog to edit a user
        // For now, we'll just pass the original User to the controller
        if (onEditUser != null) {
            onEditUser.accept(user);
        }
    }
    
    private void showAddAccommodationDialog() {
        // In a real app, this would create a dialog to add a new accommodation
        // For now, we'll just create a simple mock Accommodation and pass it to the controller
        Accommodation newAccommodation = new Accommodation();
        newAccommodation.setName("New Accommodation");
        newAccommodation.setTypeId(1);
        newAccommodation.setLocationId(1);
        newAccommodation.setAddress("123 New Street");
        newAccommodation.setBasePrice(199.99);
        
        if (onAddAccommodation != null) {
            onAddAccommodation.accept(newAccommodation);
        }
    }
    
    private void showEditAccommodationDialog(Accommodation accommodation) {
        // In a real app, this would create a dialog to edit an accommodation
        // For now, we'll just pass the original Accommodation to the controller
        if (onEditAccommodation != null) {
            onEditAccommodation.accept(accommodation);
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    // Setters for callbacks
    public void setOnManageUsers(Runnable onManageUsers) {
        this.onManageUsers = onManageUsers;
    }
    
    public void setOnManageAccommodations(Runnable onManageAccommodations) {
        this.onManageAccommodations = onManageAccommodations;
    }
    
    public void setOnGenerateReports(Runnable onGenerateReports) {
        this.onGenerateReports = onGenerateReports;
    }
    
    public void setOnAddUser(Consumer<User> onAddUser) {
        this.onAddUser = onAddUser;
    }
    
    public void setOnEditUser(Consumer<User> onEditUser) {
        this.onEditUser = onEditUser;
    }
    
    public void setOnDeactivateUser(Consumer<Integer> onDeactivateUser) {
        this.onDeactivateUser = onDeactivateUser;
    }
    
    public void setOnAddAccommodation(Consumer<Accommodation> onAddAccommodation) {
        this.onAddAccommodation = onAddAccommodation;
    }
    
    public void setOnEditAccommodation(Consumer<Accommodation> onEditAccommodation) {
        this.onEditAccommodation = onEditAccommodation;
    }
    
    public void setOnDeactivateAccommodation(Consumer<Integer> onDeactivateAccommodation) {
        this.onDeactivateAccommodation = onDeactivateAccommodation;
    }
    
    public void setOnGenerateBookingReport(Consumer<String[]> onGenerateBookingReport) {
        this.onGenerateBookingReport = onGenerateBookingReport;
    }
    
    public void setOnGenerateRevenueReport(Consumer<String[]> onGenerateRevenueReport) {
        this.onGenerateRevenueReport = onGenerateRevenueReport;
    }
    
    public void setOnGenerateOccupancyReport(Consumer<String[]> onGenerateOccupancyReport) {
        this.onGenerateOccupancyReport = onGenerateOccupancyReport;
    }
}