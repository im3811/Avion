package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.java.travelfinder.model.Accommodation;

import java.util.List;
import java.util.function.Consumer;

public class SearchResultsView {
    
    private final Stage stage;
    private VBox resultsContainer;
    private Consumer<Integer> onSelectAccommodation;
    
    public SearchResultsView() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Search Results");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
    }
    
    public void displayResults(List<Accommodation> results) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        
        // Header
        Label headerLabel = new Label("Search Results");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        Label countLabel = new Label("Found " + results.size() + " accommodations");
        
        VBox headerBox = new VBox(5);
        headerBox.getChildren().addAll(headerLabel, countLabel);
        layout.setTop(headerBox);
        
        // Results container
        resultsContainer = new VBox(10);
        resultsContainer.setPadding(new Insets(10, 0, 10, 0));
        
        populateResults(results);
        
        ScrollPane scrollPane = new ScrollPane(resultsContainer);
        scrollPane.setFitToWidth(true);
        layout.setCenter(scrollPane);
        
        // Filter sidebar (simplified)
        VBox filterBox = new VBox(10);
        filterBox.setPadding(new Insets(10));
        filterBox.setPrefWidth(150);
        
        Label filterLabel = new Label("Filters");
        filterLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        // Price range filter
        Label priceLabel = new Label("Price Range");
        Slider priceSlider = new Slider(0, 1000, 500);
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        
        // Star rating filter
        Label ratingLabel = new Label("Star Rating");
        VBox ratingBox = new VBox(5);
        for (int i = 5; i >= 1; i--) {
            CheckBox ratingCheck = new CheckBox(i + " stars");
            ratingBox.getChildren().add(ratingCheck);
        }
        
        // Amenities filter
        Label amenitiesLabel = new Label("Amenities");
        VBox amenitiesBox = new VBox(5);
        for (String amenity : List.of("WiFi", "Pool", "Parking", "Breakfast")) {
            CheckBox amenityCheck = new CheckBox(amenity);
            amenitiesBox.getChildren().add(amenityCheck);
        }
        
        Button applyFiltersButton = new Button("Apply Filters");
        
        filterBox.getChildren().addAll(
            filterLabel, priceLabel, priceSlider, ratingLabel, 
            ratingBox, amenitiesLabel, amenitiesBox, applyFiltersButton
        );
        layout.setLeft(filterBox);
        
        // Close button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        layout.setBottom(closeButton);
        
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
    }
    
    private void populateResults(List<Accommodation> results) {
        resultsContainer.getChildren().clear();
        
        for (Accommodation accommodation : results) {
            VBox accommodationBox = new VBox(5);
            accommodationBox.setPadding(new Insets(10));
            accommodationBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5;");
            
            HBox headerBox = new HBox(10);
            
            Label nameLabel = new Label(accommodation.getName());
            nameLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            
            Label ratingLabel = new Label(String.format("%.1f stars", accommodation.getStarRating()));
            
            headerBox.getChildren().addAll(nameLabel, ratingLabel);
            
            Label locationLabel = new Label(
                accommodation.getLocation() != null ? accommodation.getLocation().getName() : "Unknown Location"
            );
            
            Label addressLabel = new Label(accommodation.getAddress());
            
            Label priceLabel = new Label(String.format("From $%.2f per night", accommodation.getBasePrice()));
            priceLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
            
            Button viewDetailsButton = new Button("View Details");
            viewDetailsButton.setOnAction(e -> {
                if (onSelectAccommodation != null) {
                    onSelectAccommodation.accept(accommodation.getAccommodationId());
                }
            });
            
            accommodationBox.getChildren().addAll(headerBox, locationLabel, addressLabel, priceLabel, viewDetailsButton);
            
            resultsContainer.getChildren().add(accommodationBox);
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    public void setOnSelectAccommodation(Consumer<Integer> onSelectAccommodation) {
        this.onSelectAccommodation = onSelectAccommodation;
    }
}