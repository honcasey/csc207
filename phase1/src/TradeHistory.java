import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Represents the trade history of a User
 */

public class TradeHistory {
    private ArrayList <Transaction> transactions;
    private HashMap<User, Integer> usersNumTradeTimes;

    /**
     * Constructs an instance of TradeHistory with an empty ArrayList transactions and an empty Hashtable usersNumTradeTimes
     * The most recent Transaction is added to the end of the list transactions
     */

    public TradeHistory(){
        transactions = new ArrayList<>();
        usersNumTradeTimes = new HashMap<User, Integer>();
    }

    /**
     * Returns the three most recent transactions
     */
    public ArrayList<Transaction> mostRecentTransactions(){
        return (ArrayList<Transaction>) transactions.subList(transactions.size() - 4, transactions.size());
    }
    /**
     * Returns the users top three trading partners
     */
    public ArrayList<User> mostTradedWithUsers(){
        HashMap<User, Integer> temp = (HashMap<User, Integer>) usersNumTradeTimes.clone();
        ArrayList<User> mostTradedWith = new ArrayList<>();

        // TODO: Figure out how this top three situation is going to work



    }


}
