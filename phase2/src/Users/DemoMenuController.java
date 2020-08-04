package Users;

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
    public void convertToTradingUser(DemoUser demoUser, String username, String password) throws InvalidTradingUserException {
        tum.addTradingUser(username, password);
        dum.removeDemoUser(demoUser.getUserId());

        // TODO: need to figure out how to handle the case where the user wants to convert account to a Trading User one but the username is taken
    }



}
