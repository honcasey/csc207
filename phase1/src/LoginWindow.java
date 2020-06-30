import java.util.Scanner;

/**
 * A window
 */
public class LoginWindow {
    private int input;

    // cited from https://stackoverflow.com/questions/3059333/validating-input-using-java-util-scanner/3059367#3059367
    public int run() {
        boolean picking = true;
        while (picking) {
            getNumericInput();
            if (input == 1 | input == 2) {
                picking = false;
            }
        }
        return input;
    }

    private void getNumericInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option. \n1. Login to existing account \n2. Create a new account");
        while (!scanner.hasNextInt()) {
            System.out.println("Not a valid option.");
            scanner.next();
            System.out.println("Select an option. \n1. Login to existing account \n2. Create a new account");
        }
        input = scanner.nextInt();
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
