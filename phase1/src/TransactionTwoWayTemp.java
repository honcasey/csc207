import java.time.LocalDate;

/**
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionTwoWayTemp extends Transaction{
    public String secondMeetingLocation;
    public Item item1;
    public Item item2;
    public Meeting secondMeeting;

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
    public TransactionTwoWayTemp(User User1, User User2,Item Item1,Item Item2,Meeting FirstMeeting, Meeting secondMeeting){
        super(User1,User2,FirstMeeting);
        this.item1 = Item1;
        this.item2 = Item2;
        this.secondMeeting = secondMeeting;
    }


    /**
     * Getter for item that user1 has.
     */
    //Constructor with a return time manually inputted
    public Item getItem1(){
        return this.item1;
    }

    /**
     * Setter for item that user1 has.
     */
    public void setItem1(Item item1) {
        this.item1 = item1;
    }

    /**
     * Getter for item that user2 has .
     */
    public Item getItem2(){
        return this.item2;
    }

    /**
     * Setter for item that user2 has.
     */

    public void setItem2(Item item2){
        this.item2 = item2;
    }

    /**
     * Getter for the second meeting location.
     */

    public Meeting getSecondMeeting(){
        return this.secondMeeting;
    }

    /**
     * Setter for the second meeting location.
     */

    public void setSecondMeeting(Meeting secondMeeting){
        this.secondMeeting = secondMeeting;
    }
}
