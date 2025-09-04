/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import database.VenueManager;
import eventmanagementsystem.Main;
import models.Venue;

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
public class VenueManagementUI extends JFrame{
    
    private VenueManager venueManager;
    private JTextField txtVenueName, txtAddress, txtCapacity;
    private JCheckBox chkAvailability;
    private JTable table;
    private DefaultTableModel model;

    public VenueManagementUI() {
        venueManager = new VenueManager();

        setTitle("Venue Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(new JLabel("Venue Name:"));
        txtVenueName = new JTextField();
        formPanel.add(txtVenueName);

        formPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        formPanel.add(txtAddress);

        formPanel.add(new JLabel("Capacity:"));
        txtCapacity = new JTextField();
        formPanel.add(txtCapacity);

        formPanel.add(new JLabel("Available:"));
        chkAvailability = new JCheckBox();
        formPanel.add(chkAvailability);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Venue");
        JButton btnUpdate = new JButton("Update Venue");
        JButton btnDelete = new JButton("Delete Venue");
        JButton btnClear = new JButton("Clear");
        JButton btnBack = new JButton("Back");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear); 
        buttonPanel.add(btnBack); 
        add(buttonPanel, BorderLayout.SOUTH);

        // Table for displaying venues
        model = new DefaultTableModel(new String[]{"ID", "Name", "Address", "Capacity", "Availability"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load venues initially
        loadVenues();

        // Button listeners
        btnAdd.addActionListener(new ActionListener() {
        @Override
         public void actionPerformed(ActionEvent e) {
        // Validate empty fields
        if (txtVenueName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Venue Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Address cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtCapacity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Capacity cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Capacity as a number
        try {
            Integer.parseInt(txtCapacity.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Capacity must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        String name = txtVenueName.getText().trim();
        String address = txtAddress.getText().trim();
        int capacity = Integer.parseInt(txtCapacity.getText().trim());
        boolean availability = chkAvailability.isSelected();

        try {
            Venue venue = new Venue(0, name, address, capacity, availability);
            venueManager.addVenue(venue);
            loadVenues();  
            JOptionPane.showMessageDialog(null, "Venue added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the input fields
            txtVenueName.setText("");
            txtAddress.setText("");
            txtCapacity.setText("");
            chkAvailability.setSelected(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while adding the venue. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
});


        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateVenue();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteVenue();
            }
        });
        
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtVenueName.setText("");
                txtAddress.setText("");
                txtCapacity.setText("");
                chkAvailability.setSelected(false);
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
        txtVenueName.setText(model.getValueAt(selectedRow, 1).toString());
        txtAddress.setText(model.getValueAt(selectedRow, 2).toString());
        txtCapacity.setText(model.getValueAt(selectedRow, 3).toString());
    }
        });
    
    }

    // Load venues
    private void loadVenues() {
        model.setRowCount(0);
        List<Venue> venues = venueManager.getAllVenues();
        for (Venue venue : venues) {
            model.addRow(new Object[]{
                    venue.getVenueId(),
                    venue.getVenueName(),
                    venue.getAddress(),
                    venue.getCapacity(),
                    venue.isAvailability()
            });
        }
    }

    // Add Venue
    private void addVenue() {
        String name = txtVenueName.getText();
        String address = txtAddress.getText();
        int capacity = Integer.parseInt(txtCapacity.getText());
        boolean availability = chkAvailability.isSelected();

        Venue venue = new Venue(0, name, address, capacity, availability);
        venueManager.addVenue(venue);
        loadVenues();
    }

    // Update selected venue
    private void updateVenue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a venue to update.");
            return;
        }

        int venueId = (int) model.getValueAt(selectedRow, 0);
        String name = txtVenueName.getText();
        String address = txtAddress.getText();
        int capacity = Integer.parseInt(txtCapacity.getText());
        boolean availability = chkAvailability.isSelected();

        Venue venue = new Venue(venueId, name, address, capacity, availability);
        venueManager.updateVenue(venue);
        loadVenues(); // Refresh the table to show updated data
    }

    // Delete selected venue
    private void deleteVenue() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a venue to delete.");
            return;
        }

        int venueId = (int) model.getValueAt(selectedRow, 0);
        venueManager.deleteVenue(venueId);
        loadVenues(); // Refresh the table after deletion
    }

    public static void main(String[] args) {
        VenueManagementUI frame = new VenueManagementUI();
        frame.setVisible(true);
}
}