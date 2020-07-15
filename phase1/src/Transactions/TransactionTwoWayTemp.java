package Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>TransactionTwoWayTemp</h1>
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */
public class TransactionTwoWayTemp extends Transaction {
    public UUID item1;
    public UUID item2;
    public Meeting secondMeeting;
    private String item2Name;

    /**
     * Constructor for TransactionTwowayTemp class. This constructor initializes a 2 way borrowing transaction with
     * the returnTime variable being given a default value of a month from the current date.
     * @param User1 the first user.
     * @param User2 the second user.
     * @param Item1 the item possessed originally by User1.
     * @param Item2 the item possessed originally by User2
     * @param secondMeeting the second meeting details.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTwoWayTemp(UUID User1, UUID User2, UUID Item1, UUID Item2, Meeting FirstMeeting,
                                 Meeting secondMeeting, String item1Name, String item2Name){
        super(User1,User2,FirstMeeting, item1Name);
        this.item1 = Item1;
        this.item2 = Item2;
        this.secondMeeting = secondMeeting;
        this.item2Name = item2Name;
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
    public boolean isOneWay() {
        return false;
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

    @Override
    public List<UUID> getTransactionItems(){
        List<UUID> ItemReturnList = new ArrayList<>();
        ItemReturnList.add(this.getItem1());
        ItemReturnList.add(this.getItem2());
        return(ItemReturnList);
    }
}