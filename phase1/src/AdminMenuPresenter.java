import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminMenuPresenter {
    private AdminMenu am;
    private int input;

    public AdminMenuPresenter(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public String mainMenu() {
        return "1. Check Pending Items for Approval. \n2. Check Pending Users for Approval \n3. Create " +
                "New Admin User \n4. Add New Item to a User's Wishlist/Inventory \n5. Change User Threshold " +
                "\n6. Logout \nPick an option.";
    }

    public String empty(String which) { // do we want to make this an Interface for AdminMenu and UserMenu?
        return which + " list is empty. Nothing to be checked.";
    }

    public String enterName(String name) {
        return "Please enter name for this " + name;
    }

    public String successfullyAdded(String what, String who, String where) {
        return what + "has been successfully added to " + who + "'s " + where;
    }

    public String successfullyChanged(String what, String who) {
        return who + "'s " + what + "has been successfully changed.";
    }

    public String validOptions(List<String> optionList) {
        return "Valid options include: " + optionList.toString();
    }

    public String whichThreshold(String whichThreshold) {
        return "What would you like to change the " + whichThreshold + " threshold to?";
    }




}