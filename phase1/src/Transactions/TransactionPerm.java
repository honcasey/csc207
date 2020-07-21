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


    /**
     *
     * @param userToItems
     * @param FirstMeeting
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionPerm(HashMap<UUID,List<UUID>> userToItems, Meeting FirstMeeting){
        super(userToItems,FirstMeeting);
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

    /**
     * NEEDS TO BE FIXED
     * @return returns a string representation
     */
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
}

