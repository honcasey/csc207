import java.util.Scanner;

public class UserMenuViewer {
    private UserMenu um;
    private int input;

    public UserMenuViewer(UserMenu userMenu) {
        um = userMenu;
    }

    public void run() {
        System.out.println("1. Request Item for Approval");
        System.out.println("2. Browse Available Items for Trade");
        System.out.println("3. View Active Transactions");
        System.out.println("4. View Past Transaction Details");
        System.out.println("5. View Wishlist");
        System.out.println("6. View Inventory");
        System.out.println("7. Log Out");

        Scanner scanner = new Scanner(System.in);
        boolean variable = true;

        while (variable) {
            System.out.println("Pick an option.");
            input = scanner.nextInt();

            if (input == 1) {
                // call um.requestAddItem()
            } else if (input == 2) {
                // call um.getAvailableItems()
            } else if (input == 3) {
                // call um.getActiveTransactions()
            } else if (input == 4) {
                // call um.method()
            } else if (input == 5) {
                // call um.method
            } else if (input == 6) {
                // call um.method
            } else if (input == 7) {
                System.out.println("You have successfully logged out.");
                // stop the while loop
                variable = false;
            }
        }
    }
}
