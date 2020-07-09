package Items;

import Exceptions.InvalidItemException;
import Transactions.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemManager {
    private HashMap<UUID, Item> allItems;

    public ItemManager(HashMap<UUID, Item> items) {
        this.allItems = items;
    }

    public Item getItem(UUID id) throws InvalidItemException {
        if (allItems.containsKey(id)){
        return allItems.get(id);
        }
        else{
        throw new InvalidItemException();}
    }
}
