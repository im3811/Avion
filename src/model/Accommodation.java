package main.java.travelfinder.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Accommodation {
    private int accommodationId;
    private String name;
    private int typeId;
    private String description;
    private int locationId;
    private String address;
    private BigDecimal starRating;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String cancellationPolicy;
    private BigDecimal basePrice;
    private String metadata; // JSON stored as String
    private boolean isVerified;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public Accommodation() {}
    
    public Accommodation(int accommodationId, String name, int typeId, int locationId, String address, BigDecimal basePrice) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.typeId = typeId;
        this.locationId = locationId;
        this.address = address;
        this.basePrice = basePrice;
        this.isActive = true;
    }
    
    // Getters and Setters
    public int getAccommodationId() {
        return accommodationId;
    }
    
    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getTypeId() {
        return typeId;
    }
    
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public BigDecimal getStarRating() {
        return starRating;
    }
    
    public void setStarRating(BigDecimal starRating) {
        this.starRating = starRating;
    }
    
    public LocalTime getCheckInTime() {
        return checkInTime;
    }
    
    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }
    
    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }
    
    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public String getCancellationPolicy() {
        return cancellationPolicy;
    }
    
    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
    
    public BigDecimal getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    public boolean isVerified() {
        return isVerified;
    }
    
    public void setVerified(boolean verified) {
        isVerified = verified;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Accommodation{" +
                "accommodationId=" + accommodationId +
                ", name='" + name + '\'' +
                ", basePrice=" + basePrice +
                ", starRating=" + starRating +
                '}';
    }
}