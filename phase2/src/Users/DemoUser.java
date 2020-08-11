package Users;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>DemoUser</h1>
 * Represents a DemoUser in the Initialization.TradingSystem
 * Does not have the ability to Trade or communicate with an Admin User
 */

public class DemoUser extends User implements Serializable {
    private final List<UUID> wishList;
    private final List<UUID> inventory;
    public DemoUser(String username, String password) {
        super(username, password);
        wishList = new ArrayList<>();
        inventory = new ArrayList<>();

    }

    public List<UUID> getInventory(){
        return inventory;
    }

    public List<UUID> getWishlist(){
        return wishList;
    }
}
