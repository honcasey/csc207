package TradingUserGUI;

import Exceptions.InvalidItemException;
import Items.Item;
import Presenters.UserMenuPresenter;
import Transactions.TransactionBuilder;
import Users.TradingUser;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TransactionWindow {
    private UserMenuController umc;
    private final UserMenuPresenter ump = new UserMenuPresenter();
    private Item offeredItem;
    private TradingUser selectedItemOwner;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private TransactionBuilder tb;
    private JFrame frame = new JFrame(ump.createTrans);
    private JButton submit = new JButton("Submit");
    private Item item;
    private Calendar dateCalendar;
    private Calendar timeCalendar;

    public TransactionWindow(UserMenuController umc, UUID itemId, UUID ownerId) throws InvalidItemException {
        this.umc = umc;
        this.tb = umc.GetTransBuilder();
        selectedItemOwner = umc.getUm().getTradingUserById(ownerId);
        tb.declareIntent(ownerId, itemId); // this is the selected item that the user chose from the available items window, which would be the desired item and item's owner
        item = umc.getIm().getItem(itemId);
    }

    public void display() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(1280, 720));
        frame.setLocationRelativeTo(null);

        // set the panels on the frame
        frame.add(panel1, BorderLayout.WEST);

        setupLeftPanel();
        // display the window
        frame.setVisible(true);
    }

    private void setupLeftPanel() {
        panel1.add(new JLabel(ump.tranStatus));

        // create the radio buttons
        JRadioButton virtualButton = new JRadioButton(ump.virtual);
        JRadioButton permButton = new JRadioButton(ump.perm);
        JRadioButton tempButton = new JRadioButton(ump.temp);

        // group the buttons
        ButtonGroup group = new ButtonGroup();
        group.add(virtualButton);
        group.add(permButton);
        group.add(tempButton);

        // add event handlers for the buttons
        virtualButton.addActionListener(e -> {
            try {
                panel2.removeAll();
                setupRightPanel(ump.virtual);
            } catch (InvalidItemException invalidItemException) {
                //
            }
        });

        permButton.addActionListener(e -> {
            try {
                panel2.removeAll();
                setupRightPanel(ump.perm);
            } catch (InvalidItemException invalidItemException) {
                //
            }
        });

        tempButton.addActionListener(e -> {
            try {
                panel2.removeAll();
                setupRightPanel(ump.temp);
            } catch (InvalidItemException invalidItemException) {
                //
            }
        });

        // add the buttons to the panel
        panel1.add(virtualButton);
        panel1.add(permButton);
        panel1.add(tempButton);
    }

    private void setupRightPanel(String type) throws InvalidItemException {
        panel2.add(new JLabel(ump.offerItem));

        // create JList of suggested items
        List<Item> itemSuggestions = umc.getIm().itemSuggestions(umc.getCurrentTradingUser(), selectedItemOwner);
        DefaultListModel<String> itemStrings = new DefaultListModel<>();
        for (Item item : itemSuggestions) {
            itemStrings.addElement(item.toString());
        }
        JList<String> suggestions = new JList<>(itemStrings);

        // create JComboBox of user's inventory
        List<Item> inventory = umc.getIm().convertIdsToItems(umc.getCurrentTradingUser().getInventory());
        JComboBox<Item> comboBox = new JComboBox<>();
        for (Item item : inventory) {
            comboBox.addItem(item);
        }

        // add event handler for comboBox
        comboBox.addActionListener(e -> {
            offeredItem = comboBox.getItemAt(comboBox.getSelectedIndex());
            tb.AddItemOffered(offeredItem.getId()); // this is the item that the current user is offering from their own inventory
        });

        // add comboBoxes to panel
        panel2.add(comboBox);
        panel2.add(suggestions);

        // add meetings fields
        if (type.equals(ump.perm)) {
            panel2 = setMeetingPanel("first");
            submit.addActionListener(e -> {
                try {
                    areYouSureWindow();
                } catch (InvalidItemException invalidItemException) {
                    //
                }
            });
            panel2.add(submit);
        }
        else if (type.equals(ump.temp)) {
            panel2 = setMeetingPanel("first");
            submit.addActionListener(e -> secondMeetingWindow());
            panel2.add(submit);
        } else if (type.equals(ump.virtual)) {
            submit.addActionListener(e -> {
                try {
                    areYouSureWindow();
                } catch (InvalidItemException invalidItemException) {
                    //
                }
            });
            panel2.add(submit);
        }

        frame.add(panel2, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void setDateCalendar() { // helper method for making a date JSpinner
        dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DATE, 1);
        dateCalendar.set(Calendar.MONTH, 1);
        dateCalendar.set(Calendar.YEAR, 2020);
    }

    private void setTimeCalendar() { // helper method for making a time JSpinner
        timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, 12);
        timeCalendar.set(Calendar.MINUTE, 0);
    }

    private JPanel setMeetingPanel(String meetingNum) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(meetingNum + "Meeting"));

        JTextField location = new JTextField("Location");
        location.setBounds(100, 100, 50, 20);

        setDateCalendar();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setValue(dateCalendar.getTime());

        JSpinner meetingDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(meetingDate, "dd/mm/yyyy");
        meetingDate.setEditor(dateEditor);

        setTimeCalendar();
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(timeCalendar.getTime());

        JSpinner meetingTime = new JSpinner(timeModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(meetingTime, "hh:mm");
        meetingTime.setEditor(editor);
        if (meetingNum.equals("first")) {
            tb.buildFirstMeeting(location.getText(), timeModel.getDate(), dateModel.getDate());
        }
        else if (meetingNum.equals("second")) {
            tb.buildSecondMeeting(location.getText(), timeModel.getDate(), dateModel.getDate());
        }

        panel.add(location);
        panel.add(meetingDate);
        panel.add(meetingTime);
        return panel;
    }

    private void areYouSureWindow() throws InvalidItemException {
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to create this transaction?");
        if (a == JOptionPane.YES_OPTION) {
            umc.transactionUpdate(tb.getTransaction());
        }
        if (a == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    private void secondMeetingWindow() {
        JFrame tempFrame = new JFrame("Second Meeting");
        JPanel panel3 = setMeetingPanel("second");
        JButton submit2 = new JButton("Submit Second Meeting");
        submit2.addActionListener(e -> {
            try {
                areYouSureWindow();
            } catch (InvalidItemException invalidItemException) {
                //
            }
        });
        panel3.add(submit2);
        tempFrame.add(panel3);
    }
}