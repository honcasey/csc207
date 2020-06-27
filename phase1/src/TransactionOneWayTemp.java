import java.time.LocalDate;

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
     * Getter for the time
     */

    public Meeting getSecondMeeting() {
        return this.secondMeeting;
    }

    /**
     * Setter for the time
     */

    public void setSecondMeeting(Meeting secondMeeting) {
        this.secondMeeting = secondMeeting;
    }

    @Override
    public boolean isOneWay() {
        return true;
    }

    /**
     * This method changes the second meeting details by passing in a string and an object of the thing
     * you want changed.
     * @param Field this is the detail of the transaction you want to change.
     *              (the values it can take on are listed above:)
     * @param NewVal this is the new value of the detail you want changed.
     * @return this returns true iff the transaction detail was found and changed successfully.
     */
    protected boolean userChangeSecondMeetingByString(String Field, Object NewVal) {
        if(this.getSecondMeeting().getuserEditable().containsKey(Field)){
            this.getSecondMeeting().getuserEditable().put(Field,NewVal);
            return(true);
        }
        else{
            return(false);
        }
    }
}

