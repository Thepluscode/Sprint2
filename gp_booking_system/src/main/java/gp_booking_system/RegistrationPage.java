package gp_booking_system;

import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class RegistrationPage extends JFrame {
    public final JTextField nameField;
    public final JTextField ageField;
    public final JComboBox<String> genderComboBox;
    public JTextField mobileField;
    public final JTextField emailField;
    public final JTextField addressField;
    public final JTextField usernameField;
    public final JPasswordField passwordField;
    public final JComboBox<String> roleComboBox;
    public final JComboBox<String> doctorComboBox;
    public final JTextField specialisationField;
    private String selectedDoctor;
    private JButton registerButton;
    private GraphicsConfiguration patientId;
    private Object name;

    /**
     * Constructor for RegistrationPage class.
     */
    public RegistrationPage() {
        super("Registration Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Header
        JPanel headerPanel = new JPanel();
        JLabel headerLabel = new JLabel("GP Booking System");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerPanel.add(headerLabel);

        // Footer
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("Welcome to the GP Booking System");
        footerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        footerPanel.add(footerLabel);

        // Main panel for form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Create fields and add them to the form panel.

        // Name
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        nameField = new JTextField(50);
        nameField.setPreferredSize(new Dimension(nameField.getPreferredSize().width, 50));
        formPanel.add(nameField, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(ageLabel, gbc);
        gbc.gridx = 1;
        ageField = new JTextField(30);
        ageField.setPreferredSize(new Dimension(ageField.getPreferredSize().width, 50));
        formPanel.add(ageField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(genderLabel, gbc);
        gbc.gridx = 1;
        String[] genders = {"Female", "Male"};
        genderComboBox = new JComboBox<>(genders);
        formPanel.add(genderComboBox, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel contactInfoLabel = new JLabel("Address:");
        contactInfoLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(contactInfoLabel, gbc);
        gbc.gridx = 1;
        addressField = new JTextField(50);
        addressField.setPreferredSize(new Dimension(addressField.getPreferredSize().width, 50));
        formPanel.add(addressField, gbc);

        // Mobile
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(mobileLabel, gbc);
        gbc.gridx = 1;
        mobileField = new JTextField(30);
        mobileField.setPreferredSize(new Dimension(mobileField.getPreferredSize().width, 50));
        formPanel.add(mobileField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(emailLabel, gbc);
        gbc.gridx = 1;
        emailField = new JTextField(50);
        emailField.setPreferredSize(new Dimension(emailField.getPreferredSize().width, 50));
        formPanel.add(emailField, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(50);
        usernameField.setPreferredSize(new Dimension(usernameField.getPreferredSize().width, 50));
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(50);
        passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, 50));
        formPanel.add(passwordField, gbc);

        // Role ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(roleLabel, gbc);
        gbc.gridx = 1;
        String[] roles = {"Select Role","Patient", "Receptionist", "Doctor"};
        roleComboBox = new JComboBox<>(roles);

        // Increase the height of the combo box
        Dimension comboBoxDimension = roleComboBox.getPreferredSize();
        comboBoxDimension.height = 30; // Set the desired height
        roleComboBox.setPreferredSize(comboBoxDimension);
        formPanel.add(roleComboBox, gbc);

        // Specialization Field
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel specialisationLabel = new JLabel("Specialisation:");
        specialisationLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(specialisationLabel, gbc);
        gbc.gridx = 1;
        specialisationField = new JTextField(50);
        specialisationField.setPreferredSize(new Dimension(specialisationField.getPreferredSize().width, 50));
        formPanel.add(specialisationField, gbc);
        specialisationLabel.setVisible(false);
        specialisationField.setVisible(false);

        // Doctor ComboBox
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        formPanel.add(doctorLabel, gbc);
        gbc.gridx = 1;
        doctorComboBox = new JComboBox<>();
        formPanel.add(doctorComboBox, gbc);
        doctorLabel.setVisible(false);
        doctorComboBox.setVisible(false);

        // Register button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton registerButton = new JButton("Register Button");
        registerButton.setFont(registerButton.getFont().deriveFont(Font.PLAIN, 30)); // Set the desired font size here
        formPanel.add(registerButton, gbc);


        // Login link button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginLinkButton = new JButton("Already have an account? Login");
        loginLinkButton.setForeground(Color.BLUE);
        loginLinkButton.setBorderPainted(false);
        loginLinkButton.setContentAreaFilled(false);
        loginLinkButton.setFocusPainted(false);
        loginLinkButton.setOpaque(false);
        loginLinkButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        formPanel.add(loginLinkButton, gbc);

        // Add panels to the frame
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(footerPanel, BorderLayout.SOUTH);

        // Role ComboBox action listener
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if (selectedRole.equals("Patient")) {
                    doctorLabel.setVisible(true);
                    doctorComboBox.setVisible(true);
                    specialisationLabel.setVisible(false);
                    specialisationField.setVisible(false);
                } else if (selectedRole.equals("Doctor")) {
                    doctorLabel.setVisible(false);
                    doctorComboBox.setVisible(false);
                    specialisationLabel.setVisible(true);
                    specialisationField.setVisible(true);
                } else {
                    doctorLabel.setVisible(false);
                    doctorComboBox.setVisible(false);
                    specialisationLabel.setVisible(false);
                    specialisationField.setVisible(false);
                }
            }
        });

        // Populate doctorComboBox when the role is set to "Patient"
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                if (selectedRole.equals("Patient")) {
                    populateDoctorComboBox();
                }
            }
        });

        // Register button action listener...
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if any required field is empty...
                if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || emailField.getText().isEmpty() ||
                        addressField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                        passwordField.getPassword().length == 0 || (specialisationField.isVisible() && specialisationField.getText().isEmpty()) ||
                        (roleComboBox.getSelectedItem().equals("Patient") && doctorComboBox.getSelectedIndex() == -1)) {
                    JOptionPane.showMessageDialog(null, "Please complete all fields.");
                    return; // Stop registration process
                }

                // If all required fields are filled, proceed with registration...
                registerUser();
            }
        });

        // Action listener for login link button
        loginLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPage();
            }
        });
    }

    /**
     * Getter for the registerButton.
     */
    public JButton getRegisterButton() {
        if (registerButton == null) {
            registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Check if any required field is empty...
                    if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || emailField.getText().isEmpty() ||
                            addressField.getText().isEmpty() || usernameField.getText().isEmpty() ||
                            passwordField.getPassword().length == 0 || (specialisationField.isVisible() && specialisationField.getText().isEmpty()) ||
                            (roleComboBox.getSelectedItem().equals("Patient") && doctorComboBox.getSelectedIndex() == -1)) {
                        JOptionPane.showMessageDialog(null, "Please complete all fields.");
                        return; // Stop registration process
                    }

                    // If all required fields are filled, proceed with registration...
                    registerUser();
                }
            });
        }
        return registerButton;
    }

    /**
     * Method to populate the doctorComboBox with the list of all doctors.
     */
    private void populateDoctorComboBox() {
        doctorComboBox.removeAllItems(); // Clear existing items
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT doctors.name FROM doctors")) {
            while (resultSet.next()) {
                doctorComboBox.addItem(resultSet.getString("name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to handle user registration.
     */
    public boolean registerUser() {
        // Get input values from the form
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String gender = (String) genderComboBox.getSelectedItem();
        String email = emailField.getText();
        String address = addressField.getText();
        String mobile = mobileField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String specialisation = specialisationField.getText();
        String selectedDoctor = (String) doctorComboBox.getSelectedItem();

         // Get input values from the form
        String ageText = ageField.getText(); // Retrieve the age text from the age field

        // Add debug output to check the value of ageText
        System.out.println("Age Text: [" + ageText + "]");

        // Validate age field is not empty
        if (ageText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter your age.");
            return false; // Registration failed
        }

        // Validate age
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                JOptionPane.showMessageDialog(null, "Age must be a positive integer.");
                return false; // Registration failed
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Age must be a valid integer.");
            return false; // Registration failed
        }

        // Validate name
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a name.");
            return false; // Registration failed
        }

        // Validate email
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid email address.");
            return false; // Registration failed
        }

        // Validate mobile
        if (!isValidMobile(mobile)) {
            JOptionPane.showMessageDialog(null, "Please enter a valid mobile phone number.");
            return false; // Registration failed
        }


        // Hash the password
        String hashedPassword = hashPassword(password);

        boolean registrationSuccessful = false;

        try (Connection connection = DatabaseManager.getConnection()) {

            // Insert data into the appropriate table based on the user's role
            if (role.equals("Doctor")) {
                // Insert data into the doctors table
                String doctorId = generateId("doctors", "Doc", connection);
                PreparedStatement insertDoctorStatement = connection.prepareStatement("INSERT INTO doctors (doctor_id, name, age, gender, address, mobile, email, specialisation, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                insertDoctorStatement.setString(1, doctorId);
                insertDoctorStatement.setString(2, name);
                insertDoctorStatement.setInt(3, age);
                insertDoctorStatement.setString(4, gender);
                insertDoctorStatement.setString(5, address);
                insertDoctorStatement.setString(6, mobile);
                insertDoctorStatement.setString(7, email);
                insertDoctorStatement.setString(8, specialisation);
                insertDoctorStatement.setString(9, username);
                insertDoctorStatement.setString(10, hashedPassword);
                insertDoctorStatement.setString(11, role); // Add role information
                insertDoctorStatement.executeUpdate();
                insertDoctorStatement.close();
            } else if (role.equals("Patient")) {
                // Insert data into the patients table
                String patientId = generateId("patients", "Pat", connection);
                Doctor doctor = getDoctor(selectedDoctor, connection);
                if (doctor != null) {
                    String doctorId = doctor.getDoctorId();
                    String doctorName = doctor.getName();
                    PreparedStatement insertPatientStatement = connection.prepareStatement("INSERT INTO patients (patient_id, name, age, gender, address, mobile, email, doctor_id, doctor_name, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    insertPatientStatement.setString(1, patientId);
                    insertPatientStatement.setString(2, name);
                    insertPatientStatement.setInt(3, age);
                    insertPatientStatement.setString(4, gender);
                    insertPatientStatement.setString(5, address);
                    insertPatientStatement.setString(6, mobile);
                    insertPatientStatement.setString(7, email);
                    insertPatientStatement.setString(8, doctorId);
                    insertPatientStatement.setString(9, doctorName);
                    insertPatientStatement.setString(10, username);
                    insertPatientStatement.setString(11, hashedPassword);
                    insertPatientStatement.setString(12, role); // Add role information
                    insertPatientStatement.executeUpdate();
                    insertPatientStatement.close();

                    registrationSuccessful = true; // Set registrationSuccessful to true after successful registration

                    // Get the email of the patient
                    String patientEmail = email;

                    // Send confirmation emails to patient and doctor
                    sendConfirmationEmailToPatient(name, patientEmail);

                    // Send confirmation email to doctor
                    sendConfirmationEmailToDoctor(doctorName, doctor.getEmail());

                    // Send alert message to patient
                    sendAlertMessage(patientEmail);

                    // Send alert message to doctor
                    sendAlertMessage(doctor.getEmail());
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Selected doctor not found.");
                    return registrationSuccessful;
                }
            } else if (role.equals("Receptionist")) {
                // Insert data into the receptionists table
                String receptionistId = generateId("receptionists", "Rec", connection);
                PreparedStatement insertReceptionistStatement = connection.prepareStatement("INSERT INTO receptionists (receptionist_id, name, age, gender, address, mobile, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                insertReceptionistStatement.setString(1, receptionistId);
                insertReceptionistStatement.setString(2, name);
                insertReceptionistStatement.setInt(3, age);
                insertReceptionistStatement.setString(4, gender);
                insertReceptionistStatement.setString(5, address);
                insertReceptionistStatement.setString(6, mobile);
                insertReceptionistStatement.setString(7, email);
                insertReceptionistStatement.setString(8, username);
                insertReceptionistStatement.setString(9, hashedPassword);
                insertReceptionistStatement.setString(10, role); // Add role information
                insertReceptionistStatement.executeUpdate();
                insertReceptionistStatement.close();
            }
            // If registration process completes without errors
            registrationSuccessful = true;

            JOptionPane.showMessageDialog(null, "Registration successful. You can now login.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
        }
        return registrationSuccessful;
    }

    /**
     * Method to check if registration was successful.
     */
    public boolean isRegistrationSuccessful() {
        return registerUser();
    }

    /**
     * Returns the number of rows affected by executing an update or delete operation.
     * @param tableName
     * @param prefix
     * @param connection
     * @return
     * @throws SQLException
     */
    private String generateId(String tableName, String prefix, Connection connection) throws SQLException {
        String id = prefix;
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1) + 1;
            id += String.format("%03d", count);
        }
        resultSet.close();
        preparedStatement.close();
        return id;
    }

    /**
     *  Closes the database connection. This method is automatically called when the application shuts down.
     * @param password
     * @return
     */
    // Method to hash the password using bcrypt
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Method to open the login page and handle user authentication
     * @param request
     * @param response
     */
    private void openLoginPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
        dispose(); // Close the registration page
    }

    /**
     * Method to send confirmation email to the patient's registered email address
     * @param name
     * @param email
     */
    private void sendConfirmationEmailToPatient(String name, String email) {
        String subject = "Registration Confirmation";
        String messageText = "Dear " + name + ",\n\nThank you for registering with GP Booking System. Your registration was successful.\n\nBest regards,\nThe GP Booking System Team";

        sendEmail(email, subject, messageText);
    }

    /**
     * Method to send confirmation email to the doctor's registered email address
     * @param doctorName The name of the doctor
     * @param doctorEmail The email address of the doctor
     */
    private void sendConfirmationEmailToDoctor(String doctorName, String doctorEmail) {
        String subject = "New Patient Registration";
        String messageText = "Dear Dr. " + doctorName + ",\n\nA new patient has registered with GP Booking System. Please review the patient's details.\n\nBest regards,\nThe GP Booking System Team";

        sendEmail(doctorEmail, subject, messageText);
    }

    /**
     * Retrieves the doctor's information based on the given name.
     * @param doctorName The name of the doctor to retrieve
     * @param connection The database connection
     * @return The Doctor object if found, or null if not found
     */
    private Doctor getDoctor(String doctorName, Connection connection) {
        try {
            // Prepare the SQL query to retrieve the doctor based on the name
            String query = "SELECT * FROM doctors WHERE name = ?";
            // Create a prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set the parameter (doctorName) in the query
            preparedStatement.setString(1, doctorName);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Check if a doctor with the given name exists
            if (resultSet.next()) {
                // Extract doctor information from the result set
                String doctorId = resultSet.getString("doctor_id");
                // Add other attributes as needed
                // Create and return a Doctor object
                return new Doctor(doctorId, doctorName); // Add other attributes as needed
            } else {
                // Doctor not found
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle database errors
            return null;
        }
    }

    private void sendEmail(String recipientEmail, String subject, String messageText) {
        // Check if recipient email is null or empty
    if (recipientEmail == null || recipientEmail.isEmpty()) {
        System.err.println("Error: Recipient email address is null or empty.");
        return;
    }

    // Check if recipient email is valid
    if (!isValidEmail(recipientEmail)) {
        System.err.println("Error: Invalid recipient email address.");
        return;
    }
        // SMTP server configuration
        String host = "smtp.Gmail.com";
        String port = "587";
        String username = "ogievatheophilus@gmail.com";
        String password = "eqycpnpylevulpxu";

        // Set up properties for SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create a Session object
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(username));

            // Set To: header field
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Set the actual message
            message.setText(messageText);

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            // Print detailed error message
            System.err.println("Error occurred while sending email:");
            e.printStackTrace();
        }
    }


    // Method to send an alert message to the user's mobile number and the selected doctor's mobile number
    private void sendAlertMessage(String patientId) {
        // Retrieve the user's mobile number from the database
        String patientMobileNumber = getMobileNumber(patientId);

        // If mobile number is found, send the alert message to the patient
        if (patientMobileNumber != null) {
            String patientMessage = "Your registration was successful. Thank you!";
            sendSMS(patientMobileNumber, patientMessage);
        } else {
            System.out.println("Failed to send alert message to patient: Mobile number not found for user ID " + patientId);
        }

        // Retrieve the mobile number of the selected doctor
        String doctorMobileNumber = getDoctorMobileNumber(selectedDoctor);

        // If doctor's mobile number is found, send the alert message to the doctor
        if (doctorMobileNumber != null) {
            String doctorMessage = "A new patient has registered. Please review the details.";
            sendSMS(doctorMobileNumber, doctorMessage);
        } else {
            System.out.println("Failed to send alert message to doctor: Mobile number not found for doctor " + selectedDoctor);
        }
    }

    // Method to get the mobile number associated with the user from the database
    private String getMobileNumber(String patientId) {
        String mobileNumber = null;
        try (Connection connection = DatabaseManager.getConnection()) {
            // Establish connection and prepare the SQL statement
            String sql = "SELECT mobile FROM users WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patientId);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the mobile number if the user exists
            if (resultSet.next()) {
                mobileNumber = resultSet.getString("mobile");
            }

            // Close the ResultSet, PreparedStatement, and Connection
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mobileNumber;
    }

    // Method to get the mobile number of the selected doctor
    private String getDoctorMobileNumber(String selectedDoctor) {
        String mobileNumber = null;
        try (Connection connection = DatabaseManager.getConnection()) {
            // Establish connection and prepare the SQL statement
            String sql = "SELECT mobile FROM users WHERE name = ? AND role = 'Doctor'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedDoctor);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Retrieve the mobile number if the doctor exists
            if (resultSet.next()) {
                mobileNumber = resultSet.getString("mobile");
            }

            // Close the ResultSet, PreparedStatement, and Connection
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mobileNumber;
    }

    // Method to send an SMS alert to the given mobile number
    private void sendSMS(String mobileNumber, String message) {
        // Replace this block with your actual SMS sending code
        System.out.println("Sending SMS to " + mobileNumber + ": " + message);
    }

    // Method to validate email address
    private boolean isValidEmail(String email) {
        // Regular expression for validating email addresses
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate mobile phone number
    private boolean isValidMobile(String mobile) {
        // Regular expression for validating mobile numbers (allows only digits and optional leading plus sign)
        String mobileRegex = "^\\+?[0-9]+$";

        // Remove any whitespace or special characters from the mobile number
        String cleanMobile = mobile.replaceAll("[^0-9+]", "");

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(mobileRegex);

        // Check if the cleaned mobile number matches the pattern
        Matcher matcher = pattern.matcher(cleanMobile);
        return matcher.matches();
    }

}

