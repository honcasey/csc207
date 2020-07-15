package Transactions;
import Items.Item;
import Users.TradingUser;

import java.io.ObjectInputFilter;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;

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
     * Creates a transaction only if it is valid, otherwise an error will be thrown
     * @return a one-way transaction
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     * @param meeting2 the second meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(UUID user1, UUID user2, Item item1, Meeting meeting1,
                                         Meeting meeting2) {
        Transaction transaction = new TransactionOneWayTemp(user1, user2, item1.getId(), meeting1, meeting2, item1.getName());
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a transaction only if it is valid, otherwise an error will be thrown
     * @return a one-way transaction
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(UUID user1, UUID user2, Item item1,
                                               Meeting meeting1){
        Transaction transaction = new TransactionOneWayPerm(user1, user2, item1.getId(), meeting1, item1.getName());
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a transaction only if it is valid, otherwise an error will be thrown
     * @return two-way transaction
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     * @param meeting2 the second meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(UUID user1, UUID user2, Item item1, Item item2,
                                         Meeting meeting1, Meeting meeting2) {
        Transaction transaction = new TransactionTwoWayTemp(user1, user2, item1.getId(), item2.getId(), meeting1,
                meeting2, item1.getName(),
                item2.getName());
        UUID id = transaction.getId();
        getAllTransactions().put(id, transaction);
        return transaction;
    }

    /**
     * Creates a transaction only if it is valid, otherwise an error will be thrown
     * @return a two-way transaction
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(UUID user1, UUID user2, Item item1, Item item2,
                                         Meeting meeting1) {
            Transaction transaction = new TransactionTwoWayPerm(user1, user2, item1.getId(), item2.getId(),
                    meeting1, item1.getName(), item2.getName());
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
        if (transaction.getUser1().equals(userId)){
            return 1;
        }
        else{ return 2; }
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
            if (userNum == 1){
                meeting.user1edits();}
            else{
                meeting.user2edits();
            }
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
        if (optionChosen.equals("Confirm Transactions Meeting(s)")){
            transaction.setStatusUserNum(Statuses.CONFIRMED, userNum);
            return true;
        }
        if (optionChosen.equals("Cancel transaction")){
           transaction.setStatusUserNum(Statuses.CANCELLED, userNum);
           return true;
        }
        if (optionChosen.equals("Confirm the exchange has taken place")) {
            transaction.setStatusUserNum(Statuses.TRADED, userNum);
            return true;
        }
        if (optionChosen.equals("Claim that the exchange has not taken place")) {
           transaction.setStatusUserNum(Statuses.INCOMPLETE, userNum);
           return true;
        }
        if (optionChosen.equals("Confirm the item has been returned")){
           transaction.setStatusUserNum(Statuses.COMPLETED, userNum);
           return true;
        }
        if (optionChosen.equals("Claim that the item has not been returned past due date")){
           transaction.setStatusUserNum(Statuses.NEVERRETURNED, userNum);
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
        if (userNum == 1){
            return meeting.getNumEditsUser1() < meeting.getMaxNumEdits();
        }
        else{
            return meeting.getNumEditsUser2() < meeting.getMaxNumEdits();
         }
    }

    private boolean pendingToConfirmed(Transaction transaction){
        if (!transaction.getStatus().equals(Statuses.PENDING)){
            return false;
        }
        else{
            if (transaction.getStatusUser1().equals(Statuses.CONFIRMED) & transaction.getStatusUser2().equals(Statuses.CONFIRMED)){
                transaction.setStatus(Statuses.CONFIRMED);
                return true;
            }
            else{
                return false;
            }
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