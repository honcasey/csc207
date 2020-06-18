/**
 * Represents an administrative user in the trading system.
 */

public class AdminUser {
    String username;
    String password;
    int userId;

    /**
     * Constructs an instance of an Admin User based on Strings of username and password.
     */
    public AdminUser(String username, String password) {
        this.username = username;
        this.password = password;
        // TO-DO: set a (random?) user id to each Admin User
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
     * Getter for userid as an int
     * @return userid as an integer
     */
    public int getUserId() { return userId; }

    /**
     * Represents the current AdminUser by their username and userId
     * @return the username and userid separated by a comma
     */
    public String toString() { return username + ", " + userId; }


}
