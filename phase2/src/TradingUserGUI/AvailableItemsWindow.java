package TradingUserGUI;

import Initialization.PopUpWindow;
import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
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
        createComboBox();
        frame.add(panel);
        placeComponents(panel);

        // display the window
        frame.setVisible(true);
    }

    private void createComboBox() {
        availableItemsMap = umc.getAvailableItems();
        if (availableItemsMap == null) {
            // print a statement on the panel that says no available items;
        }
        else {
            comboBox1 = new JComboBox<>();

            for (Item item : availableItemsMap.keySet()) {
                comboBox1.addItem(item.toString());
            }

            comboBox1.addActionListener(e -> {
                JComboBox cb = (JComboBox)e.getSource();
                selectedItem = (Item) cb.getSelectedItem();
                new ItemDetailsWindow(umc, selectedItem, availableItemsMap).display();
            });
        }
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // add the combo box
        panel.add(comboBox1);
    }
}
