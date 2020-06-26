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
     * @param type must be either "temp" or "perm", specifies what type of transaction will be created
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     * @param meeting2 the second meeting location that the users will meet at to exchange items.
     */


    public Transaction createTransaction(User user1, User user2, Item item1, String type, Meeting meeting1,
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
     * @param type must be either "temp" or "perm", specifies what type of transaction will be created
     * @param meeting1 the first meeting location that the users will meet at to exchange items.
     */
    public Transaction createTransaction(User user1, User user2, Item item1, String type,
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
         * @param type must be either "temp" or "perm", specifies what type of transaction will be created
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         * @param meeting2 the second meeting location that the users will meet at to exchange items.
         */


        public Transaction createTransaction(User user1, User user2, Item item1, Item item2, String type,
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
         * @param type must be either "temp" or "perm", specifies what type of transaction will be created
         * @param meeting1 the first meeting location that the users will meet at to exchange items.
         */

        public Transaction createTransaction(User user1, User user2, Item item1, Item item2, String type,
                Meeting meeting1) throws InvalidTransactionException {
                Transaction transaction = new TransactionTwoWayPerm(user1, user2, item1, item2, meeting1);
                allTransactions.add(transaction);
                return transaction;
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
}
