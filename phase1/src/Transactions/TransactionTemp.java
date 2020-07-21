package Transactions;

import java.util.*;

/**
 * <h1>TransactionTwoWayTemp</h1>
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */
public class TransactionTemp extends Transaction {
    public Meeting secondMeeting;
    private String item2Name;

    /**
     * Constructor for Transactions.TransactionTwoWayPerm class. This constructor initializes a 2 way permanent transaction with
     * @param userToItems A hashmap which maps userId's to a list of
     *      Item ids(where the list is in the form of [ItemId owned, ItemId wanted]).
     * @param firstMeeting This is just a meeting object representing where the users will meet for the first time.
     * @param itemToName A hashmap which maps itemId to the string Name of the item.
     * @param secondMeeting The second meeting in the transaction where the users return the items.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTemp(TreeMap<UUID, List<UUID>> userToItems, Meeting firstMeeting, HashMap<UUID, List<String>> itemToName, Meeting secondMeeting){
        super(userToItems,firstMeeting);
        this.secondMeeting = secondMeeting;
    }

    /**
     * Getter for the second meeting.
     * @return returns the second meeting of the transaction.
     */

    public Meeting getSecondMeeting(){
        return this.secondMeeting;
    }


    @Override
    public boolean isPerm() {
        return false;
    }

    @Override
    public String toString(){
        String FirstMeetingString = this.getFirstMeeting().toString();
        String SecondMeetingString = this.getSecondMeeting().toString();
        return("One way transaction to trade "+ getItem1Name() +" for " + this.item2Name +". Where the first "+ FirstMeetingString
                +". The second" + SecondMeetingString + "."  + "\n Status: " + getStatus());
    }
    @Override
    public List<Meeting> getTransactionMeetings(){
        List<Meeting> MeetingReturnList = new ArrayList<>();
        MeetingReturnList.add(this.getFirstMeeting());
        MeetingReturnList.add(this.getSecondMeeting());
        return(MeetingReturnList);
    }
}