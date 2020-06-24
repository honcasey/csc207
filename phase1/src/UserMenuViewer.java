import java.util.Scanner;

public class UserMenuViewer {
    private UserMenu um;
    private int input;

    public UserMenuViewer(UserMenu userMenu) {
        um = userMenu;
    }

    public void run() {
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Log Out");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick an option.");
        input = scanner.nextInt();

        if (input == 1) {
            // call um.method 1
        } else if (input == 2) {
            // call um.method 2
        } else if (input == 3) {
            System.out.println("You have successfully logged out.");
        }

    }
}
