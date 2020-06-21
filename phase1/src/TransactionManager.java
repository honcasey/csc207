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
    public boolean transactionIsValid(User user1, User user2, String type){
        if (user1.getStatus().equals("frozen") || user2.getStatus().equals("frozen")){return false;}
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
     * @param location1 the first meeting location that the users will meet at to exchange items.
     * @param location2 the second meeting location that the users will meet at to exchange items.
     */


    public Transaction createOneWayTransaction(User user1, User user2, Item item1, String type, LocalDate date1,
                                           LocalDate date2) throws Exception {
        if (! transactionIsValid(user1, user2, "oneway")){
            throw new Exception("the transaction is not valid");
        }
        if (type.equals("temp")) {
            Transaction transaction = new TransactionOneWayTemp(user1, user2, item1, date1, date2);
            allTransactions.add(transaction);
        }
    }

    public boolean createOneWayTransaction(User user1, User user2, Item item1, String type, LocalDate date1) {
        if (! transactionIsValid(user1, user2, "twoway")){
            throw exception("the transaction is not valid");
        }
        if (type.equals("perm")){
            Transaction transaction = new TransactionOneWayTemp(user1, user2, item1, date1);
            allTransactions.add(transaction);
            return transaction
        }
    }



    public void createTwoWayTransaction(User user1, User user2, Item item1, Item item2, String type){

    }
}
