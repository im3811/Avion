package main.java.travelfinder.controller;

import main.java.travelfinder.dao.AccommodationDAO;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.view.SearchView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Controller for search results and filtering
 */
public class SearchController {
    private SearchView view;
    private AccommodationDAO accommodationDAO;
    
    private String currentLocation;
    private LocalDate currentCheckInDate;
    private LocalDate currentCheckOutDate;
    private String currentGuests;
    
    private List<Accommodation> searchResults;
    private Map<String, Boolean> filters = new HashMap<>();
    
    private Consumer<Accommodation> onAccommodationSelected;
    private Runnable onBackToSearch;
    
    public SearchController(SearchView view, AccommodationDAO accommodationDAO) {
        this.view = view;
        this.accommodationDAO = accommodationDAO;
        
        // Initialize filters
        initializeFilters();
        
        // Setup view event handlers
        this.view.setOnBackButtonClicked(() -> {
            if (onBackToSearch != null) {
                onBackToSearch.run();
            }
        });
        
        this.view.setOnFilterApplied(filters -> {
            this.filters = filters;
            applyFilters();
        });
        
        this.view.setOnAccommodationSelected(accommodation -> {
            if (onAccommodationSelected != null) {
                onAccommodationSelected.accept(accommodation);
            }
        });
    }
    
    public void showSearchResults(String location, LocalDate checkInDate, LocalDate checkOutDate, String guests, List<Accommodation> results) {
        this.currentLocation = location;
        this.currentCheckInDate = checkInDate;
        this.currentCheckOutDate = checkOutDate;
        this.currentGuests = guests;
        this.searchResults = results;
        
        view.displaySearchCriteria(location, checkInDate, checkOutDate, guests);
        view.displaySearchResults(results);
        view.show();
    }
    
    public void setOnAccommodationSelected(Consumer<Accommodation> handler) {
        this.onAccommodationSelected = handler;
    }
    
    public void setOnBackToSearch(Runnable handler) {
        this.onBackToSearch = handler;
    }
    
    private void initializeFilters() {
        // Initialize default filter values
        filters.put("priceMin", true);
        filters.put("priceMax", true);
        
        // Star ratings (1-5 stars)
        for (int i = 1; i <= 5; i++) {
            filters.put("star" + i, true);
        }
        
        // Property types
        String[] propertyTypes = {"Hotel", "Apartment", "Vacation Home", "Hostel", "Resort"};
        for (String type : propertyTypes) {
            filters.put("type_" + type, true);
        }
        
        // Amenities
        String[] amenities = {"WiFi", "Swimming Pool", "Free Parking", "Air Conditioning", "Breakfast"};
        for (String amenity : amenities) {
            filters.put("amenity_" + amenity, false);
        }
    }
    
    private void applyFilters() {
        if (searchResults == null || searchResults.isEmpty()) {
            return;
        }
        
        // Filter by price range
        double minPrice = 0;
        double maxPrice = 1000; // Default max price
        
        try {
            minPrice = Double.parseDouble(view.getPriceRangeMin());
            maxPrice = Double.parseDouble(view.getPriceRangeMax());
        } catch (NumberFormatException e) {
            // Use defaults
        }
        
        final double finalMinPrice = minPrice;
        final double finalMaxPrice = maxPrice;
        
        // Apply all filters
        List<Accommodation> filteredResults = searchResults.stream()
            .filter(acc -> {
                // Price filter
                BigDecimal price = acc.getBasePrice();
                double priceValue = price.doubleValue();
                if (priceValue < finalMinPrice || priceValue > finalMaxPrice) {
                    return false;
                }
                
                // Star rating filter
                int starRating = acc.getStarRating().intValue();
                if (!filters.getOrDefault("star" + starRating, true)) {
                    return false;
                }
                
                // Property type filter
                // In a real app, this would check against the accommodation type
                // For now, assume we have a getTypeId() method and map to the filter key
                String typeKey = "type_" + getAccommodationTypeName(acc.getTypeId());
                if (!filters.getOrDefault(typeKey, true)) {
                    return false;
                }
                
                // Additional filters as needed
                
                return true;
            })
            .collect(Collectors.toList());
        
        view.displaySearchResults(filteredResults);
    }
    
    // Helper method to map type ID to name
    private String getAccommodationTypeName(int typeId) {
        switch (typeId) {
            case 1: return "Hotel";
            case 2: return "Apartment";
            case 3: return "Vacation Home";
            case 4: return "Hostel";
            case 5: return "Resort";
            default: return "Other";
        }
    }
}