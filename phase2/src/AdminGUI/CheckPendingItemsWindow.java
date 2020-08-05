package AdminGUI;

import Admins.AdminMenuController;
import Items.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckPendingItemsWindow {
    private final AdminMenuController amc;
    private Item selectedItem;
    private String itemName; // name of item
    private String itemDesc; // item description
    private String itemUser; // who the item belongs to

    public CheckPendingItemsWindow(AdminMenuController amc) { this.amc = amc; }

    /**
     * Part of code taken from:
     * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java
     */
    public void display() {
        // create the frame
        JFrame frame = new JFrame("Pending Items");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // closes the current frame but doesn't terminate the app

        // set the frame's size and centre it
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        // LEFT SIDE OF SPLITPANE
        // scrollable list of all pending items
        DefaultListModel<String> pendingItems = new DefaultListModel<>();
        DefaultListModel<String> itemDescs = new DefaultListModel<>();
        ArrayList<Item> listItems = new ArrayList<>();
        for (Item curr : amc.getAllPendingItems().keySet()) {
            listItems.add(curr);
            pendingItems.addElement(curr.toString());
            itemDescs.addElement(curr.getDescription());
        }

        // create JList
        JList<String> items = new JList<>(pendingItems);
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.setSelectedIndex(0);
        items.addListSelectionListener(e -> {
            // JList<String> items1 = (JList<String>)e.getSource(); // TO-DO: i don't think below is being updated properly
            itemUser = amc.getAllPendingItems().get(items.getSelectedValue()).toString();
            itemName = pendingItems.get(items.getSelectedIndex());
            itemDesc = itemDescs.get(items.getSelectedIndex());
        });

        // create JScrollPane (makes list scrollable)
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(items);

        Panel leftPanel = new Panel();
        leftPanel.add(scrollPane);

        // RIGHT SIDE OF SPLITPANE
        // create item info into text area
        String text = "Item Name: " + itemName + "\n" + "Item Description: " + itemDesc + "\n" + "Item Owner: " + itemUser;
        JTextArea desc = new JTextArea(text);

        Panel rightPanel = new Panel();
        rightPanel.add(desc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setBounds(150, 100, 400, 200);
        splitPane.setDividerLocation(150);
        splitPane.setOneTouchExpandable(true);

        frame.getContentPane().add(splitPane);

        //APPROVE AND REJECT BUTTONS
        Panel sidePanel = new Panel();
        // if Admin clicked approve item
        JButton approve = new JButton("Approve");
        approve.setBounds(600,100,100,50);
        approve.addActionListener(e -> amc.approvePendingItem(listItems.get(items.getSelectedIndex())));

        // if Admin clicked reject item
        JButton reject = new JButton("Reject");
        reject.setBounds(600, 300, 100, 50);
        reject.addActionListener(e -> amc.rejectPendingItem(listItems.get(items.getSelectedIndex())));

        sidePanel.add(approve);
        sidePanel.add(reject);
        frame.add(sidePanel);

        frame.setVisible(true);
    }
}