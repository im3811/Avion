package main.java.travelfinder.model;

public class Amenity {
    private int amenityId;
    private String name;
    private String icon;
    private String category;
    private boolean isPremium;
    private String details;
    
    // Constructors
    public Amenity() {
    }
    
    public Amenity(int amenityId, String name, String category) {
        this.amenityId = amenityId;
        this.name = name;
        this.category = category;
    }
    
    public Amenity(int amenityId, String name, String icon, String category, boolean isPremium, String details) {
        this.amenityId = amenityId;
        this.name = name;
        this.icon = icon;
        this.category = category;
        this.isPremium = isPremium;
        this.details = details;
    }
    
    // Getters and Setters
    public int getAmenityId() {
        return amenityId;
    }
    
    public void setAmenityId(int amenityId) {
        this.amenityId = amenityId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public boolean isPremium() {
        return isPremium;
    }
    
    public void setPremium(boolean premium) {
        isPremium = premium;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    @Override
    public String toString() {
        return name + (isPremium ? " (Premium)" : "");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Amenity amenity = (Amenity) o;
        return amenityId == amenity.amenityId;
    }
    
    @Override
    public int hashCode() {
        return amenityId;
    }
}