package Transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <h1>TransactionTwoWayPerm</h1>
 * This class represents a meetup between 2 Users where there is a two way permanent transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionPerm extends Transaction {
    public UUID item1;
    public UUID item2;
    private String item2Name;
    private boolean isOneWay;

    /**
     * Constructor for Transactions.TransactionTwoWayPerm class. This constructor initializes a 2 way permanent transaction with
     * @param userToItems A hashmap which maps userId's to a list of
     *      Item ids(where the list is in the form of [ItemId owned, ItemId wanted]).
     * @param firstMeeting This is just a meeting object representing where the users will meet for the first time.
     * @param itemToName A hashmap which maps itemId to the string Name of the item
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionPerm(HashMap<UUID, List<UUID>> userToItems, Meeting firstMeeting, HashMap<UUID, List<String>> itemToName, Boolean isOneWay){
        super(userToItems,firstMeeting, itemToName);
        this.isOneWay = isOneWay;
    }

    /**
     * Getter for item that user1 has.
     * @return returns the item that was owned by user1 at the beginning of the transaction.
     */
    //Constructor with a return time manually inputted
    public UUID getItem1(){
        return this.item1;
    }

    /**
     * Getter for item that user2 has.
     * @return this returns the item that user2 owned at the beginning of the transaction.
     */
    public UUID getItem2(){
        return this.item2;
    }

    @Override
    public boolean isPerm() {
        return true;
    }

    @Override
    public boolean isOneWay() {
        return false;
    }

    @Override
    public String toString(){
        String FirstMeetingString = this.getFirstMeeting().toString();
        return("One way transaction to trade "+ getItem1Name() +" for " + this.item2Name +". Where the "+ FirstMeetingString +"."  +
                "\n Status: " + getStatus());
    }
    @Override
    public List<Meeting> getTransactionMeetings(){
        List<Meeting> MeetingReturnList = new ArrayList<>();
        MeetingReturnList.add(this.getFirstMeeting());
        return(MeetingReturnList);
    }

    @Override
    public List<UUID> getTransactionItems(){
        List<UUID> ItemReturnList = new ArrayList<>();
        ItemReturnList.add(this.getItem1());
        ItemReturnList.add(this.getItem2());
        return(ItemReturnList);
    }

}

