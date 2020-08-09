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

public class FlaggedUsersWindow {
    private final AdminMenuController amc;
    private final TradingUserManager tum;
    private final AdminMenuPresenter amp = new AdminMenuPresenter();
    private JComboBox users;
    private JButton freezeButton;
    private JButton unflagButton;
    private JPanel mainPanel;
    private JLabel usernameLabel;
    private String currUser;

    public FlaggedUsersWindow(AdminMenuController amc, TradingUserManager tum) {
        this.amc = amc;
        this.tum = tum;
    }

    public void display() {
        JFrame frame = new JFrame(amp.checkFlaggedUser);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);
        // display window
        frame.setVisible(true);
        frame.add(mainPanel);

        // add labels, textField and textArea
        mainPanel.setLayout(null);
        freezeButton.setBounds(180, 200, 110, 25);
        mainPanel.add(freezeButton);

        unflagButton.setBounds(330, 200, 110, 25);
        mainPanel.add(unflagButton);

        usernameLabel.setBounds(50, 80, 150, 25);
        mainPanel.add(usernameLabel);

        users.setBounds(180, 80, 250, 25);
        mainPanel.add(users);

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
                if (currUser == null) {
                    return;
                }
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
                if (currUser == null) {
                    return;
                }
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
