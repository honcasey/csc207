package Users;

import java.io.Serializable;
import java.util.UUID;

abstract class User implements Serializable {
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
     * Getter for user id as a UUID
     * @return userid as a UUID
     */
    public UUID getUserId() { return userId; }
}

