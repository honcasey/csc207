import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class UserMenuPresenter extends MenuPresenter {


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
