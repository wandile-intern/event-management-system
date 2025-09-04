/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import database.AttendeeManager;
import eventmanagementsystem.Main;
import models.Attendee;

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
public class AttendeeRegistrationUI extends JFrame{
    
        private AttendeeManager attendeeManager;
    private JTextField txtAttendeeName, txtEmail, txtContactNumber, txtEventId;
    private JTable table;
    private DefaultTableModel model;

    public AttendeeRegistrationUI() {
        attendeeManager = new AttendeeManager();

        setTitle("Attendee Registration");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(new JLabel("Attendee Name:"));
        txtAttendeeName = new JTextField();
        formPanel.add(txtAttendeeName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Contact Number:"));
        txtContactNumber = new JTextField();
        formPanel.add(txtContactNumber);

        formPanel.add(new JLabel("Event ID:"));
        txtEventId = new JTextField();
        formPanel.add(txtEventId);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Attendee");
        JButton btnUpdate = new JButton("Update Attendee");
        JButton btnDelete = new JButton("Delete Attendee");
        JButton btnClear = new JButton("Clear");
        JButton btnBack = new JButton("Back");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear); 
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        // Table for displaying attendees
        model = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Contact", "Event ID"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load attendees initially
        loadAttendees();

        // Add event listeners
        btnAdd.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Validate empty fields
        if (txtAttendeeName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Attendee Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtContactNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Contact Number cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (txtEventId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Event ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Event ID as a number
        try {
            Integer.parseInt(txtEventId.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Event ID must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        String name = txtAttendeeName.getText().trim();
        String email = txtEmail.getText().trim();
        String contact = txtContactNumber.getText().trim();
        int eventId = Integer.parseInt(txtEventId.getText().trim());

        try {
            Attendee attendee = new Attendee(0, name, email, contact, eventId);
            attendeeManager.addAttendee(attendee);
            loadAttendees();  // Refresh the table
            JOptionPane.showMessageDialog(null, "Attendee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the input fields
            txtAttendeeName.setText("");
            txtEmail.setText("");
            txtContactNumber.setText("");
            txtEventId.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while adding the attendee. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
});


        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAttendee();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAttendee();
            }
        });
        
         btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtAttendeeName.setText("");
                txtEmail.setText("");
                txtContactNumber.setText("");
                txtEventId.setText("");
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
        txtAttendeeName.setText(model.getValueAt(selectedRow, 1).toString());
        txtEmail.setText(model.getValueAt(selectedRow, 2).toString());
        txtContactNumber.setText(model.getValueAt(selectedRow, 3).toString());
        txtEventId.setText(model.getValueAt(selectedRow, 4).toString());
    }
});

    }

    // Load attendees from database into the table
    private void loadAttendees() {
        model.setRowCount(0);
        List<Attendee> attendees = attendeeManager.getAllAttendees();
        for (Attendee attendee : attendees) {
            model.addRow(new Object[]{
                    attendee.getAttendeeId(),
                    attendee.getAttendeeName(),
                    attendee.getEmail(),
                    attendee.getContactNumber(),
                    attendee.getEventId()
            });
        }
    }

    // Add a new attendee
    private void addAttendee() {
        String name = txtAttendeeName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNumber.getText();
        int eventId = Integer.parseInt(txtEventId.getText());

        Attendee attendee = new Attendee(0, name, email, contact, eventId);
        attendeeManager.addAttendee(attendee);
        loadAttendees();
    }

    // Update selected attendee
    private void updateAttendee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an attendee to update.");
            return;
        }

        int attendeeId = (int) model.getValueAt(selectedRow, 0);
        String name = txtAttendeeName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNumber.getText();
        int eventId = Integer.parseInt(txtEventId.getText());

        Attendee attendee = new Attendee(attendeeId, name, email, contact, eventId);
        attendeeManager.updateAttendee(attendee);
        loadAttendees();
    }

 // Delete selected attendee
    private void deleteAttendee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an attendee to delete.");
            return;
        }

        int attendeeId = (int) model.getValueAt(selectedRow, 0);
        attendeeManager.deleteAttendee(attendeeId);
        loadAttendees(); // Refresh the table
    }

    public static void main(String[] args) {
        AttendeeRegistrationUI frame = new AttendeeRegistrationUI();
        frame.setVisible(true);
    }

    
}
