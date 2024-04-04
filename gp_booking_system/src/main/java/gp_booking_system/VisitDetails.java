package gp_booking_system;

import java.util.Date;

public class VisitDetails {
    private int bookingId;
    private String doctorName;
    private Date visitDate;
    private String visitTime;
    private String symptoms;
    private String diagnosis;

    public VisitDetails() {
        // Default constructor
    }

    // Getters and setters

    /**
     * Get the booking ID of the visit.
     * @return The booking ID.
     */
    public int getBookingId() {
        return bookingId;
    }

    /**
     * Set the booking ID of the visit.
     * @param bookingId The booking ID to set.
     */
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Get the name of the doctor for the visit.
     * @return The doctor's name.
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Set the name of the doctor for the visit.
     * @param doctorName The doctor's name to set.
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * Get the date of the visit.
     * @return The visit date.
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * Set the date of the visit.
     * @param visitDate The visit date to set.
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * Get the time of the visit.
     * @return The visit time.
     */
    public String getVisitTime() {
        return visitTime;
    }

    /**
     * Set the time of the visit.
     * @param visitTime The visit time to set.
     */
    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    /**
     * Get the symptoms reported during the visit.
     * @return The symptoms reported.
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * Set the symptoms reported during the visit.
     * @param symptoms The symptoms to set.
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * Get the diagnosis made during the visit.
     * @return The diagnosis.
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Set the diagnosis made during the visit.
     * @param diagnosis The diagnosis to set.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Set the booking number of the visit.
     * @param bookingNo The booking number to set.
     */
    public void setBookingNo(int bookingNo) {
        // Implementation not provided
    }

    /**
     * Set the visit date of the visit.
     * @param visitDate The visit date to set.
     */
    public void setVisitDate(String visitDate) {
        // Implementation not provided
    }
}
