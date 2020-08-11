package TradingUserGUI;

import Popups.PopUpWindow;
import Items.Item;
import Presenters.UserMenuPresenter;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AvailableItemsWindow {
    private final UserMenuController umc;
    private final UserMenuPresenter ump = new UserMenuPresenter();
    private Map<Item, TradingUser> availableItemsMap;
    private JComboBox<String> comboBox1;
    private Item selectedItem;

    public AvailableItemsWindow(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame(ump.displayAvailableItems);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        // create the panel and the components on it
        JPanel panel = new JPanel();
        panel.add(comboBox1);

        availableItemsMap = umc.getAvailableItems();
        if (availableItemsMap == null) {
            new PopUpWindow(ump.noItem).display();
        } else {

            for (Item item : availableItemsMap.keySet()) {
                comboBox1.addItem(item.toString());
            }
            panel.add(comboBox1);
            comboBox1.addActionListener(e -> {
                JComboBox cb = (JComboBox) e.getSource();
                selectedItem = (Item) cb.getSelectedItem();
                ItemDetailsWindow idw = new ItemDetailsWindow(umc, selectedItem, availableItemsMap);
                idw.display();
            });
            frame.add(panel);

            frame.setVisible(true);
        }
    }
}
