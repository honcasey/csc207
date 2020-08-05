package Users;

import Exceptions.InvalidDemoUserException;
import Exceptions.InvalidTradingUserException;
import Items.ItemManager;

public class DemoMenuController {

    public DemoUser currentDemoUser = null;
    private DemoUserManager dum;
    private TradingUserManager tum;
    private ItemManager im;

    public DemoMenuController(DemoUserManager dum, TradingUserManager tum, ItemManager im) {
        this.dum = dum;
        this.tum = tum;
        this.im = im;
    }

    public DemoUserManager getDum(){
        return dum;
    }

    /**
     * Returns true if the Username is available
     */
    public boolean availableUsername(String username) {
        return dum.checkAvailableUsername(username);
    }

    public void addDemoUser(String username, String password) throws InvalidDemoUserException {
        dum.addDemoUser(username, password);
    }
}
