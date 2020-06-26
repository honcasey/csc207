import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

/**
 * This abstract class represents a meetup between 2 Users.
 * Variables:
 *
 * user1, user2: user involved in the transaction.
 *
 * firstMeeting: The first meeting of a transaction.
 *
 * status: The possible values of this and what they mean are:
 * -- "Pending" -- Status given initially while transaction is still being negotiated.
 * -- "Confirmed" -- status given when details of all meetings involved in transaction has been confirmed by users.
 * -- "Traded" -- first meeting has been confirmed by both users as taken place.(only used for one way)
 * -- "Complete" -- the last meeeting of the transaction has happened and confirmed by both users.
 * -- "Cancelled" -- transaction has been cancelled. The transaction can only be in this state after pending
 *                   (too many times edited).
 * -- "No-Show" -- there was a no show at meeting 1.
 * -- "Never Returned" -- there was a no show at meeting 2 and items were not returned.(only used for two-way)
 */
public abstract class Transaction {
    private UUID id = UUID.randomUUID();
    private User user1;
    private User user2;
    private Meeting firstMeeting;
    private String status;

    /**
     * Constructor of abstract class Transaction.
     * @param user_1 one of the users involved in the transactions.
     * @param user_2 one of the users involved in the transactions.
     */
    public Transaction(User user_1, User user_2, Meeting firstMeeting){
        status = "pending";
        this.user1 = user_1;
        this.user2 = user_2;
        this.firstMeeting = firstMeeting;
    }

    /**
     * Setter for status. This will be called by use case classes.
     */

    public void setStatus(String newStatus){
        status = newStatus;
    }

    /**
     * Getter for status. This will be called by use case classes.
     */
    public String getStatus(){
        return status;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */

    public void setUser1(User user1) {
        this.user1 = user1;
    }
    /**
     * getter for user1. This will be called by use case classes.
     */

    public User getUser1(){
        return this.user1;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */

    public void setUser2(User user2) {
        this.user2 = user2;
    }
    /**
     * getter for user2. This will be called by use case classes.
     */

    public User getUser2(){
        return this.user2;
    }

    /**
     * getter for the first meeting of the transaction.
     * @return the object representing the first meeting of the transaction is returned.
     */
    public Meeting getFirstMeeting() {
        return firstMeeting;
    }

    /**
     * this is a setter for the first meeting of the transaction
     * @param firstMeeting the new first meeting object that we want to set to.
     */
    public void setFirstMeeting(Meeting firstMeeting) {
        this.firstMeeting = firstMeeting;
    }

    /**
     * This is an abstract method that checks if you have a one way transaction.
     * @return returns true iff the transaction you call the method on is a one way transaction.
     */
    public abstract boolean isOneWay();

    /**
     * This abstract method is to make changes to transaction details by taking in a string. This will be implemented
     * in the subclasses.
     * Possible fields to change:
     * Any of the changeable fields in meeting1 and meeting2 (using userChangeByString method.)
     * item1, item2(if two way)
     *
     * @param Field this is the detail of the transaction you want to change.
     *              (the values it can take on are listed above:)
     * @param NewVal this is the new value of the detail you want changed.
     * @return this returns true iff the transaction detail was found and changed successfully.
     */
    protected abstract boolean userChangeFirstMeetingByString(String Field, Object NewVal);
}

