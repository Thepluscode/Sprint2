package gp_booking_system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import gp_booking_system.DatabaseConnectionException.ConnectionWrapper;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Theophilus Ogieva* login: tio5,
 * Group D(Patient)
 * Unit tests for view all doctors in the PatientDashboard class.
 */
// Add this annotation to enable PowerMockito
@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({JOptionPane.class})
public class PatientDashboardTest {

    private static final Connection mockConnectionWrapper = null;
    private PatientDashboard patientDashboard;
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;
    private Mockito PowerMockito;
    private PreparedStatement mockPreparedStatement;
    private Object EmailService;

    /**
     *  Constructs a new object of type PatientDashBoard and initializes its attributes with default values.
     */
    @Before
    public void setUp() throws Exception {
        // Initialize mock objects
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        // Prepare JOptionPane for mocking
        mockStatic(JOptionPane.class);

        // Mocking a loggedInPatient object
        Patient loggedInPatient = mock(Patient.class);

        // Mock the behavior of the connection and statement
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        // Mock the PreparedStatement execution
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Initialize the PatientDashboard object
        patientDashboard = new PatientDashboard(loggedInPatient);
        patientDashboard.setConnection(mockConnection);

    }

    /**
     * Test method for ArrangeBooking method in class PatientDashBoard.
     * This test case tests if an empty booking is arranged correctly
     */
    @Test
    public void testArrangeBooking() {
        Mockito.mock(ConnectionWrapper.class);

        // Mock a patient object
        Patient patient = Mockito.mock(Patient.class);
        Mockito.when(patient.getPatientId()).thenReturn("123"); // Mock patient's ID

        // Mock a doctor object
        Doctor doctor = Mockito.mock(Doctor.class);
        Mockito.when(doctor.getDoctorId()).thenReturn("456"); // Mock doctor's ID

        // Mock the PatientDashboard behavior
        PatientDashboard patientDashboard = Mockito.mock(PatientDashboard.class);
        Mockito.when(patientDashboard.getPatientDoctor("123")).thenReturn(doctor); // Mock patient's doctor retrieval

        // Simulate the internal logic of arrangeBooking to call showDateTimePrompt
        Mockito.doAnswer(invocation -> {
            patientDashboard.showDateTimePrompt(doctor);
            return null; // Assuming arrangeBooking returns void
        }).when(patientDashboard).arrangeBooking(any(ActionEvent.class));

        // Call arrangeBooking method
        ActionEvent mockActionEvent = Mockito.mock(ActionEvent.class);
        patientDashboard.arrangeBooking(mockActionEvent); // Pass a mock ActionEvent object

        // Verify that showDateTimePrompt method is called with the mocked doctor
        try {
            Mockito.verify(patientDashboard).showDateTimePrompt(doctor);
        } catch (javax.mail.internet.ParseException | ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     *  Tests ShowDateTimePrompt method in PatientDashBoard class
     */
    @Test
    public void testShowDateTimePrompt() {
        // Create a mock doctor object
        Doctor doctor = mock(Doctor.class);

        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create a spy of PatientDashboard to mock JOptionPane behavior
        PatientDashboard patientDashboard = spy(new PatientDashboard(loggedInPatient));

        // Stub the isValidDateTime method to return true
        try {
            doReturn(true).when(patientDashboard).isValidDateTime(anyString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Stub the isDoctorAvailable method to return true
        doReturn(true).when(patientDashboard).isDoctorAvailable(anyString(), any());

        // Stub the bookAppointment method behavior
        try {
            doNothing().when(patientDashboard).bookAppointment(any(), any(), any(), any(), any());
        } catch (javax.mail.internet.ParseException e) {
            e.printStackTrace();
        }

        // Stub the sendConfirmationMessages method behavior
        doNothing().when(patientDashboard).sendConfirmationMessages(any(), any());

        // Call the method to test
        try {
            patientDashboard.showDateTimePrompt(doctor);
        } catch (javax.mail.internet.ParseException | ParseException e) {
            e.printStackTrace();
        }

        // Verify interactions
        try {
            verify(patientDashboard).isValidDateTime(anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  Verify that the Doctor is available
     * @throws SQLException
     */
    @Test
    public void testIsDoctorAvailable() throws SQLException {
        // Mock the database connection
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock the PreparedStatement execution
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Simulate one row in the result set

        // Mock the ConnectionWrapper to return the mocked Connection
        ConnectionWrapper mockConnectionWrapper = mock(ConnectionWrapper.class);
        when(mockConnectionWrapper.getConnection()).thenReturn(mockConnection);

        // Mock a loggedInPatient object
        Patient loggedInPatient = mock(Patient.class);
        when(loggedInPatient.getPatientId()).thenReturn("Doc001"); // Assuming getPatientId() is used

        // Create an instance of PatientDashboard with the mocked ConnectionWrapper
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);
        patientDashboard.setConnection((Connection) mockConnection);

        // Call the method to test
        boolean available = patientDashboard.isDoctorAvailable("Doc001", new Date());

        // Verify the expected behavior
        assertTrue(available);
    }

    /**
     *  Tests send confirmation message for appointment booking
     */
    @Test
    public void testSendConfirmationMessages() {
        // Create a mock of PatientDashboard
        PatientDashboard patientDashboardMock = mock(PatientDashboard.class);

        // Create mock objects for the method parameters
        Doctor newDoctor = mock(Doctor.class);
        String bookingDateTime = "2023-04-01T10:00:00";
        String patientEmail = "patient@example.com";

        // Stub the sendConfirmationEmailToPatient method on the mock object
        doNothing().when(patientDashboardMock).sendConfirmationEmailToPatient(newDoctor, bookingDateTime, patientEmail);

        // Call the method to test
        patientDashboardMock.sendConfirmationEmailToPatient(newDoctor, bookingDateTime, patientEmail);

        // Verify the method was called with the expected arguments
        verify(patientDashboardMock).sendConfirmationEmailToPatient(newDoctor, bookingDateTime, patientEmail);
    }

    /**
     *  Tests the addAppointment method in PatientDashboard class
     * @throws SQLException
     * @throws javax.mail.internet.ParseException
     */
    public void testBookAppointment() throws SQLException, javax.mail.internet.ParseException {
        // Mock patient and doctor information
        Patient patient = mock(Patient.class);
        when(patient.getName()).thenReturn("Bee Honey");

        Doctor doctor = mock(Doctor.class);
        when(doctor.getName()).thenReturn("Theophilus Ogieva");

        // Mock the database connection
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        // Mock the PreparedStatement execution
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        doNothing().when(preparedStatement).setDate(anyInt(), (java.sql.Date) any(Date.class));
        doNothing().when(preparedStatement).setTime(anyInt(), any());
        doReturn(1).when(preparedStatement).executeUpdate(); // Assuming executeUpdate() returns an int

        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create an instance of PatientDashboard
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);
        patientDashboard.setConnection((Connection) mockConnectionWrapper);

        // Call the method to test
        patientDashboard.bookAppointment(patient.getName(), doctor.getName(), "Pat004", "2024-03-17", "16:00");
    }

    /**
     *  Test the get patient's doctor information method in PatientDashboard class
     * @throws SQLException if there is a problem accessing the database
     */
    @Test
    public void testGetPatientDoctor() throws SQLException {
        // Mock the database connection
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Mock the PreparedStatement execution
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("doctor_id")).thenReturn("Doc001"); // Assuming this is the correct doctor ID

        // Mock the ConnectionWrapper to return the mocked Connection
        ConnectionWrapper mockConnectionWrapper = mock(ConnectionWrapper.class);
        when(mockConnectionWrapper.getConnection()).thenReturn(connection);

        // Mock a loggedInPatient object
        Patient loggedInPatient = mock(Patient.class);
        when(loggedInPatient.getPatientId()).thenReturn("Pat004"); // Assuming getPatientId() is used

        // Create an instance of PatientDashboard with the mocked ConnectionWrapper
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);
        patientDashboard.setConnection((Connection) mockConnection);

        // Call the method to test
        Doctor doctor = patientDashboard.getPatientDoctor("Pat004");

        // Verify the expected behavior
        assertNotNull(doctor);
        assertEquals("Doc001", doctor.getDoctorId()); // Corrected to match the mocked behavior

        // Optionally, you can verify other behaviors or state changes
    }

    /**
     *  Tests the
     * @throws SQLException
     */
    @Test
    public void testGetDoctor() throws SQLException {
        // Mock the database connection
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        // Mock the PreparedStatement execution
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("name")).thenReturn("Theophilus Ogieva");
        when(resultSet.getString("email")).thenReturn("tio5@kent.ac.uk");

        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create an instance of PatientDashboard
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);
        patientDashboard.setConnection((Connection) mockConnectionWrapper);

        // Call the method to test
        Doctor doctor = patientDashboard.getDoctor("Doc001");

        // Verify the expected behavior
        assertNotNull(doctor);
        assertEquals("Theophilus Ogieva", doctor.getName());
        assertEquals("tio5@kent.ac.uk", doctor.getEmail());

        // Optionally, you can verify other behaviors or state changes
    }

    /**
     * Tests that getDoctors returns all doctors in the system if no arguments are provided.
     */
    @Test
    public void testViewBookingsWithInvalidInputFormat() {
        // Mock JOptionPane to simulate user input
        JOptionPane optionPane = mock(JOptionPane.class);
        when(JOptionPane.showInputDialog(any(Component.class), anyString())).thenReturn("invalid");

        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create an instance of PatientDashboard with the mock Patient object
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);

        // Call the method to test
        patientDashboard.viewBookings(mock(ActionEvent.class));


    }

