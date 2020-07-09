import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class manages a transaction between two users. A transaction begins once a user expresses interest in an item.
 */
public class TransactionManager {
    private HashMap<UUID, Transaction> allTransactions;


    public Transaction createTransaction(User user1, User user2, Item item1, Meeting meeting1,
                                           Meeting meeting2) {
        Transaction transaction = new TransactionOneWayTemp(user1, user2, item1, meeting1, meeting2);
        UUID id = transaction.getId();
        allTransactions.put(id, transaction);
        return transaction;
    }

    /**
     * A transaction will be created only if it is valid, otherwise an error will be thrown
     * @return a one-way transaction
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(User user1, User user2, Item item1,
                                               Meeting meeting1) throws InvalidTransactionException {
            Transaction transaction = new TransactionOneWayPerm(user1, user2, item1, meeting1);
            UUID id = transaction.getId();
            allTransactions.put(id, transaction);
            return transaction;
    }

    /**
<<<<<<< HEAD
         * @return two-way transaction
         * A transaction will be created only if it is valid.
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         * @param meeting2 the second meeting location that the users will meet at to exchange items.
         */


        public Transaction createTransaction(User user1, User user2, Item item1, Item item2,
                                                   Meeting meeting1, Meeting meeting2) {
            Transaction transaction = new TransactionTwoWayTemp(user1, user2, item1, item2, meeting1, meeting2);
            UUID id = transaction.getId();
            allTransactions.put(id, transaction);
            return transaction;
        }

        /**
         * A transaction will be created only if it is valid, otherwise an error will be thrown
         * @return a two-way transaction
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         */

        public Transaction createTransaction(User user1, User user2, Item item1, Item item2,
                Meeting meeting1) throws InvalidTransactionException {
                Transaction transaction = new TransactionTwoWayPerm(user1, user2, item1, item2, meeting1);
                UUID id = transaction.getId();
                allTransactions.put(id, transaction);
                return transaction;
        }


    /**
     * This method determines if the user who is editing a transaction or meeting is user1 or user2
     * @param transaction the transaction that the user wants to work with
     * @param userId the UUID of the user
     * @return an interger either 1 or 2
     */
    public int findUserNum(Transaction transaction, UUID userId){
        if (transaction.getUser1().equals(userId)){
            return 1;
        }
        else{
            return 2;
        }
    }

