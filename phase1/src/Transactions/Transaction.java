package Transactions;

import Items.Item;

import java.io.Serializable;
import java.util.List;
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
 * -- "Complete" -- the last meeting of the transaction has happened and confirmed by both users.
 * -- "Cancelled" -- transaction has been cancelled. The transaction can only be in this state after pending
 *                   (too many times edited).
 * -- "No-Show" -- there was a no show at meeting 1.
 * -- "Never Returned" -- there was a no show at meeting 2 and items were not returned.(only used for two-way)
 */
public abstract class Transaction implements Serializable {
    private UUID id = UUID.randomUUID();
    private UUID user1;
    private UUID user2;
    private Meeting firstMeeting;
    private String status;
    private String statusUser1;
    private String statusUser2;

    /**
     * Constructor of abstract class Transactions.Transaction.
     * @param user_1 one of the users involved in the transactions.
     * @param user_2 one of the users involved in the transactions.
     */
    public Transaction(UUID user_1, UUID user_2, Meeting firstMeeting){
        status = "pending";
        this.user1 = user_1;
        this.user2 = user_2;
        this.firstMeeting = firstMeeting;
    }


    /**
     * The getter for a transaction id
     * @return the id of the transaction, a UUID object
     */
    public UUID getId() {
        return id;
    }

    /**
     * Setter for status. This will be called by use case classes.
     */

    public void setStatus(String newStatus){
        status = newStatus;
    }

    /**
     * Getter for status. This will be called by use case classes.
     * @return returns the status of the transaction which can take on values specified in class documentation.
     */
    public String getStatus(){
        return status;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */

    public void setUser1(UUID user1) {
        this.user1 = user1;
    }
    /**
     * getter for user1. This will be called by use case classes.
     * @return returns user1 of the transaction.
     */

    public UUID getUser1(){
        return this.user1;
    }

    /**
     * setter for user1. This will be called by use case classes.
     * @param user2
     */

    public void setUser2(UUID user2) {
        this.user2 = user2;
    }
    /**
     * getter for user2. This will be called by use case classes.
     * @return returns user2 of the transaction.
     */

    public UUID getUser2(){
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
     * This is an abstract method that checks if you have a permenant transaction.
     * @return returns true iff the transaction you call the method on is a one way transaction.
     */
    public abstract boolean isPerm();


    /**
     * This is an abstract method that get's all of the meetings involved in the transaction. Size of list returned
     * will depend directly on the type of transaction taking place.
     * @return returns a list of
     *         meetings in the order of them happening in the transaction. (A list of either 1 or 2 meetings.)
     */
    public abstract List<Meeting> getTransactionMeetings();

    /**
     * This is an abstract method that gets all the items involved in the transactions. Size of list returned will
     * depend directly on the type of transaction taking place.
     *
     * @return Returns list of items. The order of the items in the list are Item1,Item2(if applicable).
     */
    public abstract List<UUID> getTransactionItems();


    /**
     * This abstract method will return a string representation of the transaction. This will be implemented in the
     * subclasses in order to make output related to type which this method was called for.
     * @return returns a string representation of the transaction based on the transaction type.
     */
    @Override
    public abstract String toString();


    /**
     * Getter for status. This will be called by use case classes.
     */

    public String getStatusUser1() {return statusUser1;}

    /**
     * setter for user1. This will be called by use case classes.
     */
    public void setStatusUser1(String newStatus) {statusUser1 = newStatus;}

    /**
     * Getter for status. This will be called by use case classes.
     */
    public String getStatusUser2() {
        return statusUser2;
    }

    /**
     * setter for user1. This will be called by use case classes.
     */
    public void setStatusUser2(String newStatus){statusUser2 = newStatus;}

    public void setStatusUserNum(String newStatus, int userNum){
        if (userNum == 1){
            setStatusUser1(newStatus);}
        else {
            setStatusUser2(newStatus);
        }
    }


}

