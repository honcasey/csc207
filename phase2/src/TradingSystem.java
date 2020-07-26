import Admins.AdminManager;
import Admins.AdminMenuController;
import Admins.AdminUser;
import Exceptions.InvalidAdminException;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Items.ItemManager;
import Transactions.PastTransactionManager;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;
import Users.TradingUser;
import Users.TradingUserManager;
import Users.UserMenuController;

import java.io.File;
import java.util.*;

/**
 * <h1>TradingSystem</h1>
 *
 * <p>The TradingSystem class handles logging on and the creation of new Users.TradingUser accounts.</p>
 * <p>If logging on to an existing account, directs the user to either the administrator menu
 * or the user menu.</p>
 */

public class TradingSystem {
    private final String adminsFilePath = "admins.ser";
    private final String usersFilePath = "users.ser";
    private final String requestedItemsFilePath = "requestedItems.ser";
    private final String flaggedAccountsFilePath = "flaggedAccounts.ser";
    private final String frozenAccountsFilePath = "frozenAccounts.ser";
    private final String transactionsFilePath = "transactions.ser";
    private final String itemMapFilePath = "items.ser";
    private AdminManager am;
    private TradingUserManager tum;
    private CurrentTransactionManager tm;
    private PastTransactionManager ptm;
    private ItemManager im;
    private Map<Item, TradingUser> pendingItems;
    private AdminMenuController amc;
    private UserMenuController umc;

    /**
     * Calls to different helper methods to read data from saved files, redirects user to
     * appropriate class depending on user input, and saves changes by writing files.
     */
    public void run() {
        readData();
        checkFirstAdmin();
        LoginWindow lw = new LoginWindow(amc, umc);
        lw.display();
        writeData();
    }

    /**
     * Helper method to retrieve data from files.
     */
    private void readData() {
        // make sure files exists so they can be read
        checkFileExists(adminsFilePath);
        checkFileExists(usersFilePath);
        checkFileExists(requestedItemsFilePath);
        checkFileExists(flaggedAccountsFilePath);
        checkFileExists(frozenAccountsFilePath);
        checkFileExists(transactionsFilePath);
        checkFileExists(itemMapFilePath);

        // files exists so we can deserialize them
        Serializer serializer = new Serializer();
        List<AdminUser> admins = serializer.readAdminsFromFile(adminsFilePath);
        List<TradingUser> tradingUsers = serializer.readUsersFromFile(usersFilePath);
        pendingItems = serializer.readItemsFromFile(requestedItemsFilePath);
        List<TradingUser> flaggedAccounts = serializer.readAccountsFromFile(flaggedAccountsFilePath);
        List<TradingUser> frozenAccounts = serializer.readAccountsFromFile(frozenAccountsFilePath);
        Map<UUID, Transaction> transactions = serializer.readTransactionMapFromFile(transactionsFilePath);
        Map<UUID, Item> items = serializer.readItemMapFromFile(itemMapFilePath);

        // create new Managers
        am = new AdminManager(admins, flaggedAccounts, frozenAccounts);
        tum = new TradingUserManager(tradingUsers, flaggedAccounts, frozenAccounts);
        tm = new CurrentTransactionManager(transactions);
        ptm = new PastTransactionManager(transactions);
        im = new ItemManager(items);

        // create new controllers
        amc = new AdminMenuController(am, tum, pendingItems, im);
        umc = new UserMenuController(tum, am, tm, ptm, im, pendingItems);
    }

    /**
     * A helper method to check if the file specified by filePath exists. If this file does
     * not exist, this method generates it.
     */
    private void checkFileExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            // do nothing
        } else {
            // create a new file
            Serializer serializer = new Serializer();
            switch (filePath) {
                case adminsFilePath: {
                    List<AdminUser> list = new ArrayList<>();
                    serializer.writeAdminsToFile(filePath, list);
                    break;
                }
                case usersFilePath: {
                    List<TradingUser> list = new ArrayList<>();
                    serializer.writeUsersToFile(filePath, list);
                    break;
                }
                case requestedItemsFilePath:
                    HashMap<Item, TradingUser> map = new HashMap<>();
                    serializer.writeItemsToFile(filePath, map);
                    break;
                case flaggedAccountsFilePath:
                case frozenAccountsFilePath: {
                    List<TradingUser> tradingUsers = new ArrayList<>();
                    serializer.writeAccountsToFile(filePath, tradingUsers);
                    break;
                }
                case transactionsFilePath:
                    Map<UUID, Transaction> transactions = new HashMap<>();
                    serializer.writeTransactionsToFile(filePath, transactions);
                    break;
                case itemMapFilePath:
                    Map<UUID, Item> itemMap = new HashMap<>();
                    serializer.writeItemsMapToFile(filePath, itemMap);
                    break;
            }
        }
    }

    /**
     * A helper method that saves the changes made by the user.
     */
    private void writeData() {
        Serializer serializer = new Serializer();
        serializer.writeUsersToFile(usersFilePath, tum.getAllTradingUsers());
        serializer.writeAdminsToFile(adminsFilePath, am.getAllAdmins());
        serializer.writeItemsToFile(requestedItemsFilePath, pendingItems);
        serializer.writeAccountsToFile(flaggedAccountsFilePath, tum.getFlaggedAccounts());
        serializer.writeAccountsToFile(frozenAccountsFilePath, tum.getFrozenAccounts());
        serializer.writeTransactionsToFile(transactionsFilePath, tm.getAllTransactions());
        serializer.writeItemsMapToFile(itemMapFilePath, im.getAllItems());
    }

    /**
     * A helper method to check if the first admin already exists. If the first admin does not exist
     * this method creates it.
     */
    private void checkFirstAdmin() {
        if (am.validAdmin("admin", "password")) {
            // the first admin exists, do nothing
        } else {
            // the first admin does not exist yet, create it
            try {
                am.addAdmin("admin", "password").setFirstAdmin(true);
            } catch(InvalidAdminException e) {
                // If this admin is the first to be added in the list then it will never
                // conflict with another admin so an exception will never be thrown by addAdmin()
            }
        }
    }
}


