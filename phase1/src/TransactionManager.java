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
     * A transaction is valid if both users accounts are not frozen,
     * and if they have lent more items than they have borrowed as specified by their threshold number
     */
    //ToDo fix this method, need a method that gives me the numWeeklyTransactions, and numIncompleteTransactions
    public boolean transactionIsValid(User user1, User user2, String type){
        if (user1.getStatus().equals("frozen") || user2.getStatus().equals("frozen")){return false;}
        else
        else if (type.equals("twoway")){return true;}
        else if ((user2.getNumItemsLended() - user2.getNumItemsBorrowed()) < user2.getThreshold()){return false;}
        else{return true;}

    }

    /**
     * @return one-way transaction
     * A transaction will be created only if it is valid, otherwise an error will be thrown     *
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param type must be either "temp" or "perm", specifies what type of transaction will be created
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     * @param meeting2 the second meeting location that the users will meet at to exchange items.
     */


    public Transaction createOneWayTransaction(User user1, User user2, Item item1, String type, Meeting meeting1,
                                           Meeting meeting2) throws InvalidTransactionException {
        if (! transactionIsValid(user1, user2, "oneway")){
            throw new InvalidTransactionException("the transaction is not valid: the user's account may be frozen, or " +
                    "they may have exceeded the maximum allowable transactions for this week");
        }
        if (type.equals("temp")) {
            Transaction transaction = new TransactionOneWayTemp(user1, user2, item1, meeting1, meeting2);
            allTransactions.add(transaction);
            return transaction;
        }
        else{
            throw new InvalidTransactionException("if you provide two meeting dates for the transaction, " +
                    " must be temporary");}
    }


    /**
     * @return one-way transaction
     * A transaction will be created only if it is valid, otherwise an error will be thrown     *
     * @param user1 the user who has the item.
     * @param user2 the user who want to borrow an item.
     * @param item1 the item that user2 wants to borrow.
     * @param type must be either "temp" or "perm", specifies what type of transaction will be created
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createOneWayTransaction(User user1, User user2, Item item1, String type,
                                               Meeting meeting1) throws InvalidTransactionException {
        if (!transactionIsValid(user1, user2, "twoway")) {
            throw new InvalidTransactionException("the transaction is not valid: the user's account may be frozen, or " +
                    "they may have exceeded the maximum allowable transactions for this week");
        }
        if (type.equals("perm")) {
            Transaction transaction = new TransactionOneWayPerm(user1, user2, item1, meeting1);
            allTransactions.add(transaction);
            return transaction;
        } else {
            throw new InvalidTransactionException("if you provide one meeting dates for the transaction, " +
                    " must be permanent");
        }
    }


    /**
         * @return two-way transaction
         * A transaction will be created only if it is valid, otherwise an error will be thrown
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param type must be either "temp" or "perm", specifies what type of transaction will be created
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         * @param meeting2 the second meeting location that the users will meet at to exchange items.
         */


    public Transaction createTwoWayTransaction(User user1, User user2, Item item1, Item item2, String type,
                                                   Meeting meeting1, Meeting meeting2) throws InvalidTransactionException {
        if (!transactionIsValid(user1, user2, "twoway")) {
            throw new InvalidTransactionException("the transaction is not valid, the user's account may be frozen, or " +
                    "they may have exceeded the maximum allowable transactions for this week");
        }
        if (type.equals("temp")) {
            Transaction transaction = new TransactionTwoWayTemp(user1, user2, item1, item2, meeting1, meeting2);
            allTransactions.add(transaction);
            return transaction;
        } else {
            throw new InvalidTransactionException("if you provide one meeting dates for the transaction, " +
                    " it must be temporary");
        }
    }


        /**
         * @return two-way transaction
         * A transaction will be created only if it is valid, otherwise an error will be thrown
         * @param user1 the user who has the item.
         * @param user2 the user who want to borrow an item.
         * @param item1 the item that user2 wants to borrow.
         * @param type must be either "temp" or "perm", specifies what type of transaction will be created
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         */

        public Transaction createTwoWayTransaction(User user1, User user2, Item item1, Item item2, String type,
                Meeting meeting1) throws InvalidTransactionException {
            if (!transactionIsValid(user1, user2, "twoway")) {
                throw new InvalidTransactionException("the transaction is not valid, the user's account may be frozen, or " +
                        "they may have exceeded the maximum allowable transactions for this week");
            }
            if (type.equals("perm")) {
                Transaction transaction = new TransactionTwoWayPerm(user1, user2, item1, item2, meeting1);
                allTransactions.add(transaction);
                return transaction;
            } else {
                throw new InvalidTransactionException("if you provide two meeting dates for the transaction, " +
                        " it must be a temporary transaction");
            }
        }

    public Meeting createMeeting(String location, int year, int month, int dayOfMonth, int hour, int minutes){
           Meeting meeting = new Meeting(location, year, month, dayOfMonth, hour, minutes);
           return meeting;
    }

    public void confirmMeeting(Meeting meeting, int userNum) throws Exception{
            if (userNum == 1) {
            meeting.setUser1approved(true);}
            else if (userNum == 2){
            meeting.setUser2approved(true);}
            else{
                throw new Exception("userNum must be either 1 or 2");
            }
    }

    public void changeMeetingLocation(Meeting meeting, String location, int userNum) {
            meeting.setLocation(location);
    }

    public void changeMeetingDate(Meeting meeting, int year, int month, int dayOfMonth){
            meeting.setDate(year, month, dayOfMonth);
    }
    public void changeMeetingTime(Meeting meeting, int hour, int minutes){
        meeting.setTime(hour, minutes);
    }

    public void updateStatus(Transaction transaction) {
        if (transaction instanceof TransactionOneWayTemp || transaction instanceof TransactionTwoWayTemp) {
            updateStatusTemp(transaction);
        } else (transaction instanceof TransactionOneWayPerm || transaction instanceof TransactionTwoWayPerm) {
            updateStatusPerm(transaction);
        }
    }

    private void updateStatusPerm(Transaction transaction) {
    }

    private void updateStatusTemp(Transaction transaction){
            String status = transaction.getStatus();
            Meeting meeting2;
            Meeting meeting1 = transaction.getFirstMeeting();
            if (transaction instanceof TransactionOneWayTemp){
                TransactionOneWayTemp transactionDown = (TransactionOneWayTemp) transaction;
                meeting2 = transactionDown.getSecondMeeting();}
            else{
                TransactionTwoWayTemp transactionDown = (TransactionTwoWayTemp) transaction;
                meeting2 = transactionDown.getSecondMeeting();
                }
             if (status.equals("pending")){
                 if (meeting1.getUser1approved() & meeting1.getUser2approved() &
                         meeting2.getUser1approved() & meeting2.getUser2approved()){
                     transaction.setStatus("confirmed");
                 }
            }
             else if (status.equals("confirmed"))

    }


    // for perm Status: pending (when someone expressed interest),  confirmed (after you confirmed location), complete (after meeting), cancelled, incomplete (no show)
        // confirm exchange, confirm return

    //for temp Status: pending (when someone expressed interest),  confirmed (after you confirmed location), traded (after first meeting),  complete (after meeting), cancelled, incomplete (no show), never_returned (after deadline)
        public void changeStatus(Transaction, String status){}
        






}
