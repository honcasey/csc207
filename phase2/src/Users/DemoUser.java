package Users;

import java.io.Serializable;

/**
 * <h1>DemoUser</h1>
 * Represents a DemoUser in the TradingSystem
 * Does not have the ability to Trade or communicate with an Admin User
 */

public class DemoUser extends User implements Serializable {
    public DemoUser(String username, String password) {
        super(username, password);
    }

}
