package gp_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDashboard extends JFrame {
    private Doctor loggedInDoctor;

    public DoctorDashboard(Doctor loggedInDoctor) {
        super("Doctor Dashboard");
        this.loggedInDoctor = loggedInDoctor;

        // Configure JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);

        // Labels to display system information and patient details
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel systemLabel = new JLabel("GP Booking System - Doctor Dashboard");
        systemLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel welcomeLabel = new JLabel("Welcome: " + loggedInDoctor.getName());
        JLabel roleLabel = new JLabel("Role: " + loggedInDoctor.getRole());
        JLabel doctorIdLabel = new JLabel("Doctor ID: " + loggedInDoctor.getDoctorId());
        JLabel genderLabel = new JLabel("Gender: " + loggedInDoctor.getGender());

        // Add labels to the header panel
        headerPanel.add(systemLabel, BorderLayout.PAGE_START);
        headerPanel.add(welcomeLabel, BorderLayout.LINE_START);
        headerPanel.add(roleLabel, BorderLayout.LINE_END);
        headerPanel.add(doctorIdLabel, BorderLayout.PAGE_END);
        headerPanel.add(genderLabel, BorderLayout.WEST);
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        // Create footer panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());

        // Label to display copyright information
        JLabel footerLabel = new JLabel("© 2024 GP Booking System. All rights reserved. contact support@gpbookingsystem.com");

        // Add label to the footer panel
        footerPanel.add(footerLabel, BorderLayout.SOUTH);
        footerLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add border to the header panel// Add border to the header panel
        footerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add header and footer to content pane
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);


        // Create buttons and add functionality
        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBookings();
            }
        });

        JButton viewPatientsButton = new JButton("View Own Patients");
        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPatients();
            }
        });

        JButton enterVisitDetailsButton = new JButton("Enter Visit Details");
        enterVisitDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterVisitDetails();
            }
        });

        JButton viewVisitDetailsButton = new JButton("View Visit Details and Prescriptions");
        viewVisitDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewVisitDetails();
            }
        });

        JButton editVisitDetailsButton = new JButton("Edit Visit Details and Prescriptions");
        editVisitDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editVisitDetails();
            }
        });

        JButton assignNewDoctorButton = new JButton("Assign New Doctor to Patient");
        assignNewDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignNewDoctor();
            }
        });

        JButton viewAllPatientsButton = new JButton("View All Patients");
        viewAllPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllPatients();
            }
        });

        JButton sendMessageButton = new JButton("Send Message to Admin/Receptionist");
        sendMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(); // Perform logout action
            }
        });


        JPanel panel = new JPanel(new GridLayout(9, 1));
        panel.add(viewBookingsButton);
        panel.add(viewPatientsButton);
        panel.add(enterVisitDetailsButton);
        panel.add(viewVisitDetailsButton);
        panel.add(editVisitDetailsButton);
        panel.add(assignNewDoctorButton);
        panel.add(viewAllPatientsButton);
        panel.add(sendMessageButton);
        panel.add(logoutButton);
        add(panel, BorderLayout.CENTER);
    }

    // Method to fetch the specialization for the logged-in doctor
    private String fetchDoctorSpecialisation(String string) {
        String specialisation = "";
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT specialisation FROM doctors WHERE doctor_id = ?");
            preparedStatement.setString(1, string);
            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    specialisation = resultSet.getString("specialisation");
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return specialisation;
    }

    private String fetchDoctorId(String string) {
        String doctorId = ""; // Default value

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT doctor_id FROM doctors WHERE doctor_id = ?")) {

            preparedStatement.setString(1, string);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    doctorId = resultSet.getString("doctor_id");
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return doctorId;
    }



    private void viewBookings() {
        // Implement logic to fetch and display bookings for the doctor

        // Display the bookings in a table or any suitable UI component

    }

    private void viewPatients() {
        // Implement logic to fetch and display own patients for the doctor
        List<Patient> patients = fetchDoctorPatients(loggedInDoctor);
        // Display the patients in a table or any suitable UI component
        displayPatients(patients);
    }

    private void enterVisitDetails() {
        // Implement logic to enter visit details for a patient
        // Example: Open a form to enter visit details and prescriptions, then insert into the database

    }

    private void viewVisitDetails() {
        // Implement logic to view visit details and prescriptions
    }

    private void editVisitDetails() {
        // Implement logic to edit visit details and prescriptions
    }

    private void assignNewDoctor() {
        // Implement logic to assign a new doctor to a patient
    }

    private void viewAllPatients() {
        // Implement logic to view all patients
    }

    private void sendMessage() {
        // Implement logic to send a message to an admin/receptionist
    }



    // Method to fetch patients for the doctor from the database
    private List<Patient> fetchDoctorPatients(Doctor doctor) {
        List<Patient> patients = new ArrayList<>();
        // Implement database query to fetch patients for the doctor
        return patients;
    }

    // Method to display appointments in a UI component
    private void displayAppointments() {
        // Implement code to display appointments
    }

    // Method to display patients in a UI component
    private void displayPatients(List<Patient> patients) {
        // Implement code to display patients
    }

    // Method to perform logout action
    private void logout() {
        // Set the DoctorDashboard invisible
        setVisible(false);

        // Close the current dashboard window if it's visible
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null && window instanceof JFrame) {
            window.dispose();
        }

        // Open the login page
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}
