package Actions;

import Users.TradingUser;

import java.util.UUID;

/**
 * Represents an undoable action, storing the TradingUser who did the action and the activity/object involved.
 */
public class Action {
    private UUID id;
    private TradingUser user;

    public Action(TradingUser user) {
        this.id = UUID.randomUUID();;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public TradingUser getUser() {
        return user;
    }
}
