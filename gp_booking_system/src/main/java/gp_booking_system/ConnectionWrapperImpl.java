package gp_booking_system;

import java.sql.Connection;
import java.sql.SQLException;

import gp_booking_system.DatabaseConnectionException.ConnectionWrapper;

/**
 * Implementation of the ConnectionWrapper interface.
 */
public class ConnectionWrapperImpl implements ConnectionWrapper {
    private Connection connection;

    /**
     * Constructor for ConnectionWrapperImpl.
     * @param connection The connection to wrap.
     */
    public ConnectionWrapperImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Closes the wrapped connection.
     * @throws SQLException if a database access error occurs.
     */
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Returns the wrapped connection.
     * This method is not implemented in this class.
     * @return The wrapped connection.
     * @throws UnsupportedOperationException if the method is called.
     */
    @Override
    public Object getConnection() {
        throw new UnsupportedOperationException("Unimplemented method 'getConnection'");
    }

    // Implement other methods
}
