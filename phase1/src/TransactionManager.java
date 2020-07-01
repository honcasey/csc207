import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/**
 * This class manages a transaction between two users. A transaction begins once a user expresses interest in an item.
 */
public class TransactionManager {
    private List<Transaction> allTransactions = new ArrayList<>();
    /**
     * Checks if this transaction can be initiated based on the status of the users
     * A transaction is valid if both users accounts are not frozen, and transaction doesn't
     * and if they have lent more items than they have borrowed as specified by their threshold number
     */
    //ToDo fix this method, need a method that gives me the numWeeklyTransactions, and numIncompleteTransactions
    public boolean transactionIsValid(User user1, User user2, String type){
        // TODO
        return true;
    }

    /**
     * @return one-way transaction
     * A transaction will be created only if it is valid, otherwise an error will be thrown     *
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     * @param meeting2 the second meeting location that the users will meet at to exchange items.
     */


    public Transaction createTransaction(User user1, User user2, Item item1, Meeting meeting1,
                                           Meeting meeting2) {
        Transaction transaction = new TransactionOneWayTemp(user1, user2, item1, meeting1, meeting2);
        allTransactions.add(transaction);
        return transaction;
    }

    /**
     * @return one-way transaction
     * A transaction will be created only if it is valid, otherwise an error will be thrown     *
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(User user1, User user2, Item item1,
                                               Meeting meeting1) throws InvalidTransactionException {
            Transaction transaction = new TransactionOneWayPerm(user1, user2, item1, meeting1);
            allTransactions.add(transaction);
            return transaction;
    }


    /**
         * @return two-way transaction
         * A transaction will be created only if it is valid, otherwise an error will be thrown
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         * @param meeting2 the second meeting location that the users will meet at to exchange items.
         */


        public Transaction createTransaction(User user1, User user2, Item item1, Item item2,
                                                   Meeting meeting1, Meeting meeting2) {
            Transaction transaction = new TransactionTwoWayTemp(user1, user2, item1, item2, meeting1, meeting2);
            allTransactions.add(transaction);
            return transaction;
        }
        /**
         * @return two-way transaction
         * A transaction will be created only if it is valid, otherwise an error will be thrown
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         */

        public Transaction createTransaction(User user1, User user2, Item item1, Item item2,
                Meeting meeting1) throws InvalidTransactionException {
                Transaction transaction = new TransactionTwoWayPerm(user1, user2, item1, item2, meeting1);
                allTransactions.add(transaction);
                return transaction;
        }
    public Meeting createMeeting(String location, int year, int month, int dayOfMonth, int hour, int minutes){
           Meeting meeting = new Meeting(location, year, month, dayOfMonth, hour, minutes);
           return meeting;
    }

    public boolean confirmMeeting(Meeting meeting, int userNum) throws Exception{
            if (userNum == 1) {
                meeting.setUser1approved(true);
                return true;
            }
            else if (userNum == 2){
                meeting.setUser2approved(true);
                return true;
            }
            else{
                return false;
            }
    }


    /**
     * @return True if the meeting has been edited
     * @param meeting the transaction who's status is being changed
     * @param usernum the number of the user who is editing the transaction, must be either 1 or 2
     */
    //TODO: I don't know the best way to have an edit meeting function, do we want overloading, or if someone edits they
    //TODO:edit all parameters at once?
    public boolean editMeeting(Meeting meeting, int usernum){
            if (canEdit(meeting)){
                if (usernum == 1){meeting.user1edits();}
                else {meeting.user2edits();}
                return true;
            }
            else{return false;}
    }

    /**
     * @param transaction the transaction who's status is being updated
     * @return true if the status of the transaction has been updated, the transaction status will we updated based on
     * user input by changing their statususer
     */
    public boolean updateStatus(Transaction transaction){
        if (pendingToConfirmed(transaction)){return true;}
        if (pendingToCancelled(transaction)){return true;}
        if (confirmedToTraded(transaction)){return true;}
        if (confirmedToIncomplete(transaction)){return true;}
        if (confirmedToComplete(transaction)){return true;}
        if (tradedToComplete(transaction)){return true;}
        if (tradedToNeverReturned(transaction)){return true;}
        else{return false;}
    }


    /**
     * @return True if the meeting may be edited.
     * A meeting may be edited if a user hasn't reached his maximum number of edits yet
     * @param meeting the meeting that a user wants to edit
     */
    private boolean canEdit(Meeting meeting) {
            int edits1 = meeting.getNumEditsUser1();
            int edits2 = meeting.getNumEditsUser2();
            int max = meeting.getMaxNumEdits();
        return edits1 < max & edits2 < max;
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("confirm") & ustatus2.equals("confirm")){
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
        String ustatus1 = transaction.getStatusUser1();
        String ustatus2 = transaction.getStatusUser2();
        String status = transaction.getStatus();
        if (status.equals("pending") & (ustatus1.equals("cancel") || ustatus2.equals("cancel"))){
            transaction.setStatus("cancelled");
            return true;
        }
        if (status.equals("confirmed") & (ustatus1.equals("cancel") || ustatus2.equals("cancel"))){
            if (ustatus1.equals("traded") || ustatus1.equals("complete")){
                return false;
            }
            if (ustatus2.equals("traded") || ustatus2.equals("complete")){
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("traded") & ustatus2.equals("traded")){
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("incomplete") || ustatus2.equals("incomplete")){
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("traded") & ustatus2.equals("traded")){
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("returned") & ustatus2.equals("returned")){
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
            String ustatus1 = transaction.getStatusUser1();
            String ustatus2 = transaction.getStatusUser2();
            if (ustatus1.equals("neverReturned") || ustatus2.equals("neverReturned")){
                transaction.setStatus("neverReturned");
                return true;
            }
            else{
                return false;
            }
        }
    }
}
