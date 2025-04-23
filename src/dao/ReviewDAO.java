package main.java.travelfinder.dao;

import main.java.travelfinder.model.Review;
import main.java.travelfinder.model.Review.ModerationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for Review entity
 */
public interface ReviewDAO {
    /**
     * Find a review by its ID
     * @param reviewId The ID of the review
     * @return The review object if found, null otherwise
     */
    Review findById(int reviewId);
    
    /**
     * Find reviews by booking ID
     * @param bookingId The ID of the booking
     * @return The review associated with the booking, if any
     */
    Review findByBookingId(int bookingId);
    
    /**
     * Find reviews by user ID
     * @param userId The ID of the user
     * @return A list of reviews written by the user
     */
    List<Review> findByUserId(int userId);
    
    /**
     * Find reviews for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return A list of reviews for the accommodation
     */
    List<Review> findByAccommodationId(int accommodationId);
    
    /**
     * Find reviews by moderation status
     * @param status The moderation status to filter by
     * @return A list of reviews with the specified status
     */
    List<Review> findByModerationStatus(ModerationStatus status);
    
    /**
     * Find reviews by rating
     * @param rating The rating to filter by (1-5)
     * @return A list of reviews with the specified rating
     */
    List<Review> findByRating(int rating);
    
    /**
     * Find reviews by minimum rating
     * @param minRating The minimum rating to filter by (1-5)
     * @return A list of reviews with at least the specified rating
     */
    List<Review> findByMinRating(int minRating);
    
    /**
     * Find reviews by maximum rating
     * @param maxRating The maximum rating to filter by (1-5)
     * @return A list of reviews with at most the specified rating
     */
    List<Review> findByMaxRating(int maxRating);
    
    /**
     * Find reviews created within a date range
     * @param startDate The start date
     * @param endDate The end date
     * @return A list of reviews created within the date range
     */
    List<Review> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get the most recent reviews
     * @param limit The maximum number of reviews to return
     * @return A list of recent reviews
     */
    List<Review> findRecent(int limit);
    
    /**
     * Get reviews that need moderation
     * @return A list of unmoderated reviews
     */
    List<Review> findUnmoderated();
    
    /**
     * Get verified reviews for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return A list of verified reviews
     */
    List<Review> findVerifiedByAccommodationId(int accommodationId);
    
    /**
     * Save a new review to the database
     * @param review The review object to save
     * @return true if successful, false otherwise
     */
    boolean save(Review review);
    
    /**
     * Update an existing review in the database
     * @param review The review object to update
     * @return true if successful, false otherwise
     */
    boolean update(Review review);
    
    /**
     * Delete a review from the database
     * @param reviewId The ID of the review to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int reviewId);
    
    /**
     * Update the moderation status of a review
     * @param reviewId The ID of the review
     * @param status The new moderation status
     * @return true if successful, false otherwise
     */
    boolean updateModerationStatus(int reviewId, ModerationStatus status);
    
    /**
     * Add a response to a review
     * @param reviewId The ID of the review
     * @param response The response text
     * @param userId The ID of the user adding the response
     * @return true if successful, false otherwise
     */
    boolean addResponse(int reviewId, String response, int userId);
    
    /**
     * Calculate the average rating for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return The average rating
     */
    double calculateAverageRating(int accommodationId);
    
    /**
     * Get rating distribution for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return A map of ratings to counts (e.g., {5: 10, 4: 5, 3: 2, 2: 1, 1: 0})
     */
    Map<Integer, Integer> getRatingDistribution(int accommodationId);
    
    /**
     * Count the total number of reviews
     * @return The total count of reviews
     */
    int countAll();
    
    /**
     * Count reviews for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return The count of reviews for the accommodation
     */
    int countByAccommodationId(int accommodationId);
    
    /**
     * Check if a user has already reviewed a booking
     * @param userId The ID of the user
     * @param bookingId The ID of the booking
     * @return true if the user has already reviewed the booking, false otherwise
     */
    boolean hasUserReviewedBooking(int userId, int bookingId);
}