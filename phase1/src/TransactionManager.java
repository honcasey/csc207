/**
 * This class manages a transaction between two users. A transaction begins once a user expresses interest in an item.
 */
public class TransactionManager {

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
     * Return true if a one-way transaction was create, false otherwise
     * A transaction will be created only if
     * @param threshold as an integer
     *
     */
    public boolean createOneWayTransaction(User user1, User user2, Item item1, String type){
        if not transactionIsValid(user1, user2, "oneway")
        if (type.equals("temp")){
            Transaction transaction = TransactionOneWayTemp(user1, user2);
        }
    }

    public void createTwoWayTransaction(User user1, User user2, Item item1, Item item2, String type){

    }
}
