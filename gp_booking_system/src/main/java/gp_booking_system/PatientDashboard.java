package gp_booking_system;

import javax.mail.internet.ParseException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.Message;

import gp_booking_system.DatabaseConnectionException.ConnectionWrapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Theophilus Ogieva* login: tio5,
 * Represents the Patient Dashboard GUI.
 * This class provides functionalities for the patient to interact with the system.
 */
public class PatientDashboard extends JFrame {

    private Patient patient;
    private boolean isAvailable;
    private Object Patient;
    private String userId;
    private ConnectionWrapper connectionWrapper;

    /**
     * Constructs a PatientDashboard object.
     *
     * @param loggedInPatient The logged-in patient.
     */
    public PatientDashboard(Patient loggedInPatient) {
        super("Patient Dashboard");

        // Ensure that loggedInPatient is not null
        if (loggedInPatient == null) {
            throw new IllegalArgumentException("Logged-in patient cannot be null.");
        }

        // Fetch the patient's ID from the database
        fetchPatientId(loggedInPatient.getPatientId());
        this.patient = loggedInPatient;
        this.connectionWrapper = connectionWrapper;

        // Configure JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Labels to display system information and patient details
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel systemLabel = new JLabel("GP Booking System - Patient Dashboard");
        systemLabel.setHorizontalAlignment(JLabel.CENTER);
        systemLabel.setFont(getFont());
        JLabel welcomeLabel = new JLabel("Welcome: " + loggedInPatient.getName());
        JLabel roleLabel = new JLabel("Role: " + loggedInPatient.getRole());
        JLabel patientIdLabel = new JLabel("Patient ID: " + loggedInPatient.getPatientId());
        JLabel genderLabel = new JLabel("Gender: " + loggedInPatient.getGender());

        // Add labels to the header panel
        headerPanel.add(systemLabel, BorderLayout.PAGE_START);
        headerPanel.add(welcomeLabel, BorderLayout.LINE_START);
        headerPanel.add(roleLabel, BorderLayout.LINE_END);
        headerPanel.add(patientIdLabel, BorderLayout.PAGE_END);
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
        JButton changeDoctorButton = createButton("Change Doctor", e -> changeDoctor(e));
        JButton arrangeBookingButton = createButton("Arrange Booking", e -> arrangeBooking(e));
        JButton viewBookingsButton = createButton("View Bookings", e -> viewBookings(e));
        JButton rescheduleBookingButton = createButton("Reschedule Booking", e -> rescheduleBooking(e));
        JButton viewVisitDetailsButton = createButton("View Visit Details and Prescriptions", e -> viewVisitDetailsAndPrescriptions(e));
        JButton viewAllDoctorsButton = createButton("View All Doctors", e -> viewAllDoctors(e));
        JButton viewDoctorDetailsButton = createButton("View Doctor's Details", e -> viewDoctorDetails(e));
        JButton viewMessagesButton = createButton("View Messages", e -> displayMessages());
        JButton sendMessageButton = createButton("Send Message", e -> openMessageComposer(e));
        JButton logoutButton = createButton("Logout", e -> logout(e));

        JPanel panel = new JPanel(new GridLayout(10, 1));
        panel.add(viewMessagesButton);
        panel.add(sendMessageButton);
        panel.add(changeDoctorButton);
        panel.add(arrangeBookingButton);
        panel.add(viewBookingsButton);
        panel.add(rescheduleBookingButton);
        panel.add(viewVisitDetailsButton);
        panel.add(viewAllDoctorsButton);
        panel.add(viewDoctorDetailsButton);
        panel.add(logoutButton);
        add(panel, BorderLayout.CENTER);

        // Display session details
        Sessions.displaySessionDetails(userId);

    }

    // Method to log access from a user
     public static void logAccess(String patientId, String name, String functionalityAccessed) {
        Sessions.logAccess(patientId, name, functionalityAccessed, name, functionalityAccessed);
    }

    public void displayMessages() {
        // Fetch messages from the database for the logged-in patient
        List<Message> messages = fetchMessagesForPatient(patient.getPatientId());

        // Create a dialog or window to display the messages
        JFrame messageFrame = new JFrame("Messages");
        messageFrame.setLayout(new BorderLayout());
        messageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea messageArea = new JTextArea(20, 40);
        messageArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(messageArea);

        // Display messages in the text area
        for (Message message : messages) {
            messageArea.append(message.toString() + "\n"); // Assuming Message has a toString() method
        }

        messageFrame.add(scrollPane, BorderLayout.CENTER);
        messageFrame.pack();
        messageFrame.setLocationRelativeTo(null); // Center the frame on the screen
        messageFrame.setVisible(true);
    }


