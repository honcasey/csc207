package Transactions;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a user's transaction activities.
 */
public class CurrentTransactions {
    private ArrayList<UUID> usersTransactions;

    public ArrayList<UUID> getUsersTransactions() {
        return usersTransactions;
    }

    public void setUsersTransactions(ArrayList<UUID> transactions) {
        usersTransactions = transactions;
    }
}

