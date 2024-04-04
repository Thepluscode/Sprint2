package gp_booking_system;

import javax.swing.*;

/**
 * The entry point of the GP Booking System application.
 */
public class App {
    public static void main(String[] args) {

        // Invoke the Swing event dispatch thread to ensure thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Start the application by creating and displaying the login page
                LoginPage loginPage = new LoginPage();
                loginPage.setVisible(true);
            }
        });
    }
}
