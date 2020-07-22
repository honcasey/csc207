package Transactions;
import Items.Item;
import Users.TradingUser;

import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;

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
     * Creates a transaction
     * @param userToItems A hashmap which maps userId's to a list of Item ids(where the list is in the form of [ItemId owned, ItemId wanted])
     * @param firstMeeting firstMeeting This is just a meeting object representing where the users will meet for the first time.
     * @return
     */
    public Transaction createTransaction(TreeMap<UUID, List<UUID>> userToItems, Meeting firstMeeting){
        Transaction transaction = new TransactionPerm(userToItems, firstMeeting);
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a transaction
     * @param userToItems A hashmap which maps userId's to a list of Item ids(where the list is in the form of [ItemId owned, ItemId wanted])
     * @param firstMeeting firstMeeting This is just a meeting object representing where the users will meet for the first time.
     * @param itemToName A  hashmap which maps itemId to the string Name of the item
     * @param secondMeeting
     * @return
     */
    public Transaction createTransaction(TreeMap<UUID, List<UUID>> userToItems, Meeting firstMeeting, HashMap<UUID, List<String>> itemToName, Meeting secondMeeting) {
        Transaction transaction = new TransactionTemp(userToItems, firstMeeting, itemToName, secondMeeting);
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
        if (canEdit(meeting, userNum) && transaction.getStatus().equals(Statuses.PENDING)) {
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
     * @param time the new hour, minute the user want to have the meeting take place, must be in LocalTime format
     * @return boolean whether the meeting was successfully edited or not
     */
    public boolean editMeeting(int meetingNum, Transaction transaction, UUID userId, LocalTime time) {
        int userNum = findUserNum(transaction, userId);
        Meeting meeting = transaction.getTransactionMeetings().get(meetingNum - 1);
        if (canEdit(meeting, userNum) && transaction.getStatus().equals(Statuses.PENDING)) {
            meeting.setTime(time);
            meeting.userEdits(userNum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edits a meeting using overloading to selectively edit either the location, time or date
     * @param meetingNum the meeting that the user wants to edit (first or second in transactions)
     * @param transaction the transaction to which the meeting belongs to
     * @param userId the UUID of the Users.TradingUser who want to edit the transaction
     * @param date the new Year, month, day the user want to have the meeting take place, must be in LocalDate format
     * @return True if the meeting was successfully edited
     */
    public boolean editMeeting(int meetingNum, Transaction transaction, UUID userId, LocalDate date) {
        int userNum = findUserNum(transaction, userId);
        Meeting meeting = transaction.getTransactionMeetings().get(meetingNum - 1);
        if (canEdit(meeting, userNum) && transaction.getStatus().equals(Statuses.PENDING)) {
            meeting.setDate(date);
            meeting.userEdits(userNum);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Creates a new meeting with the time set to one month later.
     * @param firstMeeting an input meeting
     * @return a new meeting object with date to one month later
     */
    public Meeting meetOneMonthLater(Meeting firstMeeting){
        String location = firstMeeting.getLocation();
        LocalDate newDate = firstMeeting.getDate().plusMonths(1);
        LocalTime newTime = firstMeeting.getTime();
        return new Meeting(location, newTime, newDate);
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
     * Updates the inputted transaction's status depending on what the TradingUser selected. (note for Phase 2- this should be a presenter/controller method)
     * @param tradingUser currently logged in TradingUser
     * @param transaction inputted transaction
     * @param optionChosen which option has been chosen by the currently logged in TradingUser
     * @return true if the user's status has been updated
     */
    public boolean updateStatusUser(TradingUser tradingUser, Transaction transaction, String optionChosen){
        int userNum = findUserNum(transaction, tradingUser.getUserId());
        UUID userId = tradingUser.getUserId();
        if (optionChosen.equals("Confirm Transactions Meeting(s)")){
            transaction.setStatusUserID(Statuses.CONFIRMED, userId);
            return true;
        }
        if (optionChosen.equals("Cancel transaction")){
           transaction.setStatusUserID(Statuses.CANCELLED, userId);
           return true;
        }
        if (optionChosen.equals("Confirm the exchange has taken place")) {
            transaction.setStatusUserID(Statuses.TRADED, userId);
            return true;
        }
        if (optionChosen.equals("Claim that the exchange has not taken place")) {
           transaction.setStatusUserID(Statuses.INCOMPLETE, userId);
           return true;
        }
        if (optionChosen.equals("Confirm the item has been returned")){
           transaction.setStatusUserID(Statuses.COMPLETED, userId);
           return true;
        }
        if (optionChosen.equals("Claim that the item has not been returned past due date")){
           transaction.setStatusUserID(Statuses.NEVERRETURNED, userId);
           return true;
        }
        else{ return false;
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

    private boolean canEdit(Meeting meeting, int userNum) {
            return meeting.getNumEditsUser(userNum) < meeting.getMaxNumEdits();
    }

    private boolean pendingToConfirmed(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.PENDING)){
            return false;
        }
        else{
            for (UUID currUser : transaction.getUsers()) {
                if (!transaction.getUserStatus(currUser).equals(Statuses.CONFIRMED)) {
                    return false;
                }
            }
            transaction.setStatus(Statuses.CONFIRMED);
            return true;
        }
    }

    private boolean pendingToCancelled(Transaction transaction){
        if (transaction.getStatus().equals(Statuses.PENDING) & (transaction.getStatusUser1().equals(Statuses.CANCELLED) || transaction.getStatusUser2().equals(Statuses.CANCELLED))){
            transaction.setStatus(Statuses.CANCELLED);
            return true;
        }
        if (transaction.getStatus().equals(Statuses.CONFIRMED) & (transaction.getStatusUser1().equals(Statuses.CANCELLED) || transaction.getStatusUser2().equals("cancel"))){
            if (transaction.getStatusUser1().equals(Statuses.TRADED) || transaction.getStatusUser1().equals(Statuses.COMPLETED)){
                return false;
            }
            if (transaction.getStatusUser2().equals(Statuses.TRADED) || transaction.getStatusUser2().equals(Statuses.COMPLETED)){
                return false;
            }
            else{
                transaction.setStatus(Statuses.CANCELLED);
                return true;
            }
        }
        else{
            return false;
        }
    }

    private boolean confirmedToTraded(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.CONFIRMED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(Statuses.TRADED) & transaction.getStatusUser2().equals(Statuses.TRADED)){
                transaction.setStatus(Statuses.TRADED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean confirmedToIncomplete(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.CONFIRMED)){
            return false;
        }
        // else if (transaction.isPerm()){
            // return false;
        // }
        else{
            if (transaction.getStatusUser1().equals(Statuses.INCOMPLETE) || transaction.getStatusUser2().equals(Statuses.INCOMPLETE)){
                transaction.setStatus(Statuses.INCOMPLETE);
                return true;
            }
            else{
                return false;
            }

        }
    }

    private boolean confirmedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.CONFIRMED)){
            return false;
        }
        else if (!transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(Statuses.TRADED) & transaction.getStatusUser2().equals(Statuses.TRADED)){
                transaction.setStatus(Statuses.COMPLETED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean tradedToComplete(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.TRADED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(Statuses.COMPLETED) & transaction.getStatusUser2().equals(Statuses.COMPLETED)){
                transaction.setStatus(Statuses.COMPLETED);
                return true;
            }
            else{
                return false;
            }
        }
    }

    private boolean tradedToNeverReturned(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.TRADED)){
            return false;
        }
        else if (transaction.isPerm()){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(Statuses.NEVERRETURNED) || transaction.getStatusUser2().equals(Statuses.NEVERRETURNED)){
                transaction.setStatus(Statuses.NEVERRETURNED);
                return true;
            }
            else{
                return false;
            }
        }
    }
}