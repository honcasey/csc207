import java.util.HashMap;

public class AdminMenuViewer {
    private AdminMenu am;

    public AdminMenuViewer(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public void run() {
        System.out.println("This will interact with AdminMenu.");

        // when the user decides they're done
        Logout logoutSystem = new Logout();
        Logout.run();
    }
}
