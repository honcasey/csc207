package Initialization;

import Admins.AdminManager;
import Exceptions.InvalidDemoUserException;
import Exceptions.InvalidTradingUserException;
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

    public void addDemoUser(String username, String password){
        try{
            dum.addDemoUser(username, password);
        } catch (InvalidDemoUserException e){
            //
        }
    }

    public void addTradingUser(String username, String password, String city) {
        try {
            um.addTradingUser(username, password, city);
        } catch (InvalidTradingUserException e) {
            // TODO
        }
    }
}
