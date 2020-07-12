package Users;

import Transactions.Transaction;

import java.io.Serializable;
import java.util.*;


/**
 * Represents the Transactions.Transaction History of Users.TradingUser by storing information about completed transactions
 */

public class TransactionHistory implements Serializable {
    private List <UUID> oneWayTransactions; // goes in user
    private List <UUID> twoWayTransactions; //goes in user
    private HashMap<String, Integer> usersNumTradeTimes; // goes in user. change to <string, integer>
    private int numItemsLended = 0; // goes in user
    private int numItemsBorrowed = 0; // goes in user


    /**
     * Constructs an instance of Users.TransactionHistory with an empty ArrayList transactions and an empty Hashtable usersNumTradeTimes
     * The most recent Transactions.Transaction is added to the end of the list transactions
     */
    public TransactionHistory(){
        oneWayTransactions = new ArrayList<>();
        twoWayTransactions = new ArrayList<>();
        usersNumTradeTimes = new HashMap<>();
    }

    /**
     * @return the three most recent OneWay Transactions
     */
    public List<UUID> mostRecentOneWayTransactions(){ //TH manager
        if (oneWayTransactions.size() <= 3) {
           return oneWayTransactions;
        } return oneWayTransactions.subList(oneWayTransactions.size() - 3, twoWayTransactions.size());
    }

    /**
     * @return the three most recent TwoWay Transactions.Transaction
     */
    public List<UUID> mostRecentTwoWayTransactions(){
        if(twoWayTransactions.size() <= 3){
            return twoWayTransactions;
        }
        return twoWayTransactions.subList(twoWayTransactions.size() - 3, twoWayTransactions.size());
    }

    /**
     * @return the usernames of the Users.TradingUser's top three trading partners
     */
    public List<String> mostTradedWithUsers(){
        Map<String, Integer> temp = (HashMap<String, Integer>) usersNumTradeTimes.clone();
        List<Map.Entry<String, Integer>> mostTradedWith = new ArrayList<>();
        List<String> mostTradedWithUsernames = new ArrayList<>();
        // if user has less than or equal to 3 trades
        //// this is not necessary actually now that I think about it
        if(usersNumTradeTimes.size() <= 3){
            Set<String> users = usersNumTradeTimes.keySet();
            mostTradedWithUsernames.addAll(users);
            return mostTradedWithUsernames;
            }
        // if user has greater than 3 trades
        // find the three most traded users
        int j = 0;
        while(j < 3){
            Map.Entry<String, Integer> maxUser = null;
            for(Map.Entry<String, Integer> entry: temp.entrySet()){
                if(maxUser == null || entry.getValue() >= maxUser.getValue()){
                    maxUser = entry;
                }
            }
            mostTradedWith.add(maxUser);
            temp.remove(maxUser.getKey());
            j ++;
        }
        return mostTradedWithUsernames;

    }

    /**
     * @return all OneWay Transactions
     */
    public List <UUID> getOneWayTransactions(){
        return oneWayTransactions;
    }

    /**
     * @return all TwoWay Transactions
     */
    public List<UUID> getTwoWayTransactions(){
        return twoWayTransactions;
    }

    /**
     * @return an ArrayList of all Transactions
     */
    public List<UUID> getAllPastTransactions(){
        List<UUID> allTransactions = new ArrayList<>();
        allTransactions.addAll(oneWayTransactions);
        allTransactions.addAll(twoWayTransactions);
        return allTransactions;
    }

    /**
     * This returns whether the transaction history object doesn't have any past transactions in it.
     * @return returns true iff past transactions is empty.
     */
    public boolean isPastEmpty(){
        return(this.getAllPastTransactions().isEmpty());
    }
    /**
     * @return all of users and the times they have been traded with
     */
    public HashMap<String, Integer> getUsersNumTradeTimes(){
        return usersNumTradeTimes;
    }

    /**
     * Getter for this Users.TradingUser's number of transactions.
     * @return numTransactions as an integer.
     */
    public int getNumTransactions() {
        return oneWayTransactions.size() + twoWayTransactions.size();
    }

    /**
     * Getter for numItemsLended as an int
     * @return numItemsLended as an integer
     */
    public int getNumItemsLended() { return numItemsLended; }

    /**
     * Setter for numItemsLended, increases by 1 every time it is called
     */
    public void setNumItemsLended() { this.numItemsLended++; }

    /**
     * Getter for numItemsBorrowed
     * @return numItemsBorrowed as an integer
     */
    public int getNumItemsBorrowed() { return numItemsBorrowed; }

    /**
     * Setter for numItemsBorrowed, increases by 1 every time it is called
     */
    public void setNumItemsBorrowed() { this.numItemsBorrowed++; }

    /**
     * adds a Transactions.Transaction to the Transactions.Transaction History
     * @param transaction the transaction being added to the history
     */
    public void setTransactionHistory(Transaction transaction) {
        if (transaction.isOneWay()) {
            oneWayTransactions.add(transaction.getId());
        } else {
            twoWayTransactions.add(transaction.getId());
        }
    }
}
