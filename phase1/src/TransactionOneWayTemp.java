import java.time.LocalDate;

/**
 * This class represents a meetup between 2 Users where there is a one way borrowing transaction happening.
 * Note: user 1 owns the item and the second user borrows the item.
 */

public class TransactionOneWayTemp extends Transaction{
    public String secondMeetingLocation;
    public Item item;
    protected LocalDate returnTime;

    /**
     * Constructor for TransactionTwowayTemp class. This constructor initializes a 2 way borrowing transaction with
     * the returnTime variable being given a default value of a month from the current date.
     * @param User1 the first user.
     * @param User2 the second user.
     * @param item the item possessed originally by User1 that will be lent to User2.
     * @param FirstMeetingLocation the first meeting location that the users will meet at to exchange items.
     * @param secondMeetingLocation the second meeting location that the users will meet at to give back the items.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionOneWayTemp(User User1, User User2,Item item, String FirstMeetingLocation
            , String secondMeetingLocation){
        super(User1,User2,FirstMeetingLocation);
        this.item = item;
        this.returnTime = LocalDate.now().plusMonths(1);
        this.secondMeetingLocation = secondMeetingLocation;
    }


    /**
     * Getter for item that user1 has.
     */
    //Constructor with a return time manually inputted
    public Item getItem(){
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

    public LocalDate getReturn_time(){
        return this.returnTime;
    }

    /**
     * Setter for the time
     */

    public void setReturn_time(LocalDate return_time){
        this.returnTime = return_time;
    }

    /**
     * Getter for the second meeting location.
     */

    public String getSecondMeetingLocation(){
        return this.secondMeetingLocation;
    }

    /**
     * Setter for the second meeting location.
     */

    public void setSecondMeetingLocation(String returnMeeting){
        this.secondMeetingLocation = returnMeeting;
    }
}
