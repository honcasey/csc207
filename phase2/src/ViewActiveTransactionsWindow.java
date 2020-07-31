import Presenters.MenuPresenter;
import Transactions.Actions;
import Transactions.Meeting;
import Transactions.Statuses;
import Transactions.Transaction;
import Users.UserMenuController;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewActiveTransactionsWindow {
    private final MenuPresenter mp;
    private final UserMenuController umc;
    private Transaction selectedTransaction;
    private Meeting selectedMeeting;
    private int whichMeetingSelected;
    private String transactionDetails;
    private String inputLocation;
    private Calendar dateCalendar;
    private Calendar timeCalendar;
    private Date inputDate;
    private Date inputTime;

    public ViewActiveTransactionsWindow(MenuPresenter mp, UserMenuController umc) {
        this.mp = mp;
        this.umc = umc; }

    public void display() {
        List<Transaction> allTransactions = umc.currentTransactionList();

        JFrame frame = new JFrame("Active Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        DefaultListModel<String> transactionList = new DefaultListModel<>();
        for (Transaction transaction : allTransactions) {
            transactionList.addElement(transaction.toString()); //
            // THERE IS NO toString Implemented (use the method in menupresenter)

        }

        JList<String> trans = new JList<>(transactionList);
        trans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trans.setSelectedIndex(0);
        trans.addListSelectionListener(e -> {
            JList<String> trans1 = (JList<String>)e.getSource();
            selectedTransaction = allTransactions.get(trans1.getSelectedIndex());
            transactionDetails = mp.getTransactionString(selectedTransaction, umc.currentTradingUser);
        });

        JScrollPane scrollPane = new JScrollPane(); // makes the list scrollable
        scrollPane.getViewport().add(trans);

        Panel leftPanel = new Panel();
        leftPanel.setLayout(null);
        leftPanel.add(scrollPane);

        JTextArea desc = new JTextArea(transactionDetails);

        Panel rightPanel = new Panel();
        rightPanel.setLayout(null);
        rightPanel.add(desc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setBounds(150, 100, 400, 200);

        frame.getContentPane().add(splitPane);

        JButton options = new JButton("Options");
        options.setBounds(600,100,100,50);
        options.addActionListener(e -> {
            Statuses transactionStatus = allTransactions.get(trans.getSelectedIndex()).getStatus();
            switch (transactionStatus) { // opens a different window depending on the status of the selected transaction
                case PENDING:
                    pendingTransactionWindow();
                    break;
                case TRADED:
                    tradedTransactionWindow();
                    break;
                case CONFIRMED:
                    confirmedTransactionWindow();
                    break;
            }
        });

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(null);
        bottomPanel.add(options);
        frame.add(bottomPanel);
    }

    public void pendingTransactionWindow() {
        JFrame frame = new JFrame("Edit Meeting");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        Panel leftPanel = new Panel();
        leftPanel.setLayout(null);

        // edit meeting stuff
        // which meeting
        JComboBox<String> whichMeeting = new JComboBox<>();
        List<Meeting> transactionMeetings = selectedTransaction.getTransactionMeetings();
        for (Meeting meeting : transactionMeetings) {
            whichMeeting.addItem(meeting.toString());
        }

        whichMeeting.addActionListener(e -> {
            selectedMeeting = transactionMeetings.get(whichMeeting.getSelectedIndex());
            inputDate = selectedMeeting.getDate();
            inputLocation = selectedMeeting.getLocation();
            inputTime = selectedMeeting.getTime();
            whichMeetingSelected = whichMeeting.getSelectedIndex();
        });

        // location
        JTextField location = new JTextField("Location");
        location.setBounds(100, 100, 50, 20);
        location.addActionListener(e -> inputLocation = location.getText());

        // date
        setDateCalendar();
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setValue(dateCalendar.getTime());

        JSpinner meetingDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(meetingDate, "dd:mm:yyyy");
        meetingDate.setEditor(dateEditor);
        meetingDate.addChangeListener(e -> inputDate = dateModel.getDate());

        // time
        setTimeCalendar();
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeModel.setValue(timeCalendar.getTime());

        JSpinner meetingTime = new JSpinner(timeModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(meetingTime, "hh:mm");
        meetingTime.setEditor(editor);
        meetingTime.addChangeListener(e -> inputTime = timeModel.getDate());

        leftPanel.add(location);
        leftPanel.add(meetingDate);
        leftPanel.add(meetingTime);
        frame.add(leftPanel);

        Panel rightPanel = new Panel();
        rightPanel.setLayout(null);
        // button to submit edit meeting changes
        JButton updateMeeting = new JButton("Update Meeting");
        updateMeeting.setBounds(100, 250, 100, 50);
        updateMeeting.addActionListener(e -> editMeeting());

        // button to confirm finalized meeting details
        JButton finalizeMeeting = new JButton("Finalize Meeting Details");
        finalizeMeeting.setBounds(100, 350, 100, 50);
        finalizeMeeting.addActionListener(e -> {
            umc.updateUsers(selectedTransaction, Actions.CONFIRMMEETINGDETAILS);
            confirmedMeeting();
        });

        // button to cancel transaction
        JButton cancelMeeting = new JButton("Cancel Transaction");
        cancelMeeting.setBounds(100, 450, 100, 50);
        cancelMeeting.addActionListener(e -> areYouSureWindow());

        rightPanel.add(updateMeeting);
        rightPanel.add(finalizeMeeting);
        rightPanel.add(cancelMeeting);

        frame.add(rightPanel);
        frame.setVisible(true);
    }

    private void setDateCalendar() { // helper method for making a date JSpinner
        dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        dateCalendar.set(Calendar.MONTH, 1);
        dateCalendar.set(Calendar.YEAR, 2020);
    }

    private void setTimeCalendar() { // helper method for making a time JSpinner
        timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, 24);
        timeCalendar.set(Calendar.MINUTE, 0);
    }

    private void editMeeting() { // helper method to check if edit threshold has been reached or not if user clicked "edit meeting"
        if (!umc.editMeetingFlow(umc.currentTradingUser.getUserId(), selectedTransaction, whichMeetingSelected,
                inputLocation, inputTime, inputDate)) {
            PopUpWindow edited = new PopUpWindow("Successfully edited meeting");
            edited.display();
        }
        else {
            PopUpWindow thresholdReached = new PopUpWindow("Edit Threshold Reached. You cannot make any more edits.");
            thresholdReached.display();
        }
    }

    private void confirmedMeeting() { // helper method that pops up window depending on if other user has confirmed meeting details yet
        if (umc.userStatuses(selectedTransaction)) {
            PopUpWindow waiting = new PopUpWindow("Waiting for other user to confirm meeting.");
            waiting.display();
        }
        else {
            PopUpWindow confirmed = new PopUpWindow("Meeting has been confirmed.");
            confirmed.display();
        }
    }

    /* https://www.javatpoint.com/java-joptionpane#:~:text=%E2%86%92%20%E2%86%90%20prev-,Java%20JOptionPane,JOptionPane%20class%20inherits%20JComponent%20class. */
    private void areYouSureWindow() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int a = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel the transaction?");
        if (a == JOptionPane.YES_OPTION) {
            umc.updateUsers(selectedTransaction, Actions.CANCEL);
            cancelledWindow();
        }
    }

    private void cancelledWindow() {
        PopUpWindow cancelled = new PopUpWindow("Transaction has been cancelled.");
        cancelled.display();
    }

    public void tradedTransactionWindow() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton confirm = new JButton("Confirm item has been returned");
        confirm.setBounds(100, 100, 100, 50);
        confirm.addActionListener(e -> {
            umc.updateUsers(selectedTransaction, Actions.ITEMRETURNED);
            PopUpWindow confirmed = new PopUpWindow("Item return has been confirmed");
            confirmed.display();
        });

        JButton claim = new JButton("Claim item has not been returned");
        claim.setBounds(250, 100, 100, 50);
        claim.addActionListener(e -> {
            umc.updateUsers(selectedTransaction, Actions.ITEMNOTRETURNED);
            cancelledWindow();
        });

        panel.add(confirm);
        panel.add(claim);
        frame.add(panel);
        frame.setVisible(true);
    }

    public void confirmedTransactionWindow() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton confirm = new JButton("Confirm exchange has taken place");
        confirm.setBounds(100, 100, 100, 50);
        confirm.addActionListener(e -> {
            umc.updateUsers(selectedTransaction, Actions.CONFIRMMEETUP);
            PopUpWindow confirmed = new PopUpWindow("Meetup occurrence confirmed");
            confirmed.display();
        });

        JButton claim = new JButton("Claim the exchange has not taken place");
        claim.setBounds(250, 100, 100, 50);
        claim.addActionListener(e -> {
            umc.updateUsers(selectedTransaction, Actions.MEETUPINCOMPLETE);
            cancelledWindow();
        });

        panel.add(confirm);
        panel.add(claim);
        frame.add(panel);
        frame.setVisible(true);
    }
}
