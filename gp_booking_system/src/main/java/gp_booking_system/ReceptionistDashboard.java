package gp_booking_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboard extends JFrame {
    private Receptionist loggedInReceptionist;

    public ReceptionistDashboard(Receptionist receptionist) {
        super("Receptionist Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loggedInReceptionist = receptionist;

        JButton enterNewDoctorButton = new JButton("Enter New Doctor");
        enterNewDoctorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNewDoctor();
            }
        });

        JButton enterNewPatientButton = new JButton("Enter New Patient");
        enterNewPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNewPatient();
            }
        });

        JButton assignDoctorToPatientButton = new JButton("Assign Doctor to Patient");
        assignDoctorToPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignDoctorToPatient();
            }
        });

        JButton enterNewBookingButton = new JButton("Enter New Booking");
        enterNewBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterNewBooking();
            }
        });

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBookings();
            }
        });

        JButton removeOrRescheduleBookingButton = new JButton("Remove or Reschedule Booking");
        removeOrRescheduleBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeOrRescheduleBooking();
            }
        });

        JButton viewAllDoctorsButton = new JButton("View All Doctors");
        viewAllDoctorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllDoctors();
            }
        });

        JButton viewAllPatientsButton = new JButton("View All Patients");
        viewAllPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllPatients();
            }
        });

        JButton handleDoctorAvailabilityButton = new JButton("Add Doctor's Availability");
        handleDoctorAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDoctorAvailability();
            }
        });


        JPanel panel = new JPanel(new GridLayout(9, 1));
        panel.add(enterNewDoctorButton);
        panel.add(enterNewPatientButton);
        panel.add(assignDoctorToPatientButton);
        panel.add(enterNewBookingButton);
        panel.add(viewBookingsButton);
        panel.add(removeOrRescheduleBookingButton);
        panel.add(viewAllDoctorsButton);
        panel.add(viewAllPatientsButton);
        panel.add(handleDoctorAvailabilityButton);
        add(panel, BorderLayout.CENTER);

        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(); // Perform logout action
            }
        });
        add(logoutButton, BorderLayout.SOUTH);
    }

    private void enterNewDoctor() {
        // Logic to enter details for a new doctor
        // Example: Open a form to enter doctor details, then insert into the database
    }

    private void enterNewPatient() {
        // Logic to enter details for a new patient
        // Example: Open a form to enter patient details and assign a doctor, then insert into the database
    }

    private void assignDoctorToPatient() {
        // Logic to assign a doctor to a patient
        // Example: Open a form to select a patient and assign a doctor, then update the database
    }

    private void enterNewBooking() {
        // Logic to enter new booking
        // Example: Open a form to enter booking details, then insert into the database
    }

    private void viewBookings() {
        // Logic to view bookings
        // Example: Open a form to view bookings by doctor, patient, or date
    }

    private void removeOrRescheduleBooking() {
        // Logic to remove or reschedule a booking
        // Example: Open a form to select a booking and choose to remove or reschedule it, then update the database
    }

    private void viewAllDoctors() {
        // Logic to view all doctors
        // Example: Fetch all doctors from the database and display them in a list or table
    }

    private void viewAllPatients() {
        // Logic to view all patients
        // Example: Fetch all patients from the database and display them in a list or table
    }

    // Method to handle the Doctor Availability button click event
    private void handleDoctorAvailability() {
        DoctorAvalaibilityManager.showDoctorSelectionDialog();
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

