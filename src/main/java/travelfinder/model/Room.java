package main.java.travelfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomId;
    private int accommodationId;
    private String roomName;
    private String description;
    private int capacity;
    private double priceModifier;
    private Integer roomSize; // Can be null, in square meters/feet
    private String bedType;
    private String features; // JSON in database, stored as String here
    private boolean isAvailable;
    
    // Navigation properties
    private Accommodation accommodation;
    private List<Booking> bookings = new ArrayList<>();
    private List<String> mediaUrls = new ArrayList<>();
    
    // Constructors
    public Room() {
    }
    
    public Room(int roomId, int accommodationId, String roomName, int capacity, double priceModifier) {
        this.roomId = roomId;
        this.accommodationId = accommodationId;
        this.roomName = roomName;
        this.capacity = capacity;
        this.priceModifier = priceModifier;
        this.isAvailable = true;
    }
    
    public Room(int roomId, int accommodationId, String roomName, String description,
               int capacity, double priceModifier, Integer roomSize, String bedType) {
        this.roomId = roomId;
        this.accommodationId = accommodationId;
        this.roomName = roomName;
        this.description = description;
        this.capacity = capacity;
        this.priceModifier = priceModifier;
        this.roomSize = roomSize;
        this.bedType = bedType;
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
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }
    
    public double getPriceModifier() {
        return priceModifier;
    }
    
    public void setPriceModifier(double priceModifier) {
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
    
    // Navigation properties getters and setters
    public Accommodation getAccommodation() {
        return accommodation;
    }
    
    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    public List<String> getMediaUrls() {
        return mediaUrls;
    }
    
    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }
    
    // Helper methods
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
    
    public void addMediaUrl(String url) {
        mediaUrls.add(url);
    }
    
    public double calculatePrice() {
        if (accommodation != null) {
            return accommodation.getBasePrice() * priceModifier;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return roomName + " - Capacity: " + capacity + " guests";
    }
}