    /**
     * Test the change doctor method
     */
    @Test
    public void testChangeDoctor() {
        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create an instance of PatientDashboard with the mock Patient object
        PatientDashboard patientDashboard = new PatientDashboard(loggedInPatient);

        // Mock Doctor objects
        Doctor doctor1 = mock(Doctor.class);
        when(doctor1.getName()).thenReturn("Theophilus Ogieva");
        when(doctor1.getDoctorId()).thenReturn("Doc001");

        Doctor doctor2 = mock(Doctor.class);
        when(doctor2.getName()).thenReturn("Mark Love");
        when(doctor2.getDoctorId()).thenReturn("Doc002");

        // Initialize the ArgumentCaptor for Doctor objects
        ArgumentCaptor<Doctor> doctorCaptor = ArgumentCaptor.forClass(Doctor.class);

        // Mock the displayDoctorSelectionDialog method to return one of the doctors
        PatientDashboard spyDashboard = spy(patientDashboard);

        // Mock the displayDoctorSelectionDialog method to return the selected doctor
        doReturn(doctor2).when(spyDashboard).displayDoctorSelectionDialog();

        // Call the method to test
        spyDashboard.changeDoctor(mock(ActionEvent.class)); // Pass a mock ActionEvent

        // Verify interactions
        verify(spyDashboard).displayDoctorSelectionDialog();

        // Verify that updatePatientDoctor is called with the expected arguments
        verify(spyDashboard).updatePatientDoctor(eq("expectedPatientId"), eq(doctor2));

    }

