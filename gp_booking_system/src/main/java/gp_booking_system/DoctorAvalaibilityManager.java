package gp_booking_system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorAvalaibilityManager {
    private static JFrame frame;
    private static JComboBox<String> doctorList;
    private static String doctorId;

    // Method to fetch the list of doctors from the database
private static Map<String, String> fetchDoctorList() {
    Map<String, String> doctorMap = new HashMap<>();
    // Add logic to fetch doctor names and IDs from the database
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("SELECT doctor_id, name FROM doctors")) {
        while (resultSet.next()) {
            doctorMap.put(resultSet.getString("name"), resultSet.getString("doctor_id"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Handle SQLException
    }
    return doctorMap;
}

// Method to display the doctor selection dialog
public static void showDoctorSelectionDialog() {
    Map<String, String> doctorMap = fetchDoctorList();
    String[] doctorArray = doctorMap.keySet().toArray(new String[0]);
    doctorList = new JComboBox<>(doctorArray);

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("Select a doctor:"));
    panel.add(doctorList);

    int result = JOptionPane.showConfirmDialog(null, panel, "Select Doctor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
        String selectedDoctorName = (String) doctorList.getSelectedItem();
        String selectedDoctorId = doctorMap.get(selectedDoctorName);
        showDateTimePrompt(selectedDoctorName, selectedDoctorId); // Pass both doctor name and ID
    }
}
    // Method to display the date and time prompt
    private static void showDateTimePrompt(String doctorName, String doctorId) {
        JTextField dateField = new JTextField(10);
        JTextField startTimeField = new JTextField(5);
        JTextField endTimeField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Start Time (HH:MM:SS):"));
        panel.add(startTimeField);
        panel.add(new JLabel("End Time (HH:MM:SS):"));
        panel.add(endTimeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter Date and Time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();

            // Add logic to add doctor availability to the table
            addDoctorAvailability(date, startTime, endTime, doctorName, doctorId);
        }
    }

    // Method to add doctor availability to the table
    private static void addDoctorAvailability(String dateString, String startTimeString, String endTimeString, String doctorName, String doctorId) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date date = new java.sql.Date(dateFormat.parse(dateString).getTime());

            // Parse start time and end time strings into java.sql.Time objects
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.sql.Time startTime = new java.sql.Time(timeFormat.parse(startTimeString).getTime());
            java.sql.Time endTime = new java.sql.Time(timeFormat.parse(endTimeString).getTime());

            // Insert availability into the "doctor_availability" table
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO doctor_availability (date, start_time, end_time, doctor_name, doctor_id) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setDate(1, date);
                preparedStatement.setTime(2, startTime);
                preparedStatement.setTime(3, endTime);
                preparedStatement.setString(4, doctorName);
                preparedStatement.setString(5, doctorId);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Doctor availability added successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding doctor availability.");
            }
        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error establishing connection or parsing date/time.");
        }
    }

    // Method to remove doctor availability from the table
    private static void removeDoctorAvailability(String selectedDoctor) {
        // Add logic to remove doctor availability from the table
        // Example: Delete availability from a "doctor_availability" table
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database");
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM doctor_availability WHERE doctor_name = ?")) {
            preparedStatement.setString(1, selectedDoctor);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Doctor availability removed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Doctor availability not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle SQLException
            JOptionPane.showMessageDialog(null, "Error removing doctor availability.");
        }
    }

   /*  public static void main(String[] args) {
        // Example usage: Show doctor selection dialog
        showDoctorSelectionDialog();
    } */
}

