package TradingUserGUI;

import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewWishlistWindow {
    private UserMenuController umc;
    private String itemName;
    private String itemDesc;

    public ViewWishlistWindow(UserMenuController umc) {
        this.umc = umc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("Wishlist");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes the current frame but doesn't terminate the app

        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // LEFT SIDE OF SPLIT
        List<Item> items = umc.getIm().convertIdsToItems(umc.currentTradingUser.getWishlist());
        DefaultListModel<String> itemNames = new DefaultListModel<>();
        DefaultListModel<String> itemDescs = new DefaultListModel<>();
        for (Item item : items) {
            itemNames.addElement(item.toString());
            itemDescs.addElement(item.getDescription());
        }
        JList<String> itemsList = new JList<>(itemNames);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setSelectedIndex(0);
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

        // Remove Button
        JButton removeB = new JButton("Remove");
        removeB.setBounds(600, 300, 100, 50);
        // make a JOptionPane when JButton is pressed
        removeB.addActionListener(e -> {
            int input = JOptionPane.showOptionDialog(null, "Are you sure you want to remove this item from your Wishlist?", "Remove Item?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(input == JOptionPane.YES_OPTION) {
                itemNames.remove(itemsList.getSelectedIndex()); // if YES, remove the item
            }
        });

        // subpanel to hold the Remove button
        Panel subPanel = new Panel();
        subPanel.add(removeB);

        // Add the Remove Button on to the Frame
        frame.add(subPanel);

    }
}
