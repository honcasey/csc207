package AdminGUI;

import Admins.AdminMenuController;
import Exceptions.InvalidTradingUserException;
import Presenters.AdminMenuPresenter;
import Users.TradingUser;
import Users.TradingUserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrozenUsersWindow {
    private final AdminMenuController amc;
    private final TradingUserManager tum;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private JComboBox<String> users;
    private JButton unfreeze;
    private JPanel mainPanel;
    private JLabel usernameLabel;
    private String currUsername;


    public FrozenUsersWindow(AdminMenuController amc) {
        this.amc = amc;
        this.tum = amc.getTUM();
    }

    public void display() {
        JFrame frame = new JFrame("ChangeThresholdWindow");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        List<TradingUser> listUsers = tum.getFrozenAccounts();

        DefaultComboBoxModel<String> userNames = new DefaultComboBoxModel<String>();
        for (TradingUser curr : listUsers) {
            String username = curr.getUsername();
            userNames.addElement(username);
        }
        users.setModel(userNames);


        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currUsername = (String) users.getSelectedItem();


            }
        });
        unfreeze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currUsername == null) {
                    return;
                }
                try {
                    TradingUser user = tum.getTradingUser(currUsername);
                    tum.unfreezeAccount(user);
                } catch (InvalidTradingUserException invalidTradingUserException) {
                    invalidTradingUserException.printStackTrace();
                }

            }
        });
    }

}