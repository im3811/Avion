package main.java.travelfinder.controller;

import java.util.ArrayList;
import java.util.List;

import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Review;
import main.java.travelfinder.model.Room;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.view.AccommodationDetailsView;

public class AccommodationController {
    
    private AccommodationDetailsView detailsView;
    private BookingController bookingController;
    
    public AccommodationController() {
    }
    
    public void setDetailsView(AccommodationDetailsView detailsView) {
        this.detailsView = detailsView;
    }
    
    public void setBookingController(BookingController bookingController) {
        this.bookingController = bookingController;
    }
    
    public void showAccommodationDetails(int accommodationId) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database queries
            
            // Mock accommodation data for now
            Accommodation accommodation = getMockAccommodation(accommodationId);
            
            if (accommodation == null) {
                AlertUtil.showError("Error", "Accommodation not found");
                return;
            }
            
            List<Room> rooms = getMockRooms(accommodationId);
            List<Review> reviews = getMockReviews(accommodationId);
            
            if (detailsView != null) {
                detailsView.displayAccommodation(accommodation, rooms, reviews);
                
                detailsView.setOnBookRoom(roomId -> {
                    if (bookingController != null) {
                        bookingController.startBookingProcess(accommodationId, roomId);
                    }
                });
                
                detailsView.show();
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not load accommodation details: " + e.getMessage());
        }
    }
    
    public List<Accommodation> getFeaturedAccommodations() {
        // Placeholder for DAO implementation
        // This would be replaced with actual database queries
        return new ArrayList<>();
    }
    
    // Mock methods to provide sample data - will be replaced with DAO
    private Accommodation getMockAccommodation(int id) {
        // This is just placeholder data
        return null; // Implementation will be done later when model classes are defined
    }
    
    private List<Room> getMockRooms(int accommodationId) {
        // This is just placeholder data
        return new ArrayList<>(); // Implementation will be done later when model classes are defined
    }
    
    private List<Review> getMockReviews(int accommodationId) {
        // This is just placeholder data
        return new ArrayList<>(); // Implementation will be done later when model classes are defined
    }
}