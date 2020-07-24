import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ideas taken from https://beginnersbook.com/2015/07/java-swing-tutorial/
public class LoginWindow extends JFrame {
    private final JLabel userLabel = new JLabel("Username");
    private final JLabel passwordLabel = new JLabel("Password");
    private final JButton loginButton = new JButton("Sign in");
    private final JButton registerButton = new JButton("Create an account");

    public void displayLoginWindow() {
        // create the frame
        JFrame frame = new JFrame("TradingApplication");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // add action listeners for our buttons
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySecondaryMenu(userLabel.getText(), userLabel.getText());

            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationWindow rw = new RegistrationWindow();
                rw.displayRegistrationWindow();
            }
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add username label to panel
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        // create field for user to enter their username
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // add password label to panel
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        // create password field for user to enter their password
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);

        // add login button to panel
        loginButton.setBounds(10, 80, 160, 25);
        panel.add(loginButton);

        // add register button to panel
        registerButton.setBounds(10, 110, 160, 25);
        panel.add(registerButton);
    }

    // checks if credentials are valid, if so proceed to trading/admin/demo menu
    private void displaySecondaryMenu(String username, String password) {
        // use old algorithm in TradingSystem to do it
    }

}
