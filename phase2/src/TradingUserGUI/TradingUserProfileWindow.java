package TradingUserGUI;

import Transactions.PastTransactionManager;
import Users.TradingUser;

import javax.swing.*;

public class TradingUserProfileWindow {
    private JTextArea weeklyThreshold;
    private JTextArea incompleteThreshold;
    private JTextArea numWishlist;
    private JTextArea numActiveTransactions;
    private JTextArea NumLended;
    private JTextArea NumBorrowed;
    private JTextArea NumCompleteTransactions;
    private JTextArea NumCancelledTransactions;
    private JTextArea Score;
    private JTextArea numInventory;
    private JTextArea UserStatus;
    private JTextArea borrowThreshold;
    private static PastTransactionManager ptm;
    private static TradingUser currUser;
    private JPanel mainPanel;
    private JTextArea username;


    public TradingUserProfileWindow(PastTransactionManager ptm, TradingUser currUser){
        this.ptm = ptm;
        this.currUser = currUser;
    }

    public void display(){
        JFrame frame = new JFrame("UserProfile");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        weeklyThreshold.setText(Integer.toString(currUser.getWeeklyThreshold()));
        borrowThreshold.setText(Integer.toString(currUser.getBorrowThreshold()));
        incompleteThreshold.setText(Integer.toString(currUser.getIncompleteThreshold()));
        username.setText(currUser.getUsername());
        numInventory.setText(Integer.toString(currUser.getInventory().size()));
        UserStatus.setText(currUser.getStatus().toString());
        numWishlist.setText(Integer.toString(currUser.getWishlist().size()));
        numActiveTransactions.setText(Integer.toString(currUser.getCurrentTransactions().size()));

        int lended = currUser.getTransactionHistory().getNumItemsLended();
        int borrowed = currUser.getTransactionHistory().getNumItemsBorrowed();
        int cancelled = ptm.getNumCancelledTransactions(currUser);
        int complete = ptm.getNumCompletedTransactions(currUser);
        NumLended.setText(Integer.toString(lended));
        NumBorrowed.setText(Integer.toString(borrowed));
        NumCancelledTransactions.setText(Integer.toString(cancelled));
        NumCompleteTransactions.setText(Integer.toString(complete));
        Score.setText(Integer.toString(lended - borrowed + complete - cancelled));


    }

}
