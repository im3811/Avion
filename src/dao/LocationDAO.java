package main.java.travelfinder.dao;

import main.java.travelfinder.model.Location;
import main.java.travelfinder.model.Location.LocationType;
import java.math.BigDecimal;
import java.util.List;

/**
 * Data Access Object interface for Location entity
 */
public interface LocationDAO {
    /**
     * Find a location by its ID
     * @param locationId The ID of the location
     * @return The location object if found, null otherwise
     */
    Location findById(int locationId);
    
    /**
     * Find locations by name (partial match)
     * @param name The name to search for
     * @return A list of locations matching the name
     */
    List<Location> findByName(String name);
    
    /**
     * Find locations by their type
     * @param locationType The type of location
     * @return A list of locations of the specified type
     */
    List<Location> findByType(LocationType locationType);
    
    /**
     * Find child locations of a parent location
     * @param parentId The ID of the parent location
     * @return A list of child locations
     */
    List<Location> findByParentId(int parentId);
    
    /**
     * Find top-level locations (no parent)
     * @return A list of top-level locations
     */
    List<Location> findTopLevelLocations();
    
    /**
     * Find locations within a specified radius from coordinates
     * @param latitude The latitude coordinate
     * @param longitude The longitude coordinate
     * @param radiusKm The radius in kilometers
     * @return A list of locations within the radius
     */
    List<Location> findNearby(BigDecimal latitude, BigDecimal longitude, double radiusKm);
    
    /**
     * Get the full location hierarchy as a formatted string
     * @param locationId The ID of the location
     * @return A formatted string (e.g., "United States, Florida, Miami")
     */
    String getLocationHierarchy(int locationId);
    
    /**
     * Get all locations in the system
     * @return A list of all locations
     */
    List<Location> findAll();
    
    /**
     * Save a new location to the database
     * @param location The location object to save
     * @return true if successful, false otherwise
     */
    boolean save(Location location);
    
    /**
     * Update an existing location in the database
     * @param location The location object to update
     * @return true if successful, false otherwise
     */
    boolean update(Location location);
    
    /**
     * Delete a location from the database
     * @param locationId The ID of the location to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int locationId);
    
    /**
     * Get locations with the most accommodations
     * @param limit The maximum number of locations to return
     * @return A list of popular locations
     */
    List<Location> findPopularLocations(int limit);
    
    /**
     * Search for locations with text search
     * @param searchText The text to search for
     * @return A list of locations matching the search text
     */
    List<Location> search(String searchText);
    
    /**
     * Get the parent location of a location
     * @param locationId The ID of the location
     * @return The parent location if it exists, null otherwise
     */
    Location getParentLocation(int locationId);
    
    /**
     * Get the full hierarchy path of a location
     * @param locationId The ID of the location
     * @return A list of locations representing the hierarchy from top to bottom
     */
    List<Location> getLocationPath(int locationId);
    
    /**
     * Count the total number of locations
     * @return The total count of locations
     */
    int countAll();
}