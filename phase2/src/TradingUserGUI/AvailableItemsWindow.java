package TradingUserGUI;

import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class AvailableItemsWindow {
    private final UserMenuController umc;
    private Map<Item, TradingUser> availableItemsMap;
    private JComboBox<String> comboBox1;
    private Item selectedItem;

    public AvailableItemsWindow(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("Available Items");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        // create the panel and the components on it
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);
        createComboBox();
        setupLeftPanel(panel1);
        setupRightPanel(panel2);

        // display the window
        frame.setVisible(true);
    }

    private void createComboBox() {
        availableItemsMap = umc.getAvailableItems();
        JComboBox<String> comboBox1 = new JComboBox<>();

        for (Item item : availableItemsMap.keySet()) {
            comboBox1.addItem(item.getName());
        }

        comboBox1.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox)e.getSource();
            selectedItem = (Item) cb.getSelectedItem();
        });
    }

    private void setupLeftPanel(JPanel panel) {
        panel.setLayout(null);

        // add the combo box
        panel.add(comboBox1);
    }

    private void setupRightPanel(JPanel panel) {

    }
}
