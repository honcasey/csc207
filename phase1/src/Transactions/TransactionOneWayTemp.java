package Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>TransactionOneWayTemp</h1>
 * This class represents a meetup between 2 Users where there is a one way borrowing transaction happening.
 * Note: user 1 owns the item and the second user borrows the item.
 */

public class TransactionOneWayTemp extends Transaction {
    public UUID item;
    public Meeting secondMeeting;

    /**
     * Constructor for TransactionTwowayTemp class. This constructor initializes a 2 way borrowing transaction with
     * the returnTime variable being given a default value of a month from the current date.
     *
     * @param User1         the first user.
     * @param User2         the second user.
     * @param item          the item possessed originally by User1 that will be lent to User2.
     * @param secondMeeting the second meeting details.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionOneWayTemp(UUID User1, UUID User2, UUID item, Meeting FirstMeeting, Meeting secondMeeting,
                                 String item1Name) {
        super(User1, User2, FirstMeeting, item1Name);
        this.item = item;
        this.secondMeeting = secondMeeting;
    }

    /**
     * Getter for item that user1 has.
     * @return returns the item that user1 has at the beginning of the transaction.
     */
    public UUID getItem() {
        return this.item;
    }

    /**
     * Getter for the Second meeting
     * @return  returns the second meeting class variable.
     */

    public Meeting getSecondMeeting() {
        return this.secondMeeting;
    }

    @Override
    public boolean isOneWay() {
        return true;
    }

    @Override
    public boolean isPerm(){return false;}

    @Override
    public String toString(){
        String FirstMeetingString = this.getFirstMeeting().toString();
        String SecondMeetingString = this.getSecondMeeting().toString();
        return("One way transaction to trade "+ getItem1Name() +". Where the first "+ FirstMeetingString +".\n The Second "
                +SecondMeetingString + "."  + "\n Status: " + getStatus());
    }
    @Override
    public List<Meeting> getTransactionMeetings(){
        List<Meeting> MeetingReturnList = new ArrayList<>();
        MeetingReturnList.add(this.getFirstMeeting());
        MeetingReturnList.add(this.getSecondMeeting());
        return(MeetingReturnList);
    }

    @Override
    public List<UUID> getTransactionItems(){
        List<UUID> ItemReturnList = new ArrayList<>();
        ItemReturnList.add(this.getItem());
        return(ItemReturnList);
    }

}

