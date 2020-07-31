package Admins;

import Exceptions.InvalidTradingUserException;
import Users.TradingUser;
import Users.TradingUserManager;
import Users.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeThresholdForm {
    private final AdminMenuController amc;
    private final TradingUserManager tum;
    private JTextField borrowThreshold;
    private JPanel panel1;
    private JButton refreshThresholdsButton;
    private JTextField newBorrowThreshold;
    private JTextField newWeeklyThreshold;
    private JTextField newIncompleteThreshold;
    private JButton saveChangesButton;
    private JComboBox users;
    private JTextArea currBorrowThreshold = new JTextArea("", 1, 1);
    private JTextArea currWeeklyThreshold = new JTextArea("", 1, 1);
    private JTextArea currIncompleteThreshold = new JTextArea("", 1, 1);
    private String currUsername;

    public ChangeThresholdForm(AdminMenuController amc, TradingUserManager tum) {
        this.amc = amc;
        this.tum = tum;

        List<TradingUser> listUsers = amc.getAllTradingUsers();

        DefaultComboBoxModel<String> userNames = new DefaultComboBoxModel<String>();
        for (TradingUser curr : listUsers) {
            String username = curr.getUsername();
            userNames.addElement(username);
        }

        users = new JComboBox<>(userNames);






    saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int borrowThreshold = Integer.parseInt(newBorrowThreshold.getText());
                int weeklyThreshold = Integer.parseInt(newWeeklyThreshold.getText());
                int incompleteThreshold = Integer.parseInt(newIncompleteThreshold.getText());
                try {
                    amc.updateThreshold(currUsername, borrowThreshold,"Borrow");
                    amc.updateThreshold(currUsername, weeklyThreshold, "Weekly");
                    amc.updateThreshold(currUsername, incompleteThreshold, "Incomplete");
                } catch (InvalidTradingUserException invalidTradingUserException) {
                    JOptionPane.showMessageDialog(null,
                            "Error: UpdateThreshold method in adminController failed, so the threshold wasn't changed", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }


            }
        });

    refreshThresholdsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> thresholdList = tum.getCurrThresholds(currUsername);
                currBorrowThreshold.insert(thresholdList.get(0).toString(), 1);
                currWeeklyThreshold.insert(thresholdList.get(1).toString(), 1);
                currIncompleteThreshold.insert(thresholdList.get(2).toString(), 1);

            }
        });

        newBorrowThreshold.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                try {
                    int newThreshold = Integer.parseInt(newBorrowThreshold.getText());
                    if (newThreshold <= 0) {
                        JOptionPane.showMessageDialog(null,
                                "Error: Please enter number bigger than 0", "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Error: Please enter an integer", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        users.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 currUsername = (String) users.getSelectedItem();

            }
        });
    }

}
