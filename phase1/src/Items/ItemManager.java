package Items;

import Exceptions.InvalidItemException;
import java.util.*;

/**
 * <h1>ItemManager</h1>
 * Manages all items in the system.
 * Contains a map of all Items in the system with a mapping to the item's UUID.
 */
public class ItemManager {
    public Map<UUID, Item> allItems;

    /**
     * Constructs an instance of ItemManager with the inputted map of all items in the system.
     * @param items map of all items with the UUID.
     */
    public ItemManager(Map<UUID, Item> items) {
        this.allItems = items;
    }

    /**
     * Getter for this ItemManager's allItems.
     * @return map of all items to their UUIDs.
     */
    public Map<UUID, Item> getAllItems() { return allItems; }

    /**
     * Adds an item to the list of all items.
     * @param item item to add to list of all items
     */
    public void addItem (Item item){
        allItems.put(item.getId(), item);
    }

    /**
     * Getter for item object with the given UUID.
     * @param id UUID of item
     * @return Item object corresponding to the item matching the given UUID
     * @throws InvalidItemException item doesn't exist in this ItemManager's allItems
     */
    public Item getItem(UUID id) throws InvalidItemException {
        if (allItems.containsKey(id)){
            return allItems.get(id);
        } else {
            throw new InvalidItemException();
        }
    }

    /**
     * Converts a list of UUIDs to a list of Items.
     * @param ids list of all UUIDs
     * @return list of all Items
     */
    public List<Item> convertIdsToItems(List<UUID> ids) {
        List<Item> items = new ArrayList<>();
        for (UUID id : ids) {
            items.add(allItems.get(id));
        }
        return items;
    }
}