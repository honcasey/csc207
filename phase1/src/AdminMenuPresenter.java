import java.util.List;

public class AdminMenuPresenter implements PresenterStrings{

    @Override
    public String mainMenu() {
        return "1. Check Pending Items for Approval. \n2. Check Pending Users for Approval \n3. Create " +
                "New Admin User \n4. Add New Item to a User's Wishlist/Inventory \n5. Change User Threshold " +
                "\n6. Check Pending Frozen Users Request \n7. Check Frozen Users Request  \n8. Logout  \nPick an option.";
    }

    @Override
    public String empty(String which) { return which + " list is empty. Nothing to be checked."; }

    @Override
    public String enterName(String name) {
        return "Please enter name for this " + name;
    }

    @Override
    public String successfullyAdded(String what, String who, String where) {
        return what + "has been successfully added to " + who + "'s " + where;
    }

    @Override
    public String successfullyChanged(String what, String who) {
        return who + "'s " + what + "has been successfully changed.";
    }

    @Override
    public String validOptions(List<String> optionList) {
        return "Valid options include: " + optionList.toString();
    }

    @Override
    public String whichThreshold(String whichThreshold) {
        return "What would you like to change the " + whichThreshold + " threshold to?";
    }

    @Override
    public String usernameTaken() { return "Username already taken. Please enter a different one."; }

    @Override
    public String currentThreshold(String description, int threshold) {
        return "The current " + description + " is: " + threshold;
    }

    @Override
    public String freeze(String who, String frozen) {
        return who + "'s account has been set to " + frozen;
    }

    @Override
    public String logout() { return "You have successfully logged out."; }

}