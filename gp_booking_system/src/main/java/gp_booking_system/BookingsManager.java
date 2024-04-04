package gp_booking_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingsManager {
    // Method to retrieve visit details for the given booking from the database
    public static VisitDetails getVisitDetailsForBooking(Booking booking) {
        VisitDetails visitDetails = null;
        String query = "SELECT * FROM visitDetails WHERE booking_no = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getBookingNo());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                visitDetails = new VisitDetails();
                visitDetails.setBookingNo(resultSet.getInt("booking_no"));
                visitDetails.setDoctorName(resultSet.getString("doctor_name"));
                visitDetails.setVisitDate(resultSet.getString("visit_date"));
                visitDetails.setVisitTime(resultSet.getString("visit_time"));
                visitDetails.setSymptoms(resultSet.getString("symptoms"));
                visitDetails.setDiagnosis(resultSet.getString("diagnosis"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visitDetails;
    }

    // Method to retrieve prescriptions for the given booking from the database
    public static List<Prescription> getPrescriptionsForBooking(Booking booking) {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM prescriptions WHERE booking_no = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, booking.getBookingNo());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Prescription prescription = new Prescription(query, query, query);
                prescription.setMedicineName(resultSet.getString("medicine"));
                prescription.setInstructions(resultSet.getString("instructions"));
                prescription.setDoctorName(resultSet.getString("doctor_name"));
                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prescriptions;
    }
}

