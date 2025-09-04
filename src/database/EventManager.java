/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import models.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author walter n
 */
public class EventManager {
    
     private Connection connection;

    public EventManager() {
        this.connection = DatabaseConnection.getConnection();
    }

    // Create Event
    public void addEvent(Event event) {
        String sql = "INSERT INTO Events (event_name, event_date, event_time, description, organiser) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getEventDate());
            stmt.setString(3, event.getEventTime());
            stmt.setString(4, event.getDescription());
            stmt.setString(5, event.getOrganiser());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read Events
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("event_date"),
                        rs.getString("event_time"),
                        rs.getString("description"),
                        rs.getString("organiser")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    // Update Event
    public void updateEvent(Event event) {
        String sql = "UPDATE Events SET event_name = ?, event_date = ?, event_time = ?, description = ?, organiser = ? WHERE event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.getEventName());
            stmt.setString(2, event.getEventDate());
            stmt.setString(3, event.getEventTime());
            stmt.setString(4, event.getDescription());
            stmt.setString(5, event.getOrganiser());
            stmt.setInt(6, event.getEventId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Event
    public void deleteEvent(int eventId) {
        String sql = "DELETE FROM Events WHERE event_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
