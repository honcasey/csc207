package DemoUserGUI;

import Initialization.LoginController;
import Popups.PopUpWindow;
import TradingUserGUI.TradingUserMenu;
import Users.DemoMenuController;
import Users.DemoUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoUserRegistrationWindow {
    private final JLabel userLabel = new JLabel("Username");
    private final JTextField userText = new JTextField(20);
    private final JLabel passwordLabel = new JLabel("Password");
    private final JPasswordField passwordText = new JPasswordField(20);
    private final JTextField confirmText = new JPasswordField(20);
    private final JLabel confirmLabel = new JLabel("Confirm");
    private final JButton createButton = new JButton("Sign up");
    private final JTextArea desc = new JTextArea("Create a demo account to try the program out!");
    private final LoginController lc;
    private final UserMenuController umc;
    private final DemoMenuController dmc;

    public DemoUserRegistrationWindow(LoginController lc, UserMenuController umc, DemoMenuController dmc) {
        this.lc = lc;
        this.umc = umc;
        this.dmc = dmc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("Demo Account Creation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(400, 400));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // add action listener for the "sign up" button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount(userText.getText(), passwordText.getText(), confirmText.getText());
                frame.dispose();
            }
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add description
        desc.setBounds(25, 50, 350, 50);
        panel.add(desc);

        // add username label to panel
        userLabel.setBounds(50,140,80,25);
        panel.add(userLabel);

        // create field for user to enter their username
        userText.setBounds(150,140,165,25);
        panel.add(userText);

        // add password label to panel
        passwordLabel.setBounds(50,190,80,25);
        panel.add(passwordLabel);

        // add password field to panel
        passwordText.setBounds(150,190,165,25);
        panel.add(passwordText);

        // add confirm label to panel
        confirmLabel.setBounds(50,240,80,25);
        panel.add(confirmLabel);

        // add confirm field to panel
        confirmText.setBounds(150,240,165,25);
        panel.add(confirmText);

        // add "sign up" button to panel
        createButton.setBounds(50, 290, 160, 25);
        panel.add(createButton);
    }

    private void createAccount(String username, String password, String password2) {
        if (lc.availableUsername(username) && password.equals(password2)) {
            lc.addDemoUser(username, password);
            new DemoUserMenu(dmc,umc,lc).display();
        } else {
            new PopUpWindow("Username already taken!").display();
        }
    }
}
