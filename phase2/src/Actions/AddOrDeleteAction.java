package Actions;

import Items.Item;
import Users.TradingUser;

import java.util.UUID;

/**
 * Represents an "adding" or "deleting" action, including adding or deleting an Item from a TradingUser's inventory or wishlist.
 */
public class AddOrDeleteAction extends Action {
    private Item added = null;
    private Item removed = null;

    public AddOrDeleteAction(UUID id, TradingUser user) {
        super(id, user);
    }

    /**
     * Sets the Item object of this Action if an item is being added.
     * @param added Item that is being added to a list.
     */
    public void setAdded(Item added) {
        this.added = added;
    }

    /**
     * Sets the Item object of this Action if an item is being removed.
     * @param removed Item that is being removed from a list.
     */
    public void setRemoved(Item removed) {
        this.removed = removed;
    }

    /**
     * Getter for Item being added or removed.
     */
    public Item getItem() {
        if (added != null) {
            return added;
        }
        else {
            return removed;
        }
    }
}
