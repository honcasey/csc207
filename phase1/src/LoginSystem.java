import java.util.List;
import java.util.Scanner;

/**
 * Allows a user to log in or create a new account.
 */

public class LoginSystem {
    private String username, password;
    List<AdminUser> admins;
    List<User> users;
    // TODO what do we set as our file paths?
    private String adminFilePath, userFilePath;
    private boolean notLoggedIn = true;
    private AdminManager adminManager;
    private UserManager userManager;

    /**
     * Helper method to retrieve the lists of all users and admins.
     */
    private void deserialize() {
        Serializer serializer = new Serializer();
        serializer.readAdminsFromFile(adminFilePath);
        serializer.readUsersFromFile(userFilePath);
        admins = serializer.getAdmins();
        users = serializer.getUsers();
        adminManager = new AdminManager(admins);
        userManager = new UserManager(users);
    }

    // helper method to get username and password from user
    private void getUserAndPass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        username = scanner.nextLine();
        System.out.println("Enter password:");
        password = scanner.nextLine();
    }

    // helper method to log in
    private void login() {
        getUserAndPass();

        // try to log in with current user and pass, if unsuccessful prompt for new user and pass and try again
        while (notLoggedIn) {
            // check if credentials are for an admin account
            for (AdminUser admin : admins) {
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    notLoggedIn = false;
                    AdminMenuViewer adminMenuViewer = new AdminMenuViewer(adminManager, userManager, admin);
                    adminMenuViewer.run();
                }
            }
            // check if credentials are for a user account
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    notLoggedIn = false;
                    UserMenuViewer userMenuViewer = new UserMenuViewer(userManager, adminManager, user);
                    userMenuViewer.run();
                }
            }
            // no account that corresponds to user and pass
            System.out.println("Incorrect username or password.");
            getUserAndPass();
        }
    }

    // helper method to create an account
    private void createAccount() {
        getUserAndPass();

        while (!userManager.checkAvailableUsername(username)) {
            System.out.println("Username already taken!");
            getUserAndPass();
        }
        // create a new user
        userManager.addUser(username, password);
        // get user who's using this program
        User user = userManager.getUser(username);
        UserMenuViewer userMenuViewer = new UserMenuViewer(userManager, user);
        userMenuViewer.run();
    }

    // only method that should be run in this class
    public void run() {
        deserialize();
        boolean picking = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option (number) \n1. Login to existing account \n2. Create a new account");
        int input = scanner.nextInt();
        while (picking) {
            if (((Integer) input).equals(1)) {
                picking = false;
                login();
            } else if (((Integer) input).equals(2)) {
                picking = false;
                createAccount();
            } else {
                System.out.println("Not a valid option.");
                System.out.println("1. Login to existing account \n2. Create a new account");
                input = scanner.nextInt();
            }
        }
    }
}

