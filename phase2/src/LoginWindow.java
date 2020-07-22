import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private String username;
    private String password;
    private JTextField User = new JTextField("Username");
    private JPasswordField Pass = new JPasswordField("Password");
    private JButton Login = new JButton("Login");

    public void displayLoginWindow() {
        // create the frame
        JFrame frame = new JFrame("TradingApplication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // layout the fields and button on the frame
        frame.setLayout(new BorderLayout());
        frame.add(User, BorderLayout.NORTH);
        frame.add(Pass, BorderLayout.CENTER);
        frame.add(Login, BorderLayout.SOUTH);

        // when user clicks on button, we save the info entered in the fields
        // into our variables username and password
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = User.getText();
                password = Pass.getText();
            }
        });

        // display the window
        frame.pack();
        frame.setVisible(true);
    }

}
