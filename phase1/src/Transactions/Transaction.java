package Transactions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 *<h1>Transaction</h1>
 *
 * This abstract class represents a meetup between two Users. <br>
 * <br><br>
 * Variables: <br>
 *
 * users: a list of users involved in the transaction. <br>
 *
 * User2 is the user that initiates the transaction. <br>
 *
 * firstMeeting: The first meeting of a transaction. <br>
 *
 * status: The possible values of this and what they mean are: <br>
 * -- "Pending" -- Status given initially while transaction is still being negotiated. <br>
 * -- "Confirmed" -- status given when details of all meetings involved in transaction has been confirmed by users. <br>
 * -- "Traded" -- first meeting has been confirmed by both users as taken place.(only used for one way) <br>
 * -- "Completed" -- the last meeting of the transaction has happened and confirmed by both users. <br>
 * -- "Cancelled" -- transaction has been cancelled. The transaction can only be in this state after pending
 *                   (too many times edited). <br>
 * -- "Incomplete" -- A user did not show at meeting 1. <br>
 * -- "Never Returned" -- A user did not show up at meeting 2 and/or items were not returned.(only used for temporary transactions). <br>
 * userStatuses: a list of all user statuses with the position in the list corresponding to the position in the list of
 * users.
 * useritems:
 */
public abstract class Transaction implements Serializable {
    private UUID id = UUID.randomUUID();
    private Meeting firstMeeting;
    private String status;
    private HashMap<UUID,List<UUID>> userToItems;
    private HashMap<UUID, String> userToStatus;


    /**
     * This method takes in the parameters and constructs an instance of the abstract class transaction.
     * @param userToItems A hashmap which maps userids to a list of
     *        Item ids(where the list is in the form of [Itemid owned, Itemid wanted]). This list can potentially have
     *                    null values depending if the user doesn't have these items with the properties stated.
     * @param firstMeeting This is just a meeting object representing where the users will meet for the first time.
     */
    public Transaction(HashMap<UUID,List<UUID>> userToItems, Meeting firstMeeting){
        status = Statuses.PENDING;
        this.userToItems =userToItems;
        this.firstMeeting = firstMeeting;
        HashMap<UUID,String> userToStatus = new HashMap<>();
        for(UUID id:userToItems.keySet()){
            userToStatus.put(id,Statuses.PENDING);
        }
        this.userToStatus = userToStatus;
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
     * NEEDS TO BE FIXED/CHANGED
     * getter for user1. This will be called by use case classes.
     * @return returns user1 of the transaction.
     */
    public UUID getUser1(){
        userList = ;
    }

    /**
     * NEEDS TO BE FIXED/CHANGED
     * getter for user2. This will be called by use case classes.
     * @return returns user2 of the transaction.
     */
    public UUID getUser2(){
        return this.user2;
    }

    /**
     * Getter for all the userids that are involved in a transaction.
     */
    public List<UUID> getUsers(){
        return (List<UUID>) this.userToItems.keySet();
    }

    public void removeUser(UUID user){
        this.userToItems.remove(user);
    }

    /**
     * This method takes in a user's id and returns a list of id's of items that are relevant to the user in
     * the transaction.
     * @param user the user that you want the relevant items for.
     * @return returns a list of id's of items that are relevant to the user in the form:
     *       form of [Itemid owned, Itemid wanted]. NOTE: This list can contain null values depending if the user
     *       doesn't want an item from the transaction or is not giving away an item.
     */
    public List<UUID> getRelevantItems(UUID user){
        return(this.userToItems.get(user));
    }

    /**
     * This methods takes in a user's id and retuns the item that they initially owned in a transaction.
     * @param user the user that you want the relevant item for.
     * @return returns the item that they initially owned in a transaction.
     */

    public UUID getItemIdOwned(UUID user){
        return(this.userToItems.get(user)).get(0);
    }

    /**
     * This methods takes in a user's id and retuns the item that they want from the transaction.
     * @param user the user that you want the relevant item for.
     * @return returns the item that they initially owned in a transaction.
     */
    public UUID getItemIdDesired(UUID user){
        return(this.userToItems.get(user)).get(1);
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
     * @return Returns list of items. There won't be any order to this list and no one should assume their is.
     */
    public List<UUID> getTransactionItems(){
        List<UUID> TransactionItems = new ArrayList<>();
        for(UUID userid: this.userToItems.keySet()){
            if (this.getItemIdOwned(userid) != null){
                TransactionItems.add(this.getItemIdOwned(userid));
            }
        }
        return TransactionItems;
    }

    /**
     * This abstract method will return a string representation of the transaction. This will be implemented in the
     * subclasses in order to make output related to type which this method was called for.
     * @return returns a string representation of the transaction based on the transaction type.
     */
    @Override
    public abstract String toString();


    /**
     * This method takes in a user id then get's the status of that user in the context of the instance of transaction.
     * This method assumes that the user you are looking for is in the status mapping.
     * @param user the user id whose transaction status you would like to return.
     * @return returns the transaction status for the user id that was passed in to the method.
     */
    public String getUserStatus(UUID user){
        return this.userToStatus.get(user);
    }

    /**
     * This method updates the User Transaction Status for a user in the userToStatus hashmap.
     * This method assume that the user id is in the status mapping.
     * @param user the user id whose status you wish to change in the status mapping.
     * @param newStatus the new status you would like the user id to be mapped to.
     */
    public void setUserStatus(UUID user, String newStatus){
        this.userToStatus.put(user,newStatus);
    }

    /**
     * NEEDS TO BE DELETED/CHANGED
     * Getter for status. This will be called by use case classes.
     * @return statusUser1
     */
    public String getStatusUser1() {return statusUser1;}

    /**
     * NEEDS TO BE DELETED/CHANGED
     * setter for user1. This will be called by use case classes.
     *@param newStatus The new Status of statusUser1
     */
    public void setStatusUser1(String newStatus) {statusUser1 = newStatus;}

    /**
     * NEEDS TO BE DELETED/CHANGED
     * Getter for status. This will be called by use case classes.
     * @return statusUser2
     */
    public String getStatusUser2() {
        return statusUser2;
    }

    /**
     * NEEDS TO BE DELETED/CHANGED
     * Setter for user1. This will be called by use case classes.
     * @param newStatus The new Status of statusUser2
     */
    public void setStatusUser2(String newStatus){statusUser2 = newStatus;}

    /**
     * NEEDS TO BE DELETED/CHANGED
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
     * NEEDS TO BE DELETED/CHANGED
     * Getter for item1 name
     * @return string of name of item1
     */
    public String getItem1Name() {
        return item1Name;
    }

}