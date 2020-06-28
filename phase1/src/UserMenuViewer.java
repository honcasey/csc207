import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

public class UserMenuViewer {
    private UserMenu userMenu;
    private int input;

    public UserMenuViewer(UserMenu userMenu) {
        this.userMenu = userMenu;
    }
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean UserMenuActivity = true;

        while (UserMenuActivity) {
            System.out.println("1. Request Item for Approval");
            System.out.println("2. Browse Available Items for Trade");
            System.out.println("3. View Active Transactions");
            System.out.println("4. View Past Transaction Details");
            System.out.println("5. View Wishlist");
            System.out.println("6. View Inventory");
            System.out.println("7. Log Out");

            System.out.println("Pick an option.");
            input = scanner.nextInt();

            if (input == 1) {
                this.requestAddItem(scanner);
            } else if (input == 2) {
                 this.getAvailableItemsFlow(scanner);
            } else if (input == 3) {
                // call this.um.getActiveTransactions()
            } else if (input == 4) {
                // call um.method()
            } else if (input == 5) {
                // call um.method
            } else if (input == 6) {
                // call um.method
            } else if (input == 7) {
                System.out.println("You have successfully logged out.");
                // stop the while loop
                UserMenuActivity = false;
            }
        }
    }

    /**
     * This takes in input from user and creates
     * @param scanner the scanner being used by the main run method of the
     */

    public void requestAddItem(Scanner scanner){
        System.out.println("What is the name of your item?");
        String itemName = scanner.nextLine();
        System.out.println("What is the description of this item?");
        String itemDescription = scanner.nextLine();
        this.userMenu.requestAddItemInput(itemName,itemDescription);
        System.out.println("Item has been requested and is now being reviewed by the administrators.");
    }

    public void DisplayAvailableItems(Scanner scanner){
        String ItemOutputName = ") Item Name:";
        String ItemOutputDescription = " ||  Item Description:";
        List<Item> AvailableItems = this.userMenu.getAvailableItems();
        for (int i = 0; i < AvailableItems.size(); i++){
            String index = Integer.toString(i);
            String OutputLine =  index + ItemOutputName + AvailableItems.get(i).getName() +
                    ItemOutputDescription+AvailableItems.get(i).getDescription();
            System.out.println(ItemOutputDescription);
        }
        String LastIndex = Integer.toString(AvailableItems.size()+ 1);
        String LastOption =") Go back";
        System.out.println(LastIndex+LastOption);
    }
    public void SelectAvailableItem(Scanner scanner){
        int nextOption = scanner.nextInt();
        List<Item> AvailableItems = this.userMenu.getAvailableItems();




    }

    public void CreateTransaction(Scanner scanner){

    }

}
