package Transactions;
import Users.TradingUser;

import java.util.*;

/**
 * <h1>CurrentTransactionManager</h1>
 * Manages a transaction between two users. A transaction begins once a user expresses interest in an item.
 */
public class CurrentTransactionManager extends TransactionManager{

    /**
     * Constructs an instance of the CurrentTransactionManager.
     * @param transactions a map of all transactions to their UUID
     */
    public CurrentTransactionManager(Map<UUID, Transaction> transactions) {
        super(transactions);
    }

    /**
     * Creates a permanent transaction
     * @param userToItems A hashmap which maps userId's to a list of Item ids(where the list is in the form of [ItemId owned, ItemId wanted])
     * @param firstMeeting firstMeeting This is just a meeting object representing where the users will meet for the first time.
     * @return a permanent Transaction
     */
    public Transaction createTransaction(TreeMap<UUID, List<UUID>> userToItems, Meeting firstMeeting){
        Transaction transaction = new TransactionPerm(userToItems, firstMeeting);
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a temporary transaction
     * @param userToItems A hashmap which maps userId's to a list of Item ids(where the list is in the form of [ItemId owned, ItemId wanted])
     * @param firstMeeting a meeting object representing where the users will meet for the first time.
     * @param secondMeeting a meeting object representing where the users will meet to return the items
     * @return a temporary Transaction
     */
    public Transaction createTransaction(TreeMap<UUID, List<UUID>> userToItems, Meeting firstMeeting, Meeting secondMeeting) {
        Transaction transaction = new TransactionTemp(userToItems, firstMeeting, secondMeeting);
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a no-meeting transaction (virtual)
     * @param userToItems map of item to user
     * @return a no-meeting Transaction
     */
    public Transaction createTransaction(TreeMap<UUID, List<UUID>> userToItems){
        Transaction transaction = new TransactionVirtual(userToItems);
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }


    /**
     * Determines if the user who is editing a transaction or meeting is user1 or user2
     * @param transaction the transaction that the user wants to work with
     * @param userId the UUID of the user
     * @return an integer either 1 or 2
     */
    public int findUserNum(Transaction transaction, UUID userId){
        return transaction.getUsers().indexOf(userId) + 1;
    }

    /**
     * Edits a meeting using overloading to selectively edit either the location, time or date
     * @param meetingNum the meeting number that the user wants to edit
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the Users.TradingUser who want to edit the transaction
     * @param newLocation the new location that the user want to the meeting to take place
     * @return True if the meeting was successfully edited
     */
    public boolean editMeeting(int meetingNum, Transaction transaction, UUID userId, String newLocation) {
        int userNum = findUserNum(transaction, userId);
        Meeting meeting = transaction.getTransactionMeetings().get(meetingNum - 1);
        if (canEdit(meeting, userNum) && transaction.getStatus().equals(TransactionStatuses.PENDING)) {
            meeting.setLocation(newLocation);
            meeting.userEdits(userNum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edits a meeting using overloading to selectively edit either the location, time or date
     * @param meetingNum the meeting number that the user wants to edit
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the Users.TradingUser who want to edit the transaction
     * @param time the new hour, minute the user want to have the meeting take place, must be in Date format
     * @param date the new Year, month, day the user want to have the meeting take place, must be in Date format
     * @return boolean whether the meeting was successfully edited or not
     */
    public boolean editMeeting(int meetingNum, Transaction transaction, UUID userId, Date time, Date date) {
        int userNum = findUserNum(transaction, userId);
        Meeting meeting = transaction.getTransactionMeetings().get(meetingNum - 1);
        if (canEdit(meeting, userNum) && transaction.getStatus().equals(TransactionStatuses.PENDING)) {
            meeting.setTime(time);
            meeting.setDate(date);
            meeting.userEdits(userNum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the transaction has multiple meetings
     * @param transaction the transaction to be checked
     * @return true if a transaction has more than one meeting
     */
    public boolean transactionHasMultipleMeetings(Transaction transaction){
        return transaction.getTransactionMeetings().size() > 1;
    }

    /**
     * This method is ONLY allowed to be used in the createTransactionMenu
     *
     * DO NOT USE THIS METHOD. THERE ARE NO EXCEPTIONS WRITTEN AND THIS METHOD IS OUT OF PLACE
     * THIS WILL HAVE TO BE MOVED SOMEWHERE ELSE
     * @param user1 one of the users in transaction.
     * @param user2 one of the users in transaction.
     * @param newTransaction the actual transaction object. (for which method will get ids for)
     */
    public void updateUsersCurrentTransactions(TradingUser user1,TradingUser user2,Transaction newTransaction){
        user1.getCurrentTransactions().add(newTransaction.getId());
        user2.getCurrentTransactions().add(newTransaction.getId());
    }

    /**
     * @param transaction the transaction who's status is being updated
     * @return true if the status of the transaction has been updated, the transaction status will we updated based on
     * user input by changing their status user
     */
    public boolean updateStatus(Transaction transaction){
        return (noMeetingComplete(transaction) | pendingToCancelled(transaction) | pendingToConfirmed(transaction) | confirmedToTraded(transaction) |
                confirmedToIncomplete(transaction) | confirmedToComplete(transaction) | tradedToComplete(transaction) |
                tradedToNeverReturned(transaction));
    }

    private boolean noMeetingComplete(Transaction transaction){
        if(!transaction.isVirtual()){
            return false;
        }
        else{
            for(UUID userId: transaction.getUsers()){
                if (transaction.getUserStatus(userId).equals(TransactionStatuses.PENDING)){
                    return false;
                }
            }
            transaction.setStatus(TransactionStatuses.COMPLETED);
            return true;
        }
    }
    private boolean canEdit(Meeting meeting, int userNum) {
            return meeting.getNumEditsUser(userNum) < meeting.getMaxNumEdits();
    }

    private boolean pendingToConfirmed(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.PENDING)){
            return false;
        }
        else{
            for (UUID currUser : transaction.getUsers()) {
                if (!transaction.getUserStatus(currUser).equals(TransactionStatuses.CONFIRMED)) {
                    return false;
                }
            }
            transaction.setStatus(TransactionStatuses.CONFIRMED);
            return true;
        }
    }

    private boolean pendingToCancelled(Transaction transaction){
        if (transaction.getStatus().equals(TransactionStatuses.PENDING) & (transaction.getStatusUser1().equals(TransactionStatuses.CANCELLED) || transaction.getStatusUser2().equals(TransactionStatuses.CANCELLED))){
            transaction.setStatus(TransactionStatuses.CANCELLED);
            return true;
        }
        if (transaction.getStatus().equals(TransactionStatuses.CONFIRMED) & (transaction.getStatusUser1().equals(TransactionStatuses.CANCELLED) || transaction.getStatusUser2().equals(TransactionStatuses.CANCELLED))){
            if (transaction.getStatusUser1().equals(TransactionStatuses.TRADED) || transaction.getStatusUser1().equals(TransactionStatuses.COMPLETED)){
                return false;
            }
            if (transaction.getStatusUser2().equals(TransactionStatuses.TRADED) || transaction.getStatusUser2().equals(TransactionStatuses.COMPLETED)){
                return false;
            }
            else{
                transaction.setStatus(TransactionStatuses.CANCELLED);
                return true;
            }
        }
        else{
            return false;
        }
    }

    private boolean confirmedToTraded(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.CONFIRMED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(TransactionStatuses.TRADED) & transaction.getStatusUser2().equals(TransactionStatuses.TRADED)){
                transaction.setStatus(TransactionStatuses.TRADED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean confirmedToIncomplete(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.CONFIRMED)){
            return false;
        }
        // else if (transaction.isPerm()){
            // return false;
        // }
        else{
            if (transaction.getStatusUser1().equals(TransactionStatuses.INCOMPLETE) || transaction.getStatusUser2().equals(TransactionStatuses.INCOMPLETE)){
                transaction.setStatus(TransactionStatuses.INCOMPLETE);
                return true;
            }
            else{
                return false;
            }

        }
    }

    private boolean confirmedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.CONFIRMED)){
            return false;
        }
        else if (!transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(TransactionStatuses.TRADED) & transaction.getStatusUser2().equals(TransactionStatuses.TRADED)){
                transaction.setStatus(TransactionStatuses.COMPLETED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean tradedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.TRADED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(TransactionStatuses.COMPLETED) & transaction.getStatusUser2().equals(TransactionStatuses.COMPLETED)){
                transaction.setStatus(TransactionStatuses.COMPLETED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean tradedToNeverReturned(Transaction transaction){
        if (!transaction.getStatus().equals(TransactionStatuses.TRADED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(TransactionStatuses.NEVERRETURNED) || transaction.getStatusUser2().equals(TransactionStatuses.NEVERRETURNED)){
                transaction.setStatus(TransactionStatuses.NEVERRETURNED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    /**
     * returns if one of the users statuses are pending
     * @param transaction which transaction
     * @return boolean
     */
    public boolean userStatuses(Transaction transaction) {
        return !(transaction.getStatusUser1().equals(TransactionStatuses.PENDING) | transaction.getStatusUser2().equals(TransactionStatuses.PENDING));
    }

    /**
     * Updates the inputted transaction's status depending on what the TradingUser selected.
     * @param tradingUser currently logged in TradingUser
     * @param transaction inputted transaction
     * @param optionChosen which option has been chosen by the currently logged in TradingUser
     */
    public void updateStatusUser(TradingUser tradingUser, Transaction transaction, TransactionActions optionChosen){
        UUID userId = tradingUser.getUserId();
        if (optionChosen.equals(TransactionActions.CONFIRMMEETINGDETAILS)){
            transaction.setStatusUserID(TransactionStatuses.CONFIRMED, userId);
        }
        if (optionChosen.equals(TransactionActions.CANCEL)){
            transaction.setStatusUserID(TransactionStatuses.CANCELLED, userId);
        }
        if (optionChosen.equals(TransactionActions.CONFIRMMEETUP)) {
            transaction.setStatusUserID(TransactionStatuses.TRADED, userId);
        }
        if (optionChosen.equals(TransactionActions.MEETUPINCOMPLETE)) {
            transaction.setStatusUserID(TransactionStatuses.INCOMPLETE, userId);
        }
        if (optionChosen.equals(TransactionActions.ITEMRETURNED)){
            transaction.setStatusUserID(TransactionStatuses.COMPLETED, userId);
        }
        if (optionChosen.equals(TransactionActions.ITEMNOTRETURNED)){
            transaction.setStatusUserID(TransactionStatuses.NEVERRETURNED, userId);
        }
        if (optionChosen.equals(TransactionActions.EDITED)){
            transaction.setStatusUserID(TransactionStatuses.PENDING, userId);
        }
    }

}