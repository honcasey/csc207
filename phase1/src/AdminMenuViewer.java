public class AdminMenuViewer {
    AdminManager adminManager;
    AdminUser admin;

    public AdminMenuViewer(AdminManager adminManager, AdminUser admin) {
        this.adminManager = adminManager;
        this.admin = admin;
    }

    public void run() {
        AdminMenu adminMenu = new AdminMenu();
        System.out.println("This will interact with AdminMenu.");
    }
}
