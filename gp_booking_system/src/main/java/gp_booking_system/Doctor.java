package gp_booking_system;

import java.util.Objects;

public class Doctor {
    private String doctorId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private String specialisation;
    private String role;

    // Constructor
    public Doctor(String doctorId, String name, int age, String gender, String address, String mobile, String email, String username, String password, String role, String specialisation) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.username = username;
        this.password = password;
        this.specialisation = specialisation;
    }

    // Adjusted constructor
    public Doctor(String doctorId, String name) {
        this.doctorId = doctorId;
        this.name = name;
    }

    // Getters and setters for all fields
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
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

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Doctor other = (Doctor) obj;
        // Compare the state of two Doctor objects
        // You can compare individual fields or use Objects.equals() for simplicity
        return Objects.equals(this.name, other.name) &&
               Objects.equals(this.specialisation, other.specialisation);
    }

}
