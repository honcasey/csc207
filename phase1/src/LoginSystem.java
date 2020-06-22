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

    /**
     * Helper method to retrieve the lists of all users and admins.
     */
    public void deserialize() {
        Serializer serializer = new Serializer();
        serializer.readAdminsFromFile(adminFilePath);
        serializer.readUsersFromFile(userFilePath);
        admins = serializer.getAdmins();
        users = serializer.getUsers();
    }

    // cited from https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html

    public void getUserAndPass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        username = scanner.nextLine();
        System.out.println("Enter password:");
        password = scanner.nextLine();
    }

    public void run() {
        deserialize();
        getUserAndPass();

        while (notLoggedIn) {
            for (AdminUser admin : admins) {
                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    notLoggedIn = false;
                    AdminMenuViewer.run();
                }
            }

            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    notLoggedIn = false;
                    UserMenuViewer.run();
                }
            }

            System.out.println("Incorrect username or password.");
            getUserAndPass();
        }


    }
}
