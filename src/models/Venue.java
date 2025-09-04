/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author walter n
 */
public class Venue {
    
    private int venueId;
    private String venueName;
    private String address;
    private int capacity;
    private boolean availability;
    
    // Constructor
    public Venue(int venueId, String venueName, String address, int capacity, boolean availability) {
        this.venueId = venueId;
        this.venueName = venueName;
        this.address = address;
        this.capacity = capacity;
        this.availability = availability;
    }

    // Getters and Setters
    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    
}
