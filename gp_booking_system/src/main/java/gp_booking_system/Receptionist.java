package gp_booking_system;

public class Receptionist {
    private String receptionistId;
    private int userId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String mobile;
    private String email;
    private String username;
    private String password;
    private String role; //receptionist or admin

    // Constructor
    public Receptionist(String receptionistId, String name, int age, String gender, String address, String mobile, String email, String username, String password, String role) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and setters for all fields
    public String getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(String receptionistId) {
        this.receptionistId = receptionistId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
