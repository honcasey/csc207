import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Create and manage instances of item.
 */
public class ItemManager {
    private List<Item> allItems = new ArrayList<>();

    /**
     * Creates and returns a new item with the given name and description and adds to
     * history of all items created.
     * @param name The item's name.
     * @param description the item's description.
     * @return a new item with specified name and description.
     */
    public Item createItem(String name, String description) {
        Item item = new Item(name);
        item.setDescription(description);
        allItems.add(item);
        return item;
    }

    /**
     * Returns the item with specified id under the condition, returns null otherwise.
     * @param id The item's id.
     * @return the item with the associated id, null if the item does not exist.
     */
    public Item getItem(UUID id) {
        Item desiredItem = null;
        for (Item item : allItems) {
            if((item.getId().equals(id))) {
                desiredItem = item;
            }
        }
        return desiredItem;
    }

}
