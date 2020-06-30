import javax.swing.text.html.Option;
import java.util.*;

public class UserMenuViewer {
    private UserMenu userMenu;
    private int input;

    public UserMenuViewer(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    private int HandleOptions(Scanner scanner, List<String> OptionList, boolean BackOption, String OptionPrompt){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
        if(BackOption) {
            String LastIndex = Integer.toString(OptionList.size() + 1);
            String LastOption = ". Go back";
            System.out.println(LastIndex + LastOption);

            int OptionChosen;
            do {
                System.out.println(OptionPrompt);
                while (!scanner.hasNextInt()) {
                    System.out.println("That is not a valid option." +
                            " Please enter a number corresponding to one of the options.");
                    scanner.next();
                }
                OptionChosen = scanner.nextInt();
            } while (OptionChosen > OptionList.size() + 1 || OptionChosen <= 0);
            return (OptionChosen);
        }
        else{
            int OptionChosen;
            do {
                System.out.println(OptionPrompt);
                while (!scanner.hasNextInt()) {
                    System.out.println("That is not a valid option." +
                            "Please enter a number corresponding to one of the options.");
                    scanner.next();
                }
                OptionChosen = scanner.nextInt();
            } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
            return (OptionChosen);
        }
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

            System.out.println("Pick an option from above.");
            input = scanner.nextInt();

            if (input == 1) {
                requestAddItem(scanner);
            } else if (input == 2) {
                 DisplayAvailableItems(scanner);
            } else if (input == 3) {
                // call this.um.getActiveTransactions()
            } else if (input == 4) {
                // call um.method()
            } else if (input == 5) {
                viewWishlist(scanner);
            } else if (input == 6) {
                viewInventory(scanner);
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

    private void requestAddItem(Scanner scanner){
        System.out.println("What is the name of your item?");
        String itemName = scanner.nextLine();
        System.out.println("What is the description of this item?");
        String itemDescription = scanner.nextLine();
        this.userMenu.requestAddItemInput(itemName,itemDescription);
        System.out.println("Item has been requested and is now being reviewed by the administrators.");
    }

    /**
     * NOT FINISHED! (Almost finished though)
     * This is the method for handling the flow of:
     * 1) Displaying all available items to trade.
     * 2) Allowing the user to go to an item.
     * 3) Check if the transaction can occur.
     *
     * QUESTION: ARE THE AVAILABLE ITEMS FROM OTHER USERS WHO CAN TRADE? DO WE CHECK BEFORE ADDING TO AVAILABLE ITEMS
     * OR DO WE CHECK ONCE THE USER HAS SELECTED AN ITEM?
     *
     * @param scanner the scanner constructed from the run method.
     */
    private void DisplayAvailableItems(Scanner scanner){
        System.out.println("Available Items for Trade:");
        String ItemOutputName = ") Item Name: ";
        String ItemOutputDescription = " |  Item Description: ";
        HashMap<Item, User> AvailableItems  = this.userMenu.getAvailableItems();
        List<Item> ItemList = new ArrayList<>(AvailableItems.keySet());
        // Making Option List

        List<String> OptionList = new ArrayList<>();
        for (Item item : ItemList) {
            OptionList.add(ItemOutputName + item.getName() + ItemOutputDescription + item.getDescription());
        }
        String AvailableItemsPrompt = "Type the number corresponding to the item you wish to" +
                " create transaction for. To go back to the previous menu, type the number corresponding to that" +
                "option.";

        int OptionChosen = this.HandleOptions(scanner,OptionList,true,AvailableItemsPrompt);
        // Logic handling back to other menu vs. Proceed to make transaction.
        if(OptionChosen > OptionList.size()){
            System.out.println("Loading Previous Menu");
        }
        else{
            Item TransactionItem = ItemList.get(OptionChosen -1);
            User TransactionItemOwner = AvailableItems.get(TransactionItem);
            // This next control flow accounts for if a user can make a transaction. DO I NEED TO DO THIS??
            //if(){
                //CreateTransactionMenu(scanner,TransactionItem,TransactionItemOwner);
            //}
            //else{

            //}
        }

    }


    /**
     * NOT FINISHED!
     * This method handles the flow for setting up a transaction for an available item assuming that the transaction
     * is allowed between the 2 users.
     *
     * @param scanner the scanner constructed in the run method.
     * @param item The item that is going to be traded.
     * @param Owner The other user that is currently the owner of the item you want to trade for.
     */
    private void CreateTransactionMenu(Scanner scanner, Item item, User Owner){
        System.out.println("Transaction Menu");
        System.out.println("----------------");

        MeetingDetailsMenu(scanner, "First Meeting Details");
        List<String> OptionList = new ArrayList<>();
        OptionList.add("One Way Temporary Trade");
        OptionList.add("One Way Permanent Trade");
        OptionList.add("Two Way Temporary Trade");
        OptionList.add("Two Way Permanent Trade");
        int OptionChosen = this.HandleOptions(scanner,OptionList,true, "Select the kind of trade you would like to make for this item?");



        int OneWayOption = scanner.nextInt();
        if(OneWayOption == 1){
            System.out.println("First Meeting Details");
            System.out.println("----------------------");

        }



    }

    /**
     * NOT FINISHED!
     *
     * Question: Is it ok if I just ask sequentially about the meeting?(Even though this might not be extendable)
     * @param scanner the scanner of the
     * @param MeetingTitle The first thing that will be displayed "Second Meeting Details"/"First Meeting Details"
     */
    private void MeetingDetailsMenu(Scanner scanner, String MeetingTitle){
        System.out.println(MeetingTitle);
        System.out.println("----------------------");
        System.out.println("What Location");
    }

    private void viewWishlist(Scanner scanner) {
        if (userMenu.getUserWishlist() == null) {
            System.out.println("Your wishlist is empty.");
            System.out.println("Loading Previous Menu");
        }
        else {
            Iterator<Item> itemIterator = userMenu.getUserWishlist().iterator();
            List<String> optionList = new ArrayList<>();
            while (itemIterator.hasNext()) {
                optionList.add(itemIterator.next().toString());
                System.out.println(itemIterator.next().toString());
            }
            int optionChosen = HandleOptions(scanner, optionList, true, "Select an item if you wish to remove it from your wishlist.");
            if (optionChosen == optionList.size() + 1) {
                System.out.println("Loading Previous Menu");
            }
            else {
                userMenu.withdrawItem(userMenu.getUserWishlist().get(optionChosen), "wishlist");
                System.out.println("The item has been removed from your wishlist.");
            }
        }
    }

    private void viewInventory(Scanner scanner) {
        if (userMenu.getUserInventory() == null) {
            System.out.println("Your inventory is empty.");
            System.out.println("Loading Previous Menu");
        }
        else {
            Iterator<Item> itemIterator = userMenu.getUserInventory().iterator();
            List<String> optionList = new ArrayList<>();
            while (itemIterator.hasNext()) {
                optionList.add(itemIterator.next().toString());
                System.out.println(itemIterator.next().toString());
            }
            int optionChosen = HandleOptions(scanner, optionList, true, "Select an item if you wish to remove it from your inventory.");
            if (optionChosen == optionList.size() + 1) {
                System.out.println("Loading Previous Menu");
            }
            else {
                userMenu.withdrawItem(userMenu.getUserInventory().get(optionChosen), "inventory");
                System.out.println("The item has been removed from your inventory.");
            }
        }
    }
}
