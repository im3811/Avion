package main.java.travelfinder.view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Review;
import main.java.travelfinder.model.Room;

import java.util.function.Consumer;

public class AccommodationDetailsView {
    
    private final Stage stage;
    private VBox roomsContainer;
    private VBox reviewsContainer;
    private Consumer<Integer> onBookRoom;
    
    public AccommodationDetailsView() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Accommodation Details");
        stage.setMinWidth(600);
        stage.setMinHeight(500);
    }
    
    public void displayAccommodation(Accommodation accommodation, List<Room> rooms, List<Review> reviews) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        
        // Header with accommodation info
        VBox header = createHeader(accommodation);
        layout.setTop(header);
        
        // Create tab pane for rooms, amenities, reviews
        TabPane tabPane = new TabPane();
        
        // Rooms tab
        Tab roomsTab = new Tab("Rooms");
        roomsTab.setClosable(false);
        roomsContainer = new VBox(10);
        roomsContainer.setPadding(new Insets(10));
        populateRooms(rooms);
        ScrollPane roomsScroll = new ScrollPane(roomsContainer);
        roomsScroll.setFitToWidth(true);
        roomsTab.setContent(roomsScroll);
        
        // Amenities tab
        Tab amenitiesTab = new Tab("Amenities");
        amenitiesTab.setClosable(false);
        GridPane amenitiesGrid = new GridPane();
        amenitiesGrid.setPadding(new Insets(10));
        amenitiesGrid.setHgap(10);
        amenitiesGrid.setVgap(10);
        
        // Add amenities to grid - in a real app, these would come from accommodation.getAmenities()
        int row = 0;
        for (String amenity : List.of("WiFi", "Swimming Pool", "Free Parking", "Air Conditioning", "Breakfast Included")) {
            Label amenityLabel = new Label(amenity);
            amenitiesGrid.add(amenityLabel, 0, row++);
        }
        
        ScrollPane amenitiesScroll = new ScrollPane(amenitiesGrid);
        amenitiesScroll.setFitToWidth(true);
        amenitiesTab.setContent(amenitiesScroll);
        
        // Reviews tab
        Tab reviewsTab = new Tab("Reviews");
        reviewsTab.setClosable(false);
        reviewsContainer = new VBox(10);
        reviewsContainer.setPadding(new Insets(10));
        populateReviews(reviews);
        ScrollPane reviewsScroll = new ScrollPane(reviewsContainer);
        reviewsScroll.setFitToWidth(true);
        reviewsTab.setContent(reviewsScroll);
        
        tabPane.getTabs().addAll(roomsTab, amenitiesTab, reviewsTab);
        layout.setCenter(tabPane);
        
        // Back button at the bottom
        Button backButton = new Button("Back to Search Results");
        backButton.setOnAction(e -> stage.close());
        layout.setBottom(backButton);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
    }
    
    private VBox createHeader(Accommodation accommodation) {
        VBox header = new VBox(10);
        header.setPadding(new Insets(10));
        
        Label nameLabel = new Label(accommodation.getName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        
        HBox ratingBox = new HBox(5);
        Label ratingLabel = new Label(String.format("%.1f stars", accommodation.getStarRating()));
        ratingBox.getChildren().add(ratingLabel);
        
        Label addressLabel = new Label(accommodation.getAddress());
        
        Label priceLabel = new Label(String.format("From $%.2f per night", accommodation.getBasePrice()));
        priceLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        
        TextArea descriptionArea = new TextArea(accommodation.getDescription());
        descriptionArea.setWrapText(true);
        descriptionArea.setEditable(false);
        descriptionArea.setPrefHeight(80);
        
        header.getChildren().addAll(nameLabel, ratingBox, addressLabel, priceLabel, descriptionArea);
        
        return header;
    }
    
    private void populateRooms(List<Room> rooms) {
        roomsContainer.getChildren().clear();
        
        if (rooms.isEmpty()) {
            Label noRoomsLabel = new Label("No rooms available");
            roomsContainer.getChildren().add(noRoomsLabel);
            return;
        }
        
        for (Room room : rooms) {
            VBox roomBox = new VBox(5);
            roomBox.setPadding(new Insets(10));
            roomBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5;");
            
            Label nameLabel = new Label(room.getRoomName());
            nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            
            Label capacityLabel = new Label("Capacity: " + room.getCapacity() + " guests");
            
            Label priceLabel = new Label(String.format("$%.2f per night", room.calculatePrice()));
            
            Button bookButton = new Button("Book Now");
            bookButton.setOnAction(e -> {
                if (onBookRoom != null) {
                    onBookRoom.accept(room.getRoomId());
                }
            });
            
            roomBox.getChildren().addAll(nameLabel, capacityLabel, 
                                        new Label("Bed Type: " + (room.getBedType() != null ? room.getBedType() : "Standard")), 
                                        priceLabel, bookButton);
            
            roomsContainer.getChildren().add(roomBox);
        }
    }
    
    private void populateReviews(List<Review> reviews) {
        reviewsContainer.getChildren().clear();
        
        if (reviews.isEmpty()) {
            Label noReviewsLabel = new Label("No reviews yet");
            reviewsContainer.getChildren().add(noReviewsLabel);
            return;
        }
        
        for (Review review : reviews) {
            VBox reviewBox = new VBox(5);
            reviewBox.setPadding(new Insets(10));
            reviewBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5;");
            
            HBox ratingBox = new HBox(5);
            Label ratingLabel = new Label(review.getRating() + "/5 stars");
            ratingBox.getChildren().add(ratingLabel);
            
            Label userLabel = new Label("By: " + (review.getUser() != null ? review.getUser().getUsername() : "Anonymous"));
            
            Label dateLabel = new Label("Posted on: " + (review.getReviewDate() != null ? review.getReviewDate().toString() : "Unknown date"));
            
            Label commentLabel = new Label(review.getComment() != null ? review.getComment() : "No comment");
            commentLabel.setWrapText(true);
            
            reviewBox.getChildren().addAll(ratingBox, userLabel, dateLabel, commentLabel);
            
            if (review.hasResponse()) {
                VBox responseBox = new VBox(5);
                responseBox.setPadding(new Insets(5, 5, 5, 20));
                responseBox.setStyle("-fx-background-color: #f9f9f9;");
                
                Label responseLabel = new Label("Response: " + review.getResponse());
                responseLabel.setWrapText(true);
                
                responseBox.getChildren().add(responseLabel);
                reviewBox.getChildren().add(responseBox);
            }
            
            reviewsContainer.getChildren().add(reviewBox);
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    public void setOnBookRoom(Consumer<Integer> onBookRoom) {
        this.onBookRoom = onBookRoom;
    }
}