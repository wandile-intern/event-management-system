/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import models.Attendee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walter n
 */
public class AttendeeManager {
    
    private Connection connection;

    public AttendeeManager() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create Attendee
    public void addAttendee(Attendee attendee) {
        String sql = "INSERT INTO Attendees (attendee_name, email, contact_number, event_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, attendee.getAttendeeName());
            stmt.setString(2, attendee.getEmail());
            stmt.setString(3, attendee.getContactNumber());
            stmt.setInt(4, attendee.getEventId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read Attendees
    public List<Attendee> getAllAttendees() {
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT * FROM Attendees";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Attendee attendee = new Attendee(
                        rs.getInt("attendee_id"),
                        rs.getString("attendee_name"),
                        rs.getString("email"),
                        rs.getString("contact_number"),
                        rs.getInt("event_id")
                );
                attendees.add(attendee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendees;
    }

    // Update Attendee
    public void updateAttendee(Attendee attendee) {
        String sql = "UPDATE Attendees SET attendee_name = ?, email = ?, contact_number = ?, event_id = ? WHERE attendee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, attendee.getAttendeeName());
            stmt.setString(2, attendee.getEmail());
            stmt.setString(3, attendee.getContactNumber());
            stmt.setInt(4, attendee.getEventId());
            stmt.setInt(5, attendee.getAttendeeId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Attendee
    public void deleteAttendee(int attendeeId) {
        String sql = "DELETE FROM Attendees WHERE attendee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, attendeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
