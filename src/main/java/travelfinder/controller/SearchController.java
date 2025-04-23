package main.java.travelfinder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.view.SearchResultsView;

public class SearchController {
    
    private SearchResultsView searchResultsView;
    private AccommodationController accommodationController;
    
    public SearchController() {
    }
    
    public void setSearchResultsView(SearchResultsView searchResultsView) {
        this.searchResultsView = searchResultsView;
    }
    
    public void setAccommodationController(AccommodationController accommodationController) {
        this.accommodationController = accommodationController;
    }
    
    public void performSearch(Map<String, Object> criteria) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database search using AccommodationDAO
            List<Accommodation> results = getMockSearchResults();
            
            if (results.isEmpty()) {
                AlertUtil.showInfo("Search Results", "No accommodations found matching your criteria");
                return;
            }
            
            if (searchResultsView != null) {
                searchResultsView.displayResults(results);
                searchResultsView.setOnSelectAccommodation(id -> {
                    if (accommodationController != null) {
                        accommodationController.showAccommodationDetails(id);
                    }
                });
                
                searchResultsView.show();
            }
        } catch (Exception e) {
            AlertUtil.showError("Search Error", "Could not complete search: " + e.getMessage());
        }
    }
    
    // Mock method to provide sample data - will be replaced with DAO
    private List<Accommodation> getMockSearchResults() {
        // This is just placeholder data
        List<Accommodation> mockResults = new ArrayList<>();
        // Implementation will be done later when model classes are defined
        return mockResults;
    }
}