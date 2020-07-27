import Admins.AdminMenuController;
import Admins.AdminUserMenu;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ideas taken from https://beginnersbook.com/2015/07/java-swing-tutorial/
public class LoginWindow {
    private final JLabel userLabel = new JLabel("Username");
    private final JTextField userText = new JTextField(20);
    private final JLabel passwordLabel = new JLabel("Password");
    private final JPasswordField passwordText = new JPasswordField(20);
    private final JButton loginButton = new JButton("Sign in");
    private final JButton registerButton = new JButton("Create an account");
    private final AdminMenuController amc;
    private final UserMenuController umc;

    public LoginWindow(AdminMenuController amc, UserMenuController umc) {
        this.amc = amc;
        this.umc = umc;
    }

    public void display() {
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
        // if user clicks "sign in"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySecondaryMenu(userText.getText(), passwordText.getText());
            }
        });

        // if user clicks "create an account"
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationWindow rw = new RegistrationWindow(umc);
                rw.display();
                frame.dispose();
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

        // add username field to panel
        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // add password label to panel
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        // add password field to panel
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
        if (amc.validAdmin(username, password)) { // if user and pass matches an admin account
            amc.setCurrentAdmin(username);
            AdminUserMenu aum = new AdminUserMenu(amc);
            aum.display();
        } else if (umc.validUser(username, password)) { // if user and pass matches a trading user account
            umc.setCurrentTradingUser(username);
            TradingUserMenu tum = new TradingUserMenu(umc);
            tum.display();
        } else { // they entered something wrong or their account does not exist
            new PopUpWindow("Invalid credentials or account does not exist.").display();
        }

    }
}
