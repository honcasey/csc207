import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Create and manage instances of AdminUser.
 */
public class AdminManager {
    private List<AdminUser> allAdmins;
    private List<User> flaggedAccounts;
    private List<User> frozenAccounts;

    /**
     * Creates a new empty AdminManager.
     */
    public AdminManager(List<AdminUser> admins, List<User> flaggedAccounts, List<User> frozenAccounts) {
        allAdmins = admins;
        this.flaggedAccounts = flaggedAccounts;
        this.frozenAccounts = frozenAccounts;
    }

    /**
     * Creates a new AdminUser with given username and password.
     * @param username user's account name identifier
     * @param password user's account password
     */
    public AdminUser addAdmin(String username, String password) throws InvalidAdminException {
        AdminUser newAdmin = new AdminUser(username, password);
        if (allAdmins.size() == 0) {
            allAdmins.add(newAdmin);
            return newAdmin;
        } else{
            for (AdminUser admin : allAdmins) {
                if (admin.getUsername().equals(username)) {
                    throw new InvalidAdminException();
                }
            }
        }
        allAdmins.add(newAdmin);
        return newAdmin;
    }

    /**
     * Getter for AdminUser specified by their username.
     * @param username desired AdminUser's username
     * @return AdminUser
     * @throws InvalidUserException
     */
    public AdminUser getAdmin(String username) throws InvalidAdminException {
        for (AdminUser admin : allAdmins) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }
        throw new InvalidAdminException();
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

    public boolean checkAvailableUsername(String username) {
        for (AdminUser admin : allAdmins) {
            if (admin.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
