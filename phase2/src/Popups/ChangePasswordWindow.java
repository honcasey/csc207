package Popups;

import Admins.AdminManager;
import Admins.AdminUser;
import Users.TradingUser;
import Users.TradingUserManager;
import Users.User;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordWindow {
    private AdminManager am = null;
    private final TradingUserManager um;
    private final User user;
    private final JLabel newLabel = new JLabel("New password");
    private final JPasswordField newPass = new JPasswordField(20);
    private final JLabel confirmLabel = new JLabel("Confirm your password");
    private final JPasswordField confirmPass = new JPasswordField(20);
    private final JButton confirmButton = new JButton("Change password");

    public ChangePasswordWindow(AdminManager am, TradingUserManager um, TradingUser user) {
        this.am = am;
        this.um = um;
        this.user = user;
    }

    public ChangePasswordWindow(TradingUserManager um, TradingUser user) {
        this.um = um;
        this.user = user;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        placeComponents(panel);
        frame.add(panel);

        // add event handler for confirm button
        confirmButton.addActionListener(e -> {
            changePassword();
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        newLabel.setBounds(10,20,80,25);
        panel.add(newLabel);

        newPass.setBounds(100,20,165,25);
        panel.add(newPass);

        confirmLabel.setBounds(10,50,80,25);
        panel.add(confirmLabel);

        confirmPass.setBounds(100,50,165,25);
        panel.add(confirmPass);

        confirmButton.setBounds(10, 110, 160, 25);
        panel.add(confirmButton);
    }

    private void changePassword() {
        // get the entered passwords as strings
        String newPassText = String.valueOf(newPass.getPassword());
        String confirmPassText = String.valueOf(confirmPass.getPassword());

        // check if passwords match, if so then change password
        if (am == null) {
            if (newPassText.equals(confirmPassText)) {
                um.changePassword((TradingUser) user, newPassText);
            } else { // passwords don't match
                new PopUpWindow("Passwords do not match").display();
            }
        } else { // am != null, so am is passed into constructor, so it's an admin trying to change pwd
            if (newPassText.equals(confirmPassText)) {
                am.changePassword((AdminUser) user, confirmPassText);
            } else { // passwords don't match
                new PopUpWindow("Passwords do not match").display();
            }
        }
    }
}
