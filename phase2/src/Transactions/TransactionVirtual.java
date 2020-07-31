package Transactions;

import java.util.*;

public class TransactionVirtual extends Transaction{

    /**
     * Constructor for Transactions.TransactionTwoWayPerm class. This constructor initializes a 2 way permanent transaction with
     * @param userToItems A hashmap which maps userId's to a list of
     *      Item ids(where the list is in the form of [ItemId owned, ItemId wanted]).
     * @param itemToName A hashmap which maps itemId to the string Name of the item.
     */
    public TransactionVirtual(TreeMap<UUID, List<UUID>> userToItems, HashMap<UUID,
            List<String>> itemToName){
        super(userToItems);
    }

    @Override
    public boolean isPerm() {
        return false;
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    @Override
    public List<Meeting> getTransactionMeetings() {
        return new ArrayList<>();
    }
}
