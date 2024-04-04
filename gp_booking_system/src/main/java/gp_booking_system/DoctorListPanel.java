/* package gp_booking_system;

import java.util.List;

import javax.swing.*;

public class DoctorListPanel extends JPanel {
    private JTextArea doctorTextArea;

    public DoctorListPanel() {
        initComponents();
    }

    private void initComponents() {
        doctorTextArea = new JTextArea(20, 40);
        doctorTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(doctorTextArea);

        add(scrollPane);
    }

    public void displayDoctorsInfo(List<Doctor> doctorsInfo) {
        StringBuilder sb = new StringBuilder();
        for (Doctor doctor : doctorsInfo) {
            sb.append("Name: ").append(doctor.getName()).append(", Phone Number: ").append(doctor.getMobile()).append("\n");
        }
        doctorTextArea.setText(sb.toString());
    }
}

 */
