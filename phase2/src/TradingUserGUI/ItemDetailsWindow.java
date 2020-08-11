package TradingUserGUI;

import Exceptions.InvalidItemException;
import Popups.PopUpWindow;
import Items.Item;
import Presenters.UserMenuPresenter;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class ItemDetailsWindow {
    private UserMenuController umc;
    private final UserMenuPresenter ump = new UserMenuPresenter();
    private UUID itemId;
    private Item item;
    private Map<Item, TradingUser> itemsMap;

    public ItemDetailsWindow(UserMenuController umc, UUID itemId,  Map<Item, TradingUser> itemsMap) throws InvalidItemException {
        this.umc = umc;
        this.itemId = itemId;
        this.itemsMap = itemsMap;
        item = umc.getIm().getItem(itemId);
    }

    public void display() {
        JFrame frame = new JFrame(ump.itemDetails);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(550, 300));
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        formatPanel(panel);
        frame.add(panel);

        // display the window
        frame.setVisible(true);
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

        JButton wishlist = new JButton(ump.addToWishlist);
        JButton trans = new JButton(ump.requestTrans);

        // add action listeners for our two buttons
        wishlist.addActionListener(e -> {
            if (owner.getWishlist().contains(itemId)) {
                new PopUpWindow(ump.inWishlist).display();
            } else {
                umc.addToWishlist(item);
                new PopUpWindow(ump.addedToWishlist).display();
            }
        });

        trans.addActionListener(e -> {
            try {
                new TransactionWindow(umc, itemId, owner.getUserId()).display();
            } catch (InvalidItemException invalidItemException) {
                // invalidItemException.printStackTrace();
            }
        });

        panel.add(wishlist);
        panel.add(trans);

    }
}
