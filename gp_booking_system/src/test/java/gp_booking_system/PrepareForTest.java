package gp_booking_system;

import javax.swing.JOptionPane;

public @interface PrepareForTest {

    Class<JOptionPane>[] value();

}
