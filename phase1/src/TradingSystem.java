import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Allows a user to log in or create a new account.
 */

public class TradingSystem {
    private String username, password;
    // how to get file cited from here https://stackoverflow.com/questions/21059085/how-can-i-create-a-file-in-the-current-users-home-directory-using-java
    private String adminsFilePath = System.getProperty("user.home")
            + File.separator + "Documents" + File.separator + "admins.ser";
    private String usersFilePath = System.getProperty("user.home")
            + File.separator + "Documents" + File.separator + "users.ser";
    private String itemsFilePath = System.getProperty("user.home")
            + File.separator + "Documents" + File.separator + "items.ser";
    private AdminManager adminManager;
    private UserManager userManager;
    private HashMap<Item, User> pendingItems;
    private LoginWindow loginWindow;


    // only method that should be run in this class
    public void run() throws Exception {
        readData();
        checkFirstAdmin();
        loginWindow = new LoginWindow();
        int userInput = loginWindow.run();
        if (userInput == 1) {
            login();
        } else if (userInput == 2) {
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
        checkFileExists(itemsFilePath);

        // files exists so we can deserialize them
        Serializer serializer = new Serializer();
        List<AdminUser> admins = serializer.readAdminsFromFile(adminsFilePath);
        List<User> users = serializer.readUsersFromFile(usersFilePath);
        pendingItems = serializer.readItemsFromFile(itemsFilePath);

        // create new Managers
        adminManager = new AdminManager(admins);
        userManager = new UserManager(users);
    }

    // helper method to make sure files exists before we read them, if they
    // don't exist then create new files
    private void checkFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            // do nothing
        } else {
            // create a new file
            Serializer serializer = new Serializer();
            if (filePath.equals(adminsFilePath)) {
                List<AdminUser> list = new ArrayList<>();
                serializer.writeAdminsToFile(filePath, list);
            } else if (filePath.equals(usersFilePath)) {
                List<User> list = new ArrayList<>();
                serializer.writeUsersToFile(filePath ,list);
            } else if (filePath.equals(itemsFilePath)) {
                HashMap<Item, User> map = new HashMap<>();
                serializer.writeItemsToFile(filePath, map);
            }
        }
    }

    private void writeData() throws IOException {
        Serializer serializer = new Serializer();
        serializer.writeUsersToFile(usersFilePath, userManager.getAllUsers());
        serializer.writeAdminsToFile(adminsFilePath, adminManager.getAllAdmins());
        serializer.writeItemsToFile(itemsFilePath, pendingItems);
    }

    // helper method to get username and password
    private void parseCredentials(String[] credentials) {
        username = credentials[0];
        password = credentials[1];
    }

    // helper method to log in
    private void login() throws Exception {
        // get username and password
        parseCredentials(loginWindow.getUserAndPass());

        boolean notLoggedIn = true;
        // try to log in with current user and pass, if unsuccessful prompt for new user and pass and try again
        while (notLoggedIn) {
            if (adminManager.validAdmin(username, password)) {
                notLoggedIn = false;
                AdminMenu adminMenu = new AdminMenu(adminManager,
                        userManager, pendingItems, adminManager.getAdmin(username));
                AdminMenuViewer adminMenuViewer = new AdminMenuViewer(adminMenu);
                adminMenuViewer.run();
            } else if (userManager.validUser(username, password)) {
                notLoggedIn = false;
                UserMenu userMenu = new UserMenu(userManager,
                        adminManager, pendingItems, userManager.getUser(username));
                UserMenuViewer userMenuViewer = new UserMenuViewer(userMenu);
                userMenuViewer.run();
            } else {
                // no user or admin account that corresponds to user and pass
                System.out.println("Incorrect username or password.");
                parseCredentials(loginWindow.getUserAndPass());
            }
        }
    }

    // helper method to create an account
    private void createAccount() throws Exception {
        // get username and password
        parseCredentials(loginWindow.getUserAndPass());

        // continue loop if username is already taken
        while (!userManager.checkAvailableUsername(username) && !adminManager.checkAvailableUsername(username)) {
            System.out.println("Username already taken!");
            parseCredentials(loginWindow.getUserAndPass());
        }

        // create a new user
        userManager.addUser(username, password);

        // get user who's using this program
        User user = userManager.getUser(username);
        UserMenu userMenu = new UserMenu(userManager, adminManager, pendingItems, user);
        UserMenuViewer userMenuViewer = new UserMenuViewer(userMenu);
        userMenuViewer.run();
    }

    private void checkFirstAdmin() {
        if (adminManager.validAdmin("admin", "password")) {
            // the first admin exists, do nothing
        } else {
            // the first admin does not exist yet, create it
            adminManager.addAdmin("admin", "password").setFirstAdmin(true);
        }
    }
}

