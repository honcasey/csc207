import java.util.ArrayList;

/**
 * Represents a user's transaction activities.
 */
public class TransactionDetails {
    private ArrayList<Transaction> incomingOffers;
    private ArrayList<Transaction> sentOffers;

    /**
     * Returns a list of incoming Transaction offers by other users.
     * @return incomingOffers as a list of Transaction.
     */
    public ArrayList<Transaction> getIncomingOffers() {
        return incomingOffers;
    }

    /**
     * Returns a list of sent Transactions sent out.
     * @return sentOffers as a list of Transaction.
     */
    public ArrayList<Transaction> getSentOffers() {
        return sentOffers;
    }

    /**
     * Adds a Transaction to incomingOffers.
     * @param transaction the Transaction to be added.
     */
    public void addToIncomingOffers(Transaction transaction) {
        incomingOffers.add(transaction);
    }

    /**
     * Adds a Transaction to sentOffers.
     * @param transaction the Transaction to be added.
     */
    public void addToSentOffers(Transaction transaction) {
        sentOffers.add(transaction);
    }
}

