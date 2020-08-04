package TradingUserGUI;
import Items.Item;
import Items.ItemManager;
import Transactions.*;
import Users.TradingUser;
import Users.TradingUserManager;
import Users.UserMenuController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ViewTransactionHistoryWindow {
    private final UserMenuController umc;
    private final TradingUser currUser;


    public ViewTransactionHistoryWindow(UserMenuController umc, TradingUser user) {
        this.umc = umc;
        this.currUser = user;
    }
    public void display(){

        // create the frame
        JFrame frame = new JFrame("View Transaction History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sets to close the window, but not the program once this is done


        //centre frame
        frame.setSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

       JButton twoWayButton = new JButton("Most Recent Two Way Transactions");
       twoWayButton.setBounds(600, 300, 100, 50);
       twoWayButton.addActionListener(
               e -> mostRecentTwoWaysWindow()
       );

       JButton oneWayButton = new JButton("Most Recent One Way Transactions");
       oneWayButton.setBounds(600, 400, 100, 50);
       oneWayButton.addActionListener(
               e -> mostRecentOneWaysWindow()
       );

       JButton tradedUsersButton = new JButton("Most traded with Users");
       tradedUsersButton.setBounds(600, 500, 100, 50);
       tradedUsersButton.addActionListener(
               e -> mostTradedWithUsersWindow()
       );

       frame.add(twoWayButton);
       frame.add(oneWayButton);
       frame.add(tradedUsersButton);

    }
    public void mostRecentOneWaysWindow()  { // condense this a bit
        JFrame frame = new JFrame("Most Recent Two Way Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);
        List<UUID> mostRecentOneWays = currUser.getTransactionHistory().mostRecentOneWayTransactions();
        StringBuilder text = new StringBuilder(" ");
        ArrayList<Transaction> allRecentOneWays = umc.getTm().getTransactionsFromIdList(mostRecentOneWays);
        for (Transaction t: allRecentOneWays){
            String id = t.getId().toString();
            String date = t.getTransactionMeetings().get(t.getTransactionMeetings().size()-1).getDate().toString();
            List<UUID> itemIDs = t.getTransactionItems();
            List<Item> items = umc.getIm().convertIdsToItems(itemIDs);
            String transItems = items.toString();
            String users = umc.getUm().getUsernameListByID(t.getUsers()).toString();
            text.append("\n").append("Transaction: ").append(id).append("\n").append("Date: ").append(date).append("\n").append(transItems).append("\n").append(users);
        }
        String fullText = text.toString();

        JTextArea descr = new JTextArea(fullText);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(descr);

        frame.add(descr);
        frame.setVisible(true);

    }

    public void mostRecentTwoWaysWindow(){
        JFrame frame = new JFrame("Most Recent One Way Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        List<UUID> mostRecentTwoWays = currUser.getTransactionHistory().mostRecentTwoWayTransactions();

        StringBuilder text = new StringBuilder(" ");
        ArrayList<Transaction> allRecentOneWays = umc.getTm().getTransactionsFromIdList(mostRecentTwoWays);
        for (Transaction t: allRecentOneWays){
            String id = t.getId().toString();
            String date = t.getTransactionMeetings().get(t.getTransactionMeetings().size()-1).getDate().toString();
            List<UUID> itemIDs = t.getTransactionItems();
            List<Item> items = umc.getIm().convertIdsToItems(itemIDs);
            String transItems = items.toString();
            String users = umc.getUm().getUsernameListByID(t.getUsers()).toString();
            text.append("\n").append("Transaction: ").append(id).append("\n").append("Date: ").append(date).append("\n").append(transItems).append("\n").append(users);
        }

        String fullText = text.toString();

        JTextArea descr = new JTextArea(fullText);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(descr);

        frame.add(descr);
        frame.setVisible(true);

    }

    public void mostTradedWithUsersWindow(){
        JFrame frame = new JFrame("Most Traded With Users");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);

        StringBuilder text = new StringBuilder(" ");
        List<String> usernames = currUser.getTransactionHistory().mostTradedWithUsers();
        for (String u: usernames){
            text.append(u).append("/n");
        }

        String fullText = text.toString();

        JTextArea descr = new JTextArea(fullText);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(descr);

        frame.add(descr);
        frame.setVisible(true);

    }
}
