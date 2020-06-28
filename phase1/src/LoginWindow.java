import java.util.Scanner;

public class LoginWindow {
    private int input;

    public int run() {
        boolean picking = true;
        Scanner scanner = new Scanner(System.in);
        while (picking) {
            System.out.println("Select an option (number) \n1. Login to existing account \n2. Create a new account");
            input = scanner.nextInt();
            if (input == 1 | input == 2) {
                picking = false;
            } else {
                System.out.println("Not a valid option.");
            }
        }
        return input;
    }

    public String[] getUserAndPass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        return new String[]{username, password};
    }


}
