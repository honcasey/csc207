import javax.swing.text.html.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UserMenuController {
    /**
     *
     */
    private UserMenu userMenu;
    private UserMenuPresenter userMenuPresenter;


    public UserMenuController(UserMenu userMenu,UserMenuPresenter userMenuPresenter) {
        this.userMenu = userMenu;
        this.userMenuPresenter = userMenuPresenter;
    }

    /**
     * This method takes in a list of options and handles option display and selection.(generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the menu.
     * @param OptionPrompt what to be displayed after the options on the screen.
     * @return this returns the option that was selected by the user as a string.
     */
    private String HandleOptions(List<String> OptionList, boolean BackOption, String OptionTitle, String OptionPrompt) {
        if (BackOption) {
            this.userMenuPresenter.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.userMenuPresenter.displayOptions(OptionList,OptionPrompt);
        return(this.selectOption(OptionList));
    }

    private String selectOption(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid option. Please enter a number corresponding to one of the options.");
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionList.get(OptionChosen-1));
    }

    private int GetUserInt(String ErrorMsg){
        Scanner scanner = new Scanner(System.in);
        int UserInt;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid number.");
                scanner.next();
            }
            UserInt = scanner.nextInt();
        } while (UserInt < 0);
        return(UserInt);
    }

    /**
     * This method takes in a list of options and handles option display and selection. (generic)
     * @param OptionList the list of options you want displayed.
     * @param BackOption boolean representing if you want a back option appended to the option list and displayed.
     * @param OptionTitle the title of the option's page.
     * @param OptionPrompt what to be displayed after the options on the screen.
     * @return returns the index of the option chosen by the user corresponding the option list that was passed in.
     *          So that optionlist.get(return value) gives the option that the user has chosen.
     */
    private int HandleOptionsByIndex(List<String> OptionList, boolean BackOption, String
            OptionTitle,String OptionPrompt) {
        if (BackOption) {
            this.userMenuPresenter.addBackOption(OptionList);
        }
        System.out.println(OptionTitle);
        this.userMenuPresenter.displayOptions(OptionList,OptionPrompt);
        return(this.selectOptionByIndex(OptionList));
    }

    private int selectOptionByIndex(List<String> OptionList){
        Scanner scanner = new Scanner(System.in);
        int OptionChosen;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("That is not a valid option. Please enter a number corresponding to one of the options.");
                scanner.next();
            }
            OptionChosen = scanner.nextInt();
        } while (OptionChosen > OptionList.size() || OptionChosen <= 0);
        return(OptionChosen -1);
    }

    // THE ABOVE ARE ALL JUST HELPER METHODS THAT WILL BE USED BELOW:


    public void run() {
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);
        while(userInteracting){
        String input = this.HandleOptions(this.userMenuPresenter.constructMainMenu(),
                false,"User Main Menu","Please type a number corresponding to one of" +
                        "the above options.");
            if(input.equals("Request Item for Approval")){
                requestAddItem();
            } else if (input.equals("Browse Available Items for Trade")) {
                DisplayAvailableItems();
            } else if (input.equals("View Active Transactions")) {
                //This Still needs to be done. this.userMenu.getActiveTransactions();
            } else if (input.equals("View Past Transaction Details")) {
                viewPastTransaction();
            } else if (input.equals("View Wishlist")) {
                viewWishlist();
            } else if (input.equals("View Inventory")) {
                viewInventory();
            } else if (input.equals("Request Admin to Unfreeze Account")) {
                requestUnfreezeAccount();
            } else if (input.equals("Log Out")) {
                System.out.println("You have successfully logged out.");
                userInteracting = false;
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
     * This is the method for handling the flow of:
     * 1) Displaying all available items to trade.
     * 2) Allowing the user to click on an item
     * 3) if the account is not frozen then the user can make a transaction for an item.
     */
    private void DisplayAvailableItems(){
        boolean userInteracting = true;
        Scanner scanner = new Scanner(System.in);

        while(userInteracting){
            HashMap<Item, User> AvailableItems  = this.userMenu.getAvailableItems();
            List<Item> ItemList = new ArrayList<>(AvailableItems.keySet());

            List<String> OptionList = this.userMenuPresenter.constructAvailableItemsMenu(ItemList);
            String AvailableItemsTitle = "Available Items For Transaction:";
            String AvailableItemsPrompt = "Type the number corresponding to the item you wish to" +
                " create a transaction for. To go back to the previous menu, type the number corresponding to that" +
                "option.";

            int OptionChosen = this.HandleOptionsByIndex(OptionList,true,AvailableItemsPrompt,
                AvailableItemsTitle);
            // Logic handling back to other menu vs. your account is frozen vs proceed to make create transaction menu.
            if(OptionChosen == OptionList.size()){
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            }
            else{
                if(this.userMenu.currentUser.isFrozen()){
                    System.out.println("Your account is frozen so you cannot make an offer for this item. Please request" +
                        "to have your account unfrozen.");
                    System.out.println("You will now be taken back to the main user menu.");
                    userInteracting = false;
                }
                else {
                    Item TransactionItem = ItemList.get(OptionChosen);
                    User TransactionItemOwner = AvailableItems.get(TransactionItem);
                    userInteracting = CreateTransactionMenu(TransactionItem,TransactionItemOwner);
                }
            }
        }

    }

    /**
     * This method handles the flow for setting up a transaction for an available item assuming that the transaction
     * is allowed between the 2 users.
     *
     * @param item The item that is going to be traded.
     * @param Owner The other user that is currently the owner of the item you want to trade for.
     * @return this method returns a true if the user wants to make another transaction and returns false if the user
     * wants to head back to the main menu.
     */
    private boolean CreateTransactionMenu(Item item, User Owner) {
        Scanner scanner = new Scanner(System.in);
        boolean userInteracting = true;
        while (userInteracting) {
            System.out.println("Transaction Menu");
            System.out.println("----------------");
            List<String> transOptionList = this.userMenuPresenter.constructTransactionTypeMenu();
            String OptionChosen = this.HandleOptions(transOptionList, true, "",
                    "Select from one of " +
                            "the transaction types above.");
            Meeting FirstMeeting = MeetingDetailsMenu("First Meeting Details");
            if (OptionChosen.equals("back")) {
                System.out.println("Loading Previous Menu");
                userInteracting = false;
            }
        }
    }


    /**
     * This method walks the user through the details required for a meeting, then constructs a meeting.
     * @param MeetingTitle The first thing that will be displayed "Second Meeting Details"/"First Meeting Details"
     */
    private Meeting MeetingDetailsMenu(String MeetingTitle){
        Scanner scanner = new Scanner(System.in);
        System.out.println(MeetingTitle);
        System.out.println("Where do you want to have the first meeting?");
        String MeetingLocation = scanner.nextLine();
        LocalTime MeetingTime = this.UserTimeGetter();
        LocalDate MeetingDate = this.UserDateGetter();
        Meeting returnMeeting = new Meeting(MeetingLocation,MeetingTime,MeetingDate);
        return(returnMeeting);
    }


    /**
     * Checks the date string that the user has inputted to see if it is in the accepted format.
     * @return this returns tru iff Returns true iff it is
     *      in the accepted format dd/mm/yyyy.
     */

    private LocalTime UserTimeGetter(){
        Scanner scanner = new Scanner(System.in);
        LocalTime returnTime;
        while (true) {
            try {
                System.out.println("Please Enter the time of your meeting in the format: HH:mm:ss");
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                returnTime = LocalTime.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid time please,try again.");

            }
        }
        return(returnTime);
    }

    private LocalDate UserDateGetter(){
        Scanner scanner = new Scanner(System.in);
        LocalDate returnDate;
        while (true) {
            try {
                System.out.println("Please Enter the date of your meeting in the format: dd-mm-yyyy");
                String DateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
                returnDate = LocalDate.parse(DateString, formatter);
                break;
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid Date please,try again.");

            }
        }
        return(returnDate);
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
            int optionChosen = HandleOptionsByIndex(optionList, true, "Wishlist Menu",
                    "Select an item if you wish to remove it from your wishlist.");
            if (optionChosen == optionList.size()) {
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
            int optionChosen = HandleOptionsByIndex(optionList, true,
                    "Inventory Menu","Select an item if you wish to remove it from your inventory.");
            if (optionChosen == optionList.size()) {
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
