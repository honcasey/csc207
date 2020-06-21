import java.util.UUID;

/**
 * Represents an administrative user in the trading system.
 */

public class AdminUser {
    private String username;
    private String password;
    private UUID adminId = UUID.randomUUID();

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
     * Setter for username as a String
     * @param username new username as a String
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Getter for password as a String
     * @return password as a String
     */
    public String getPassword() { return password; }

    /**
     * Setter for password as a String
     * @param password new password as a String
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Getter for admin id as a UUID
     * @return admin id as a UUID
     */
    public UUID getAdminId() { return adminId; }

    /**
     * Represents the current AdminUser by their username and userId
     * @return the username and adminid separated by a comma
     */
    public String toString() { return username + ", " + adminId; }


}
