package TradingUserGUI;

import Initialization.PopUpWindow;
import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ItemDetailsWindow {
    private UserMenuController umc;
    private Item item;
    private Map<Item, TradingUser> itemsMap;

    public ItemDetailsWindow(UserMenuController umc, Item item,  Map<Item, TradingUser> itemsMap) {
        this.umc = umc;
        this.item = item;
        this.itemsMap = itemsMap;
    }

    public void display() {
        JFrame frame = new JFrame("Item Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        formatPanel(panel);
        frame.add(panel);
    }

    private void formatPanel(JPanel panel) {
        JLabel itemLabel = new JLabel(item.toString());
        // the user who owns the item
        TradingUser owner = itemsMap.get(item);
        JLabel user = new JLabel(owner.toString());

        JTextArea desc = new JTextArea(item.getDescription());

        panel.add(itemLabel);
        panel.add(user);
        panel.add(desc);

        JButton wishlist = new JButton("Add to Wishlist");
        JButton trans = new JButton("Request Transaction");

        // add action listeners for our two buttons
        wishlist.addActionListener(e -> {
            if (owner.getWishlist().contains(item.getId())) {
                new PopUpWindow("Already in your wishlist").display();
            } else {
                new PopUpWindow("Added to wishlist").display();
            }
        });

        trans.addActionListener(e -> {
            new TransactionWindow.display(umc, item, owner);
        });
    }
}
