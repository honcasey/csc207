import java.util.HashMap;

public class UserMenuViewer {
    private UserMenu um;

    public UserMenuViewer(UserMenu userMenu) {
        um = userMenu;
    }

    public void run() {
        System.out.println("This will interact with UserMenu.");

        // when the user decides they're done
        Logout logoutSystem = new Logout();
        Logout.run();
    }
}
