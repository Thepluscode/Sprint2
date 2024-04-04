package gp_booking_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Manages database connections and provides utility methods for common database operations.
 */
public class DatabaseManager {

    /** The URL of the PostgreSQL database. */
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/gp_booking_system";

    /** The username for accessing the database. */
    private static final String DB_USER = "postgres";

    /** The password for accessing the database. */
    private static final String DB_PASSWORD = "MY12database";

    /** The connection to the database. */
    private Connection connection;

    /**
     * Returns a connection to the database.
     *
     * @return The connection to the database.
     * @throws DatabaseConnectionException If an error occurs while establishing the database connection.
     */
    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to establish database connection", e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to close the database connection", e);
        }
    }

    public String generateId(String tableName, String prefix, Connection connection2) throws SQLException {
        String id = prefix;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1) + 1;
                id += String.format("%03d", count);
            }
        }
        return id;
    }

    /**
     * Generates an ID for a new record in the specified table.
     *
     * @param tableName   The name of the table.
     * @param prefix      The prefix for the ID.
     * @param connection2 The connection to the database.
     * @return The generated ID.
     * @throws
     /**
     * Retrieves a doctor from the database based on the specified name.
     *
     * @param selectedDoctor The name of the doctor to retrieve.
     * @param connection2    The connection to the database.
     * @return The Doctor object representing the retrieved doctor, or null if not found.
     */
    public Doctor getDoctor(String selectedDoctor, Connection connection2) {
        try {
            // Prepare the SQL query to retrieve the doctor based on the name
            String query = "SELECT * FROM doctors WHERE name = ?";

            // Create a prepared statement
            PreparedStatement preparedStatement = connection2.prepareStatement(query);

            // Set the parameter (selectedDoctor) in the query
            preparedStatement.setString(1, selectedDoctor);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a doctor with the given name exists
            if (resultSet.next()) {
                // Extract doctor information from the result set
                String doctorId = resultSet.getString("doctor_id");
                String name = resultSet.getString("name");
                // Add other attributes as needed

                // Create and return a Doctor object
                return new Doctor(doctorId, name); // Add other attributes as needed
            } else {
                // Doctor not found
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle database errors
            return null;
        }
    }

    // You can add more methods here for common database operations

    /**
     * Closes the database connection.
     *
     * @throws SQLException If an error occurs while closing the connection.
     */
    // You can add more methods here for common database operations

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

