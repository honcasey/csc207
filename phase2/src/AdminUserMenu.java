import Admins.AdminMenuController;
import Admins.CheckPendingItemsWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserMenu {
    private final AdminMenuController amc;
    private final JButton button1 = new JButton("Check Pending Items");
    private CheckPendingItemsWindow itemsWindow;
    private final JButton button2 = new JButton("Check Flagged Users");
    private final JButton button3 = new JButton("Create New Admin User Account");
    private final JButton button4 = new JButton("Add New Item to a TradingUser's Account");
    private final JButton button5 = new JButton("Change Threshold");
    private final JButton button6 = new JButton("Check Frozen Users");


    public AdminUserMenu(AdminMenuController amc) {
        this.amc = amc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("AdminMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(600, 300));
        frame.setLocationRelativeTo(null);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // if button1 (check pending items) is clicked
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckPendingItemsWindow iw = new CheckPendingItemsWindow(amc);
                iw.display();
            }});

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add button's to panel
        button1.setBounds(50, 50, 150, 75);
        button1.setBackground(Color.PINK); // DO NOT CHANGE THIS >: ((
        button1.setForeground(Color.PINK);
        panel.add(button1);

        button2.setBounds(200, 50, 150, 75);
        panel.add(button2);

        button3.setBounds(350, 50, 150, 75);
        panel.add(button3);

        button4.setBounds(50, 150, 150, 75);
        panel.add(button4);

        button5.setBounds(200, 150, 150, 75);
        panel.add(button5);

        button6.setBounds(350, 150, 150, 75);
        panel.add(button6);
    }

}
