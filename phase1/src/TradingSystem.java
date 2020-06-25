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
    private boolean notLoggedIn = true;
    private List<AdminUser> admins;
    private List<User> users;
    private AdminManager adminManager;
    private UserManager userManager;
    private HashMap<Item, User> pendingItems;
    private LoginWindow loginWindow;


    // only method that should be run in this class
    public void run() throws IOException {
        readData();
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
    private void readData() throws IOException {
        // make sure files exists so they can be read
        checkFileExists(adminsFilePath);
        checkFileExists(usersFilePath);
        checkFileExists(itemsFilePath);

        // files exists so we can deserialize them
        Serializer serializer = new Serializer();
        serializer.readAdminsFromFile(adminsFilePath);
        serializer.readUsersFromFile(usersFilePath);
        serializer.readItemsFromFile(itemsFilePath);

        // set the retrieved objects to local variables
        admins = serializer.getAdmins();
        users = serializer.getUsers();
        adminManager = new AdminManager(admins);
        userManager = new UserManager(users);
        pendingItems = serializer.getPendingItems();
    }

    // helper method to make sure files exists before we read them, if they
    // don't exist then create new files
    private void checkFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            ; // do nothing
        } else {
            // create a new file
            Serializer serializer = new Serializer();
            if (filePath.equals(adminsFilePath)) {
                List<AdminUser> list = new ArrayList<>();
                serializer.setAdmins(list);
                serializer.writeAdminsToFile(filePath);
            } else if (filePath.equals(usersFilePath)) {
                List<User> list = new ArrayList<>();
                serializer.setUsers(list);
                serializer.writeUsersToFile(filePath);
            } else if (filePath.equals(itemsFilePath)) {
                HashMap<Item, User> map = new HashMap<>();
                serializer.setItems(map);
                serializer.writeItemsToFile(filePath);
            }
        }
    }

    private void writeData() {
    }

    // helper method to get username and password
    private void parseCredentials(String[] credentials) {
        username = credentials[0];
        password = credentials[1];
    }

    // helper method to log in
    private void login() {
        // get username and password
        parseCredentials(loginWindow.getUserAndPass());

        // try to log in with current user and pass, if unsuccessful prompt for new user and pass and try again
        while (notLoggedIn) {
            // check if credentials are for an admin account
            for (AdminUser admin : admins) {
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    notLoggedIn = false;
                    AdminMenu adminMenu = new AdminMenu(adminManager, userManager, pendingItems, admin);
                    AdminMenuViewer adminMenuViewer = new AdminMenuViewer(adminMenu);
                    adminMenuViewer.run();
                }
            }
            // not admin so check if credentials are for a user account
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    notLoggedIn = false;
                    UserMenu userMenu = new UserMenu(userManager, adminManager, pendingItems, user);
                    UserMenuViewer userMenuViewer = new UserMenuViewer(userMenu);
                    userMenuViewer.run();
                }
            }
            // no user or admin account that corresponds to user and pass
            System.out.println("Incorrect username or password.");
            parseCredentials(loginWindow.getUserAndPass());
        }
    }

    // helper method to create an account
    private void createAccount() {
        // get username and password
        parseCredentials(loginWindow.getUserAndPass());

        // continue loop if username is already taken
        while (!userManager.checkAvailableUsername(username)) {
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

}