    public void openMessageComposer(ActionEvent e) {
        // Create a dialog for composing a message
        JDialog dialog = new JDialog(this, "Compose Message", true);
        dialog.setLayout(new BorderLayout());

        // Create components for composing message
        JLabel recipientLabel = new JLabel("Recipient:");
        JComboBox<String> recipientComboBox = new JComboBox<>(new String[]{"Doctor", "Receptionist"}); // Add more options if needed
        JTextArea messageTextArea = new JTextArea(10, 40);
        JButton sendButton = new JButton("Send");

        // Add components to dialog
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(recipientLabel);
        panel.add(recipientComboBox);
        panel.add(messageTextArea);
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(sendButton, BorderLayout.SOUTH);

        // Add action listener to send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected recipient and message content
                String sender = patient.getPatientId().toString();
                String recipient = (String) recipientComboBox.getSelectedItem();
                String messageContent = messageTextArea.getText();

                // Send message (Implement this part based on your requirements)
                // For example, you can call a method to send the message to the selected recipient
                sendMessage(sender, recipient, messageContent);

                // Close the dialog after sending the message
                dialog.dispose();
            }
        });

        // Set dialog properties
        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center the dialog on the parent frame
        dialog.setVisible(true);
    }

    public void sendMessage(String sender, String recipient, String messageContent) {
    // Implement logic to send the message to the specified recipient
    // For example, you can send the message to the recipient's email or store it in the database
    // Adjust the implementation based on your requirements
    MessageService messageService = new MessageService();
    messageService.sendMessage(sender, recipient, messageContent);

    }

    public List<Message> fetchMessagesForPatient(String patientId) {
        // Implement logic to fetch messages for the specified patient from the database
        // You may need to query the 'messages' table based on the patient ID
        // Populate the 'messages' list with UserMessage objects retrieved from the database
        return MessageService.fetchMessagesForPatient(patientId);
    }


    /**
     * Creates a button with the given label and action listener.
     *
     * @param label          The label of the button.
     * @param actionListener The action listener for the button.
     * @return The configured button.
     */
    public JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.addActionListener(actionListener);
        return button;
    }

    /**
     * Fetches the patient ID from the database based on the username.
     *
     * @param username The username of the logged-in patient.
     * @return The fetched patient ID.
     */
    public String fetchPatientId(String username) {
        String patientId = " ";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT patient_id FROM patients WHERE username = ?")) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patientId = resultSet.getString("patient_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return patientId;
    }

    /**
     * Opens a dialog to change the patient's doctor.
     *
     * @param e The action event.
     */
    public void changeDoctor(ActionEvent e) {
        // Display a dialog to allow the patient to select a new doctor from the list of all doctors
        Doctor newDoctor = displayDoctorSelectionDialog();

        // If a new doctor is selected, proceed with changing the doctor
        if (newDoctor != null) {
            // Set the patient ID (replace "expectedPatientId" with the actual patient ID)
            String patientId = "expectedPatientId";

            // Perform the necessary database operation to update the patient's doctor
            boolean doctorChanged = updatePatientDoctor(patientId, newDoctor);

            // If the doctor is successfully changed, send confirmation messages
            if (doctorChanged) {
                sendConfirmationMessages(newDoctor);
                JOptionPane.showMessageDialog(null, "Doctor successfully changed.");
            } else {
                // Handle error if doctor change fails
                JOptionPane.showMessageDialog(null, "Failed to change doctor. Please try again.");
            }
        }
    }

    /**
     * Function to display a dialog for selecting a new doctor from the available doctors in the system.
     * @return
     */
    public Doctor displayDoctorSelectionDialog() {
        // Retrieve the list of all doctors from the database or any other source
        Doctor[] allDoctors = getAllDoctors();

        // If no doctors are available, display a message and return null
        if (allDoctors == null || allDoctors.length == 0) {
            JOptionPane.showMessageDialog(null, "No doctors available.");
            return null;
        }

        // Create an array to hold the names of all doctors
        String[] doctorNames = new String[allDoctors.length];
        for (int i = 0; i < allDoctors.length; i++) {
            doctorNames[i] = allDoctors[i].getName(); // Get doctor names
        }

        // Create a combo box to display the list of doctor names
        JComboBox<String> doctorComboBox = new JComboBox<>(doctorNames); // Use String array
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select Doctor:"));
        panel.add(doctorComboBox);

        // Show input dialog with the list of doctors
        int result = JOptionPane.showConfirmDialog(null, panel, "Select Doctor", JOptionPane.OK_CANCEL_OPTION);

        // If the user selects OK, return the selected doctor
        if (result == JOptionPane.OK_OPTION) {
            String selectedDoctorName = (String) doctorComboBox.getSelectedItem();
            for (Doctor doctor : allDoctors) {
                if (doctor.getName().equals(selectedDoctorName)) {
                    return doctor; // Return the doctor object
                }
            }
            // If no doctor matches the selected name, return null
            return null;
        } else {
            // If the user cancels, return null
            return null;
        }
    }

    /**
     * Function to update the patient's doctor in the database with the given ID.
     * @param patientId
     * @param newDoctor
     * @return
     */
    public boolean updatePatientDoctor(String patientId, Doctor newDoctor) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // Check if the newDoctor object is null
            if (newDoctor == null) {
                System.err.println("Error: The new doctor object is null.");
                return false;
            }

            // Prepare the SQL statement to update the patient's doctor
            String updateQuery = "UPDATE patients SET doctor_id = ?, doctor_name = ? WHERE patient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                // Set the parameters for the SQL statement
                preparedStatement.setString(1, newDoctor.getDoctorId());
                preparedStatement.setString(2, newDoctor.getName());
                preparedStatement.setString(3, patientId);

                // Execute the update statement
                int rowsUpdated = preparedStatement.executeUpdate();

                // Check if the update was successful
                boolean success = rowsUpdated > 0;
                if (!success) {
                    System.err.println("No rows were updated for patientId: " + patientId);
                }
                return success; // Returns true if rows were updated
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error updating patient's doctor: " + e.getMessage());
            return false; // Update failed
        }
    }

    /**
     * Function to retrieve all doctors from the database and store them as a list of Doctors objects.
     * @return A List of Doctors objects containing all doctors in the database.
     * @return
     */
    public Doctor[] getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection()) {
            // Prepare SQL query to select all doctors
            String query = "SELECT doctor_id, name FROM doctors";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Doctor objects
            while (resultSet.next()) {
                String doctorId = resultSet.getString("doctor_id");
                String doctorName = resultSet.getString("name");
                doctorList.add(new Doctor(doctorId, doctorName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception if necessary
        }

        // Convert list to array and return
        return doctorList.toArray(new Doctor[0]);
    }

    /**
     * Function to send confirmation messages to the patient and the new doctor about their appointment.
     * @param newDoctor
     */
    public void sendConfirmationMessages(Doctor newDoctor) {
        // Send confirmation email to the patient
        //sendConfirmationEmailToPatient(newDoctor, patientEmail);

        // Send confirmation email to the new doctor
        sendConfirmationEmailToDoctor(newDoctor);

        // Optionally, display a success message to the user
        JOptionPane.showMessageDialog(null, "Doctor changed successfully.");
    }


   // Function to send confirmation email to the patient
   public void sendConfirmationEmailToPatient(Doctor newDoctor, String patientEmail) {
    String subject = "Doctor Change Confirmation";
    String body = "Dear Patient,\n\nYour doctor has been successfully changed to " + newDoctor.getName() + ".\n\nSincerely,\nThe Hospital Team";

    // Call the EmailService to send the email to the patient
    EmailService.sendEmail(patientEmail, subject, body);
    }

    // Function to send confirmation email to the new doctor
    public void sendConfirmationEmailToDoctor(Doctor newDoctor) {
        String subject = "New Patient Assigned";
        String body = "Dear Dr. " + newDoctor.getName() + ",\n\nA new patient has been assigned to you.\n\nSincerely,\nThe Hospital Team";

        // Call the EmailService to send the email to the new doctor
        EmailService.sendEmail(newDoctor.getEmail(), subject, body);
    }

    /**
     * Opens a dialog to arrange a new booking.
     *
     * @param e The action event.
     */
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // Method to arrange booking
    public void arrangeBooking(ActionEvent e) {
        // Retrieve the patient's doctor from the database
        Doctor patientDoctor = getPatientDoctor(patient.getPatientId());

        if (patientDoctor == null) {
            JOptionPane.showMessageDialog(null, "You do not have a doctor assigned. Please contact your healthcare provider.");
            return;
        }

        // Prompt the user to enter the date and time for the booking
        try {
            showDateTimePrompt(patientDoctor);
        } catch (ParseException | java.text.ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    /**
     * Method to show date-time prompt for the booking
     * @param doctor
     * @throws ParseException
     * @throws java.text.ParseException
     */
    public void showDateTimePrompt(Doctor doctor) throws ParseException, java.text.ParseException {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        // Add text fields for date and time input
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField timeField = new JTextField("HH:MM");

        panel.add(new JLabel("Enter date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Enter time (HH:MM):"));
        panel.add(timeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Date and Time", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String dateString = dateField.getText();
            String timeString = timeField.getText();

            // Combine date and time strings
            String dateTimeString = dateString + " " + timeString;

            // Check if input matches the expected format
            if (isValidDateTime(dateTimeString)) {
                // Parse the input string to a Date object
                java.util.Date dateTime = dateFormat.parse(dateTimeString);

                // Check doctor's availability
                if (isDoctorAvailable(doctor.getDoctorId(), dateTime)) {
                    // Perform the booking and send confirmation messages
                    bookAppointment(patient.getName(), doctor.getName(), patient.getPatientId(), dateString, timeString);
                    sendConfirmationMessages(doctor, dateTimeString);

                    JOptionPane.showMessageDialog(null, "Booking arranged successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "The doctor is not available at the chosen date and time. Please choose another slot.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter date and time in yyyy-MM-dd HH:mm format.");
                showDateTimePrompt(doctor);
            }
        }
    }

    /**
     * Method to check if the doctor is available at the given date and time and return true or false accordingly.
     * Validates whether the given datetime string is in the correct format.
     * @param doctorId
     * @param dateTime
     * @return
     */
    public boolean isDoctorAvailable(String doctorId, java.util.Date dateTime) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor_availability WHERE doctor_id = ? AND start_time <= ? AND end_time >= ?")) {
            preparedStatement.setString(1, doctorId);
            preparedStatement.setTime(2, new java.sql.Time(dateTime.getTime()));
            preparedStatement.setTime(3, new java.sql.Time(dateTime.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Return true if the doctor is available for the given date and time
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error checking doctor availability.");
            return false;
        }
    }

    // Method to check if the input string is a valid date and time in the expected format
    public boolean isValidDateTime(String dateTimeString) throws java.text.ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false); // Disable lenient parsing
        dateFormat.parse(dateTimeString);
        return true;
    }


    // Method to send confirmation messages to the patient and the doctor
    public void sendConfirmationMessages(Doctor newDoctor, String bookingDateTime) {
        // Retrieve the patient's email from the database or another source
        String patientEmail = patient.getEmail();

        // Send confirmation email to the patient
        if (patientEmail != null) {
            sendConfirmationEmailToPatient(newDoctor, bookingDateTime, patientEmail);
        } else {
            JOptionPane.showMessageDialog(null, "Patient email address not found. Unable to send confirmation email.");
        }

        // Send confirmation email to the doctor
        sendConfirmationEmailToDoctor(newDoctor, bookingDateTime);

        // Optionally, display a success message to the user
        JOptionPane.showMessageDialog(null, "Booking arranged successfully.");
    }

    // Method to send confirmation email to patient
    public void sendConfirmationEmailToPatient(Doctor newDoctor, String bookingDateTime, String patientEmail) {
        // Assuming you have access to an EmailService class for sending emails
        String subject = "Booking Confirmation";
        String body = "Dear Patient,\n\nYour appointment with Dr. " + newDoctor.getName() + " on " + bookingDateTime + " has been confirmed.\n\nSincerely,\nThe GP Booking System Team";

        // Call the EmailService to send the email to the patient
        EmailService.sendEmail(patientEmail, subject, body);
    }

    // Method to send confirmation email to doctor
    public void sendConfirmationEmailToDoctor(Doctor newDoctor, String bookingDateTime) {
         // Doctor's email is stored in the Doctor object
        String doctorEmail = newDoctor.getEmail();
        if (doctorEmail != null) {
            String subject = "New Appointment Scheduled";
            String body = "Dear Dr. " + newDoctor.getName() + ",\n\nYou have a new appointment scheduled on " + bookingDateTime + ".\n\nSincerely,\nThe GP Booking System Team";

            // Call the EmailService to send the email to the doctor
            EmailService.sendEmail(doctorEmail, subject, body);
        } else {
            JOptionPane.showMessageDialog(null, "Doctor email address not found. Unable to send confirmation email to doctor.");
        }
    }

    /**
     * Method to book appointment with a specific Doctor in a specific Date and Time.
     * @param patientName
     * @param doctorName
     * @param patientId
     * @param dateString
     * @param timeString
     * @throws ParseException
     */
    public void bookAppointment(String patientName, String doctorName, String patientId, String dateString, String timeString) throws ParseException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bookings (patient_name, doctor_name, patient_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, patientName);
            preparedStatement.setString(2, doctorName);
            preparedStatement.setString(3, patientId);

            // Convert dateString to java.sql.Date
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            preparedStatement.setDate(4, sqlDate);

            // Convert timeString to java.sql.Time
            java.sql.Time sqlTime = java.sql.Time.valueOf(timeString + ":00");
            preparedStatement.setTime(5, sqlTime);

            preparedStatement.executeUpdate();
        } catch (SQLException | java.text.ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error booking appointment.");
        }
    }

    /**
     * Method to retrieve patient's doctor information from database.
     * @param patientId
     * @return
     */
    Doctor getPatientDoctor(String patientId) {
        Doctor doctor = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT doctor_id FROM patients WHERE patient_id = ?")) {
            preparedStatement.setString(1, patientId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String doctorId = resultSet.getString("doctor_id");
                    doctor = getDoctor(doctorId);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching patient's doctor information.");
        }
        return doctor;
    }

    /**
     * Returns the list of all doctors in the system.
     * @param doctorId
     * @return
     */
    public Doctor getDoctor(String doctorId) {
        Doctor doctor = null;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctors WHERE doctor_id = ?")) {
            preparedStatement.setString(1, doctorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    doctor = new Doctor(doctorId, name);
                    doctor.setDoctorId(doctorId);
                    doctor.setName(name);
                    doctor.setEmail(email);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching doctor information.");
        }
        return doctor;
    }

    /**
     * Opens a dialog to view the patient's bookings.
     *
     * @param e The action event.
     */
    public void viewBookings(ActionEvent e) {
        String inputMonthYear = JOptionPane.showInputDialog(null, "Enter month and year (MM/YYYY):");
        if (inputMonthYear == null || inputMonthYear.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid month and year (MM/YYYY).");
            return;
        }

        // Parse month and year from input
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(inputMonthYear));
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input format. Please enter month and year in MM/YYYY format.");
            return;
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Month is zero-based in Calendar, so add 1

        // Query to fetch bookings for the specified month and year
        String query = "SELECT * FROM bookings WHERE EXTRACT(MONTH FROM appointment_date) = ? AND EXTRACT(YEAR FROM appointment_date) = ? AND patient_id = ?";
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            if (connection == null) {
                throw new SQLException("Failed to obtain a database connection");
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, month);
                preparedStatement.setInt(2, year);
                preparedStatement.setString(3, patient.getPatientId());

                ResultSet resultSet = preparedStatement.executeQuery();

                // Create a table to display bookings
                JTable table = new JTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addColumn("Date");
                model.addColumn("Time");
                model.addColumn("Doctor");

                // Populate the table with booking data
                while (resultSet.next()) {
                    String date = resultSet.getString("appointment_date");
                    String time = resultSet.getString("appointment_time");
                    String doctor = resultSet.getString("doctor_name");
                    model.addRow(new Object[]{date, time, doctor});
                }


                // Display the table in a scrollable dialog
                JOptionPane.showMessageDialog((Component) e.getSource(), new JScrollPane(table), "Bookings for " + inputMonthYear, JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching bookings.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * Opens a dialog to reschedule a booking.
     *
     * @param e The action event.
     */
    public void rescheduleBooking(ActionEvent e) {
        // Code for rescheduling booking
    }

    // Assume you have a UI component like a JList to display bookings
    private JList<Booking> bookingList;

    // Method to get the selected booking from the UI
    public Booking getSelectedBooking() {
        // Get the index of the selected item in the JList
        int selectedIndex = bookingList.getSelectedIndex();

        // If no item is selected, return null
        if (selectedIndex == -1) {
            return null;
        }

        // Otherwise, return the selected booking
        return bookingList.getModel().getElementAt(selectedIndex);
    }

    /**
     * Opens a dialog to view visit details and prescriptions for past bookings.
     *
     * @param e The action event.
     */
    public void viewVisitDetailsAndPrescriptions(ActionEvent e) {
        // Get the selected booking
        Booking selectedBooking = getSelectedBooking(); // Assuming you have this method

        // If no booking is selected, display an error message
        if (selectedBooking == null) {
            displayErrorMessage("Please select a past booking."); // Assuming you have this method
            return;
        }

        // Retrieve visit details and prescriptions for the selected booking
        VisitDetails visitDetails = BookingsManager.getVisitDetailsForBooking(selectedBooking);
        List<Prescription> prescriptions = BookingsManager.getPrescriptionsForBooking(selectedBooking);

        // Display visit details and prescriptions
        displayVisitDetailsAndPrescriptions(visitDetails, prescriptions); // Assuming you have this method
    }

    /**
     * Method to display visit details and prescriptions in a pop-up window or frame.
     * @param visitDetails
     * @param prescriptions
     */
    public void displayVisitDetailsAndPrescriptions(VisitDetails visitDetails, List<Prescription> prescriptions) {
        // Check if visitDetails or prescriptions is null
        if (visitDetails == null || prescriptions == null) {
            System.out.println("Unable to display visit details and prescriptions. Data is null.");
            return;
        }

        // Display visit details
        System.out.println("Visit Details:");
        System.out.println("Booking ID: " + visitDetails.getBookingId());
        System.out.println("Doctor Name: " + visitDetails.getDoctorName());
        System.out.println("Visit Date: " + visitDetails.getVisitDate());
        System.out.println("Visit Time: " + visitDetails.getVisitTime());
        System.out.println("Symptoms: " + visitDetails.getSymptoms());
        System.out.println("Diagnosis: " + visitDetails.getDiagnosis());

        // Display prescriptions
        System.out.println("\nPrescriptions:");
        for (Prescription prescription : prescriptions) {
            System.out.println("Medicine: " + prescription.getMedicineName());
            System.out.println("Dosage: " + prescription.getDosage());
            System.out.println("Prescribing Doctor: " + prescription.getPrescribingDoctor());
            System.out.println();
        }
    }

    public void displayErrorMessage(String message) {

        // You can show a message dialog with the provided message
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Displays information about all doctors in the system.
     *
     * @param e The action event.
     * @return A message containing information about all doctors.
     */
    public String viewAllDoctors(ActionEvent e) {
        StringBuilder doctorsInfo = new StringBuilder();
        try {
            // Connect to the database
            Connection connection = DatabaseManager.getConnection();
            if (connection != null) {
                // Create a statement
                Statement statement = connection.createStatement();

                // Execute the query to fetch all doctors
                ResultSet resultSet = statement.executeQuery("SELECT * FROM doctors");

                // Iterate over the result set
                boolean hasDoctors = false;
                while (resultSet.next()) {
                    hasDoctors = true;
                    // Retrieve doctor information
                    String name = resultSet.getString("name");
                    String phoneNumber = resultSet.getString("mobile");

                    // Append the doctor information to the StringBuilder
                    doctorsInfo.append("Name: ").append(name).append(", Phone Number: ").append(phoneNumber).append("\n");
                }

                // Close the statement and result set
                resultSet.close();
                statement.close();

                if (!hasDoctors) {
                    // Return a message indicating that no doctors are available or found
                    JOptionPane.showMessageDialog(null, "No doctors are available or found.", "Doctors Information", JOptionPane.INFORMATION_MESSAGE);
                    return "No Doctors Available";
                }
            } else {
                // Handle the case where connection is null
                JOptionPane.showMessageDialog(null, "Error connecting to the database.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Error connecting to the database";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Log the exception for debugging purposes
            JOptionPane.showMessageDialog(null, "Error fetching doctors information: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "Error  executing SQL query: " + ex.getMessage();
        }

        // Display the fetched doctor information in a message dialog
        JOptionPane.showMessageDialog(null, doctorsInfo.toString(), "Doctors Information", JOptionPane.INFORMATION_MESSAGE);
        return doctorsInfo.toString();
    }

    /**
     * Opens a dialog to view details of a specific doctor.
     *
     * @param e The action event.
     */
    private void viewDoctorDetails(ActionEvent e) {
        // Code for viewing doctor details
    }

    /**
     * Logs out the patient and returns to the login page.
     *
     * @param event The action event.
     */
    private void logout(ActionEvent event) {
        // Set the PatientDashboard invisible
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

    // Method to set the database connection
    public void setConnection(Connection connection) {
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    // Implement methods from Message interface
    public byte[] getByteBuffer() {
        // If this method is not needed, you can return null
        return null;
    }

    public int getPosition() {
        // If this method is not needed, you can return a default value
        return 0;
    }

}
