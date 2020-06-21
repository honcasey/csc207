import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

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
    public ArrayList<String> mostTradedWithUsers(){
        HashMap<User, Integer> temp = (HashMap<User, Integer>) usersNumTradeTimes.clone();
        ArrayList<User> mostTradedWith = new ArrayList<>();
        ArrayList<String> mostTradedWithUsernames = new ArrayList<>();
        // TODO: Figure out how this top three situation is going to work
        // fuck it, let's just iterate through multiple times because I dont want to deal with a linked hashmap jfc

        // if the user hasn't traded with anyone yet
        if(usersNumTradeTimes.size() == 0){
            String note = new String("You haven't traded with anyone yet!");
            mostTradedWithUsernames.add(note);
            return mostTradedWithUsernames;
        }
        // if user has less than or equal to 3 trades
        if(usersNumTradeTimes.size() <= 3){
            Set<User> users = usersNumTradeTimes.keySet();
            for (User userTemp : users) {
                mostTradedWithUsernames.add(userTemp.getUsername());
            }
            return mostTradedWithUsernames;
            }


        // if user has greater than 3 trades
        int j = 0;
        while(j < 3){
            ArrayList<HashMap<User, Integer>> max = new ArrayList<>();
            for(User i: temp.keySet()){
                if (temp.get(i) < max[0].value(){

            }
            j ++;
        }


    }


}
