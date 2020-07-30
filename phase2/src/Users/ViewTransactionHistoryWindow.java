package Users;
import Items.Item;
import Items.ItemManager;
import Transactions.*;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewTransactionHistoryWindow {
    private final CurrentTransactionManager ctm;
    private final TradingUser currUser;
    private final ItemManager im;
    private final TradingUserManager tum;
    private List<UUID> mostRecentOneWays;
    private List<UUID> mostRecentTwoWays;
    private List<String> mostTradedWithUsers;

    public ViewTransactionHistoryWindow(CurrentTransactionManager ctm, ItemManager im, TradingUserManager tum, TradingUser user) {
        this.ctm = ctm;
        this.im = im;
        this.tum = tum;
        this.currUser = user;
    }
    public void display(){

        // create the frame
        JFrame frame = new JFrame("View Transaction History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sets to close the window, but not the program once this is done


        //centre frame
        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        // left panel
        DefaultListModel<String> thOptionsStr = new DefaultListModel<>();
        thOptionsStr.addElement("Three Most Recent One Way Transactions");
        thOptionsStr.addElement("Three Most Recent Two Way Transactions");
        thOptionsStr.addElement("Most Traded With Users");

        /// make JList
        JList<String> thOptions = new JList<>(thOptionsStr);
        thOptions.setVisibleRowCount(3); // set how many selections we can see in one frame
        thOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        thOptions.setSelectedIndex(0);
        thOptions.addListSelectionListener(e -> { // updates with the information of the current user's transaction history
            mostRecentOneWays = currUser.getTransactionHistory().mostRecentOneWayTransactions();
            mostRecentTwoWays = currUser.getTransactionHistory().mostRecentTwoWayTransactions();
            mostTradedWithUsers= currUser.getTransactionHistory().mostTradedWithUsers();
        });

        /// by setting visible row count to be three (the total number of options in this window), you don't need scrolling


        // right panel

        // JSplit pane


        // go back button
        JButton jb = new JButton("Go Back");

    }
    public void mostRecentOneWaysWindow()  { // condense this a bit
        StringBuilder text = new StringBuilder(" ");
        ArrayList<Transaction> allRecentOneWays = ctm.getTransactionsFromIdList(mostRecentOneWays);
        for (Transaction t: allRecentOneWays){
            String id = t.getId().toString();
            String date = t.getTransactionMeetings().get(t.getTransactionMeetings().size()-1).getDate().toString();
            List<UUID> itemIDs = t.getTransactionItems();
            List<Item> items = im.convertIdsToItems(itemIDs);
            String transItems = items.toString();
            String users = tum.getUsernameListByID(t.getUsers()).toString();
            text.append("\n").append("Transaction: ").append(id).append("\n").append("Date: ").append(date).append("\n").append(transItems).append("\n").append(users);
        }

    }

    public void mostRecentTwoWaysWindow(){

    }

    public void mostTradedWithUsersWindow(){

    }
}
