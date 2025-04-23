CREATE DATABASE IF NOT EXISTS travel_finder;
USE travel_finder;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    address VARCHAR(255),
    role ENUM('admin', 'traveler') NOT NULL DEFAULT 'traveler',
    preferences JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE Locations (
    location_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    parent_id INT NULL,
    location_type ENUM('Country', 'Region', 'City', 'District') NOT NULL,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    description TEXT,
    attractions TEXT,
    geo_data JSON,
    FOREIGN KEY (parent_id) REFERENCES Locations(location_id) ON DELETE SET NULL
);

CREATE TABLE Accommodation_Types (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE Amenities (
    amenity_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    icon VARCHAR(100),
    category VARCHAR(50)
);

CREATE TABLE Accommodations (
    accommodation_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type_id INT NOT NULL,
    description TEXT,
    location_id INT NOT NULL,
    address VARCHAR(255) NOT NULL,
    star_rating DECIMAL(2,1) CHECK (star_rating >= 0 AND star_rating <= 5),
    check_in_time TIME,
    check_out_time TIME,
    cancellation_policy TEXT,
    base_price DECIMAL(10,2) NOT NULL,
    metadata JSON,
    is_verified BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (type_id) REFERENCES Accommodation_Types(type_id),
    FOREIGN KEY (location_id) REFERENCES Locations(location_id)
);

CREATE TABLE Accommodation_Amenities (
    accommodation_id INT,
    amenity_id INT,
    details VARCHAR(255),
    is_premium BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (accommodation_id, amenity_id),
    FOREIGN KEY (accommodation_id) REFERENCES Accommodations(accommodation_id) ON DELETE CASCADE,
    FOREIGN KEY (amenity_id) REFERENCES Amenities(amenity_id) ON DELETE CASCADE
);

CREATE TABLE Rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    accommodation_id INT NOT NULL,
    room_name VARCHAR(100) NOT NULL,
    description TEXT,
    capacity INT NOT NULL CHECK (capacity > 0),
    price_modifier DECIMAL(5,2) NOT NULL DEFAULT 1.00,
    room_size INT,
    bed_type VARCHAR(50),
    features JSON,
    is_available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (accommodation_id) REFERENCES Accommodations(accommodation_id) ON DELETE CASCADE
);

CREATE TABLE Media (
    media_id INT AUTO_INCREMENT PRIMARY KEY,
    accommodation_id INT NOT NULL,
    room_id INT NULL,
    file_path VARCHAR(255) NOT NULL,
    caption VARCHAR(255),
    media_type ENUM('image', 'video', 'vr') NOT NULL DEFAULT 'image',
    is_primary BOOLEAN DEFAULT FALSE,
    sort_order INT DEFAULT 0,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accommodation_id) REFERENCES Accommodations(accommodation_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES Rooms(room_id) ON DELETE CASCADE
);

CREATE TABLE Booking_Statuses (
    status_id INT AUTO_INCREMENT PRIMARY KEY,
    status_name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    accommodation_id INT NOT NULL,
    room_id INT NULL,
    reference_number VARCHAR(20) NOT NULL UNIQUE,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    num_guests INT NOT NULL CHECK (num_guests > 0),
    status_id INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    special_requests TEXT,
    booking_extras JSON,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    modified_by_user_id INT NULL,
    booking_history JSON,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (accommodation_id) REFERENCES Accommodations(accommodation_id),
    FOREIGN KEY (room_id) REFERENCES Rooms(room_id),
    FOREIGN KEY (status_id) REFERENCES Booking_Statuses(status_id),
    FOREIGN KEY (modified_by_user_id) REFERENCES Users(user_id)
);

CREATE TABLE Payment_Methods (
    method_id INT AUTO_INCREMENT PRIMARY KEY,
    method_name VARCHAR(50) NOT NULL UNIQUE,
    processor VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    method_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency CHAR(3) NOT NULL DEFAULT 'USD',
    exchange_rate DECIMAL(10,6) DEFAULT 1.0,
    transaction_id VARCHAR(100) UNIQUE,
    payment_status ENUM('Pending', 'Completed', 'Failed', 'Refunded') NOT NULL,
    payment_type ENUM('Full', 'Installment', 'Refund') NOT NULL DEFAULT 'Full',
    payment_details JSON,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    parent_payment_id INT NULL,
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id),
    FOREIGN KEY (method_id) REFERENCES Payment_Methods(method_id),
    FOREIGN KEY (parent_payment_id) REFERENCES Payments(payment_id)
);

CREATE TABLE Reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    accommodation_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    response JSON,
    response_by_user_id INT NULL,
    response_date TIMESTAMP NULL,
    moderation_status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_verified BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (accommodation_id) REFERENCES Accommodations(accommodation_id),
    FOREIGN KEY (response_by_user_id) REFERENCES Users(user_id),
    UNIQUE KEY unique_booking_review (booking_id)
);

