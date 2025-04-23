package main.java.travelfinder.model;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int bookingId;
    private int userId;
    private int accommodationId;
    private int rating; // 1-5
    private String comment;
    private String response; // JSON in database, stored as String here
    private Integer responseByUserId; // Can be null
    private Timestamp responseDate;
    private String moderationStatus; // Enum in database ('Pending', 'Approved', 'Rejected')
    private Timestamp reviewDate;
    private boolean isVerified;
    
    // Navigation properties
    private User user;
    private User responseBy;
    private Booking booking;
    private Accommodation accommodation;
    
    // Constructors
    public Review() {
    }
    
    public Review(int reviewId, int bookingId, int userId, int accommodationId, int rating) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.rating = rating;
        this.moderationStatus = "Pending";
        this.isVerified = true;
    }
    
    public Review(int reviewId, int bookingId, int userId, int accommodationId, 
                 int rating, String comment, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.moderationStatus = "Pending";
        this.isVerified = true;
    }
    
    // Getters and Setters
    public int getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getAccommodationId() {
        return accommodationId;
    }
    
    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public Integer getResponseByUserId() {
        return responseByUserId;
    }
    
    public void setResponseByUserId(Integer responseByUserId) {
        this.responseByUserId = responseByUserId;
    }
    
    public Timestamp getResponseDate() {
        return responseDate;
    }
    
    public void setResponseDate(Timestamp responseDate) {
        this.responseDate = responseDate;
    }
    
    public String getModerationStatus() {
        return moderationStatus;
    }
    
    public void setModerationStatus(String moderationStatus) {
        this.moderationStatus = moderationStatus;
    }
    
    public Timestamp getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    
    // Navigation properties getters and setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getResponseBy() {
        return responseBy;
    }
    
    public void setResponseBy(User responseBy) {
        this.responseBy = responseBy;
    }
    
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
    
    // Helper methods
    public boolean hasResponse() {
        return response != null && !response.isEmpty();
    }
    
    public boolean isPending() {
        return "Pending".equals(moderationStatus);
    }
    
    public boolean isApproved() {
        return "Approved".equals(moderationStatus);
    }
    
    public boolean isRejected() {
        return "Rejected".equals(moderationStatus);
    }
    
    @Override
    public String toString() {
        return rating + "/5 - " + (comment != null ? (comment.length() > 30 ? comment.substring(0, 30) + "..." : comment) : "No comment");
    }
}