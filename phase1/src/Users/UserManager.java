package Users;

import java.util.*;

abstract class UserManager {

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
