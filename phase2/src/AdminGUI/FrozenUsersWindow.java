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
    private JComboBox users;
    private JButton unfreeze;
    private JPanel mainPanel;
    private JLabel usernameLabel;
    private String currUsername;


    public FrozenUsersWindow(AdminMenuController amc, TradingUserManager tum) {
        this.amc = amc;
        this.tum = tum;
    }

    public void display() {
        JFrame frame = new JFrame(amp.unfreezeRequest);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);
        // display window
        frame.setVisible(true);
        frame.add(mainPanel);

        // add labels, textField and textArea
        mainPanel.setLayout(null);
        usernameLabel.setBounds(50, 80, 150, 25);
        mainPanel.add(usernameLabel);

        users.setBounds(180, 80, 250, 25);
        mainPanel.add(users);

        unfreeze.setBounds(300, 200, 120, 25);
        mainPanel.add(unfreeze);

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