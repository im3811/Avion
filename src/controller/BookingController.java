package main.java.travelfinder.controller;

import main.java.travelfinder.dao.BookingDAO;
import main.java.travelfinder.dao.UserDAO;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.Room;
import main.java.travelfinder.model.User;
import main.java.travelfinder.view.BookingView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Controller for booking process
 */
public class BookingController {
    private BookingView view;
    private BookingDAO bookingDAO;
    private UserDAO userDAO;
    
    private Accommodation selectedAccommodation;
    private Room selectedRoom;
    private String currentUser;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numGuests;
    
    private Runnable onBackToDetails;
    private Consumer<Booking> onBookingComplete;
    
    public BookingController(BookingView view, BookingDAO bookingDAO, UserDAO userDAO) {
        this.view = view;
        this.bookingDAO = bookingDAO;
        this.userDAO = userDAO;
        
        // Setup view event handlers
        this.view.setOnBackButtonClicked(() -> {
            if (onBackToDetails != null) {
                onBackToDetails.run();
            }
        });
        
        this.view.setOnCompleteBookingClicked(bookingDetails -> {
            processBooking(bookingDetails);
        });
    }
    
    public void startBookingProcess(Accommodation accommodation, Room room, String username, 
                                   LocalDate checkIn, LocalDate checkOut, int guests) {
        this.selectedAccommodation = accommodation;
        this.selectedRoom = room;
        this.currentUser = username;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.numGuests = guests;
        
        // Calculate booking summary
        String accommodationName = accommodation.getName();
        if (room != null) {
            accommodationName += " - " + room.getRoomName();
        }
        
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal basePrice = accommodation.getBasePrice();
        if (room != null) {
            basePrice = basePrice.multiply(room.getPriceModifier());
        }
        
        BigDecimal totalBeforeTax = basePrice.multiply(BigDecimal.valueOf(nights));
        BigDecimal tax = totalBeforeTax.multiply(BigDecimal.valueOf(0.12)); // 12% tax
        BigDecimal totalPrice = totalBeforeTax.add(tax);
        
        // Get user information
        User user = userDAO.findByUsername(username);
        
        // Prepare view with data
        view.setupBookingForm(accommodationName, checkIn, checkOut, guests, 
                             nights, basePrice, totalBeforeTax, tax, totalPrice);
        
        if (user != null) {
            view.prefillUserInfo(user.getFirstName(), user.getLastName(), 
                                user.getEmail(), user.getPhoneNumber());
        }
        
        view.show();
    }
    
    public void setOnBackToDetails(Runnable handler) {
        this.onBackToDetails = handler;
    }
    
    public void setOnBookingComplete(Consumer<Booking> handler) {
        this.onBookingComplete = handler;
    }
    
    private void processBooking(BookingView.BookingDetails details) {
        // Validate inputs
        if (!details.isTermsAccepted()) {
            view.showAlert("Please accept the terms and conditions");
            return;
        }
        
        // In a real app, validate payment information
        
        // Create booking
        try {
            User user = userDAO.findByUsername(currentUser);
            if (user == null) {
                view.showAlert("User not found");
                return;
            }
            
            Booking booking = new Booking(
                user.getUserId(), 
                selectedAccommodation.getAccommodationId(),
                selectedRoom != null ? selectedRoom.getRoomId() : null,
                generateReferenceNumber(),
                checkInDate,
                checkOutDate,
                numGuests,
                2, // Status ID 2 = Confirmed
                details.getTotalPrice()
            );
            
            booking.setSpecialRequests(details.getSpecialRequests());
            
            // Save to database
            boolean success = bookingDAO.save(booking);
            
            if (success) {
                view.showBookingConfirmation(booking.getReferenceNumber());
                
                if (onBookingComplete != null) {
                    onBookingComplete.accept(booking);
                }
            } else {
                view.showAlert("Failed to complete booking. Please try again.");
            }
        } catch (Exception e) {
            view.showAlert("Error processing booking: " + e.getMessage());
        }
    }
    
    private String generateReferenceNumber() {
        // Simple reference number generation
        return "BOOK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}