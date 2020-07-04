import java.util.List;

public class AdminMenuPresenter implements PresenterStrings{

    @Override
    public void mainMenu() {
        System.out.println("1. Check Pending Items for Approval. \n2. Check Pending Users for Approval \n3. Create " +
                "New Admin User \n4. Add New Item to a User's Wishlist/Inventory \n5. Change User Threshold " +
                "\n6. Logout \nPick an option.");
    }

    @Override
    public void empty(String which) { System.out.println(which + " list is empty. Nothing to be checked."); }

    @Override
    public void enterName(String name) {
        System.out.println("Please enter name for this " + name);
    }

    @Override
    public void successfullyAdded(String what, String who, String where) {
        System.out.println(what + "has been successfully added to " + who + "'s " + where);
    }

    @Override
    public void successfullyChanged(String what, String who) {
        System.out.println(who + "'s " + what + "has been successfully changed.");
    }

    @Override
    public void validOptions(List<String> optionList) {
        System.out.println("Valid options include: " + optionList.toString());
    }

    @Override
    public void whichThreshold(String whichThreshold) {
        System.out.println("What would you like to change the " + whichThreshold + " threshold to?");
    }

    @Override
    public void usernameTaken() { System.out.println("Username already taken. Please enter a different one."); }

    @Override
    public void currentThreshold(String description, int threshold) {
        System.out.println("The current " + description + " is: " + threshold);
    }


}