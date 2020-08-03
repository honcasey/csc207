package TradingUserGUI;

import Items.Item;
import Users.TradingUser;
import Users.UserMenuController;

public class TransactionWindow {
    private UserMenuController umc;
    private Item item;
    private TradingUser owner;

    public TransactionWindow(UserMenuController umc, Item item, TradingUser owner) {
        this.umc = umc;
        this.item = item;
        this.owner = owner;
    }
}
