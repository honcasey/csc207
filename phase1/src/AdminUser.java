import java.io.Serializable;
import java.util.UUID;

/**
 * Represents an administrative user in the trading system.
 */

public class AdminUser implements Serializable {
    private String username;
    private String password;
    private final UUID adminId = UUID.randomUUID();
    private boolean firstAdmin = false;

    /**
     * Constructs an instance of an Admin User based on Strings of username and password.
     */
    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for username as a String
     * @return username as a String
     */
    public String getUsername() { return username; }

    /**
     * Getter for password as a String
     * @return password as a String
     */
    public String getPassword() { return password; }

    /**
     * Getter for admin id as a UUID
     * @return admin id as a UUID
     */
    public UUID getAdminId() { return adminId; }

    /**
     * Getter for if this AdminUser is the initial administrative user
     * @return boolean whether AdminUser is initial user
     */
    public boolean isFirstAdmin() { return firstAdmin; }

    /**
     * Setter for AdminUser firstAdmin
     * @param isFirstAdmin as boolean
     */
    public void setFirstAdmin(boolean isFirstAdmin) { this.firstAdmin = isFirstAdmin; }

    /**
     * Represents the current AdminUser by their username and userId
     * @return the username and adminid separated by a comma
     */
    @Override
    public String toString() { return username + ", " + adminId; }


}
