package main.java.travelfinder.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String statusName; // For convenience
    private double totalPrice;
    private String specialRequests;
    private String bookingExtras; // JSON in database, stored as String here
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Integer modifiedByUserId; // Can be null
    private String bookingHistory; // JSON in database, stored as String here
    
    // Navigation properties
    private User user;
    private Accommodation accommodation;
    private Room room;
    private List<Payment> payments = new ArrayList<>();
    private Review review;
    
    // Constructors
    public Booking() {
    }
    
    public Booking(int bookingId, int userId, int accommodationId, Integer roomId,
                  String referenceNumber, LocalDate checkInDate, LocalDate checkOutDate,
                  int numGuests, int statusId, double totalPrice) {
        this.bookingId = bookingId;
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
    
    public String getStatusName() {
        return statusName;
    }
    
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
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
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }
    
    public void setModifiedAt(Timestamp modifiedAt) {
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
    
    // Navigation properties getters and setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
    
    public List<Payment> getPayments() {
        return payments;
    }
    
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public Review getReview() {
        return review;
    }
    
    public void setReview(Review review) {
        this.review = review;
    }
    
    // Helper methods
    public void addPayment(Payment payment) {
        payments.add(payment);
    }
    
    public int getNights() {
        return (int) (checkOutDate.toEpochDay() - checkInDate.toEpochDay());
    }
    
    public boolean isCancellable() {
        return statusId == 1 || statusId == 2; // Assuming 1=Pending, 2=Confirmed
    }
    
    public boolean isCompleted() {
        return statusId == 3; // Assuming 3=Completed
    }
    
    public boolean isCancelled() {
        return statusId == 4; // Assuming 4=Cancelled
    }
    
    public boolean isNoShow() {
        return statusId == 5; // Assuming 5=No-Show
    }
    
    public double getTotalPaid() {
        double total = 0;
        for (Payment payment : payments) {
            if (payment.getPaymentStatus().equals("Completed")) {
                total += payment.getAmount();
            }
        }
        return total;
    }
    
    public double getRemaining() {
        return totalPrice - getTotalPaid();
    }
    
    @Override
    public String toString() {
        return "Booking #" + referenceNumber + " (" + statusName + ")";
    }
}