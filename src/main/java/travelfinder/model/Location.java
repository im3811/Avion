package main.java.travelfinder.model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private int locationId;
    private String name;
    private Integer parentId; // Can be null
    private String locationType; // Enum in database ('Country', 'Region', 'City', 'District')
    private double latitude;
    private double longitude;
    private String description;
    private String attractions;
    private String geoData; // JSON in database, stored as String here
    
    // Navigation properties
    private Location parent;
    private List<Location> children = new ArrayList<>();
    private List<Accommodation> accommodations = new ArrayList<>();
    
    // Constructors
    public Location() {
    }
    
    public Location(int locationId, String name, String locationType) {
        this.locationId = locationId;
        this.name = name;
        this.locationType = locationType;
    }
    
    public Location(int locationId, String name, Integer parentId, String locationType,
                   double latitude, double longitude, String description, String attractions) {
        this.locationId = locationId;
        this.name = name;
        this.parentId = parentId;
        this.locationType = locationType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.attractions = attractions;
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
    
    public String getLocationType() {
        return locationType;
    }
    
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
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
    
    // Navigation properties getters and setters
    public Location getParent() {
        return parent;
    }
    
    public void setParent(Location parent) {
        this.parent = parent;
    }
    
    public List<Location> getChildren() {
        return children;
    }
    
    public void setChildren(List<Location> children) {
        this.children = children;
    }
    
    public List<Accommodation> getAccommodations() {
        return accommodations;
    }
    
    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }
    
    // Helper methods
    public void addChild(Location child) {
        children.add(child);
    }
    
    public void addAccommodation(Accommodation accommodation) {
        accommodations.add(accommodation);
    }
    
    public String getFullName() {
        StringBuilder fullName = new StringBuilder(name);
        Location currentParent = parent;
        
        while (currentParent != null) {
            fullName.insert(0, currentParent.getName() + ", ");
            currentParent = currentParent.getParent();
        }
        
        return fullName.toString();
    }
    
    @Override
    public String toString() {
        return name + " (" + locationType + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Location location = (Location) o;
        return locationId == location.locationId;
    }
    
    @Override
    public int hashCode() {
        return locationId;
    }
}