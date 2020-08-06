package Initialization;

import Admins.AdminManager;
import Exceptions.InvalidTradingUserException;
import Users.TradingUserManager;

public class LoginController {
    private final AdminManager am;
    private final TradingUserManager um;

    public LoginController(AdminManager am, TradingUserManager um) {
        this.am = am;
        this.um = um;
    }

    public boolean validUser(String username, String password) {
        return um.validUser(username, password);
    }

    public boolean availableUsername(String username) {
        return am.checkAvailableUsername(username) && um.checkAvailableUsername(username);
    }

    public void addTradingUser(String username, String password) {
        try {
            um.addTradingUser(username, password);
        } catch (InvalidTradingUserException e) {
            // TODO
        }
    }
}
