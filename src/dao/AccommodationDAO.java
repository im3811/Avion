package main.java.travelfinder.dao;

import main.java.travelfinder.model.Accommodation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for Accommodation entity
 */
public interface AccommodationDAO {
    /**
     * Find an accommodation by its ID
     * @param accommodationId The ID of the accommodation
     * @return The accommodation object if found, null otherwise
     */
    Accommodation findById(int accommodationId);
    
    /**
     * Find accommodations by their type
     * @param typeId The ID of the accommodation type
     * @return A list of accommodations of the specified type
     */
    List<Accommodation> findByType(int typeId);
    
    /**
     * Find accommodations by location
     * @param locationId The ID of the location
     * @return A list of accommodations in the specified location
     */
    List<Accommodation> findByLocation(int locationId);
    
    /**
     * Find accommodations within a price range
     * @param minPrice The minimum price
     * @param maxPrice The maximum price
     * @return A list of accommodations within the price range
     */
    List<Accommodation> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find accommodations with a minimum star rating
     * @param minRating The minimum star rating (1-5)
     * @return A list of accommodations with at least the specified rating
     */
    List<Accommodation> findByMinRating(BigDecimal minRating);
    
    /**
     * Search for accommodations based on location name
     * @param locationName The location name to search for
     * @return A list of accommodations matching the search criteria
     */
    List<Accommodation> search(String locationName);
    
    /**
     * Search for accommodations with advanced filters
     * @param filters A map of filter criteria
     * @return A list of accommodations matching the filters
     */
    List<Accommodation> searchWithFilters(Map<String, Object> filters);
    
    /**
     * Get available accommodations for a specific date range
     * @param locationId The location ID
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @param guests The number of guests
     * @return A list of available accommodations
     */
    List<Accommodation> findAvailable(int locationId, LocalDate checkIn, LocalDate checkOut, int guests);
    
    /**
     * Get a list of featured accommodations for display
     * @return A list of featured accommodations
     */
    List<Accommodation> getFeatured();
    
    /**
     * Get all accommodations in the system
     * @return A list of all accommodations
     */
    List<Accommodation> findAll();
    
    /**
     * Save a new accommodation to the database
     * @param accommodation The accommodation object to save
     * @return true if successful, false otherwise
     */
    boolean save(Accommodation accommodation);
    
    /**
     * Update an existing accommodation in the database
     * @param accommodation The accommodation object to update
     * @return true if successful, false otherwise
     */
    boolean update(Accommodation accommodation);
    
    /**
     * Delete an accommodation from the database (soft delete by setting isActive to false)
     * @param accommodationId The ID of the accommodation to delete
     * @return true if successful, false otherwise
     */
    boolean delete(int accommodationId);
    
    /**
     * Count the total number of accommodations
     * @return The total count of accommodations
     */
    int countAll();
    
    /**
     * Get the average rating for an accommodation
     * @param accommodationId The ID of the accommodation
     * @return The average rating
     */
    BigDecimal getAverageRating(int accommodationId);
    
    /**
     * Add an amenity to an accommodation
     * @param accommodationId The ID of the accommodation
     * @param amenityId The ID of the amenity
     * @param isPremium Whether the amenity is premium
     * @return true if successful, false otherwise
     */
    boolean addAmenity(int accommodationId, int amenityId, boolean isPremium);
    
    /**
     * Get accommodations with specific amenities
     * @param amenityIds List of amenity IDs to filter by
     * @return A list of accommodations with the specified amenities
     */
    List<Accommodation> findByAmenities(List<Integer> amenityIds);
}