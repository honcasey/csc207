import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserMenuPresenter {

    private UserMenu userMenu;

    /**
     * This Presenter class contains helper methods that will be used to format and output the prompts that a user sees.
     *
     * Class variables representing common strings that users will see as prompts will be here.
     *
     * Methods that combine these common strings will be here.
     *
     * Methods that print output(Phase 1)/ display stuff(Phase 2) will be here.
     *
     * Class variables:
     *
     * UserMenu: Instance of UserMenu class.
     * menuOptions: This is a dictionary mapping the menu to the options that a user can Select.
     */

    public UserMenuPresenter(UserMenu userMenu){
        this.userMenu = userMenu;
    }

    /**
     * Formats and displays a list of options to the user.
     * @param OptionList the list of options that you want to be displayed.
     */
    protected void displayOptions(List<String> OptionList, String OptionPrompt){
        for(int i = 0; i < OptionList.size(); i++){
            String index = Integer.toString(i+1);
            String OutputLine =  index + ". " + OptionList.get(i);
            System.out.println(OutputLine);
        }
        System.out.println(OptionPrompt);
    }

    /**
     * Adds Back option at the end of options being displayed to the user.
     * @param OptionList The list of options being displayed prior to calling this method.
     */
    protected void addBackOption(List<String> OptionList){
        String LastIndex = Integer.toString(OptionList.size() + 1);
        String LastOption = ". Go back";
        OptionList.add(LastIndex + LastOption);
    }


    /**
     * Construct methods like this return a list of options/prompts that the menu will have.
     *
     * This particular method constructs the option list that the user will be greeted with upon first logging into
     * the program.
     * @return this returns a list of options that the user can choose from.
     */
    public List<String> constructMainMenu(){
        List<String> MenuOptionList = new ArrayList<>();
        MenuOptionList.add("Request Item for Approval");
        MenuOptionList.add("Browse Available Items for Trade");
        MenuOptionList.add("View Active Transactions");
        MenuOptionList.add("View Past Transaction Details");
        MenuOptionList.add("View Wishlist");
        MenuOptionList.add("View Inventory");
        MenuOptionList.add("Request Admin to Unfreeze Account");
        MenuOptionList.add("Log Out");
        return(MenuOptionList);
    }

    /**
     * Construct methods like this will return a list of options/prompts that the menu will have.
     * @param ItemList this is a list of items(all available items that are for trade from other users.)
     * @return this returns the list of options that the user can choose from.
     */
    public List<String> constructAvailableItemsMenu(List<Item> ItemList){
        System.out.println("Available Items for Trade:");
        String ItemOutputName = " Item Name: ";
        String ItemOutputDescription = " |  Item Description: ";
        // Making Option List

        List<String> AvailableItemOptionList = new ArrayList<>();
        for (Item item : ItemList) {
            AvailableItemOptionList.add(ItemOutputName + item.getName() + ItemOutputDescription + item.getDescription());
        }
        return(AvailableItemOptionList);
    }

    /**
     * Construct methods like this will return a list of options/prompts that the menu will have.
     * @return This returns a list of all the possible transaction type options.
     */

    public List<String> constructTransactionTypeMenu(){
        List<String> OptionList = new ArrayList<>();
        OptionList.add("One Way Temporary Trade");
        OptionList.add("One Way Permanent Trade");
        OptionList.add("Two Way Temporary Trade");
        OptionList.add("Two Way Permanent Trade");
        return(OptionList);
    }

}
