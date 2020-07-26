package Admins;

import Items.Item;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class CheckPendingItemsWindow {
    private final AdminMenuController amc;

    public CheckPendingItemsWindow(AdminMenuController amc) {
        this.amc = amc;
    }

    public void display() {
        // create the frame
        JFrame frame = new JFrame("Pending Items");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set the frame's size and centre it
        frame.setSize(new Dimension(500, 250));
        frame.setLocationRelativeTo(null);

        // making a SplitPane
        JLabel l = new JLabel();
        l.setSize(500,100);

        // approve item button
        JButton approve = new JButton("Approve");
        approve.setBounds(200,150,80,30);

        // reject item button
        JButton reject = new JButton("Reject");
        reject.setBounds(200, 75, 80, 30);

        // list of all pending items for list on left side of SplitPane
        DefaultListModel<String> pendingItems = new DefaultListModel<>();
        for (Item curr : amc.allPendingItems.keySet()) {
            pendingItems.addElement(curr.toString());
        }

        // create JList
        JList<String> items = new JList<>(pendingItems);
        items.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        items.setSelectedIndex(0);
        items.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList items = (JList)e.getSource();
                // render which text to show (map the key of item selected to the value of it, probs need a toString for item desc )
            }
        });

        // create JScrollPane (makes list scrollable)
        JScrollPane spane = new JScrollPane();
        spane.getViewport().add(items);

        Panel leftPanel = new Panel();
        leftPanel.add(spane);

        // create item info into text box on right side of SplitPane
        JTextArea desc = new JTextArea();

        Panel rightPanel = new Panel();
        rightPanel.add(desc);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

        frame.add(splitPane);

    }
}