package Transactions;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *<h1>Transaction</h1>
 *
 * <p> This abstract class represents a meetup between two Users. </p>
 * Variables: <br>
 *
 * user1, user2: users involved in the transaction. <br>
 *
 * User2 is the user that initiates the transaction. <br>
 *
 * firstMeeting: The first meeting of a transaction. <br>
 *
 * status: The possible values of this and what they mean are: <br>
 * -- "Pending" -- Status given initially while transaction is still being negotiated. <br>
 * -- "Confirmed" -- status given when details of all meetings involved in transaction has been confirmed by users. <br>
 * -- "Traded" -- first meeting has been confirmed by both users as taken place.(only used for one way) <br>
 * -- "Complete" -- the last meeting of the transaction has happened and confirmed by both users. <br>
 * -- "Cancelled" -- transaction has been cancelled. The transaction can only be in this state after pending
 *                   (too many times edited). <br>
 * -- "Incomplete" -- A user did not show at meeting 1. <br>
 * -- "Never Returned" -- A user did not show up at meeting 2 and/or items were not returned.(only used for temporary transactions). <br>
 *
 * statusUser1: the status that user1 can change <br>
 * statusUser2: the status that user2 can change  <br>
 * item1Name: the name of item1
 */
public abstract class Transaction implements Serializable {
    private UUID id = UUID.randomUUID();
    private UUID user1;
    private UUID user2;
    private Meeting firstMeeting;
    private String status;
    private String statusUser1;
    private String statusUser2;
    private String item1Name;

    /**
     * Constructor of abstract class Transactions.Transaction.
     * @param user_1 one of the users involved in the transactions.
     * @param user_2 one of the users involved in the transactions.
     * @param firstMeeting the first meeting in the transaction.
     * @param item1Name the name of the item belonging to user1
     */
    public Transaction(UUID user_1, UUID user_2, Meeting firstMeeting, String item1Name){
        status = "pending";
        this.user1 = user_1;
        this.user2 = user_2;
        this.firstMeeting = firstMeeting;
        this.item1Name = item1Name;
        statusUser1 = "pending";
        statusUser2 = "pending";
    }

    /**
     * Getter for a transaction id
     * @return the id of the transaction, a UUID object
     */
    public UUID getId() {
        return id;
    }

    /**
     * Setter for status. This will be called by use case classes.
     * @param newStatus the new status.
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
     * getter for user1. This will be called by use case classes.
     * @return returns user1 of the transaction.
     */
    public UUID getUser1(){
        return this.user1;
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
     * This is an abstract method that checks if you have a one way transaction.
     * @return returns true iff the transaction you call the method on is a one way transaction.
     */
    public abstract boolean isOneWay();

    /**
     * This is an abstract method that checks if you have a permanent transaction.
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
     * @return statusUser1
     */
    public String getStatusUser1() {return statusUser1;}

    /**
     * setter for user1. This will be called by use case classes.
     *@param newStatus The new Status of statusUser1
     */
    public void setStatusUser1(String newStatus) {statusUser1 = newStatus;}

    /**
     * Getter for status. This will be called by use case classes.
     * @return statusUser2
     */
    public String getStatusUser2() {
        return statusUser2;
    }

    /**
     * Setter for user1. This will be called by use case classes.
     * @param newStatus The new Status of statusUser2
     */
    public void setStatusUser2(String newStatus){statusUser2 = newStatus;}

    /**
     * Calls either setStatusUser1 or setStatusUser2
     * @param newStatus the new status to be changed
     * @param userNum either 1 or 2, if usernum == 1 then call setStatusUser1 else call setStatusUser2
     */
    public void setStatusUserNum(String newStatus, int userNum){
        if (userNum == 1){
            setStatusUser1(newStatus);}
        else {
            setStatusUser2(newStatus);
        }
    }

    /**
     * Getter for item1 name
     * @return string of name of item1
     */
    public String getItem1Name() {
        return item1Name;
    }
}

