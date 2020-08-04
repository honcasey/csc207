package DemoUserGUI;

import Initialization.PopUpWindow;
import TradingUserGUI.AvailableItemsWindow;
import Users.DemoMenuController;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Main DemoUser Menu
 */
public class DemoUserMenu {
    private final DemoMenuController dmc;
    private final UserMenuController umc;
    private final JButton button1 = new JButton();
    private final JButton button2 = new JButton();
    private final JButton button3 = new JButton();
    private final JButton button4 = new JButton();
    private final JButton button5 = new JButton();
    private final JButton button6 = new JButton();
    private final JButton button7 = new JButton();

    public DemoUserMenu(DemoMenuController dmc, UserMenuController umc){
        this.dmc = dmc;
        this.umc = umc;
    }

    public void display(){
        // create the frame
        JFrame frame = new JFrame("Demo User Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 500));
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

        // if clicked "Request Add Item" --> go to the window for adding items but you can't actually add items
        button1.addActionListener(e -> {
            DemoUserRequestAddItemsWindow rtw = new DemoUserRequestAddItemsWindow();
            rtw.display();
        });
        // if clicked "display available items" button
        button2.addActionListener(e -> {
            new AvailableItemsWindow(umc).display();
        });

        // if clicked "view active transactions" button
        button3.addActionListener(e -> {
            new PopUpWindow("This feature is available in the full version of the program. Upgrade today!");
        });

        // if clicked "View Transaction History" button
        button4.addActionListener(e -> {
            new PopUpWindow("This feature is available in the full version of the program. Upgrade today!");
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

        button7.setBounds(300, 250, 150, 75);
        button7.setText("Upgrade to Full Account");
        panel.add(button7);
    }

    private void formatMenuOptions(JMenu menu) {
        // log out menu option
        JMenuItem logOut = new JMenuItem("Log out", KeyEvent.VK_L); // press the L key to access log out option
        logOut.addActionListener(e -> System.exit(0));
        menu.add(logOut);
    }
}
