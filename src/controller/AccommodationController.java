package main.java.travelfinder.controller;

import main.java.travelfinder.dao.AccommodationDAO;
import main.java.travelfinder.dao.ReviewDAO;
import main.java.travelfinder.dao.RoomDAO;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Review;
import main.java.travelfinder.model.Room;
import main.java.travelfinder.view.AccommodationView;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Controller for accommodation details
 */
public class AccommodationController {
    private AccommodationView view;
    private AccommodationDAO accommodationDAO;
    private RoomDAO roomDAO;
    private ReviewDAO reviewDAO;
    
    private Accommodation currentAccommodation;
    private String currentUser;
    
    private Runnable onBackToResults;
    private Runnable onBackToSearch;
    private BiConsumer<Room, Accommodation> onRoomSelected;
    
    public AccommodationController(AccommodationView view, AccommodationDAO accommodationDAO, 
                                  RoomDAO roomDAO, ReviewDAO reviewDAO) {
        this.view = view;
        this.accommodationDAO = accommodationDAO;
        this.roomDAO = roomDAO;
        this.reviewDAO = reviewDAO;
        
        // Setup view event handlers
        this.view.setOnBackToResultsClicked(() -> {
            if (onBackToResults != null) {
                onBackToResults.run();
            }
        });
        
        this.view.setOnBackToSearchClicked(() -> {
            if (onBackToSearch != null) {
                onBackToSearch.run();
            }
        });
        
        this.view.setOnBookRoomClicked((room) -> {
            handleBookRoom(room);
        });
        
        this.view.setOnModifySearchClicked((location, checkInDate, checkOutDate, guests) -> {
            // This would typically go back to search with updated criteria
            if (onBackToSearch != null) {
                onBackToSearch.run();
            }
        });
    }
    
    public void showAccommodationDetails(Accommodation accommodation, String currentUser) {
        this.currentAccommodation = accommodation;
        this.currentUser = currentUser;
        
        // Get all needed data
        List<Room> rooms = loadRoomsForAccommodation();
        List<Review> reviews = loadReviewsForAccommodation();
        
        // Update the view
        view.displayAccommodationDetails(accommodation);
        view.displayRooms(rooms);
        view.displayReviews(reviews);
        view.updateGuestAccessibility("Guest".equals(currentUser));
        
        view.show();
    }
    
    public void setOnBackToResults(Runnable handler) {
        this.onBackToResults = handler;
    }
    
    public void setOnBackToSearch(Runnable handler) {
        this.onBackToSearch = handler;
    }
    
    public void setOnRoomSelected(BiConsumer<Room, Accommodation> handler) {
        this.onRoomSelected = handler;
    }
    
    private List<Room> loadRoomsForAccommodation() {
        if (roomDAO != null && currentAccommodation != null) {
            return roomDAO.findByAccommodationId(currentAccommodation.getAccommodationId());
        }
        return null;
    }
    
    private List<Review> loadReviewsForAccommodation() {
        if (reviewDAO != null && currentAccommodation != null) {
            return reviewDAO.findByAccommodationId(currentAccommodation.getAccommodationId());
        }
        return null;
    }
    
    private void handleBookRoom(Room room) {
        if ("Guest".equals(currentUser)) {
            view.showLoginRequiredDialog();
            return;
        }
        
        if (onRoomSelected != null) {
            onRoomSelected.accept(room, currentAccommodation);
        }
    }
}