INSERT INTO Accommodation_Types (type_name, description) VALUES ('Hotel', 'Traditional accommodation with rooms and services');
INSERT INTO Accommodation_Types (type_name, description) VALUES ('Apartment', 'Self-contained units with kitchen facilities');
INSERT INTO Accommodation_Types (type_name, description) VALUES ('Vacation Home', 'Entire property rentals for exclusive use');
INSERT INTO Accommodation_Types (type_name, description) VALUES ('Hostel', 'Budget accommodation with shared facilities');
INSERT INTO Accommodation_Types (type_name, description) VALUES ('Resort', 'Full-service properties with extensive amenities');

INSERT INTO Booking_Statuses (status_name, description) VALUES ('Pending', 'Booking has been created but not confirmed');
INSERT INTO Booking_Statuses (status_name, description) VALUES ('Confirmed', 'Booking has been confirmed and is active');
INSERT INTO Booking_Statuses (status_name, description) VALUES ('Completed', 'Stay has been completed');
INSERT INTO Booking_Statuses (status_name, description) VALUES ('Cancelled', 'Booking has been cancelled');
INSERT INTO Booking_Statuses (status_name, description) VALUES ('No-Show', 'Guest did not arrive for their booking');

INSERT INTO Payment_Methods (method_name, processor, is_active) VALUES ('Credit Card', 'Stripe', TRUE);
INSERT INTO Payment_Methods (method_name, processor, is_active) VALUES ('PayPal', 'PayPal', TRUE);
INSERT INTO Payment_Methods (method_name, processor, is_active) VALUES ('Bank Transfer', 'Manual', TRUE);

INSERT INTO Amenities (name, icon, category) VALUES ('WiFi', 'wifi-icon.png', 'Technology');
INSERT INTO Amenities (name, icon, category) VALUES ('Swimming Pool', 'pool-icon.png', 'Recreation');
INSERT INTO Amenities (name, icon, category) VALUES ('Free Parking', 'parking-icon.png', 'Transportation');
INSERT INTO Amenities (name, icon, category) VALUES ('Air Conditioning', 'ac-icon.png', 'Comfort');
INSERT INTO Amenities (name, icon, category) VALUES ('Breakfast Included', 'breakfast-icon.png', 'Food');


INSERT INTO Locations (name, parent_id, location_type, latitude, longitude, description, attractions) 
VALUES ('United States', NULL, 'Country', 37.0902, -95.7129, 'Country in North America', 'National Parks, Major Cities');

INSERT INTO Locations (name, parent_id, location_type, latitude, longitude, description, attractions) 
VALUES ('Florida', 1, 'Region', 27.6648, -81.5158, 'Southeastern US state', 'Beaches, Theme Parks');

INSERT INTO Locations (name, parent_id, location_type, latitude, longitude, description, attractions) 
VALUES ('Miami', 2, 'City', 25.7617, -80.1918, 'Major city in South Florida', 'South Beach, Art Deco District');

INSERT INTO Locations (name, parent_id, location_type, latitude, longitude, description, attractions) 
VALUES ('California', 1, 'Region', 36.7783, -119.4179, 'Western US state', 'Hollywood, Silicon Valley');

INSERT INTO Locations (name, parent_id, location_type, latitude, longitude, description, attractions) 
VALUES ('Los Angeles', 4, 'City', 34.0522, -118.2437, 'Major city in Southern California', 'Hollywood, Beverly Hills');

INSERT INTO Users (username, email, password_hash, first_name, last_name, phone_number, role, is_active)
VALUES ('john_doe', 'john@example.com', '$2a$10$dL4B7.tyRJb8fWB8INpBNO9BqJRWxKiBFZ.Qz.7lWkCfZ6TM2EhSe', 'John', 'Doe', '+1-555-123-4567', 'traveler', TRUE);

INSERT INTO Users (username, email, password_hash, first_name, last_name, phone_number, role, is_active)
VALUES ('admin_user', 'admin@travelfinder.com', '$2a$10$j3NCXU8HbdOKGWA4SgQqueUoAB7Uw6AQRGfA0TZlAZPd0jGLCJfVu', 'Admin', 'User', '+1-555-000-0000', 'admin', TRUE);

INSERT INTO Accommodations (name, type_id, description, location_id, address, star_rating, base_price, is_verified, is_active)
VALUES ('Miami Beach Resort', 5, 'Beachfront resort', 3, '123 Beach Drive, Miami, FL 33139', 4.5, 299.99, TRUE, TRUE);

INSERT INTO Accommodations (name, type_id, description, location_id, address, star_rating, base_price, is_verified, is_active)
VALUES ('Hollywood Hotel', 1, 'Central hotel', 5, '789 Hollywood Blvd, Los Angeles, CA 90028', 4.0, 249.99, TRUE, TRUE);