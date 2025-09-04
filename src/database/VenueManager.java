/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import models.Venue;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author walter n
 */
public class VenueManager {
    
        private Connection connection;

    public VenueManager() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create Venue
    public void addVenue(Venue venue) {
        String sql = "INSERT INTO Venues (venue_name, address, capacity, availability) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venue.getVenueName());
            stmt.setString(2, venue.getAddress());
            stmt.setInt(3, venue.getCapacity());
            stmt.setBoolean(4, venue.isAvailability());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read Venues
    public List<Venue> getAllVenues() {
        List<Venue> venues = new ArrayList<>();
        String sql = "SELECT * FROM Venues";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venue venue = new Venue(
                        rs.getInt("venue_id"),
                        rs.getString("venue_name"),
                        rs.getString("address"),
                        rs.getInt("capacity"),
                        rs.getBoolean("availability")
                );
                venues.add(venue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return venues;
    }

    // Update Venue
    public void updateVenue(Venue venue) {
        String sql = "UPDATE Venues SET venue_name = ?, address = ?, capacity = ?, availability = ? WHERE venue_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, venue.getVenueName());
            stmt.setString(2, venue.getAddress());
            stmt.setInt(3, venue.getCapacity());
            stmt.setBoolean(4, venue.isAvailability());
            stmt.setInt(5, venue.getVenueId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Venue
    public void deleteVenue(int venueId) {
        String sql = "DELETE FROM Venues WHERE venue_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, venueId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
