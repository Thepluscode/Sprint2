package gp_booking_system;

import java.sql.SQLException;

/**
 * Represents a custom exception for database connection errors.
 * Extends RuntimeException to indicate unchecked exceptions.
 */
public class DatabaseConnectionException extends RuntimeException {

    /**
     * Constructs a new DatabaseConnectionException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public DatabaseConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs a new DatabaseConnectionException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the Throwable.getCause() method).
     */
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public interface ConnectionWrapper {
        // Define methods that you need to mock
        void close() throws SQLException;
        // Add other methods as needed

        Object getConnection();
    }
}
