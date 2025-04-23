package main.java.travelfinder.dao;

import main.java.travelfinder.model.Booking;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for Booking entity
 */
public interface BookingDAO {
    /**
     * Find a booking by its ID
     * @param bookingId The ID of the booking
     * @return The booking object if found, null otherwise
     */
    Booking findById(int bookingId);
    
    /**
     * Find a booking by its reference number
     * @param referenceNumber The booking reference number
     * @return The booking object if found, null otherwise
     */
    Booking findByReferenceNumber(String referenceNumber);
    
    /**
     * Find all bookings for a specific user
     * @param userId The ID of the user
     * @return A list of bookings for the user
     */
    List<Booking> findByUserId(int userId);
    
    /**
     * Find upcoming bookings for a user (check-in date in the future)
     * @param userId The ID of the user
     * @return A list of upcoming bookings
     */
    List<Booking> findUpcomingByUserId(int userId);
    
    /**
     * Find past bookings for a user (check-out date in the past)
     * @param userId The ID of the user
     * @return A list of past bookings
     */
    List<Booking> findPastByUserId(int userId);
    
    /**
     * Find cancelled bookings for a user
     * @param userId The ID of the user
     * @return A list of cancelled bookings
     */
    List<Booking> findCancelledByUserId(int userId);
    
    /**
     * Find bookings for a specific accommodation
     * @param accommodationId The ID of the accommodation
     * @return A list of bookings for the accommodation
     */
    List<Booking> findByAccommodationId(int accommodationId);
    
    /**
     * Find bookings for a specific room
     * @param roomId The ID of the room
     * @return A list of bookings for the room
     */
    List<Booking> findByRoomId(int roomId);
    
    /**
     * Find bookings by status
     * @param statusId The ID of the booking status
     * @return A list of bookings with the specified status
     */
    List<Booking> findByStatus(int statusId);
    
    /**
     * Find bookings for a specific date range
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of bookings within the date range
     */
    List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate);
    
    /**
     * Check if a room is available for a specific date range
     * @param roomId The ID of the room
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return true if the room is available, false otherwise
     */
    boolean isRoomAvailable(int roomId, LocalDate checkIn, LocalDate checkOut);
    
    /**
     * Save a new booking to the database
     * @param booking The booking object to save
     * @return true if successful, false otherwise
     */
    boolean save(Booking booking);
    
    /**
     * Update an existing booking in the database
     * @param booking The booking object to update
     * @return true if successful, false otherwise
     */
    boolean update(Booking booking);
    
    /**
     * Cancel a booking (update status to cancelled)
     * @param bookingId The ID of the booking to cancel
     * @param cancelledByUserId The ID of the user who cancelled the booking
     * @return true if successful, false otherwise
     */
    boolean cancelBooking(int bookingId, int cancelledByUserId);
    
    /**
     * Get the most recent bookings
     * @param limit The maximum number of bookings to return
     * @return A list of recent bookings
     */
    List<Booking> findRecent(int limit);
    
    /**
     * Count the total number of bookings
     * @return The total count of bookings
     */
    int countAll();
    
    /**
     * Calculate the total revenue from all bookings
     * @return The total revenue
     */
    double calculateTotalRevenue();
    
    /**
     * Calculate revenue for bookings created since a specific date
     * @param since The date to calculate from
     * @return The revenue since the given date
     */
    double calculateRevenueSince(LocalDate since);
    
    /**
     * Calculate revenue for bookings created between two dates
     * @param startDate The start date
     * @param endDate The end date
     * @return The revenue for the date range
     */
    double calculateRevenueBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Count bookings created since a specific date
     * @param since The date to count from
     * @return The number of bookings created since the given date
     */
    int countCreatedSince(LocalDate since);
    
    /**
     * Find bookings created between two dates
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of bookings created in the date range
     */
    List<Booking> findCreatedBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Get booking statistics grouped by a time period
     * @param groupBy The time period to group by (day, week, month, year)
     * @param startDate The start date
     * @param endDate The end date
     * @return A map of dates to booking counts
     */
    Map<String, Integer> getBookingStatistics(String groupBy, LocalDate startDate, LocalDate endDate);
}