import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Create and manage instances of AdminUser.
 */
public class AdminManager {
    private List<AdminUser> allAdmins;

    /**
     * Creates a new empty AdminManager.
     */
    public AdminManager(List<AdminUser> admins) {
        allAdmins = admins;
    }

    /**
     * Creates a new AdminUser with given username and password.
     * @param username user's account name identifier
     * @param password user's account password
     */
    public AdminUser addAdmin(String username, String password) {
        AdminUser admin = new AdminUser(username, password);
        if (!allAdmins.contains(admin)) {
            allAdmins.add(admin);
            return admin;
        }
        return null;
    }

    public AdminUser getAdmin(String username) {
        for (AdminUser admin : allAdmins) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        // TODO precondition that this admin must exist
        return null;
    }

    public List<AdminUser> getAllAdmins() {
        return allAdmins;
    }

    public boolean validAdmin(String username, String password) {
        for (AdminUser admin : allAdmins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
