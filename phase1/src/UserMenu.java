public class UserMenu {
    private User currentUser;
    private AdminManager am;
    private UserManager um;

    public UserMenu(UserManager userManager, AdminManager adminManager, User currentUser) {
        this.currentUser = currentUser;
        am = adminManager;
        um = userManager;
    }

    public String requestAddItem(String itemName){

    }

}
