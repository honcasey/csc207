package Users;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents the parent class of TradingUser and AdminUser
 */

public abstract class User implements Serializable {
    private String username;
    private String password;
    private final UUID userId = UUID.randomUUID();

    public User(String username, String password) {
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
     * Getter for userId as a UUID
     * @return userId as a UUID
     */
    public UUID getUserId() { return userId; }
}

