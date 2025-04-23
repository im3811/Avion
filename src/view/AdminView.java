package main.java.travelfinder.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.User;
import main.java.travelfinder.utils.AlertUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * View class for admin panel
 */
public class AdminView {
    
    private final Stage primaryStage;
    private Scene adminScene;
    
    // UI components
    private TabPane tabPane;
    private TableView<User> usersTable;
    private TableView<Accommodation> accommodationsTable;
    private TableView<Booking> bookingsTable;
    private VBox statisticsBox;
    private VBox reportsBox;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private ComboBox<String> reportTypeCombo;
    
    // Event handlers
    private Runnable onBackButtonClicked;
    private Consumer<User> onEditUserClicked;
    private Consumer<Accommodation> onEditAccommodationClicked;
    private Consumer<Booking> onViewBookingClicked;
    private BiConsumer<User, Boolean> onUpdateUserStatusClicked;
    private BiConsumer<Accommodation, Boolean> onUpdateAccommodationStatusClicked;
    private BiConsumer<Booking, Integer> onUpdateBookingStatusClicked;
    private TriConsumer<String, LocalDate, LocalDate> onGenerateReportClicked;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public AdminView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createAdminScene();
    }
    
    /**
     * Create the admin scene
     */
    private void createAdminScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #e74c3c;"); // Red color for admin panel
        
        Button backButton = new Button("Back to Main");
        backButton.setOnAction(e -> {
            if (onBackButtonClicked != null) {
                onBackButtonClicked.run();
            }
        });
        
        Text title = new Text("Admin Panel");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the admin content with tabs
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        
        // Dashboard Tab
        Tab dashboardTab = new Tab("Dashboard");
        VBox dashboardContent = new VBox(20);
        dashboardContent.setPadding(new Insets(20));
        
        Text dashboardTitle = new Text("Admin Dashboard");
        dashboardTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Statistics cards
        HBox statsCards = new HBox(20);
        statsCards.setAlignment(Pos.CENTER);
        
        // Total Users Card
        VBox usersCard = createStatCard("Total Users", "0");
        // Total Accommodations Card
        VBox accommodationsCard = createStatCard("Total Accommodations", "0");
        // Total Bookings Card
        VBox bookingsCard = createStatCard("Total Bookings", "0");
        // Total Revenue Card
        VBox revenueCard = createStatCard("Total Revenue", "$0.00");
        
        statsCards.getChildren().addAll(usersCard, accommodationsCard, bookingsCard, revenueCard);
        
        // Recent activity section
        Text recentActivityTitle = new Text("Recent Activity");
        recentActivityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        HBox recentActivityStats = new HBox(20);
        recentActivityStats.setAlignment(Pos.CENTER);
        
        // New users in last 30 days
        VBox newUsersCard = createStatCard("New Users (30 days)", "0");
        // New bookings in last 30 days
        VBox newBookingsCard = createStatCard("New Bookings (30 days)", "0");
        // Revenue in last 30 days
        VBox monthlyRevenueCard = createStatCard("Monthly Revenue", "$0.00");
        
        recentActivityStats.getChildren().addAll(newUsersCard, newBookingsCard, monthlyRevenueCard);
        
        // Charts section
        Text chartsTitle = new Text("Analytics");
        chartsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        HBox chartsBox = new HBox(20);
        chartsBox.setAlignment(Pos.CENTER);
        
        // Create charts
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Count");
        
        BarChart<String, Number> bookingChart = new BarChart<>(xAxis, yAxis);
        bookingChart.setTitle("Bookings by Month");
        bookingChart.setPrefSize(400, 300);
        
        XYChart.Series<String, Number> bookingSeries = new XYChart.Series<>();
        bookingSeries.setName("Bookings");
        bookingSeries.getData().add(new XYChart.Data<>("Jan", 25));
        bookingSeries.getData().add(new XYChart.Data<>("Feb", 30));
        bookingSeries.getData().add(new XYChart.Data<>("Mar", 40));
        bookingSeries.getData().add(new XYChart.Data<>("Apr", 35));
        
        bookingChart.getData().add(bookingSeries);
        
        // Create pie chart for bookings by status
        PieChart statusChart = new PieChart();
        statusChart.setTitle("Bookings by Status");
        statusChart.setPrefSize(400, 300);
        
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("Confirmed", 50),
            new PieChart.Data("Completed", 30),
            new PieChart.Data("Cancelled", 15),
            new PieChart.Data("Pending", 5)
        );
        
        statusChart.setData(pieChartData);
        
        chartsBox.getChildren().addAll(bookingChart, statusChart);
        
        statisticsBox = new VBox(10);
        statisticsBox.getChildren().addAll(
            statsCards, 
            new Separator(),
            recentActivityTitle,
            recentActivityStats,
            new Separator(),
            chartsTitle,
            chartsBox
        );
        
        dashboardContent.getChildren().addAll(dashboardTitle, statisticsBox);
        
        ScrollPane dashboardScrollPane = new ScrollPane(dashboardContent);
        dashboardScrollPane.setFitToWidth(true);
        dashboardTab.setContent(dashboardScrollPane);
        
        // Users Tab
        Tab usersTab = new Tab("Users");
        VBox usersContent = new VBox(15);
        usersContent.setPadding(new Insets(20));
        
        Text usersTitle = new Text("Manage Users");
        usersTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Controls for user management
        HBox userControls = new HBox(10);
        userControls.setAlignment(Pos.CENTER_LEFT);
        
        TextField userSearchField = new TextField();
        userSearchField.setPromptText("Search users...");
        userSearchField.setPrefWidth(250);
        
        Button userSearchButton = new Button("Search");
        userSearchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        Button addUserButton = new Button("Add User");
        addUserButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        
        Pane userControlSpacer = new Pane();
        HBox.setHgrow(userControlSpacer, Priority.ALWAYS);
        
        userControls.getChildren().addAll(userSearchField, userSearchButton, userControlSpacer, addUserButton);
        
        // Users table
        usersTable = new TableView<>();
        usersTable.setPrefHeight(400);
        
        // Add columns to the users table
        TableColumn<User, Integer> userIdCol = new TableColumn<>("ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                user.getFirstName() + " " + user.getLastName()
            );
        });
        
        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        
        TableColumn<User, Boolean> activeCol = new TableColumn<>("Active");
        activeCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        
        TableColumn<User, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setPrefWidth(150);
        
        // Add action buttons to the actions column
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button toggleBtn = new Button("Disable");
            
            {
                editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                toggleBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                
                editBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    if (onEditUserClicked != null) {
                        onEditUserClicked.accept(user);
                    }
                });
                
                toggleBtn.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    boolean newStatus = !user.isActive();
                    
                    if (onUpdateUserStatusClicked != null) {
                        onUpdateUserStatusClicked.accept(user, newStatus);
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    User user = getTableView().getItems().get(getIndex());
                    toggleBtn.setText(user.isActive() ? "Disable" : "Enable");
                    toggleBtn.setStyle(user.isActive() ? 
                                     "-fx-background-color: #e74c3c; -fx-text-fill: white;" : 
                                     "-fx-background-color: #2ecc71; -fx-text-fill: white;");
                    
                    HBox buttons = new HBox(5);
                    buttons.getChildren().addAll(editBtn, toggleBtn);
                    setGraphic(buttons);
                }
            }
        });
        
        usersTable.getColumns().addAll(userIdCol, usernameCol, emailCol, nameCol, roleCol, activeCol, actionsCol);
        
        usersContent.getChildren().addAll(usersTitle, userControls, usersTable);
        
        ScrollPane usersScrollPane = new ScrollPane(usersContent);
        usersScrollPane.setFitToWidth(true);
        usersTab.setContent(usersScrollPane);
        
        // Accommodations Tab
        Tab accommodationsTab = new Tab("Accommodations");
        VBox accommodationsContent = new VBox(15);
        accommodationsContent.setPadding(new Insets(20));
        
        Text accommodationsTitle = new Text("Manage Accommodations");
        accommodationsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Controls for accommodation management
        HBox accommodationControls = new HBox(10);
        accommodationControls.setAlignment(Pos.CENTER_LEFT);
        
        TextField accommodationSearchField = new TextField();
        accommodationSearchField.setPromptText("Search accommodations...");
        accommodationSearchField.setPrefWidth(250);
        
        Button accommodationSearchButton = new Button("Search");
        accommodationSearchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        Button addAccommodationButton = new Button("Add Accommodation");
        addAccommodationButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        
        Pane accommodationControlSpacer = new Pane();
        HBox.setHgrow(accommodationControlSpacer, Priority.ALWAYS);
        
        accommodationControls.getChildren().addAll(accommodationSearchField, accommodationSearchButton, accommodationControlSpacer, addAccommodationButton);
        
        // Accommodations table
        accommodationsTable = new TableView<>();
        accommodationsTable.setPrefHeight(400);
        
        // Add columns to the accommodations table
        TableColumn<Accommodation, Integer> accommodationIdCol = new TableColumn<>("ID");
        accommodationIdCol.setCellValueFactory(new PropertyValueFactory<>("accommodationId"));
        
        TableColumn<Accommodation, String> accommodationNameCol = new TableColumn<>("Name");
        accommodationNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Accommodation, Integer> typeIdCol = new TableColumn<>("Type");
        typeIdCol.setCellValueFactory(new PropertyValueFactory<>("typeId"));
        
        TableColumn<Accommodation, Integer> locationIdCol = new TableColumn<>("Location");
        locationIdCol.setCellValueFactory(new PropertyValueFactory<>("locationId"));
        
        TableColumn<Accommodation, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        TableColumn<Accommodation, BigDecimal> priceCol = new TableColumn<>("Base Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("basePrice"));
        
        TableColumn<Accommodation, Boolean> verifiedCol = new TableColumn<>("Verified");
        verifiedCol.setCellValueFactory(new PropertyValueFactory<>("verified"));
        
        TableColumn<Accommodation, Boolean> accommodationActiveCol = new TableColumn<>("Active");
        accommodationActiveCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        
        TableColumn<Accommodation, Void> accommodationActionsCol = new TableColumn<>("Actions");
        accommodationActionsCol.setPrefWidth(150);
        
        // Add action buttons to the actions column
        accommodationActionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button toggleBtn = new Button("Disable");
            
            {
                editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                toggleBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
                
                editBtn.setOnAction(event -> {
                    Accommodation accommodation = getTableView().getItems().get(getIndex());
                    if (onEditAccommodationClicked != null) {
                        onEditAccommodationClicked.accept(accommodation);
                    }
                });
                
                toggleBtn.setOnAction(event -> {
                    Accommodation accommodation = getTableView().getItems().get(getIndex());
                    boolean newStatus = !accommodation.isActive();
                    
                    if (onUpdateAccommodationStatusClicked != null) {
                        onUpdateAccommodationStatusClicked.accept(accommodation, newStatus);
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Accommodation accommodation = getTableView().getItems().get(getIndex());
                    toggleBtn.setText(accommodation.isActive() ? "Disable" : "Enable");
                    toggleBtn.setStyle(accommodation.isActive() ? 
                                     "-fx-background-color: #e74c3c; -fx-text-fill: white;" : 
                                     "-fx-background-color: #2ecc71; -fx-text-fill: white;");
                    
                    HBox buttons = new HBox(5);
                    buttons.getChildren().addAll(editBtn, toggleBtn);
                    setGraphic(buttons);
                }
            }
        });
        
        accommodationsTable.getColumns().addAll(accommodationIdCol, accommodationNameCol, typeIdCol, locationIdCol, addressCol, priceCol, verifiedCol, accommodationActiveCol, accommodationActionsCol);
        
        accommodationsContent.getChildren().addAll(accommodationsTitle, accommodationControls, accommodationsTable);
        
        ScrollPane accommodationsScrollPane = new ScrollPane(accommodationsContent);
        accommodationsScrollPane.setFitToWidth(true);
        accommodationsTab.setContent(accommodationsScrollPane);
        
        // Bookings Tab
        Tab bookingsTab = new Tab("Bookings");
        VBox bookingsContent = new VBox(15);
        bookingsContent.setPadding(new Insets(20));
        
        Text bookingsTitle = new Text("Manage Bookings");
        bookingsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Controls for booking management
        HBox bookingControls = new HBox(10);
        bookingControls.setAlignment(Pos.CENTER_LEFT);
        
        TextField bookingSearchField = new TextField();
        bookingSearchField.setPromptText("Search bookings...");
        bookingSearchField.setPrefWidth(250);
        
        Button bookingSearchButton = new Button("Search");
        bookingSearchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        ComboBox<String> statusFilterCombo = new ComboBox<>();
        statusFilterCombo.getItems().addAll("All", "Pending", "Confirmed", "Completed", "Cancelled", "No-Show");
        statusFilterCombo.setValue("All");
        
        Pane bookingControlSpacer = new Pane();
        HBox.setHgrow(bookingControlSpacer, Priority.ALWAYS);
        
        bookingControls.getChildren().addAll(bookingSearchField, bookingSearchButton, new Label("Status:"), statusFilterCombo);
        
        // Bookings table
        bookingsTable = new TableView<>();
        bookingsTable.setPrefHeight(400);
        
        // Add columns to the bookings table
        TableColumn<Booking, Integer> bookingIdCol = new TableColumn<>("ID");
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        
        TableColumn<Booking, String> referenceCol = new TableColumn<>("Reference");
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("referenceNumber"));
        
        TableColumn<Booking, Integer> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        TableColumn<Booking, Integer> accommodationCol = new TableColumn<>("Accom. ID");
        accommodationCol.setCellValueFactory(new PropertyValueFactory<>("accommodationId"));
        
        TableColumn<Booking, LocalDate> checkInCol = new TableColumn<>("Check In");
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        
        TableColumn<Booking, LocalDate> checkOutCol = new TableColumn<>("Check Out");
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        
        TableColumn<Booking, Integer> guestsCol = new TableColumn<>("Guests");
        guestsCol.setCellValueFactory(new PropertyValueFactory<>("numGuests"));
        
        TableColumn<Booking, Integer> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusId"));
        
        TableColumn<Booking, BigDecimal> totalPriceCol = new TableColumn<>("Total Price");
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        TableColumn<Booking, Void> bookingActionsCol = new TableColumn<>("Actions");
        bookingActionsCol.setPrefWidth(200);
        
        // Add action buttons to the actions column
        bookingActionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final ComboBox<String> statusCombo = new ComboBox<>();
            
            {
                viewBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                
                statusCombo.getItems().addAll("Pending", "Confirmed", "Completed", "Cancelled", "No-Show");
                statusCombo.setPrefWidth(100);
                
                viewBtn.setOnAction(event -> {
                    Booking booking = getTableView().getItems().get(getIndex());
                    if (onViewBookingClicked != null) {
                        onViewBookingClicked.accept(booking);
                    }
                });
                
                statusCombo.setOnAction(event -> {
                    Booking booking = getTableView().getItems().get(getIndex());
                    String selectedStatus = statusCombo.getValue();
                    int statusId = 0;
                    
                    switch (selectedStatus) {
                        case "Pending": statusId = 1; break;
                        case "Confirmed": statusId = 2; break;
                        case "Completed": statusId = 3; break;
                        case "Cancelled": statusId = 4; break;
                        case "No-Show": statusId = 5; break;
                    }
                    
                    if (statusId > 0 && onUpdateBookingStatusClicked != null) {
                        onUpdateBookingStatusClicked.accept(booking, statusId);
                    }
                });
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Booking booking = getTableView().getItems().get(getIndex());
                    int statusId = booking.getStatusId();
                    
                    // Set the selected status in the combo box
                    switch (statusId) {
                        case 1: statusCombo.setValue("Pending"); break;
                        case 2: statusCombo.setValue("Confirmed"); break;
                        case 3: statusCombo.setValue("Completed"); break;
                        case 4: statusCombo.setValue("Cancelled"); break;
                        case 5: statusCombo.setValue("No-Show"); break;
                        default: statusCombo.setValue("");
                    }
                    
                    HBox buttons = new HBox(5);
                    buttons.getChildren().addAll(viewBtn, statusCombo);
                    setGraphic(buttons);
                }
            }
        });
        
        bookingsTable.getColumns().addAll(bookingIdCol, referenceCol, userIdCol, accommodationCol, checkInCol, checkOutCol, guestsCol, statusCol, totalPriceCol, bookingActionsCol);
        
        bookingsContent.getChildren().addAll(bookingsTitle, bookingControls, bookingsTable);
        
        ScrollPane bookingsScrollPane = new ScrollPane(bookingsContent);
        bookingsScrollPane.setFitToWidth(true);
        bookingsTab.setContent(bookingsScrollPane);
        
        // Reports Tab
        Tab reportsTab = new Tab("Reports");
        VBox reportsContent = new VBox(15);
        reportsContent.setPadding(new Insets(20));
        
        Text reportsTitle = new Text("Generate Reports");
        reportsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Report generation controls
        GridPane reportControls = new GridPane();
        reportControls.setHgap(10);
        reportControls.setVgap(10);
        reportControls.setPadding(new Insets(20));
        reportControls.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Label reportTypeLabel = new Label("Report Type:");
        reportTypeCombo = new ComboBox<>();
        reportTypeCombo.getItems().addAll("User Activity", "Booking Statistics", "Revenue Analysis");
        reportTypeCombo.setValue("Booking Statistics");
        
        Label dateRangeLabel = new Label("Date Range:");
        startDatePicker = new DatePicker(LocalDate.now().minusMonths(1));
        endDatePicker = new DatePicker(LocalDate.now());
        
        HBox dateRangeBox = new HBox(10);
        dateRangeBox.getChildren().addAll(startDatePicker, new Label("to"), endDatePicker);
        
        Button generateReportButton = new Button("Generate Report");
        generateReportButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        generateReportButton.setOnAction(e -> {
            if (onGenerateReportClicked != null && validateReportInput()) {
                onGenerateReportClicked.accept(
                    reportTypeCombo.getValue(),
                    startDatePicker.getValue(),
                    endDatePicker.getValue()
                );
            }
        });
        
        reportControls.add(reportTypeLabel, 0, 0);
        reportControls.add(reportTypeCombo, 1, 0);
        reportControls.add(dateRangeLabel, 0, 1);
        reportControls.add(dateRangeBox, 1, 1);
        reportControls.add(generateReportButton, 1, 2);
        
        // Report output area
        Text reportOutputTitle = new Text("Report Output");
        reportOutputTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        reportsBox = new VBox(10);
        reportsBox.setPadding(new Insets(20));
        reportsBox.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        reportsBox.setMinHeight(300);
        
        Text placeholderText = new Text("Generated reports will appear here.");
        placeholderText.setFill(Color.GRAY);
        
        reportsBox.getChildren().add(placeholderText);
        reportsBox.setAlignment(Pos.CENTER);
        
        reportsContent.getChildren().addAll(reportsTitle, reportControls, reportOutputTitle, reportsBox);
        
        ScrollPane reportsScrollPane = new ScrollPane(reportsContent);
        reportsScrollPane.setFitToWidth(true);
        reportsTab.setContent(reportsScrollPane);
        
        // Add all tabs
        tabPane.getTabs().addAll(dashboardTab, usersTab, accommodationsTab, bookingsTab, reportsTab);
        
        root.setCenter(tabPane);
        
        adminScene = new Scene(root, 1000, 700);
    }
    
    /**
     * Create a statistic card
     * @param title The card title
     * @param value The card value
     * @return A VBox containing the card
     */
    private VBox createStatCard(String title, String value) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setMinWidth(200);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text valueText = new Text(value);
        valueText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        card.getChildren().addAll(titleText, valueText);
        return card;
    }
    
    /**
     * Show the admin view
     */
    public void show() {
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Travel Finder - Admin Panel");
    }
    
    /**
     * Display users in the users table
     * @param users The list of users to display
     */
    public void displayUsers(List<User> users) {
        ObservableList<User> data = FXCollections.observableArrayList(users);
        usersTable.setItems(data);
    }
    
    /**
     * Display accommodations in the accommodations table
     * @param accommodations The list of accommodations to display
     */
    public void displayAccommodations(List<Accommodation> accommodations) {
        ObservableList<Accommodation> data = FXCollections.observableArrayList(accommodations);
        accommodationsTable.setItems(data);
    }
    
    /**
     * Display bookings in the bookings table
     * @param bookings The list of bookings to display
     */
    public void displayBookings(List<Booking> bookings) {
        ObservableList<Booking> data = FXCollections.observableArrayList(bookings);
        bookingsTable.setItems(data);
    }
    
    /**
     * Display statistics on the dashboard
     * @param totalUsers Total number of users
     * @param totalAccommodations Total number of accommodations
     * @param totalBookings Total number of bookings
     * @param totalRevenue Total revenue
     * @param newUsers Number of new users in the last 30 days
     * @param newBookings Number of new bookings in the last 30 days
     * @param monthlyRevenue Revenue in the last 30 days
     */
    public void displayStatistics(int totalUsers, int totalAccommodations, int totalBookings, double totalRevenue,
                                int newUsers, int newBookings, double monthlyRevenue) {
        // Update stats cards
        HBox statsCards = (HBox) statisticsBox.getChildren().get(0);
        
        VBox usersCard = (VBox) statsCards.getChildren().get(0);
        Text usersValue = (Text) usersCard.getChildren().get(1);
        usersValue.setText(String.valueOf(totalUsers));
        
        VBox accommodationsCard = (VBox) statsCards.getChildren().get(1);
        Text accommodationsValue = (Text) accommodationsCard.getChildren().get(1);
        accommodationsValue.setText(String.valueOf(totalAccommodations));
        
        VBox bookingsCard = (VBox) statsCards.getChildren().get(2);
        Text bookingsValue = (Text) bookingsCard.getChildren().get(1);
        bookingsValue.setText(String.valueOf(totalBookings));
        
        VBox revenueCard = (VBox) statsCards.getChildren().get(3);
        Text revenueValue = (Text) revenueCard.getChildren().get(1);
        revenueValue.setText("$" + String.format("%.2f", totalRevenue));
        
        // Update recent activity
        HBox recentActivityStats = (HBox) statisticsBox.getChildren().get(3);
        
        VBox newUsersCard = (VBox) recentActivityStats.getChildren().get(0);
        Text newUsersValue = (Text) newUsersCard.getChildren().get(1);
        newUsersValue.setText(String.valueOf(newUsers));
        
        VBox newBookingsCard = (VBox) recentActivityStats.getChildren().get(1);
        Text newBookingsValue = (Text) newBookingsCard.getChildren().get(1);
        newBookingsValue.setText(String.valueOf(newBookings));
        
        VBox monthlyRevenueCard = (VBox) recentActivityStats.getChildren().get(2);
        Text monthlyRevenueValue = (Text) monthlyRevenueCard.getChildren().get(1);
        monthlyRevenueValue.setText("$" + String.format("%.2f", monthlyRevenue));
    }
    
    /**
     * Display a users report
     * @param users The list of users in the report
     * @param startDate The start date of the report
     * @param endDate The end date of the report
     */
    public void displayUsersReport(List<User> users, LocalDate startDate, LocalDate endDate) {
        reportsBox.getChildren().clear();
        
        Text reportTitle = new Text("User Activity Report");
        reportTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Text dateRange = new Text("Date Range: " + startDate.toString() + " to " + endDate.toString());
        
        Text userCountText = new Text("Total Users: " + users.size());
        userCountText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Create a table for user data
        TableView<User> userReportTable = new TableView<>();
        
        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        
        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                user.getFirstName() + " " + user.getLastName()
            );
        });
        
        TableColumn<User, LocalDateTime> createdCol = new TableColumn<>("Created At");
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        userReportTable.getColumns().addAll(idCol, usernameCol, nameCol, createdCol);
        userReportTable.setItems(FXCollections.observableArrayList(users));
        userReportTable.setPrefHeight(300);
        
        Button exportButton = new Button("Export to CSV");
        exportButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        reportsBox.getChildren().addAll(reportTitle, dateRange, userCountText, new Separator(), userReportTable, exportButton);
        reportsBox.setAlignment(Pos.TOP_LEFT);
    }
    
    /**
     * Display a bookings report
     * @param bookings The list of bookings in the report
     * @param startDate The start date of the report
     * @param endDate The end date of the report
     */
    public void displayBookingsReport(List<Booking> bookings, LocalDate startDate, LocalDate endDate) {
        reportsBox.getChildren().clear();
        
        Text reportTitle = new Text("Booking Statistics Report");
        reportTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Text dateRange = new Text("Date Range: " + startDate.toString() + " to " + endDate.toString());
        
        Text bookingCountText = new Text("Total Bookings: " + bookings.size());
        bookingCountText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Create a table for booking data
        TableView<Booking> bookingReportTable = new TableView<>();
        
        TableColumn<Booking, String> refCol = new TableColumn<>("Reference");
        refCol.setCellValueFactory(new PropertyValueFactory<>("referenceNumber"));
        
        TableColumn<Booking, LocalDate> checkInCol = new TableColumn<>("Check In");
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        
        TableColumn<Booking, LocalDate> checkOutCol = new TableColumn<>("Check Out");
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        
        TableColumn<Booking, Integer> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusId"));
        
        TableColumn<Booking, BigDecimal> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        TableColumn<Booking, LocalDateTime> createdCol = new TableColumn<>("Created At");
        createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        bookingReportTable.getColumns().addAll(refCol, checkInCol, checkOutCol, statusCol, priceCol, createdCol);
        bookingReportTable.setItems(FXCollections.observableArrayList(bookings));
        bookingReportTable.setPrefHeight(300);
        
        Button exportButton = new Button("Export to CSV");
        exportButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        reportsBox.getChildren().addAll(reportTitle, dateRange, bookingCountText, new Separator(), bookingReportTable, exportButton);
        reportsBox.setAlignment(Pos.TOP_LEFT);
    }
    
    /**
     * Display a revenue report
     * @param totalRevenue The total revenue in the report period
     * @param startDate The start date of the report
     * @param endDate The end date of the report
     */
    public void displayRevenueReport(double totalRevenue, LocalDate startDate, LocalDate endDate) {
        reportsBox.getChildren().clear();
        
        Text reportTitle = new Text("Revenue Analysis Report");
        reportTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Text dateRange = new Text("Date Range: " + startDate.toString() + " to " + endDate.toString());
        
        Text revenueText = new Text("Total Revenue: $" + String.format("%.2f", totalRevenue));
        revenueText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Create chart for revenue by day
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Revenue ($)");
        
        BarChart<String, Number> revenueChart = new BarChart<>(xAxis, yAxis);
        revenueChart.setTitle("Daily Revenue");
        revenueChart.setPrefHeight(400);
        
        XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>();
        revenueSeries.setName("Revenue");
        
        // In a real app, this would be populated from the data
        // For now, add some sample data
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            // Random revenue between $100 and $500
            double dailyRevenue = 100 + Math.random() * 400;
            revenueSeries.getData().add(new XYChart.Data<>(date.toString(), dailyRevenue));
            date = date.plusDays(1);
        }
        
        revenueChart.getData().add(revenueSeries);
        
        Button exportButton = new Button("Export to CSV");
        exportButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        
        reportsBox.getChildren().addAll(reportTitle, dateRange, revenueText, new Separator(), revenueChart, exportButton);
        reportsBox.setAlignment(Pos.TOP_LEFT);
    }
    
    /**
     * Validate report input
     * @return true if the input is valid, false otherwise
     */
    private boolean validateReportInput() {
        if (reportTypeCombo.getValue() == null) {
            showAlert("Please select a report type");
            return false;
        }
        
        if (startDatePicker.getValue() == null) {
            showAlert("Please select a start date");
            return false;
        }
        
        if (endDatePicker.getValue() == null) {
            showAlert("Please select an end date");
            return false;
        }
        
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showAlert("Start date must be before end date");
            return false;
        }
        
        return true;
    }
    
    /**
     * Show an alert
     * @param message The alert message
     */
    public void showAlert(String message) {
        AlertUtil.showAlert(Alert.AlertType.WARNING, "Admin Error", message);
    }
    
    // Setter methods for event handlers
    
    public void setOnBackButtonClicked(Runnable handler) {
        this.onBackButtonClicked = handler;
    }
    
    public void setOnEditUserClicked(Consumer<User> handler) {
        this.onEditUserClicked = handler;
    }
    
    public void setOnEditAccommodationClicked(Consumer<Accommodation> handler) {
        this.onEditAccommodationClicked = handler;
    }
    
    public void setOnViewBookingClicked(Consumer<Booking> handler) {
        this.onViewBookingClicked = handler;
    }
    
    public void setOnUpdateUserStatusClicked(BiConsumer<User, Boolean> handler) {
        this.onUpdateUserStatusClicked = handler;
    }
    
    public void setOnUpdateAccommodationStatusClicked(BiConsumer<Accommodation, Boolean> handler) {
        this.onUpdateAccommodationStatusClicked = handler;
    }
    
    public void setOnUpdateBookingStatusClicked(BiConsumer<Booking, Integer> handler) {
        this.onUpdateBookingStatusClicked = handler;
    }
    
    public void setOnGenerateReportClicked(TriConsumer<String, LocalDate, LocalDate> handler) {
        this.onGenerateReportClicked = handler;
    }
    
    // Inner interface for event handler with three parameters
    @FunctionalInterface
    public interface TriConsumer<T, U, V> {
        void accept(T t, U u, V v);
    }
}