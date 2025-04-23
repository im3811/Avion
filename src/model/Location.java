package model;

public class Location {
    
}
package main.java.travelfinder.model;

import java.math.BigDecimal;

public class Location {
    private int locationId;
    private String name;
    private Integer parentId; // Can be null for top-level locations (e.g., countries)
    private LocationType locationType;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String description;
    private String attractions;
    private String geoData; // JSON stored as String
    
    public enum LocationType {
        COUNTRY, REGION, CITY, DISTRICT
    }
    
    // Constructors
    public Location() {}
    
    public Location(int locationId, String name, LocationType locationType) {
        this.locationId = locationId;
        this.name = name;
        this.locationType = locationType;
    }
    
    // Getters and Setters
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getParentId() {
        return parentId;
    }
    
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
    
    public LocationType getLocationType() {
        return locationType;
    }
    
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }
    
    public BigDecimal getLatitude() {
        return latitude;
    }
    
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
    
    public BigDecimal getLongitude() {
        return longitude;
    }
    
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAttractions() {
        return attractions;
    }
    
    public void setAttractions(String attractions) {
        this.attractions = attractions;
    }
    
    public String getGeoData() {
        return geoData;
    }
    
    public void setGeoData(String geoData) {
        this.geoData = geoData;
    }
    
    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", locationType=" + locationType +
                '}';
    }
    
    // Helper method to get formatted location string
    public String getFormattedLocation() {
        // This would typically be populated from a parent location, if available
        // For now, return just the name
        return name;
    }
    
    // Helper method to get coordinates as string
    public String getCoordinatesString() {
        if (latitude == null || longitude == null) {
            return "Coordinates not available";
        }
        return latitude + ", " + longitude;
    }
}