    /**
     * This a method for editing a meeting, this method uses overloading to selectively edit either the location, time
     * or date
     * @param meeting the meeting that the user wants to edit
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the User who want to edit the transaction
     * @param newLocation the new location that the user want to the meeting to take place
     * @return True if the meeting was successfully edited
     */
    public boolean editMeeting(Meeting meeting, Transaction transaction, UUID userId, String newLocation) {
        int userNum = findUserNum(transaction, userId);
        if (canEdit(meeting, userNum)) {
            meeting.setLocation(newLocation);
            if (userNum == 1){
                meeting.user1edits();
            }
            else{
                meeting.user2edits();
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * This a method for editing a meeting, this method uses overloading to selectively edit either the location, time
     * or date
     * @param meeting the meeting that the user wants to edit
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the User who want to edit the transaction
     * @param newHour the new hour the user want to have the meeting take place
     * @param newMinute the newMinute the user wants to have the meeting take place
     * @return True if the meeting was successfully edited
     */
    public boolean editMeeting(Meeting meeting, Transaction transaction, UUID userId, int newHour, int newMinute) {
        int userNum = findUserNum(transaction, userId);
        if (canEdit(meeting, userNum)) {
            meeting.setTime(newHour, newMinute);
            if (userNum == 1){
                meeting.user1edits();
            }
            else{
                meeting.user2edits();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This a method for editing a meeting, this method uses overloading to selectively edit either the location, time
     * or date
     * @param meeting the meeting that the user wants to edit
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the User who want to edit the transaction
     * @param newYear the new Year the user want to have the meeting take place
     * @param newMonth the new month the user wants to have the meeting take place
     * @param newDay the new day that the user wants to have the meeting take placec
     * @return True if the meeting was successfully edited
     */
    public boolean editMeeting(Meeting meeting, Transaction transaction, UUID userId, int newYear, int newMonth,
                               int newDay) {
        int userNum = findUserNum(transaction, userId);
        if (canEdit(meeting, userNum)) {
            meeting.setDate(newYear, newMonth, newDay);
            if (userNum == 1){
                meeting.user1edits();
            }
            else{
                meeting.user2edits();
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param transaction the transaction who's status is being updated
     * @return true if the status of the transaction has been updated, the transaction status will we updated based on
     * user input by changing their status user
     */
    public boolean updateStatus(Transaction transaction){
        return (pendingToCancelled(transaction) | pendingToConfirmed(transaction) | confirmedToTraded(transaction) |
                confirmedToIncomplete(transaction) | confirmedToComplete(transaction) | tradedToComplete(transaction) |
                tradedToNeverReturned(transaction));
    }

    /**
     * @return True if the meeting can be edited.
     * A meeting can be edited if a user hasn't reached his maximum number of edits yet
     * @param meeting the meeting that a user wants to edit
     */
    private boolean canEdit(Meeting meeting, int userNum) {
        if (userNum == 1){
            return meeting.getNumEditsUser1() < meeting.getMaxNumEdits();
        }
        else{
            return meeting.getNumEditsUser2() < meeting.getMaxNumEdits();
         }
    }

    /**
     * @return True if the status of the transaction has been changed to confirmed
     * @param transaction the transaction who's status is being changed
     */
    private boolean pendingToConfirmed(Transaction transaction){
        if (!transaction.getStatus().equals("pending")){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("confirm") & transaction.getStatusUser2().equals("confirm")){
                transaction.setStatus("confirmed");
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * @return True if the status of the transaction has been changed to cancelled
     * @param transaction the transaction who's status is being changed
     */
    private boolean pendingToCancelled(Transaction transaction){
        if (transaction.getStatus().equals("pending") & (transaction.getStatusUser1().equals("cancel") || transaction.getStatusUser2().equals("cancel"))){
            transaction.setStatus("cancelled");
            return true;
        }
        if (transaction.getStatus().equals("confirmed") & (transaction.getStatusUser1().equals("cancel") || transaction.getStatusUser2().equals("cancel"))){
            if (transaction.getStatusUser1().equals("traded") || transaction.getStatusUser1().equals("complete")){
                return false;
            }
            if (transaction.getStatusUser2().equals("traded") || transaction.getStatusUser2().equals("complete")){
                return false;
            }
            else{
                transaction.setStatus("cancelled");
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * @return True if the status of the transaction has been changed to traded
     * @param transaction the transaction who's status is being changed
     */
    private boolean confirmedToTraded(Transaction transaction){
        if (!transaction.getStatus().equals("confirmed")){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("traded") & transaction.getStatusUser2().equals("traded")){
                transaction.setStatus("traded");
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * @return True if the status of the transaction has been changed to incomplete
     * @param transaction the transaction who's status is being changed
     */
    private boolean confirmedToIncomplete(Transaction transaction){
        if (!transaction.getStatus().equals("confirmed")){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("incomplete") || transaction.getStatusUser2().equals("incomplete")){
                transaction.setStatus("incomplete");
                return true;
            }
            else{
                return false;
            }

        }
    }

    /**
     * @return True if the status of the transaction has been changed to complete
     * @param transaction the transaction who's status is being changed
     */
    private boolean confirmedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals("confirmed")){
            return false;
        }
        else if (!transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("traded") & transaction.getStatusUser2().equals("traded")){
                transaction.setStatus("complete");
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * @return True if the status of the transaction has been changed to complete
     * @param transaction the transaction who's status is being changed
     */
    private boolean tradedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals("traded")){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("returned") & transaction.getStatusUser2().equals("returned")){
                transaction.setStatus("complete");
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * @return True if the status of the transaction has been changed to neverReturned
     * @param transaction the transaction who's status is being changed
     */
    private boolean tradedToNeverReturned(Transaction transaction){
        if (!transaction.getStatus().equals("traded")){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals("neverReturned") || transaction.getStatusUser2().equals("neverReturned")){
                transaction.setStatus("neverReturned");
                return true;
            }
            else{
                return false;
            }
        }
    }
}
