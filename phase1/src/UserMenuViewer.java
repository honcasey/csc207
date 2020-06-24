public class UserMenuViewer {
    UserManager userManager;
    AdminManager adminManager;
    User user;

    public UserMenuViewer(UserManager userManager, AdminManager adminManager, User user) {
        this.userManager = userManager;
        this.adminManager = adminManager;
        this.user = user;
    }

    public void run() {
        UserMenu userMenu = new UserMenu(userManager, adminManager, user);
        System.out.println("This will interact with UserMenu.");
    }
}
