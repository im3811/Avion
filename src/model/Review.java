package main.java.travelfinder.model;

import java.time.LocalDateTime;

public class Review {
    private int reviewId;
    private int bookingId;
    private int userId;
    private int accommodationId;
    private int rating;
    private String comment;
    private String response;  // JSON stored as String
    private Integer responseByUserId;  // Can be null
    private LocalDateTime responseDate;  // Can be null
    private ModerationStatus moderationStatus;
    private LocalDateTime reviewDate;
    private boolean isVerified;
    
    public enum ModerationStatus {
        PENDING, APPROVED, REJECTED
    }
    
    // Constructors
    public Review() {}
    
    public Review(int reviewId, int bookingId, int userId, int accommodationId, int rating) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.rating = rating;
        this.moderationStatus = ModerationStatus.PENDING;
        this.reviewDate = LocalDateTime.now();
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
    
    public LocalDateTime getResponseDate() {
        return responseDate;
    }
    
    public void setResponseDate(LocalDateTime responseDate) {
        this.responseDate = responseDate;
    }
    
    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }
    
    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }
    
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    
    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", rating=" + rating +
                ", moderationStatus=" + moderationStatus +
                ", reviewDate=" + reviewDate +
                '}';
    }
    
    // Helper methods
    
    // Format the review date for display
    public String getFormattedReviewDate() {
        // Format: "Month Year" (e.g., "January 2025")
        if (reviewDate == null) {
            return "Date unknown";
        }
        
        String month = reviewDate.getMonth().toString();
        month = month.charAt(0) + month.substring(1).toLowerCase();
        int year = reviewDate.getYear();
        
        return month + " " + year;
    }
    
    // Check if review has a response
    public boolean hasResponse() {
        return response != null && !response.isEmpty() && responseByUserId != null;
    }
    
    // Get star rating as a string of stars
    public String getStarRating() {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("★"); // Filled star
            } else {
                stars.append("☆"); // Empty star
            }
        }
        return stars.toString();
    }
}