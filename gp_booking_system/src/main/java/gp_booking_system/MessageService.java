package gp_booking_system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.mysql.cj.protocol.Message;

/**
 * This class represents a service for managing messages in the system.
 */
public class MessageService {

    private Timer timer; // Declare timer as a class-level variable

    private static Patient patient; // Declare patient as a class-level variable

    /**
     * Sets the patient for the message service.
     * @param patient The patient to set.
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Sends a message to the specified recipient.
     * @param sender The sender of the message.
     * @param recipient The recipient of the message.
     * @param messageContent The content of the message.
     */
    public void sendMessage(String sender, String recipient, String messageContent) {
        // Establish database connection
        try (Connection connection = DatabaseManager.getConnection()) {
            // Prepare SQL statement to insert message into the database
            String insertQuery = "INSERT INTO messages (sender_id, recipient_id, message_content) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Set parameters for the SQL statement
                preparedStatement.setString(1, sender);
                preparedStatement.setString(2, recipient);
                preparedStatement.setString(3, messageContent);

                // Execute the update statement
                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Message sent successfully.");
                } else {
                    System.out.println("Failed to send message.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches messages for the specified patient.
     * @param patientId The ID of the patient.
     * @return A list of messages for the specified patient.
     */
    public static List<Message> fetchMessagesForPatient(String patientId) {
        List<Message> messages = new ArrayList<>();
        // Establish database connection
        try (Connection connection = DatabaseManager.getConnection()) {
            // Prepare SQL query to select messages for the specified patient
            String selectQuery = "SELECT * FROM messages WHERE recipient_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                // Set parameter for the SQL statement
                preparedStatement.setString(1, patientId);

                // Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                // Iterate through the result set and populate the 'messages' list
                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    String senderId = resultSet.getString("sender_id");
                    String messageContent = resultSet.getString("message_content");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    // Create a new Message object and add it to the list
                    UserMessage message = new UserMessage(messageId, senderId, patientId, messageContent, timestamp);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Fetches new messages for the specified patient.
     * @param patientId The ID of the patient.
     * @return A list of new messages for the specified patient.
     */
    public static List<UserMessage> fetchNewMessagesForPatient(String patientId) {
        List<Message> allMessages = MessageService.fetchMessagesForPatient(patientId);
        List<UserMessage> newMessages = new ArrayList<>();

        // Assuming you have a way to determine if a message is new or not, for example, based on a timestamp
        for (Message message : allMessages) {
            if (((UserMessage) message).getTimestamp().isAfter(patient.getLastMessageCheckTime())) {
                newMessages.add((UserMessage) message);
            }
        }

        return newMessages;
    }

    /**
     * Sets up a message listener to periodically check for new messages.
     */
    private void setUpMessageListener() {
        // Set up a background thread or timer to periodically check for new messages/alerts from the system
        timer = new Timer(60000, new ActionListener() { // Check every minute
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check for new messages and notify the user
                checkForNewMessages();
            }
        });
        timer.start();
    }

    /**
     * Checks for new messages and notifies the user if any are found.
     */
    private void checkForNewMessages() {
        // Implement logic to check for new messages/alerts from the system
        List<UserMessage> newMessages = MessageService.fetchNewMessagesForPatient(patient.getPatientId());

        if (!newMessages.isEmpty()) {
            // Display a notification or alert to the user about the new messages
            JOptionPane.showMessageDialog(null, "You have new messages!", "New Messages", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
