package TradingUserGUI;

import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionWindow {
    private UserMenuController umc;
    private Item item;
    private TradingUser owner;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();

    public TransactionWindow(UserMenuController umc, Item item, TradingUser owner) {
        this.umc = umc;
        this.item = item;
        this.owner = owner;
    }

    public void display() {
        JFrame frame = new JFrame("Create Transaction");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1280, 720));
        frame.setLocationRelativeTo(null);

        // set the panels on the frame
        frame.add(panel1, BorderLayout.WEST);
        frame.add(panel2, BorderLayout.EAST);

        setupLeftPanel();

        // display the window
        frame.setVisible(true);
    }

    private void setupLeftPanel() {
        panel1.add(new JLabel("Transaction Status"));

        // create the radio buttons
        JRadioButton virtualButton = new JRadioButton("Virtual");
        JRadioButton permButton = new JRadioButton("Permanent");
        JRadioButton tempButton = new JRadioButton("Temporary");

        // group the buttons
        ButtonGroup group = new ButtonGroup();
        group.add(virtualButton);
        group.add(permButton);
        group.add(tempButton);

        // add event handlers for the buttons
        virtualButton.addActionListener(e -> {
            setupRightPanel("Virtual");
        });

        permButton.addActionListener(e -> {
            setupRightPanel("Perm");
        });

        tempButton.addActionListener(e -> {
            setupRightPanel("Temp");
        });

        // add the buttons to the panel
        panel1.add(virtualButton);
        panel1.add(permButton);
        panel1.add(tempButton);
    }

    private void setupRightPanel(String type) {
        panel2.add(new JLabel("Would you like to offer one of your items?"));

        List<Item> inventory = umc.getIm().convertIdsToItems(owner.getInventory());

        // create JComboBox of user's inventory
        JComboBox<Item> comboBox = new JComboBox<>();

        for (Item item : inventory) {
            comboBox.addItem(item);
        }

        // add event handler for comboBox
        comboBox.addActionListener(e -> {
            Item selectedItem = (Item) comboBox.getSelectedItem();
        });

        // add comboBox to panel
        panel2.add(comboBox);

        // add meetings fields
        setupMeetings(type);

        // create and add submit button to panel
        JButton submit = new JButton("Submit");
        panel2.add(submit);

        // add event handler for submit button
    }

    private void setupMeetings(String type) {
        switch (type) {
            case "Perm":
                panel2.add(new JLabel)
                addMeeting();
        }
    }

    private void addMeeting() {

    }
}
