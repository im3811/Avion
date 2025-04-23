package main.java.travelfinder.model;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int bookingId;
    private int methodId;
    private double amount;
    private String currency;
    private double exchangeRate;
    private String transactionId;
    private String paymentStatus; // Enum in database ('Pending', 'Completed', 'Failed', 'Refunded')
    private String paymentType; // Enum in database ('Full', 'Installment', 'Refund')
    private String paymentDetails; // JSON in database, stored as String here
    private Timestamp paymentDate;
    private Integer parentPaymentId; // Can be null
    
    // Navigation properties
    private Booking booking;
    private Payment parentPayment;
    
    // Constructors
    public Payment() {
    }
    
    public Payment(int paymentId, int bookingId, int methodId, double amount, String paymentStatus) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.methodId = methodId;
        this.amount = amount;
        this.currency = "USD";
        this.exchangeRate = 1.0;
        this.paymentStatus = paymentStatus;
        this.paymentType = "Full";
    }
    
    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }
    
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
    
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getMethodId() {
        return methodId;
    }
    
    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public double getExchangeRate() {
        return exchangeRate;
    }
    
    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getPaymentDetails() {
        return paymentDetails;
    }
    
    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public Integer getParentPaymentId() {
        return parentPaymentId;
    }
    
    public void setParentPaymentId(Integer parentPaymentId) {
        this.parentPaymentId = parentPaymentId;
    }
    
    // Navigation properties getters and setters
    public Booking getBooking() {
        return booking;
    }
    
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    
    public Payment getParentPayment() {
        return parentPayment;
    }
    
    public void setParentPayment(Payment parentPayment) {
        this.parentPayment = parentPayment;
    }
    
    // Helper methods
    public double getAmountInBaseCurrency() {
        return amount * exchangeRate;
    }
    
    public boolean isCompleted() {
        return "Completed".equals(paymentStatus);
    }
    
    public boolean isPending() {
        return "Pending".equals(paymentStatus);
    }
    
    public boolean isFailed() {
        return "Failed".equals(paymentStatus);
    }
    
    public boolean isRefunded() {
        return "Refunded".equals(paymentStatus);
    }
    
    public boolean isRefund() {
        return "Refund".equals(paymentType);
    }
    
    @Override
    public String toString() {
        return "#" + paymentId + " - " + amount + " " + currency + " (" + paymentStatus + ")";
    }
}