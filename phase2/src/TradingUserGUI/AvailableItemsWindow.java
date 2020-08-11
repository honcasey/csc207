package TradingUserGUI;

import Popups.PopUpWindow;
import Items.Item;
import Presenters.UserMenuPresenter;
import Users.TradingUser;
import Users.UserMenuController;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class AvailableItemsWindow {
    private final UserMenuController umc;
    private final UserMenuPresenter ump = new UserMenuPresenter();
    private Map<Item, TradingUser> availableItemsMap;
    private final JPanel panel = new JPanel();
    private final JButton itemButton = new JButton();
    private Item selectedItem;

    public AvailableItemsWindow(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        JFrame frame = new JFrame(ump.displayAvailableItems);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        availableItemsMap = umc.getAvailableItems();
        frame.setVisible(true);

        if (availableItemsMap.isEmpty()) {
            new PopUpWindow(ump.noItem).display();
        } else {
            List<Item> itemList = new ArrayList<Item>(availableItemsMap.keySet());

            DefaultListModel<String> itemNames = new DefaultListModel<>();
            for (Item item : availableItemsMap.keySet()) {
                itemNames.addElement(item.toString());
            }
            JList<String> itemsList = new JList<>(itemNames);
            itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            itemsList.setSelectedIndex(0);
            itemsList.addListSelectionListener(e -> {
                JList<String> itemList1 = (JList<String>) e.getSource();
                selectedItem = itemList.get(itemList1.getSelectedIndex());
            });

            itemButton.setBounds(400, 300, 100, 50);
            itemButton.addActionListener(e -> {
                ItemDetailsWindow idw = new ItemDetailsWindow(umc, selectedItem, availableItemsMap);
                idw.display();
            });

            panel.add(itemsList);
            panel.add(itemButton);
            frame.setContentPane(panel);
            panel.setLayout(null);
        }
    }

}
