package Transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a meetup between 2 Users where there is a two way permanent transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionTwoWayPerm extends Transaction {
    public UUID item1;
    public UUID item2;

    /**
     * Constructor for Transactions.TransactionTwoWayPerm class. This constructor initializes a 2 way permanent transaction with
     * @param User1 the first user.
     * @param User2 the second user.
     * @param Item1 the item possessed originally by User1.
     * @param Item2 the item possessed originally by User2
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTwoWayPerm(UUID User1, UUID User2, UUID Item1, UUID Item2, Meeting firstMeeting){
        super(User1,User2,firstMeeting);
        this.item1 = Item1;
        this.item2 = Item2;
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
     * Setter for item that user1 has.
     */
    public void setItem1(UUID item1) {
        this.item1 = item1;
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
     * Setter for item that user2 has.
     */
    public void setItem2(UUID item2){
        this.item2 = item2;
    }

    @Override
    public boolean isOneWay() {
        return false;
    }

    @Override
    public String toString(){
        String FirstMeetingString = this.getFirstMeeting().toString();
        String Item1String = this.getItem1().toString();
        String Item2String = this.getItem2().toString();
        return("One way transaction to trade"+Item1String+" for " +Item2String +"Where the"+ FirstMeetingString +".");
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

