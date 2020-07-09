package Items;

import Exceptions.InvalidItemException;

import java.util.List;
import java.util.UUID;

public class ItemManager {
    private List<Item> items;

    public ItemManager(List<Item> items) {
        this.items = items;
    }

    public Item getItem(UUID id) throws InvalidItemException {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new InvalidItemException();
    }
}
