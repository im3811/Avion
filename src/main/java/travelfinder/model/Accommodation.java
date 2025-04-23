package main.java.travelfinder.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Accommodation {
    private int accommodationId;
    private String name;
    private int typeId;
    private String typeName; // For convenience
    private String description;
    private int locationId;
    private String address;
    private double starRating;
    private String checkInTime;
    private String checkOutTime;
    private String cancellationPolicy;
    private double basePrice;
    private String metadata; // JSON in database, stored as String here
    private boolean isVerified;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Navigation properties
    private Location location;
    private List<Room> rooms = new ArrayList<>();
    private List<Amenity> amenities = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    
    // Constructors
    public Accommodation() {
    }
    
    public Accommodation(int accommodationId, String name, int typeId, String description,
                         int locationId, String address, double starRating, double basePrice) {
        this.accommodationId = accommodationId;
        this.name = name;
        this.typeId = typeId;
        this.description = description;
        this.locationId = locationId;
        this.address = address;
        this.starRating = starRating;
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
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    
    public double getStarRating() {
        return starRating;
    }
    
    public void setStarRating(double starRating) {
        this.starRating = starRating;
    }
    
    public String getCheckInTime() {
        return checkInTime;
    }
    
    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }
    
    public String getCheckOutTime() {
        return checkOutTime;
    }
    
    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    
    public String getCancellationPolicy() {
        return cancellationPolicy;
    }
    
    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(double basePrice) {
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
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Navigation properties getters and setters
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public List<Amenity> getAmenities() {
        return amenities;
    }
    
    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    // Helper methods
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
    }
    
    public void addReview(Review review) {
        reviews.add(review);
    }
    
    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0;
        }
        
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        
        return sum / reviews.size();
    }
    
    @Override
    public String toString() {
        return name + " (" + starRating + " stars) - $" + basePrice + "/night";
    }
}