package gp_booking_system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

/**
 * Utility class for logging user sessions and displaying session details.
 */
public class Sessions {

    /**
     * Logs user access to the system.
     * @param userId The ID of the user accessing the system.
     * @param name The name of the user accessing the system.
     * @param functionalityAccessed The functionality accessed by the user.
     * @param name2 Unused parameter (should be removed if not needed).
     * @param functionalityAccessed2 Unused parameter (should be removed if not needed).
     */
    public static void logAccess(String userId, String name, String functionalityAccessed, String name2, String functionalityAccessed2) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database")) {
            // Format the current date and time
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
            String formattedTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            // Convert the formatted date and time to java.sql.Date and java.sql.Time objects
            Date sqlDate = Date.valueOf(formattedDate);
            Time sqlTime = Time.valueOf(formattedTime);

            // Prepare the SQL statement
            String sql = "INSERT INTO access_logs (user_id, patient_name, functionality_accessed, access_date, access_time) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, userId);
                preparedStatement.setString(3, functionalityAccessed);
                preparedStatement.setDate(4, sqlDate); // Set the date
                preparedStatement.setTime(5, sqlTime); // Set the time
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection or query execution errors
        }
    }

    /**
     * Displays session details for the specified user.
     * @param userId The ID of the user for whom session details are to be displayed.
     */
    public static void displaySessionDetails(String userId) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gp_booking_system", "postgres", "MY12database")) {
            String sql = "SELECT patient_name, functionality_accessed, access_date, access_time FROM access_logs WHERE user_id = ? ORDER BY access_date DESC, access_time DESC LIMIT 1 OFFSET 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString("patient_name");
                    String functionalityAccessed = resultSet.getString("functionality_accessed");
                    String loginDate = resultSet.getString("access_date");
                    String loginTime = resultSet.getString("access_time");

                    // Construct the message to display
                    String message = "Welcome back, " + name + "\n"
                    + "Functionality accessed was " + functionalityAccessed + "\n"
                    + "Your last login was on.\n"
                    + "Date: " + loginDate + "\n"
                    + "Time: " + loginTime;

                    // Display the message to the user
                    JOptionPane.showMessageDialog(null, message, "Session Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // No session details found for the user
                    // Uncomment the line below if logging this condition is necessary
                    // System.out.println("No session details found for user ID: " + userId);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection or query execution errors
        }
    }
}
