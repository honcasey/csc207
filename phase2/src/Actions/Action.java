package Actions;

import Users.TradingUser;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents an undoable action, storing the TradingUser who did the action and the activity/object involved.
 */
public abstract class Action implements Serializable {
    private UUID id;
    private UUID userId;

    public Action(UUID userId) {
        this.id = UUID.randomUUID();;
        this.userId = userId;
    }

    public abstract boolean isEditAction();

    public abstract boolean isAddorDeleteAction();

    /**
     * Getter for the Id of an Action
     * @return UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Getter for the User who is involved in an Action
     * @return UUID of the User
     */
    public UUID getUser() {
        return userId;
    }
}
