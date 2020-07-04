import javax.swing.text.html.Option;
import java.util.*;

public class UserMenuController {
    /**
     *
     */


    private UserMenu userMenu;
    private int input;

    public UserMenuController(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    /**
     * takes in a list of options, scanner
     * @param scanner
     * @param OptionList
     * @param BackOption
     * @param OptionPrompt
     * @return
     */
    private int HandleOptions(Scanner scanner, List<String> OptionList, boolean BackOption, String OptionPrompt) {
        if(BackOption) {
            this.addBackOption(OptionList);
        }
        this.displayOptions(OptionList);
        return (this.selectOption(OptionList,OptionPrompt));
    }

    /**
     * Formats and displays a list of options to the user.
     * @param OptionList the list of options that you want to be displayed.
     */
    public void displayOptions(List<String> OptionList){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
    }

    /**
     * Adds Back option at the end of options being displayed to the user.
     * @param OptionList The list of options being displayed prior to calling this method.
     */
    public void addBackOption(List<String> OptionList){
        String LastIndex = Integer.toString(OptionList.size() + 1);
        String LastOption = ". Go back";
        OptionList.add(LastIndex + LastOption);
    }

    public int selectOption(List<String> OptionList, String OptionPrompt){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            System.out.println(OptionPrompt);
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid option. Please enter a number corresponding to one of the options.");
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionChosen);
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
            System.out.println("7. Request Admin to Unfreeze Account");
            System.out.println("8. Log Out");
            System.out.println("Pick an option from above.");
            input = scanner.nextInt();
            if (input == 1) {
                requestAddItem();
            } else if (input == 2) {
                DisplayAvailableItems();
            } else if (input == 3) {
                // call this.um.getActiveTransactions()
            } else if (input == 4) {
                viewPastTransaction();
            } else if (input == 5) {
                viewWishlist();
            } else if (input == 6) {
                viewInventory();
            } else if (input == 7) {
                requestUnfreezeAccount();
            } else if (input == 8) {
                System.out.println("You have successfully logged out.");
                // stop the while loop
                UserMenuActivity = false;
            }
        }
    }

    /**
     * This takes in input from user and creates
     */
    private void requestAddItem(){
        Scanner scanner = new Scanner(System.in);
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
     */
    private void DisplayAvailableItems(){
        Scanner scanner = new Scanner(System.in);
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
     * @param item The item that is going to be traded.
     * @param Owner The other user that is currently the owner of the item you want to trade for.
     */
    private void CreateTransactionMenu(Item item, User Owner){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transaction Menu");
        System.out.println("----------------");

        MeetingDetailsMenu("First Meeting Details");
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
     * @param MeetingTitle The first thing that will be displayed "Second Meeting Details"/"First Meeting Details"
     */
    private void MeetingDetailsMenu(String MeetingTitle){
        Scanner scanner = new Scanner(System.in);
        System.out.println(MeetingTitle);
        System.out.println("----------------------");
        System.out.println("What Location");
    }

    private void viewWishlist() {
        Scanner scanner = new Scanner(System.in);
        if (userMenu.currentUser.getWishlist() == null) {
            System.out.println("Your wishlist is empty.");
        }
        else {
            Iterator<Item> itemIterator = userMenu.currentUser.getWishlist().iterator();
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
                userMenu.withdrawItem(userMenu.currentUser.getWishlist().get(optionChosen), "wishlist");
                System.out.println("The item has been removed from your wishlist.");
            }
        }
    }

    private void viewInventory() {
        Scanner scanner = new Scanner(System.in);
        if (userMenu.currentUser.getInventory() == null) {
            System.out.println("Your inventory is empty.");
        }
        else {
            Iterator<Item> itemIterator = userMenu.currentUser.getInventory().iterator();
            List<String> optionList = new ArrayList<>();
            while (itemIterator.hasNext()) {
                optionList.add(itemIterator.next().toString());
            } // TO-DO: can this be shortened to add all the items at once in one line?
            int optionChosen = HandleOptions(scanner, optionList, true, "Select an item if you wish to remove it from your inventory.");
            if (optionChosen == optionList.size() + 1) {
                System.out.println("Loading Previous Menu");
            }
            else {
                userMenu.withdrawItem(userMenu.currentUser.getInventory().get(optionChosen), "inventory");
                System.out.println("The item has been removed from your inventory.");
            }
        }
    }

    private void requestUnfreezeAccount() {
        if (userMenu.currentUser.isFrozen()) {
            userMenu.requestUnfreezeAccount();
            System.out.println("You have successfully requested for your account to be unfrozen.");
        }
        else {
            System.out.println("Your account is not frozen.");
        }
    }

    private void viewPastTransaction(){
        TransactionHistory transactionHistory= userMenu.currentUser.getTransactionHistory();
        if (transactionHistory == null){
            System.out.println("Your transaction history is empty.");
        }
        else {
            System.out.println(transactionHistory.toString());
        }
    }
}
