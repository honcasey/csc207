import Transactions.Meeting;
import Transactions.Statuses;
import Transactions.Transaction;
import Users.UserMenuController;
import Users.UserMenuPresenter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


public class ViewActiveTransactionsWindow {
    private final UserMenuController umc;
    private final UserMenuPresenter ump;
    private Transaction selectedTransaction;
    private Meeting selectedMeeting;
    private int whichMeetingSelected;
    private String transactionDetails;
    private String inputLocation;
    private Calendar calendar;
    private Date inputDate;
    private Date inputTime;

    public ViewActiveTransactionsWindow(UserMenuController umc, UserMenuPresenter ump) {
        this.umc = umc;
        this.ump = ump;
    }

    public void display() {
        List<Transaction> allTransactions = umc.currentTransactionList();

        JFrame frame = new JFrame("Active Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        DefaultListModel<String> transactionList = new DefaultListModel<>();
        for (Transaction transaction : allTransactions) {
            transactionList.addElement(transaction.toString());
        }

        JList trans = new JList(transactionList);
        trans.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        trans.setSelectedIndex(0);
        trans.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList trans1 = (JList)e.getSource();
                selectedTransaction = allTransactions.get(trans1.getSelectedIndex());
                transactionDetails = allTransactions.get(trans1.getSelectedIndex()).toString();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(trans);

        Panel leftPanel = new Panel();
        leftPanel.add(scrollPane);

        JTextArea desc = new JTextArea(transactionDetails);

        Panel rightPanel = new Panel();
        rightPanel.add(desc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setBounds(150, 100, 400, 200);

        frame.getContentPane().add(splitPane);

        JButton options = new JButton("Options");
        options.setBounds(600,100,100,50);
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String transactionStatus = allTransactions.get(trans.getSelectedIndex()).getStatus();
                switch (transactionStatus) {
                    case Statuses.PENDING:
                        pendingTransactionWindow();
                        break;
                    case Statuses.TRADED:
                        tradedTransactionWindow();
                        break;
                    case Statuses.CONFIRMED:
                        confirmedTransactionWindow();
                        break;
                }
            }
        });

        Panel bottomPanel = new Panel();
        bottomPanel.add(options);
        frame.add(bottomPanel);

    }

    /*
     * http://www.java2s.com/Tutorials/Java/Data_Type_How_to/Date_Convert/Convert_LocalTime_to_java_util_Date.htm
     */
    private Date localTimeToDate(LocalTime time) {
        return Date.from(time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant());
    }

    /* converts LocalDate to Date */
    private Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void pendingTransactionWindow() {
        JFrame frame = new JFrame("Edit Meeting");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 500));
        frame.setLocationRelativeTo(null);

        Panel leftPanel = new Panel();

        // edit meeting stuff
        // which meeting
        JComboBox whichMeeting = new JComboBox();
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
        location.addActionListener(e -> {
            inputLocation = location.getText();
        });

        // date
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.set(Calendar.DAY_OF_MONTH, 1);
        dateCalendar.set(Calendar.MONTH, 1);
        dateCalendar.set(Calendar.YEAR, 2020);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dateModel.setValue(dateCalendar.getTime());

        JSpinner meetingDate = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(meetingDate, "dd:mm:yyyy");
        meetingDate.setEditor(dateEditor);
        meetingDate.addChangeListener(e -> inputDate = dateModel.getDate());

        // time
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, 24);
        timeCalendar.set(Calendar.MINUTE, 0);
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
        // button to submit edit meeting changes
        JButton updateMeeting = new JButton("Update Meeting");
        updateMeeting.setBounds(100, 250, 100, 50);
        updateMeeting.addActionListener(e -> {
            if (!umc.editMeetingFlow(umc.currentTradingUser.getUserId(), selectedTransaction, whichMeetingSelected,
                    inputLocation, inputTime, inputDate)) {
                PopUpWindow edited = new PopUpWindow("Successfully edited meeting");
                edited.display();
            }
            else {
                PopUpWindow thresholdReached = new PopUpWindow("Edit Threshold Reached. You cannot make any more edits.");
                thresholdReached.display();
            }
        });

        // button to confirm finalized meeting details
        JButton finalizeMeeting = new JButton("Finalize Meeting Details");
        finalizeMeeting.setBounds(100, 350, 100, 50);
        finalizeMeeting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        rightPanel.add(updateMeeting);

    }

    public void tradedTransactionWindow() {

    }

    public void confirmedTransactionWindow() {

    }
}
