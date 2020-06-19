import java.time.LocalDate;

/**
 * This class represents a meetup between 2 Users where there is a two way borrowing transaction happening.
 * Note: user 1 originally had item 1 and user 2 originally had item 2.
 */

public class TransactionTwoWayTemp extends Transaction{
    public String secondMeetingLocation;
    public Item item1;
    public Item item2;
    protected LocalDate returnTime;

    /**
     * Constructor for TransactionTwowayTemp class. This constructor initializes a 2 way borrowing transaction with
     * the returnTime variable being given a default value of a month from the current date.
     * @param User1 the first user.
     * @param User2 the second user.
     * @param Item1 the item possessed originally by User1.
     * @param Item2 the item possessed originally by User2
     * @param FirstMeetingLocation the first meeting location that the users will meet at to exchange items.
     * @param secondMeetingLocation the second meeting location that the users will meet at to give back the items.
     */
    //Constructor with no return time given (default is a month (31 days))
    public TransactionTwoWayTemp(User User1, User User2,Item Item1,Item Item2, String FirstMeetingLocation
            , String secondMeetingLocation){
        super(User1,User2,FirstMeetingLocation);
        this.item1 = Item1;
        this.item2 = Item2;
        this.returnTime = LocalDate.now().plusMonths(1);
        this.secondMeetingLocation = secondMeetingLocation;
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
