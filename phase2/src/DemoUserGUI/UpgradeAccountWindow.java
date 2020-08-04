package DemoUserGUI;

import Initialization.PopUpWindow;
import Users.DemoMenuController;
import Users.UserMenuController;

import javax.swing.*;
import java.util.UUID;

public class UpgradeAccountWindow {
    private final JLabel userLabel = new JLabel("Username");
    private final JTextField userText = new JTextField(20);
    private final JLabel passwordLabel = new JLabel("Password");
    private final JPasswordField passwordText = new JPasswordField(20);
    private final JTextField confirmText = new JPasswordField(20);
    private final JLabel confirmLabel = new JLabel("Confirm");
    private final JButton createButton = new JButton("Sign up");
    private final UserMenuController umc;
    private final DemoMenuController dmc;

    public UpgradeAccountWindow(DemoMenuController dmc, UserMenuController umc){
        this.dmc = dmc;
        this.umc = umc;
    }
    public void display(){

    }

    private void createAccount(String username, String password, String password2) {
        if (umc.availableUsername(username) && password.equals(password2)) {
            umc.addTradingUser(username, password);
            UUID oldID = dmc.currentDemoUser.getUserId();
            dmc.getDum().removeDemoUser(oldID);

        } else {
            new PopUpWindow("Username already taken! Please enter a new username.").display();
        }
    }
}