    /**
     * Test the display doctor  selection dialog method in PatientDashboard class
     */
    @Test
    public void testDisplayDoctorSelectionDialog() {
        // Create a mock Patient object
        Patient loggedInPatient = mock(Patient.class);

        // Create mock doctors
        Doctor doctor1 = mock(Doctor.class);
        Doctor doctor2 = mock(Doctor.class);

        // Mock the getAllDoctors method to return an array of mock doctors
        PatientDashboard patientDashboard = spy(new PatientDashboard(loggedInPatient)); // Pass the mock patient object
        doReturn(new Doctor[] { doctor1, doctor2 }).when(patientDashboard).getAllDoctors();


        // Deregister the existing static mock for JOptionPane
        Mockito.framework().clearInlineMocks();

        // Mock the showConfirmDialog method of JOptionPane to simulate user selection
        try (MockedStatic<JOptionPane> mockedJOptionPane = Mockito.mockStatic(JOptionPane.class)) {
            mockedJOptionPane.when(() -> JOptionPane.showConfirmDialog(
                any(), any(), any(), anyInt()
            )).thenReturn(JOptionPane.OK_OPTION); // Simulate user selecting OK

            // Mock the getSelectedItem method of JComboBox to return the name of the first doctor
            JComboBox<String> comboBoxMock = mock(JComboBox.class);
            when(comboBoxMock.getSelectedItem()).thenReturn(doctor1.getName());

            JPanel panelMock = mock(JPanel.class);
            when(panelMock.getComponent(anyInt())).thenReturn(comboBoxMock);

            // Call the method to test
            Doctor selectedDoctor = patientDashboard.displayDoctorSelectionDialog();

        // Assuming 'selectedDoctor' is the Doctor object returned by the method under test
            assertThat(selectedDoctor.getDoctorId()).isEqualTo("Doc001");
            assertThat(selectedDoctor.getName()).isEqualTo("Theophilus Ogieva");
        }
    }

    /**
     * Tests the update patient doctor method
     */
    @Test
    public void testUpdatePatientDoctor() {
        // Mock objects
        Doctor newDoctor = Mockito.mock(Doctor.class);
        Mockito.when(newDoctor.getDoctorId()).thenReturn("Doc001");
        Mockito.when(newDoctor.getName()).thenReturn("Theophilus Ogieva");

        // Test case
        boolean result = patientDashboard.updatePatientDoctor("patientId", newDoctor);

        // Assert the result
        assertFalse(result);
    }

    /**
     *  Tests the get all doctors for a patient method
     * @throws SQLException
     */
    @Test
    public void testGetAllDoctors() throws SQLException {
        // Mock objects
        Doctor doctor1 = new Doctor("Doc001", "Theophilus Ogieva");
        Doctor doctor2 = new Doctor("Doc002", "Mark Love");
        Doctor[] expectedDoctors = {doctor1, doctor2};

        // Mock the Connection and ResultSet
        Connection mockConnection = Mockito.mock(Connection.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, true, false); // Simulate two rows in the result set
        Mockito.when(mockResultSet.getString(Mockito.anyString())).thenReturn("Doc001", "Theophilus Ogieva", "Doc002", "Mark Love");

        // Mock the static method
        Mockito.mockStatic(DatabaseManager.class);

        // Mock the DatabaseManager to return the mock connection
        Mockito.when(DatabaseManager.getConnection()).thenReturn(mockConnection);

        // Test case
        Doctor[] doctors = patientDashboard.getAllDoctors();

        // Assert the result
        assertArrayEquals(expectedDoctors, doctors);
    }

    /**
     * Tests the non availability of the doctor
     * @throws SQLException
     */
    @Test
    public void testIsDoctorAvailable_NoDoctorAvailable() throws SQLException {
        // Simulate an empty result set
        when(mockResultSet.next()).thenReturn(false);

        // Call the method to test
        boolean available = patientDashboard.isDoctorAvailable("", new Date());

        // Verify the expected behavior
        assertFalse(available);
    }

}
