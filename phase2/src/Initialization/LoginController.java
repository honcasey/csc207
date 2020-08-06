package Initialization;

import Admins.AdminManager;
import Exceptions.InvalidTradingUserException;
import Users.DemoUser;
import Users.DemoUserManager;
import Users.TradingUserManager;

public class LoginController {
    private final AdminManager am;
    private final TradingUserManager um;
    private final DemoUserManager dum;

    public LoginController(AdminManager am, TradingUserManager um, DemoUserManager dum) {
        this.am = am;
        this.um = um;
        this.dum = dum;
    }

    public boolean validUser(String username, String password) {
        return um.validUser(username, password);
    }

    public boolean validDemoUser(String username, String password) {return dum.validDemoUser(username, password);}
    public boolean availableUsername(String username) {
        return am.checkAvailableUsername(username) && um.checkAvailableUsername(username) && dum.checkAvailableUsername(username);
    }

    public void addTradingUser(String username, String password) {
        try {
            um.addTradingUser(username, password);
        } catch (InvalidTradingUserException e) {
            // TODO
        }
    }
}
