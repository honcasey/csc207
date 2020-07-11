package Users;

import java.util.*;

public class UserManager {

    protected Map<UUID, User> idToUser;
    protected List<User> allUsers;

    public UserManager() {
        idToUser = new HashMap<>();
        allUsers = new ArrayList<>();
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
