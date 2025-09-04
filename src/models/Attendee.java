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
public class Attendee {
    
    private int attendeeId;
    private String attendeeName;
    private String email;
    private String contactNumber;
    private int eventId;
    
        // Constructor
    public Attendee(int attendeeId, String attendeeName, String email, String contactNumber, int eventId) {
        this.attendeeId = attendeeId;
        this.attendeeName = attendeeName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.eventId = eventId;
    }

    // Getters
    public int getAttendeeId() {
        return attendeeId;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getEventId() {
        return eventId;
    }

    // Setters
    public void setAttendeeId(int attendeeId) {
        this.attendeeId = attendeeId;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    
}
