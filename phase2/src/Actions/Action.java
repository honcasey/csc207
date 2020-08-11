package Actions;

import Users.TradingUser;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents an undoable action, storing the TradingUser who did the action and the activity/object involved.
 */
public abstract class Action implements Serializable {
    private UUID id;
    private TradingUser user;

    public Action(TradingUser user) {
        this.id = UUID.randomUUID();;
        this.user = user;
    }

    public abstract boolean isEditAction();

    public abstract boolean isAddorDeleteAction();

    public UUID getId() {
        return id;
    }

    public TradingUser getUser() {
        return user;
    }
}
