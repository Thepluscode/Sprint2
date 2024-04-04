package gp_booking_system;

import java.time.LocalDateTime;

/**
* Theophilus Ogieva* login: tio5, thpr2, Ws247 and Km769
* Group D(Patient)
* Represents a Patient in the booking system.
*/
public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private String role;

    /**
     * Constructs a Patient object with the provided details.
     *
     * @param patientId the unique identifier of the patient
     * @param name      the name of the patient
     * @param age       the age of the patient
     * @param gender    the gender of the patient
     * @param address   the address of the patient
     * @param mobile    the mobile number of the patient
     * @param email     the email address of the patient
     * @param username  the username of the patient
     * @param password  the password of the patient
     */
    public Patient(String patientId, String name, int age, String gender, String address, String mobile, String email, String username, String password, String role) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role ="patient";
        setPatientId(patientId);
    }

    /**
     * Retrieves the patient ID.
     *
     * @return the patient ID
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient ID.
     *
     * @param patientId the patient ID to set
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getLastMessageCheckTime() {
        // Implement logic to retrieve the last message check time for the patient
        // For example, you can have a field in the Patient class to store this information
        return getLastMessageCheckTime();
    }


    public void showMessageDialog(Object any, Object any2, String eq, int eq2) {
    }
}
