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

    public DemoUserManager getDum(){
        return dum;
    }
}
