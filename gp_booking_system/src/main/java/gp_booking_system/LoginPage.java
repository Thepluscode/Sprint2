package gp_booking_system;

import org.mindrot.jbcrypt.BCrypt;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents the Login Page GUI.
 * This class provides functionalities for users to authenticate and login to the system.
 */
public class LoginPage extends JFrame {
    protected static final String String = null;
    public final JTextField usernameField;
    public final JPasswordField passwordField;
    public final JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JButton registerButton;
    private boolean isLoggedIn;
    private Doctor loggedInDoctor;
    private Patient loggedInPatient;
    private Receptionist loggedInReceptionist;
    private int loginAttempts;
    // Declare class-level fields for enteredUsername, enteredPassword, and selectedRole
    private String enteredUsername;
    private String enteredPassword;
    private String selectedRole;
    private java.lang.String patientId;
    private java.lang.String name;
    private java.lang.String userId;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public Doctor getLoggedInDoctor() {
        return loggedInDoctor;
    }

    public Patient getLoggedInPatient() {
        return loggedInPatient;
    }

    public Receptionist getLoggedInReceptionist() {
        return loggedInReceptionist;
    }

    /**
     * Constructor for LoginPage.
     * Initializes the login page GUI components.
     */
    public LoginPage() {
        super("Login Page");

        // Assuming login is successful
        String date = LocalDate.now().toString();
        String time = LocalTime.now().toString();
        storeSessionDetails(patientId, name, date, time);
        logAccess(patientId, name, "Patient Dashboard", date, time);

        // Configure JFrame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(true);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add border with 10-pixel padding

        // Create header panel
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("GP Booking System - Login");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerPanel.add(headerLabel);

        // Create footer panel
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2024 GP Booking System. All rights reserved.");
        footerPanel.add(footerLabel);

        // Creates and configures the form panel with login fields and buttons.
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 2, 5)); // Rows, Columns, Horizontal Gap, Vertical Gap
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("SelectRole:");
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(Font.PLAIN, 20));
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.PLAIN, 20));
        roleLabel.setFont(roleLabel.getFont().deriveFont(Font.PLAIN, 20));
        usernameField = new JTextField(20);
        usernameField.setFont(usernameField.getFont().deriveFont(Font.PLAIN, 20));
        passwordField = new JPasswordField(20);
        passwordField.setFont(passwordField.getFont().deriveFont(Font.PLAIN, 20));
        String[] roles = {"Doctor", "Receptionist", "Patient"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(roleComboBox.getFont().deriveFont(Font.PLAIN, 20));
        // Increase the height of the combo box
        roleComboBox.setPreferredSize(new Dimension(roleComboBox.getPreferredSize().height, 50)); // Adjust width as needed
        roleComboBox.setFont(roleComboBox.getFont().deriveFont(Font.PLAIN, 20));
        loginButton = new JButton("Login");
        loginButton.setFont(loginButton.getFont().deriveFont(Font.PLAIN, 20));
        registerButton = new JButton("Register");
        registerButton.setFont(registerButton.getFont().deriveFont(Font.PLAIN, 20));

        // Add components to the form panel
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        //formPanel.add(new JPanel()); // Placeholder for alignment
        formPanel.add(registerButton);
        //formPanel.add(new JPanel()); // Placeholder for alignment
        formPanel.add(loginButton);


        /// Add panels to the frame
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);


        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser(enteredUsername, enteredPassword, selectedRole);
            }
        });


        // Add action listener to the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationPage();
            }
        });
    }

    private void logAccess(java.lang.String patientId, java.lang.String name, java.lang.String string2,
            java.lang.String date, java.lang.String time) {
    }

    private void storeSessionDetails(java.lang.String patientId, java.lang.String name, java.lang.String date,
            java.lang.String time) {
    }

    /**
     * Authenticates the user based on entered credentials.
     *
     * @param username The entered username.
     * @param password The entered password.
     * @param role     The selected role.
     */
    public void authenticateUser(String username, String password, String role) {
        String enteredUsername = usernameField.getText();
        String enteredPassword = new String(passwordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();

        // Generate a random 6-digit number for confirmation
        String confirmationCode = generateConfirmationCode();

        // Send the confirmation code to the user's email
        sendConfirmationCodeToEmail(enteredUsername, confirmationCode, selectedRole);

        // Send the confirmation code to the user's email
        sendConfirmationCodeToMobile(enteredUsername, confirmationCode, selectedRole);

        // Prompt the user to enter the confirmation code
        String userInputCode = JOptionPane.showInputDialog(null, "Enter the 6-digit code sent to your email or mobile:");

        // Verify the entered code
        if (userInputCode != null && userInputCode.equals(confirmationCode)) {
            // Code verification successful, proceed with authentication
            try (Connection connection = DatabaseManager.getConnection()) {
                // Check if the username exists in the appropriate table based on the selected role
                String query = "SELECT * FROM " + selectedRole.toLowerCase() + "s WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, enteredUsername);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            // Retrieve hashed password from the database
                            String hashedPassword = resultSet.getString("password");

                            // Verify the entered password against the hashed password
                            if (BCrypt.checkpw(enteredPassword, hashedPassword)) {
                                // Authentication succeeded
                                isLoggedIn = true; // Update isLoggedIn flag
                                // Retrieve user information
                                String name = resultSet.getString("name");
                                int age = resultSet.getInt("age");
                                String gender = resultSet.getString("gender");
                                String address = resultSet.getString("address");
                                String mobile = resultSet.getString("mobile");
                                String email = resultSet.getString("email");
                                resultSet.getString("role");
                                String userId = "";

                                // Display session details
                                Sessions.displaySessionDetails(userId);


                                // Instantiate the appropriate user subclass based on the role
                                switch (selectedRole) {
                                    case "Doctor":
                                        // Retrieve additional doctor information if needed
                                        String specialisation = resultSet.getString("specialisation");
                                        String doctorId = resultSet.getString("doctor_id");
                                        userId = resultSet.getString("doctor_id");
                                        // Create a Doctor object
                                        loggedInDoctor = new Doctor(doctorId, name, age, gender, address, mobile, email, enteredUsername, enteredPassword, specialisation, role);
                                        // Open doctor dashboard
                                        openDoctorDashboard(loggedInDoctor);
                                        break;
                                    case "Receptionist":
                                        String receptionistId = resultSet.getString("receptionist_id");
                                        userId = resultSet.getString("receptionist_id");
                                        // Create a Receptionist object
                                        loggedInReceptionist = new Receptionist(receptionistId, name, age, gender, address, mobile, email, enteredUsername, enteredPassword, role);
                                        // Open receptionist dashboard
                                        openReceptionistDashboard(loggedInReceptionist);
                                        break;
                                    case "Patient":
                                        resultSet.getString("doctor_name");
                                        String patientId = resultSet.getString("patient_id");
                                        userId = resultSet.getString("patient_id");
                                        // Create a Patient object
                                        loggedInPatient = new Patient(patientId, name, age, gender, address, mobile, email, enteredUsername, enteredPassword, role);
                                        // Open patient dashboard if patient is not null
                                        if (loggedInPatient != null) {
                                            openPatientDashboard(loggedInPatient);
                                        }
                                        break;
                                    default:
                                        System.out.println("Invalid role.");
                                        break;
                                }

                                loginAttempts = 0; // Reset login attempts

                                 // Log the login functionality
                                 String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE); // Formats date as YYYY-MM-DD
                                 String time = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME); // Formats time as HH:MM:SS
                                logAccess(userId, name, date, time, "login");

                                // Display session details
                                Sessions.displaySessionDetails(userId);


                            } else {
                                // Authentication failed
                                handleAuthenticationFailure();
                            }
                        } else {
                            // Authentication failed
                            handleAuthenticationFailure();
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle database connection or query execution errors
            }
        } else {
            // Code verification failed, inform the user and handle accordingly
            JOptionPane.showMessageDialog(null, "Code verification failed. Please check the entered code.");

        }
    }

    /**
 * Generates a random 6-digit confirmation code.
 * @return The generated confirmation code.
 */
    private String generateConfirmationCode() {
        // Generate a random 6-digit number
        int min = 100000;
        int max = 999999;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return String.valueOf(randomNum);
    }

    /**
     * Sends the confirmation code to the user's email.
     *
     * @param username         The username of the user.
     * @param confirmationCode The confirmation code to send.
     * @param selectedRole     The selected role of the user.
     */
    private void sendConfirmationCodeToEmail(String username, String confirmationCode, String selectedRole) {
        String userEmail = getUserEmail(username, selectedRole);
        if (userEmail != null) {
            // Sender's email address
            final String senderEmail = "ogievatheophilus@gmail.com";
            // Sender's email password
            final String senderPassword = "eqycpnpylevulpxu";

            // SMTP server configuration for Gmail
            final String host = "smtp.gmail.com";
            final String port = "587";

            // Email properties
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);

            // Create a Session object with the sender's email and password
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            try {
                // Create a MimeMessage object
                Message message = new MimeMessage(session);

                // Set the sender and recipient email addresses
                message.setFrom(new InternetAddress(senderEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));

                // Set the email subject and body
                message.setSubject("Confirmation Code");
                message.setText("Your confirmation code is: " + confirmationCode);

                // Send the email
                Transport.send(message);

                System.out.println("Confirmation code sent to email " + userEmail + ": " + confirmationCode);
            } catch (MessagingException e) {
                System.out.println("Failed to send confirmation code to email " + userEmail + ": " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unable to retrieve user's email.");
        }
    }

    /**
     * Sends the confirmation code to the user's mobile.
     *
     * @param username         The username of the user.
     * @param confirmationCode The confirmation code to send.
     * @param selectedRole     The selected role of the user.
     */
    private void sendConfirmationCodeToMobile(String username, String confirmationCode, String selectedRole) {
        String userMobile = getUserMobile(username, selectedRole);
        if (userMobile != null) {
            // Send the confirmation code to the user's mobile
            // Replace this with your SMS sending logic
            System.out.println("Confirmation code sent to mobile " + userMobile + ": " + confirmationCode);
        } else {
            JOptionPane.showMessageDialog(null, "Unable to retrieve user's mobile.");
        }
    }

    /**
     * Retrieves the user's email based on the username and role.
     *
     * @param username The username of the user.
     * @param role     The role of the user.
     * @return The user's email, or null if not found.
     */
    private String getUserEmail(String username, String role) {
        String email = null;
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT email FROM " + role.toLowerCase() + "s WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        email = resultSet.getString("email");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection or query execution errors
        }
        return email;
    }

    /**
     * Retrieves the user's mobile number based on the username and role.
     *
     * @param username The username of the user.
     * @param role     The role of the user.
     * @return The user's mobile number, or null if not found.
     */
    private String getUserMobile(String username, String role) {
        String mobile = null;
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "SELECT mobile FROM " + role.toLowerCase() + "s WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        mobile = resultSet.getString("mobile");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database connection or query execution errors
        }
        return mobile;
    }

    /**
     * Handles authentication failure.
     * Displays appropriate messages and actions for failed login attempts.
     */
    private void handleAuthenticationFailure() {
        loginAttempts++;
        if (loginAttempts >= 3) {
            openRegistrationPage();
        } else {
            JOptionPane.showMessageDialog(null, "Authentication failed. Please check your credentials and role.");
        }
    }

    /**
     * Opens the doctor dashboard window.
     *
     * @param doctor The logged-in doctor.
     */
    private void openDoctorDashboard(Doctor doctor) {
        DoctorDashboard doctorDashboard = new DoctorDashboard(doctor);
        doctorDashboard.setVisible(true);
        dispose(); // Close the login page window
    }

    /**
     * Opens the receptionist dashboard window.
     *
     * @param receptionist The logged-in receptionist.
     */
    private void openReceptionistDashboard(Receptionist receptionist) {
        ReceptionistDashboard receptionistDashboard = new ReceptionistDashboard(receptionist);
        receptionistDashboard.setVisible(true);
        dispose(); // Close the login page window
    }

    /**
     * Opens the patient dashboard window.
     *
     * @param patient The logged-in patient.
     */
    private void openPatientDashboard(Patient patient) {
        PatientDashboard patientDashboard = new PatientDashboard(patient);
        // Display session details
       // Sessions.displaySessionDetails(userId);

        patientDashboard.setVisible(true);
        dispose(); // Close the login page window
    }

     /**
     * Opens the registration page window.
     */
    public void openRegistrationPage() {
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.setVisible(true);
    }
}
