package Admins;

import Exceptions.InvalidAdminException;
import Users.User;

import java.util.ArrayList;
import java.util.List;
/**
 * Creates and manages instances of AdminUsers, as well as all accounts that have been flagged or frozen, and a list
 * of Users who have requested their accounts to be unfrozen.
 */
public class AdminManager {
    private List<AdminUser> allAdmins;
    /**
     * A list of  Users that have had their account flagged to be frozen automatically by the system.
     */
    private List<User> flaggedAccounts;

    /**
     * A list of Users that have had their account frozen after approval by an Admin.
     */
    private List<User> frozenAccounts;

    /**
     * A list of Users that have requested their account to be unfrozen.
     */
    private List<User> pendingFrozenUsers;

    /**
     * Creates an AdminManager.
     * @param admins list of all AdminUsers in the system
     * @param flaggedAccounts list of all Users that have had their account flagged to be frozen automatically by the system.
     * @param frozenAccounts list of all Users tha have had their account frozen after approval by an Admin
     */
    public AdminManager(List<AdminUser> admins, List<User> flaggedAccounts, List<User> frozenAccounts) {
        allAdmins = admins;
        this.flaggedAccounts = flaggedAccounts;
        this.frozenAccounts = frozenAccounts;
        pendingFrozenUsers = new ArrayList<>();
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
        }
        for (AdminUser admin : allAdmins)
            if (admin.getUsername().equals(username)) throw new InvalidAdminException();
        allAdmins.add(newAdmin);
        return newAdmin;
    }

    /**
     * Getter for AdminUser specified by their username.
     * @param username desired AdminUser's username
     * @return AdminUser
     * @throws InvalidAdminException the inputted username does not match any existing Admins.AdminUser account in the system.
     */
    public AdminUser getAdmin(String username) throws InvalidAdminException {
        for (AdminUser admin : allAdmins) if (admin.getUsername().equals(username)) return admin;
        throw new InvalidAdminException();
    }

    /**
     * Getter for all admins in the trading system.
     * @return list of all AdminUsers that are currently in the trading system.
     */
    public List<AdminUser> getAllAdmins() {
        return allAdmins;
    }

    /**
     * Returns whether the input username and password match an existing AdminUser account.
     * @param username inputted username
     * @param password inputted password
     * @return boolean whether the inputs match an existing AdminUser account or not.
     */
    public boolean validAdmin(String username, String password) { // TO-DO: check if the account also matches a normal User, and if yes, should throw something?
        for (AdminUser admin : allAdmins)
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) return true;
        return false;
    }

    /**
     * Returns whether the input username is available, or in other words, does not match any existing account usernames.
     * @param username inputted username
     * @return boolean whether the input matches an existing AdminUser account or not.
     */
    public boolean checkAvailableUsername(String username) { // TO-DO: check if the username also matches any usernames of a normal User and not just AdminUsers?
        for (AdminUser admin : allAdmins) if (admin.getUsername().equals(username)) return false;
        return true;
    }

    /**
     * Getter for this AdminMenu's list of all pending frozen Users that have been requested to be unfrozen.
     * @return a list of all users that have requested to be unfrozen.
     */
    public List<User> getPendingFrozenUsers() { return pendingFrozenUsers; }

    /**
     * Getter for this AdminMenu's list of all Users who are frozen.
     * @return a list of all users whos accounts are frozen.
     */
    public List<User> getFrozenAccounts() { return frozenAccounts; }

    /**
     * Getter for this AdminMenu's list of all flagged Users that have been flagged to be frozen by the system.
     * @return a list of all users that have been flagged to be frozen.
     */
    public List<User> getFlaggedAccounts() { return flaggedAccounts; }
}