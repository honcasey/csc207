package Users;

import Items.Item;
import Items.ItemManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewWishlistWindow {
    private TradingUser currUser;
    private ItemManager im;
    private String itemName;
    private String itemDesc;

    public void ViewWishListWindow(TradingUser currUser, ItemManager im) {
        this.currUser = currUser;
        this.im = im;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("Inventory");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes the current frame but doesn't terminate the app

        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        // LEFT SIDE OF SPLIT
        List<Item> items = im.convertIdsToItems(currUser.getWishlist());
        DefaultListModel<String> itemNames = new DefaultListModel<>();
        DefaultListModel<String> itemDescs = new DefaultListModel<>();
        for (Item item : items) {
            itemNames.addElement(item.toString());
            itemDescs.addElement(item.getDescription());
        }
        JList<String> itemsList = new JList<>(itemNames);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setSelectionMode(0);
        itemsList.addListSelectionListener(e -> {
            JList<String> itemList1 = (JList<String>) e.getSource();
            itemName = itemNames.get(itemList1.getSelectedIndex());
            itemDesc = itemDescs.get(itemList1.getSelectedIndex());
        });

        // create a JScrollPane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(itemsList);

        Panel leftPanel = new Panel();
        leftPanel.add(scrollPane);

        // RIGHT SIDE OF SPLIT
        String text = "Item Name: " + itemName + "\n" + "Item Description: " + itemDesc;
        JTextArea desc = new JTextArea(text);

        Panel rightPanel = new Panel();
        rightPanel.add(desc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setBounds(150, 100, 400, 200);

        frame.getContentPane().add(splitPane);

    }
}
