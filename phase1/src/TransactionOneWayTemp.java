import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a meetup between 2 Users where there is a one way borrowing transaction happening.
 * Note: user 1 owns the item and the second user borrows the item.
 */

public class TransactionOneWayTemp extends Transaction {
    public Item item;
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
    public TransactionOneWayTemp(User User1, User User2, Item item, Meeting FirstMeeting, Meeting secondMeeting) {
        super(User1, User2, FirstMeeting);
        this.item = item;
        this.secondMeeting = secondMeeting;
    }


    /**
     * Getter for item that user1 has.
     * @return returns the item that user1 has at the beginning of the transaction.
     */
    //Constructor with a return time manually inputted
    public Item getItem() {
        return this.item;
    }

    /**
     * Setter for item that user1 has.
     */
    public void setItem1(Item item) {
        this.item = item;
    }

    /**
     * Getter for the Second meeting
     * @return  returns the second meeting class variable.
     */

    public Meeting getSecondMeeting() {
        return this.secondMeeting;
    }

    /**
     * Setter for the secondmeeting class variable.
     */

    public void setSecondMeeting(Meeting secondMeeting) {
        this.secondMeeting = secondMeeting;
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
        String ItemString = this.getItem().toString();
        return("One way transaction to trade"+ItemString+"Where the first "+ FirstMeetingString +". The Second "
                +SecondMeetingString );
    }
    @Override
    public List<Meeting> getTransactionMeetings(){
        List<Meeting> MeetingReturnList = new ArrayList<>();
        MeetingReturnList.add(this.getFirstMeeting());
        MeetingReturnList.add(this.getSecondMeeting());
        return(MeetingReturnList);
    }

    @Override
    public List<Item> getTransactionItems(){
        List<Item> ItemReturnList = new ArrayList<>();
        ItemReturnList.add(this.getItem());
        return(ItemReturnList);
    }

}

