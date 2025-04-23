package main.java.travelfinder.model;

public class Amenity {
    private int amenityId;
    private String name;
    private String icon;
    private String category;
    
    // Constructors
    public Amenity() {}
    
    public Amenity(int amenityId, String name, String category) {
        this.amenityId = amenityId;
        this.name = name;
        this.category = category;
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
    
    @Override
    public String toString() {
        return "Amenity{" +
                "amenityId=" + amenityId +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
    
    // Class for mapping the many-to-many relationship
    public static class AccommodationAmenity {
        private int accommodationId;
        private int amenityId;
        private String details;
        private boolean isPremium;
        
        // Constructors
        public AccommodationAmenity() {}
        
        public AccommodationAmenity(int accommodationId, int amenityId) {
            this.accommodationId = accommodationId;
            this.amenityId = amenityId;
            this.isPremium = false;
        }
        
        // Getters and Setters
        public int getAccommodationId() {
            return accommodationId;
        }
        
        public void setAccommodationId(int accommodationId) {
            this.accommodationId = accommodationId;
        }
        
        public int getAmenityId() {
            return amenityId;
        }
        
        public void setAmenityId(int amenityId) {
            this.amenityId = amenityId;
        }
        
        public String getDetails() {
            return details;
        }
        
        public void setDetails(String details) {
            this.details = details;
        }
        
        public boolean isPremium() {
            return isPremium;
        }
        
        public void setPremium(boolean premium) {
            isPremium = premium;
        }
        
        @Override
        public String toString() {
            return "AccommodationAmenity{" +
                    "accommodationId=" + accommodationId +
                    ", amenityId=" + amenityId +
                    ", isPremium=" + isPremium +
                    '}';
        }
    }
}