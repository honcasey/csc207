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

    public ViewTransactionHistoryWindow(UserMenuController umc) {
        this.umc = umc;

    }
    public void display(){

        // create the frame
        JFrame frame = new JFrame("View Transaction History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // sets to close the window, but not the program once this is done


        //centre frame
        frame.setSize(new Dimension(550, 550));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

       JButton twoWayButton = new JButton("Most Recent Two Way Transactions");
       twoWayButton.setBounds(50, 50, 150, 75);
       twoWayButton.addActionListener(
               e -> mostRecentTwoWaysWindow()
       );

       JButton oneWayButton = new JButton("Most Recent One Way Transactions");
       oneWayButton.setBounds(200, 50, 150, 75);
       oneWayButton.addActionListener(
               e -> mostRecentOneWaysWindow()
       );

       JButton tradedUsersButton = new JButton("Most traded with Users");
       tradedUsersButton.setBounds(250, 100, 150, 75);
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
        frame.setVisible(true);
        List<UUID> mostRecentOneWays = umc.currentTradingUser.getTransactionHistory().mostRecentOneWayTransactions(); // need to handle this when it's null
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


    }

    public void mostRecentTwoWaysWindow(){
        JFrame frame = new JFrame("Most Recent One Way Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(350, 200));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        List<UUID> mostRecentTwoWays = umc.currentTradingUser.getTransactionHistory().mostRecentTwoWayTransactions(); // need to handle this when it's null

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
        frame.setVisible(true);
        StringBuilder text = new StringBuilder(" ");
        List<String> usernames = umc.currentTradingUser.getTransactionHistory().mostTradedWithUsers();
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
