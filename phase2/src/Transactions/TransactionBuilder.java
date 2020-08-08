package Transactions;

import Items.Item;
import Users.TradingUser;

import java.util.*;

public class TransactionBuilder {

    private List<Meeting> meetingList;
    private TradingUser currentUser;
    private TradingUser otherUser;
    private TreeMap<UUID, List<UUID>> userToItems;
    private TransactionFactory transFact;

    /**
     * This is the builder for a transaction. This will be called by GUI/UserMenuController.
     * @param currentUser The current user using the program.
     * @param transFact An instance of Transaction Factory.
     */
    public TransactionBuilder(TradingUser currentUser, TransactionFactory transFact){
        this.currentUser = currentUser;
        this.transFact = transFact;
        this.userToItems = new TreeMap<UUID, List<UUID>>();
    }

    /**
     * @param otherUser The other user that you want to make the transaction with.
     * @param desiredItem The item that the currentUser wants from the transaction.(whether they are borrowing or
     *            keeping it.)
     */
    public void declareIntent(TradingUser otherUser, UUID desiredItem){
        this.otherUser = otherUser;

        List<UUID> currentUserItemIds = new ArrayList<>();

        currentUserItemIds.add(null);
        currentUserItemIds.add(desiredItem);
        this.userToItems.put(currentUser.getUserId(),currentUserItemIds);

        List<UUID> otherUserItemIds = new ArrayList<>();

        otherUserItemIds.add(desiredItem);
        otherUserItemIds.add(null);
        this.userToItems.put(otherUser.getUserId(),otherUserItemIds);

    }

    public void buildFirstMeeting(String location, Date meetingTime, Date meetingDate){
        this.meetingList.add(new Meeting(location,meetingTime,meetingDate));
    }

    public void buildSecondMeeting(String location, Date meetingTime, Date meetingDate){
        this.meetingList.add(new Meeting(location,meetingTime,meetingDate));
    }

    public void AddItemOffered(UUID otherUserItem){
        this.userToItems.get(currentUser.getUserId()).set(0,otherUserItem);
        this.userToItems.get(otherUser.getUserId()).set(1,otherUserItem);
    }

    public Transaction getTransaction(){
        return transFact.createTransaction(userToItems,meetingList);
    }
}
