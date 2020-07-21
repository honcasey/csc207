package Transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <h1>TransactionTwoWayTemp</h1>
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */
public class TransactionTemp extends Transaction {
    public UUID item1;
    public UUID item2;
    public Meeting secondMeeting;
    private String item2Name;

    /**
     * This method constructs a temporary transaction.
     * @param userToItems A hashmap which maps userids to a list of
     *            Item ids(where the list is in the form of [Itemid owned, Itemid wanted])
     * @param FirstMeeting This is the first meeting of the transaction.
     * @param secondMeeting This is the second meeting of the transaction.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTemp(HashMap<UUID,List<UUID>> userToItems,Meeting FirstMeeting,
                           Meeting secondMeeting){
        super(userToItems,FirstMeeting);
        this.secondMeeting = secondMeeting;
    }

    /**
     * Getter for item that user1 has.
     * @return returns the item that user1 has at the beginning of the transaction.
     */
    //Constructor with a return time manually inputted
    public UUID getItem1(){
        return this.item1;
    }

    /**
     * Getter for item that user2 has.
     * @return returns the item that user2 has at the beginniing of the transaction.
     */
    public UUID getItem2(){
        return this.item2;
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

    /**
     * WE NEED TO CHANGE THIS TOSTRING METHOD.
     */
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