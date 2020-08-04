package TradingUserGUI;

import AdminGUI.ChangePasswordWindow;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Main Trading User Menu
 */
public class TradingUserMenu {
    private final UserMenuController umc;
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    private final JButton button3 = new JButton();
    private final JButton button4 = new JButton();
    private final JButton button5 = new JButton();
    private final JButton button6 = new JButton();

    public TradingUserMenu(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("AdminMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // if clicked request add items button
        button1.addActionListener(e -> {
            RequestAddItemsWindow riw = new RequestAddItemsWindow(umc);
            riw.display();
        });

        // if clicked "display available items" button
        button2.addActionListener(e -> {
            new AvailableItemsWindow(umc).display();
        });


        // if clicked "view active transactions" button
        button3.addActionListener(e -> {
            ViewActiveTransactionsWindow atw = new ViewActiveTransactionsWindow(umc);
            atw.display();
        });

        // if clicked "View Transaction History" button
        button4.addActionListener(e -> {
            ViewTransactionHistoryWindow thw = new ViewTransactionHistoryWindow(umc);
            thw.display();
        });

        // if clicked "View Wishlist" button
        button5.addActionListener(e -> {
            ViewWishlistWindow vww = new ViewWishlistWindow(umc);
            vww.display();

        });

        // if clicked "View Inventory" button
        button6.addActionListener(e -> {
            ViewInventoryWindow viw = new ViewInventoryWindow(umc);
            viw.display();
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
        button1.setText("Request Add Item");
        panel.add(button1);

        button2.setBounds(200, 50, 150, 75);
        button2.setText("Display Available Items");
        panel.add(button2);

        button3.setBounds(350, 50, 150, 75);
        button3.setText("View Active Transactions");
        panel.add(button3);

        button4.setBounds(50, 150, 150, 75);
        button4.setText("View Transaction History");
        panel.add(button4);

        button5.setBounds(200, 150, 150, 75);
        button5.setText("View Wishlist");
        panel.add(button5);

        button6.setBounds(350, 150, 150, 75);
        button6.setText("View Inventory");
        panel.add(button6);
    }

    private void formatMenuOptions(JMenu menu) {
        // log out menu option
        JMenuItem logOut = new JMenuItem("Log out", KeyEvent.VK_L); // press the L key to access log out option
        logOut.addActionListener(e -> System.exit(0));
        menu.add(logOut);
    }
}
