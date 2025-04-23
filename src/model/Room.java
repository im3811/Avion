package main.java.travelfinder.model;

import java.math.BigDecimal;

public class Room {
    private int roomId;
    private int accommodationId;
    private String roomName;
    private String description;
    private int capacity;
    private BigDecimal priceModifier;
    private Integer roomSize;  // Can be null
    private String bedType;    // Can be null
    private String features;   // JSON stored as String
    private boolean isAvailable;
    
    // Constructors
    public Room() {}
    
    public Room(int roomId, int accommodationId, String roomName, int capacity, BigDecimal priceModifier) {
        this.roomId = roomId;
        this.accommodationId = accommodationId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.priceModifier = priceModifier;
        this.isAvailable = true;
    }
    
    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public int getAccommodationId() {
        return accommodationId;
    }
    
    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    public BigDecimal getPriceModifier() {
        return priceModifier;
    }
    
    public void setPriceModifier(BigDecimal priceModifier) {
        this.priceModifier = priceModifier;
    }
    
    public Integer getRoomSize() {
        return roomSize;
    }
    
    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
    }
    
    public String getBedType() {
        return bedType;
    }
    
    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
    
    public String getFeatures() {
        return features;
    }
    
    public void setFeatures(String features) {
        this.features = features;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", capacity=" + capacity +
                ", priceModifier=" + priceModifier +
                ", isAvailable=" + isAvailable +
                '}';
    }
    
    // Helper methods
    public String getCapacityDescription() {
        if (capacity == 1) {
            return "Sleeps 1 person";
        } else {
            return "Sleeps " + capacity + " people";
        }
    }
    
    public String getRoomSizeFormatted() {
        if (roomSize == null) {
            return "Size not specified";
        }
        return roomSize + " sq m";
    }
    
    // Calculate the final price based on base price and modifier
    public BigDecimal calculatePrice(BigDecimal basePrice) {
        if (basePrice == null || priceModifier == null) {
            return BigDecimal.ZERO;
        }
        return basePrice.multiply(priceModifier);
    }
}