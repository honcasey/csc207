package Admins;

import Exceptions.InvalidTradingUserException;
import Users.TradingUser;
import Users.TradingUserManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FrozenUsersWindow {
    private final AdminMenuController amc;
    private final TradingUserManager tum;
    private JComboBox users;
    private JButton Unfreeze;
    private String currUsername;


    public FrozenUsersWindow(AdminMenuController amc, TradingUserManager tum) {
        this.amc = amc;
        this.tum = tum;

        List<TradingUser> listUsers = tum.getFrozenAccounts();

        DefaultComboBoxModel<String> userNames = new DefaultComboBoxModel<String>();
        for (TradingUser curr : listUsers) {
            String username = curr.getUsername();
            userNames.addElement(username);
        }
        users = new JComboBox<>(userNames);


        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currUsername = (String) users.getSelectedItem();


            }
        });
        Unfreeze.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
