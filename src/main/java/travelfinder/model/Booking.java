package main.java.travelfinder.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private int userId;
    private int accommodationId;
    private Integer roomId; // Can be null
    private String referenceNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numGuests;
    private int statusId;
    private BigDecimal totalPrice;
    private String specialRequests;
    private String bookingExtras; // JSON stored as String
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer modifiedByUserId; // Can be null
    private String bookingHistory; // JSON stored as String
    
    // Constructors
    public Booking() {}
    
    public Booking(int userId, int accommodationId, Integer roomId, String referenceNumber, 
                  LocalDate checkInDate, LocalDate checkOutDate, int numGuests, 
                  int statusId, BigDecimal totalPrice) {
        this.userId = userId;
        this.accommodationId = accommodationId;
        this.roomId = roomId;
        this.referenceNumber = referenceNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numGuests = numGuests;
        this.statusId = statusId;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
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
    
    public Integer getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    
    public int getNumGuests() {
        return numGuests;
    }
    
    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }
    
    public int getStatusId() {
        return statusId;
    }
    
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public String getSpecialRequests() {
        return specialRequests;
    }
    
    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }
    
    public String getBookingExtras() {
        return bookingExtras;
    }
    
    public void setBookingExtras(String bookingExtras) {
        this.bookingExtras = bookingExtras;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
    
    public Integer getModifiedByUserId() {
        return modifiedByUserId;
    }
    
    public void setModifiedByUserId(Integer modifiedByUserId) {
        this.modifiedByUserId = modifiedByUserId;
    }
    
    public String getBookingHistory() {
        return bookingHistory;
    }
    
    public void setBookingHistory(String bookingHistory) {
        this.bookingHistory = bookingHistory;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numGuests=" + numGuests +
                ", totalPrice=" + totalPrice +
                '}';
    }
}