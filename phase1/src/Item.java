import java.util.UUID;

/**
 * Represents an item in the trading system.
 */
public class Item {
    private UUID id = UUID.randomUUID();
    private String name;
    private String description = "This is an item for trade.";

    /**
     * Creates a new item with the given name.
     * @param name This item's name.
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Returns this item's name.
     * @return this item's name as a String.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Returns a unique id associated with this item.
     * @return this item's id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets this item's name.
     * @param name This item's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets this item's name.
     * @return this item's name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets this item's description.
     * @param description A brief description of this item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets this item's description.
     * @return this item's description as a String.
     */
    public String getDescription() {
        return description;
    }

}
