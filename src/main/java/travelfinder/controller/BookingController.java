package main.java.travelfinder.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import main.java.travelfinder.model.Accommodation;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.model.Room;
import main.java.travelfinder.utils.AlertUtil;
import main.java.travelfinder.view.BookingView;

public class BookingController {
    
    private BookingView bookingView;
    private String currentUser;
    
    public BookingController() {
    }
    
    public void setBookingView(BookingView bookingView) {
        this.bookingView = bookingView;
    }
    
    public void setCurrentUser(String username) {
        this.currentUser = username;
    }
    
    public void startBookingProcess(int accommodationId, int roomId) {
        if ("Guest".equals(currentUser)) {
            AlertUtil.showInfo("Login Required", "Please login to book accommodations");
            return;
        }
        
        if (bookingView != null) {
            bookingView.setupBookingForm(accommodationId, roomId);
            
            // Use method reference instead of lambda to avoid referring to BookingFormSubmitHandler
            bookingView.setOnConfirmBooking((checkIn, checkOut, guests, special) -> 
                createBooking(accommodationId, roomId, checkIn, checkOut, guests, special));
            
            bookingView.show();
        }
    }
    
    public void showUserBookings(String username) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database queries
            List<Booking> bookings = getMockBookings(username);
            
            if (bookings.isEmpty()) {
                AlertUtil.showInfo("My Bookings", "You don't have any bookings");
                return;
            }
            
