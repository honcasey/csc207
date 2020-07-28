package Admins;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Main Admin Menu
 */
public class AdminUserMenu {
    private final AdminMenuController amc;
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    private final JButton button3 = new JButton();
    private final JButton button4 = new JButton();
    private final JButton button5 = new JButton();
    private final JButton button6 = new JButton();

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

        // create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // build a menu
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic('M'); // press the M key to access the menu

        // change password menu option
        JMenuItem changePassword = new JMenuItem("Change password", KeyEvent.VK_C); // press the C key to access this menu item
        //changePassword.addActionListener(new ActionListener() {
            //@Override
            //public void actionPerformed(ActionEvent e) {
                //  TO-DO (optional?) : make a "change password" window
            //}
        //});

        // log out menu option
        JMenuItem logOut = new JMenuItem("Log out", KeyEvent.VK_L); // press the L key to access log out option
        // logOut.addActionListener(new ActionListener() {
            // @Override
            // public void actionPerformed(ActionEvent e) {
            // TO-DO: make the log out window
        // }
        // });
        menu.add(changePassword);
        menu.add(logOut);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // layout the fields and button on the frame
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // if button1 (check pending items) is clicked
        button1.addActionListener(e -> {
            CheckPendingItemsWindow iw = new CheckPendingItemsWindow(amc);
            iw.display();
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // panel.setLayout(null);

        // add button's to panel
        button1.setBounds(50, 50, 150, 75);
        button1.setBackground(Color.PINK); // DO NOT CHANGE THIS >: ((
        button1.setForeground(Color.PINK);
        button1.setText("Check Pending Items for Approval");
        panel.add(button1);

        button2.setBounds(200, 50, 150, 75);
        button2.setText("Check Flagged Users");
        panel.add(button2);

        button3.setBounds(350, 50, 150, 75);
        button3.setText("Create New Admin User");
        panel.add(button3);

        button4.setBounds(50, 150, 150, 75);
        button4.setText("Add New Item to a TradingUser's Wishlist/Inventory");
        panel.add(button4);

        button5.setBounds(200, 150, 150, 75);
        button5.setText("Change TradingUser Threshold");
        panel.add(button5);

        button6.setBounds(350, 150, 150, 75);
        button6.setText("Check Unfreeze TradingUser Account Requests");
        panel.add(button6);
    }

}
