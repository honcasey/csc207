import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ideas taken from https://beginnersbook.com/2015/07/java-swing-tutorial/
public class RegistrationWindow {
    private String username;
    private String password;
    private String password2;
    private String city;

    public void displayRegistrationWindow() {
        // create the frame
        JFrame frame = new JFrame("Account Creation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);

        // display the window
        frame.setVisible(true);
    }
}