            if (bookingView != null) {
                bookingView.displayUserBookings(bookings);
                bookingView.setOnCancelBooking(this::cancelBooking);
                bookingView.show();
            }
        } catch (Exception e) {
            AlertUtil.showError("Error", "Could not load bookings: " + e.getMessage());
        }
    }
    
    private void createBooking(int accommodationId, int roomId, LocalDate checkIn, 
                              LocalDate checkOut, int guests, String specialRequests) {
        try {
            if (checkIn.isAfter(checkOut) || checkIn.equals(checkOut)) {
                AlertUtil.showError("Invalid Dates", "Check-out date must be after check-in date");
                return;
            }
            
            if (guests <= 0) {
                AlertUtil.showError("Invalid Guests", "Number of guests must be at least 1");
                return;
            }
            
            // Placeholder for DAO implementation to check availability
            boolean isAvailable = true; // Assume available for now
            
            if (!isAvailable) {
                AlertUtil.showError("Room Unavailable", "This room is not available for the selected dates");
                return;
            }
            
            // Create mock booking - this would be replaced with actual database operations
            int userId = 1; // Assume user ID 1 for now
            String reference = generateBookingReference();
            double totalPrice = calculateTotalPrice(accommodationId, roomId, checkIn, checkOut, guests);
            
            Booking newBooking = new Booking(
                new Random().nextInt(1000), // Mock booking ID
                userId,
                accommodationId,
                roomId,
                reference,
                checkIn,
                checkOut,
                guests,
                2, // Status ID 2 = Confirmed
                totalPrice
            );
            newBooking.setSpecialRequests(specialRequests);
            newBooking.setStatusName("Confirmed");
            
            // In a real implementation, we would save this booking to the database
            
            AlertUtil.showInfo("Booking Confirmed", "Your booking has been confirmed. Reference: " + reference);
            
            if (bookingView != null) {
                bookingView.close();
            }
            
            // Process payment
            processPayment(newBooking.getBookingId(), newBooking.getTotalPrice());
            
        } catch (Exception e) {
            AlertUtil.showError("Booking Error", "Could not complete booking: " + e.getMessage());
        }
    }
    
    private double calculateTotalPrice(int accommodationId, int roomId, LocalDate checkIn, LocalDate checkOut, int guests) {
        // In a real implementation, this would calculate the actual price based on:
        // - Base accommodation price
        // - Room price modifier
        // - Length of stay
        // - Number of guests
        // - Any applicable discounts
        
        // Mock implementation for now
        double basePrice = 149.99;
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        return basePrice * nights * (1 + (guests - 1) * 0.2); // 20% increase per additional guest
    }
    
    private String generateBookingReference() {
        // Generate a random booking reference like "BK12345"
        return "BK" + (10000 + new Random().nextInt(90000));
    }
    
    private void cancelBooking(int bookingId) {
        try {
            // Placeholder for DAO implementation
            // This would be replaced with actual database operations
            boolean success = true; // Assume success for now
            
            if (success) {
                AlertUtil.showInfo("Booking Cancelled", "Your booking has been cancelled");
                showUserBookings(currentUser);
            } else {
                AlertUtil.showError("Cancellation Failed", "Could not cancel your booking");
            }
        } catch (Exception e) {
            AlertUtil.showError("Cancellation Error", "Could not cancel booking: " + e.getMessage());
        }
    }
    
    private void processPayment(int bookingId, double amount) {
        // Payment processing logic would be implemented here
        // This is just a placeholder
        System.out.println("Processing payment of $" + amount + " for booking #" + bookingId);
    }
    
    // Mock method to provide sample data - will be replaced with DAO
    private List<Booking> getMockBookings(String username) {
        List<Booking> bookings = new ArrayList<>();
        
        // Skip if guest user
        if ("Guest".equals(username)) {
            return bookings;
        }
        
        // Create some mock bookings
        int userId = 1; // Assume this is the ID for the current user
        
        // Booking 1: Current booking
        Booking booking1 = new Booking(
            1, 
            userId, 
            1, // Miami Beach Resort
            101, 
            "BK12345", 
            LocalDate.now().plusDays(30), 
            LocalDate.now().plusDays(33), 
            2, 
            2, // Status ID 2 = Confirmed
            599.98 // $199.99 * 3 nights
        );
        booking1.setStatusName("Confirmed");
        booking1.setCreatedAt(new Timestamp(System.currentTimeMillis() - 86400000)); // 1 day ago
        
        // Create accommodation for booking1
        Accommodation accommodation1 = new Accommodation(1, "Miami Beach Resort", 5, "Beachfront resort", 3, "123 Beach Drive, Miami, FL 33139", 4.5, 299.99);
        booking1.setAccommodation(accommodation1);
        
        // Create room for booking1
        Room room1 = new Room(101, 1, "Deluxe Ocean View", 2, 1.0);
        booking1.setRoom(room1);
        
        bookings.add(booking1);
        
        // Booking 2: Past booking
        Booking booking2 = new Booking(
            2, 
            userId, 
            2, // Hollywood Hotel
            201, 
            "BK23456", 
            LocalDate.now().minusDays(60), 
            LocalDate.now().minusDays(55), 
            3, 
            3, // Status ID 3 = Completed
            1249.95 // $249.99 * 5 nights
        );
        booking2.setStatusName("Completed");
        booking2.setCreatedAt(new Timestamp(System.currentTimeMillis() - 7776000000L)); // 90 days ago
        
        // Create accommodation for booking2
        Accommodation accommodation2 = new Accommodation(2, "Hollywood Hotel", 1, "Central hotel", 5, "789 Hollywood Blvd, Los Angeles, CA 90028", 4.0, 249.99);
        booking2.setAccommodation(accommodation2);
        
        // Create room for booking2
        Room room2 = new Room(201, 2, "Family Suite", 4, 1.2);
        booking2.setRoom(room2);
        
        bookings.add(booking2);
        
        // Booking 3: Cancelled booking
        Booking booking3 = new Booking(
            3, 
            userId, 
            1, // Miami Beach Resort
            102, 
            "BK34567", 
            LocalDate.now().minusDays(10), 
            LocalDate.now().minusDays(7), 
            2, 
            4, // Status ID 4 = Cancelled
            899.97 // $299.99 * 3 nights
        );
        booking3.setStatusName("Cancelled");
        booking3.setCreatedAt(new Timestamp(System.currentTimeMillis() - 1728000000L)); // 20 days ago
        booking3.setAccommodation(accommodation1); // Same accommodation as booking1
        
        // Create room for booking3
        Room room3 = new Room(102, 1, "Luxury Suite", 2, 1.5);
        booking3.setRoom(room3);
        
        bookings.add(booking3);
        
        return bookings;
    }
}