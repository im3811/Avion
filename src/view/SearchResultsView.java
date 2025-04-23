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
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.utils.AlertUtil;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * View class for search results screen
 */
public class SearchResultsView {
    
    private final Stage primaryStage;
    private Scene searchResultsScene;
    
    // UI components
    private Text resultsInfo;
    private VBox resultsList;
    private Slider priceSlider;
    private Map<String, CheckBox> filterCheckboxes = new HashMap<>();
    
    // Search criteria
    private String location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guests;
    
    // Event handlers
    private Runnable onBackButtonClicked;
    private Consumer<Map<String, Boolean>> onFilterApplied;
    private Consumer<Accommodation> onAccommodationSelected;
    private BiConsumer<Accommodation, String> onBookButtonClicked;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public SearchResultsView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createSearchResultsScene();
    }
    
    /**
     * Create the search results scene
     */
    private void createSearchResultsScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back to Search");
        backButton.setOnAction(e -> {
            if (onBackButtonClicked != null) {
                onBackButtonClicked.run();
            }
        });
        
        Text title = new Text("Search Results");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create the search results content
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        resultsInfo = new Text("Showing results...");
        
        // Create filter section
        TitledPane filtersPane = new TitledPane();
        filtersPane.setText("Filters");
        
        GridPane filterGrid = new GridPane();
        filterGrid.setHgap(10);
        filterGrid.setVgap(10);
        filterGrid.setPadding(new Insets(10));
        
        // Price range slider
        Label priceLabel = new Label("Price Range:");
        priceSlider = new Slider(0, 1000, 500);
        priceSlider.setShowTickLabels(true);
        priceSlider.setShowTickMarks(true);
        priceSlider.setMajorTickUnit(250);
        
        // Star rating checkboxes
        Label ratingLabel = new Label("Star Rating:");
        VBox ratingBox = new VBox(5);
        for (int i = 5; i >= 1; i--) {
            CheckBox starCheck = new CheckBox(i + " Stars");
            starCheck.setSelected(true);
            filterCheckboxes.put("star" + i, starCheck);
            ratingBox.getChildren().add(starCheck);
        }
        
        // Property type checkboxes
        Label typeLabel = new Label("Property Type:");
        VBox typeBox = new VBox(5);
        for (String type : new String[]{"Hotel", "Apartment", "Vacation Home", "Hostel", "Resort"}) {
            CheckBox typeCheck = new CheckBox(type);
            typeCheck.setSelected(true);
            filterCheckboxes.put("type_" + type, typeCheck);
            typeBox.getChildren().add(typeCheck);
        }
        
        // Amenities checkboxes
        Label amenitiesLabel = new Label("Amenities:");
        VBox amenitiesBox = new VBox(5);
        for (String amenity : new String[]{"WiFi", "Swimming Pool", "Free Parking", "Air Conditioning", "Breakfast Included"}) {
            CheckBox amenityCheck = new CheckBox(amenity);
            filterCheckboxes.put("amenity_" + amenity, amenityCheck);
            amenitiesBox.getChildren().add(amenityCheck);
        }
        
        // Apply filters button
        Button applyFiltersButton = new Button("Apply Filters");
        applyFiltersButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        applyFiltersButton.setOnAction(e -> {
            if (onFilterApplied != null) {
                Map<String, Boolean> filters = new HashMap<>();
                
                // Get all filter values
                for (Map.Entry<String, CheckBox> entry : filterCheckboxes.entrySet()) {
                    filters.put(entry.getKey(), entry.getValue().isSelected());
                }
                
                // Add price range
                filters.put("priceMin", true);
                filters.put("priceMax", true);
                
                onFilterApplied.accept(filters);
            }
        });
        
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
        resultsList = new VBox(15);
        
        // Add components to content
        content.getChildren().addAll(resultsInfo, filtersPane, resultsList);
        
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        
        root.setCenter(scrollPane);
        
        searchResultsScene = new Scene(root, 800, 600);
    }
    
    /**
     * Show the search results view
     */
    public void show() {
        primaryStage.setScene(searchResultsScene);
        primaryStage.setTitle("Travel Finder - Search Results");
    }
    
    /**
     * Display search criteria
     * @param location The selected location
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @param guests The number of guests
     */
    public void displaySearchCriteria(String location, LocalDate checkInDate, LocalDate checkOutDate, String guests) {
        this.location = location;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
        
        resultsInfo.setText("Showing results for " + location + " from " + 
                           checkInDate.toString() + " to " + checkOutDate.toString() + 
                           " for " + guests);
    }
    
    /**
     * Display search results
     * @param accommodations The list of accommodations that match the search
     */
    public void displaySearchResults(List<Accommodation> accommodations) {
        resultsList.getChildren().clear();
        
        if (accommodations == null || accommodations.isEmpty()) {
            Text noResults = new Text("No accommodations found matching your criteria.");
            noResults.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            resultsList.getChildren().add(noResults);
            return;
        }
        
        for (Accommodation accommodation : accommodations) {
            resultsList.getChildren().add(createSearchResultItem(accommodation));
        }
    }
    
    /**
     * Create a search result item
     * @param accommodation The accommodation to display
     * @return An HBox containing the accommodation details
     */
    private HBox createSearchResultItem(Accommodation accommodation) {
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
        
        Label nameLabel = new Label(accommodation.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        // Star rating
        HBox stars = new HBox(2);
        int starRating = accommodation.getStarRating() != null ? 
                        accommodation.getStarRating().intValue() : 0;
        
        for (int i = 0; i < 5; i++) {
            Label star = new Label("★");
            star.setFont(Font.font(14));
            star.setTextFill(i < starRating ? Color.GOLD : Color.LIGHTGRAY);
            stars.getChildren().add(star);
        }
        
        Label descLabel = new Label(accommodation.getDescription() != null ? 
                                   accommodation.getDescription() : "No description available");
        descLabel.setWrapText(true);
        
        // Amenities icons (would come from accommodation in a real app)
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
        
        Label priceLabel = new Label("$" + accommodation.getBasePrice());
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        Label perNightLabel = new Label("per night");
        perNightLabel.setFont(Font.font(12));
        
        Button viewButton = new Button("View Details");
        viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        viewButton.setOnAction(e -> {
            if (onAccommodationSelected != null) {
                onAccommodationSelected.accept(accommodation);
            }
        });
        
        Button bookButton = new Button("Book Now");
        bookButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        bookButton.setOnAction(e -> {
            if (onBookButtonClicked != null) {
                onBookButtonClicked.accept(accommodation, "direct");
            }
        });
        
        bookingBox.getChildren().addAll(priceLabel, perNightLabel, viewButton, bookButton);
        
        // Add all sections to the main container
        item.getChildren().addAll(imagePlaceholder, details, bookingBox);
        HBox.setHgrow(details, Priority.ALWAYS);
        
        return item;
    }
    
    /**
     * Get the minimum price from the slider
     * @return The minimum price as a string
     */
    public String getPriceRangeMin() {
        return String.valueOf(priceSlider.getMin());
    }
    
    /**
     * Get the maximum price from the slider
     * @return The maximum price as a string
     */
    public String getPriceRangeMax() {
        return String.valueOf(priceSlider.getMax());
    }
    
    // Setter methods for event handlers
    
    public void setOnBackButtonClicked(Runnable handler) {
        this.onBackButtonClicked = handler;
    }
    
    public void setOnFilterApplied(Consumer<Map<String, Boolean>> handler) {
        this.onFilterApplied = handler;
    }
    
    public void setOnAccommodationSelected(Consumer<Accommodation> handler) {
        this.onAccommodationSelected = handler;
    }
    
    public void setOnBookButtonClicked(BiConsumer<Accommodation, String> handler) {
        this.onBookButtonClicked = handler;
    }
    
    // Inner interface for event handler with two parameters
    @FunctionalInterface
    public interface BiConsumer<T, U> {
        void accept(T t, U u);
    }
}