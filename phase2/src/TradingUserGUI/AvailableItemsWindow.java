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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        // create the panel and the components on it
        JPanel panel = new JPanel();
        // add ComboBox to panel
        // add button to pop up window that has add to wishlist/request transaction buttons

        frame.add(panel);
        placeComponents(panel);

        // display the window
        frame.setVisible(true);
    }

    private void availableItemsBox() {
        availableItemsMap = umc.getAvailableItems();
        JComboBox comboBox = new JComboBox<>((ComboBoxModel<String>) availableItemsMap.keySet());

        comboBox1.addActionListener(e -> {
            JComboBox cb = (JComboBox)e.getSource();
            selectedItem = (Item) cb.getSelectedItem();
        });
    }

    private void itemDetailsWindow() { // the second window
        JFrame frame = new JFrame("Item Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel item = new JLabel(selectedItem.toString());
        // the user who owns the item
        TradingUser owner = availableItemsMap.get(selectedItem);
        JLabel user = new JLabel(owner.toString());

        JTextArea desc = new JTextArea(selectedItem.getDescription());

        panel.add(item);
        panel.add(user);
        panel.add(desc);

        JButton wishlist = new JButton("Add to Wishlist");
        JButton trans = new JButton("Request Transaction");

        frame.add(panel);

    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add the combo box
        panel.add(comboBox1);
    }
}
