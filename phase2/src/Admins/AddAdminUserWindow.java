package Admins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminUserWindow {
    private final AdminMenuController amc;
    private JPanel mainPanel = new JPanel();
    private JLabel username = new JLabel("New Admin Username:");
    private JLabel password = new JLabel("New Admin Password:");
    private JButton createAccountButton = new JButton("Create Account");
    private JTextField usernameTextField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public AddAdminUserWindow(AdminMenuController amc) {
        this.amc = amc;
    }
    public void display(){
        JFrame frame = new JFrame("Add New Admin");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.add(mainPanel);
        placeComponents(mainPanel);


        createAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String passText = new String(passwordField.getPassword());
                amc.createAdmin(usernameTextField.getText(),passText);
                JOptionPane.showMessageDialog(null,"Admin created.","Message",JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        username.setBounds(50, 80, 150, 25);
        panel.add(username);

        password.setBounds(50, 130, 150, 25);
        panel.add(password);

        usernameTextField.setBounds(220, 80, 165, 25);
        panel.add(usernameTextField);

        passwordField.setBounds(220, 130, 165, 25);
        panel.add(passwordField);

        createAccountButton.setBounds(150, 200, 160, 25);
        panel.add(createAccountButton);
        }


    }
