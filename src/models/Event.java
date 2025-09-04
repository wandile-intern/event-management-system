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
public class Event {
    
    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String description;
    private String organiser;
    
    // Constructor
        public Event(int eventId, String eventName, String eventDate, String eventTime, String description, String organiser) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.description = description;
        this.organiser = organiser;
    }

    // Getters
    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getDescription() {
        return description;
    }

    public String getOrganiser() {
        return organiser;
    }

    // Setters
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    
}
