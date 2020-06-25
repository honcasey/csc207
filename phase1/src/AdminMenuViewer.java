import java.util.Scanner;

public class AdminMenuViewer {
    private AdminMenu am;
    private int input;

    public AdminMenuViewer(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public void run() {
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Log Out");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pick an option.");
        input = scanner.nextInt();

        if (input == 1) {
            // call am.method 1
        } else if (input == 2) {
            // call am.method 2
        } else if (input == 3) {
            System.out.println("You are being logged out, saving data.");

        }

    }
}
