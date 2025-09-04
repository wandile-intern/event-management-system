/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import database.EventManager;
import eventmanagementsystem.Main;
import models.Event;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 *
 * @author walter n
 */
public class EventPlannerUI extends JFrame{
    
    private EventManager eventManager;
    private JTextField txtEventName, txtEventDate, txtEventTime, txtDescription, txtOrganiser;
    private JTable table;
    private DefaultTableModel model;

    public EventPlannerUI() {
        eventManager = new EventManager();

        // Frame settings
        setTitle("Event Planner");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        formPanel.add(new JLabel("Event Name:"));
        txtEventName = new JTextField();
        formPanel.add(txtEventName);

        formPanel.add(new JLabel("Event Date (YYYY-MM-DD):"));
        txtEventDate = new JTextField();
        formPanel.add(txtEventDate);

        formPanel.add(new JLabel("Event Time (HH:MM:SS):"));
        txtEventTime = new JTextField();
        formPanel.add(txtEventTime);

        formPanel.add(new JLabel("Description:"));
        txtDescription = new JTextField();
        formPanel.add(txtDescription);

        formPanel.add(new JLabel("Organiser:"));
        txtOrganiser = new JTextField();
        formPanel.add(txtOrganiser);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Event");
        JButton btnUpdate = new JButton("Update Event");
        JButton btnDelete = new JButton("Delete Event");
        JButton btnClear = new JButton("Clear");
        JButton btnBack = new JButton("Back");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear); 
        buttonPanel.add(btnBack); 
        add(buttonPanel, BorderLayout.SOUTH);

        // Table for displaying events
        model = new DefaultTableModel(new String[]{"ID", "Name", "Date", "Time", "Description", "Organiser"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load events initially
        loadEvents();

        // Add event listeners
        btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Validate empty fields
        if (txtEventName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Event Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtEventDate.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Event Date cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtEventTime.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Event Time cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtDescription.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtOrganiser.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Organiser cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Date and Time Formats
        if (!isValidDate(txtEventDate.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Event Date must be in the format YYYY-MM-DD!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidTime(txtEventTime.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Event Time must be in the format HH:MM:SS!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        String name = txtEventName.getText().trim();
        String date = txtEventDate.getText().trim();
        String time = txtEventTime.getText().trim();
        String description = txtDescription.getText().trim();
        String organiser = txtOrganiser.getText().trim();

        try {
            Event event = new Event(0, name, date, time, description, organiser);
            eventManager.addEvent(event);
            loadEvents();  // Refresh the table
            JOptionPane.showMessageDialog(null, "Event added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the input fields
            txtEventName.setText("");
            txtEventDate.setText("");
            txtEventTime.setText("");
            txtDescription.setText("");
            txtOrganiser.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while adding the event. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    
private boolean isValidDate(String date) {
    try {
        java.time.LocalDate.parse(date);  // Validates YYYY-MM-DD format
        return true;
    } catch (Exception ex) {
        return false;
    }
}


private boolean isValidTime(String time) {
    try {
        java.time.LocalTime.parse(time);  // Validates HH:MM:SS format
        return true;
    } catch (Exception ex) {
        return false;
    }
}
});




        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEvent();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEvent();
            }
        });
        
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtEventName.setText("");
                txtEventDate.setText("");
                txtEventTime.setText("");
                txtDescription.setText("");
                txtOrganiser.setText("");
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();  // Close the current window
        new Main().setVisible(true);  // Open the main menu
           }
        });
        
        
        table.getSelectionModel().addListSelectionListener(e -> {
   
    if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
        int selectedRow = table.getSelectedRow();

        // Populate text fields with the selected row's data
        txtEventName.setText(model.getValueAt(selectedRow, 1).toString());
        txtEventDate.setText(model.getValueAt(selectedRow, 2).toString());
        txtEventTime.setText(model.getValueAt(selectedRow, 3).toString());
        txtDescription.setText(model.getValueAt(selectedRow, 4).toString());
        txtOrganiser.setText(model.getValueAt(selectedRow, 5).toString());

    }
        });
    }

    // Load events from database into the table
    private void loadEvents() {
        model.setRowCount(0); // Clear existing rows
        List<Event> events = eventManager.getAllEvents();
        for (Event event : events) {
            model.addRow(new Object[]{event.getEventId(), event.getEventName(), event.getEventDate(), event.getEventTime(), event.getDescription(), event.getOrganiser()});
        }
    }

    // Add a new event
    private void addEvent() {
        String name = txtEventName.getText();
        String date = txtEventDate.getText();
        String time = txtEventTime.getText();
        String description = txtDescription.getText();
        String organiser = txtOrganiser.getText();

        Event event = new Event(0, name, date, time, description, organiser);
        eventManager.addEvent(event);
        loadEvents(); // Refresh table
    }

    // Update selected event
    private void updateEvent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to update.");
            return;
        }

        int eventId = (int) model.getValueAt(selectedRow, 0);
        String name = txtEventName.getText();
        String date = txtEventDate.getText();
        String time = txtEventTime.getText();
        String description = txtDescription.getText();
        String organiser = txtOrganiser.getText();

        Event event = new Event(eventId, name, date, time, description, organiser);
        eventManager.updateEvent(event);
        loadEvents(); // Refresh table
    }

    // Delete selected event
    private void deleteEvent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an event to delete.");
            return;
        }

        int eventId = (int) model.getValueAt(selectedRow, 0);
        eventManager.deleteEvent(eventId);
        loadEvents(); // Refresh table
    }

    public static void main(String[] args) {
        EventPlannerUI frame = new EventPlannerUI();
        frame.setVisible(true);
    }   
}
