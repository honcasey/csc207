import Admins.AdminManager;
import Admins.AdminMenuController;
import Admins.AdminUser;
import Exceptions.InvalidAdminException;
import Exceptions.InvalidTradingUserException;
import Items.Item;
import Items.ItemManager;
import Presenters.BootupMenuPresenter;
import Transactions.PastTransactionManager;
import Transactions.Transaction;
import Transactions.CurrentTransactionManager;
import Users.TradingUser;
import Users.TradingUserManager;
import Users.UserMenuController;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The TradingSystem class handles logging on and the creation of new Users.TradingUser accounts.
 * <p>
 * If logging on to an existing account, directs the user to either the administrator menu
 * or the user menu.
 * </p>
 */

public class TradingSystem {
    private String username, password;
    private final String adminsFilePath = "admins.ser";
    private final String usersFilePath = "users.ser";
    private final String requestedItemsFilePath = "requestedItems.ser";
    private final String flaggedAccountsFilePath = "flaggedAccounts.ser";
    private final String frozenAccountsFilePath = "frozenAccounts.ser";
    private final String transactionsFilePath = "transactions.ser";
    private final String itemMapFilePath = "items.ser";
    private AdminManager adminManager;
    private TradingUserManager tradingUserManager;
    private CurrentTransactionManager currentTransactionManager;
    private PastTransactionManager pastTransactionManager;
    private ItemManager itemManager;
    private Map<Item, TradingUser> pendingItems;
    private final BootupMenuPresenter bmp = new BootupMenuPresenter();


    /**
     * Reads data from saved files, redirects user to appropriate class depending on
     * user input, saves changes by updating files before program exits.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void run() throws IOException, ClassNotFoundException {
        readData();
        checkFirstAdmin();
        int userInput = bmp.selectOption();
        if (userInput == 0) {
            login();
        } else if (userInput == 1) {
            createAccount();
        }
        writeData();
    }

    /**
     * Helper method to retrieve data from files.
     */
    private void readData() throws IOException, ClassNotFoundException {
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
        adminManager = new AdminManager(admins, flaggedAccounts, frozenAccounts);
        tradingUserManager = new TradingUserManager(tradingUsers, flaggedAccounts, frozenAccounts);
        currentTransactionManager = new CurrentTransactionManager(transactions);
        pastTransactionManager = new PastTransactionManager(transactions);
        itemManager = new ItemManager(items);
    }

    /**
     * A helper method to check if the file specified by filePath exists. If this file does
     * not exist, this method generates it.
     */
    private void checkFileExists(String filePath) throws IOException {
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
    private void writeData() throws IOException {
        Serializer serializer = new Serializer();
        serializer.writeUsersToFile(usersFilePath, tradingUserManager.getAllTradingUsers());
        serializer.writeAdminsToFile(adminsFilePath, adminManager.getAllAdmins());
        serializer.writeItemsToFile(requestedItemsFilePath, pendingItems);
        serializer.writeAccountsToFile(flaggedAccountsFilePath, tradingUserManager.getFlaggedAccounts());
        serializer.writeAccountsToFile(frozenAccountsFilePath, tradingUserManager.getFrozenAccounts());
        serializer.writeTransactionsToFile(transactionsFilePath, currentTransactionManager.getAllTransactions());
        serializer.writeItemsMapToFile(itemMapFilePath, itemManager.getAllItems());
    }

    /**
     * A helper method to get username and password inputted by the user.
     */
    private void parseCredentials(String[] credentials) {
        username = credentials[0];
        password = credentials[1];
    }

    /**
     *  A helper method used to validate the username and password, if correct run the correct menu.
     */
    private void login() {
        // get username and password
        parseCredentials(getUserAndPass());

        boolean notLoggedIn = true;
        // try to log in with current user and pass, if unsuccessful prompt for new user and pass and try again
        while (notLoggedIn) {
            if (adminManager.validAdmin(username, password)) {
                notLoggedIn = false;
                try {
                    AdminMenuController adminMenuController = new AdminMenuController(adminManager,
                            tradingUserManager, pendingItems, adminManager.getAdmin(username), itemManager);
                    adminMenuController.run();
                } catch(InvalidAdminException e) {
                    // we already checked this username corresponds to a valid admin on line 109
                    // so technically adminManager.getAdmin(username) should never throw an exception
                }
            } else if (tradingUserManager.validUser(username, password)) {
                notLoggedIn = false;
                try {
                    UserMenuController userMenuController = new UserMenuController(tradingUserManager, adminManager,
                            currentTransactionManager, pastTransactionManager, itemManager,
                            pendingItems, tradingUserManager.getTradingUser(username));
                    userMenuController.run();
                } catch(InvalidTradingUserException e) {
                    // we already checked this username corresponds to a valid user on line 120
                    // so technically userManager.getUser(username) should never throw an exception
                }
            } else {
                // no user or admin account that corresponds to user and pass
                System.out.println(bmp.getInvalidCredentials());
                parseCredentials(getUserAndPass());
            }
        }
    }

    /**
     * A helper method to create a new user account.
     */
    private void createAccount() {
        // get username and password
        parseCredentials(getUserAndPass());
        boolean userInteracting = true;
        while (userInteracting) {
            if (adminManager.checkAvailableUsername(username) && tradingUserManager.checkAvailableUsername(username)) {
                try {
                    userInteracting = false;
                    TradingUser tradingUser = tradingUserManager.addTradingUser(username, password);
                    UserMenuController userMenuController = new UserMenuController(tradingUserManager, adminManager,
                            currentTransactionManager, pastTransactionManager, itemManager, pendingItems, tradingUser);
                    userMenuController.run();
                } catch(InvalidTradingUserException e) {
                    // we just created this new user so we know it's a valid user so userManager.getUser()
                    // should not throw an Exceptions.InvalidUserException
                }
            } else {
                System.out.println(bmp.getTakenUsername());
                parseCredentials(getUserAndPass());
            }
        }
    }

    /**
     * A helper method to check if the first admin already exists. If the first admin does not exist
     * this method creates it.
     */
    private void checkFirstAdmin() {
        if (adminManager.validAdmin("admin", "password")) {
            // the first admin exists, do nothing
        } else {
            // the first admin does not exist yet, create it
            try {
                adminManager.addAdmin("admin", "password").setFirstAdmin(true);
            } catch(InvalidAdminException e) {
                // If this admin is the first to be added in the list then it will never
                // conflict with another admin so an exception will never be thrown by addAdmin()
            }
        }
    }

    public String[] getUserAndPass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(bmp.getUsernamePrompt());
        String username = scanner.nextLine();
        System.out.println(bmp.getPasswordPrompt());
        String password = scanner.nextLine();
        return new String[]{username, password};
    }
}


