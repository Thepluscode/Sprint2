package gp_booking_system;

/**
 * Represents a prescription with medicine name, dosage, and prescribing doctor information.
 */
public class Prescription {
    private String medicineName;
    private String dosage;
    private String prescribingDoctor;

    /**
     * Constructs a Prescription object with the given medicine name, dosage, and prescribing doctor.
     * @param medicineName The name of the prescribed medicine.
     * @param dosage The dosage of the prescribed medicine.
     * @param prescribingDoctor The name of the doctor who prescribed the medicine.
     */
    public Prescription(String medicineName, String dosage, String prescribingDoctor) {
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.prescribingDoctor = prescribingDoctor;
    }

    // Getters and setters
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPrescribingDoctor() {
        return prescribingDoctor;
    }

    public void setPrescribingDoctor(String prescribingDoctor) {
        this.prescribingDoctor = prescribingDoctor;
    }

    // Additional setters (Not implemented in the current code)

    /**
     * Sets additional instructions for the prescription.
     * @param instructions Additional instructions for the prescription.
     */
    public void setInstructions(String instructions) {
        // Implement this method if needed
    }

    /**
     * Sets the name of the doctor who prescribed the medicine.
     * @param doctorName The name of the doctor who prescribed the medicine.
     */
    public void setDoctorName(String doctorName) {
        // Implement this method if needed
    }
}
