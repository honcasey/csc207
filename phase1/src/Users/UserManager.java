package Users;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {

    protected HashMap<UUID, User> idToUser;
    protected List<User> allUsers;

    public UserManager() {
        this.idToUser = new HashMap<>();
    }

    /**
     * Checks whether the input username is valid.
     *
     * @param username online identifier of a Users.TradingUser
     * @return True or False as boolean
     */
    public boolean checkAvailableUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
