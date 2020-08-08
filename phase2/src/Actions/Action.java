package Actions;

import Users.User;

import java.util.UUID;

/**
 * Represents an undoable action, storing the TradingUser who did the action and the activity/object involved.
 */
public class Action {
    private UUID id;
    private User user;

    public Action(User user) {
        this.id = UUID.randomUUID();;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
