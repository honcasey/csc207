package TradingUserGUI;

import Initialization.Filepaths;
import Initialization.Serializer;
import Presenters.UserMenuPresenter;
import Users.User;
import Popups.ChangePasswordWindow;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Main Trading User Menu
 */
public class TradingUserMenu {
    private final UserMenuController umc;
    private final UserMenuPresenter ump = new UserMenuPresenter();
    private final Filepaths fp = new Filepaths();
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
        JFrame frame = new JFrame(ump.TradingUserMenuTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        // create the menu bar
        JMenuBar menuBar = new JMenuBar();

        // build a menu
        JMenu menu = new JMenu(ump.menu);
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
        button1.setText(ump.requestAddItem);
        panel.add(button1);

        button2.setBounds(200, 50, 150, 75);
        button2.setText(ump.displayAvailableItems);
        panel.add(button2);

        button3.setBounds(350, 50, 150, 75);
        button3.setText(ump.viewActiveTrans);
        panel.add(button3);

        button4.setBounds(50, 150, 150, 75);
        button4.setText(ump.viewTranHistory);
        panel.add(button4);

        button5.setBounds(200, 150, 150, 75);
        button5.setText(ump.viewWishlist);
        panel.add(button5);

        button6.setBounds(350, 150, 150, 75);
        button6.setText(ump.viewInventory);
        panel.add(button6);
    }

    private void formatMenuOptions(JMenu menu) {
        // change email option
        JMenuItem changeEmail = new JMenuItem(ump.changeEmail);
        changeEmail.addActionListener(e -> {
            // TODO make change email window
        });

        // change password option
        JMenuItem changePassword = new JMenuItem(ump.changePsw);
        changePassword.addActionListener(e -> {
            new ChangePasswordWindow(umc.getUm(), umc.getCurrentTradingUser()).display();
        });
        menu.add(changePassword);

        // change home city option
        JMenuItem changeCity = new JMenuItem(ump.changeCity);
        changeCity.addActionListener(e -> {
            // TODO make change city window
        });

        // request to have your account unfrozen option
        JMenuItem requestUnfrozen = new JMenuItem(ump.requestUnfreeze);
        requestUnfrozen.addActionListener(e -> {
            // TODO make request unfrozen window
        });

        // log out menu option
        JMenuItem logOut = new JMenuItem(ump.logOut, KeyEvent.VK_L); // press the L key to access log out option
        logOut.addActionListener(e -> {
            writeData();
            System.exit(0);
        });
        menu.add(logOut);
    }

    private void writeData() {
        Serializer serializer = new Serializer();
        serializer.writeUsersToFile(fp.USERS, umc.getUm().getAllTradingUsers());
        serializer.writeItemsToFile(fp.REQUESTEDITEMS, umc.getAllPendingItems());
        serializer.writeAccountsToFile(fp.FLAGGEDACCOUNTS, umc.getUm().getFlaggedAccounts());
        serializer.writeAccountsToFile(fp.FROZENACCOUNTS, umc.getUm().getFrozenAccounts());
        serializer.writeTransactionsToFile(fp.TRANSACTIONS, umc.getTm().getAllTransactions());
        serializer.writeItemsMapToFile(fp.ITEMS, umc.getIm().getAllItems());
    }
}
