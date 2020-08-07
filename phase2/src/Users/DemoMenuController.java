package Users;

import Exceptions.InvalidDemoUserException;
import Items.ItemManager;

public class DemoMenuController {

    public DemoUser currentDemoUser = null;
    private final DemoUserManager dum;
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

    public boolean validDemoUser(String username, String password){
        return dum.validDemoUser(username, password);
    }

    public void setCurrentDemoUser(String username){
        try{
            currentDemoUser = dum.getDemoUser(username);
        } catch (InvalidDemoUserException e) {
            // TODO
        }
    }
}
