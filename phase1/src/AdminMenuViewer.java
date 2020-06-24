public class AdminMenuViewer {
    AdminManager adminManager;
    UserManager userManager;
    AdminUser admin;

    public AdminMenuViewer(AdminManager adminManager, UserManager userManager, AdminUser admin) {
        this.adminManager = adminManager;
        this.userManager = userManager;
        this.admin = admin;
    }

    public void run() {
        AdminMenu adminMenu = new AdminMenu(adminManager, userManager, admin);
        System.out.println("This will interact with AdminMenu.");
    }
}
