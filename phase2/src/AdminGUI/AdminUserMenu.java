package AdminGUI;

import Admins.AdminMenuController;

import Initialization.Filepaths;
import Initialization.Serializer;
import Users.TradingUserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Main Admin Menu
 */
public class AdminUserMenu {
    private final AdminMenuController amc;
    private final TradingUserManager tum;
    private final Filepaths fp = new Filepaths();
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    private final JButton button3 = new JButton();
    private final JButton button4 = new JButton();
    private final JButton button5 = new JButton();
    private final JButton button6 = new JButton();

    public AdminUserMenu(AdminMenuController amc) {
        this.amc = amc;
        this.tum = amc.getTUM();
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("AdminMenu");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        // create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // build a menu
        JMenu menu = new JMenu("Menu");
        menu.setMnemonic('M'); // press the M key to access the menu

        formatMenuOptions(menu);

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

        // if button2 (Check Flagged Users) is clicked
        button2.addActionListener(e -> {
            FlaggedUsersWindow fuw = new FlaggedUsersWindow(amc,tum);
            fuw.display();
        });

        // if button3 (create new admin user) is clicked
        button3.addActionListener(e -> {
            if (amc.getCurrentAdmin().isFirstAdmin()){//creates a new admin which can only be done by the first admin
                AddAdminUserWindow auw = new AddAdminUserWindow(amc);
                auw.display();
            }else {
                JOptionPane.showMessageDialog(null, "Permission Denied", "Error Message", JOptionPane.WARNING_MESSAGE);
            }});

        //if button4 is clicked
        button4.addActionListener(e -> {
            AddNewItemToTradingUserWindow aiw = new AddNewItemToTradingUserWindow(amc);
            aiw.display();
        });

        // if button5 (Change TradingUser Threshold) is clicked
        button5.addActionListener(e ->{
            ChangeThresholdForm tw = new ChangeThresholdForm(amc, tum);
        });

        // display the window
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

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

    private void formatMenuOptions(JMenu menu) {
        // change password menu option
        JMenuItem changePassword = new JMenuItem("Change password", KeyEvent.VK_C); // press the C key to access this menu item
        changePassword.addActionListener(e -> {
            ChangePasswordWindow cpw = new ChangePasswordWindow(amc);
            cpw.display();
        });

        // log out menu option
        JMenuItem logOut = new JMenuItem("Log out", KeyEvent.VK_L); // press the L key to access log out option
        logOut.addActionListener(e -> {
            writeData();
            System.exit(0);
        });
        menu.add(changePassword);
        menu.add(logOut);
    }

    private void writeData() {
        Serializer serializer = new Serializer();
        serializer.writeUsersToFile(fp.USERS, tum.getAllTradingUsers());
        serializer.writeAdminsToFile(fp.ADMINS, amc.getAm().getAllAdmins());
        serializer.writeItemsToFile(fp.REQUESTEDITEMS, amc.getAllPendingItems());
        serializer.writeAccountsToFile(fp.FLAGGEDACCOUNTS, tum.getFlaggedAccounts());
        serializer.writeAccountsToFile(fp.FROZENACCOUNTS, tum.getFrozenAccounts());
        serializer.writeItemsMapToFile(fp.ITEMS, amc.getIm().getAllItems());
    }

}
