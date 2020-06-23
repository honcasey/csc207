public class UserMenuViewer {
    UserManager userManager;
    User user;

    public UserMenuViewer(UserManager userManager, User user) {
        this.userManager = userManager;
        this.user = user;
    }

    public void run() {
        UserMenu userMenu = new UserMenu();
        System.out.println("This will interact with UserMenu.");
    }
}
