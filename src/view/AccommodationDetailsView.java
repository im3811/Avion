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
import javafx.stage.Window;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Review;
import main.java.travelfinder.model.Room;
import main.java.travelfinder.utils.AlertUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * View class for accommodation details screen
 */
public class AccommodationDetailsView {
    
    private final Stage primaryStage;
    private Scene accommodationDetailsScene;
    
    // UI components
    private Text title;
    private ComboBox<String> locationCombo;
    private DatePicker checkInPicker;
    private DatePicker checkOutPicker;
    private ComboBox<String> guestsCombo;
    private VBox roomsList;
    private VBox reviewsBox;
    
    // Current accommodation
    private Accommodation currentAccommodation;
    
    // Event handlers
    private Runnable onBackToResultsClicked;
    private Runnable onBackToSearchClicked;
    private Consumer<Room> onBookRoomClicked;
    private Consumer<String> onWriteReviewClicked;
    private QuadConsumer<String, LocalDate, LocalDate, String> onModifySearchClicked;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public AccommodationDetailsView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createAccommodationDetailsScene();
    }
    
    /**
     * Create the accommodation details scene
     */
    private void createAccommodationDetailsScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with navigation buttons
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        // Back to results button
        Button backToResultsButton = new Button("Back to Results");
        backToResultsButton.setOnAction(e -> {
            if (onBackToResultsClicked != null) {
                onBackToResultsClicked.run();
            }
        });
        
        // Back to search button
        Button backToSearchButton = new Button("Back to Search");
        backToSearchButton.setOnAction(e -> {
            if (onBackToSearchClicked != null) {
                onBackToSearchClicked.run();
            }
        });
        
        title = new Text("Accommodation Details");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Add a spacer to push the title to the center
        Pane headerSpacer = new Pane();
        HBox.setHgrow(headerSpacer, Priority.ALWAYS);
        
        header.getChildren().addAll(backToResultsButton, backToSearchButton, headerSpacer, title);
        
        root.setTop(header);
        
        // Create the details content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        
        // Quick search bar at the top
        TitledPane quickSearchPane = new TitledPane();
        quickSearchPane.setText("Modify Search");
        quickSearchPane.setCollapsible(true);
        quickSearchPane.setExpanded(false);
        
        GridPane quickSearchGrid = new GridPane();
        quickSearchGrid.setHgap(10);
        quickSearchGrid.setVgap(10);
        quickSearchGrid.setPadding(new Insets(10));
        
        // Location dropdown
        locationCombo = new ComboBox<>();
        locationCombo.getItems().addAll("Miami, FL", "Los Angeles, CA", "New York, NY", "Chicago, IL", "San Francisco, CA");
        locationCombo.setPromptText("Where are you going?");
        
        // Check-in date
        checkInPicker = new DatePicker(LocalDate.now());
        
        // Check-out date
        checkOutPicker = new DatePicker(LocalDate.now().plusDays(7));
        
        // Guests dropdown
        guestsCombo = new ComboBox<>();
        guestsCombo.getItems().addAll("1 Adult", "2 Adults", "2 Adults, 1 Child", "2 Adults, 2 Children", "3 Adults", "4 Adults");
        guestsCombo.setValue("2 Adults");
        
        // Quick search button
        Button quickSearchButton = new Button("Update Search");
        quickSearchButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        quickSearchButton.setOnAction(e -> {
            if (locationCombo.getValue() == null) {
                AlertUtil.showAlert(Alert.AlertType.WARNING, "Search Error", "Please select a location");
                return;
            }
            
            if (onModifySearchClicked != null) {
                onModifySearchClicked.accept(
                    locationCombo.getValue(),
                    checkInPicker.getValue(),
                    checkOutPicker.getValue(),
                    guestsCombo.getValue()
                );
            }
        });
        
        quickSearchGrid.add(new Label("Location:"), 0, 0);
        quickSearchGrid.add(locationCombo, 1, 0);
        quickSearchGrid.add(new Label("Check-in:"), 0, 1);
        quickSearchGrid.add(checkInPicker, 1, 1);
        quickSearchGrid.add(new Label("Check-out:"), 0, 2);
        quickSearchGrid.add(checkOutPicker, 1, 2);
        quickSearchGrid.add(new Label("Guests:"), 0, 3);
        quickSearchGrid.add(guestsCombo, 1, 3);
        quickSearchGrid.add(quickSearchButton, 1, 4);
        
        quickSearchPane.setContent(quickSearchGrid);
        
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
        
        Text propertyName = new Text("Property Name");
        propertyName.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        // Star rating
        HBox stars = new HBox(2);
        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(18));
            star.setTextFill(i < 4 ? Color.GOLD : Color.LIGHTGRAY);
            stars.getChildren().add(star);
        }
        
        Label locationLabel = new Label("Location");
        locationLabel.setFont(Font.font(14));
        
        Text descriptionText = new Text("Description");
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
        roomsList = new VBox(15);
        
        roomSelection.getChildren().addAll(roomsTitle, roomsList);
        
        // Reviews section
        TitledPane reviewsPane = new TitledPane();
        reviewsPane.setText("Guest Reviews");
        
        reviewsBox = new VBox(15);
        reviewsBox.setPadding(new Insets(10));
        
        // Add write review button
        Button writeReviewButton = new Button("Write a Review");
        writeReviewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        writeReviewButton.setOnAction(e -> {
            if (onWriteReviewClicked != null) {
                onWriteReviewClicked.accept(currentAccommodation.getAccommodationId() + "");
            }
        });
        
        reviewsBox.getChildren().add(writeReviewButton);
        
        reviewsPane.setContent(reviewsBox);
        
        // Location section
        TitledPane locationPane = new TitledPane();
        locationPane.setText("Location");
        
        VBox locationBox = new VBox(10);
        locationBox.setPadding(new Insets(10));
        
        // Map placeholder
        Rectangle mapPlaceholder = new Rectangle(700, 300);
        mapPlaceholder.setFill(Color.LIGHTGRAY);
        
        Label addressLabel = new Label("Address");
        
        Text nearbyInfo = new Text("Nearby attractions will be displayed here.");
        
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
                quickSearchPane,  // Add quick search at the top
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
        
        // Add a floating action button to easily return to search
        Button floatingBackButton = new Button("↑ Back to Search");
        floatingBackButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 20;");
        floatingBackButton.setPadding(new Insets(10, 15, 10, 15));
        floatingBackButton.setOnAction(e -> {
            if (onBackToSearchClicked != null) {
                onBackToSearchClicked.run();
            }
        });
        
        StackPane.setAlignment(floatingBackButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(floatingBackButton, new Insets(0, 20, 20, 0));
        
        // Create a root stack pane that includes the main content and the floating button
        StackPane stackRoot = new StackPane();
        stackRoot.getChildren().addAll(root, floatingBackButton);
        
        accommodationDetailsScene = new Scene(stackRoot, 800, 600);
    }
    
    /**
     * Show the accommodation details view
     */
    public void show() {
        primaryStage.setScene(accommodationDetailsScene);
        primaryStage.setTitle("Travel Finder - Accommodation Details");
    }
    
    /**
     * Display accommodation details
     * @param accommodation The accommodation to display
     */
    public void displayAccommodationDetails(Accommodation accommodation) {
        this.currentAccommodation = accommodation;
        
        // Update UI with accommodation details
        title.setText(accommodation.getName());
        
        // Property details section
        VBox propertyDetails = (VBox) ((VBox) ((ScrollPane) ((BorderPane) ((StackPane) accommodationDetailsScene.getRoot()).getChildren().get(0)).getCenter()).getContent()).getChildren().get(2);
        
        Text propertyName = (Text) propertyDetails.getChildren().get(0);
        propertyName.setText(accommodation.getName());
        
        // Update star rating
        HBox stars = (HBox) propertyDetails.getChildren().get(1);
        stars.getChildren().clear();
        
        int starRating = accommodation.getStarRating() != null ? 
                        accommodation.getStarRating().intValue() : 0;
                        
        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(18));
            star.setTextFill(i < starRating ? Color.GOLD : Color.LIGHTGRAY);
            stars.getChildren().add(star);
        }
        
        // Update location label and description
        Label locationLabel = (Label) propertyDetails.getChildren().get(2);
        // In a real app, this would come from a location service
        locationLabel.setText("Location: " + "Unknown Location");
        
        Text descriptionText = (Text) propertyDetails.getChildren().get(4);
        descriptionText.setText(accommodation.getDescription() != null ? 
                               accommodation.getDescription() : 
                               "No description available.");
                               
        // Update address
        TitledPane locationPane = (TitledPane) ((VBox) ((ScrollPane) ((BorderPane) ((StackPane) accommodationDetailsScene.getRoot()).getChildren().get(0)).getCenter()).getContent()).getChildren().get(6);
        VBox locationBox = (VBox) locationPane.getContent();
        Label addressLabel = (Label) locationBox.getChildren().get(1);
        addressLabel.setText(accommodation.getAddress() != null ? 
                            accommodation.getAddress() : 
                            "Address not available");
    }
    
    /**
     * Display rooms for the current accommodation
     * @param rooms The list of rooms to display
     */
    public void displayRooms(List<Room> rooms) {
        roomsList.getChildren().clear();
        
        if (rooms == null || rooms.isEmpty()) {
            Text noRooms = new Text("No rooms available for this accommodation.");
            roomsList.getChildren().add(noRooms);
            return;
        }
        
        for (Room room : rooms) {
            roomsList.getChildren().add(createRoomItem(room));
        }
    }
    
    /**
     * Create a room item for display
     * @param room The room to display
     * @return An HBox containing the room details
     */
    private HBox createRoomItem(Room room) {
        HBox roomItem = new HBox(15);
        roomItem.setPadding(new Insets(10));
        roomItem.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        // Room image placeholder
        Rectangle roomImage = new Rectangle(100, 75);
        roomImage.setFill(Color.LIGHTGRAY);
        
        // Room details
        VBox roomDetails = new VBox(5);
        roomDetails.setPrefWidth(400);
        
        Label roomName = new Label(room.getRoomName());
        roomName.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Label roomDesc = new Label(room.getDescription() != null ? 
                                  room.getDescription() : 
                                  "No description available");
        
        Label roomCapacity = new Label("Sleeps " + room.getCapacity());
        
        roomDetails.getChildren().addAll(roomName, roomDesc, roomCapacity);
        
        // Price and booking section
        VBox roomBooking = new VBox(5);
        roomBooking.setAlignment(Pos.CENTER_RIGHT);
        
        // Calculate room price based on base price and modifier
        String roomPrice = "$" + (currentAccommodation.getBasePrice().doubleValue() * 
                                room.getPriceModifier().doubleValue());
        
        Label roomPriceLabel = new Label(roomPrice);
        roomPriceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        Label perNightLabel = new Label("per night");
        perNightLabel.setFont(Font.font(12));
        
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        bookButton.setOnAction(e -> {
            if (onBookRoomClicked != null) {
                onBookRoomClicked.accept(room);
            }
        });
        
        roomBooking.getChildren().addAll(roomPriceLabel, perNightLabel, bookButton);
        
        // Add components to room item
        roomItem.getChildren().addAll(roomImage, roomDetails, roomBooking);
        HBox.setHgrow(roomDetails, Priority.ALWAYS);
        
        return roomItem;
    }
    
    /**
     * Display reviews for the current accommodation
     * @param reviews The list of reviews to display
     */
    public void displayReviews(List<Review> reviews) {
        // Keep the write review button
        Button writeReviewButton = (Button) reviewsBox.getChildren().get(0);
        reviewsBox.getChildren().clear();
        reviewsBox.getChildren().add(writeReviewButton);
        
        if (reviews == null || reviews.isEmpty()) {
            Text noReviews = new Text("No reviews yet for this accommodation. Be the first to write a review!");
            reviewsBox.getChildren().add(noReviews);
            return;
        }
        
        for (Review review : reviews) {
            reviewsBox.getChildren().add(createReviewItem(review));
        }
        
        Hyperlink moreReviews = new Hyperlink("Read more reviews (" + reviews.size() + ")");
        reviewsBox.getChildren().add(moreReviews);
    }
    
    /**
     * Create a review item for display
     * @param review The review to display
     * @return A VBox containing the review details
     */
    private VBox createReviewItem(Review review) {
        VBox reviewItem = new VBox(5);
        reviewItem.setPadding(new Insets(10));
        reviewItem.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        HBox reviewHeader = new HBox(10);
        reviewHeader.setAlignment(Pos.CENTER_LEFT);
        
        // Use reviewer name if available, otherwise use a placeholder
        String reviewerName = review.getReviewerName() != null ? 
                            review.getReviewerName() : 
                            "Anonymous";
                            
        Label reviewer = new Label(reviewerName);
        reviewer.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        // Star rating
        HBox reviewStars = new HBox(2);
        for (int j = 0; j < 5; j++) {
            Label star = new Label("★");
            star.setFont(Font.font(14));
            star.setTextFill(j < review.getRating() ? Color.GOLD : Color.LIGHTGRAY);
            reviewStars.getChildren().add(star);
        }
        
        Label reviewDate = new Label("Stayed in " + review.getFormattedReviewDate());
        reviewDate.setFont(Font.font(12));
        reviewDate.setTextFill(Color.GRAY);
        
        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        reviewHeader.getChildren().addAll(reviewer, reviewStars, spacer, reviewDate);
        
        Text reviewText = new Text(review.getComment() != null ? 
                                 review.getComment() : 
                                 "No comment provided.");
        reviewText.setWrappingWidth(700);
        
        reviewItem.getChildren().addAll(reviewHeader, reviewText);
        
        // Add response if available
        if (review.hasResponse()) {
            VBox responseBox = new VBox(5);
            responseBox.setPadding(new Insets(10, 10, 5, 20));
            responseBox.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");
            
            Label responseLabel = new Label("Response from management:");
            responseLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            
            Text responseText = new Text(review.getResponse());
            responseText.setWrappingWidth(650);
            
            responseBox.getChildren().addAll(responseLabel, responseText);
            reviewItem.getChildren().add(responseBox);
        }
        
        return reviewItem;
    }
    
    /**
     * Update UI accessibility for guest users
     * @param isGuest Whether the current user is a guest
     */
    public void updateGuestAccessibility(boolean isGuest) {
        // Find the write review button
        Button writeReviewButton = (Button) reviewsBox.getChildren().get(0);
        writeReviewButton.setDisable(isGuest);
        
        // Find all book now buttons in room items
        for (int i = 0; i < roomsList.getChildren().size(); i++) {
            if (roomsList.getChildren().get(i) instanceof HBox) {
                HBox roomItem = (HBox) roomsList.getChildren().get(i);
                VBox roomBooking = (VBox) roomItem.getChildren().get(2);
                Button bookButton = (Button) roomBooking.getChildren().get(2);
                bookButton.setDisable(isGuest);
                
                if (isGuest) {
                    bookButton.setTooltip(new Tooltip("Please login to book a room"));
                }
            }
        }
    }
    
    /**
     * Show a login required dialog
     */
    public void showLoginRequiredDialog() {
        Window owner = primaryStage;
        String result = AlertUtil.showLoginRequiredAlert(owner);
        
        // Handle the result in the controller
    }
    
    // Setter methods for event handlers
    
    public void setOnBackToResultsClicked(Runnable handler) {
        this.onBackToResultsClicked = handler;
    }
    
    public void setOnBackToSearchClicked(Runnable handler) {
        this.onBackToSearchClicked = handler;
    }
    
    public void setOnBookRoomClicked(Consumer<Room> handler) {
        this.onBookRoomClicked = handler;
    }
    
    public void setOnWriteReviewClicked(Consumer<String> handler) {
        this.onWriteReviewClicked = handler;
    }
    
    public void setOnModifySearchClicked(QuadConsumer<String, LocalDate, LocalDate, String> handler) {
        this.onModifySearchClicked = handler;
    }
    
    // Inner interface for event handler with four parameters
    @FunctionalInterface
    public interface QuadConsumer<T, U, V, W> {
        void accept(T t, U u, V v, W w);
    }
}