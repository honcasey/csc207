import java.util.HashMap;

public class AdminMenuViewer {
    private AdminMenu am;

    public AdminMenuViewer(AdminMenu adminMenu) {
        am = adminMenu;
    }

    public void run() {
        System.out.println("This will interact with AdminMenu.");

        // when the user decides they're done and logs out
        System.out.println("You have successfully logged out.");

    }
}
