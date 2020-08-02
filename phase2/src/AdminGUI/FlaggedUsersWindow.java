package AdminGUI;

import Admins.AdminMenuController;
import Exceptions.InvalidTradingUserException;
import Users.TradingUser;
import Users.TradingUserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FlaggedUsersWindow {

    private JComboBox users;
    private JButton freezeButton;
    private JButton unflagButton;
    private String currUser;

    public FlaggedUsersWindow(AdminMenuController amc, TradingUserManager tum) {

        List<TradingUser> listUsers = tum.getFlaggedAccounts();

        DefaultComboBoxModel<String> userNames = new DefaultComboBoxModel<String>();
        for (TradingUser curr : listUsers) {
            String username = curr.getUsername();
            userNames.addElement(username);
        }

        users = new JComboBox<>(userNames);


        freezeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tum.freezeAccount(tum.getTradingUser(currUser));
                } catch (InvalidTradingUserException invalidTradingUserException) {
                    invalidTradingUserException.printStackTrace();
                }

            }
        });
        unflagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TradingUser user = tum.getTradingUser(currUser);
                    tum.getFlaggedAccounts().remove(user);
                } catch (InvalidTradingUserException invalidTradingUserException) {
                    invalidTradingUserException.printStackTrace();
                }

            }
        });
        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currUser = (String) users.getSelectedItem();

            }
        });
    }
}
